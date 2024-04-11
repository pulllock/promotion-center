package fun.pullock.promotion.core.calculation.handlers;

import fun.pullock.promotion.api.model.param.CalculateParam;
import fun.pullock.promotion.core.calculation.AbstractCalculationHandler;
import fun.pullock.promotion.core.calculation.CalculateContext;
import fun.pullock.promotion.core.enums.RuleMode;
import fun.pullock.promotion.core.enums.RuleScope;
import fun.pullock.promotion.core.enums.RuleType;
import fun.pullock.promotion.core.model.dto.CouponRuleDTO;
import fun.pullock.promotion.core.model.dto.RuleDTO;
import fun.pullock.promotion.core.model.dto.RuleTargetDTO;
import fun.pullock.promotion.core.model.dto.calculate.RuleTargetsCompositeDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CouponCalculationHandler extends AbstractCalculationHandler {

    @Override
    public RuleType ruleType() {
        return RuleType.COUPON;
    }

    @Override
    public void calculate(CalculateContext context) {
        // 按照规则类型排序
        Map<Integer, List<RuleTargetsCompositeDTO>> groupByRuleType = context.getRuleTargetComposites()
                .stream()
                .collect(Collectors.groupingBy(RuleTargetsCompositeDTO::getRuleType));
        List<RuleTargetsCompositeDTO> ruleTargets = groupByRuleType.get(ruleType().getType());
        if (CollectionUtils.isEmpty(ruleTargets)) {
            return;
        }

        for (RuleTargetsCompositeDTO ruleTarget : ruleTargets) {
            CouponRuleDTO couponRule = ruleTarget.getCouponRule();
            if (couponRule == null) {
                continue;
            }

            // 规则
            RuleDTO rule = ruleTarget.getRule();

            // 单商品优惠券
            if (rule.getScope() == RuleScope.PRODUCT.getScope() && rule.getMode() == RuleMode.SINGLE.getMode()) {
                // 当前规则绑定的商品对象
                List<RuleTargetDTO> products = ruleTarget.getRuleTargets();
                // 所有商品ID
                List<Long> productIds = products.stream().map(RuleTargetDTO::getTargetId).collect(Collectors.toList());

                // 优惠券门槛值
                Long threshold = ruleTarget.getCouponRule().getThreshold();
                // 优惠券优惠值
                Long discount = ruleTarget.getCouponRule().getDiscount();

                CalculateParam.Product promotionProduct = context.getParam().getOrderItems()
                        .stream()
                        .flatMap(orderItem -> orderItem.getProducts().stream())
                        .filter(product -> productIds.contains(product.getSkuId()))
                        .filter(product -> product.getPrice() >= threshold)
                        .findFirst()
                        .orElse(null);
                if (promotionProduct == null) {
                    return;
                }

                // 找到了可以使用优惠券的商品
                // 优惠金额大于等于实付金额
                if (discount >= promotionProduct.getPrice()) {
                    // 商品金额设置为优惠后的金额
                    promotionProduct.setPrice(0L);
                }
                // 优惠金额小于实付金额
                else {
                    promotionProduct.setPrice(promotionProduct.getPrice() - discount);
                }
            }
            // 多商品优惠券
            else if (rule.getScope() == RuleScope.PRODUCT.getScope() && rule.getMode() == RuleMode.ACCUMULATE.getMode()) {
                // 当前规则绑定的商品对象
                List<RuleTargetDTO> products = ruleTarget.getRuleTargets();
                // 所有商品ID
                List<Long> productIds = products.stream().map(RuleTargetDTO::getTargetId).collect(Collectors.toList());

                // 优惠券门槛值
                Long threshold = ruleTarget.getCouponRule().getThreshold();
                // 优惠券优惠值
                Long discount = ruleTarget.getCouponRule().getDiscount();

                List<CalculateParam.Product> promotionProducts = context.getParam().getOrderItems()
                        .stream()
                        .flatMap(orderItem -> orderItem.getProducts().stream())
                        .filter(product -> productIds.contains(product.getSkuId()))
                        .collect(Collectors.toList());

                if (CollectionUtils.isEmpty(promotionProducts)) {
                    return;
                }

                long total = promotionProducts
                        .stream()
                        .mapToLong(product -> product.getPrice())
                        .sum();
                // 总金额小于门槛值
                if (total < threshold) {
                    return;
                }

                // 优惠金额大于实付总金额
                if (discount >= total) {
                    for (CalculateParam.Product promotionProduct : promotionProducts) {
                        // 商品金额设置为优惠后的金额
                        promotionProduct.setPrice(0L);
                    }
                }

                // 将优惠金额分摊到每个商品上
                // 已使用掉的折扣金额
                long usedDiscount = 0L;
                for (int i = 0; i < promotionProducts.size(); i++) {
                    CalculateParam.Product product = promotionProducts.get(i);
                    if (i == promotionProducts.size() - 1) {
                        // 最后一个商品的折扣金额为剩余的所有折扣金额
                        long productDiscount = discount - usedDiscount;
                        product.setPrice(product.getPrice() - productDiscount);
                    } else {
                        BigDecimal price = new BigDecimal(String.valueOf(product.getPrice()));
                        BigDecimal totalPrice = new BigDecimal(String.valueOf(total));
                        BigDecimal discountPrice = new BigDecimal(String.valueOf(discount));

                        // 当前商品价格占所有商品总价格的比例
                        BigDecimal rate = price.divide(totalPrice, 3, RoundingMode.FLOOR);

                        // 当前商品的折扣金额
                        long productDiscount = discountPrice.multiply(rate).setScale(0, RoundingMode.FLOOR).longValue();
                        product.setPrice(product.getPrice() - productDiscount);

                        // 累计已使用的折扣金额
                        usedDiscount += productDiscount;
                    }
                }

            }
            // 单商户优惠券
            else if (rule.getScope() == RuleScope.SELLER.getScope() && rule.getMode() == RuleMode.SINGLE.getMode()) {

            }
            // 多商户优惠券
            else if (rule.getScope() == RuleScope.SELLER.getScope() && rule.getMode() == RuleMode.ACCUMULATE.getMode()) {

            }
            // 单类目优惠券
            else if (rule.getScope() == RuleScope.CATEGORY.getScope() && rule.getMode() == RuleMode.SINGLE.getMode()) {

            }
            // 多类目优惠券
            else if (rule.getScope() == RuleScope.CATEGORY.getScope() && rule.getMode() == RuleMode.ACCUMULATE.getMode()) {

            }
        }
    }
}
