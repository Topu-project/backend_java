package jp.co.topucomunity.backend_java.recruitments.controller;

import jp.co.topucomunity.backend_java.recruitments.controller.in.CreateRecruitmentRequest;
import jp.co.topucomunity.backend_java.recruitments.controller.in.UpdateRecruitmentRequest;
import jp.co.topucomunity.backend_java.recruitments.controller.out.RecruitmentIndexPageResponse;
import jp.co.topucomunity.backend_java.recruitments.controller.out.RecruitmentResponse;
import jp.co.topucomunity.backend_java.recruitments.usecase.RecruitmentsUsecase;
import jp.co.topucomunity.backend_java.recruitments.usecase.in.PostRecruitment;
import jp.co.topucomunity.backend_java.recruitments.usecase.in.UpdateRecruitment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/recruitments")
@RestController
@RequiredArgsConstructor
public class RecruitmentsController {

    private final RecruitmentsUsecase recruitmentsUsecase;

    @PostMapping
    public void createRecruitment(@RequestBody CreateRecruitmentRequest request) { // TODO : バリデーション
        recruitmentsUsecase.post(PostRecruitment.from(request));
    }

    @GetMapping("/{recruitmentId}")
    public RecruitmentResponse getRecruitmentById(@PathVariable Long recruitmentId) { // TODO : recruitmentIdのnullバリデーション
        return recruitmentsUsecase.getRecruitment(recruitmentId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{recruitmentId}")
    public void deleteRecruitmentById(@PathVariable Long recruitmentId) { // TODO : recruitmentIdのnullバリデーション
        recruitmentsUsecase.deleteRecruitment(recruitmentId);
    }

    @GetMapping// TODO : Paging, Search, QueryDSL
    public List<RecruitmentIndexPageResponse> getRecruitmentsForIndexPage() {
        return recruitmentsUsecase.getRecruitments();
    }

    @PutMapping("/{recruitmentId}")
    public void updateByRecruitmentId(@PathVariable Long recruitmentId, @RequestBody UpdateRecruitmentRequest request) {
        recruitmentsUsecase.update(recruitmentId, UpdateRecruitment.from(request));
    }

    // TODO : getTechStacks
}
