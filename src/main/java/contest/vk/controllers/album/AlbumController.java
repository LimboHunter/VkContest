package contest.vk.controllers.album;


import contest.vk.model.Album;
import contest.vk.service.album.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping("/api")
public class AlbumController {
    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/albums")
    public ResponseEntity<List<Album>> getAllAlbums(@RequestParam(required = false) Long userId) {
        List<Album> albums = albumService.getAllAlbums(userId);

        return Optional.ofNullable(albums)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/albums/{id}")
    public ResponseEntity<Album> getAlbum(@PathVariable Long id) {
        Album album = albumService.getAlbum(id);

        return Optional.ofNullable(album)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/albums")
    public ResponseEntity<Album> createAlbum(@RequestBody Album album) {
        ResponseEntity<Album> responseEntity = albumService.createAlbum(album);

        return Optional.of(responseEntity)
                .filter(res -> res.getStatusCode()==HttpStatus.CREATED)
                .map(res -> ResponseEntity.ok(res.getBody()))
                .orElse(ResponseEntity.status(responseEntity.getStatusCode()).build());
    }

    @PutMapping("/albums/{id}")
    public ResponseEntity<Album> updateAlbum(@PathVariable Long id, @RequestBody Album album) {

        if(albumService.getAlbum(id) == null) return ResponseEntity.notFound().build();

        ResponseEntity<Album> responseEntity = albumService.updateAlbum(id, album);


        return ResponseEntity.ok(responseEntity.getBody());
    }

    @DeleteMapping("/albums/{id}")
    public ResponseEntity<String> deleteAlbum(@PathVariable Long id) {
        albumService.deleteAlbum(id);

        return ResponseEntity.ok("Album " + id + " deleted");
    }

}