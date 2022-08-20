package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentWebServiceRest {

    @Autowired
    private CommentService commentService;

    //http://localhost:8080/api/comment/minesweeper
    @GetMapping("/{game}")
    public List<Comment> getBestComments(@PathVariable String game){
        return commentService.getComments (game);
    }

    /*
    //http://localhost:8080/api/comment?game=minesweeper
    @GetMapping
    public List<Comment> getBestComments(String game){
        return commentService.getBestComments (game);
    }
     */

    @PostMapping
    public void addComment(@RequestBody Comment comment){commentService.addComment(comment);}

}