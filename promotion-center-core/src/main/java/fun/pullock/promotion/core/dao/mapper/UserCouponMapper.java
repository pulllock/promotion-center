package fun.pullock.promotion.core.dao.mapper;

import fun.pullock.promotion.core.dao.model.UserCouponDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserCouponMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserCouponDO row);

    int insertSelective(UserCouponDO row);

    UserCouponDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserCouponDO row);

    int updateByPrimaryKey(UserCouponDO row);

    List<UserCouponDO> selectByUserCouponId(
            @Param("userId") Long userId,
            @Param("couponId") Long couponId
    );
}