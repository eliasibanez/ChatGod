package test.elias.ChatGod.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.elias.ChatGod.model.ChatDTO;
import test.elias.ChatGod.model.MessageModel;
import test.elias.ChatGod.model.UserModel;
import test.elias.ChatGod.service.MessageService;
import test.elias.ChatGod.service.UserService;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// This class will be used to handle all chat-related requests. Including ChatViews and ChatWindows.
@RestController
@CrossOrigin("localhost:3000")
@RequestMapping("/chat")
public class ChatController {

    private final UserService userService;
    private final MessageService messageService;

    @Autowired
    public ChatController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    // Endpoint para obtener la lista de chats de un usuario específico
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ChatDTO>> getChatsForUser(@PathVariable Long userId) {
        Optional<UserModel> userOptional = userService.getUserById(userId);
        if (!userOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        UserModel user = userOptional.get();
        List<ChatDTO> chatList = messageService.(user);
        return ResponseEntity.ok(chatList);
    }


}
