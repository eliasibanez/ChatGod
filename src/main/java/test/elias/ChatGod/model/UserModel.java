package test.elias.ChatGod.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String username;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(length = 250)
    private String bio; // Short bio or status message

    @Column(name = "profile_picture_url")
    private String profilePictureUrl; // URL to the user's profile picture

    @Enumerated(EnumType.STRING)
    private Role role; // Role of the user (e.g., ADMIN, USER)

    @Enumerated(EnumType.STRING)
    private UserStatus status; // Online, offline, away, etc.

    @Column(name = "last_seen")
    private LocalDateTime lastSeen; // Timestamp of the user's last activity

    // Method to update fields with non-null values from another UserModel
    public void updateWith(UserModel other) {
        if (other.username != null) {
            this.username = other.username;
        }
        if (other.email != null) {
            this.email = other.email;
        }
        if (other.password != null) {
            this.password = other.password;
        }
        if (other.bio != null) {
            this.bio = other.bio;
        }
        if (other.profilePictureUrl != null) {
            this.profilePictureUrl = other.profilePictureUrl;
        }
        if (other.role != null) {
            this.role = other.role;
        }
        if (other.status != null) {
            this.status = other.status;
        }
        if (other.lastSeen != null) {
            this.lastSeen = other.lastSeen;
        }
    }

    // Enum for user roles
    public enum Role {
        USER, ADMIN, MODERATOR
    }

    // Enum for user status
    public enum UserStatus {
        ONLINE, OFFLINE, AWAY, BUSY
    }

    // Getters and Setters, hashCode, equals, and toString methods are omitted for brevity
    // Please implement them similar to the previous example
}
