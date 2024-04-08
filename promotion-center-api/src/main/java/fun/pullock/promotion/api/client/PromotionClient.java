package fun.pullock.promotion.api.client;

import fun.pullock.promotion.api.model.param.CalculateParam;
import fun.pullock.promotion.api.model.result.CalculateResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public interface PromotionClient {

    /**
     * 促销计算
     * @param param 计算参数
     * @return 计算结果
     */
    @PostMapping(value = "/rpc/promotion/calculate", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    CalculateResult calculate(@RequestBody CalculateParam param);
}
