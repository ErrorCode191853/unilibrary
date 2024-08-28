package com.khoi.unilibrary.service;

import com.khoi.unilibrary.dto.UserPayload;
import com.khoi.unilibrary.model.Role;
import com.khoi.unilibrary.model.User;
import com.khoi.unilibrary.repository.PasswordResetTokenRepository;
import com.khoi.unilibrary.repository.RoleRepository;
import com.khoi.unilibrary.repository.UserRepository;
import com.khoi.unilibrary.token.PasswordResetToken;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Getter
@Setter
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordResetTokenRepository passwordResetTokenRepository,
                           EmailService emailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.emailService = emailService;
    }

    @Transactional
    public String createPasswordResetToken(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return null;
        }

        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(passwordResetToken);

        return token;
    }

    public void sendPasswordResetEmail(String email, String resetUrl) {
        String subject = "Password Reset Request";
        String message = "To reset your password, click the link below:\n" + resetUrl;

        emailService.sendSimpleMessage(email, subject, message);
    }

    @Override
    public Page<User> getAllUsers(Authentication authentication, String keyword, String roleName, int page, int size, String[] sort) {
        var currentUser = userRepository.findByEmail(authentication.getName());
        var currentUserRoles = currentUser.getRoles().stream()
                .map(Role::getName)
                .toList();

        var sortField = sort[0];
        var sortDirection = sort[1];
        var direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        var order = new Sort.Order(direction, sortField);

        Pageable paging = PageRequest.of(page - 1, size, Sort.by(order));

        Page<User> pageUsers;

        var roleMember = roleRepository.findByName("MEMBER");

        Role role = null;

        if (keyword != null && roleName != null && !roleName.equals("All roles")) {
            role = currentUserRoles.contains("ADMIN") ? roleRepository.findByName(roleName) : roleMember;
            pageUsers = userRepository.findByRolesEqualsAndEmailContainingIgnoreCase(role, keyword, paging);
        } else if (roleName != null && !roleName.equals("All roles")) {
            role = currentUserRoles.contains("ADMIN") ? roleRepository.findByName(roleName) : roleMember;
            pageUsers = userRepository.findByRolesEquals(role, paging);
        } else if (keyword != null) {
            if (currentUserRoles.contains("ADMIN")) {
                pageUsers = userRepository.findByEmailContainingOrFirstNameContainingOrLastNameContainingAllIgnoreCase(keyword, keyword, keyword, paging);
            } else {
                pageUsers = userRepository.findByRolesEqualsAndEmailContainingIgnoreCase(roleMember, keyword, paging);
            }
        } else {
            pageUsers = currentUserRoles.contains("ADMIN") ? userRepository.findAll(paging) : userRepository.findByRolesEquals(roleMember, paging);
        }

        return pageUsers;
    }

    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public User getUser(Integer id) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var currentUser = userRepository.findByEmail(authentication.getName());
        var currentUserRoles = currentUser.getRoles().stream()
                .map(Role::getName)
                .toList();

        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }

        var userRoles = user.getRoles().stream()
                .map(Role::getName)
                .toList();

        if (currentUserRoles.contains("LIBRARIAN") && (userRoles.contains("ADMIN") || userRoles.contains("LIBRARIAN"))) {
            return null;
        }

        return user;
    }

    @Override
    public User currentUserDetails() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName());
    }

    @Override
    public void deleteUserById(Integer id) {
        userRepository.findById(id).ifPresent(userRepository::delete);
    }

    @Override
    public User createUser(UserPayload userPayload) throws ParseException {
        var bCryptPasswordEncoder = new BCryptPasswordEncoder();

        var user = new User();
        user.setFirstName(userPayload.getFirstName());
        user.setLastName(userPayload.getLastName());
        user.setEmail(userPayload.getEmail());
        user.setContactNumber(userPayload.getContactNumber());

        var roleUser = roleRepository.findByName(userPayload.getUserRole());
        Set<Role> roles = new HashSet<>();
        roles.add(roleUser);
        user.setRoles(roles);

        if (userPayload.getPassword() != null) {
            var encodedPassword = bCryptPasswordEncoder.encode(userPayload.getPassword());
            user.setPassword(encodedPassword);
        }

        var date = userPayload.getDateOfBirth();
        var timestamp = new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(date.toString()).getTime());
        user.setDateOfBirth(timestamp);

        user.setEnabled(true);

        return userRepository.save(user);
    }

    @Override
    public User editUser(Integer id, UserPayload userPayload) throws ParseException {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }

        user.setFirstName(userPayload.getFirstName());
        user.setLastName(userPayload.getLastName());
        user.setEmail(userPayload.getEmail());
        user.setContactNumber(userPayload.getContactNumber());

        var roleUser = roleRepository.findByName(userPayload.getUserRole());
        Set<Role> roles = new HashSet<>();
        roles.add(roleUser);
        user.setRoles(roles);

        if (userPayload.getPassword() != null) {
            var bCryptPasswordEncoder = new BCryptPasswordEncoder();
            var encodedPassword = bCryptPasswordEncoder.encode(userPayload.getPassword());
            user.setPassword(encodedPassword);
        }

        var date = userPayload.getDateOfBirth();
        var timestamp = new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(date.toString()).getTime());
        user.setDateOfBirth(timestamp);

        return userRepository.save(user);
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user){
        return user;
    }
}
