package fun.pullock.promotion.core.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"fun.pullock.promotion.core.dao.mapper"})
public class DataSourceConfig {
}
