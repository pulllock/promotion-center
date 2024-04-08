package fun.pullock.promotion.core.dao.mapper;

import fun.pullock.promotion.core.dao.model.RuleTargetDO;

public interface RuleTargetMapper {

    int deleteByPrimaryKey(Long id);

    int insert(RuleTargetDO row);

    int insertSelective(RuleTargetDO row);

    RuleTargetDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RuleTargetDO row);

    int updateByPrimaryKey(RuleTargetDO row);
}