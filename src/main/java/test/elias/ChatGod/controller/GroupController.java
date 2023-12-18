package test.elias.ChatGod.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.elias.ChatGod.model.GroupModel;
import test.elias.ChatGod.model.UserModel;
import test.elias.ChatGod.service.GroupService;
import test.elias.ChatGod.service.UserService;

import java.util.Set;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;
    private final UserService userService;

    @Autowired
    public GroupController(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }

    // Create a new group
    @PostMapping
    public ResponseEntity<GroupModel> createGroup(@RequestBody GroupModel group) {
        GroupModel newGroup = groupService.createGroup(group.getName(), (Set<UserModel>) group.getMembers());
        return ResponseEntity.ok(newGroup);
    }

    // Add a member to a group
    @PostMapping("/{groupId}/members/{userId}")
    public ResponseEntity<GroupModel> addMemberToGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        UserModel user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        GroupModel updatedGroup = groupService.addMember(groupId, user);
        return ResponseEntity.ok(updatedGroup);
    }

    // Remove a member from a group
    @DeleteMapping("/{groupId}/members/{userId}")
    public ResponseEntity<GroupModel> removeMemberFromGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        UserModel user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        GroupModel updatedGroup = groupService.removeMember(groupId, user);
        return ResponseEntity.ok(updatedGroup);
    }

    // Get a specific group by ID
    @GetMapping("/{id}")
    public ResponseEntity<GroupModel> getGroupById(@PathVariable Long id) {
        return groupService.getGroupById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a group
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.ok().build();
    }
}
