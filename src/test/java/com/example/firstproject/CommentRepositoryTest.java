package com.example.firstproject;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")
    void findByArticleId() {
        /* case 1. :4번 게시글의 모든 댓글 조회*/
        {
            //1. 입력 데이터 준비
            Long articleId = 4L;
            //2. 실제 데이터
            List<Comment> comments = commentRepository.findByArticleId(articleId);
            // 3. 예상 데이터
            Article article = new Article(4L, "당신의 인생 영화는?", "댓글 고");

            Comment a = new Comment(1L, article, "Park", "굿 월 헌팅");
            Comment b = new Comment(2L, article, "Kim", "아이 엠 샘");
            Comment c = new Comment(3L, article, "Choi", "쇼생크 탈출");

            List<Comment> expected = Arrays.asList(a, b, c);
            //4. 비교 및 검증
            assertEquals(expected.toString(),comments.toString(),"4번 글은 모든 댓글을 출력!");
        }
        /* case 2. :1번 게시글의 모든 댓글 조회*/
        {
            //1. 입력 데이터 준비
            Long articleId = 1L;
            //2. 실제 데이터
            List<Comment> comments = commentRepository.findByArticleId(articleId);
            // 3. 예상 데이터
            Article article = new Article(1L, "가가가가", "1111");

            List<Comment> expected = Arrays.asList();
            //4. 비교 및 검증
            assertEquals(expected.toString(),comments.toString(),"1번 글은 댓글이 없음");
        }
    }

    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickname() {
        /* case 1. : Park의 모든 댓글 조회*/
        {
            //1. 입력 데이터 준비
            String nickname = "Park";
            //2. 실제 데이터
            List<Comment> comments = commentRepository.findByNickname(nickname);
            // 3. 예상 데이터
            Comment a = new Comment(1L, new Article(4L, "당신의 인생 영화는?", "댓글 고"), nickname,"굿 월 헌팅");
            Comment b = new Comment(4L, new Article(5L, "당신의 소울 푸드는?", "댓글 고고"), nickname, "치킨!");
            Comment c = new Comment(7L, new Article(6L, "당신의 취미는?", "댓글 고고고"), nickname, "조깅");

            List<Comment> expected = Arrays.asList(a, b, c);
            //4. 비교 및 검증
            assertEquals(expected.toString(),comments.toString(),"Park의 모든 댓글을 출력");
        }
    }
}