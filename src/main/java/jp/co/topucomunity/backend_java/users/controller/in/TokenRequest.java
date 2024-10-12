package jp.co.topucomunity.backend_java.users.controller.in;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TokenRequest {

    @NotBlank(message = "{auth.notnull}")
    private String code;

}
