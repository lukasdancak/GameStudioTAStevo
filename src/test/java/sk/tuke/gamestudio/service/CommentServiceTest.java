//package sk.tuke.gamestudio.service;
//
//import sk.tuke.gamestudio.entity.Comment;
//import org.junit.jupiter.api.Test;
//import sk.tuke.gamestudio.service.CommentService;
//import sk.tuke.gamestudio.service.CommentServiceJDBC;
////import service.CommentServiceFile;
//
//import java.util.Date;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class CommentServiceTest {
//    private CommentService commentService = new CommentServiceJDBC();
//
//    @Test
//    public void testReset() {
//        commentService.addComment(new Comment("minesweeper", "David", "bla bla bla", new Date()));
//        commentService.reset();
//        assertEquals(0, commentService.getComments("").size());
//    }
//
//    ;
//
//
//    @Test
//    public void testAddScore() {
//        commentService.reset();
//        var date = new Date();
//        commentService.addComment(new Comment("minesweeper", "David", "bla bla bla", date));
//
//
//        var scores = commentService.getComments("minesweeper");
//
//
//        assertEquals(1, scores.size());
//        assertEquals("minesweeper", scores.get(0).getGame());
//        assertEquals("David", scores.get(0).getUsername());
//        assertEquals("bla bla bla", scores.get(0).getComment());
//        assertEquals(date, scores.get(0).getCommentedOn());
//
//        //doplnit doma
//
//    }
//
//
//    @Test
//    public void testGetComments() {
//        commentService.reset();
//        var date1 = new Date();
//        var date2 = new Date(date1.getTime()-10000000L);
//        var date3 = new Date(date2.getTime()-10000000L);
//        var date4 = new Date(date3.getTime()-10000000L);
//
//        commentService.addComment(new Comment("minesweeper", "Katka", "Katka bla bla", date1));
//        commentService.addComment(new Comment("minesweeper", "Peto", "Peto bla bla", date2));
//
//        commentService.addComment(new Comment("tiles", "Zuzka", "Zuzka bla bla", date3));
//        commentService.addComment(new Comment("minesweeper", "Jergus", "Jergus bla bla", date4));
//
//        var comments = commentService.getComments("minesweeper");
//
//        assertEquals(3, comments.size());
//
//
//
//        assertEquals("minesweeper", comments.get(0).getGame());
//        assertEquals("Katka", comments.get(0).getUsername());
//        assertEquals("Katka bla bla", comments.get(0).getComment());
//        assertEquals(date1, comments.get(0).getCommentedOn());
//
//        assertEquals("minesweeper", comments.get(1).getGame());
//        assertEquals("Peto", comments.get(1).getUsername());
//        assertEquals("Peto bla bla", comments.get(1).getComment());
//        assertEquals(date2, comments.get(1).getCommentedOn());
//
//
//
//        assertEquals("minesweeper", comments.get(2).getGame());
//        assertEquals("Jergus", comments.get(2).getUsername());
//        assertEquals("Jergus bla bla", comments.get(2).getComment());
//        assertEquals(date4, comments.get(2).getCommentedOn());
//    }
//
//}
