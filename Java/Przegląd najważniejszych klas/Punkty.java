/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projekt;

/**
 *
 * @author DELL
 */
    
public class Punkty {
    private int X, Y,Wierzchołek,X2,Y2;
    public Punkty(int x,int y)
    {
        this.X = x;
        this.Y = y;
    }
    
    public Punkty(int x1,int y1,int x2,int y2)
    {
    this.X = x1;
    this.Y = y1;
    this.X2 = x2;
    this.Y2 = y2;
    }
    
    public Punkty(int x,int y,int Wierzchołek)
    {
        this.X = x;
        this.Y = y;
        this.Wierzchołek = Wierzchołek;
    }
    
    public int returnX()
    {
        return X;
    }
    
    public int returnY()
    {
        return Y;
    }
    
    public int returnX2()
    {
        return X2;
    }
    
    public int returnY2()
    {
        return Y2;
    }
    
    public int returnWierzcholek()
    {
        return Wierzchołek;
    }
}
