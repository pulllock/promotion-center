package fun.pullock.promotion.core.model.app.param;

import lombok.Data;

@Data
public class AvailablePromotionParam {

    private Long spuId;

    private Integer type;

    private String channel;
}
