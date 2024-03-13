package contest.vk.controllers;

import contest.vk.data.PostRepository;
import contest.vk.data.UserRepository;
import contest.vk.model.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    PostRepository postRepo;

    @Autowired
    UserRepository userRepo;

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts(){
        Iterable<Post> it = postRepo.findAll();

        List<Post> posts = new ArrayList<>();

        it.forEach(posts::add);

        return Optional.ofNullable(posts)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/posts/create")
    public ResponseEntity<String> createNewPost(@RequestBody Post post){

        if(userRepo.findByUsername(post.getUsername()).isEmpty())
            return ResponseEntity.ok("No user with such username was found");

        postRepo.save(post);

        return ResponseEntity.ok("New post created");
    }
}
