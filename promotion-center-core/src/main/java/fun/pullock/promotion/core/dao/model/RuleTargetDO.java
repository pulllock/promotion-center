package fun.pullock.promotion.core.dao.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RuleTargetDO {
    
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer ruleType;

    private Long ruleId;

    private Long targetId;

    private Integer targetType;
}