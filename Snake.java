import java.util.LinkedList;
/**
 * Class representing the Snake
 * sets the parts of the snake, and contains methods regarding growth and movement of Snake
 * @author Abeer Abbasi
 * @version 1.0
 */
public class Snake {
    private final LinkedList<Cell> snakeParts = new LinkedList<>();
    //Snake length is initialized to be of length 4
    private final int initSnakeLength = 4;
    private Cell head;
    
    /**
     * Constructor for Snake, creates a snake with a length of 4 cells with first cell placed at initial Position given
     * @param initPos Initial Position of snake on the Board
     * @param board The board containing all possible cells
     */
    public Snake(Cell initPos, Board board) {
        for (int i = 0; i < initSnakeLength; i++) {
            //keep adding body parts to the last body part added
            head = new Cell(initPos.getRow(), initPos.getCol() + i);
            snakeParts.addFirst(head);
            //add cell to snake, and set type of cell to SNAKE
            board.setCells(head.getRow(), head.getCol(), CellType.SNAKE);
        }
    }
    
    /**
     * grows the snake by adding a cell to beginning of snakeParts
     * @param nextCell The cell that will be added to snakeParts (will become part of the snake)
     * @param board The board containing all possible cells
     */
    public void grow(Cell nextCell, Board board) {
        head = nextCell;
        head.setType(CellType.SNAKE);
        snakeParts.addFirst(head);
        
        board.setCells(head.getRow(), head.getCol(), CellType.SNAKE);
    }

    public void move(Cell nextCell, Board board) {
        Cell tail = snakeParts.removeLast();
        board.setCells(tail.getRow(), tail.getCol(), CellType.EMPTY);
        head = nextCell;
        head.setType(CellType.SNAKE);
        snakeParts.addFirst(head);
        board.setCells(head.getRow(), head.getCol(), CellType.SNAKE);

    }
    /**
     * retrieves list of snakeParts
     * @return snakeParts list
     */
    public LinkedList<Cell> getSnakeParts() {
        return snakeParts;
    }
    
    /**
     * retrieves head of snake
     * @return head cell
     */
    public Cell getHead() {
        return head;
    }


}
