package test.elias.ChatGod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.elias.ChatGod.model.GroupModel;
import test.elias.ChatGod.model.MessageModel;
import test.elias.ChatGod.model.UserModel;
import test.elias.ChatGod.repository.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // Send a message
    public MessageModel sendMessage(UserModel sender, UserModel receiver, String content) {
        MessageModel message = new MessageModel(content, sender, receiver, LocalDateTime.now());
        return messageRepository.save(message);
    }

    // Retrieve all messages between two users
    public List<MessageModel> getAllMessagesBetweenUsers(UserModel user1, UserModel user2) {
        return messageRepository.findAllBySenderAndReceiver(user1, user2);
    }

    // Retrieve a specific message by ID
    public Optional<MessageModel> getMessageById(Long messageId) {
        return messageRepository.findById(messageId);
    }

    // Mark a message as read
    public void markMessageAsRead(Long messageId) {
        messageRepository.findById(messageId).ifPresent(message -> {
            message.setRead(true);
            messageRepository.save(message);
        });
    }

    // Delete a message
    public void deleteMessage(Long messageId) {
        messageRepository.deleteById(messageId);
    }

    public List<MessageModel> findAllBySenderAndReceiver(UserModel sender, UserModel receiver) {
        return messageRepository.findAllBySenderAndReceiver(sender, receiver);
    }

    public List<MessageModel> findRecentConversations(UserModel user) {
        // Assuming you have a method in your repository for this
        return messageRepository.findRecentConversations(user);
    }

    public List<MessageModel> findBySenderAndReceiverAndTimestampBetween(UserModel sender, UserModel receiver, LocalDateTime start, LocalDateTime end) {
        return messageRepository.findBySenderAndReceiverAndTimestampBetween(sender, receiver, start, end);
    }

    public List<MessageModel> findUnreadByReceiver(UserModel receiver) {
        return messageRepository.findUnreadByReceiver(receiver);
    }

    public long countUnreadByReceiver(UserModel receiver) {
        return messageRepository.countUnreadByReceiver(receiver);
    }

    public List<MessageModel> searchInMessages(UserModel user, String searchText) {
        return messageRepository.searchInMessages(user, searchText);
    }

    public void deleteBySenderOrReceiver(UserModel user) {
        messageRepository.deleteBySenderOrReceiver(user);
    }

    public List<MessageModel> findAllByGroup(GroupModel group) {
        return messageRepository.findAllByGroup(group);
    }


}
