import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * This Panel Class represents The main gaim area of the Snake Game.
 * it handles the game loop, rendering, and input events.
 * @author Abeer Abbasi
 * @version 1.0
 */

public class Panel extends JPanel implements ActionListener {
	//constants for screen dimensions and refresh delay
    static int SCREEN_WIDTH = 550;
    static int SCREEN_HEIGHT = 550;
    static int UNIT_SIZE = 25;  //size of 1 unit on the board
    static int DELAY = 75;      //timer delay in milliseconds
    private final int start = 4; //initial snake size
    Timer timer = new Timer(DELAY, this);   //Timer for game loop
    Board board; //represent game board
    Cell initPos; //represent initial position of snake on the board
    Snake snake;  //represents the snake 
    private int score;  //current score 
    private GameStatus currentGameStatus; //current game status (start, running, game over)
    private MovementDirection currentDirection; //current direction(up, down, left, right)
    private boolean generateFood; //flag to indicate whether food should be generated

    /**
     * Constructor for Panel that initializes the game panel
     */
    public Panel() {
    	//initialize board, initial position, and snake
        board = new Board(SCREEN_HEIGHT / UNIT_SIZE, SCREEN_WIDTH / UNIT_SIZE);
        initPos = new Cell(SCREEN_HEIGHT / (UNIT_SIZE * 2), SCREEN_WIDTH / (UNIT_SIZE * 2) - start + 1);
        snake = new Snake(initPos, board);
        //set panel properties
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter()); //add keylistener for input events
        this.requestFocus(); //request focus to receive key events
        //set initial game status and movement directions 
        //(set game status to START, snake moving to the RIGHT at beginning of game, and generateFood is true)
        currentGameStatus = GameStatus.Start; 
        currentDirection = MovementDirection.RIGHT;
        generateFood = true;
    }
    
    /**
     * Start the game by initializing the score and starting the timer
     */
    public void startGame() {
    	score = 0;
    	timer.stop();
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    /**
     * restart game by reinitializing game components and calling startGame()
     */
    public void restartGame() {
    	//resetting board, initial position and snake
        board = new Board(SCREEN_HEIGHT / UNIT_SIZE, SCREEN_WIDTH / UNIT_SIZE);
        initPos = new Cell(SCREEN_HEIGHT / (UNIT_SIZE * 2), SCREEN_WIDTH / (UNIT_SIZE * 2) - start + 1);
        snake = new Snake(initPos, board);
        //resetting panel properties
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.requestFocus();
        board.generateFood(); //generating food on the board
        //restarting game status and direction
        currentGameStatus = GameStatus.Start;
        currentDirection = MovementDirection.RIGHT;
        //starting the game again by resetting the timer
        startGame();
    }
    
    /**
     * Override the paintComponent method to render the game elements
     * @param g The graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }
    /**
     * Draw the game elements based on the Game Status
     * @param g The Graphics object on which to draw
     */

    public void draw(Graphics g) {
        switch (currentGameStatus) {
            case Start:
                displayStartMessage(g);
                break;
            case Running:
                drawBoard(g);
                break;
            case GameOver:
                gameOver(g);
                break;
        }

    }
    /**
     * Displays message on screen at start of game
     * @param g The Graphics object on which to draw
     */
    public void displayStartMessage(Graphics g) {
        g.setColor(Color.GREEN);
        g.setFont(new Font("Courier new", Font.BOLD, 32));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("SNAKE GAME", (SCREEN_WIDTH - metrics1.stringWidth("SNAKE GAME")) / 2, (SCREEN_HEIGHT / 2) - metrics1.getHeight());

        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier new", Font.BOLD, 22));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Press enter to Start", (SCREEN_WIDTH - metrics2.stringWidth("Press enter to Start:")) / 2, (SCREEN_HEIGHT / 2) + metrics2.getHeight());
    }
    
    /**
     * Render the game board, including the snake, walls, and food
     * @param g The Graphics object on which to draw
     */
    public void drawBoard(Graphics g) {
        Cell[][] cells = board.getCells();
        //iterate over all of board
        for (int r = 0; r < SCREEN_HEIGHT / UNIT_SIZE; r++) {
            for (int c = 0; c < SCREEN_WIDTH / UNIT_SIZE; c++) {
            	//if the cell belongs to the edge/wall, color the cell blue
                if (cells[r][c].getType() == CellType.WALL) {
                    g.setColor(Color.BLUE);
                    g.fillRect(c * UNIT_SIZE, r * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                    //if the cells contain food, fill the cell with a red oval representing food
                } else if (cells[r][c].getType() == CellType.FOOD) {
                    g.setColor(Color.RED);
                    g.fillOval(c * UNIT_SIZE, r * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                    //if cell contains a snake, color the cell green to represent a snake part
                } else if (cells[r][c].getType() == CellType.SNAKE) {
                    g.setColor(Color.GREEN);
                    g.fillRect(c * UNIT_SIZE, r * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                }


            }
        }
        //create grid on game panel using white lines
        g.setColor(Color.WHITE);
        for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
        }


        for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
        }
    }
    
    /**
     * Display the game over message along with the final score
     * @param g The Graphics object on which to draw
     */
    public void gameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Courier new", Font.BOLD, 32));
        FontMetrics metrics = getFontMetrics(g.getFont());
        int gHeight = metrics.getHeight();
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
        String points = Integer.toString(snake.getSnakeParts().size());
        points = "SCORE: " + score;
        g.setFont(new Font("Courier new", Font.BOLD, 24));
        metrics = getFontMetrics(g.getFont());
        int extra = 50;
        g.drawString(points, (SCREEN_WIDTH - metrics.stringWidth(points)) / 2, (SCREEN_HEIGHT + gHeight + extra / 2) / 2);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier new", Font.BOLD, 22));
        metrics = getFontMetrics(g.getFont());
        g.drawString("Press ENTER to restart", (SCREEN_WIDTH - metrics.stringWidth("Press ENTER to restart")) / 2, (SCREEN_HEIGHT + gHeight + extra * 2) / 2);
    }
    
    /**
     * checks for collision with the snake's next movement
     * @param nextCell The next cell that the snake will move to
     * @return true if collision occurs, false if not
     */
    public boolean checkCrash(Cell nextCell) {
    	//if the next cell type is SNAKE or WAll, that means a collision happened, will return true
        if ((board.getCellType(nextCell.getRow(), nextCell.getCol()) == CellType.SNAKE) || (board.getCellType(nextCell.getRow(), nextCell.getCol()) == CellType.WALL)) {
            return true;
        //if the cell type is Food, generateFood is changed to true and score is indexed
        } else if (board.getCellType(nextCell.getRow(), nextCell.getCol()) == CellType.FOOD) {
            generateFood = true;
            score++;
        }
        //returns false if cell type is not SNAKE or WALL
        return false;
    }
    
    /**
     * Game loop: this function is invoked periodically by the timer
     * @param e an ActionEvent object
     */
    @Override
    public void actionPerformed(ActionEvent e) {
    	//if game is currently running: get the head of snake and check the current direction value
        if (currentGameStatus == GameStatus.Running) {
            Cell head = snake.getHead();
            switch (currentDirection) {
            //if current direction is Left, the new head will shift to the left by one cell
                case LEFT:
                    head = new Cell(head.getRow(), head.getCol() - 1);
                    break;
            //if current direction is right, the new head will shift to the right by one cell
                case RIGHT:
                    head = new Cell(head.getRow(), head.getCol() + 1);
                    break;
            //if current direction is up, new head will shift upwards by one cell
                case UP:
                    head = new Cell(head.getRow() - 1, head.getCol());
                    break;
            //if current direction is down, new head will shift downwards by one cell
                case DOWN:
                    head = new Cell(head.getRow() + 1, head.getCol());
                    break;
                default:
                    break;
            }
            //if a collision occured, the status is changed to GameOver
            if (checkCrash(head)) {
                currentGameStatus = GameStatus.GameOver;
            //Otherwise, if generateFood is true, food is generated on board and snake grows one cell 
            //change generateFood afterwards to false
            } else if (generateFood) {
                board.generateFood();
                generateFood = false;
                snake.grow(head, board);
            //If non of the above is true, the snake simply moves on the board
            } else {
                snake.move(head, board);
            }

        }
        //request to redraw game panel
        repaint();
    }
    
    /**
     * custom key adapter to handle keyboard input
     */
    public class myKeyAdapter extends KeyAdapter {
        @Override
        /**
         * overriding keyPressed method to run game based on keyboard input and currentGameStatus
         * @param keyEvent e The key pressed by user
         */
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            	//if ENTER is pressed at START of game, change status to RUNNING and start the game
                if (currentGameStatus == GameStatus.Start) {
                    currentGameStatus = GameStatus.Running;
                    startGame();
                //if ENTER is pressed at end of game (GAME OVER), restart the game
                } else if (currentGameStatus == GameStatus.GameOver) {
                    restartGame();
                }
            //otherwise, if game is running check keyboard input and change movement direction of snake accordingly
            } else if (currentGameStatus == GameStatus.Running) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                    	//checking if current direction is not to the right to avoid collision
                        if (currentDirection != MovementDirection.RIGHT) {
                            currentDirection = MovementDirection.LEFT;
                        }
                 
                        break;
                    case KeyEvent.VK_RIGHT:
                    	//checking if current direction is not to the left to avoid collision
                        if (currentDirection != MovementDirection.LEFT) {
                            currentDirection = MovementDirection.RIGHT;
                        }
                        break;
                    case KeyEvent.VK_UP:
                    	//checking if current direction is not down to avoid collision
                        if (currentDirection != MovementDirection.DOWN) {
                            currentDirection = MovementDirection.UP;
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                    	//checking if current direction is not up to avoid collision
                        if (currentDirection != MovementDirection.UP) {
                            currentDirection = MovementDirection.DOWN;
                        }
                        break;
                }
            }
        }
    }

}
	
