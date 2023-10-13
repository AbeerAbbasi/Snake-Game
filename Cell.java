/**
 * Class representing Cell on the Board of the Snake Game
 * @author Abeer Abbasi
 * @version 1.0
 */

public class Cell {
    //represents the x(row) & y(column) positions of the cell
    private final int row;
    private final int column;
    //represents type of cell
    private CellType type;
    
    /**
     * Constructor for Cell that initializes the row and column of the cell
     * @param row
     * @param col
     */
    public Cell(int row, int col) {
        this.row = row;
        this.column = col;
    }
    
    /**
     * retrieves the row that the cell is in
     * @return row of cell
     */
    public int getRow() {
        return row;
    }
    
    /**
     * retrieves column that the cell is in
     * @return column of cell
     */
    public int getCol() {
        return column;
    }
    
    /**
     * retrieves type of cell
     * @return cell type
     */
    public CellType getType() {
        return type;
    }
    
    /**
     * sets the type of cell 
     * @param type The type of the cell
     */
    public void setType(CellType type) {
        this.type = type;
    }
}
