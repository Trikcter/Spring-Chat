package com.simbirsoft.chat.service;

import com.simbirsoft.chat.DAO.UserRepository;
import com.simbirsoft.chat.entity.Role;
import com.simbirsoft.chat.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUser(User user) {
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public User getByUsername(String username) {
        return null;
    }

    @Override
    public User editUser(User user) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User save(User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(true);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
}
