package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class SystemMessageController {


    List<String> messagesForUser = new ArrayList<String>();
    List<String> messagesForAdmin = new ArrayList<String>();

    public List<String> getMessagesForUser() {
        return messagesForUser;
    }

    public List<String> getMessagesForUserAndDeleteThem() {
        List<String> temp = new ArrayList<String>();
        temp.addAll(messagesForUser);
        messagesForUser.clear();
        return temp;
    }

    public List<String> getMessagesForAdmin() {
        messagesForUser.add("dfgjfgjdf dhgkdjhg skdjgh dfj");
        return messagesForAdmin;
    }
}
