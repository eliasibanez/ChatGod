package test.elias.ChatGod.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;

import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "groups")
@NoArgsConstructor
@AllArgsConstructor
public class GroupModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    @Getter
    @Setter
    private String name;

    @ManyToMany
    @JoinTable(
            name = "group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserModel> members;

    public Collection<UserModel> getMembers() {
        return members;
    }

    public void setMembers(Collection<UserModel> members) {
        this.members = (Set<UserModel>) members;
    }
}
