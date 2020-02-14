package com.capstone.openhelp.config;


import com.capstone.openhelp.models.UserWithRoles;
import com.capstone.openhelp.services.UserDetailsLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = UserWithRoles.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private UserDetailsLoader usersLoader;

    public SecurityConfiguration(UserDetailsLoader usersLoader) {
        this.usersLoader = usersLoader;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usersLoader) // How to find users by their username --- switched email
                .passwordEncoder(passwordEncoder()) // How to encode and verify passwords
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /* Login configuration */
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email").passwordParameter("password")
                .defaultSuccessUrl("/users/profile") // user's home page, it can be any URL
                .permitAll() // Anyone can go to the login page
                /* Logout configuration */
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout") // append a query string value
                /* Pages that can be viewed without having to log in */
                .and()
                .authorizeRequests()
                .antMatchers("/", "/home","/events","/users","/about", "/sign-up") // anyone can see the home, events and the about pages
                .permitAll()
                /* Pages that require authentication */
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/events/create", // only authenticated users can create events
                        "/events/edit/{id}", // only authenticated users can edit events
                        "/users/profile",
                        "/events/singleevent/{id}",
                        "/user/{id}",
                        "/events/delete/{id}",
                        "/events/singleevent/{id}/enroll",
                        "/users/delete/{id}"

                )
                .authenticated()
        ;
    }
}
