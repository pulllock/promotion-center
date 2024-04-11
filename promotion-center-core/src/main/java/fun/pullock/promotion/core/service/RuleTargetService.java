package fun.pullock.promotion.core.service;

import fun.pullock.promotion.core.dao.mapper.RuleTargetMapper;
import fun.pullock.promotion.core.dao.model.RuleTargetDO;
import fun.pullock.promotion.core.enums.RuleTargetType;
import fun.pullock.promotion.core.model.dto.RuleTargetDTO;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RuleTargetService {

    @Resource
    private RuleTargetMapper ruleTargetMapper;

    public List<RuleTargetDTO> queryTargets(List<Long> skuIds, List<Long> sellerIds, List<Long> categoryIds, Integer ruleType) {
        List<RuleTargetDTO> ruleTargets = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(skuIds)) {
            ruleTargets.addAll(
                    ruleTargetMapper.selectTargets(skuIds, RuleTargetType.PRODUCT.getType(), ruleType)
                            .stream()
                            .map(this::toRuleTargetDTO)
                            .collect(Collectors.toList())
            );
        }

        if (CollectionUtils.isNotEmpty(sellerIds)) {
            ruleTargets.addAll(
                    ruleTargetMapper.selectTargets(sellerIds, RuleTargetType.SELLER.getType(), ruleType)
                            .stream()
                            .map(this::toRuleTargetDTO)
                            .collect(Collectors.toList())
            );
        }

        if (CollectionUtils.isNotEmpty(categoryIds)) {
            ruleTargets.addAll(
                    ruleTargetMapper.selectTargets(categoryIds, RuleTargetType.CATEGORY.getType(), ruleType)
                            .stream()
                            .map(this::toRuleTargetDTO)
                            .collect(Collectors.toList())
            );
        }
        return ruleTargets;
    }

    private RuleTargetDTO toRuleTargetDTO(RuleTargetDO source) {
        if (source == null) {
            return null;
        }

        RuleTargetDTO target = new RuleTargetDTO();
        target.setId(source.getId());
        target.setTargetId(source.getTargetId());
        target.setTargetType(source.getTargetType());
        target.setRuleId(source.getRuleId());
        target.setRuleType(source.getRuleType());
        target.setCreateTime(source.getCreateTime());
        target.setUpdateTime(source.getUpdateTime());
        return target;
    }
}
