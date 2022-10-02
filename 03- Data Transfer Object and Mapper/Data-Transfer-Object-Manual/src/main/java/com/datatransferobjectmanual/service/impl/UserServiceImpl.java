package com.datatransferobjectmanual.service.impl;

import com.datatransferobjectmanual.entity.User;
import com.datatransferobjectmanual.repository.UserRepository;
import com.datatransferobjectmanual.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @Created 02/10/2022 - 10:37
 * @Package com.datatransferobjectmanual.service.impl
 * @Project Data-Transfer-Object-Manual
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Transactional
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        if (Objects.isNull(id)) {
            throw new RuntimeException("user id can't be null");
        }
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new RuntimeException("user can not be found!");
        }
        return user.get();
    }

    @Override
    public User findByUsername(String username) {
        if (Objects.isNull(username)) {
            throw new RuntimeException("username can't be null");
        }
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        if (Objects.isNull(email)) {
            throw new RuntimeException("email can't be null");
        }
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        User getUserByUsername = findByUsername(user.getUsername());
        if (Objects.nonNull(getUserByUsername)) {
            throw new RuntimeException("user already exist!");

        }

        User getUserByEmail = findByEmail(user.getEmail());
        if (Objects.nonNull(getUserByEmail)) {
            throw new RuntimeException("user already exist!");

        }
        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        User getUserByUsername = findById(id);
        getUserByUsername.setFirstName(getUserByUsername.getFirstName());
        getUserByUsername.setLastName(getUserByUsername.getLastName());
        getUserByUsername.setUsername(getUserByUsername.getUsername());
        getUserByUsername.setEmail(getUserByUsername.getEmail());
        getUserByUsername.setPassword(getUserByUsername.getPassword());

        User updatedUser = userRepository.save(getUserByUsername);

        return updatedUser;
    }

    @Override
    public void delete(long id) {
        User getUserByUsername = findById(id);
        userRepository.delete(getUserByUsername);
    }
}
