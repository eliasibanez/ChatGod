package test.elias.ChatGod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.elias.ChatGod.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long>, UserRepositoryCustom {


}
