package contest.vk.model;

import lombok.Data;

@Data
public class Photo {
    Long albumId;

    Long id;

    String title;

    String url;

    String thumbnailUrl;
}