package fun.pullock.promotion.core.service;

import fun.pullock.promotion.core.dao.mapper.CouponRuleMapper;
import fun.pullock.promotion.core.dao.model.CouponRuleDO;
import fun.pullock.promotion.core.model.dto.CouponRuleDTO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CouponRuleService {

    @Resource
    private CouponRuleMapper couponRuleMapper;

    public CouponRuleDTO queryById(Long id) {
        return toCouponRuleDTO(couponRuleMapper.selectByPrimaryKey(id));
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
