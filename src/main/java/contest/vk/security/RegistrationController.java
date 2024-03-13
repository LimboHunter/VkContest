package contest.vk.security;


import contest.vk.data.UserRepository;
import contest.vk.model.User;
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

        log.info(String.valueOf(user));
        userRepo.save(user);

        return ResponseEntity.ok("You now have an account!");
    }

}
