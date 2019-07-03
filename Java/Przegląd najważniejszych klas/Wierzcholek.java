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
public class Wierzcholek {
    public int post=-1;
    public int pre=-1;
    public boolean odwiedzony = false;
    public int wartosc;
    public String path="";
    
    public Wierzcholek(int wartosc)

    {
        this.odwiedzony=false;
        this.wartosc = wartosc;
    }
}
