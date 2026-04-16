package apiGateway.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

import serviceLibrary.dto.usersService.UserDto;

@Configuration
@EnableWebFluxSecurity
public class ApiGatewayAuthentication {
	
	@Bean
	SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
		http
		.csrf(csrf -> csrf.disable())
		.authorizeExchange(exchange -> 
		exchange.pathMatchers(HttpMethod.POST).hasRole("ADMIN")
		.pathMatchers("/currency-exchange").permitAll()
		.pathMatchers("/currency-conversion").hasRole("USER")
		.pathMatchers("users").hasRole("ADMIN"))
		.httpBasic(Customizer.withDefaults())
		;
		
		return http.build();
	}

	@Bean
	WebClient.Builder webclientBuilder(){
		return WebClient.builder();
	}
	
	@Bean
	BCryptPasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	ReactiveUserDetailsService getUserDetails(WebClient.Builder webclientBuilder,
			BCryptPasswordEncoder encoder) {
		
		WebClient webclient = webclientBuilder.baseUrl("http://localhost:8770").build();
		
		return user -> webclient.get()
				.uri(uriBuilder -> uriBuilder
				.path("/users/email")
				.queryParam("email", user)
				.build())
				
				.retrieve()
				.bodyToMono(UserDto.class)
				.map(dto -> User.withUsername(dto.getEmail())
						.password(encoder.encode(dto.getPassword()))
						.roles(dto.getRole())
						.build()
						);
	}
	
	
	
	
	
	
}
