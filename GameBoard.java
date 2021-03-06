package KaterynaShpakGame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GameBoard {

    public static final int ROWS = 4;
    public static final int COLS = 4;

    private final int startingTiles = 2;
    private Tile[][] board;
    private boolean dead;
    private boolean won;
    private BufferedImage gameBoard;
    private BufferedImage finalBoard;
    private int x;
    private int y;

    public static int SPACING = 10;
    public static int BOARD_WIDTH = (COLS + 1) * SPACING + COLS * Tile.WIDTH;
    public static int BOARD_HEIGHT = (ROWS + 1) * SPACING + ROWS * Tile.HEIGHT;

    private boolean hasStarted;

    public GameBoard(int x, int y){
        this.x = x;
        this.y = y;
        board = new Tile[ROWS][COLS];
        gameBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        finalBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);

        createBoardImage();
        start();

    }

    private void createBoardImage(){
        Graphics2D g = (Graphics2D) gameBoard.getGraphics();
        g.setColor(Color.darkGray);
        g.fillRect(0,0, BOARD_WIDTH, BOARD_HEIGHT);
        g.setColor(Color.lightGray);

        for(int row = 0; row < ROWS; row++){
            for (int col = 0; col < COLS; col++){
                int x = SPACING + SPACING * col + Tile.WIDTH * col;
                int y = SPACING + SPACING * row + Tile.HEIGHT * row;
                g.fillRoundRect(x, y, Tile.WIDTH, Tile.HEIGHT, Tile.ARC_WIDTH, Tile.ARC_HEIGHT);

            }
        }
    }

    private void start(){
        for(int i = 0; i < startingTiles; i++){
            spawnRandom();
        }
    }

    private void spawnRandom(){
        Random rand = new Random();
        boolean notValid = true;

        while(notValid){
            int location = rand.nextInt(ROWS * COLS);
            int row = location / ROWS;
            int col = location % COLS;
            Tile current = board[row][col];
            if(current == null){
                int value = rand.nextInt(10) < 9 ? 2 : 4;
                Tile tile = new Tile(value, getTileX(col), getTileY(row));
                board[row][col] = tile;
                notValid = false;

            }
        }
    }

    public int getTileX(int col){
        return SPACING + col * Tile.WIDTH + col * SPACING;
    }

    public int getTileY(int row){
        return SPACING + row * Tile.HEIGHT + row * SPACING;
    }

    public void render(Graphics2D g){
        Graphics2D g2d = (Graphics2D)finalBoard.getGraphics();
        g2d.drawImage(gameBoard, 0, 0, null);


        for (int row = 0; row < ROWS; row++){
            for (int col = 0; col < COLS; col++){
                Tile current = board[row][col];
                if(current == null) continue;
                current.render(g2d);

            }
        }

        g.drawImage(finalBoard, x, y, null);
        g2d.dispose();

    }

    public void update(){
        checkKeys();

        for (int row = 0; row < ROWS; row++){
            for(int col = 0; col < COLS; col++){
                Tile current = board[row][col];
                if (current == null) continue;
                current.update();
                //reset position
                if(current.getValue() == 2048){
                    won = true;
                }
            }
        }
    }

    private void checkKeys(){
        if(Keyboard.typed(KeyEvent.VK_LEFT)){
            //move tiles left
            if(!hasStarted) hasStarted = true;

        }
        if(Keyboard.typed(KeyEvent.VK_RIGHT)){
            //move tiles right
            if(!hasStarted) hasStarted = true;

        }
        if(Keyboard.typed(KeyEvent.VK_UP)){
            //move tiles UP
            if(!hasStarted) hasStarted = true;

        }
        if(Keyboard.typed(KeyEvent.VK_DOWN)){
            //move tiles DOWN
            if(!hasStarted) hasStarted = true;
        }
    }
}
