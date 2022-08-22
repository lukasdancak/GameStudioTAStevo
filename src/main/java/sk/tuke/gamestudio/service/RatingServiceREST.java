package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Rating;


public class RatingServiceREST implements RatingService {

    @Value("${remote.server.api}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void setRating(Rating rating) {
        restTemplate.postForEntity(url + "/rating", rating, Rating.class);
    }

    @Override
    public int getAverageRating(String game) {
       // return restTemplate.getForObject(url + "/rating/" + game, Rating.class).getRating();
       return  Integer.parseInt(
               restTemplate.getForEntity(url + "/rating/" + game, String.class, new Object() ).getBody()
       )  ;
    }

    @Override
    public int getRating(String game, String username) {
//        return restTemplate.getForEntity(url + "/rating/" + game + "/" + username, Rating.class).getBody().getRating();
        return restTemplate.getForObject(url + "/rating/" + game + "/" + username, Rating.class).getRating();
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web.");
    }
}