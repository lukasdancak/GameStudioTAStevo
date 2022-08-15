package sk.tuke.gamestudio.minesweeper;


import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreService;
import sk.tuke.gamestudio.service.ScoreServiceJDBC;

import java.util.Date;


public class PlaygroundJDBC {
    public static void main(String[] args){
        ScoreService service = new ScoreServiceJDBC();

        //service.reset();

        service.addScore(new Score("minesweeper","David",456,new Date()));

        var scores = service.getBestScores("minesweeper");
        System.out.println(scores);




    }

}


//    public static void main(String[] args) throws Exception{
//
//        try( var connection =DriverManager.getConnection("jdbc:postgresql://localhost/gamestudio","postgres","postgres");
//             var statement = connection.createStatement();
//             var rs = statement.executeQuery("SELECT game, username, points, played_on FROM score WHERE game='minesweeper' ORDER BY points DESC LIMIT 5")
//             )
//        {
//            System.out.println("Pripojenie uspesne.");
//
//            while(rs.next()) {
//                System.out.printf("%s, %s, %d, %s \n", rs.getString(1), rs.getString(2), rs.getInt(3), rs.getTimestamp(4));
//            }
//
//
//        }catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//}



//    public static void main(String[] args) throws Exception{
//
//        try( var connection=DriverManager.getConnection("jdbc:postgresql://localhost/gamestudio","postgres","postgres");
//             var statement=connection.createStatement()
//        ){
//            statement.executeUpdate(
//                    "INSERT INTO score VALUES ('minesweeper', 'July', 663, '2022-07-30 12:45')");
//        }
//    }



//    public static void main(String[] args) throws Exception{
//        DriverManager.getConnection("jdbc:postgresql://localhost/gamestudio","postgres","postgres");
//        System.out.println("Uspesne pripojene k databaze");
//    }
