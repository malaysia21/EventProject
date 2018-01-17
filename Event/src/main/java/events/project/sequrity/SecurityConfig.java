package events.project.sequrity;

import events.project.users.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

            http//.formLogin().and()
                .cors().and().httpBasic().and()
                .logout().and()
                .authorizeRequests()
                    .antMatchers("/addEvent").authenticated().anyRequest().permitAll()
                    .and()
                .csrf().disable();
    }



}