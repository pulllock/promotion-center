package fun.pullock.promotion.core.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserCouponDTO {
    
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long userId;

    private Long couponId;

    private Integer status;

    private LocalDateTime claimTime;

    private LocalDateTime useTime;

    private LocalDateTime validityStartTime;

    private LocalDateTime validityEndTime;

    private Long threshold;

    private Long discount;

    private String channel;

    private String source;

    private String uniqueSourceId;

    private String tradeNo;
}