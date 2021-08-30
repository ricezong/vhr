package com.gz.vhr.config;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MybatisPlusGeneratorConfig
 * @Description mybatis自动生成源码工具类
 * @Author 孔明灯
 * @Data 2021/7/31 17:22
 * @Version 1.0
 */
@Configuration
public class MybatisPlusGeneratorConfig {

    public static void main(String[] args) {

        AutoGenerator generator = new AutoGenerator();
        //数据源配置，通过该配置，指定需要生成代码的具体数据库
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/vhr?useUnicode=true&useSSL=false&characterEncoding=utf-8&serverTimezone=UTC");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("0825");
        generator.setDataSource(dataSourceConfig);

        //包名配置，通过该配置，指定生成代码的包路径
        PackageConfig packageConfig = new PackageConfig();
        //父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
        packageConfig.setParent("com.gz.vhr");
        //Entity包名
        packageConfig.setEntity("bean");
        //Entity包名
        packageConfig.setMapper("mapper");
        //Mapper XML包名
        packageConfig.setXml("mapper");
        generator.setPackageInfo(packageConfig);

        //数据库表配置，通过该配置，可指定需要生成哪些表或者排除哪些表
        StrategyConfig strategyConfig = new StrategyConfig();
        //自定义继承的Entity类全称
        //strategyConfig.setSuperEntityClass(BaseEntity.class);
        //表名驼峰命名
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        //字段驼峰命名
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        //是否为链式模型
        strategyConfig.setChainModel(true);
        //是否使用lombok注解
        strategyConfig.setEntityLombokModel(true);
        //生成 @RestController 控制器
        strategyConfig.setRestControllerStyle(true);
        //是否生成实体时，生成字段注解
        strategyConfig.setEntityTableFieldAnnotationEnable(true);
        //不生成serial version uuid
        strategyConfig.setEntitySerialVersionUID(false);
        //自动匹配表名生成entity类
        strategyConfig.getLikeTable();
        //要生成的表名
        //strategyConfig.setInclude("hr");
        generator.setStrategy(strategyConfig);

        //全局策略配置
        GlobalConfig globalConfig = new GlobalConfig();
        //生成文件的输出目录
        globalConfig.setOutputDir(System.getProperty("user.dir") + "/src/main/java");
        //开发人员
        globalConfig.setAuthor("zong");

        globalConfig.setServiceName("%sService");
        //是否打开输出目录
        globalConfig.setOpen(false);

        generator.setGlobalConfig(globalConfig);
        //执行
        generator.execute();
    }
}
