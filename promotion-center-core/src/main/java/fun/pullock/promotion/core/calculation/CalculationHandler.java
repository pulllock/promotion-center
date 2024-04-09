package fun.pullock.promotion.core.calculation;

import fun.pullock.promotion.core.enums.RuleType;

public interface CalculationHandler {

    RuleType ruleType();

    void calculate(CalculateContext context);
}
