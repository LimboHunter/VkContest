package contest.vk.security;


import contest.vk.data.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class RegistrationController {

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/registration")
    public ResponseEntity<String> registerUser(@RequestBody User user) {

        if(userRepo.findByUsername(user.getUsername()).isPresent()) return ResponseEntity.badRequest().body("Please choose another username, this one is occupied(");

        userRepo.save(user);

        return ResponseEntity.ok("You now have an account!");
    }

}
