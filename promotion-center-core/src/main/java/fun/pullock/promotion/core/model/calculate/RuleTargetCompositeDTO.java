package fun.pullock.promotion.core.model.calculate;

import fun.pullock.promotion.core.model.CouponRuleDTO;
import fun.pullock.promotion.core.model.RuleDTO;
import fun.pullock.promotion.core.model.RuleTargetDTO;
import lombok.Data;

import java.util.List;

@Data
public class RuleTargetCompositeDTO {

    /**
     * 规则类型，取值：1-优惠券 2-满减 3-满件折 4-满额赠 5-积分 6-金币
     */
    private Integer ruleType;

    /**
     * 促销规则
     */
    private RuleDTO rule;

    /**
     * 优惠券规则，仅当ruleType=1的时候使用该字段
     */
    private CouponRuleDTO couponRule;

    /**
     * 促销规则绑定的对象
     */
    private List<RuleTargetDTO> ruleTargets;
}
