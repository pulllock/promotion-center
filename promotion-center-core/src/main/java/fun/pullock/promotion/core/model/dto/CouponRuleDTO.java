package fun.pullock.promotion.core.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CouponRuleDTO {

    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long ruleId;

    private String name;

    private String description;

    private String ruleDescription;

    private Integer status;

    private String redirectUrl;

    private Integer type;

    private Boolean dynamic;

    private Integer validityType;

    private LocalDateTime validityStartTime;

    private LocalDateTime validityEndTime;

    private Integer validityDays;

    private LocalDateTime claimStartTime;

    private LocalDateTime claimEndTime;

    private Long total;

    private Long claimed;

    private Long userTotal;

    private Long userDailyTotal;

    private Long threshold;

    private Long discount;

    private Boolean exclusive;

    private String channel;
}