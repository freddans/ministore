package com.freddan.ministore.services;

import com.freddan.ministore.entities.User;
import com.freddan.ministore.repositories.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final Logger logger = Logger.getLogger(UserService.class);
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {

            return optionalUser.get();
        } else {

            logger.error("\nERROR: User with provided ID does not exist.\n" +
                    "Provided ID: " + id + "\n");
            return null;
        }
    }

    public User findUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {

            return optionalUser.get();
        } else {

            logger.error("\nERROR: User with provided Username does not exist.\n" +
                    "Provided Username: " + username + "\n");
            return null;
        }
    }

    public User createUser(User newUserInfo) {
        User existingUser = findUserByUsername(newUserInfo.getUsername());

        if (existingUser == null) {

            if (newUserInfo.getUsername() != null && !newUserInfo.getUsername().isEmpty() && newUserInfo.getPassword() != null && !newUserInfo.getPassword().isEmpty()) {

                if (newUserInfo.getRoles() != null && !newUserInfo.getRoles().isEmpty()) {
                    if (newUserInfo.getRoles().equals("USER") || newUserInfo.getRoles().equals("ADMIN")) {

                        newUserInfo.setPassword(passwordEncoder.encode(newUserInfo.getPassword()));

                        User newUser = new User(newUserInfo.getUsername(), newUserInfo.getPassword(), newUserInfo.getRoles());

                        userRepository.save(newUser);

                        return newUser;
                    } else {

                        logger.error("\nERROR: New Users role was not set to USER nor ADMIN.\n" +
                                "Provided role: " + newUserInfo.getRoles() + "\n");
                        return null;
                    }

                } else {
                    newUserInfo.setPassword(passwordEncoder.encode(newUserInfo.getPassword()));

                    User newUser = new User(newUserInfo.getUsername(), newUserInfo.getPassword());
                    newUser.setRoles("USER");

                    userRepository.save(newUser);

                    return newUser;
                }


            } else {

                logger.error("\nERROR: Username or Password is either null och empty.\n");
                return null;
            }
        } else {

            logger.error("\nERROR: Username already exist\n");
            return null;
        }
    }

    public User updateUser(long id, User updatedUserInfo) {
        User existingUser = findUserById(id);

        if (existingUser == null) {
            logger.error("\nERROR: User with provided ID does not exist.\n" +
                    "Provided ID: " + id + "\n");
            return null;
        }

        // Update username if provided and not empty
        if (updatedUserInfo.getUsername() != null && !updatedUserInfo.getUsername().isEmpty() &&
                !updatedUserInfo.getUsername().equals(existingUser.getUsername())) {
            existingUser.setUsername(updatedUserInfo.getUsername());
        }

        // Update roles if provided, not empty, and valid
        if (updatedUserInfo.getRoles() != null && !updatedUserInfo.getRoles().isEmpty() &&
                !updatedUserInfo.getRoles().equals(existingUser.getRoles())) {
            if (updatedUserInfo.getRoles().equals("USER") || updatedUserInfo.getRoles().equals("ADMIN")) {
                existingUser.setRoles(updatedUserInfo.getRoles());
            } else {
                logger.error("\nERROR: New User's role was not set to USER nor ADMIN.\n" +
                        "Provided role: " + updatedUserInfo.getRoles() + "\n");
                return null; // Optionally, return error here if role is invalid
            }
        }

        // Update password if provided and not empty
        if (updatedUserInfo.getPassword() != null && !updatedUserInfo.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUserInfo.getPassword()));
        }

        userRepository.save(existingUser);

        return existingUser;
    }

    public String deleteUser(long id) {
        User existingUser = findUserById(id);

        if (existingUser != null) {

            userRepository.delete(existingUser);
            return "Deleted User with ID: " + id;
        } else {

            logger.error("\nERROR: User with provided ID does not exist.\n" +
                    "Provided ID: " + id + "\n");
            return "ERROR: User does not exist";
        }
    }


}
