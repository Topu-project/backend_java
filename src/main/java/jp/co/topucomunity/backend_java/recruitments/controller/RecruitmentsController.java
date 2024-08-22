package jp.co.topucomunity.backend_java.recruitments.controller;

import jp.co.topucomunity.backend_java.recruitments.controller.in.CreateRecruitmentRequest;
import jp.co.topucomunity.backend_java.recruitments.usecase.RecruitmentsUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/recruitments")
@RestController
@RequiredArgsConstructor
public class RecruitmentsController {

    private final RecruitmentsUsecase recruitmentsUsecase;

    @PostMapping
    public void createRecruitment(@RequestBody CreateRecruitmentRequest request) {
        recruitmentsUsecase.post(request.toPostRecruitment());
    }
}
