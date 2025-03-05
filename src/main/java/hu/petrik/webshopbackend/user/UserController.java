package hu.petrik.webshopbackend.user;

import hu.petrik.webshopbackend.Exceptions.UserNotFoundException;
import hu.petrik.webshopbackend.auth.JwtUtil;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    private JwtUtil jwtUtil;
    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }



    @GetMapping()
    public List<User> getUsers() {
        return userService.findAll();
    }

    @PostMapping("register")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

   @DeleteMapping("deleteUser/{id}")
   public List<User> deleteUser(@PathVariable Long id) {
        return userService.deleteById(id);
   }

   @PatchMapping("update/{id}")
   public User updateUser(@PathVariable Long id, @RequestBody Map<String, Object> user) {
        return userService.updateUser(id,user);
   }

    /*
    @PostMapping(path = "/login", consumes = "application/json")
    @ResponseBody
    public User login( @RequestBody UserLoginDto user) {
        try{
            return userService.login(user.getEmail(), user.getPassword());
        }
        catch(UserNotFoundException e){
            throw new UserNotFoundException("User not found");
        }

    }
     */

    @PostMapping(path = "/login", consumes = "application/json")
    @ResponseBody
    public ResponseEntity login(@RequestBody UserLoginDto loginReq)  {

        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
            String email = authentication.getName();
            User user = new User(email,"");
            String token = jwtUtil.createToken(user);
            LoginRes loginRes = new LoginRes(email,token);

            return ResponseEntity.ok(loginRes);

        }catch (BadCredentialsException e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (Exception e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }



    @GetMapping("{id}")
    public User findById(@PathVariable long id) {
        return userService.findById(id);
    }

    @GetMapping("admin")
    public String adminPage(@RequestHeader String role) {
        return userService.adminPage(role);
    }



}
