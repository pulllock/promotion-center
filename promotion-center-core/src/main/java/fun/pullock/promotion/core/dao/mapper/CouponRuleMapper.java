package fun.pullock.promotion.core.dao.mapper;

import fun.pullock.promotion.core.dao.model.CouponRuleDO;

public interface CouponRuleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CouponRuleDO row);

    int insertSelective(CouponRuleDO row);

    CouponRuleDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CouponRuleDO row);

    int updateByPrimaryKey(CouponRuleDO row);
}