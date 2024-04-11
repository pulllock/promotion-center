package fun.pullock.promotion.core.calculation;

import fun.pullock.promotion.api.model.param.CalculateParam;
import fun.pullock.promotion.api.model.result.CalculateResult;
import fun.pullock.promotion.core.model.dto.calculate.RuleTargetsCompositeDTO;
import lombok.Data;

import java.util.List;

@Data
public class CalculateContext {

    private CalculateParam param;

    private List<RuleTargetsCompositeDTO> ruleTargetComposites;

    private CalculateResult result;
}
