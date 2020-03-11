package com.example.Dijkstra_algorithm;

import java.util.LinkedList;
import java.util.Queue;

public class Dijkstra {
    private int[][] graph;
    private int[] distances, prev;
    private String message = "";
    final int v;

    public Dijkstra(int[][] graph)
    {
        this.graph = graph;
        this.v = graph.length;
        distances = new int[v];
        prev = new int[v];
    }

    public void doAlgorithm(int firstV)
    {
        for (int i = 0;i < distances.length; i++)
            distances[i] = Integer.MAX_VALUE;

        distances[firstV] = 0;

        Queue<Integer> queue = makeQueue(firstV);

        while (!queue.isEmpty())
        {
            // Edge checking
            for (int u = 0; u < graph.length; u++)
            {
                for (int v = 0; v < graph.length; v++)
                {
                    if (graph [u][v] > 0 && (distances[v] > distances[u] + graph[u][v]))
                    {
                        distances[v] = distances[u] + graph[u][v];
                        prev[v] = u;
                    }
                }
            }
            queue.remove();
        }
        showOutput();
    }

    public LinkedList<Integer> makeQueue(int firstV)
    {
        LinkedList<Integer> lList = new LinkedList<>();
        lList.add(firstV);
        for (int i = 0; i < v; i++)
        {
            if (firstV != i)
                lList.add(i);
        }
        return lList;
    }

    public void showOutput()
    {
        message += "Vertex \t Previous \t Dist from Source";
        for (int i = 0; i < v; i++)
            if (distances[i] < Integer.MAX_VALUE && distances[i] > 0)
                message += "\n" + i + " \t\t\t\t " + prev[i] + "\t\t\t\t\t\t\t " + distances[i];
    }

    public String getMessage()
    {
        return message;
    }

    public int[] getPrevVertices()
    {
        return prev;
    }

    public int[] getDistances()
    {
        return distances;
    }
}
