package GameEngine.utils;

import java.io.Serializable;

public record Position(int row, int col) implements Serializable {

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        Position position2 = (Position) o;
        return position2.getCol() == this.getCol() && position2.getRow() == this.getRow();
    }

    @Override
    public int hashCode() {
        return this.col * 13 + this.row * 7;
    }

    @Override
    public String toString() {
        return "Position: ( row: " + row + ", col: " + col + ")";
    }

    public String serialize(){ return "("+row+";"+col+")"; }
}


