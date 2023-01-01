package com.example.board.service;

import com.example.board.domain.UserAccount;
import com.example.board.dto.UserAccountDto;
import com.example.board.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    public Optional<UserAccountDto> searchUserByUsername(String username) {
        return userAccountRepository.findById(username).map(UserAccountDto::from);
    }

    @Transactional
    public UserAccountDto saveUser(String username, String password, String email, String nickname, String memo) {
        return UserAccountDto.from(userAccountRepository.save(UserAccount.of(
                username, password, email, nickname, memo, username
        )));
    }
}
