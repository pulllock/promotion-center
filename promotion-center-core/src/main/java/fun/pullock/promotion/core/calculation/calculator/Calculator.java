package fun.pullock.promotion.core.calculation.calculator;

import fun.pullock.promotion.api.enums.CalculationBizType;
import fun.pullock.promotion.core.calculation.CalculateContext;

public interface Calculator {

    CalculationBizType bizType();

    void execute(CalculateContext context);
}
