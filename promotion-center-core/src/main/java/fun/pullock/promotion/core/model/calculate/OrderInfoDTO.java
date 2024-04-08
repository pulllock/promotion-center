package fun.pullock.promotion.core.model.calculate;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * 计算过程中使用到的订单中的信息
 */
@Data
public class OrderInfoDTO {

    public OrderInfoDTO() {
        skuIds = new HashSet<>();
        sellerIds = new HashSet<>();
        categoryIds = new HashSet<>();
    }

    /**
     * SKU ID列表
     */
    private Set<Long> skuIds;

    /**
     * 商户ID列表
     */
    private Set<Long> sellerIds;

    /**
     * 类目ID列表
     */
    private Set<Long> categoryIds;

    /**
     * 渠道
     */
    private String channel;
}
