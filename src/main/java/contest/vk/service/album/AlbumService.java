package contest.vk.service.album;

import contest.vk.model.Album;
import contest.vk.model.Photo;
import contest.vk.config.CacheConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AlbumService {

    private final String API_URL = "https://jsonplaceholder.typicode.com";
    private final RestTemplate restTemplate;
    private final CacheConfig cacheConfig;

    @Autowired
    public AlbumService(RestTemplate restTemplate, CacheConfig cacheConfig) {
        this.restTemplate = restTemplate;
        this.cacheConfig = cacheConfig;
    }

    public List<Album> getAllAlbums(Long userId) {
        String cacheKey = "allAlbums_" + userId;
        List<Album> cachedAlbums = (List<Album>) cacheConfig.get(cacheKey).orElse(Collections.emptyList());

        if (!cachedAlbums.isEmpty()) {
            return cachedAlbums;
        }

        String url = userId == null ? API_URL + "/albums" : API_URL + "/albums?userId={userId}";
        Album[] albums = restTemplate.getForObject(url, Album[].class, userId);

        List<Album> result = Optional.ofNullable(albums)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());

        cacheConfig.put(cacheKey, result);
        return result;
    }

    public Album getAlbum(Long id) {
        String cacheKey = "Album_" + id;
        Album cachedAlbums = (Album) cacheConfig.get(cacheKey).orElse(null);
        if (cachedAlbums != null) {
            return cachedAlbums;
        }

        String url = API_URL + "/albums/" + id;
        Album album = restTemplate.getForObject(url, Album.class);
        if (album != null) {
            cacheConfig.put(cacheKey, album);
        }
        return album;
    }

    public List<Photo> getPhotosByAlbumId(Long albumId) {
        String cacheKey = "PhotosByAlbumId_" + albumId;
        List<Photo> cachedPhotos = (List<Photo>) cacheConfig.get(cacheKey).orElse(Collections.emptyList());
        if (!cachedPhotos.isEmpty()) {
            return cachedPhotos;
        }

        String url = String.format("%s/albums/%d/photos", API_URL, albumId);
        Photo[] photos = restTemplate.getForObject(url, Photo[].class);

        List<Photo> result = Optional.ofNullable(photos)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());

        cacheConfig.put(cacheKey, result);
        return result;
    }

    public ResponseEntity<Album> createAlbum(Album album) {
        // Очистите кэш для данного запроса
        cacheConfig.evict("allAlbums_");

        String url = API_URL + "/albums";
        return restTemplate.postForEntity(url, album, Album.class);
    }

    public ResponseEntity<Album> updateAlbum(Long id, Album album) {
        cacheConfig.evict("allAlbums_");

        String url = API_URL + "/albums/{id}";
        Map<String, Long> params = Collections.singletonMap("id", id);

        return restTemplate.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(album),
                Album.class,
                params
        );
    }

    public void deleteAlbum(Long id) {
        cacheConfig.evict("allAlbums_");

        // Удалите пост на сервере
        String url = API_URL + "/albums/{id}";
        restTemplate.delete(url, id);
    }
}
