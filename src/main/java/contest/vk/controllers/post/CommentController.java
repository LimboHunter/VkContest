package contest.vk.controllers.post;

import contest.vk.model.Comment;
import contest.vk.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final PostService postService;

    @Autowired
    public CommentController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long id) {
        List<Comment> comments = postService.getCommentsByPostId(id);

        return Optional.ofNullable(comments)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/comments")
    public ResponseEntity<List<Comment>> getCommentsByPostIdQueryParam(@RequestParam Long postId) {
        List<Comment> comments = postService.getCommentsByPostIdQueryParam(postId);

        return Optional.ofNullable(comments)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}