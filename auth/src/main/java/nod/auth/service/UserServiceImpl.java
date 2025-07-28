package nod.auth.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nod.auth.entity.User;
import nod.auth.repository.UserRepository;
import nod.common.global.error.UserError;
import nod.common.global.exception.BusinessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void register(
            @NonNull String userId,
            @NonNull String password
    ) {
        userRepository.findByUserId(userId).ifPresent(user -> {
            throw new BusinessException(UserError.DUPLICATE_USER_ID);
        });

        User user = User.create(userId, passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public void login(String userId, String password) {

    }
}
