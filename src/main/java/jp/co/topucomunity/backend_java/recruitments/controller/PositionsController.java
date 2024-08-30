package jp.co.topucomunity.backend_java.recruitments.controller;

import jp.co.topucomunity.backend_java.recruitments.usecase.PositionsUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/positions")
@RestController
@RequiredArgsConstructor
public class PositionsController {

    private final PositionsUsecase positionsUsecase;

    @GetMapping
    public List<String> getPositions() {
        return positionsUsecase.getPositions();
    }

}
