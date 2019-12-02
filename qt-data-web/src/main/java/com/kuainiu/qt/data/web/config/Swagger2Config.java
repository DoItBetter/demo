package com.kuainiu.qt.data.web.config;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by Trace on 2018-05-16.<br/>
 * Desc: swagger2配置类
 */
@SuppressWarnings({"unused"})
@Configuration @EnableSwagger2
public class Swagger2Config {
    @Value("${swagger2.enable}") private boolean enable;

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Qt系统平台接口文档")
//                .description("提供子模块1/子模块2/子模块3的文档, 更多请关注公众号: 随行享阅")
//                .termsOfServiceUrl("https://xingtian.github.io/trace.github.io/")
                .version("1.0")
                .build();
    }

    @Bean("PortfolioApis")
    public Docket portfolioApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("投资组合模块")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.regex("/portfolio.*"))
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }

    @Bean("StkPositionApis")
    public Docket stkPositionApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("股票仓位模块")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.regex("/stkPosition.*"))
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }

    @Bean("FuturesPositionApis")
    public Docket futuresPositionApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("期货仓位模块")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.regex("/futuresPosition.*"))
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }

    @Bean("TestApis")
    public Docket testApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("测试接口模块")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.regex("/test.*"))
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }
}