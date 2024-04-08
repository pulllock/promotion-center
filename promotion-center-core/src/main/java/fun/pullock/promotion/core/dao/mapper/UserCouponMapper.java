package fun.pullock.promotion.core.dao.mapper;

import fun.pullock.promotion.core.dao.model.UserCouponDO;

public interface UserCouponMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserCouponDO row);

    int insertSelective(UserCouponDO row);

    UserCouponDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserCouponDO row);

    int updateByPrimaryKey(UserCouponDO row);
}