public class Node{
	int x;
	int y;
	
	Node(int x,int y){
		this.x=x;
		this.y=y;
	}
	Node(Node n){
		this.x=n.x;
		this.y=n.y;
	}
	boolean equal(Node n){
		if(this.x==n.x && this.y==n.y)
			return true;
		return false;
	}
}