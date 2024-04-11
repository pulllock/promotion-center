package fun.pullock.promotion.core.calculation.handlers;

import fun.pullock.promotion.core.calculation.AbstractCalculationHandler;
import fun.pullock.promotion.core.calculation.CalculateContext;
import fun.pullock.promotion.core.enums.RuleMode;
import fun.pullock.promotion.core.enums.RuleScope;
import fun.pullock.promotion.core.enums.RuleType;
import fun.pullock.promotion.core.model.dto.RuleDTO;
import fun.pullock.promotion.core.model.dto.calculate.RuleTargetsCompositeDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ReachExemptionCalculationHandler extends AbstractCalculationHandler {

    @Override
    public RuleType ruleType() {
        return RuleType.REACH_EXEMPTION;
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
            // 规则
            RuleDTO rule = ruleTarget.getRule();

            // 单商品满减
            if (rule.getScope() == RuleScope.PRODUCT.getScope() && rule.getMode() == RuleMode.SINGLE.getMode()) {
                
            }
            // 多商品满减
            else if (rule.getScope() == RuleScope.PRODUCT.getScope() && rule.getMode() == RuleMode.ACCUMULATE.getMode()) {
                
            }
            // 单商户满减
            else if (rule.getScope() == RuleScope.SELLER.getScope() && rule.getMode() == RuleMode.SINGLE.getMode()) {

            }
            // 多商户满减
            else if (rule.getScope() == RuleScope.SELLER.getScope() && rule.getMode() == RuleMode.ACCUMULATE.getMode()) {

            }
            // 单类目满减
            else if (rule.getScope() == RuleScope.CATEGORY.getScope() && rule.getMode() == RuleMode.SINGLE.getMode()) {

            }
            // 多类目满减
            else if (rule.getScope() == RuleScope.CATEGORY.getScope() && rule.getMode() == RuleMode.ACCUMULATE.getMode()) {

            }
        }
    }
}
