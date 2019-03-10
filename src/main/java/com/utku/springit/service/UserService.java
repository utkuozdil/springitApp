package com.utku.springit.service;

import com.utku.springit.domain.User;
import com.utku.springit.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    public UserService(UserRepository userRepository, RoleService roleService, MailService mailService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.mailService = mailService;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        ;
    }

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RoleService roleService;

    private final MailService mailService;


    public User register(User user) {
        String secret = "{bcrypt}" + bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(secret);
        user.setConfirmPassword(secret);
        user.addRole(roleService.findByName("ROLE_USER"));
        user.setEnabled(false);
        user.setActivationCode(UUID.randomUUID().toString());
        save(user);
        sendActivationEmail(user);
        return user;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void SaveUsers(User... users) {
        for (User user : users) {
            logger.info("saving user: " + user.getEmail());
            userRepository.save(user);
        }
    }

    public void sendActivationEmail(User user) {
        mailService.sendActivationEmail(user);
    }

    public void sendWelcomeEmail(User user) {
        mailService.sendWelcomeEmail(user);
    }

    public Optional<User> findByEmailAndActivationCode(String email, String activationCode) {
        return userRepository.findByEmailAndActivationCode(email, activationCode);
    }
}
