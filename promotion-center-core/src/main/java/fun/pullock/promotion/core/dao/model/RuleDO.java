package fun.pullock.promotion.core.dao.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RuleDO {
    
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String name;

    private String description;

    private Integer type;

    private Integer scope;

    private Integer mode;

    private Integer status;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Boolean exclusive;

    private Integer order;

    private String channel;

    private String rules;
}