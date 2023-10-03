package chess;


public class Position implements ChessPosition {
    private int row;
    private int column;
    @Override
    public int getRow() {
        return row;
    }
    public void setRow(int r) {
        row = r;
    }

    @Override
    public int getColumn() {
        return column;
    }
    public void setColumn(int c) {
        column = c;
    }

}
