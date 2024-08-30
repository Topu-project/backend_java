package jp.co.topucomunity.backend_java.recruitments.usecase;

import jp.co.topucomunity.backend_java.recruitments.domain.TechStack;
import jp.co.topucomunity.backend_java.recruitments.repository.TechStacksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TechStacksUsecase {

    private final TechStacksRepository techStacksRepository;

    public List<String> getTechnologyNames() {
        var techStacks = techStacksRepository.findAll();
        return techStacks.stream().map(TechStack::getTechnologyName).toList();
    }
}
