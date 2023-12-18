package test.elias.ChatGod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.elias.ChatGod.model.GroupModel;
import test.elias.ChatGod.model.UserModel;
import test.elias.ChatGod.repository.GroupRepository;

import java.util.Optional;
import java.util.Set;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    // Create a new group
    public GroupModel createGroup(String groupName, Set<UserModel> members) {
        GroupModel group = new GroupModel();
        group.setName(groupName);
        group.setMembers(members);
        return groupRepository.save(group);
    }

    // Add a member to a group
    public GroupModel addMember(Long groupId, UserModel user) {
        GroupModel group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        group.getMembers().add(user);
        return groupRepository.save(group);
    }

    // Remove a member from a group
    public GroupModel removeMember(Long groupId, UserModel user) {
        GroupModel group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        group.getMembers().remove(user);
        return groupRepository.save(group);
    }

    // Find a group by ID
    public Optional<GroupModel> getGroupById(Long groupId) {
        return groupRepository.findById(groupId);
    }

    // Delete a group
    public void deleteGroup(Long groupId) {
        groupRepository.deleteById(groupId);
    }
}
