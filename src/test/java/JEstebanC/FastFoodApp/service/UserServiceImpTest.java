package JEstebanC.FastFoodApp.service;

import JEstebanC.FastFoodApp.dto.UserDTO;
import JEstebanC.FastFoodApp.dto.update.UserClientDTO;
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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {
    @Mock
    private IUserRepository userRepository;
    @Spy
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
        user.setName("Adrian");
        user.setUsername("AdrianMan");
        user.setPassword("aPassWord");
        user.setPhone(123456789L);
        user.setEmail("super_hyper_duper_email_address@chelsea.sport");
        user.setUserRoles(AppUserRole.ROLE_CLIENT);
        user.setDiscountPoint(0);
        user.setStatus(Status.ACTIVE);
        authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getUserRoles().getAuthority()));
        userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @AfterEach
    void Destroy() throws Exception {
        autoCloseable.close();
    }

    @Test
    @DisplayName("USER loadUserByUserName() does not return null")
    void loadUserByUsernameReturnsNotNull() {
        when(userRepository.findByUsername(any(String.class))).thenReturn(user);
        assertNotNull(userServiceImp.loadUserByUsername(user.getUsername()));
    }

    @Test
    @DisplayName("USER loadUserByUserName() returns right user")
    void loadUserByUserNameNameReturnsCorrectUser() {
        when(userRepository.findByUsername(any(String.class))).thenReturn(user);
        assertEquals(userDetails, userServiceImp.loadUserByUsername(user.getUsername()));
    }

    @Test
    @DisplayName("USER loadUserByUserName() throws exception when user is null")
    void loadUserByUserNameThrowsExceptionWhenUserIsNull() {
        when(userRepository.findByUsername(any(String.class))).thenReturn(null);
        //username is got using String to avoid two possible exceptions
        assertThrows(UsernameNotFoundException.class, () -> userServiceImp.loadUserByUsername("Adrian"));
    }

    @Test
    @DisplayName("USER create() returns correctly modified User to original preset")
    void createModifiesUserToPreSet() {
        User user2 = new User();
        when(userRepository.save(any(User.class))).thenReturn(user);
        user2 = userServiceImp.create(user, null);
        assertSame(user.getStatus(), user2.getStatus());
        assertSame(user.getUserRoles(), user2.getUserRoles());
        assertSame(user.getDiscountPoint(), user2.getDiscountPoint());
        assertSame(60, user2.getPassword().length());
    }

    @Test
    @DisplayName("USER update() changes all User properties")
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

        when(userRepository.findByIdUser(any(Long.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDTO usrDto = userServiceImp.update(user.getIdUser(), newUser, null);
        assertSame("Julian", usrDto.getName());
        assertSame("JulianHandsome", usrDto.getUsername());
        assertTrue(usrDto.getPhone().equals(956987456L));
        assertSame("julianthehappyguy@nsa.gov", usrDto.getEmail());
        //comparing with updated User, as UserDTO has no getter for password.
        assertSame(60, user.getPassword().length());
        assertSame(123, usrDto.getDiscountPoint());
        assertSame(Status.INACTIVE, usrDto.getStatus());
        assertSame(newUser.getUrlImage(), usrDto.getUrlImage());
        //assertSame(AppUserRole.ROLE_CLIENT, usrDto.getUserRoles());
    }

    @Test
    @DisplayName("USER validationToken returns null when token is null")
    void validationTokenThrowsExceptionWhenTokenIsNull() {
        assertNull(userServiceImp.validationToken(null));
    }

    @Test
    @DisplayName("USER updateClient() changes  all user properties")
    void updateClientUpdatesUserClient() {
        UserClientDTO oldUserClientDTO = new UserClientDTO();
        user.setPassword("aPassWord");
        oldUserClientDTO.setName(user.getName());
        oldUserClientDTO.setUsername(user.getUsername());
        oldUserClientDTO.setPhone(user.getPhone());
        oldUserClientDTO.setEmail(user.getEmail());
        oldUserClientDTO.setPassword(user.getPassword());
        oldUserClientDTO.setStatus(user.getStatus());
        when(userRepository.findByIdUser(any(Long.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        String expectedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        UserDTO newUserDTO = userServiceImp.updateClient(oldUserClientDTO, user.getIdUser(), null);
        assertSame(newUserDTO.getName(), oldUserClientDTO.getName());
        assertSame(newUserDTO.getUsername(), oldUserClientDTO.getUsername());
        assertSame(newUserDTO.getPhone(), oldUserClientDTO.getPhone());
        assertSame(newUserDTO.getEmail(), oldUserClientDTO.getEmail());
        assertSame(newUserDTO.getStatus(), oldUserClientDTO.getStatus());
    }

}

