package sk.tuke.gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.minesweeper.core.Clue;
import sk.tuke.gamestudio.minesweeper.core.Field;
import sk.tuke.gamestudio.minesweeper.core.Tile;

import java.util.Date;

@Controller
@RequestMapping("/minesweeper")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MinesweeperController {

    private Field field = new Field(9,9,10);

    private boolean marking = false;

    @RequestMapping
    public String minesweeper(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column){

        if(row != null && column != null){

            if(marking){
                field.markTile(row,column);
            }else{
                field.openTile(row,column);
            }
        }

        return "minesweeper";
    }

    @RequestMapping("/mark")
    public  String changeMarking(){
        marking = !marking;
        return "minesweeper";
    }

    @RequestMapping("/new")
    public  String newGame(){
        field = new Field(9,9,10);
        return "minesweeper";
    }

    public String getCurrTime(){
        return new Date().toString();
    }

    public boolean getMarking(){
        return marking;
    }
    /*
            sb.append("<table class='minefield'>\n");
        for (int row = 0; row < field.getRowCount(); row++) {
        sb.append("<tr>\n");
        for (int column = 0; column < field.getColumnCount(); column++) {
            var tile = field.getTile(row, column);
            sb.append("<td class='" + getTileClass(tile) + "'>\n");
            if (tile.getState() != Tile.State.OPEN)
                sb.append("<a href='/minesweeper?row=" + row + "&column=" + column + "'>\n");
            sb.append("<span>" + getTileText(tile) + "</span>");
            if (tile.getState() != Tile.State.OPEN)
                sb.append("</a>\n");
            sb.append("</td>\n");
        }
        sb.append("</tr>\n");
    }
        sb.append("</table>\n");
*/

    public String getFieldAsHtml(){

        int rowCount = field.getRowCount();
        int colCount = field.getColumnCount();

        StringBuilder sb = new StringBuilder();

        sb.append("<table class='minefield'>\n");

        for (int row = 0; row<rowCount;row++){
            sb.append("<tr>\n");

            for (int col = 0; col<colCount;col++){
                Tile tile = field.getTile(row,col);

                sb.append("<td class='" + getTileClass(tile) + "'> ");
                sb.append("<a href='/minesweeper/?row="+row+"&column="+col+"'> ");
                sb.append("<span>" + getTileText(tile) + "</span>");
                sb.append(" </a>\n");
                sb.append(" </td>\n");

            }




            sb.append("</tr>\n");
        }


        sb.append("</table>\n");

        return sb.toString();
    }

    private String getTileText(Tile tile){
        switch (tile.getState()){
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

    private String getTileClass(Tile tile) {
        switch (tile.getState()) {
            case OPEN:
                if (tile instanceof Clue)
                    return "open" + ((Clue) tile).getValue();
                else
                    return "mine";
            case CLOSED:
                return "closed";
            case MARKED:
                return "marked";
            default:
                throw new RuntimeException("Unexpected tile state");
        }
    }



}