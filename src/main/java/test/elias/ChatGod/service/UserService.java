package test.elias.ChatGod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.elias.ChatGod.exception.UserNotFoundException;
import test.elias.ChatGod.model.UserModel;
import test.elias.ChatGod.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Retrieve all users from the database
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    // Find a user by ID
    public Optional<UserModel> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Create a new user with validation and potential default settings
    public UserModel createUser(UserModel user) {
        // Add validation logic here
        return userRepository.save(user);
    }

    // Update an existing user
    @Transactional
    public UserModel updateUser(Long id, UserModel userDetails) {
        return userRepository.findById(id)
                .map(existingUser -> {

                    // Update the existing user's fields with userDetails
                    existingUser.updateWith(userDetails);
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    // Delete a user by ID
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    public UserModel updateUserStatus(Long userId, UserModel.UserStatus newStatus) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setStatus(newStatus);
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
