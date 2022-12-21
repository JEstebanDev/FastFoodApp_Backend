/**
 *
 */
package JEstebanC.FastFoodApp.service;

import JEstebanC.FastFoodApp.dto.EntireUserDTO;
import JEstebanC.FastFoodApp.dto.Mail;
import JEstebanC.FastFoodApp.dto.UserDTO;
import JEstebanC.FastFoodApp.dto.update.UserClientDTO;
import JEstebanC.FastFoodApp.dto.update.UserEmployeeDTO;
import JEstebanC.FastFoodApp.dto.validation.UserEmailDTO;
import JEstebanC.FastFoodApp.dto.validation.UsernameDTO;
import JEstebanC.FastFoodApp.enumeration.AppUserRole;
import JEstebanC.FastFoodApp.enumeration.Status;
import JEstebanC.FastFoodApp.model.User;
import JEstebanC.FastFoodApp.repository.IUserRepository;
import JEstebanC.FastFoodApp.security.OperationUtil;
import JEstebanC.FastFoodApp.service.interfaces.IUserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-02-05
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements IUserService, UserDetailsService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private final CloudinaryService cloudinaryService;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("User with username: " + username + " not found");
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        } else {
            if (user.getStatus().equals(Status.ACTIVE)) {
                log.info("User found " + username);
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(user.getUserRoles().getAuthority()));
                return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                        authorities);
            } else {
                log.error("User with username: " + username + " is inactive");
                throw new UsernameNotFoundException("User with username: " + username + " is inactive");
            }
        }
    }

    @Override
    public User create(User user, MultipartFile file) {
        log.info("Saving new user: " + user.getName());
        user.setIdUser(user.getIdUser());
        if (file != null) {
            try {
                user.setUrlImage(cloudinaryService.upload(file, "user"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        // Set automatically the user type Client
        user.setUserRoles(AppUserRole.ROLE_CLIENT);
        user.setDiscountPoint(0);
        user.setStatus(Status.ACTIVE);

        try {
            welcomeMessage(user.getName(),user.getEmail());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return userRepository.save(user);
    }

    public User createAdmin(User user, MultipartFile file) {
        log.info("Saving new user-admin: " + user.getName());
        user.setIdUser(user.getIdUser());
        if (file != null) {
            try {
                user.setUrlImage(cloudinaryService.upload(file, "user"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUserRoles(user.getUserRoles());
        user.setDiscountPoint(0);
        user.setStatus(Status.ACTIVE);
        return userRepository.save(user);
    }

    @Override
    public UserDTO update(Long id, User user, MultipartFile file) {
        log.info("Updating user with id: " + id);
        User userOld = userRepository.findByIdUser(id);
        userOld.setName(user.getName());
        userOld.setUsername(user.getUsername());
        userOld.setPhone(user.getPhone());
        userOld.setEmail(user.getEmail());
        if (user.getPassword() != null) {
            userOld.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userOld.setDiscountPoint(user.getDiscountPoint());
        if (file != null) {
            try {
                userOld.setUrlImage(cloudinaryService.upload(file, "user"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (user.getUrlImage() == null && file == null) {
            userOld.setUrlImage(null);
        }
        if (user.getUrlImage() != null) {
            userOld.setUrlImage(userOld.getUrlImage());
        }
        userOld.setStatus(user.getStatus());
        if (user.getUserRoles() != null) {
            userOld.setUserRoles(user.getUserRoles());
        }
        return convertUserToDTO(userRepository.save(userOld));
    }

    public UserDTO updateEmployee(UserEmployeeDTO userEmployeeDTO, long id) {
        log.info("Updating user with id: " + id);
        User userOld = userRepository.findByIdUser(id);
        userOld.setPhone((long) userEmployeeDTO.getPhone());
        userOld.setEmail(userEmployeeDTO.getEmail());
        return convertUserToDTO(userRepository.save(userOld));
    }

    public UserDTO updateClient(UserClientDTO userClientDTO, long id, MultipartFile file) {
        log.info("Updating user with id: " + id);
        User userOld = userRepository.findByIdUser(id);
        userOld.setName(userClientDTO.getName());
        userOld.setPhone(userClientDTO.getPhone());
        userOld.setEmail(userClientDTO.getEmail());
        if (userClientDTO.getPassword() != null) {
            userOld.setPassword(bCryptPasswordEncoder.encode(userClientDTO.getPassword()));
        }
        userOld.setStatus(userClientDTO.getStatus());
        userOld.setUrlImage(userClientDTO.getUrlImage());
        if (file != null) {
            try {
                userOld.setUrlImage(cloudinaryService.upload(file, "user"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (userClientDTO.getUrlImage() == null && file == null) {
            userOld.setUrlImage(null);
        }
        return convertUserToDTO(userRepository.save(userOld));
    }

    public boolean setDisableAccount(long id) {
        log.info("Updating user with id: " + id);
        User userOld = userRepository.findByIdUser(id);
        userOld.setStatus(Status.INACTIVE);
        return true;
    }

    public String validationToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(OperationUtil.keyValue().getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodeJWT = verifier.verify(token);
            return decodeJWT.getSubject();
        } catch (Exception e) {
            log.error("Error logging in AuthorizationFilter: " + e.getMessage());
        }
        return null;
    }

    public User updatePasswordClient(String username, String password) {
        log.info("Updating password username: " + username);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("User with username: " + username + " not found");
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(password));
            System.out.println(bCryptPasswordEncoder.encode(password));
        }
        return userRepository.save(user);
    }

    @Override
    public Boolean delete(Long iduser) {
        log.info("Deleting the user with id: " + iduser);
        if (userRepository.existsById(iduser)) {
            userRepository.deleteById(iduser);
            return true;
        } else {
            return false;
        }
    }

    public EntireUserDTO list(int page) {
        log.info("List all users");

        PageRequest pageRequest = PageRequest.of(page, 12);
        EntireUserDTO entity = new EntireUserDTO();
        Page<User> pages = userRepository.list(pageRequest);
        return convertPageToList(entity, pages);
    }

    public EntireUserDTO listAdmin(int page) {
        log.info("List all users especial");
        PageRequest pageRequest = PageRequest.of(page, 12);
        EntireUserDTO entity = new EntireUserDTO();
        Page<User> pages = userRepository.listUser(pageRequest);
        return convertPageToList(entity, pages);
    }

    public UserDTO getUser(String username) {
        return convertUserToDTO(userRepository.findByUsername(username));
    }

    private EntireUserDTO convertPageToList(EntireUserDTO entity, Page<User> pages) {
        List<UserDTO> listUserDTO = pages.getContent().stream().map(this::convertUserToDTO).collect(Collectors.toList());
        entity.setListUser(listUserDTO);
        int totalPage = pages.getTotalPages();
        if(totalPage > 0) {
            List<Integer> pagination = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
            entity.setPages(pagination);
        }
        return entity;
    }
    private UserDTO convertUserToDTO(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setIdUser(user.getIdUser());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setUrlImage(user.getUrlImage());
        userDto.setPhone(user.getPhone());
        userDto.setEmail(user.getEmail());
        userDto.setUserRoles(user.getUserRoles());
        userDto.setDiscountPoint(user.getDiscountPoint());
        userDto.setStatus(user.getStatus());
        return userDto;
    }

    @Override
    public Boolean exist(Long idUser) {
        log.info("Searching user by id: " + idUser);
        return userRepository.existsById(idUser);
    }

    public Boolean exist(String username) {
        log.info("Searching user by username: " + username);
        User user = userRepository.findByUsername(username);
        return user != null;
    }

    public Collection<UserDTO> findByName(String name) {
        log.info("Searching user by name: " + name);

        return userRepository.findByNameStartsWith(name + "%").stream().map(this::convertUserToDTO)
                .collect(Collectors.toList());

    }

    public Collection<UserDTO> findByNameAdmin(String name) {
        log.info("Searching user-admin by name: " + name);

        return userRepository.findByNameAdminStartsWith(name + "%").stream().map(this::convertUserToDTO)
                .collect(Collectors.toList());

    }

    public UserDTO findByEmail(String email) {
        log.info("Searching user by email: " + email);
        return userRepository.findByEmail(email) != null ? convertUserToDTO(userRepository.findByEmail(email)) : null;
    }

    public UserEmailDTO findByEmailValid(String email) {
        log.info("Searching if the email " + email + " is valid");
        return userRepository.findByEmail(email) != null ? convertUserEmailToDTO(userRepository.findByEmail(email))
                : null;
    }

    private UserEmailDTO convertUserEmailToDTO(User user) {
        UserEmailDTO userEmailDTO = new UserEmailDTO();
        userEmailDTO.setEmail(user.getEmail());
        return userEmailDTO;
    }

    public User findById(Long id) {
        log.info("Searching user by id: " + id);
        return userRepository.findByIdUser(id);
    }

    public User findByUsername(String username) {
        log.info("Searching user by username: " + username);
        return userRepository.findByUsername(username) != null ? userRepository.findByUsername(username) : null;
    }

    public UsernameDTO findByUsernameValidation(String username) {
        log.info("Searching user by username: " + username);
        return userRepository.findByUsername(username) != null
                ? convertUsernameToDTO(userRepository.findByUsername(username))
                : null;
    }

    private UsernameDTO convertUsernameToDTO(User user) {
        UsernameDTO usernameDTO = new UsernameDTO();
        usernameDTO.setUsername(user.getUsername());
        return usernameDTO;
    }

    public Boolean sendMail(HttpServletRequest request, String email, String userName,
                            String name) throws MessagingException {
        log.info("Sending mail");
        // Reference to the keyValue
        Algorithm algorithm = Algorithm.HMAC256(OperationUtil.keyValue().getBytes());
        String token = JWT.create().withSubject(userName)
                // give 10 minutes for the token to expire
                .withExpiresAt(new Date(System.currentTimeMillis() + 20 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString()).sign(algorithm);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        Mail mail = new Mail();
        mail.setFrom("pruebitas675@gmail.com");
        mail.setMailTo(email);//replace with your desired email
        mail.setSubject("Recuperar contraseña - Burger App");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", name);
        model.put("username", userName);
        //model.put("token", "http://localhost:4200/recover-password/" + token);
        model.put("token", "https://jefastfoodapp.netlify.app/recover-password/" + token);
        mail.setProps(model);

        Context context = new Context();
        context.setVariables(mail.getProps());

        String html = templateEngine.process("Email_Password", context);
        helper.setTo(mail.getMailTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());
        javaMailSender.send(message);
        return true;
    }

    public void welcomeMessage(String name,String email) throws MessagingException {
        log.info("Sending welcome message");
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        Mail mail = new Mail();
        mail.setFrom("pruebitas675@gmail.com");
        mail.setMailTo(email);
        mail.setSubject("Bienvenido/a - Burger App");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", name);
        mail.setProps(model);
        Context context = new Context();
        context.setVariables(mail.getProps());
        String html = templateEngine.process("Email_Welcome", context);
        helper.setTo(mail.getMailTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());
        javaMailSender.send(message);
    }

}
