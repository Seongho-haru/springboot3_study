package com.example.firstproject.api;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommnetApiController {
    @Autowired
    private CommentService commentService;
    //1. 댓글 조회
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comment (@PathVariable Long articleId){
        //서비스에 위임
        List<CommentDto> dto = commentService.comments(articleId);
        // 결과 응답
        return  (ResponseEntity.status(HttpStatus.OK).body(dto));
    }
    //2. 댓글 생성
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> comment (@PathVariable Long articleId,
                                               @RequestBody CommentDto dto){
        //서비스에 위임
        CommentDto createDto = commentService.create(articleId,dto);
        //결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
    //3. 댓글 수정
    //4. 댓글 삭제

}
