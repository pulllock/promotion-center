package fun.pullock.promotion.core.service;

import fun.pullock.promotion.api.enums.CalculationBizType;
import fun.pullock.promotion.api.model.param.CalculateParam;
import fun.pullock.promotion.api.model.result.CalculateResult;
import fun.pullock.promotion.core.calculation.CalculateContext;
import fun.pullock.promotion.core.calculation.calculator.CalculatorFactory;
import fun.pullock.promotion.core.model.dto.calculate.OrderInfoDTO;
import fun.pullock.promotion.core.model.dto.calculate.RuleTargetsCompositeDTO;
import fun.pullock.starter.json.Json;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionCalculationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PromotionCalculationService.class);

    @Resource
    private RuleService ruleService;

    @Resource
    private CalculatorFactory calculatorFactory;

    public CalculateResult calculate(CalculateParam param) {
        // 查询绑定的规则对象和规则
        OrderInfoDTO orderInfo = extractOrderInfo(param);
        List<RuleTargetsCompositeDTO> ruleTargetComposites = ruleService.queryRuleTargets(orderInfo);

        // TODO 排序

        // 计算
        CalculateContext context = new CalculateContext();
        context.setParam(param);
        context.setRuleTargetComposites(ruleTargetComposites);
        context.setResult(new CalculateResult());
        LOGGER.info("Before calculate: {}", Json.toJson(param));
        calculatorFactory.getCalculator(CalculationBizType.of(param.getBizType())).execute(context);
        LOGGER.info("After calculate: {}", Json.toJson(context.getParam()));
        return context.getResult();
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
