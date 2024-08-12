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
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(RegistrationForm form) {
        User user = new User();
        user.setUsername(form.getUsername());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
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

    public Optional<User> findStudentById(Long id) {
        return userRepository.findById(id);
    }

    public void createStudent(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // Encode the initial password
        user.setRole("STUDENT");
        user.setActive(true);
        userRepository.save(user);
    }

    public void updateStudent(User user) {
        Optional<User> existingUser = userRepository.findById(user.getId());
        if (existingUser.isPresent()) {
            User updateUser = existingUser.get();
            updateUser.setEmail(user.getEmail());
            updateUser.setFullName(user.getFullName());
            // Update other fields as needed (excluding personal information)
            userRepository.save(updateUser);
        }
    }

    public void deleteStudent(Long id) {
        userRepository.deleteById(id);
    }

    public void resetPassword(Long id, String newPassword) {
        Optional<User> user = userRepository.findById(id);
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
