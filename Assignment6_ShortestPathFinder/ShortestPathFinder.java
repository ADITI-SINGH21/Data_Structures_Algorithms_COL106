package col106.assignment6;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Iterator;
import java.util.LinkedList; 
import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Set;
import java.util.Deque;
import java.util.ArrayDeque;
public class ShortestPathFinder implements ShortestPathInterface {
    /**
     * Computes shortest-path from the source vertex s to destination vertex t 
     * in graph G.
     * DO NOT MODIFY THE ARGUMENTS TO THIS CONSTRUCTOR
     *
     * @param  G the graph
     * @param  s the source vertex
     * @param  t the destination vertex 
     * @param left the cost of taking a left turn
     * @param right the cost of taking a right turn
     * @param forward the cost of going forward
     * @throws IllegalArgumentException unless 0 <= s < V
     * @throws IllegalArgumentException unless 0 <= t < V
     * where V is the number of vertices in the graph G.
     */
    Digraph G;
    int[] s;
    int[] t;
    int left;
    int right;
    int forward;
    PseudoDual Graph;
    ArrayList<ArrayList<Integer>> pathList;
    int k1;
    int k2;
    public ShortestPathFinder (final Digraph G, final int[] s, final int[] t, 
    final int left, final int right, final int forward) {
        // YOUR CODE GOES HERE
        this.G = G;
        this.s = s;
        this.t = t;
        this.left = left;
        this.right = right;
        this.forward = forward;
        this.Graph = new PseudoDual(G,s,t,left,right,forward);
        this.pathList = new ArrayList();
        this.k1 = s[0]*G.W()+s[1];
        this.k2 = t[0]*G.W()+t[1];
        this.Graph.DijkstraAlgo();
    }

    // Return number of nodes in dual graph
    public int numDualNodes() {
        // YOUR CODE GOES HERE
        int nodes = G.E()+1;
        return nodes;
    }

    // Return number of edges in dual graph
    public int numDualEdges() {
        // YOUR CODE GOES HERE
        int n = Graph.edgesCount(); 
        return n;
    }

    // Return hooks in dual graph
    // A hook (0,0) - (1,0) - (1,2) with weight 8 should be represented as
    // the integer array {0, 0, 1, 0, 1, 2, 8}
    public ArrayList<int[]> dualGraph() {
        // YOUR CODE GOES HERE 
        ArrayList<int[]> hook = Graph.getHooks();
        return hook;
    }

    // Return true if there is a path from s to t.
    public boolean hasValidPath() {
        // YOUR CODE GOES HERE
        if(s==t) return true;
        Queue<Integer> queue = new LinkedList<Integer>();
        int vertex = G.V();
        int width = G.W();
        boolean visited[] = new boolean[vertex];
        int key1 = s[0]*width+s[1];
        int key2 = t[0]*width+t[1];
		visited[key1] = true;
		queue.add(key1);
		ArrayList<Edge>[] adj = adjacencyList();
		Iterator<Edge> i = null;
		while(queue.size()!=0){
			int top = queue.poll();
			Edge e = null;
			i = adj[top].listIterator();
			while(i.hasNext()){
				e = i.next();
				int n = e.to();
				if (n==key2) {
					return true;
				}
				if (!visited[n]) {
					visited[n] = true;
					queue.add(n);
				}
			}
		}

        return false;
    }

    // Return the length of the shortest path from s to t.
    public int ShortestPathValue() {
        // YOUR CODE GOES HERE
        int k = t[0]*G.W()+t[1];
        if (!hasValidPath() || s==t) {
        	return 0;
        }
        HashMap<Integer,Edge> edgeMapping = Graph.map();
        double[] dist = Graph.getDist();
        double min = Double.MAX_VALUE;
        for (int i =0; i<dist.length ; i++ ) {
        	Edge e = edgeMapping.get(i);
        	if (e==null) {
        		continue;
        	}
        	if ((int)e.to()==k && min>dist[i]) {
        		min = dist[i];
        	}
        }
        return (int)min;
    }

    // Return the shortest path computed from s to t as an ArrayList of nodes, 
    // where each node is represented by its location on the grid.
    public ArrayList<int[]> getShortestPath() {
        // YOUR CODE GOES HERE
        if (!hasValidPath()) {
        	return null;
        }
        int k = t[0]*G.W()+t[1];
        HashMap<Integer,Edge> edgeMapping = Graph.map();
        double[] dist = Graph.getDist();
        double min = Double.MAX_VALUE;
        Edge target = null;
        for (int i =0; i<dist.length ; i++ ) {
        	Edge e = edgeMapping.get(i);
        	if (e==null) {
        		continue;
        	}
        	if ((int)e.to()==k && min>dist[i]) {
        		min = dist[i];
        		target = e;
        	}
        }
        ArrayList<Edge> edgelist = Graph.getPath(target);
        HashMap<Integer,int[]> map3 = G.getVerticeMap();
        ArrayList<int[]> result = new ArrayList<>();
        for (int i=0; i<edgelist.size();i++ ) {
        	int[] arr = new int[2];
        	Edge e = edgelist.get(i);
        	int K = (int)e.to();
        	arr = map3.get(K);
        	result.add(arr);
        }
        return result;
    }


    public HashMap<Integer,Vertex> getMap(){
    	return G.getHashMap();
    }

    public ArrayList<Edge>[] adjacencyList(){
    	return G.adjacency();
    }

    public Iterable<Edge> getEdges() {
    	return G.edges();
    }

    public Iterable<Edge> adjIncident(int v) {
        return G.adj(v);
    }
}

class PseudoDual{
    ArrayList<DualEdge>[] dualAdj;
    HashMap<Integer,Edge> map;
    HashMap<Edge,Integer> map2;
    int edge;
    ArrayList<int[]> hook;
    Edge zero;
    Digraph G;
    int[] source;
    int[] target;
    HashMap<Edge,Edge> tracked; 
	PriorityQueue<Edge> pq;
	double[] Distance;
	HashSet<Edge> Settled;
	public PseudoDual(Digraph G,int[] source,int[] target,int left,int right,int forward){
		this.G = G;
		this.source = source;
		this.target = target;
		this.dualAdj = new ArrayList[this.G.E()+1];
		this.map = new HashMap<Integer,Edge>();
		this.map2 = new HashMap<Edge,Integer>();
		this.edge = 0;
		this.hook = new ArrayList<>();
		this.tracked = new HashMap<Edge,Edge>();
		this.pq = new PriorityQueue<Edge>(G.E()+1,new Edge());
		this.Distance = new double[G.E()+1];
		this.Settled = new HashSet<Edge>();
		int width = this.G.E();
		ArrayList<Edge> allEdges = this.G.Alledges();
		int k1 = -1;
		int k2 = source[0]*width+source[1];
		int vG = width+1;
		this.zero = new Edge(this.G.V(),k2,0);
		this.map.put(0,this.zero);
		this.map2.put(this.zero,0);
        ArrayList<Edge>[] adjList = this.G.adjacency();
        for (int i=0; i<vG ; i++) {
			this.dualAdj[i] = new ArrayList<DualEdge>();
		}
		for (int i=0;i<allEdges.size(); i++ ) {
			Edge e0 = allEdges.get(i);
			this.map.put(i+1,e0);
			this.map2.put(e0,i+1);
		}
        HashMap<Integer,int[]> verticeMap = this.G.getVerticeMap();
        for (int ver=0;ver<vG;ver++ ) { //filling the adjacency list of dual graph
        	for (int j=0;j<vG;j++ ) {
        		if (ver==j) {
        			continue;
        		}
        		if (this.map.get(ver).to()==this.map.get(j).from()) {
        			if (ver==0) {
        				double we = this.map.get(j).weight();
        				double d = forward;
        				double w = d+we;
        				DualEdge E = new DualEdge(this.map.get(ver),this.map.get(j),w,forward);
        				dualAdj[ver].add(E);
        				int first = this.map.get(ver).from();
        				int mid = this.map.get(ver).to();
	        			int last = this.map.get(j).to();
	        			int[] f = {-1,-1};
	        			int[] m = verticeMap.get(mid);
	        			int[] l = verticeMap.get(last);
	        			int[] array = new int[7];
        				array[0] = f[0];
        				array[1] = f[1];
        				array[2] = m[0];
        				array[3] = m[1];
        				array[4] = l[0];
        				array[5] = l[1];
        				array[6] = (int) this.map.get(j).weight() + forward;
        				this.hook.add(array);

        			}
        			else{
        				int first = this.map.get(ver).from();
	        			int mid = this.map.get(ver).to();
	        			int last = this.map.get(j).to();
	        			int[] f = verticeMap.get(first);
	        			int[] m = verticeMap.get(mid);
	        			int[] l = verticeMap.get(last);
        				if ((f[0]==m[0]&&m[0]==l[0])||(f[1]==m[1]&&m[1]==l[1])) {
        					double we = this.map.get(j).weight();
	        				double d = forward;
	        				double w = d+we;
	        				DualEdge E = new DualEdge(this.map.get(ver),this.map.get(j),w,forward);
	        				dualAdj[ver].add(E);
	        				int[] array = new int[7];
	        				array[0] = f[0];
	        				array[1] = f[1];
	        				array[2] = m[0];
	        				array[3] = m[1];
	        				array[4] = l[0];
	        				array[5] = l[1];
	        				array[6] = (int)this.map.get(j).weight() + forward;
	        				this.hook.add(array);
        				}
        				else{
        					if (f[0]==m[0]&&l[1]>f[1]&&f[0]>l[0]||f[1]==m[1]&&l[1]>f[1]&&f[0]<l[0]) {
        						double we = this.map.get(j).weight();
		        				double d = left;
		        				double w = d+we;
		        				DualEdge E = new DualEdge(this.map.get(ver),this.map.get(j),w,left);
		        				dualAdj[ver].add(E);
		        				int[] array = new int[7];
		        				array[0] = f[0];
		        				array[1] = f[1];
		        				array[2] = m[0];
		        				array[3] = m[1];
		        				array[4] = l[0];
		        				array[5] = l[1];
		        				array[6] = (int)this.map.get(j).weight()+left;
		        				this.hook.add(array);
        					}
        					else{
        						double we = this.map.get(j).weight();
		        				double d = right;
		        				double w = d+we;
		        				DualEdge E = new DualEdge(this.map.get(ver),this.map.get(j),w,right) ;
		        				dualAdj[ver].add(E);
		        				int[] array = new int[7];
		        				array[0] = f[0];
		        				array[1] = f[1];
		        				array[2] = m[0];
		        				array[3] = m[1];
		        				array[4] = l[0];
		        				array[5] = l[1];
		        				array[6] = (int)this.map.get(j).weight() + right;
		        				this.hook.add(array);
        					}
        				}

        			}
        			
        		}
        	}
        }
        for (int a = 0; a < vG ; a++) {
            for (DualEdge e : this.dualAdj[a]) {
                this.edge+=1;
            }
        }		

	}

	public int edgesCount(){
		return edge;
	}

	public ArrayList<int[]> getHooks(){
		return hook;
	}

	public ArrayList<Edge> getPath(Edge target){
		ArrayList<Edge> route = new ArrayList<>();
		Edge e = target;
		if (tracked.get(e)==null) {
			return null;
		}
		route.add(e);
		while(tracked.get(e)!=null){
			e = tracked.get(e);
			route.add(e);
		}
		Collections.reverse(route);
		return route;
	}

	public void DijkstraAlgo(){
		boolean[] check = new boolean[G.E()+1];
		for (int i=0; i<G.E()+1; i++){
			Distance[i] = Double.MAX_VALUE;
		}
		int index = map2.get(zero);
		Distance[index] = 0.0;
		zero.setCost(0.0);
		pq.add(zero);
		while(Settled.size()!=G.E()+1){
			Edge e = pq.remove();
			// System.out.println("e "+e.from()+" "+e.to());
			Settled.add(e); 
			searchN(e);
		}
	}

	public void searchN(Edge e){
		double eDist = -1.0;
		double nDist = -1.0;
		int u = map2.get(e);
		for ( int i = 0; i< dualAdj[u].size() ; i++ ) {
			DualEdge E = dualAdj[u].get(i);
			Edge v = E.endTo();
			if (!Settled.contains(v)) {
				double cost = E.getWeight();
				eDist = cost;
				nDist = Distance[u] + eDist;
				int check = map2.get(v);
				if (nDist<Distance[check]) {
					Distance[check] = nDist;
					v.setCost(nDist);
					tracked.put(v,e);
				}
				// v.setCost(Distance[check]);
				pq.add(v);
			}
		}
	}

	public double[] getDist(){
		return this.Distance;
	}

	public HashMap<Edge,Integer> map2(){
		return this.map2;
	}

	public HashMap<Integer,Edge> map(){
		return this.map;
	}
}

class DualEdge{
	Edge e1;
	Edge e2;
	double value;
	int hook;
	public DualEdge(Edge e1, Edge e2,double value,int hook){
		this.e1 = e1;
		this.e2 = e2;
		this.value = value;
		this.hook = hook;

	}

	public Edge startFrom(){
		return this.e1;
	}

	public Edge endTo(){
		return this.e2;
	}
	public double getWeight(){
		return this.value;
	}
	public int getHook(){
		return this.hook;
	}
}

