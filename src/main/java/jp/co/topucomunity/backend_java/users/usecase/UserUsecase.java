package jp.co.topucomunity.backend_java.users.usecase;

import jp.co.topucomunity.backend_java.users.controller.out.UserResponse;
import jp.co.topucomunity.backend_java.users.exception.AlreadyExistNicknameException;
import jp.co.topucomunity.backend_java.users.exception.AlreadyExistUserException;
import jp.co.topucomunity.backend_java.users.exception.UserNotFoundException;
import jp.co.topucomunity.backend_java.users.repository.ClientRegistrationFacade;
import jp.co.topucomunity.backend_java.users.repository.UserRepository;
import jp.co.topucomunity.backend_java.users.usecase.in.RegisterUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserUsecase {

    private final UserRepository userRepository;
    private final ClientRegistrationFacade clientRegistrationFacade;

    public UserResponse getUser(Long userId) {
        var foundUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return UserResponse.from(foundUser);
    }

    @Transactional
    public void signUp(RegisterUser registerUser) {
        var foundUser = userRepository.findById(registerUser.getUserId())
                .orElseThrow(UserNotFoundException::new);

        if (!foundUser.isFirstLogin()) {
            throw new AlreadyExistUserException();
        }

        userRepository.findByNickname(registerUser.getNickname()).ifPresent(user -> {
            throw new AlreadyExistNicknameException(user.getNickname());
        });

        foundUser.registerFirstLoginUserInfo(registerUser);
    }

    public void oidcLogin(String accessToken, String idToken) {
//         var jwt = JwtUtil.decodeToken(clientRegistrationFacade.getIssuerUri(), idToken);

        // 사용자 정보 추출
//        String sub = jwt.getSubject();
//        String email = jwt.getClaimAsString("email");
//        String name = jwt.getClaimAsString("name");
//        String picture = jwt.getClaimAsString("picture");

//        User.from(jwt);

    }
}
