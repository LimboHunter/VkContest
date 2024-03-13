package contest.vk.controllers.album;

import contest.vk.model.Photo;
import contest.vk.service.album.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping("/api")
public class PhotoController {
    private final AlbumService albumService;

    @Autowired
    public PhotoController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/albums/{id}/photos")
    public ResponseEntity<List<Photo>> getPhotosByAlbumId(@PathVariable("id") Long albumId) {
        List<Photo> photos = albumService.getPhotosByAlbumId(albumId);

        return Optional.ofNullable(photos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}