package jp.co.topucomunity.backend_java.recruitments.controller;

import jp.co.topucomunity.backend_java.recruitments.usecase.TechStacksUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/tech-stacks")
@RestController
@RequiredArgsConstructor
public class TechStacksController {

    private final TechStacksUsecase techStacksUsecase;

    @GetMapping
    public List<String> techStacks() {
        return techStacksUsecase.getTechnologyNames();
    }
}
