package col106.assignment6;

import java.util.ArrayList;

interface ShortestPathInterface {
    // Dual graph
    public int numDualNodes();
    public int numDualEdges();
    public ArrayList<int[]> dualGraph();

    // Shortest path
    public boolean hasValidPath();
    public int ShortestPathValue();
    public ArrayList<int[]> getShortestPath();    
}