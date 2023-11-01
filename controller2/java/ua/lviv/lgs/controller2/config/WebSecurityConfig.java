package ua.lviv.lgs.controller2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	private final UserDetailsService userDetailsService;

	public WebSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				// Настройки авторизации
				.authorizeHttpRequests((authz) -> authz.requestMatchers("/").permitAll().requestMatchers("/home")
						.hasRole("USER").anyRequest().authenticated())
				// Настройки формы логина
				.formLogin((form) -> form.loginPage("/login").defaultSuccessUrl("/home").usernameParameter("email")
						.passwordParameter("password"))
				// Настройки выхода из системы
				.logout((logout) -> logout.logoutSuccessUrl("/login?logout"))
				// Обработка исключений
				.exceptionHandling((exceptions) -> exceptions.accessDeniedPage("/403"))
				// Настройка CSRF
				.csrf(AbstractHttpConfigurer::disable);

		return http.build();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}