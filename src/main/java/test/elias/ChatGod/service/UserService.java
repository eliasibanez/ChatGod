package test.elias.ChatGod.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.elias.ChatGod.exception.RegisterException;
import test.elias.ChatGod.exception.UserNotFoundException;
import test.elias.ChatGod.model.UserModel;
import test.elias.ChatGod.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
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
        if(userRepository.existsById(user.getId())) return null;

        return userRepository.save(user);
    }

    public UserModel authenticate(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password).orElse(null);
    }
    public UserModel register(String username, String password, String email) {
        logger.debug("Registering user with username: " + username + " and email: " + email);
        UserModel user = new UserModel(username, email, password);
        logger.debug("UserModel created: " + user.toString());
        if(userRepository.findByUsername(username).isPresent() || userRepository.findByEmail(email).isPresent()) throw new RegisterException("Username or email already exists!");


        return userRepository.save(user);
    }

    // Update an existing user
    @Transactional
    public UserModel updateUser(Long id, UserModel userDetails) {
        return userRepository.findById(id)
                .map(existingUser -> {

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
