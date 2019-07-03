/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projekt;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class Wierzcholki_Pre_Post {
    int[] Wierzcholki;
    int[] Pre;
    int[] Post;
    
    public Wierzcholki_Pre_Post(int[] W)
    {
        this.Wierzcholki = W;
    }
    
    
    public void AddPreArray(int[] pre)
    {
        this.Pre = pre; 
    }
    
    public void AddPostArray(int[] post)
    {
        this.Post = post;
    }
    
    public void ShowContent()
    {
        System.out.println("Wierzcholki: ");
        for (int i=0;i<Wierzcholki.length;i++)
        {
            System.out.print(Wierzcholki[i]+" ");
        }
        
        System.out.println();
        System.out.println("Pre: ");
        for (int i=0;i<Pre.length;i++)
        {
            System.out.print(Pre[i]+ " ");
        }
        
        System.out.println();
        System.out.println("Post: ");
        for (int i=0;i<Post.length;i++)
        {
            System.out.print(Post[i]+" ");
        }
        System.out.println();
    }
    
}
