package test.elias.ChatGod.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.elias.ChatGod.model.GroupModel;
import test.elias.ChatGod.model.MessageModel;
import test.elias.ChatGod.model.UserModel;
import test.elias.ChatGod.service.GroupService;
import test.elias.ChatGod.service.MessageService;
import test.elias.ChatGod.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@AllArgsConstructor
public class MessageController {

    @Autowired
    private final MessageService messageService;

    @Autowired
    private final UserService userService;

    @Autowired
    private GroupService groupService;


    // Send a message
    @PostMapping
    public ResponseEntity<MessageModel> sendMessage(@RequestBody MessageModel message) {
        MessageModel sentMessage = messageService.sendMessage(message.getSender(), message.getReceiver(), message.getContent());
        return ResponseEntity.ok(sentMessage);
    }

    // Get message history between two users
    @GetMapping("/history")
    public ResponseEntity<List<MessageModel>> getMessageHistory(@RequestParam Long senderId, @RequestParam Long receiverId) {
        UserModel sender = userService.getUserById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        UserModel receiver = userService.getUserById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        List<MessageModel> messages = messageService.getAllMessagesBetweenUsers(sender, receiver);
        return ResponseEntity.ok(messages);
    }

    // Get a specific message by ID
    @GetMapping("/{id}")
    public ResponseEntity<MessageModel> getMessageById(@PathVariable Long id) {
        return messageService.getMessageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a message
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/conversation")
    public ResponseEntity<List<MessageModel>> getConversation(@RequestParam Long senderId, @RequestParam Long receiverId) {
        UserModel sender = userService.getUserById(senderId).orElseThrow(() -> new RuntimeException("Sender not found"));
        UserModel receiver = userService.getUserById(receiverId).orElseThrow(() -> new RuntimeException("Receiver not found"));
        List<MessageModel> messages = messageService.findAllBySenderAndReceiver(sender, receiver);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/recent/{userId}")
    public ResponseEntity<List<MessageModel>> getRecentConversations(@PathVariable Long userId) {
        UserModel user = userService.getUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<MessageModel> messages = messageService.findRecentConversations(user);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/range")
    public ResponseEntity<List<MessageModel>> getMessagesInRange(@RequestParam Long senderId, @RequestParam Long receiverId, @RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
        UserModel sender = userService.getUserById(senderId).orElseThrow(() -> new RuntimeException("Sender not found"));
        UserModel receiver = userService.getUserById(receiverId).orElseThrow(() -> new RuntimeException("Receiver not found"));
        List<MessageModel> messages = messageService.findBySenderAndReceiverAndTimestampBetween(sender, receiver, start, end);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/unread/{receiverId}")
    public ResponseEntity<List<MessageModel>> getUnreadMessages(@PathVariable Long receiverId) {
        UserModel receiver = userService.getUserById(receiverId).orElseThrow(() -> new RuntimeException("Receiver not found"));
        List<MessageModel> messages = messageService.findUnreadByReceiver(receiver);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/count-unread/{receiverId}")
    public ResponseEntity<Long> countUnreadMessages(@PathVariable Long receiverId) {
        UserModel receiver = userService.getUserById(receiverId).orElseThrow(() -> new RuntimeException("Receiver not found"));
        long count = messageService.countUnreadByReceiver(receiver);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MessageModel>> searchMessages(@RequestParam Long userId, @RequestParam String searchText) {
        UserModel user = userService.getUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<MessageModel> messages = messageService.searchInMessages(user, searchText);
        return ResponseEntity.ok(messages);
    }

    @DeleteMapping("/delete-user-messages/{userId}")
    public ResponseEntity<?> deleteMessagesByUser(@PathVariable Long userId) {
        UserModel user = userService.getUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        messageService.deleteBySenderOrReceiver(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<MessageModel>> getGroupMessages(@PathVariable Long groupId) {
        GroupModel group = groupService.getGroupById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
        List<MessageModel> messages = messageService.findAllByGroup(group);
        return ResponseEntity.ok(messages);
    }
}
