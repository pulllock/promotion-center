package fun.pullock.promotion.core.dao.mapper;

import fun.pullock.promotion.core.dao.model.CouponRuleDO;
import org.apache.ibatis.annotations.Param;

public interface CouponRuleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CouponRuleDO row);

    int insertSelective(CouponRuleDO row);

    CouponRuleDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CouponRuleDO row);

    int updateByPrimaryKey(CouponRuleDO row);

    int updateClaimed(
            @Param("id") Long id,
            @Param("claimed") Long claimed
    );
}