package com.example.firstproject.service;

import com.example.firstproject.ArticleRepository;
import com.example.firstproject.CommentRepository;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) {
//        // 1. 댓글 조회
//        List<Comment> comments = commentRepository.findByArticleId(articleId);
//        //2. 엔티티 -> DTO 변환
//        List<CommentDto> dtos = new ArrayList<CommentDto>();
//        for (int i = 0 ; i < comments.size() ; i++) {
//            Comment c = comments.get(i);
//            CommentDto dto = CommentDto.createCommentDto(c);
//            dtos.add(dto);
//        }
        //3. 결과 반환
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
    }
    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {
        //1.  게시글 조회 및 예외 방생
        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다!")
        );
        //2. 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto,article);
        //3. 댓글 엔티티를 DB에 저장
        Comment createdComment = commentRepository.save(comment);
        //4. DTO로 반환해 반환
        return CommentDto.createCommentDto(createdComment);
    }
}
