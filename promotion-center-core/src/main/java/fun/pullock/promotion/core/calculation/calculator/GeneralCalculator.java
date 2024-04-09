package fun.pullock.promotion.core.calculation.calculator;

import fun.pullock.promotion.api.enums.CalculationBizType;
import fun.pullock.promotion.core.calculation.CalculateContext;
import fun.pullock.promotion.core.calculation.CalculationHandler;
import fun.pullock.promotion.core.calculation.handlers.CouponCalculationHandler;
import fun.pullock.promotion.core.calculation.handlers.ReachExemptionCalculationHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GeneralCalculator implements Calculator {

    private List<CalculationHandler> handlers;

    private final ReachExemptionCalculationHandler reachExemptionCalculationHandler;

    private final CouponCalculationHandler couponCalculationHandler;



    public GeneralCalculator(ReachExemptionCalculationHandler reachExemptionCalculationHandler,
                             CouponCalculationHandler couponCalculationHandler) {
        this.reachExemptionCalculationHandler = reachExemptionCalculationHandler;
        this.couponCalculationHandler = couponCalculationHandler;
        handlers = new ArrayList<>();
        handlers.add(this.reachExemptionCalculationHandler);
        handlers.add(this.couponCalculationHandler);
    }

    @Override
    public CalculationBizType bizType() {
        return null;
    }

    @Override
    public void execute(CalculateContext context) {
        for (CalculationHandler handler : handlers) {
            handler.calculate(context);
        }
    }
}
