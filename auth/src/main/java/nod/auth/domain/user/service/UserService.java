package nod.auth.domain.user.service;

public interface UserService {
    void register(String userId, String password);
    void login(String userId, String password);
}
