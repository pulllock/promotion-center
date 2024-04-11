package fun.pullock.promotion.core.calculation.handlers;

import fun.pullock.promotion.core.calculation.AbstractCalculationHandler;
import fun.pullock.promotion.core.calculation.CalculateContext;
import fun.pullock.promotion.core.enums.RuleType;
import fun.pullock.promotion.core.model.dto.calculate.RuleTargetsCompositeDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ReachDiscountCalculationHandler extends AbstractCalculationHandler {

    @Override
    public RuleType ruleType() {
        return RuleType.REACH_DISCOUNT;
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

        // 满减逻辑
    }
}
