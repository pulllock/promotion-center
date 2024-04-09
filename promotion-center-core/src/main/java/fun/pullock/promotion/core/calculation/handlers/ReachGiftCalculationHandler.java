package fun.pullock.promotion.core.calculation.handlers;

import fun.pullock.promotion.core.calculation.AbstractCalculationHandler;
import fun.pullock.promotion.core.calculation.CalculateContext;
import fun.pullock.promotion.core.enums.RuleType;
import fun.pullock.promotion.core.model.calculate.RuleTargetCompositeDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ReachGiftCalculationHandler extends AbstractCalculationHandler {

    @Override
    public RuleType ruleType() {
        return RuleType.REACH_GIFT;
    }

    @Override
    public void calculate(CalculateContext context) {
        // 按照规则类型排序
        Map<Integer, List<RuleTargetCompositeDTO>> groupByRuleType = context.getRuleTargetComposites()
                .stream()
                .collect(Collectors.groupingBy(RuleTargetCompositeDTO::getRuleType));
        List<RuleTargetCompositeDTO> ruleTargets = groupByRuleType.get(ruleType().getType());
        if (CollectionUtils.isEmpty(ruleTargets)) {
            return;
        }

        // 满赠逻辑
    }
}
