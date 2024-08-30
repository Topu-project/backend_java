package jp.co.topucomunity.backend_java.recruitments.usecase;

import jp.co.topucomunity.backend_java.recruitments.domain.Position;
import jp.co.topucomunity.backend_java.recruitments.repository.PositionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionsUsecase {

    private final PositionsRepository positionsRepository;
    
    public List<String> getPositions() {
        var positions = positionsRepository.findAll();
        return positions.stream().map(Position::getPositionName).toList();
    }
}
