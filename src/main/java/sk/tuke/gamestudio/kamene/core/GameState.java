package sk.tuke.gamestudio.kamene.core;

public enum GameState {
    /**
     * Playing game.
     */
    PLAYING,
    //hrac zadal EXIT game
    EXIT_BY_USER,
    ASKED_FOR_NEW_GAME,

    // toto zatial nepouzivam
//    /**
//     * Game failed.
//     */
//    FAILED,

    /**
     * Game solved.
     */
    SOLVED

}
