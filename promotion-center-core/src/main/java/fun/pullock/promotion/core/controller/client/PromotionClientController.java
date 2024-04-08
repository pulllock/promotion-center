package fun.pullock.promotion.core.controller.client;

import fun.pullock.promotion.api.client.PromotionClient;
import fun.pullock.promotion.api.model.param.CalculateParam;
import fun.pullock.promotion.api.model.result.CalculateResult;
import fun.pullock.promotion.core.service.PromotionCalculationService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PromotionClientController implements PromotionClient {

    @Resource
    private PromotionCalculationService promotionCalculationService;

    @Override
    public CalculateResult calculate(CalculateParam param) {
        // TODO 参数校验
        return promotionCalculationService.calculate(param);
    }
}
