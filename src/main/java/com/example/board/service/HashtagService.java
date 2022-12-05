package com.example.board.service;

import com.example.board.dto.HashtagDto;
import com.example.board.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class HashtagService {

    private final HashtagRepository hashtagRepository;

    /**
     * 이름으로 해시태그 만들기
     * @param content
     * @return
     */
    public Object parseHashtagNames(String content) {
        var hashtagDto = HashtagDto.of(content);
        var initHashTag = hashtagDto.toEntity();
        return hashtagRepository.save(initHashTag);
    }

    /**
     * 이름으로 해시태그 찾기
     * @param expectedHashtagNames
     * @return
     */
    public Object findHashtagsByNames(Set<String> expectedHashtagNames) {
        return hashtagRepository.findAllHashtagNames();
    }

    /**
     * hashtag 삭제
     * @param any
     */
    public void deleteHashtagWithoutArticles(Object any) {
        hashtagRepository.deleteByHashtagName(any.toString());
    }
}
