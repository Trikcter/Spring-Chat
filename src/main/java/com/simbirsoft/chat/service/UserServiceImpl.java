package com.simbirsoft.chat.service;

import com.simbirsoft.chat.DAO.UserRepository;
import com.simbirsoft.chat.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUser(Users user) {
        userRepository.save(user);
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Users getByusername(String username) {
        return null;
    }

    @Override
    public Users editUser(Users user) {
        return null;
    }

    @Override
    public List<Users> getAll() {
        return null;
    }
}
