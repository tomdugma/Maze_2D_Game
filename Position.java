package algorithms.mazeGenerators;
import java.util.ArrayList;
public class Position {

    int row;
    int col;
    boolean isVisited;


    public Position() {
        this.row = 0;
        this.col = 0;
    }


    public Position(int row, int col) {
        this.row = row;
        this.col = col;
        isVisited = false;
    }

    public int getRowIndex() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumnIndex() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public String toString() {
        return "{" + this.row + "," + this.col + '}';
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Position)) {
            return false;
        } else {
            Position other = (Position)obj;
            return (other.col == this.col & other.row == this.row);
        }
    }

//    public boolean Pequals(Position o) {
//        if(o == null) {
//            System.out.println("wrong parameter given");
//            return false;
//        }
//        return (o.row == this.row) && (o.col == this.col);
//    }

    public boolean contains(ArrayList<Position> list)
    {
        for(int i = 0; i < list.size(); i++)
        {
            if(this.equals(list.get(i)))
                return true;
        }
        return false;
    }


}
