package fun.pullock.promotion.api.model.result;

import java.time.LocalDateTime;
import java.util.List;

public class CalculateResult {

    /**
     * 实际支付总金额
     */
    private Long totalAmount;

    /**
     * 可用的优惠券列表
     */
    private List<Coupon> coupons;

    /**
     * 可用的红包列表
     */
    private List<Coupon> redPackets;

    /**
     * 满减列表
     */
    private List<ReachExemption> reachExemptions;



    public static class ReachExemption {
        private Long id;

        private String name;

        private String description;

        private Long discount;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Long getDiscount() {
            return discount;
        }

        public void setDiscount(Long discount) {
            this.discount = discount;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "ReachExemption{" +
                    "description='" + description + '\'' +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    ", discount=" + discount +
                    '}';
        }
    }

    public static class Coupon {

        private Long id;

        private Long couponRuleId;

        private String name;

        private Long userId;

        private Integer status;

        private LocalDateTime claimTime;

        private LocalDateTime useTime;

        private LocalDateTime validityStartTime;

        private LocalDateTime validityEndTime;

        private Long threshold;

        private Long discount;

        public LocalDateTime getClaimTime() {
            return claimTime;
        }

        public void setClaimTime(LocalDateTime claimTime) {
            this.claimTime = claimTime;
        }

        public Long getCouponRuleId() {
            return couponRuleId;
        }

        public void setCouponRuleId(Long couponRuleId) {
            this.couponRuleId = couponRuleId;
        }

        public Long getDiscount() {
            return discount;
        }

        public void setDiscount(Long discount) {
            this.discount = discount;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Long getThreshold() {
            return threshold;
        }

        public void setThreshold(Long threshold) {
            this.threshold = threshold;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public LocalDateTime getUseTime() {
            return useTime;
        }

        public void setUseTime(LocalDateTime useTime) {
            this.useTime = useTime;
        }

        public LocalDateTime getValidityEndTime() {
            return validityEndTime;
        }

        public void setValidityEndTime(LocalDateTime validityEndTime) {
            this.validityEndTime = validityEndTime;
        }

        public LocalDateTime getValidityStartTime() {
            return validityStartTime;
        }

        public void setValidityStartTime(LocalDateTime validityStartTime) {
            this.validityStartTime = validityStartTime;
        }

        @Override
        public String toString() {
            return "Coupon{" +
                    "claimTime=" + claimTime +
                    ", id=" + id +
                    ", couponRuleId=" + couponRuleId +
                    ", name='" + name + '\'' +
                    ", userId=" + userId +
                    ", status=" + status +
                    ", useTime=" + useTime +
                    ", validityStartTime=" + validityStartTime +
                    ", validityEndTime=" + validityEndTime +
                    ", threshold=" + threshold +
                    ", discount=" + discount +
                    '}';
        }
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    public List<ReachExemption> getReachExemptions() {
        return reachExemptions;
    }

    public void setReachExemptions(List<ReachExemption> reachExemptions) {
        this.reachExemptions = reachExemptions;
    }

    public List<Coupon> getRedPackets() {
        return redPackets;
    }

    public void setRedPackets(List<Coupon> redPackets) {
        this.redPackets = redPackets;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "CalculateResult{" +
                "coupons=" + coupons +
                ", totalAmount=" + totalAmount +
                ", redPackets=" + redPackets +
                ", reachExemptions=" + reachExemptions +
                '}';
    }
}
