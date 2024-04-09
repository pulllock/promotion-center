package fun.pullock.promotion.core.calculation;

import fun.pullock.promotion.api.model.param.CalculateParam;
import fun.pullock.promotion.api.model.result.CalculateResult;
import fun.pullock.promotion.core.model.calculate.RuleTargetCompositeDTO;
import lombok.Data;

import java.util.List;

@Data
public class CalculateContext {

    private CalculateParam param;

    private List<RuleTargetCompositeDTO> ruleTargetComposites;

    private CalculateResult result;
}
