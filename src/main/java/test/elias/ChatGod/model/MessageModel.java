package test.elias.ChatGod.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MessageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content; // The text content of the message

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private UserModel sender; // The sender of the message

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private UserModel receiver; // The receiver of the message

    @Column(nullable = false)
    private LocalDateTime timestamp; // The time when the message was sent

    private boolean isRead; // Whether the message has been read by the receiver

    // Constructor with fields
    public MessageModel(String content, UserModel sender, UserModel receiver, LocalDateTime timestamp) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = timestamp;
        this.isRead = false; // By default, a new message is unread
    }

    // Other business logic methods can be added here
}
