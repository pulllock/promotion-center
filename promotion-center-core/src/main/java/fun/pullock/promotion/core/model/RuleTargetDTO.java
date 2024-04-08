package fun.pullock.promotion.core.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RuleTargetDTO {
    
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer ruleType;

    private Long ruleId;

    private Long targetId;

    private Integer targetType;
}