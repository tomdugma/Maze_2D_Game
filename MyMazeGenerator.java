package algorithms.mazeGenerators;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.Random;

public class MyMazeGenerator extends AMazeGenerator {


    /**
     *  step 1 : init all maze to 1.
     *  step 2 : choose start and ending points.
     *  step 3 : call Prim.
     * @param i
     * @param j
     * @return Maze.
     */

    @Override
    public Maze generate(int i, int j) {

        if (i <= 0 || j <= 0) {
            System.out.println("wrong parameters given");
            return null;
        }
        Maze myMaze = new Maze(i,j);
        setMazeToOne(myMaze);
        myMaze = Prim(myMaze);
        setStartPoint(myMaze);
        setEndingPoint(myMaze);
//        setStartAndEndingPoints(myMaze);
        return myMaze;
    }


    private void setMazeToOne(Maze maze)
    {
        if (maze == null) {
            System.out.println("maze given is null at setMazeToOne function");
        }
        for (int row = 0; row < maze.row; row++) {
            for (int col = 0; col < maze.col; col++) {
                if(row == 0 || row == maze.row-1 || col == 0 || col == maze.col - 1)
                    maze.setPath(new Position(row,col), 2);
                maze.setPath(new Position(row,col), 1);
            }
        }
    }

    private void setStartAndEndingPoints(Maze m)
    {
        Random rand = new Random();
        int randRow;
        int randCol;
        boolean startPointFound = false;
        while(true)
        {
            randRow = rand.nextInt(m.row);
            randCol = rand.nextInt(m.col);
            if(!startPointFound && ((randRow == 1) || (randCol == 1) || (randRow == m.row - 2) || (randCol == m.col - 2)) && m.maze[randRow][randCol]==0)
            {
                m.setStartingPoint(randRow,randCol);
                startPointFound = true;
            }

            // ending point
            if(startPointFound)
            {
                if((m.getStartPosition().getRowIndex() == 1) && (randRow != 1)) // STARTING POINTS UP WALL
                {
                    if (randRow == m.row - 2) {
                        m.setEndingPoint(randRow, randCol);
                        break;
                    } else if (randCol == 1 || randCol == m.col - 2) {
                        m.setEndingPoint(randRow, randCol);
                        break;
                    }
                }
                else if((m.getStartPosition().getRowIndex() == m.row - 2) && (randRow != m.row -2)) // STARTING POINTS BOTTOM WALL
                {
                    if(randRow == 1)
                    {
                        m.setEndingPoint(randRow, randCol);
                        break;
                    }
                    else if (randCol == 1 || randCol == m.col - 2)
                    {
                        m.setEndingPoint(randRow, randCol);
                        break;
                    }
                }
                else if((m.getStartPosition().getColumnIndex() == m.col - 2) && (randCol != m.col - 2)) // STARTING POINTS RIGHT WALL
                {
                    if(randCol == 1)
                    {
                        m.setEndingPoint(randRow, randCol);
                        break;
                    }
                    else if(randRow == 1 || randRow == m.row - 2)
                    {
                        m.setEndingPoint(randRow, randCol);
                        break;
                    }
                }
                else if((m.getStartPosition().getColumnIndex() == 1) && (randCol != 1)) // STARTING POINTS LEFT WALL
                {
                    if(randCol == m.col - 2)
                    {
                        m.setEndingPoint(randRow, randCol);
                        break;
                    }
                    else if(randRow == 1 || randRow == m.row - 2)
                    {
                        m.setEndingPoint(randRow, randCol);
                        break;
                    }
                }
            }
        }

    }

    private Maze Prim(Maze m)
    {
        if(m == null)
        {
            System.out.println("maze value provided is null");
            return null;
        }
        int rowStart = (int)(Math.random() * m.row);
        int colStart = (int)(Math.random() * m.col);
        Position begin = new Position(rowStart,colStart);

        ArrayList<Position> neighbours = new ArrayList();
        m.setPath(begin, 0);
        chooseRandomNeighbour(m, neighbours,begin,true);
        while(!neighbours.isEmpty())
        {
            Random rand = new Random();
            int randomIndex = rand.nextInt(neighbours.size());
            Position chosen = neighbours.get(randomIndex);
            neighbours.remove(randomIndex);
            chooseRandomNeighbour(m,neighbours,chosen,false);
        }
        return m;
    }

    private void findNeighbours(Maze m, Position p, ArrayList<Position> neighbours)
    {
        if(p.row - 2 >= 0 && !((new Position(p.row-2,p.col)).contains(neighbours)) && m.maze[p.row-2][p.col] == 1 && !p.isVisited)
            neighbours.add(new Position(p.row-2,p.col));
        if(p.row + 2 < m.row && !((new Position(p.row+2,p.col)).contains(neighbours)) && m.maze[p.row+2][p.col] == 1 && !p.isVisited)
            neighbours.add(new Position(p.row+2,p.col));
        if(p.col + 2 < m.col && !((new Position(p.row,p.col+2)).contains(neighbours)) && m.maze[p.row][p.col+2] == 1 && !p.isVisited)
            neighbours.add(new Position(p.row,p.col+2));
        if(p.col - 2 >= 0 && !((new Position(p.row,p.col-2)).contains(neighbours)) && m.maze[p.row][p.col-2] == 1 && !p.isVisited)
            neighbours.add(new Position(p.row,p.col-2));
    }

    private void chooseRandomNeighbour(Maze m, ArrayList<Position> neighbours,Position chosen, boolean addBetween)
    {
        findNeighbours(m,chosen,neighbours);
        m.setPath(chosen, 0);
        boolean top = false;
        boolean bottom = false;
        boolean right = false;
        boolean left = false;

        if(chosen.row - 2 >= 0)
            top = true;
        if(chosen.row  +2  < m.row)
            bottom = true;
        if(chosen.col + 2 < m.col )
            right = true;
        if(chosen.col -2 >= 0 )
            left = true;

        while (!addBetween){
            Random rand = new Random();
            int randomIndex1 = rand.nextInt(4);
            if(randomIndex1 ==0 &&  top)
                if(m.maze[chosen.row-2][chosen.col] == 0 )
                {
                    m.setPath(new Position(chosen.row-1, chosen.col), 0);
                    addBetween = true;
                }

            if(randomIndex1 == 1 && bottom)
                if(m.maze[chosen.row+2][chosen.col] == 0 )
                {
                    m.setPath(new Position(chosen.row+1, chosen.col), 0);
                    addBetween = true;
                }

            if(randomIndex1 == 2 && right)
                if(m.maze[chosen.row][chosen.col+2] == 0 )
                {
                    m.setPath(new Position(chosen.row, chosen.col+1), 0);
                    addBetween = true;
                }

            if(randomIndex1 == 3 && left)
                if(m.maze[chosen.row][chosen.col-2] == 0 )
                {
                    m.setPath(new Position(chosen.row, chosen.col-1), 0);
                    addBetween =true;
                }

        }

            chosen.isVisited = true;
    }

    private boolean topTriangle(Maze m, Position p)
    {
//        if(m.maze[p.row-1][p.col] == 0 && m.maze[p.row][p.col+1]==0 && m.maze[p.row][p.col-1] == 0 &&  m.maze[p.row-1][p.col+1]==0)
        if(m.maze[p.row-1][p.col] == 0 && m.maze[p.row-1][p.col+1]==0 && m.maze[p.row][p.col+1] == 0)
            return false;
        return true;
    }

    private boolean bottomTriangle(Maze m, Position p)
    {
//        if(m.maze[p.row+1][p.col] == 0 && m.maze[p.row][p.col-1]==0 && m.maze[p.row][p.col+1] == 0 && m.maze[p.row+1][p.col-1]==0)
        if(m.maze[p.row+1][p.col] == 0 && m.maze[p.row+1][p.col-1]==0 && m.maze[p.row][p.col-1] == 0)
            return false;
        return true;
    }

    private boolean rightTriangle(Maze m, Position p)
    {
//        if(m.maze[p.row-1][p.col] == 0 && m.maze[p.row+1][p.col]==0 && m.maze[p.row][p.col+1] == 0 && m.maze[p.row+1][p.col+1]==0)
        if(m.maze[p.row+1][p.col] == 0 && m.maze[p.row+1][p.col+1]==0 && m.maze[p.row][p.col+1] == 0)
            return false;
        return true;
    }

    private boolean leftTriangle(Maze m, Position p)
    {
//        if(m.maze[p.row-1][p.col] == 0 && m.maze[p.row+1][p.col]==0 && m.maze[p.row][p.col-1] == 0 && m.maze[p.row-1][p.col-1]==0)
        if(m.maze[p.row-1][p.col] == 0 && m.maze[p.row-1][p.col-1]==0 && m.maze[p.row][p.col-1] == 0)
            return false;
        return true;
    }

    private  void  setStartPoint(Maze m){
        Random rand = new Random();
        int randRow;
        int randCol;
        boolean startPointFound = false;
        boolean topFrame = false;
        boolean bottomFrame = false;
        boolean rigthFrame = false;
        boolean leftFrame = false;
        while(true) {
            randRow = rand.nextInt(m.row);
            randCol = rand.nextInt(m.col);
            topFrame = randRow == 0;
            bottomFrame= randRow == m.row-1;
            rigthFrame = randCol == m.col-1;
            leftFrame = randCol == 0;
            startPointFound = m.maze[randRow][randCol] == 0;


            if((topFrame|| bottomFrame || rigthFrame || leftFrame) && startPointFound)
            {
                m.setStartingPoint(randRow, randCol);
                break;
            }
//        else if(bottomFrame&& startPointFound)
//        {
//            m.setStartingPoint(randRow, randCol);
//            break;
//        }
//        else if(rigthFrame&& startPointFound)
//        {
//            m.setStartingPoint(randRow, randCol);
//            break;
//        }
//        else if(leftFrame&& startPointFound)
//        {
//            m.setStartingPoint(randRow, randCol);
//            break;
//        }

//        if (! startPointFound && ((randRow == 1) || (randCol == 1) || (randRow == m.row - 2) || (randCol == m.col - 2)) && m.maze[randRow][randCol] == 0) {
//            m.setStartingPoint(randRow, randCol);
//            startPointFound = true;
//        }
        }
    }

    private  void  setEndingPoint(Maze m) {
        Random rand = new Random();
        int randRow;
        int randCol;
        boolean endPointFound = false;
        boolean s_topFrame = m.getStartPosition().getRowIndex() == 0;
        boolean s_bottomFrame = m.getStartPosition().getRowIndex() == m.row - 1;
        boolean s_rigthFrame = m.getStartPosition().getColumnIndex() == m.col - 1;
        boolean s_leftFrame = m.getStartPosition().getColumnIndex() == 0;
        while (true) {
            randRow = rand.nextInt(m.row);
            randCol = rand.nextInt(m.col);
            endPointFound = m.maze[randRow][randCol] == 0;
            if (s_topFrame && (randRow != 0) && endPointFound) // Start Point is on the top frame
            {
                if (randRow == m.row - 1 || randCol == m.col - 1 || randCol == 0) //ending point bottom/right/left frame
                {
                    m.setEndingPoint(randRow , randCol);
                    break;
                }
            }
            else if (s_bottomFrame && (randRow != m.row - 1)&& endPointFound) // Start Point is on the bottom frame
            {
                if (randRow == 0 || randCol == m.col - 1 || randCol == 0) //Ending point is on the top/right/left frame
                {
                    m.setEndingPoint(randRow , randCol);
                    break;
                }
            }
            else if (s_rigthFrame && (randCol != m.col - 1)&& endPointFound) // Start Point is on the right frame
            {
                if (randCol == 0 || randRow == 0 ||randRow == m.row - 1) //ending point left/top/bottom frame
                {
                    m.setEndingPoint(randRow, randCol);
                    break;
                }
            }
            else if (s_leftFrame && (randCol != 0)&& endPointFound) // Start Point is on the left frame
            {
                if (randCol == m.col - 1 ||randRow == 0 || randRow == m.row - 1) //ending point right/top/bottom frame
                {
                    m.setEndingPoint(randRow, randCol);
                    break;
                }
            }
        }
    }



}
