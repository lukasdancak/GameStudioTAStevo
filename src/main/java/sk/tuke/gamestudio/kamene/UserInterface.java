package sk.tuke.gamestudio.kamene;

import sk.tuke.gamestudio.kamene.core.Field;

public interface UserInterface {
    void newGameStarted(Field field);

    void update();
}