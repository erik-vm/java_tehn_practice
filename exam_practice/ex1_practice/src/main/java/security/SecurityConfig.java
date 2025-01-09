package security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/security/user.html").hasAuthority("ROLE_USER")
                .antMatchers("/security/admin.html").hasAuthority("ROLE_ADMIN");

        // kood tuleb siia

    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.inMemoryAuthentication();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // do not apply security filter to servlet.MyServlet and mvc.Ctrl
        web.ignoring().antMatchers("/calculate");
        web.ignoring().antMatchers("/api/**");
    }
}