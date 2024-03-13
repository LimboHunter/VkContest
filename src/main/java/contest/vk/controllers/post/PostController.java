package contest.vk.controllers.post;

import contest.vk.model.Post;
import contest.vk.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAll(@RequestParam(required = false) Long userId) {
        List<Post> posts = postService.getAllPosts(userId);

        return Optional.ofNullable(posts)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()); // Возвращаем результат клиенту
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        Post post = postService.getPost(id);

        return Optional.ofNullable(post)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        ResponseEntity<Post> responseEntity = postService.createPost(post);

        return Optional.of(responseEntity)
                .filter(res -> res.getStatusCode() == HttpStatus.CREATED)
                .map(res -> ResponseEntity.ok(res.getBody()))
                .orElse(ResponseEntity.status(responseEntity.getStatusCode()).build());
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
        ResponseEntity<Post> responseEntity = postService.updatePost(id, post);

        return Optional.ofNullable(responseEntity.getBody())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Post with Id=\" + id + \" has been successfully deleted");
    }

}