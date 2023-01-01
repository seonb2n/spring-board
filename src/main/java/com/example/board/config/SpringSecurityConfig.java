package com.example.board.config;

import com.example.board.dto.UserAccountDto;
import com.example.board.dto.security.BoardPrincipal;
import com.example.board.repository.UserAccountRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService
    ) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        // 정적 리소스에 대해 permit all
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        // 메인 페이지와, 해시태그 페이지는 모든 접근 허용
                        .mvcMatchers(
                                HttpMethod.GET,
                                "/",
                                "/articles",
                                "/articles/search-hashtag"
                        ).permitAll()
                        // 그 외 요청에 대해서는 모두 인증 요청
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults())
                .logout(logout -> logout.logoutSuccessUrl("/"))
                //oauth 인증 기능 설정
                .oauth2Login(oAuth -> oAuth
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService)
                        )
                )
                .build();
    }

    /**
     * 인증 정보를 가져오는 userDetailsService 를 생성
     *
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService(UserAccountRepository userAccountRepository) {
        return username -> userAccountRepository
                .findById(username)
                .map(UserAccountDto::from)
                .map(BoardPrincipal::from)
                //해당되는 사용자가 없는 경우 Exception 처리
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다. - username: " + username));
    }

    /**
     * OAuth2 에서 사용될 userService
     *
     * @return
     */
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        return null;
    }

    /**
     * Spring Security 에는 비밀번호 인코더 필요
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
