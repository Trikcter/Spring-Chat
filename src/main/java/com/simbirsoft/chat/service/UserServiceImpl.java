package com.simbirsoft.chat.service;

import com.simbirsoft.chat.DAO.UserRepository;
import com.simbirsoft.chat.entity.Role;
import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUser(UserDTO userDTO) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(true);
        user.setUsername(userDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode("user"));

        userRepository.save(user);
    }

    @Override
    public void delete(String username) {
        User delUser = userRepository.findByUsername(username);

        userRepository.delete(delUser);
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
    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        List<UserDTO> answer = new ArrayList<>();

        for(User user: users){
            UserDTO userDTO = new UserDTO();

            userDTO.setActive(user.getActive());
            userDTO.setUsername(user.getUsername());

            List<Role> roles = new ArrayList<>();

            for(Role role : user.getRoles()){
                roles.add(role);
            }

            userDTO.setRoleSet(roles);

            answer.add(userDTO);
        }

        return answer;
    }

    @Override
    public User save(User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(true);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public void makeModerator(String username) {
        User user = userRepository.findByUsername(username);
        user.addRole(Role.MODERATOR);

        userRepository.save(user);
    }

    @Override
    public void removeModerator(String username) {
        User user = userRepository.findByUsername(username);
        Set<Role> roleSet = user.getRoles();

        roleSet.remove(Role.MODERATOR);

        user.setRoles(roleSet);

        userRepository.save(user);
    }

    @Override
    public void blockUser(String username) {
        User user = userRepository.findByUsername(username);
        user.setActive(false);

        userRepository.save(user);
    }

    @Override
    public void unBlockUser(String username) {
        User user = userRepository.findByUsername(username);
        user.setActive(true);

        userRepository.save(user);
    }
}
