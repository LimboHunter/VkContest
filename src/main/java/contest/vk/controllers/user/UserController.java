package contest.vk.controllers.user;

import contest.vk.model.Album;
import contest.vk.model.Post;
import contest.vk.model.User;
import contest.vk.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.getAllUsers();

        return Optional.ofNullable(users)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUser(id);

        return Optional.ofNullable(user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/users/{id}/posts")
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable("id") Long userId) {
        List<Post> posts = userService.getPostsByUserId(userId);

        return Optional.ofNullable(posts)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/users/{id}/albums")
    public ResponseEntity<List<Album>> getAlbumsByUserId(@PathVariable("id") Long userId) {
        List<Album> albums = userService.getAlbumsByUserId(userId);

        return Optional.ofNullable(albums)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        ResponseEntity<User> responseEntity = userService.createUser(user);

        return Optional.of(responseEntity)
                .filter(res -> res.getStatusCode() == HttpStatus.CREATED)
                .map(res -> ResponseEntity.ok(res.getBody()))
                .orElse(ResponseEntity.status(responseEntity.getStatusCode()).build());
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        ResponseEntity<User> responseEntity = userService.updateUser(id, user);

        return Optional.ofNullable(responseEntity.getBody())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User with Id=" + id + " has been successfully deleted");
    }

}