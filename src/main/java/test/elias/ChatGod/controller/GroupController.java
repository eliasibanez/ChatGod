package test.elias.ChatGod.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.elias.ChatGod.model.GroupModel;
import test.elias.ChatGod.model.UserModel;
import test.elias.ChatGod.service.GroupService;
import test.elias.ChatGod.service.UserService;

import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
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
    @ApiResponse(responseCode = "200", description = "Created the group",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GroupModel.class)) })
    @PostMapping
    public ResponseEntity<GroupModel> createGroup(@RequestBody GroupModel group) {
        GroupModel newGroup = groupService.createGroup(group.getName(), (Set<UserModel>) group.getMembers());
        return ResponseEntity.ok(newGroup);
    }

    // Add a member to a group
    @ApiResponse(responseCode = "200", description = "Added the member to the group",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GroupModel.class)) })
    @PostMapping("/{groupId}/members/{userId}")
    public ResponseEntity<GroupModel> addMemberToGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        UserModel user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        GroupModel updatedGroup = groupService.addMember(groupId, user);
        return ResponseEntity.ok(updatedGroup);
    }

    // Remove a member from a group
    @ApiResponse(responseCode = "200", description = "Removed the member from the group",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GroupModel.class)) })
    @DeleteMapping("/{groupId}/members/{userId}")
    public ResponseEntity<GroupModel> removeMemberFromGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        UserModel user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        GroupModel updatedGroup = groupService.removeMember(groupId, user);
        return ResponseEntity.ok(updatedGroup);
    }

    // Get a specific group by ID
    @ApiResponse(responseCode = "200", description = "Found the group",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GroupModel.class)) })
    @GetMapping("/{id}")
    public ResponseEntity<GroupModel> getGroupById(@PathVariable Long id) {
        return groupService.getGroupById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a group
    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Deleted the group")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.ok().build();
    }
}
