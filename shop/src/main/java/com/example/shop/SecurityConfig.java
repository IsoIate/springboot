package com.example.shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 다른 사람이 만든 클래스를 bean 등록해서 다른 파일에서 사용하는 방법
    // 오브젝트 생성 함수 하나 만들고 해당 함수에 @Bean, 해당 함수를 포함하는 클래스에 @Configuration 부착
    @Bean
    PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        csrf 기능 사용시 주석해제
//        http.csrf(csrf -> csrf.csrfTokenRepository(csrfTokenRepository())
//                .ignoringRequestMatchers("/login")
//        )
        http.csrf((csrf) -> csrf.disable());    // csrf 공격 방지 시 삭제
        http.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers("/**").permitAll()    // 로그인 해제
        );
        
        // form 으로 로그인
        http.formLogin((formLogin) -> formLogin.loginPage("/login")
                .defaultSuccessUrl("/")
//                .failureUrl("/fail")
        );
        
        // 로그아웃 url 작성
        http.logout( logout -> logout.logoutUrl("/logout"));

        return http.build();
    }

//        csrf 기능 사용시 주석해제
//    @Bean
//    public CsrfTokenRepository csrfTokenRepository() {
//        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
//        repository.setHeaderName("X-XSRF-TOKEN");
//        return repository;
//    }
}