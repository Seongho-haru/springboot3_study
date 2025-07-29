package com.example.firstproject.controller;

import com.example.firstproject.ArticleRepository;
import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepsitory;
    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/new")
    public String newArticleFrom() {
        return "articles/new";
    }
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        log.info(form.toString());
//        System.out.println("articleForm = " + form.toString());
        //1. DTO 엔티티로 변환
        Article article = form.toEntity();
        log.info(article.toString());
//        System.out.println("article = " + article.toString());
        //2. 리포지터리로 엔티티를 DB 에저장
        Article saved = articleRepsitory.save(article);
        log.info(saved.toString());
//        System.out.println("saved = " + saved.toString());
        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id: " + id);
        //1. id를 조회해 데이터 가져오기
        Article articleEntity = articleRepsitory.findById(id).orElse(null);
        List<CommentDto> commentsDtos = commentService.comments(id);
        //2. 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentsDtos);
        //3. 뷰페이지 반환하기
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        //1. 모든 데이터 가져오기
        ArrayList<Article> articlesEntityList = articleRepsitory.findAll();
        //2. 모델에 데이터 등록학;
        model.addAttribute("articleList", articlesEntityList);
        //3. 뷰 페이지 설정하기
        return "articles/index";
    }
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Article articleEntity = articleRepsitory.findById(id).orElse(null);
        model.addAttribute("article", articleEntity);
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String updateArticle(ArticleForm form) {
        log.info(form.toString());
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());
        Article target = articleRepsitory.findById(articleEntity.getId()).orElse(null);
        if (target != null) {
            articleRepsitory.save(articleEntity);
        }
        log.info(target.toString());
        return "redirect:/articles/" + target.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info( "삭제 요청이 들어왔습니다.");
        Article target = articleRepsitory.findById(id).orElse(null);
        log.info(target.toString());
        if( target != null ) {
            articleRepsitory.delete(target);
            rttr.addFlashAttribute("msg","삭제되었습니다!");
        }
         return "redirect:/articles";
    }
}
