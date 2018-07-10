package indi.xp;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationWebSecurityConfig extends WebSecurityConfigurerAdapter {
    
//    @SuppressWarnings("deprecation")  
//    @Bean  
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {  
//       return new InMemoryUserDetailsManager(  
//            User.withDefaultPasswordEncoder().username("user").password("password")  
//                   .authorities("ROLE_USER").build(),  
//             User.withDefaultPasswordEncoder().username("admin").password("admin")  
//                   .authorities("ROLE_ACTUATOR", "ROLE_USER", "SUPERUSER").build());  
//    }  

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 授权所有url
        http.authorizeRequests().anyRequest().permitAll();
        
//        // 授权management endpoint url
        http.authorizeRequests().requestMatchers(EndpointRequest.toAnyEndpoint()).hasAnyRole("SUPERUSER", "ACTUATOR")
            .antMatchers("/manage/**").permitAll().and().httpBasic();

//      http.requestMatcher(EndpointRequest.toAnyEndpoint()).authorizeRequests().anyRequest().hasRole("SUPERUSER")
//      .and().httpBasic();
        
        // 设置url校验规则
//        http
//        .authorizeRequests()
//            .antMatchers("/", "/home").permitAll()
//            .anyRequest().authenticated()
//            .and()
//        .formLogin()
//            .loginPage("/login")
//            .permitAll()
//            .and()
//        .logout()
//            .permitAll();
    }

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder().username("user").password("password").roles("USER", "SUPERUSER", "ACTUATOR").build();
//        return new InMemoryUserDetailsManager(user);
//    }
}