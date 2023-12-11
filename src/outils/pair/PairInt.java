package outils.pair;

import java.util.Objects;

public class PairInt {
    int row, col;

    public PairInt(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        PairInt pair = (PairInt) obj;
        return row == pair.row && col == pair.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
