package test.elias.ChatGod.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import test.elias.ChatGod.model.UserModel;
import test.elias.ChatGod.repository.UserRepositoryCustom;

import java.util.Optional;

// Custom methods for UserRepository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Optional<UserModel> findByUsername(String username) {
        TypedQuery<UserModel> query = entityManager.createQuery(
                "SELECT u FROM UserModel u WHERE u.username = :username", UserModel.class);
        query.setParameter("username", username);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Optional<UserModel> findByEmail(String email) {
       TypedQuery<UserModel> query = entityManager.createQuery(
               "SELECT u FROM UserModel u WHERE u.email = :email", UserModel.class);
       query.setParameter("email", email);
   return query.getResultList().stream().findFirst();
    }

    @Override
    public Optional<UserModel> findByUsernameAndPassword(String username, String password) {
        TypedQuery<UserModel> query = entityManager.createQuery(
                "SELECT u FROM UserModel u WHERE u.username = :username AND u.password = :password", UserModel.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        return query.getResultList().stream().findFirst();
    }
}
