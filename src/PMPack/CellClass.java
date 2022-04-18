/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PMPack;

public class CellClass{
    private boolean reachable;
    private int x;
    private int y;
    private int g;
    private int h;
    private int f;
    private CellClass parent;
    
    public CellClass(int X, int Y, boolean reach){
        reachable = reach;
        x = X;
        y = Y;
        g = 0;
        h = 0;
        f = 0;
        parent = null;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public int getF(){
        return f;
    }
    
    public int getG(){
        return g;
    }
    
    public int getH(){
        return h;
    }
    
    public CellClass getParent(){
        return parent;
    }
    
    public boolean getReach(){
        return reachable;
    }
    
    public void setF(int a){
        f = a;
    }
    
    public void setG(int a){
        g = a;
    }
    
    public void setH(int a){
        h = a;
    }
    
    public void setParent(CellClass a){
        parent = a;  //MAYBE remake for deep copying
    }
    
//    @Override
//    public int compareTo(CellClass a){  //Guess we need to mirror the functionality
//        if (this.getF() < a.getF()) return 1;
//        else if (this.getF() == a.getF()) return 0;
//        else return -1;
//    }

    @Override
    public boolean equals(Object a){
        if (a instanceof CellClass){
            boolean output = true;
            if (((CellClass)a).getX() != this.x) output = false;
            if (((CellClass)a).getY() != this.y) output = false;
            if (((CellClass)a).getF() != this.f) output = false;
            if (((CellClass)a).getG() != this.g) output = false;
            if (((CellClass)a).getH() != this.h) output = false;
            if (((CellClass)a).getReach() != this.reachable) output = false;
            return output;
        }
        else return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.reachable ? 1 : 0);
        hash = 67 * hash + this.x;
        hash = 67 * hash + this.y;
        hash = 67 * hash + this.g;
        hash = 67 * hash + this.h;
        hash = 67 * hash + this.f;
        return hash;
    }
}
