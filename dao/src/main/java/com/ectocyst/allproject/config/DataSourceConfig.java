package com.ectocyst.allproject.config;//package com.all.diy.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.ectocyst.allproject.constans.DataSourceConstants;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.transaction.TransactionDefinition.ISOLATION_DEFAULT;

/**
 * @Author: Damon . yanli
 * @Description:
 * @Date: Created in 15:56 2020-04-27
 * @Modified By:
 */
@Component
@ConfigurationProperties(prefix = "diy.datasource")
@MapperScan(basePackages = {DataSourceConstants.MAPPER_PACKAGE}, sqlSessionFactoryRef = "mybatisSqlSessionFactory")
@PropertySource("classpath:application.properties")
public class DataSourceConfig {

    @Value("${allproject.datasource.jdbc.url}")
    private String url;

    @Value("${allproject.datasource.username}")
    private String username;

    @Value("${allproject.datasource.maxActive}")
    private Integer maxActive;

    @Value("${allproject.datasource.password}")
    private String password;


    /**
     * 数据源
     * @return
     */
    @Bean(name = "mybatisDataSource")
    public DataSource mybatisDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxActive(maxActive);
        return dataSource;
    }

    /**
     *
     * @param mybatisDataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "mybatisSqlSessionFactory")
    public SqlSessionFactory mybatisSqlSessionFactory(@Qualifier("mybatisDataSource") DataSource mybatisDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(mybatisDataSource);

        Resource[] localMapperResources = new PathMatchingResourcePatternResolver()
                .getResources(DataSourceConstants.MAPPER_LOCATION);


        List<Resource> resourceList = new ArrayList<>(localMapperResources.length);
        resourceList.addAll(Arrays.asList(localMapperResources));

        Resource[] resources = new Resource[resourceList.size()];
        sessionFactory.setMapperLocations(resourceList.toArray(resources));
        return sessionFactory.getObject();
    }


    /**
     * 事物管理
     * @param mybatisDataSource
     * @return
     */
    @Bean(name = "mybatisTransactionManager")
    public DataSourceTransactionManager mybatisTransactionManager(@Qualifier("mybatisDataSource") DataSource mybatisDataSource) {
        return new DataSourceTransactionManager(mybatisDataSource);
    }

    /**
     * 事物
     * @param mybatisTransactionManager
     * @return
     */
    @Bean(name = "transactionTemplate")
    public TransactionTemplate transactionTemplate(@Qualifier("mybatisTransactionManager") DataSourceTransactionManager mybatisTransactionManager) {
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        transactionTemplate.setIsolationLevel(ISOLATION_DEFAULT);
        transactionTemplate.setTimeout(5000);
        transactionTemplate.setTransactionManager(mybatisTransactionManager);
        return transactionTemplate;
    }


}
