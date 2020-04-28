package algorithms.search;

import algorithms.mazeGenerators.Position;

public class MazeState extends AState {

    public MazeState() {
    }

    public MazeState(Position position) {
        super(position);
    }

    public MazeState(Position position, int p)
    {
        super(position);
        cost = p;
    }

}
