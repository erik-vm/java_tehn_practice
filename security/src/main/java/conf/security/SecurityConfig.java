package conf.security;

import conf.security.handlers.ApiAccessDeniedHandler;
import conf.security.handlers.ApiEntryPoint;
import conf.security.handlers.ApiLogoutSuccessHandler;
import conf.security.jwt.JwtAuthenticationFilter;
import conf.security.jwt.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@EnableWebSecurity
@EnableMethodSecurity
@PropertySource("classpath:/application.properties")
public class SecurityConfig {

    @Value("${jwt.signing.key}")
    private String jwtKey;

    private final MvcRequestMatcher.Builder mvc;

    public SecurityConfig(HandlerMappingIntrospector introspector) {
        this.mvc = new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.with(new FilterConfigurer(), withDefaults());

        http.formLogin(withDefaults());

        http.csrf(AbstractHttpConfigurer::disable);

        http.logout(conf -> conf.logoutUrl("/api/logout"));

        http.authorizeHttpRequests(conf -> conf
                .requestMatchers(mvc.pattern("/home")).permitAll()
                .requestMatchers(mvc.pattern("/admin/**")).hasRole("ADMIN")
                .requestMatchers(antMatcher("/static/**")).permitAll()
                .requestMatchers(mvc.pattern("/**")).authenticated()
        );

        http.exceptionHandling(conf -> conf
                .authenticationEntryPoint(new ApiEntryPoint())
                .accessDeniedHandler(new ApiAccessDeniedHandler()));

        http.logout(conf -> conf
                .logoutSuccessHandler(new ApiLogoutSuccessHandler())
                .logoutUrl("/api/logout"));

        return http.build();
    }

    public class FilterConfigurer extends AbstractHttpConfigurer<FilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) {
            AuthenticationManager manager = http.getSharedObject(AuthenticationManager.class);

            var loginFilter = new JwtAuthenticationFilter(
                    manager, "/api/login", jwtKey);

            http.addFilterBefore(loginFilter,
                    UsernamePasswordAuthenticationFilter.class);

            var authorizationFilter = new JwtAuthorizationFilter(jwtKey);

            http.addFilterBefore(authorizationFilter, AuthorizationFilter.class);

        }
    }

    @Bean
    public UserDetailsService userDetailService() {
        UserDetails user = User.builder()
                .username("user")
                .password("$2a$10$9eqxUT44JVtHcN6zaJqn9uwQio3vjyT2yMe3d4lY2NDyWltCM2v9a")
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("$2a$10$.FDr9hytownJXk.s4hX6AOAEk4Kn/Y8FqWGVhuwjLmDgWDGFx10J.")
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.debug(false);
    }

    private MvcRequestMatcher mvcMatcher(String pattern) {
        return mvc.pattern(pattern);
    }
}