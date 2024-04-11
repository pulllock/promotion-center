package fun.pullock.promotion.core.controller.app;

import fun.pullock.general.model.ServiceException;
import fun.pullock.promotion.core.enums.CouponType;
import fun.pullock.promotion.core.enums.RuleType;
import fun.pullock.promotion.core.model.app.param.AvailablePromotionParam;
import fun.pullock.promotion.core.model.app.result.AvailablePromotion;
import fun.pullock.promotion.core.service.PromotionService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static fun.pullock.promotion.api.enums.ErrorCode.PARAM_ERROR;

@RestController
@RequestMapping("/app/promotion")
public class PromotionController {

    @Resource
    private PromotionService promotionService;

    @PostMapping("/list/available")
    public List<AvailablePromotion> availablePromotions(@RequestBody AvailablePromotionParam param) {
        Long userId = 1L;
        if (param == null) {
            throw new ServiceException(PARAM_ERROR);
        }

        if (param.getSpuId() == null) {
            throw new ServiceException(PARAM_ERROR, "SPU ID不能为空");
        }

        if (RuleType.of(param.getType()) == null) {
            throw new ServiceException(PARAM_ERROR, "未知的促销类型");
        }

        return promotionService.availablePromotions(userId, param);
    }
}
