package col106.assignment6;
/**
 * Directed, weighted Edge class
 */
import java.util.Comparator;
public class Edge implements Comparator<Edge>{
    private  int u;
    private  int v;
    private  double weight;
    public boolean visited;
    public double cost;

    /**
     * Initializes a directed edge from vertex u to vertex v
     * @param u the source vertex
     * @param v the destination vertex
     * @param weight the weight of the directed edge
     * @throws IllegalArgumentException if either u or v is a negative integer
     * @throws IllegalArgumentException if {@code weight} is {@code NaN}
     */

	public Edge(){}
    public Edge(int u, int v, double weight) {
        if (u < 0) throw new IllegalArgumentException("Vertex names must be nonnegative integers");
        if (v < 0) throw new IllegalArgumentException("Vertex names must be nonnegative integers");
        if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
        this.u = u;
        this.v = v;
        this.weight = weight;
        this.visited = false;
        this.cost = Double.MAX_VALUE;
    }

    /**
     * Returns the tail vertex of the directed edge.
     * @return the tail vertex of the directed edge
     */
    public int from() {
        return u;
    }

    /**
     * Returns the head vertex of the directed edge.
     * @return the head vertex of the directed edge
     */
    public int to() {
        return v;
    }

    /**
     * Returns the weight of the directed edge.
     * @return the weight of the directed edge
     */
    public double weight() {
        return weight;
    }

    public boolean isVisited(){
    	return visited;
    }
    public void setCost(double d){
    	this.cost = d;
    }
    public double getCost(){
    	return this.cost;
    }
    public void visit(){
    	visited = true;
    }
    public void unvisit(){
    	visited = false;
    }
    @Override
    public int compare(Edge node1, Edge node2) 
    { 
        if (node1.cost < node2.cost) 
            return -1; 
        if (node1.cost > node2.cost) 
            return 1; 
        return 0; 
    } 
    

}