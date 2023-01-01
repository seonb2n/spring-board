package com.example.board.config;

import com.example.board.dto.UserAccountDto;
import com.example.board.service.UserAccountService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SpringSecurityConfig.class)
public class TestSecurityConfig {

    @MockBean
    private UserAccountService userAccountService;

    /**
     * Spring Test 가 시행되기 전에만 호출되는 security 인증 메서드
     */
    @BeforeTestMethod
    public void securitySetUp() {
        given(userAccountService.searchUserByUsername(anyString()))
                .willReturn(Optional.of(createUserAccountDto()));
        given(userAccountService.saveUser(anyString(), anyString(), anyString(), anyString(), anyString()))
                .willReturn(createUserAccountDto());
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                "sbTest",
                "pw",
                "sb-test@gmail.com",
                "sb-test",
                "test memo"
        );
    }
}

