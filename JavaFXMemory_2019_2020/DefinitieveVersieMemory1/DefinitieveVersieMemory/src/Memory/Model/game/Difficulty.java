package Memory.Model.game;

public enum Difficulty {

    //Moeilijkheid, grid van 4x5 of 6x6

    FIVE_BY_FOUR(5,4),SIX_BY_SIX(6,6);

    private final int row;
    private final int column;

    Difficulty(int row,int column) {
        this.row = row;
        this.column=column;
    }

    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }



}
