//package sk.tuke.gamestudio.service;
//
//import sk.tuke.gamestudio.entity.Rating;
//import org.junit.jupiter.api.Test;
//import sk.tuke.gamestudio.service.RatingService;
//import sk.tuke.gamestudio.service.RatingServiceJDBC;
//
//import java.util.Date;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class RatingServiceTest {
//    private RatingService ratingService = new RatingServiceJDBC();
//
//    // TESTY by mali overiť, či:
//    //Nebudú vznikať duplicitné ratingy a
//    //getAverageRating a getRating vracajú 0 ak sa žiadne hodnotenie v tabuľke nenájde.
//
//    @Test
//    public void testAddRating() {
//        ratingService.reset();
//
//        ratingService.setRating(new Rating("minesweeper", "Jozef", 3, new Date()));
//
//        assertEquals(3, ratingService.getRating("minesweeper", "Jozef"));
//
//
//    }
//
//    @Test
//    public void testAddRatingIfUserWithRatingExists() {
//        ratingService.reset();
//        Date date = new Date(); //nie je podstatny
//        ratingService.setRating(new Rating("minesweeper", "Jozef", 3, date));
//        ratingService.setRating(new Rating("minesweeper", "Jozef", 1, date));
//
//        assertEquals(1, ratingService.getRating("minesweeper", "Jozef"));
//    }
//
//    @Test
//    public void testAddRatingNoDuplicate() {
//        ratingService.reset();
//        Date date = new Date(); //nie je podstatny
//        ratingService.setRating(new Rating("minesweeper", "Jozef", 3, date));
//        ratingService.setRating(new Rating("minesweeper", "Jozef", 1, date));
//
//        assertEquals(1, ratingService.getAverageRating("minesweeper"));
//    }
//
//    @Test
//    public void testGetAverageRatingReturnZeroIfDatabaseIsEmpty() {
//        ratingService.reset();
//        assertEquals(0, ratingService.getAverageRating("minesweeper"));
//    }
//
//    @Test
//    public void testGetRatingReturnZeroIfNoRatingExists() {
//        ratingService.reset();
//        assertEquals(0, ratingService.getRating("minesweeper", "Jozef"));
//
//    }
//
//    @Test
//    public void testReset(){
//        ratingService.setRating(new Rating("minesweeper", "Jozef", 3, new Date()));
//        ratingService.reset();
//        assertEquals(0, ratingService.getAverageRating("minesweeper"));
//    }
//}
