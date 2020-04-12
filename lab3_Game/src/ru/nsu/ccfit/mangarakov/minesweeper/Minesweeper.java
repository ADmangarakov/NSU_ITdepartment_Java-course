package ru.nsu.ccfit.mangarakov.minesweeper;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ru.nsu.ccfit.mangarakov.minesweeper.View.GUI;
import ru.nsu.ccfit.mangarakov.minesweeper.View.HighScore;
import ru.nsu.ccfit.mangarakov.minesweeper.View.ScoreView;
import ru.nsu.ccfit.mangarakov.minesweeper.View.View;

public class Minesweeper {
    private static GameBoard gameBoard;
    private static View view;

    public static void startGame(int cellsCountX, int cellsCountY, int percentMine) {
        gameBoard = new GameBoard(cellsCountX, cellsCountY, percentMine);
        view.changeSize(cellsCountX, cellsCountY);
        startGame();
    }

    public static void showHelp() {
        view.showHelp();
    }

    public static void saveScore(String text) {
        HighScore.saveScore(text, view.getTime(), gameBoard.getTotalMinesNum());
    }

    public enum Level {
        EASY,
        MEDIUM,
        HARD
    }

    public enum ExitStatus {
        CONTINUE,
        FAIL,
        WIN
    }

    public static void init(int cellsCountX, int cellsCountY, Stage stage, Pane gameWindow, Pane startupWindow) {
        gameBoard = new GameBoard(cellsCountX, cellsCountY, 5);
        view = new GUI(stage, gameWindow, startupWindow, cellsCountX, cellsCountY);
    }

    public static void startGame() {
        gameBoard.generate();
        view.generate();
    }

    public static void openCell(int X, int Y) {
        ExitStatus status = gameBoard.checkCell(X, Y, view);
        if (status != ExitStatus.CONTINUE) {
            view.showEndgameWindow(status, gameBoard.getTotalMinesNum());
        }
    }

    public static void changeFlag(int X, int Y) {
        ExitStatus status = gameBoard.changeFlag(X, Y, view);
    }

    public static void exit() {
        view.exit();
    }
}
