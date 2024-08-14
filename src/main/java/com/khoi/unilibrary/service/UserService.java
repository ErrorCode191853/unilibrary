package com.khoi.unilibrary.service;

import com.khoi.unilibrary.dto.RegistrationForm;
import com.khoi.unilibrary.entity.User;
import com.khoi.unilibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(RegistrationForm form) {
        User user = new User();
        user.setUsername(form.getUsername());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setEmail(form.getEmail());
        user.setRole("STUDENT"); // Assign role based on logic
        userRepository.save(user);
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public List<User> findAllStudents() {
        return userRepository.findByRole("STUDENT");
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        return user;
    }

    public Optional<User> findStudentById(Long userId) {
        return userRepository.findById(userId);
    }

    public void createStudent(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // Encode the initial password
        user.setRole("STUDENT");
        user.setActive(true);
        userRepository.save(user);
    }

    public void updateStudent(User user) {
        Optional<User> existingUser = userRepository.findById(user.getUserId());
        if (existingUser.isPresent()) {
            User updateUser = existingUser.get();
            updateUser.setEmail(user.getEmail());
            updateUser.setFullName(user.getFullName());
            // Update other fields as needed (excluding personal information)
            userRepository.save(updateUser);
        }
    }

    public void deleteStudent(Long userId) {
        userRepository.deleteById(userId);
    }

    public void resetPassword(Long userId, String newPassword) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User resetUser = user.get();
            resetUser.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(resetUser);
        }
    }
    public long countStudents() {
        return userRepository.countByRole("STUDENT");
    }

    public long countStaff() {
        return userRepository.countByRole("STAFF");
    }
}
