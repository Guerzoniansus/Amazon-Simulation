package com.nhlstenden.amazonsimulatie.models;//package com.nhlstenden.amazonsimulatie.models;// A Java program for Dijkstra's single source shortest path algorithm.
// The program is for adjacency matrix representation of the graph 
// https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/
import java.util.*; 
import java.lang.*; 
import java.io.*; 
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

class ShortestPathCalculator {
    // A utility function to find the vertex with minimum distance value,
    // from the set of vertices not yet included in shortest path tree

    private ShortestPathCalculator() {
        // Private constructor to prevent instantiation
    }

    /**
     * Creates a Queue with the shortest path
     * @param matrix Is used to get the neighbors and a array with the shortest distance to all nodes from the scource
     * @param startingNodeID The ID of the node where the path begins
     * @param endNodeID The ID of the final node where the path needs to end
     * @return An Queue with the shortest path form scource to end
     */
    public static Queue<Integer> calculateShortestPath(int matrix[][], int startingNodeID, int endNodeID)
    {
        startingNodeID = startingNodeID - 1;
        endNodeID = endNodeID - 1;

        int [] dijkstra = dijkstra(matrix, startingNodeID);
        int[] path = {endNodeID};
        int position = 0;
        boolean[] neighbors;
        int current = endNodeID;

        while(path[path.length - 1] != startingNodeID)
        {
            path = makeArraylonger(path);
            position++;
            boolean firstset = false;
            neighbors = neighbors(matrix, current);
            for(int i = 0; i < matrix.length; i++)
            {
                if(path[position] == 0 && neighbors[i] == true && firstset == false)
                {
                    path[position] = i;
                    current = i;
                    firstset = true;
                }
                else if(dijkstra[path[position]] > dijkstra[i] && neighbors[i] == true)
                {
                    path[position] = i;
                    current = i;
                }
            }
        }

        path = reverseArray(path);
        path = deleteFirstnode(path);
        path = Arrays.stream(path).map(ID -> ID = ID + 1).toArray();

        List<Integer> pathAsList = Arrays.stream(path).boxed().collect(Collectors.toList());
        Queue<Integer> queue = new LinkedList<Integer>(pathAsList);
        return queue;
    }


    /**
     * Determins the minimum distance to get to a node
     * @param dist The array with minimum values to get to certain nodes from the scource
     * @param sptSet The array that checks that a node has already bin visited
     * @return the minium distance to get to the next node
     */
    private static int minDistance(int dist[], Boolean sptSet[])
    { 
        // Initialize min value 
        int min = Integer.MAX_VALUE, min_index = -1; 
  
        for (int v = 0; v < dist.length; v++)
            if (sptSet[v] == false && dist[v] <= min) { 
                min = dist[v]; 
                min_index = v; 
            } 
  
        return min_index; 
    } 
  
  
    // Function that implements Dijkstra's single source shortest path 
    // algorithm for a graph represented using adjacency matrix 
    // representation 
    /**
     * Creates a array with the minimum value to get to every node for the scource
     * @param graph Is used to determin how var nodes are and get the minimum value
     * @param src Is the beginning node and what all the calculations are based on
     * @return an array with the minimum value to get to every node from the sourche where the scource is 0
     */
    private static int[] dijkstra(int graph[][], int src)
    {
        int dist[] = new int[graph.length]; // The output array. dist[i] will hold
        // the shortest distance from src to i 
  
        // sptSet[i] will true if vertex i is included in shortest 
        // path tree or shortest distance from src to i is finalized 
        Boolean sptSet[] = new Boolean[graph.length];
  
        // Initialize all distances as INFINITE and stpSet[] as false 
        for (int i = 0; i < graph.length; i++) { 
            dist[i] = Integer.MAX_VALUE; 
            sptSet[i] = false; 
        } 
  
        // Distance of source vertex from itself is always 0 
        dist[src] = 0; 
  
        // Find shortest path for all vertices 
        for (int count = 0; count < graph.length - 1; count++) { 
            // Pick the minimum distance vertex from the set of vertices 
            // not yet processed. u is always equal to src in first 
            // iteration. 
            int u = minDistance(dist, sptSet); 
  
            // Mark the picked vertex as processed 
            sptSet[u] = true; 
  
            // Update dist value of the adjacent vertices of the 
            // picked vertex. 
            for (int v = 0; v < graph.length; v++) 
  
                // Update dist[v] only if is not in sptSet, there is an 
                // edge from u to v, and total weight of path from src to 
                // v through u is smaller than current value of dist[v] 
                if (!sptSet[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) 
                    dist[v] = dist[u] + graph[u][v]; 
        } 
  
        // print the constructed distance array 
        //printSolution(dist); 
        return dist;
    }

    /**
     * Gets the neighbors of a node
     * @param f The 2d array that determins wether two nodes are connected
     * @param scr The node for which the neighbours need to be determined 
     * @return A boolean array with the neighbours form scr as true
     */
    private static boolean[] neighbors(int[][] f, int scr)
    {
        boolean[] neighbors = new boolean[f.length];
        for(int i = 0; i < f.length;i++)
        {
            if(f[scr][i] != 0)
            {
                neighbors[i] = true;
            }
        }
        return neighbors;
    }

    /** Increases the length of an array by one
     * @param path The array to make longer
     * @return newPath that is one longer then path with the same inputs
     */
    private static int[] makeArraylonger(int[] path)
    {
        int[] newPath = new int[path.length + 1];
        for(int i = 0; i < path.length; i ++)
        {
            newPath[i] = path[i];
        }
        newPath[newPath.length - 1] = 0;
        return newPath;
    }

    /**
     * Reverses an array
     * @param path the array to reverse
     * @return path2 which is a reverst array path
     */
    private static int[] reverseArray(int[] path)
    {
        int[] path2 = new int[path.length];
        int pathposition = 0;
        for(int i = path.length-1; i>-1;i--)
        {
            path2[i] = path[pathposition];
            pathposition++;
        } 
        return path2;
    }

    /**
     * Gets rid of the first node in the array
     * @param path The array from which the first node needs to be removed
     * @return path2 which is path without the first node
     */
    private static int[] deleteFirstnode(int[] path)
    {
        int[] path2 = new int[path.length - 1];
        int pathposition = 0;
        for(int i = 1; i < path.length; i++)
        {
            path2[pathposition] = path[i];
            pathposition++;
        }
        return path2;
    }
   
} 

