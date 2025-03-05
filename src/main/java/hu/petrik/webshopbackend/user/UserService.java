package hu.petrik.webshopbackend.user;

import hu.petrik.webshopbackend.Exceptions.UserNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
       return userRepository.save(user);
    }

    public User login(String email, String password) {

        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent() ) {
           if(passwordEncoder.matches(password, userOptional.get().getPassword())) {
                return userOptional.get();
           }
           else {
               throw new UserNotFoundException("User not found"); //asd
           }
       }
       else {
           throw new UserNotFoundException("User not found");
       }

    }

    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public String adminPage(String role) {
        if(role.equals("admin")) {
            return "Access Granted";
        }
        else
        {
            return "Access Denied";
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        UserDetails userDetails =
                org.springframework.security.core.userdetails.User.builder()
                        .username(user.get().getEmail())
                        .password(user.get().getPassword())
                        .roles(roles.toArray(new String[0]))
                        .build();
        return userDetails;
    }


    public List<User> deleteById(Long id) {
        userRepository.findById(id).ifPresent(userRepository::delete);
        return userRepository.findAll();
    }

    public User updateUser(long id, Map<String, Object> fields) {
        Optional<User> existingProduct = userRepository.findById(id);

        if (existingProduct.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(User.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingProduct.get(), value);
            });
            return userRepository.save(existingProduct.get());
        }
        return null;
    }

}

