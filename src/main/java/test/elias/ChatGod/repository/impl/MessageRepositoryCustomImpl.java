package test.elias.ChatGod.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import test.elias.ChatGod.model.GroupModel;
import test.elias.ChatGod.model.MessageModel;
import test.elias.ChatGod.model.UserModel;
import test.elias.ChatGod.repository.MessageRepositoryCustom;
import java.time.LocalDateTime;

import java.util.List;

public class MessageRepositoryCustomImpl implements MessageRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<MessageModel> findAllBySenderAndReceiver(UserModel sender, UserModel receiver) {
        TypedQuery<MessageModel> query = entityManager.createQuery(
                "SELECT m FROM MessageModel m WHERE m.sender = :sender AND m.receiver = :receiver", MessageModel.class);
        query.setParameter("sender", sender);
        query.setParameter("receiver", receiver);
        return query.getResultList();
    }


    @Override
    public List<MessageModel> findRecentConversations(UserModel user) {
        // This query assumes a specific database structure and might need adjustments
        String jpql = "SELECT m FROM MessageModel m WHERE m.sender = :user OR m.receiver = :user GROUP BY m.conversationId ORDER BY MAX(m.timestamp) DESC";
        TypedQuery<MessageModel> query = entityManager.createQuery(jpql, MessageModel.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    @Override
    public List<MessageModel> findBySenderAndReceiverAndTimestampBetween(UserModel sender, UserModel receiver, LocalDateTime start, LocalDateTime end) {
        String jpql = "SELECT m FROM MessageModel m WHERE m.sender = :sender AND m.receiver = :receiver AND m.timestamp BETWEEN :start AND :end";
        TypedQuery<MessageModel> query = entityManager.createQuery(jpql, MessageModel.class);
        query.setParameter("sender", sender);
        query.setParameter("receiver", receiver);
        query.setParameter("start", start);
        query.setParameter("end", end);
        return query.getResultList();
    }

    @Override
    public List<MessageModel> findUnreadByReceiver(UserModel receiver) {
        String jpql = "SELECT m FROM MessageModel m WHERE m.receiver = :receiver AND m.isRead = false";
        TypedQuery<MessageModel> query = entityManager.createQuery(jpql, MessageModel.class);
        query.setParameter("receiver", receiver);
        return query.getResultList();
    }

    @Override
    public long countUnreadByReceiver(UserModel receiver) {
        String jpql = "SELECT COUNT(m) FROM MessageModel m WHERE m.receiver = :receiver AND m.isRead = false";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("receiver", receiver);
        return query.getSingleResult();
    }

    @Override
    public List<MessageModel> searchInMessages(UserModel user, String searchText) {
        String jpql = "SELECT m FROM MessageModel m WHERE (m.sender = :user OR m.receiver = :user) AND LOWER(m.content) LIKE LOWER(:searchText)";
        TypedQuery<MessageModel> query = entityManager.createQuery(jpql, MessageModel.class);
        query.setParameter("user", user);
        query.setParameter("searchText", "%" + searchText + "%");
        return query.getResultList();
    }

    @Override
    public void deleteBySenderOrReceiver(UserModel user) {
        String jpql = "DELETE FROM MessageModel m WHERE m.sender = :user OR m.receiver = :user";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("user", user);
        query.executeUpdate();
    }

    @Override
    public List<MessageModel> findAllByGroup(GroupModel group) {
        String jpql = "SELECT m FROM MessageModel m WHERE m.group = :group";
        TypedQuery<MessageModel> query = entityManager.createQuery(jpql, MessageModel.class);
        query.setParameter("group", group);
        return query.getResultList();
    }
}
