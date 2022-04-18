/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PMPack;

import java.util.ArrayList;
import java.util.Random;

public class MazeBuilderClass {
    private final int width;
    private final int height;
    private int borderWid;
    private int borderHigh;
    private ArrayList<ArrayList<Integer>> maze;
    
    public Random rand = new Random();
    
    public MazeBuilderClass(int w, int h){  //contructor
        if (w < 0) w = -w;
        if (w == 0) w = 19;
        if (w % 2 == 0) w++;

        if (h < 0) h = -h;
        if (h == 0) h = 19;
        if (h % 2 == 0) h++;
        maze = new ArrayList<>();
        
        width = w;
        height = h;
        borderWid = 0;
        borderHigh = 0;
        for (int i = 0; i < h; i++){
            maze.add(new ArrayList<>());
            for (int j = 0; j < w; j++)
                maze.get(i).add(1);  //first fill the maze with walls
        }
        
        for (int i = 1; i < height - 1; i++)
            for (int j = 1; j < width - 1; j++)
                maze.get(i).set(j, 0);  //then fill everything but borders with empty cells
    }
    
    //1 - wall, 0 - empty
    public void generator(){
        //Generate the maze itself
        double placeChance = 0.35;
        for (int i = 1; i < height - 1; i++){
            for (int j = 1; j < width - 1; j++){
                if (i % 2 == 0 && j % 2 == 0){
                    if (rand.nextDouble() > placeChance){  //if we have to spawn a wall
                        maze.get(i).set(j, 1);  //then spawn it
                        //randomly select the next space to fill
                        int a = rand.nextDouble() < 0.5 ? 0 : (rand.nextDouble() < 0.5 ? -1 : 1);
                        int b = a != 0 ? 0 : (rand.nextDouble() < 0.5 ? -1 : 1);
                        maze.get(i + a).set(j + b, 1);
                    }
                }
            }
        }
        
        //"Free" empty spaces surrounded by walls
        for (int i = 1; i < height - 1; i++){
            for (int j = 1; j < width - 1; j++){
                int sum = 0;
                sum += maze.get(i - 1).get(j - 1) == 1 ? 1 : 0;
                sum += maze.get(i - 1).get(j) == 1 ? 1 : 0;
                sum += maze.get(i - 1).get(j + 1) == 1 ? 1 : 0;
                sum += maze.get(i).get(j - 1) == 1 ? 1 : 0;
                sum += maze.get(i).get(j + 1) == 1 ? 1 : 0;
                sum += maze.get(i + 1).get(j - 1) == 1 ? 1 : 0;
                sum += maze.get(i + 1).get(j) == 1 ? 1 : 0;
                sum += maze.get(i + 1).get(j + 1) == 1 ? 1 : 0;
                
                if (maze.get(i).get(j) == 0 && sum == 8){
                    int choice = rand.nextInt(4);
                    switch(choice){
                        case 0:
                            maze.get(i - 1).set(j, 0);
                        case 1:
                            maze.get(i).set(j + 1, 0);
                        case 2:
                            maze.get(i + 1).set(j, 0);
                        case 3:
                            maze.get(i).set(j - 1, 0);
                    }
                }
            }
        }
        
        //Place Pac-Man as close to the center as possible
        int midW = new Integer(Integer.toString(width / 2));
        int midH = new Integer(Integer.toString(height / 2));
        int k = 0;
        int xRes = 0;
        int yRes = 0;
        boolean check = false;
        boolean outerCheck = false;
        while (k < midW && k < midH && !outerCheck){
            for (int i = -k; i <= k; i++){
                for (int j = -k; j <= k; j++){
                    if (maze.get(midH + i).get(midW + j) == 0){
                        maze.get(midH + i).set(midW + j, 2);
                        xRes = midW + j;
                        yRes = midH + i;
                        check = true;
                        break;
                    }
                }
                if (check){
                    outerCheck = true;
                    break;
                }
                else
                    k++;
            }
        }
        
        if (maze.get(yRes).get(xRes - 1) == 1 && maze.get(yRes).get(xRes + 1) == 1 && maze.get(yRes - 1).get(xRes) == 1 &&
                maze.get(yRes + 1).get(xRes) == 1){
            int choice = rand.nextInt(4);
            switch(choice){
                case 0:
                    maze.get(yRes).set(xRes - 1, 0);
                case 1:
                    maze.get(yRes).set(xRes + 1, 0);
                case 2:
                    maze.get(yRes - 1).set(xRes, 0);
                case 3:
                    maze.get(yRes + 1).set(xRes, 0);
            }
        }
        
        //Now place the ghosts
        //I need to find 4 rectangles to place them there
        int randomer = rand.nextInt(2);
        borderWid = midW + randomer;
        borderHigh = midH + randomer;
        for (int l = 0; l < 4; l++){
            check = false;
            outerCheck = false;
            switch(l){
                case 0:
                    while (!outerCheck){
                        for (int i = 0; i <= borderHigh; i++){
                            for (int j = 0; j <= borderWid; j++){
                                if (maze.get(i).get(j) == 0){
                                    maze.get(i).set(j, 100);
                                    check = true;
                                    break;
                                }
                            }
                            if (check){
                                outerCheck = true;
                                break;
                            }
                            else
                                k++;
                        }
                    }
                    break;
                case 1:
                    while (!outerCheck){
                        for (int i = 0; i <= borderHigh; i++){
                            for (int j = width - 1; j > borderWid; j--){
                                if (maze.get(i).get(j) == 0){
                                    maze.get(i).set(j, 100);
                                    check = true;
                                    break;
                                }
                            }
                            if (check){
                                outerCheck = true;
                                break;
                            }
                            else
                                k++;
                        }
                    }
                    break;
                case 2:
                    while (!outerCheck){
                        for (int i = height - 1; i > borderHigh; i--){
                            for (int j = 0; j <= borderWid; j++){
                                if (maze.get(i).get(j) == 0){
                                    maze.get(i).set(j, 100);
                                    check = true;
                                    break;
                                }
                            }
                            if (check){
                                outerCheck = true;
                                break;
                            }
                            else
                                k++;
                        }
                    }
                    break;
                case 3:
                    while (!outerCheck){
                        for (int i = height - 1; i > borderHigh; i--){
                            for (int j = width - 1; j > borderWid; j--){
                                if (maze.get(i).get(j) == 0){
                                    maze.get(i).set(j, 100);
                                    check = true;
                                    break;
                                }
                            }
                            if (check){
                                outerCheck = true;
                                break;
                            }
                            else
                                k++;
                        }
                    }
                    break;
            }
        }
    }
    
    public ArrayList<ArrayList<Integer>> outMaze(){
        return maze;
    }
    
    public int getBordWid(){
        return borderWid;
    }
    
    public int getBordHigh(){
        return borderHigh;
    }
    
    private boolean orientation(int width, int height){
        boolean vert = false;
        if (width < height)
            vert = false;
        else if (height < width)
            vert = true;
        else
            vert = rand.nextInt(2) == 0;
        return vert;
    }
    
    //this is the algo for one step of recursive division; orientation is true when vertical
    //I WON'T BE DOING THIS
    private void division(int startX, int startY, int wid, int heig, boolean orientation){
        if (wid < 3 || heig < 3) return;  //if the maze has reached the smallest resolution
        
        int wx = startX + (orientation ? rand.nextInt(wid - 2) : 0);  //where will the wall start
        int wy = startY + (orientation ? 0 : rand.nextInt(heig - 2));
        
        int px = wx + (orientation ?  0 : rand.nextInt(wid - 1));  //where will the passage be
        int py = wy + (orientation ? rand.nextInt(heig - 1) : 0);
        
        int dx = orientation ? 0 : 1;  //the direction of the wall
        int dy = orientation ? 1 : 0;
        
        int length = orientation ? heig : wid;
        for (int i = 1; i < length; i++){
            if (wx != px || wy != py){
                maze.get(wy).set(wx, 1);
            }
            wx += dx;
            wy += dy;
        }
        
        int newX = startX;
        int newY = startY;
        int w = orientation ? wx - startX + 2 : wid;
        int h = orientation ? heig : wy - startY + 2;
        division (newX, newY, w, h, orientation(w, h));
        
        newX = orientation ? wx + 2 : startX;
        newY = orientation ? startY : wy + 2;
        w = orientation ? startX + wid - wx - 2 : wid;
        h = orientation ? heig : startY + heig - wy - 2;
        division(newX, newY, w, h, orientation(w, h));
    }
}
