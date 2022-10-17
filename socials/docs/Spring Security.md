Needed dependency in pom.xml
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
```
##### Basic authentication
```
@Configuration

public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/", "index").permitAll().anyRequest().authenticated().and().httpBasic();
        return http.build();
    }
}
```
