package jp.co.topucomunity.backend_java.recruitments.controller.in;

import jp.co.topucomunity.backend_java.recruitments.domain.enums.ProgressMethods;
import lombok.Data;

import java.util.List;

/**
 * ?techStacks=kotlin,java,spring&positions=backend,frontend&progressMethods=ALL&search=encoded(searchValue)
 */

@Data
public class RecruitmentSearch {

    private List<String> techStacks;
    private List<String> positions;
    private ProgressMethods progressMethods;
    private String search;

}
