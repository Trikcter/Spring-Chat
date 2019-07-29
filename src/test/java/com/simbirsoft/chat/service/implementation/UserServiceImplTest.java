package com.simbirsoft.chat.service.implementation;

import com.simbirsoft.chat.DAO.UserRepository;
import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Before
    public void beforeTest(){
        User user = new User();
        user.setUsername("TestUser");

        Mockito.when(userRepository.findByUsername("TestUser")).thenReturn(Optional.of(user));
    }

    @Test
    public void getUserByUsernameTest(){
        Optional<User> testUser = userService.getByUsername("TestUser");

        Assert.assertEquals("TestUser",testUser.get().getUsername());
    }
}
