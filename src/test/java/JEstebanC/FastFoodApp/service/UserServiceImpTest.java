package JEstebanC.FastFoodApp.service;

import JEstebanC.FastFoodApp.dto.UserDTO;
import JEstebanC.FastFoodApp.enumeration.AppUserRole;
import JEstebanC.FastFoodApp.enumeration.Status;
import JEstebanC.FastFoodApp.model.User;
import JEstebanC.FastFoodApp.repository.IUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {
    @Mock
    private IUserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @InjectMocks
    private UserServiceImp userServiceImp;

    private UserDetails userDetails;
    @Mock
    Collection<SimpleGrantedAuthority> authorities;
    private User user;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {

        autoCloseable = MockitoAnnotations.openMocks(this);
        user = new User();
        user.setIdUser(29L);
        user.setUsername("Adrian");
        user.setPassword("aPassWord");
        user.setUserRoles(AppUserRole.ROLE_CLIENT);
        user.setDiscountPoint(0);
        user.setStatus(Status.ACTIVE);
        authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getUserRoles().getAuthority()));
        userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);
    }

    @AfterEach
    void Destroy() throws Exception {
        autoCloseable.close();
    }

    @Test
    @DisplayName("loadUserByUserName() does not return null")
    void loadUserByUsernameReturnsNotNull() {
        when(userRepository.findByUsername(any(String.class))).thenReturn(user);
        assertNotNull(userServiceImp.loadUserByUsername(user.getUsername()));
    }

    @Test
    @DisplayName("loadUserByUserName() returns right user")
    void loadUserByUserNameNameReturnsCorrectUser() {
        when(userRepository.findByUsername(any(String.class))).thenReturn(user);
        assertEquals(userDetails, userServiceImp.loadUserByUsername(user.getUsername()));
    }

    @Test
    @DisplayName("loadUserByUserName() throws exception when user is null")
    void loadUserByUserNameThrowsExceptionWhenUserIsNull() {
        when(userRepository.findByUsername(any(String.class))).thenReturn(null);
        //username is got using String to avoid two possible exceptions
        assertThrows(UsernameNotFoundException.class, () -> userServiceImp.loadUserByUsername("Adrian"));
    }

    @Test
    @DisplayName("create() returns correctly modified User to original preset")
    void createModifiesUserToPreSet() {
        User user2 = new User();
        user2.setIdUser(100L);
        user2.setName("Peter");
        //user.setPassword("aPassWord");
        String pass = bCryptPasswordEncoder.encode(user.getPassword());
        when(userRepository.save(any(User.class))).thenReturn(user2);
        userServiceImp.create(user2, null);
        assertSame(Status.ACTIVE, user2.getStatus());
        assertSame(AppUserRole.ROLE_CLIENT, user2.getUserRoles());
        assertSame(0, user2.getDiscountPoint());
        assertSame(pass, user2.getPassword());
    }

    @Test
    @DisplayName("update() changes all User properties")
    void updateChangesUserProperties() {
        User newUser = new User();
        newUser.setName("Julian");
        newUser.setUsername("JulianHandsome");
        newUser.setPhone(956987456L);
        newUser.setEmail("julianthehappyguy@nsa.gov");
        newUser.setPassword("aPassWordForJulian");
        newUser.setDiscountPoint(123);
        newUser.setStatus(Status.INACTIVE);
        newUser.setUserRoles(AppUserRole.ROLE_CLIENT);

        String encodedPass = bCryptPasswordEncoder.encode(newUser.getPassword());

        when(userRepository.findByIdUser(any(Long.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDTO usrDto = userServiceImp.update(user.getIdUser(), newUser, null);
        assertSame("Julian", usrDto.getName());
        assertSame("JulianHandsome", usrDto.getUsername());
        assertTrue(usrDto.getPhone().equals(956987456L));
        assertSame("julianthehappyguy@nsa.gov", usrDto.getEmail());
        //comparing with updated User, as UserDTO has no getter for password.
        assertSame(encodedPass, user.getPassword());
        assertSame(123, usrDto.getDiscountPoint());
        assertSame(Status.INACTIVE, usrDto.getStatus());
        assertSame(newUser.getUrlImage(), usrDto.getUrlImage());
        //assertSame(AppUserRole.ROLE_CLIENT, usrDto.getUserRoles());
    }
}