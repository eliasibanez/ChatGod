package test.elias.ChatGod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.elias.ChatGod.model.GroupModel;

public interface GroupRepository extends JpaRepository<GroupModel, Long> {
}
