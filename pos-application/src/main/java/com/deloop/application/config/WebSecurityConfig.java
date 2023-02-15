package com.deloop.application.config;

import com.deloop.domain.services.AuthenticationFacade;
import com.deloop.domain.services.AuthenticationFacadeImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.Objects;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Import({JwtConfig.class})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {// implements EnvironmentAware {

    //    @Autowired
//    JwtTokenService jwtTokenService;
//

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/v2/api-docs",
            "/webjars/**",
            "/home"
    };
//    @Autowired
//    private final JwtAuthenticationProvider jwtAuthenticationProvider;
//
//    @Autowired
//    private final UserService userService;
//
//    @Autowired
//    private final PasswordEncoder passwordEncoder;
    @Autowired
    private Environment env;

//    @Bean
//    public AuthenticationEntryPoint authenticationEntryPoint(ObjectMapper objectMapper) {
//        return new JwtAuthenticationEntryPoint(objectMapper);
//    }

    @Bean
    AuthenticationFacade authenticationFacade() {
        return new AuthenticationFacadeImpl();
    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
//            }
//        };
//    }

    // To enable CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();

//        configuration.setAllowedOrigins(List.of("https://www.yourdomain.com")); // www - obligatory
        configuration.setAllowedOrigins(List.of("*"));  //set access from all domains
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
//        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        AuthenticationEntryPoint authenticationEntryPoint = lookup("authenticationEntryPoint");
//        JwtTokenService jwtTokenService = lookup("jwtTokenService");
//
//        OncePerRequestFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenService);

        System.err.println(env.getProperty("env") + " in websecurity");

//        if (Objects.equals(env.getProperty("env"), "dev")) {
//
//        Authentication authentication = jwtTokenService.parseJwtToken(jwtToken);
//            UserDao.builder().id(2).email("delcoker@live.ca").firstname("dev env user 2").
//                    userRoleDao(UserRoleDao.builder().id(2).build()).build();
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        }

        if (!Objects.equals(env.getProperty("env"), "de")) {
            // TODO release uncomment for
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
//                    .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
//                    .and()
                    .authorizeRequests().antMatchers(AUTH_WHITELIST).authenticated()
                    .and()
                    .authorizeRequests().regexMatchers("/actuator/.*").permitAll()
                    .and()
                    .authorizeRequests().regexMatchers("/sentiment/.*").permitAll()
                    .and()
                    .authorizeRequests((request) -> request.antMatchers("/registration/**",
                                    "/auth/**",
                                    "/swagger-ui/**")
                            .permitAll()
                            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                            .anyRequest().authenticated());
//                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        }

        http.csrf().disable().cors().and().headers().frameOptions().disable();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService);
//        auth.authenticationProvider(daoAuthenticationProvider());
//        auth.authenticationProvider(jwtAuthenticationProvider); // https://www.javadevjournal.com/spring-security/spring-security-multiple-authentication-providers/
//    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }

//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(passwordEncoder);
//        provider.setUserDetailsService(userService);
//        return provider;
//    }

    protected <T> T lookup(String beanName) {
        return (T) getApplicationContext().getBean(beanName);
    }

}
