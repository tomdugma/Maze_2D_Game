package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;

public abstract  class AMazeGenerator implements IMazeGenerator {


    @Override
    public long measureAlgorithmTimeMillis(int i, int j) {

        if (i <= 0 || j <= 0) {
            System.out.println("wrong parameters given");
            return -1;
        }

       long time1 =  System.currentTimeMillis();
       Maze tmp = generate(i,j);
       if(tmp == null)
       {
           System.out.println("generate function provided null");
           return -1;
       }

       long time2 =  System.currentTimeMillis();
       return time2-time1;

    }
}