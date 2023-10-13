import java.util.Random;
/**
 * class representing Board of Snake Game
 * @author Abeer Abbasi
 * @version 1.0
 */

public class Board {
    //bounds of rows and columns in the board
    final int rowBoard, columnBoard;
    private final Cell[][] cells;
    
    /**
     * Constructor for board that initializes rows and column of board along with the cell types
     * Sets cellTypes to EMPTY or WALL depending on their position on the board 
     * @param rowBoard
     * @param columnBoard
     */
    public Board(int rowBoard, int columnBoard) {
        this.rowBoard = rowBoard;
        this.columnBoard = columnBoard;
        cells = new Cell[rowBoard][columnBoard];
        //iterate over all rows and columns on board
        for (int r = 0; r < rowBoard; r++) {
            for (int c = 0; c < columnBoard; c++) {
                cells[r][c] = new Cell(r, c);
                //If cells are on edge, set the type to WALL
                if (r == 0 || c == 0 || r == rowBoard - 1 || c == columnBoard - 1) {
                    cells[r][c].setType(CellType.WALL);
                //Otherwise, set the type to EMPTY 
                } else {
                    cells[r][c].setType(CellType.EMPTY);
                }
            }
        }
    }
    
    /**
     * retrieves the cells of the board
     * @return cells of board
     */
    public Cell[][] getCells() {
        return cells;
    }
    
    /**
     * sets the type of the cell, known by row and column given, to the type given as parameter
     * @param row The row that the targeted cell is in
     * @param column The column that the targeted cell is in
     * @param type The type of the desired cell
     */
    public void setCells(int row, int column, CellType type) {
        cells[row][column].setType(type);
    }
    
    /**
     * retrieves the type of the cell within the given row and column
     * @param row The row that the cell is in
     * @param column The column that the cell is in
     * @return The type of the cell (EMPTY, WALL, FOOD, SNAKE)
     */
    public CellType getCellType(int row, int column) {
        return cells[row][column].getType();
    }
    
    /**
     * generates food in a cell if the cell was originally empty
     * Sets cell type to FOOD
     */

    public void generateFood() {
        int row, column;
        int attempts = 0;
        
        while (true) {
            if (attempts++ > 1000) {
                break;
            }
            //generating two random values for row and column within the Board
            Random rand = new Random();
            row = rand.nextInt(rowBoard);
            column = rand.nextInt(columnBoard);
            //if the cell type is originally EMPTY, change it to FOOD (makes sure we do not generate food in a Non-empty cell
            if (cells[row][column].getType() == CellType.EMPTY) {
                cells[row][column].setType(CellType.FOOD);
                break;
            }
        }
    }
}
