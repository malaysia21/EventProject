package events.project.security;

import events.project.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * Klasa konfiguracyjna Spring Security
 *
 * @version 1.1
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


//
//    @Autowired
//    private DaoAuthenticationProvider authenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/registerUser").permitAll()
                .antMatchers(HttpMethod.POST, "/logUser").permitAll()
                .antMatchers("/logUserTest").permitAll()
                .antMatchers("/allConfirmedEvents").permitAll()
                .anyRequest()
                .authenticated();
    }
//    @Bean
//    public HttpSessionEventPublisher httpSessionEventPublisher() {
//        return new HttpSessionEventPublisher();
//    }
//
//    @Override
//    protected void configure(
//            AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.authenticationProvider(authenticationProvider);
//    }

//
//    private AuthenticationProvider authenticationProvider;
//
//    @Autowired
//    @Qualifier("daoAuthenticationProvider")
//    public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
//        this.authenticationProvider = authenticationProvider;
//    }
//
//
//    @Autowired
//    public void configureAuthManager(AuthenticationManagerBuilder authenticationManagerBuilder){
//        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
//    }





//    @Bean
//    public HttpSessionStrategy httpSessionStrategy() {
//        return new CookieHttpSessionStrategy();
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider);
//    }




}