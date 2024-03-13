package contest.vk.model;

import lombok.Data;

@Data
public class Comment {

    private Long id;

    private String name;
    private String email;
    private String body;

    private Long postId;
}
