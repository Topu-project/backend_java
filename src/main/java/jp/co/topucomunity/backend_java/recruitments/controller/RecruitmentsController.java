package jp.co.topucomunity.backend_java.recruitments.controller;

import jp.co.topucomunity.backend_java.recruitments.controller.in.CreateRecruitmentRequest;
import jp.co.topucomunity.backend_java.recruitments.controller.out.RecruitmentResponse;
import jp.co.topucomunity.backend_java.recruitments.usecase.RecruitmentsUsecase;
import jp.co.topucomunity.backend_java.recruitments.usecase.in.PostRecruitment;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/recruitments")
@RestController
@RequiredArgsConstructor
public class RecruitmentsController {

    private final RecruitmentsUsecase recruitmentsUsecase;

    @PostMapping
    public void createRecruitment(@RequestBody CreateRecruitmentRequest request) {// TODO : バリデーション
        recruitmentsUsecase.post(PostRecruitment.from(request));
    }

    @GetMapping("/{recruitmentId}")
    public RecruitmentResponse getRecruitmentById(@PathVariable Long recruitmentId) { // TODO : recruitmentIdのnullバリデーション
        return recruitmentsUsecase.getRecruitment(recruitmentId);
    }

    @DeleteMapping("/{recruitmentId}")
    public void deleteRecruitmentById(@PathVariable Long recruitmentId) { // TODO : recruitmentIdのnullバリデーション
        recruitmentsUsecase.deleteRecruitment(recruitmentId);
    }
}
