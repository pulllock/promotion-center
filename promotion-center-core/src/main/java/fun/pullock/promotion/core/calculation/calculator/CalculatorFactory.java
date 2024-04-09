package fun.pullock.promotion.core.calculation.calculator;

import fun.pullock.promotion.api.enums.CalculationBizType;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CalculatorFactory {

    @Resource
    private List<Calculator> calculators;

    public Calculator getCalculator(CalculationBizType bizType) {
        return calculators.stream().filter(c -> c.bizType() == bizType).findFirst().orElse(null);
    }
}
