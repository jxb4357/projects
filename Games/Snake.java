package com.codegym.games.snake;
import com.codegym.engine.cell.*;

import java.util.List;
import java.util.ArrayList;


public class Snake extends Game {
    
    private List<GameObject> snakeParts = new ArrayList<>();
    
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;

    // initialize snake
    public Snake (int x, int y) {
        GameObject first = new GameObject(x,y);
        GameObject second = new GameObject(x+1,y);
        GameObject third = new GameObject(x+2,y);
        
        snakeParts.add(first);
        snakeParts.add(second);
        snakeParts.add(third);
    }
    
    // draws the snake on the game map
    public void draw (Game game) {
        for (GameObject gameObject : snakeParts) {
            if (isAlive == true) {
                if (snakeParts.indexOf(gameObject) == 0) {
                    game.setCellValueEx(gameObject.x, gameObject.y, Color.NONE, HEAD_SIGN, Color.BLACK, 75);
                }
                else {
                    game.setCellValueEx(gameObject.x, gameObject.y, Color.NONE, BODY_SIGN, Color.BLACK, 75);
                }
            }
            else if (isAlive == false) {
                if(snakeParts.indexOf(gameObject) == 0) {
                    game.setCellValueEx(gameObject.x, gameObject.y, Color.NONE, HEAD_SIGN, Color.RED, 75);
                }
                else {
                    game.setCellValueEx(gameObject.x, gameObject.y, Color.NONE, BODY_SIGN, Color.RED, 75);
                }
            }
        }
    }
    
    public Direction getDirection() {
        return direction;
    }
    
    // setter for snake direction
    public void setDirection(Direction direction) {
        
        // if the key pressed is the opposite of the snake's current direction, do nothing
        if ((direction == Direction.UP && this.direction == Direction.DOWN)
            || (direction == Direction.DOWN && this.direction == Direction.UP)
            || (direction == Direction.LEFT && this.direction == Direction.RIGHT)
            || (direction == Direction.RIGHT && this.direction == Direction.LEFT)) {
                return;
            }
            
            
        if((this.direction == Direction.LEFT && snakeParts.get(0).x == snakeParts.get(1).x)
            || (this.direction == Direction.RIGHT && snakeParts.get(0).x == snakeParts.get(1).x)
            || (this.direction == Direction.UP && snakeParts.get(0).y == snakeParts.get(1).y)
            || (this.direction == Direction.DOWN && snakeParts.get(0).y == snakeParts.get(1).y)) {
                return;
            }
        
        this.direction = direction;
    }
    
    public void move(Apple apple) {
        GameObject newHead = createNewHead();
        
        if (checkCollision(newHead)) {
            isAlive = false;
            return;
        }
        
        // if the head hits the edge of the map, it's dead
        if (newHead.x >= 0 && newHead.x < SnakeGame.WIDTH
            && newHead.y >= 0 && newHead.y < SnakeGame.HEIGHT) {
            snakeParts.add(0, newHead);
        }
        else {
            isAlive = false;
        }
        
        if (snakeParts.get(0).x == apple.x && snakeParts.get(0).y == apple.y) {
            apple.isAlive = false;
        }
        else {
            removeTail();
        }
        
        
    }
    
    public GameObject createNewHead() {
        if (direction == Direction.LEFT) {
            return new GameObject(snakeParts.get(0).x-1, snakeParts.get(0).y);
        }
        else if (direction == Direction.RIGHT) {
            return new GameObject(snakeParts.get(0).x+1, snakeParts.get(0).y);
        }
        else if (direction == Direction.DOWN) {
            return new GameObject(snakeParts.get(0).x, snakeParts.get(0).y+1);
        }
        else {
            return new GameObject(snakeParts.get(0).x, snakeParts.get(0).y-1);
        }
    }
    
    public void removeTail() {
        snakeParts.remove(snakeParts.size()-1);
    }
    
    public boolean checkCollision (GameObject gameObject) {
        for(int i = 0; i < snakeParts.size(); i++) {
            if (gameObject.x == snakeParts.get(i).x && gameObject.y == snakeParts.get(i).y) {
                return true;
            }
        }
        return false;
    }
    
    public int getLength() {
        return snakeParts.size();
    }
}