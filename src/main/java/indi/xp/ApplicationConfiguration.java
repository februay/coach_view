package indi.xp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import indi.xp.common.utils.StringUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class ApplicationConfiguration {

    @Value("${cros.allow-domain}")
    private String crosAllowDomain;

    @Value("${swagger.base-package}")
    private String swaggerBasePackage;

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();

        // TODO: 后续需测试添加配置域名
        if (StringUtils.isBlank(crosAllowDomain)) {
            config.addAllowedOrigin("*");// 允许向该服务器提交请求的URI，*表示全部允许。
        } else {
            String[] crosArray = crosAllowDomain.split(",");
            for (String cros : crosArray) {
                config.addAllowedOrigin(cros);
            }
        }
        config.setAllowCredentials(true); // 允许cookies跨域
        config.addAllowedHeader("*");// 允许访问的头信息,*表示全部
        config.setMaxAge(18000L);// 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        config.addAllowedMethod("*");// 允许提交请求的方法，*表示全部允许，也可以单独设置GET、PUT等
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public Docket createSwaggerApi() {
        ApiInfo apiInfo = new ApiInfoBuilder().title("Api文档").description("").termsOfServiceUrl("").version("1.0.0")
            .build();

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo).select()
            .apis(RequestHandlerSelectors.basePackage(swaggerBasePackage)).paths(PathSelectors.any()).build();
    }

}
