package com.example.biggestpicture.endpoints;

import com.example.biggestpicture.entity.Photo;
import com.example.biggestpicture.entity.Photos;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Pictures {

    String apiBaseUrl = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos";
    String apiKey = "DEMO_KEY";


    @Cacheable("LargestPictures")
    @GetMapping("/pictures/{sol}/largest")
    public ResponseEntity<String> getLargestPicture (@PathVariable String sol) {
        System.out.println("Hello: " + sol);

        String url = UriComponentsBuilder.fromHttpUrl(apiBaseUrl)
                .queryParam("sol", sol)
                .queryParam("api_key", apiKey)
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();

        var response = restTemplate.getForEntity(url, Photos.class);

        var photos = response.getBody().getPhotos();

        Map<String, Integer> pictureSizes= new HashMap<>();

        for (Photo photo : photos) {
            int size = getMaxPictureSize(photo.getImg_src());
            pictureSizes.put(photo.getImg_src(), size);
        }

        var imgLink = pictureSizes.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).map(Map.Entry::getKey);
        var size = pictureSizes.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).map(Map.Entry::getValue);

        System.out.println("Image Link: " + imgLink);
        System.out.println("Image Size: " + size);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header("Location", imgLink.get())
                .build();
    }

    private int getMaxPictureSize(String url) {
        RestTemplate restTemplate = new RestTemplate();
        var response = restTemplate.getForEntity(url, String.class);
        var status = response.getStatusCode();
        if (status.equals(HttpStatus.MOVED_PERMANENTLY)){
            var location = response.getHeaders().get("Location").get(0);
            return getMaxPictureSize(location);
        } else {
            return Integer.parseInt(response.getHeaders().get("Content-Length").get(0));
        }
    }
}
