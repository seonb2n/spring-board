package com.example.board.service;

import com.example.board.domain.Article;
import com.example.board.domain.ArticleComment;
import com.example.board.domain.UserAccount;
import com.example.board.dto.ArticleCommentDto;
import com.example.board.repository.ArticleCommentRepository;
import com.example.board.repository.ArticleRepository;
import com.example.board.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ArticleCommentService {

    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;

    /**
     * articleId 로 게시물과 연관된 댓글 조회하는 메서드
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComments(Long articleId) {
        return articleCommentRepository.findByArticle_Id(articleId)
                .stream()
                .map(ArticleCommentDto::from)
                .toList();
    }

    /**
     * 댓글 저장하는 메서드
     */
    public void saveArticleComment(ArticleCommentDto dto) {
        try {
            Article article = articleRepository.getReferenceById(dto.articleId());
            UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());
            articleCommentRepository.save(dto.toEntity(article, userAccount));
        } catch (EntityNotFoundException e) {
            log.warn("댓글 저장 실패. 댓글의 게시글을 찾을 수 없습니다 - dto: {}", e.getLocalizedMessage());
        }
    }

    /**
     * 댓글 업데이트하는 메서드
     *
     * @param dto
     */
    public void updateArticleComment(ArticleCommentDto dto) {
        try {
            //proxy 객체 조회
            ArticleComment articleComment = articleCommentRepository.getReferenceById(dto.id());
            if (dto.content() != null) {
                articleComment.setContent(dto.content());
            }
        } catch (EntityNotFoundException e) {
            log.warn("댓글 업데이트 실패. 댓글을 찾을 수 없습니다 - dto: {}", dto);
        }
    }

    /**
     * 댓글 삭제하는 메서드
     *
     * @param articleCommendId
     */
    public void deleteArticleComment(Long articleCommendId) {
        articleCommentRepository.deleteById(articleCommendId);
    }

}
