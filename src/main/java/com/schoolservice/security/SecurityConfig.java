/*
 * package com.schoolservice.security;
 * 
 * import org.keycloak.adapters.KeycloakConfigResolver; import
 * org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver; import
 * org.keycloak.adapters.springsecurity.KeycloakSecurityComponents; import
 * org.keycloak.adapters.springsecurity.authentication.
 * KeycloakAuthenticationProvider; import
 * org.keycloak.adapters.springsecurity.config.
 * KeycloakWebSecurityConfigurerAdapter; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.ComponentScan; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.context.annotation.FilterType; import
 * org.springframework.http.HttpMethod; import
 * org.springframework.security.config.annotation.authentication.builders.
 * AuthenticationManagerBuilder; import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.config.annotation.web.configuration.
 * EnableWebSecurity; import
 * org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
 * import org.springframework.security.core.session.SessionRegistryImpl; import
 * org.springframework.security.web.authentication.session.
 * RegisterSessionAuthenticationStrategy; import
 * org.springframework.security.web.authentication.session.
 * SessionAuthenticationStrategy;
 * 
 * 
 * 
 * 
 * @Configuration
 * 
 * @ComponentScan( basePackageClasses = KeycloakSecurityComponents.class,
 * excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern =
 * "org.keycloak.adapters.springsecurity.management.HttpSessionManager"))
 * 
 * @EnableWebSecurity class SecurityConfig extends
 * KeycloakWebSecurityConfigurerAdapter {
 *//**
	 * Registers the KeycloakAuthenticationProvider with the authentication manager.
	 */
/*
 * @Autowired public void configureGlobal(AuthenticationManagerBuilder auth)
 * throws Exception { KeycloakAuthenticationProvider
 * keycloakAuthenticationProvider = keycloakAuthenticationProvider();
 * keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new
 * SimpleAuthorityMapper());
 * auth.authenticationProvider(keycloakAuthenticationProvider); }
 * 
 * @Bean public KeycloakConfigResolver KeycloakConfigResolver() { return new
 * KeycloakSpringBootConfigResolver(); }
 * 
 *//**
	 * Defines the session authentication strategy.
	 *//*
		 * @Bean
		 * 
		 * @Override protected SessionAuthenticationStrategy
		 * sessionAuthenticationStrategy() { return new
		 * RegisterSessionAuthenticationStrategy(new SessionRegistryImpl()); }
		 * 
		 * @Override protected void configure(HttpSecurity http) throws Exception {
		 * super.configure(http); http.authorizeRequests() .antMatchers(HttpMethod.GET,
		 * "/health").permitAll() .antMatchers(HttpMethod.GET,
		 * "/api/school").authenticated() .antMatchers(HttpMethod.GET,
		 * "/api/school/all").authenticated() .antMatchers(HttpMethod.GET,
		 * "/api/school/getStudentsBySchool/*").hasRole("readschool")
		 * .antMatchers(HttpMethod.POST, "/api/school/create").hasRole("create_school");
		 * } }
		 */