package test.elias.ChatGod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.elias.ChatGod.model.MessageModel;
import test.elias.ChatGod.model.UserModel;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageModel, Long>, MessageRepositoryCustom {


    // Other standard JPA methods are inherited from JpaRepository
}
