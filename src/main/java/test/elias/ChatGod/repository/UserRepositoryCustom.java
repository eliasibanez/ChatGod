package test.elias.ChatGod.repository;

import test.elias.ChatGod.model.UserModel;

import javax.swing.text.html.Option;
import java.util.Optional;

// Custom methods for UserRepository
public interface UserRepositoryCustom {
    Optional<UserModel> findByUsername(String username);

    Optional<UserModel> findByEmail(String email);

    Optional<UserModel> findByUsernameAndPassword(String username, String password);
}
