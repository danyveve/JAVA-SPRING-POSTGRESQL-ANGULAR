package ro.ubb.catalog.web.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import ro.ubb.catalog.core.model.UserRole;
import ro.ubb.catalog.web.security.LogoutSuccess;
import ro.ubb.catalog.web.security.MySavedRequestAwareAuthenticationSuccessHandler;
import ro.ubb.catalog.web.security.RentalUserDetailsService;
import ro.ubb.catalog.web.security.RestAuthenticationEntryPoint;


@Configuration
@EnableWebSecurity
@ComponentScan("ro.ubb.catalog.web.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private MySavedRequestAwareAuthenticationSuccessHandler
            mySavedRequestAwareAuthenticationSuccessHandler;

    @Autowired
    LogoutSuccess logoutSuccess;

    @Autowired
    private RentalUserDetailsService rentalUserDetailsService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        PasswordEncoder passwordEncoder = passwordEncoder();
        authProvider.setPasswordEncoder(passwordEncoder);
        String passwordToEncode = "123456";
        System.out.println("Encoded password: " + passwordToEncode + " = " + passwordEncoder.encode(passwordToEncode));
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        auth.authenticationProvider(authProvider());
//        auth.inMemoryAuthentication()
//                .withUser("teacher").password("pass").roles("TEACHER")
//                .and()
//                .withUser("student").password("pass").roles("STUDENT");
    }


    @Override
    protected UserDetailsService userDetailsService() {
        return rentalUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/api/rentals").hasRole(UserRole.ADMIN.toString())
                .antMatchers("/api/clients").hasAnyRole(UserRole.ADMIN.toString(), UserRole.CLIENT.toString())
                .antMatchers("/api/movies").hasAnyRole(UserRole.ADMIN.toString(), UserRole.CLIENT.toString())
                //.antMatchers("/api/users**").permitAll()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(mySavedRequestAwareAuthenticationSuccessHandler)
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccess)
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(false)
                .permitAll();
        http.cors();

    }

    @Bean
    public MySavedRequestAwareAuthenticationSuccessHandler mySuccessHandler() {
        return mySavedRequestAwareAuthenticationSuccessHandler;
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler myFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }
}

