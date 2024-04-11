package fun.pullock.promotion.core.model.dto.calculate;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 计算过程中使用到的订单中的信息
 */
@Data
public class OrderInfoDTO {

    public OrderInfoDTO() {
        skuIds = new ArrayList<>();
        sellerIds = new ArrayList<>();
        categoryIds = new ArrayList<>();
    }

    /**
     * SKU ID列表
     */
    private List<Long> skuIds;

    /**
     * 商户ID列表
     */
    private List<Long> sellerIds;

    /**
     * 类目ID列表
     */
    private List<Long> categoryIds;

    /**
     * 渠道
     */
    private String channel;
}
