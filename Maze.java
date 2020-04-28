package algorithms.mazeGenerators;

import javafx.geometry.Pos;

import java.util.ArrayList;

public class Maze {

    int maze[][];
    int row;
    int col;
    Position startingPoint;
    Position endingPoint;

    public Maze(int i, int j) {
        row = i;
        col = j;
        startingPoint = new Position();
        endingPoint = new Position();
        maze = new int[row][col];
    }

    public Maze() {
        row = 0;
        col = 0;
        startingPoint = new Position();
        endingPoint = new Position();
        maze = null;
    }

    public Maze(byte[] b)
    {
        int i = 0;
        boolean startRow = true;
        boolean startCol = false;
        boolean goalRow = false;
        boolean goalCol = false;
        boolean rowsCount = false;
        boolean colsCount = false;
        int currSum = 0;
        Maze mymaze = new Maze();
        int saveStartRowAmout = 0;
        int saveGoalRowAmount = 0;
        int toContinueIndex = 0;
        while (i < b.length)
        {

            while(b[i] != -24)
            {
                currSum += b[i] & 0xFF;
                i++;
            }
            i++;

            if(startRow)
            {
                saveStartRowAmout = currSum;
                startRow = false;
                startCol = true;
                currSum = 0;
                continue;
            }
            if(startCol)
            {
                this.setStartingPoint(saveStartRowAmout, currSum);
                startCol = false;
                goalRow = true;
                currSum = 0;
                continue;
            }
            if(goalRow)
            {
                saveGoalRowAmount = currSum;
                goalRow = false;
                goalCol = true;
                currSum = 0;
                continue;
            }
            if(goalCol)
            {
                this.setEndingPoint(saveGoalRowAmount, currSum);
                goalCol = false;
                rowsCount = true;
                currSum = 0;
                continue;
            }
            if(rowsCount)
            {
                this.row = currSum;
                rowsCount = false;
                colsCount = true;
                currSum = 0;
                continue;
            }
            if(colsCount)
            {
                this.col = currSum;
                toContinueIndex = i;
                break;
            }
        }
        this.maze = new int[this.row][this.col];
        int r=0;
        int c=0;
        for(int t=toContinueIndex;t<b.length;t++)
        {
            this.maze[r][c] = b[t];
            if(c==col-1)
            {
                c=0;
                r++;
            }
            else
            {
                c++;
            }
        }
    }

    public void setPath(Position p, int num)
    {
        maze[p.row][p.col] = num;
    }

    public void setStartingPoint(int i, int j)
    {
        startingPoint.setRow(i);
        startingPoint.setCol(j);
    }

    public void setEndingPoint(int i, int j)
    {
        endingPoint.setRow(i);
        endingPoint.setCol(j);
    }



    public Position getStartPosition()
    {
        return startingPoint;
    }



    public Position getGoalPosition()
    {
        return endingPoint;
    }

    public int[][] getMaze(){return maze;}

    public Maze addFrame(Maze m){

        Maze myMaze = new Maze(m.row+1, m.col+1);
        for (int index = 0; index < myMaze.col; index++)  // CREATING BOTTOM AND UPPER FRAME
        {
            myMaze.setPath(new Position(0, index), 1);
            myMaze.setPath(new Position(myMaze.row-1, index), 1);
        }
        for (int index = 0; index < myMaze.row; index++)  // CREATING RIGHT AND LEFT FRAME
        {
            myMaze.setPath(new Position(index, 0), 1);
            myMaze.setPath(new Position(index, myMaze.col - 1), 1);
        }

        for (row = 1; row < myMaze.row-1 ; row++) {
            for (col = 1; col < myMaze.col -1; col++) {
                myMaze.maze[row][col] = m.maze[row][col];
            }
        }
        return myMaze;
    }

    public void simplePrint()
    {
        for(int i = 0; i < this.maze.length;i++)
        {
            for(int j = 0; j < this.maze[0].length;j++)
            {
                System.out.print(maze[i][j] + ",");
            }
            System.out.println();
        }
    }

    public void Print()
    {
        for (int i =0; i < maze.length;i++)
        {
            for (int j = 0; j < maze[0].length; j++)
            {
                if(this.startingPoint.getRowIndex() == i && this.startingPoint.getColumnIndex() == j && this.startingPoint.getColumnIndex() < maze[0].length-1)
                    System.out.print("S,");
                else if (this.startingPoint.getRowIndex() == i && this.startingPoint.getColumnIndex() == j && this.startingPoint.getColumnIndex() == maze[0].length-1)
                    System.out.print("S");
                else if(this.endingPoint.getRowIndex() == i && this.endingPoint.getColumnIndex() == j && this.endingPoint.getColumnIndex() < maze[0].length-1)
                    System.out.print("E,");
                else if(this.endingPoint.getRowIndex() == i && this.endingPoint.getColumnIndex() == j && this.endingPoint.getColumnIndex() == maze[0].length-1)
                    System.out.print("E");
                else if(j < maze[0].length - 1)
                    System.out.print(maze[i][j]+",");
                else
                    System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }
    public void addToArr(ArrayList<Byte> arr, int num){
        while(num > 255){
            arr.add((byte)255);
            num -= 255;
        }
        arr.add((byte)num);
        arr.add((byte)1000);
    }


    public byte[] toByteArray()
    {

        ArrayList<Byte> tmp =new  ArrayList<>();
        addToArr(tmp,  this.getStartPosition().getRowIndex());
        addToArr(tmp,  this.getStartPosition().getColumnIndex());
        addToArr(tmp,  this.getGoalPosition().getRowIndex());
        addToArr(tmp,  this.getGoalPosition().getColumnIndex());
        addToArr(tmp,  (byte) this.row);
        addToArr(tmp,  (byte) this.col);


//        int size = this.row*this.col + 6;

//        b[0] = (byte) this.getStartPosition().getRowIndex();
//        b[1] = (byte) this.getStartPosition().getColumnIndex();
//        b[2] = (byte) this.getGoalPosition().getRowIndex();
//        b[3] = (byte) this.getGoalPosition().getColumnIndex();
//        b[4] = (byte) this.row;
//        b[5] = (byte) this.col;
        int k = 6;
        for (int i = 0; i < this.row;i++)
        {
            for(int j = 0; j < this.col;j++)
            {
                tmp.add((byte)this.maze[i][j]);
//                b[k] = (byte)this.maze[i][j];
//                k++;
            }
        }
        byte[] b = new byte[tmp.size()];
        for(int i=0 ; i< tmp.size(); i++)
            b[i] = tmp.get(i);


        return b;
    }
}