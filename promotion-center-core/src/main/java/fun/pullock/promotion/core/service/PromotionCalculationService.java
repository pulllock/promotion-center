package fun.pullock.promotion.core.service;

import fun.pullock.promotion.api.model.param.CalculateParam;
import fun.pullock.promotion.api.model.result.CalculateResult;
import fun.pullock.promotion.core.model.calculate.OrderInfoDTO;
import fun.pullock.promotion.core.model.calculate.RuleTargetCompositeDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PromotionCalculationService {

    @Resource
    private RuleService ruleService;

    public CalculateResult calculate(CalculateParam param) {
        // 查询绑定的规则对象和规则
        OrderInfoDTO orderInfo = extractOrderInfo(param);
        List<RuleTargetCompositeDTO> ruleTargetComposites = ruleService.queryRuleTargets(orderInfo);

        // TODO 排序

        // 按照规则类型排序
        Map<Integer, List<RuleTargetCompositeDTO>> groupByRuleType = ruleTargetComposites
                .stream()
                .collect(Collectors.groupingBy(RuleTargetCompositeDTO::getRuleType));

        // TODO 计算
        return null;
    }

    private OrderInfoDTO extractOrderInfo(CalculateParam param) {
        OrderInfoDTO orderInfo = new OrderInfoDTO();
        for (CalculateParam.OrderItem orderItem : param.getOrderItems()) {
            // 商户ID
            orderInfo.getSellerIds().add(orderItem.getSellerId());

            for (CalculateParam.Product product : orderItem.getProducts()) {
                // SKU ID
                orderInfo.getSkuIds().add(product.getSkuId());

                // 类目ID
                orderInfo.getCategoryIds().add(product.getCategoryId());
            }
        }
        return orderInfo;
    }
}
