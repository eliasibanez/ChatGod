package test.elias.ChatGod.repository;

import test.elias.ChatGod.model.GroupModel;
import test.elias.ChatGod.model.MessageModel;
import test.elias.ChatGod.model.UserModel;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepositoryCustom {
    List<MessageModel> findAllBySenderAndReceiver(UserModel sender, UserModel receiver);

    List<MessageModel> findRecentConversations(UserModel user);

    List<MessageModel> findBySenderAndReceiverAndTimestampBetween(UserModel sender, UserModel receiver, LocalDateTime start, LocalDateTime end);

    List<MessageModel> findUnreadByReceiver(UserModel receiver);

    long countUnreadByReceiver(UserModel receiver);

    List<MessageModel> searchInMessages(UserModel user, String searchText);

    void deleteBySenderOrReceiver(UserModel user);

    public List<MessageModel> findAllByGroup(GroupModel group);

}
