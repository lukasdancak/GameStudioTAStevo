package sk.tuke.gamestudio.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sk.tuke.gamestudio.minesweeper.core.Clue;
import sk.tuke.gamestudio.minesweeper.core.Field;
import sk.tuke.gamestudio.minesweeper.core.Tile;

import javax.xml.crypto.Data;
import java.util.Date;

@Controller
@RequestMapping("/minesweeper")
public class MinesweeperController {

    private Field field = new Field(9,9, 10);
    private boolean marking = false;

    @RequestMapping
    public String minesweeper(){
        return "minesweeper";

    }

    public String getCurrTime(){
        return new Date().toString();
    }

    public String getFieldAsHtml(){


        int rowCount = field.getRowCount();
        int colCount = field.getColumnCount();

        StringBuilder sb = new StringBuilder();

        sb.append("<table>\n");
        for(int row=0; row<rowCount;row++){
            sb.append("<tr>\n");

            for(int col=0; col<colCount;col++){
                sb.append("<td>\n");
                Tile tile =field.getTile(row,col);
                sb.append(getTileText(tile));
                sb.append("<td>\n");
            }



            sb.append("</tr>\n");
        }

        sb.append("</table>\n");

        return sb.toString();
    }

    private String getTileText(Tile tile) {
        switch (tile.getState()) {
            case CLOSED:
                return "-";
            case MARKED:
                return "M";
            case OPEN:
                if (tile instanceof Clue) {
                    return String.valueOf(((Clue) tile).getValue());
                } else {
                    return "X";
                }
            default:
                throw new IllegalArgumentException("Unsupported tile state " + tile.getState());
        }
    }
}
