package nod.auth.service;

public interface UserService {
    void register(String userId, String password);
    void login(String userId, String password);
}
