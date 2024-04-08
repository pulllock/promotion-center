package fun.pullock.promotion.core.dao.mapper;

import fun.pullock.promotion.core.dao.model.RuleDO;

public interface RuleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(RuleDO row);

    int insertSelective(RuleDO row);

    RuleDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RuleDO row);

    int updateByPrimaryKey(RuleDO row);
}