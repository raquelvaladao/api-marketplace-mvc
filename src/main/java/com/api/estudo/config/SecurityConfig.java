package com.api.estudo.config;

import com.api.estudo.filter.AutenticacaoFilter;
import com.api.estudo.services.AutenticacaoService;
import com.api.estudo.services.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**",
            "/api/**"
    };
    private final UsuarioService usuarioService;
    private final AutenticacaoService autenticacaoService;

    public SecurityConfig(UsuarioService usuarioService, @Lazy AutenticacaoService autenticacaoService) {
        this.usuarioService = usuarioService;
        this.autenticacaoService = autenticacaoService;
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/v1/auth").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/usuario").permitAll()
                .antMatchers(HttpMethod.DELETE,"/v1/usuario/**", "/v1/transacoes/**").hasAuthority("ADMIN")
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(STATELESS)
                .and()
                .addFilterBefore(new AutenticacaoFilter(usuarioService, autenticacaoService),
                        UsernamePasswordAuthenticationFilter.class);
    }
}