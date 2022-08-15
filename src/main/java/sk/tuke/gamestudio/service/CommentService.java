package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Score;

import java.util.List;

public interface CommentService {

    void addComment(Comment comment);

    List<Comment> getComments(String game);

    void reset();
}
