package fun.pullock.promotion.api.model.param;

import java.io.Serializable;
import java.util.List;

public class CalculateParam implements Serializable {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 业务类型，取值：0-通用 1-优惠券凑单 2-包邮凑单 3-满折凑单 4-购物车
     */
    private Integer bizType;

    /**
     * 渠道
     */
    private String channel;

    /**
     * 订单列表
     */
    private List<OrderItem> orderItems;

    /**
     * 用户使用的平台优惠券ID
     */
    private Long couponId;

    /**
     * 用户使用的红包ID
     */
    private Long redPacketId;


    public static class OrderItem {

        /**
         * 商户ID
         */
        private Long sellerId;

        /**
         * 商品列表
         */
        private List<Product> products;

        /**
         * 用户使用的商户优惠券ID
         */
        private Long couponId;

        public Long getCouponId() {
            return couponId;
        }

        public void setCouponId(Long couponId) {
            this.couponId = couponId;
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

        public Long getSellerId() {
            return sellerId;
        }

        public void setSellerId(Long sellerId) {
            this.sellerId = sellerId;
        }

        @Override
        public String toString() {
            return "OrderItem{" +
                    "couponId=" + couponId +
                    ", sellerId=" + sellerId +
                    ", products=" + products +
                    '}';
        }
    }

    public static class Product {

        /**
         * 商户ID
         */
        private Long sellerId;

        /**
         * SPU ID
         */
        private Long spuId;

        /**
         * SKU ID
         */
        private Long skuId;

        /**
         * 商品关联的邮费ID
         */
        private Long postageId;

        /**
         * 数量
         */
        private Long quantity;

        /**
         * 单价
         */
        private Long price;

        /**
         * 类目ID
         */
        private Long categoryId;

        public Long getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Long categoryId) {
            this.categoryId = categoryId;
        }

        public Long getPostageId() {
            return postageId;
        }

        public void setPostageId(Long postageId) {
            this.postageId = postageId;
        }

        public Long getPrice() {
            return price;
        }

        public void setPrice(Long price) {
            this.price = price;
        }

        public Long getQuantity() {
            return quantity;
        }

        public void setQuantity(Long quantity) {
            this.quantity = quantity;
        }

        public Long getSellerId() {
            return sellerId;
        }

        public void setSellerId(Long sellerId) {
            this.sellerId = sellerId;
        }

        public Long getSkuId() {
            return skuId;
        }

        public void setSkuId(Long skuId) {
            this.skuId = skuId;
        }

        public Long getSpuId() {
            return spuId;
        }

        public void setSpuId(Long spuId) {
            this.spuId = spuId;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "categoryId=" + categoryId +
                    ", sellerId=" + sellerId +
                    ", spuId=" + spuId +
                    ", skuId=" + skuId +
                    ", postageId=" + postageId +
                    ", quantity=" + quantity +
                    ", price=" + price +
                    '}';
        }
    }

    public Integer getBizType() {
        return bizType;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Long getRedPacketId() {
        return redPacketId;
    }

    public void setRedPacketId(Long redPacketId) {
        this.redPacketId = redPacketId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CalculateParam{" +
                "bizType=" + bizType +
                ", userId=" + userId +
                ", channel='" + channel + '\'' +
                ", orderItems=" + orderItems +
                ", couponId=" + couponId +
                ", redPacketId=" + redPacketId +
                '}';
    }
}
