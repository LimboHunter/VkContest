package contest.vk.model;

import lombok.Data;

@Data
public class Todo {
    Long userId;

    Long id;

    String title;

    Boolean completed;
}