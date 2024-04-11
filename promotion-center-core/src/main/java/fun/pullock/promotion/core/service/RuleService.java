package fun.pullock.promotion.core.service;

import fun.pullock.promotion.core.dao.mapper.CouponRuleMapper;
import fun.pullock.promotion.core.dao.mapper.RuleMapper;
import fun.pullock.promotion.core.dao.mapper.RuleTargetMapper;
import fun.pullock.promotion.core.dao.model.CouponRuleDO;
import fun.pullock.promotion.core.dao.model.RuleDO;
import fun.pullock.promotion.core.dao.model.RuleTargetDO;
import fun.pullock.promotion.core.enums.RuleType;
import fun.pullock.promotion.core.model.dto.CouponRuleDTO;
import fun.pullock.promotion.core.model.dto.RuleDTO;
import fun.pullock.promotion.core.model.dto.RuleTargetDTO;
import fun.pullock.promotion.core.model.dto.calculate.OrderInfoDTO;
import fun.pullock.promotion.core.model.dto.calculate.RuleTargetsCompositeDTO;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static fun.pullock.promotion.core.enums.RuleTargetType.*;

@Service
public class RuleService {

    @Resource
    private RuleTargetMapper ruleTargetMapper;

    @Resource
    private RuleMapper ruleMapper;

    @Resource
    private CouponRuleMapper couponRuleMapper;


    public List<RuleTargetsCompositeDTO> queryRuleTargets(OrderInfoDTO orderInfo) {
        List<RuleTargetDO> targets = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(orderInfo.getSkuIds())) {
            targets.addAll(ruleTargetMapper.selectTargets(orderInfo.getSkuIds(), PRODUCT.getType()));
        }

        if (CollectionUtils.isNotEmpty(orderInfo.getSellerIds())) {
            targets.addAll(ruleTargetMapper.selectTargets(orderInfo.getSellerIds(), SELLER.getType()));
        }

        if (CollectionUtils.isNotEmpty(orderInfo.getCategoryIds())) {
            targets.addAll(ruleTargetMapper.selectTargets(orderInfo.getCategoryIds(), CATEGORY.getType()));
        }

        Map<Integer, List<RuleTargetDO>> groupByRuleType = targets
                .stream()
                .collect(Collectors.groupingBy(RuleTargetDO::getRuleType));

        List<RuleTargetsCompositeDTO> composites = new ArrayList<>();
        for (Map.Entry<Integer, List<RuleTargetDO>> ruleTypeEntry : groupByRuleType.entrySet()) {
            RuleTargetsCompositeDTO composite = new RuleTargetsCompositeDTO();
            composite.setRuleType(ruleTypeEntry.getKey());

            Map<Long, List<RuleTargetDO>> groupByRuleId = ruleTypeEntry.getValue()
                    .stream()
                    .collect(Collectors.groupingBy(RuleTargetDO::getRuleId));

            for (Map.Entry<Long, List<RuleTargetDO>> ruleIdEntry : groupByRuleId.entrySet()) {
                // 优惠券类型的需要查询优惠券规则表
                if (ruleTypeEntry.getKey() == RuleType.COUPON.getType()) {
                    CouponRuleDTO couponRule = toCouponRuleDTO(couponRuleMapper.selectByPrimaryKey(ruleIdEntry.getKey()));
                    composite.setCouponRule(couponRule);
                    composite.setRule(toRuleDTO(ruleMapper.selectByPrimaryKey(couponRule.getRuleId())));
                }
                // 其他类型的查询规则表
                else {
                    composite.setRule(toRuleDTO(ruleMapper.selectByPrimaryKey(ruleIdEntry.getKey())));
                }

                composite.setRuleTargets(
                        ruleIdEntry.getValue().stream().map(this::toRuleTargetDTO).collect(Collectors.toList())
                );
            }

            composites.add(composite);
        }
        return composites;
    }

    public CouponRuleDTO queryById(Long id) {
        return toCouponRuleDTO(couponRuleMapper.selectByPrimaryKey(id));
    }

    private RuleTargetDTO toRuleTargetDTO(RuleTargetDO source) {
        if (source == null) {
            return null;
        }

        RuleTargetDTO target = new RuleTargetDTO();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    private RuleDTO toRuleDTO(RuleDO source) {
        if (source == null) {
            return null;
        }

        RuleDTO target = new RuleDTO();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    private CouponRuleDTO toCouponRuleDTO(CouponRuleDO source) {
        if (source == null) {
            return null;
        }

        CouponRuleDTO target = new CouponRuleDTO();
        BeanUtils.copyProperties(source, target);
        return target;
    }
}
