package fun.pullock.promotion.core.dao.mapper;

import fun.pullock.promotion.core.dao.model.RuleTargetDO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface RuleTargetMapper {

    int deleteByPrimaryKey(Long id);

    int insert(RuleTargetDO row);

    int insertSelective(RuleTargetDO row);

    RuleTargetDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RuleTargetDO row);

    int updateByPrimaryKey(RuleTargetDO row);

    List<RuleTargetDO> selectTargets(
            @Param("targetIds") Set<Long> targetIds,
            @Param("targetType") int targetType
    );
}