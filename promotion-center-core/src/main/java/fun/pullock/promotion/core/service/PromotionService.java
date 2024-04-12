package fun.pullock.promotion.core.service;

import fun.pullock.promotion.core.model.app.param.AvailablePromotionParam;
import fun.pullock.promotion.core.model.app.result.AvailablePromotion;
import fun.pullock.promotion.core.model.dto.RuleDTO;
import fun.pullock.promotion.core.model.dto.RuleTargetDTO;
import fun.pullock.promotion.core.proxy.product.ProductClientService;
import fun.pullock.promotion.core.proxy.product.model.MockProductDTO;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionService {

    @Resource
    private ProductClientService productClientService;

    @Resource
    private RuleTargetService ruleTargetService;

    @Resource
    private RuleService ruleService;

    public List<AvailablePromotion> availablePromotions(Long userId, AvailablePromotionParam param) {
        List<RuleTargetDTO> promotionTargets = queryPromotionTargets(param);
        if (CollectionUtils.isEmpty(promotionTargets)) {
            return null;
        }

        return promotionTargets
                .stream()
                .map(p -> ruleService.queryById(p.getRuleId()))
                .map(this::toAvailablePromotion)
                .collect(Collectors.toList());
    }

    private List<RuleTargetDTO> queryPromotionTargets(AvailablePromotionParam param) {
        // 根据spu id查询对应的sku id、商户ID、类目
        MockProductDTO product = productClientService.queryBySpu(param.getSpuId());
        List<Long> skuIds = product.getSkus()
                .stream()
                .map(MockProductDTO.MockProductSkuDTO::getId)
                .collect(Collectors.toList());
        List<Long> categoryIds = product.getCategories()
                .stream()
                .map(MockProductDTO.MockProductCategoryDTO::getId)
                .collect(Collectors.toList());

        // 查询促销规则对象
        return ruleTargetService.queryTargets(
                skuIds, Collections.singletonList(product.getSellerId()), categoryIds, param.getType()
        );
    }

    private AvailablePromotion toAvailablePromotion(RuleDTO source) {
        if (source == null) {
            return null;
        }

        AvailablePromotion target = new AvailablePromotion();
        BeanUtils.copyProperties(source, target);
        return target;
    }
}
