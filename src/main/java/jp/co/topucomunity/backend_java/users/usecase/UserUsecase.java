package jp.co.topucomunity.backend_java.users.usecase;

import jp.co.topucomunity.backend_java.users.controller.out.UserResponse;
import jp.co.topucomunity.backend_java.users.exception.AlreadyExistNicknameException;
import jp.co.topucomunity.backend_java.users.exception.AlreadyExistTopuAuthException;
import jp.co.topucomunity.backend_java.users.exception.TopuAuthNotFoundException;
import jp.co.topucomunity.backend_java.users.repository.UserRepository;
import jp.co.topucomunity.backend_java.users.usecase.in.RegisterUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserUsecase {

    private final UserRepository userRepository;

    public UserResponse getUser(Long userId) {
        var foundUser = userRepository.findById(userId).orElseThrow(TopuAuthNotFoundException::new);
        return UserResponse.from(foundUser);
    }

    @Transactional
    public void signUp(RegisterUser registerUser) {
        var foundUser = userRepository.findById(registerUser.getUserId())
                .orElseThrow(TopuAuthNotFoundException::new);

        if (!foundUser.isFirstLogin()) {
            throw new AlreadyExistTopuAuthException();
        }

        userRepository.findByNickname(registerUser.getNickname()).ifPresent(user -> {
            throw new AlreadyExistNicknameException(user.getNickname());
        });

        foundUser.registerFirstLoginUserInfo(registerUser);
    }
}
