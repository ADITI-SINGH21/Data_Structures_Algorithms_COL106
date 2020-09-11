package col106.assignment6;

public class Vertex {
    int i;
    int j;
    int key;

    public Vertex(int i, int j, int key){
        this.i = i;
        this.j = j;
        this.key = key;
    }

    public int geti(){
    	return this.i;
    }

    public int getj(){
    	return this.j;
    }

    public int getkey(){
    	return this.key;
    }

}