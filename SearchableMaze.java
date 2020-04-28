package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable {

    public Maze m;



    public SearchableMaze(Maze maze) {
        this.m = maze;
    }

    @Override
    public MazeState getStartState() {
        MazeState ms = new MazeState(m.getStartPosition());
        return ms;
    }

    @Override
    public AState getGoalState() {
        MazeState ms = new MazeState(m.getGoalPosition());
        return ms;
    }

    @Override
    public ArrayList<AState> getAllPossibleStates(AState s) {
        ArrayList<AState> res = new ArrayList();
        boolean top = false;
        boolean bottom = false;
        boolean right = false;
        boolean left = false;
        boolean topRight = false;
        boolean topLeft = false;
        boolean bottomRight = false;
        boolean bottomLeft = false;

        top = s.position.getRowIndex()-1>=0;
        bottom = s.position.getRowIndex()+1 <= m.getMaze().length - 1;
        right = s.position.getColumnIndex()+1 <= m.getMaze()[0].length - 1;
        left = s.position.getColumnIndex()-1 >= 0;
        topRight = top && right;
        topLeft = top && left;
        bottomRight = bottom && right;
        bottomLeft = bottom && left;

        if(top && m.getMaze()[s.position.getRowIndex()-1][s.position.getColumnIndex()] == 0 )
            res.add(new MazeState(new Position(s.position.getRowIndex()-1,s.position.getColumnIndex()),10));

        if(topRight && m.getMaze()[s.position.getRowIndex()-1][s.position.getColumnIndex()] == 0 &&  m.getMaze()[s.position.getRowIndex()-1][s.position.getColumnIndex()+1] == 0)
            res.add(new MazeState(new Position(s.position.getRowIndex()-1,s.position.getColumnIndex()+1),15));

        if(right && m.getMaze()[s.position.getRowIndex()][s.position.getColumnIndex()+1] == 0 )
            res.add(new MazeState(new Position(s.position.getRowIndex(),s.position.getColumnIndex()+1),10));

        if(bottomRight && m.getMaze()[s.position.getRowIndex()+1][s.position.getColumnIndex()] == 0 && m.getMaze()[s.position.getRowIndex()+1][s.position.getColumnIndex()+1] == 0)
            res.add(new MazeState(new Position(s.position.getRowIndex()+1,s.position.getColumnIndex()+1),15));

        if(bottom && m.getMaze()[s.position.getRowIndex()+1][s.position.getColumnIndex()] == 0)
            res.add(new MazeState(new Position(s.position.getRowIndex()+1,s.position.getColumnIndex()),10));

        if(bottomLeft && m.getMaze()[s.position.getRowIndex()+1][s.position.getColumnIndex()] == 0 && m.getMaze()[s.position.getRowIndex()+1][s.position.getColumnIndex()-1] == 0)
            res.add(new MazeState(new Position(s.position.getRowIndex()+1,s.position.getColumnIndex()-1),15));

        if(left && m.getMaze()[s.position.getRowIndex()][s.position.getColumnIndex()-1] == 0)
            res.add(new MazeState(new Position(s.position.getRowIndex(),s.position.getColumnIndex()-1),10));

        if(topLeft && m.getMaze()[s.position.getRowIndex()-1][s.position.getColumnIndex()] == 0 && m.getMaze()[s.position.getRowIndex()-1][s.position.getColumnIndex()-1] == 0)
            res.add(new MazeState(new Position(s.position.getRowIndex()-1,s.position.getColumnIndex()-1),15));

        return res;
    }
}
