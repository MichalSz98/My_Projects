package Projekt;

import java.io.Console;
import java.util.*;

public class Graf {
    

    public ArrayList<Wierzcholek> listawierzcholki= new  ArrayList<Wierzcholek>();
    public ArrayList<Krawedz2> listakrawedzi = new ArrayList<Krawedz2>();
    public static int licznik=1;

    //Najkrószy cykl    
    private Stack<ArrayList<Integer>> NajkrotszyCykl;
    
    
    public Graf(ArrayList<Krawedz> krawedzie,ArrayList<Punkty> Wierzcholki)
    {
        for (int i=0;i<Wierzcholki.size();i++)
        {
            Wierzcholek temp = new Wierzcholek(Wierzcholki.get(i).returnWierzcholek());
            this.listawierzcholki.add(temp);//lista wierzcholkow
        }

        for (int i=0;i<krawedzie.size();i++)
        {
            Wierzcholek start=null;
            Wierzcholek koniec=null;
            for(int j=0;j<listawierzcholki.size();j++)
            {
                if (krawedzie.get(i).getFromWezelIndex() == listawierzcholki.get(j).wartosc) {
                     start = listawierzcholki.get(j);
                }
                if (krawedzie.get(i).getToWezelIndex() == listawierzcholki.get(j).wartosc)
                {
                    koniec= listawierzcholki.get(j);
                }
            }

            Krawedz2 temp2 = new Krawedz2(start, koniec);
            this.listakrawedzi.add(temp2); //lista krawedzi
        }
       

    }
    
    //  CZĘŚĆ PROJEKTOWA
   
    public void DFS()
    {
        for(int i=0;i<listawierzcholki.size();i++)
        {
            if (!listawierzcholki.get(i).odwiedzony)

            {
                Explore(listawierzcholki.get(i));
            }
        }
    }
    public void Explore(Wierzcholek a)
    {
        a.odwiedzony = true;
        a.pre = licznik;
        licznik+=1;
        for(int i=0;i<listakrawedzi.size();i++)
        {
            if (listakrawedzi.get(i).start.wartosc == a.wartosc)
                if (listakrawedzi.get(i).koniec.odwiedzony == false)
                {
                    Explore(listakrawedzi.get(i).koniec);
                    listakrawedzi.get(i).drzewowa = true;
                }
        }
        a.post = licznik;
        licznik+=1;

    }

    public   ArrayList<Krawedz2> ZnajdzPowrotne(ArrayList<Krawedz2> E)
    {
        ArrayList<Krawedz2> Powrotne = new ArrayList<Krawedz2>();
        for(int i=0;i<E.size();i++)
        {
            if (E.get(i).koniec.pre < E.get(i).start.pre && E.get(i).start.pre < E.get(i).koniec.post && E.get(i).start.post < E.get(i).koniec.post && !E.get(i).drzewowa)
                Powrotne.add(E.get(i));
        }
        return Powrotne;
    }

    public String  ZnajdzCykl(Wierzcholek s)
    {

        for(int i=0;i<listawierzcholki.size();i++)
        {
            listawierzcholki.get(i).odwiedzony = false;
            listawierzcholki.get(i).path = "";
        }
        Queue<Wierzcholek> Q = new LinkedList<Wierzcholek>();

        Q.add(s);
        Wierzcholek tmp;

        while (!Q.isEmpty())
        {
            tmp = Q.remove();
            for(int i=0;i<listakrawedzi.size();i++)
            {
                if (listakrawedzi.get(i).start == tmp)
                {
                    if (!listakrawedzi.get(i).koniec.odwiedzony)
                    {
                        Q.add(listakrawedzi.get(i).koniec);
                        listakrawedzi.get(i).koniec.path += tmp.path + tmp.wartosc;
                    }
                    if (listakrawedzi.get(i).koniec.wartosc == s.wartosc)
                    {
                        String sciezka=tmp.path +tmp.wartosc + s.wartosc;
                        return sciezka;
                    }
                }

                tmp.odwiedzony = true;
            }

        }
        return "";

    }
    ////    Zwróć Stos z najmniejszymi cyklami
    public Stack<ArrayList<Integer>> ReturnShortestCycle()
    {

        Stack <ArrayList<Integer>>NajkrotszyCykl = new Stack<ArrayList<Integer>>();
        DFS();
        for(int i=0;i<listawierzcholki.size();i++)
        {
            System.out.println("post: "+listawierzcholki.get(i).post+"pre:  "+listawierzcholki.get(i).pre+"id: "+listawierzcholki.get(i).wartosc);
        }
        for(int i=0;i<listakrawedzi.size();i++)
        {
            System.out.println("start: "+listakrawedzi.get(i).start.wartosc+"koniec: "+listakrawedzi.get(i).koniec.wartosc);
        }
        ArrayList<Krawedz2> Powrotne = ZnajdzPowrotne(listakrawedzi);
        int mincylk=3000;
        String najmniejszy="";
        String a="";
        for(int i=0;i<Powrotne.size();i++)
        {
             a=ZnajdzCykl(Powrotne.get(i).start);

             if(a.length()<mincylk)
             {
                 mincylk=a.length();
                 najmniejszy=a;
             }
        }
        for(int i=0;i<najmniejszy.length();i++)
        {
            int tmp=najmniejszy.charAt(i);
        }
         System.out.println(najmniejszy);

        ArrayList<Integer> t = new ArrayList<>();
        for (int i=0;i<najmniejszy.length();i++)
        {
            int wartosc = najmniejszy.charAt(i)-48;
            t.add(wartosc);
        }
        NajkrotszyCykl.push(t);

        return NajkrotszyCykl;
    }
}