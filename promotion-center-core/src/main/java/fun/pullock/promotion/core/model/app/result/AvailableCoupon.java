package fun.pullock.promotion.core.model.app.result;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AvailableCoupon {

    private Long id;

    private String name;

    private String description;

    private String ruleDescription;

    private String redirectUrl;

    private Integer type;

    private Boolean dynamic;

    private Long threshold;

    private Long discount;

    private Integer validityType;

    private LocalDateTime validityStartTime;

    private LocalDateTime validityEndTime;

    private Integer validityDays;

    private Boolean claimed;

}
