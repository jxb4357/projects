package com.codegym.games.snake;
import com.codegym.engine.cell.*;

public class SnakeGame extends Game {
    
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    
    private Snake snake;
    private Apple apple;
    
    private int turnDelay;
    private boolean isGameStopped;
    
    private static final int GOAL = 28;
    private int score;
    
    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }
    
    private void createGame() {
        snake = new Snake(WIDTH/2, HEIGHT/2);
        createNewApple();
        
        isGameStopped = false;
        drawScene();

        score = 0;
        setScore(score);
        turnDelay = 300;
        setTurnTimer(turnDelay);
    }
    
    @Override
    public void onTurn(int step) {
        
        if(!apple.isAlive){
            createNewApple();
            
            // increase score
            score += 5;
            setScore(score);
            
            // speed up turn
            turnDelay -= 10;
            setTurnTimer(turnDelay);
        }
        
        if (snake.isAlive == false) {
            gameOver();
        }
        
        if (snake.getLength() > GOAL) {
            win();
        }
        
        snake.move(apple);
        
        drawScene();
    }
    
    // draws the tiles on the map and the snake
    private void drawScene() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x, y, Color.DARKSEAGREEN, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }
    
    @Override
    public void onKeyPress(Key key) {
        if (key == Key.SPACE && isGameStopped) {
            createGame();
        }
        
        
        switch(key) {
            case LEFT:
                snake.setDirection(Direction.LEFT);
                break;
            case RIGHT:
                snake.setDirection(Direction.RIGHT);
                break;
            case UP:
                snake.setDirection(Direction.UP);
                break;
            case DOWN:
                snake.setDirection(Direction.DOWN);
                break;
        }
    }
    
    private void createNewApple() {
        Apple newApple;
        
        do {
            int x = getRandomNumber(WIDTH);
            int y = getRandomNumber(HEIGHT);
            newApple = new Apple(x, y);
        } while (snake.checkCollision(newApple));
        
        apple = newApple;
    }
    
    private void gameOver() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "GAME OVER", Color.RED, 50);
    }
    
    private void win() {
        stopTurnTimer();
        isGameStopped = true;
        
        showMessageDialog(Color.NONE, "YOU WIN", Color.DARKGREEN, 50);
    }
    
}