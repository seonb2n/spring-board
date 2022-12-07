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
     * 본문의 내용을 바탕으로 # 이 붙은 단어를 HashTag Set 으로 파싱
     * @param content
     * @return
     */
    public Set<String> parseHashtagNames(String content) {
        return null;
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
