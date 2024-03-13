package contest.vk.controllers.user;

import contest.vk.model.Todo;
import contest.vk.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TodoController {

    private final UserService userService;

    @Autowired
    public TodoController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}/todos")
    public ResponseEntity<List<Todo>> getTodosByUserId(@PathVariable("id") Long userId) {
        List<Todo> todos = userService.getTodosByUserId(userId);

        return Optional.ofNullable(todos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
