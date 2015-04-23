import java.awt.Color;
import java.util.*;



public class Game {
	LinkedList <Node> board=new LinkedList<Node>();
	State state;
	LinkedList <Node> pMove=new LinkedList<Node>();
	String color="red";
	int limit;
	
	
	void initial(String color,int limit){
		//initial board
		this.limit=limit;
		for(int i=0;i<3;i++)
			for(int j=3-i;j<=4+i;j++)
				board.add(new Node(i,j));
		for(int i=3;i<11;i++)
			for(int j=0;j<8;j++)
				board.add(new Node(i,j));
		for(int i=11;i<14;i++)
			for(int j=3-(13-i);j<=4+(13-i);j++)
				board.add(new Node(i,j));
		//initial player one unit
		LinkedList <Node> player1=new LinkedList<Node>();
		if(color=="red"){
			for(int i=2;i<=5;i++){
				Node d=new Node(4,i);
				player1.add(d);
			}
			for(int i=3;i<=4;i++){
				Node d=new Node(5,i);
				player1.add(d);
			}
		}else{
			for(int i=2;i<=5;i++){
				Node d=new Node(9,i);
				player1.add(d);
			}
			for(int i=3;i<=4;i++){
				Node d=new Node(8,i);
				player1.add(d);
			}
		}
		//initial palyer two unit
		LinkedList <Node> player2=new LinkedList<Node>();//AI player
		if(color=="red"){
			for(int i=2;i<=5;i++){
				Node d=new Node(9,i);
				player2.add(d);
			}
			for(int i=3;i<=4;i++){
				Node d=new Node(8,i);
				player2.add(d);
			}
		}else{
			for(int i=2;i<=5;i++){
				Node d=new Node(4,i);
				player2.add(d);
			}
			for(int i=3;i<=4;i++){
				Node d=new Node(5,i);
				player2.add(d);
			}
		}
		state=new State(board,player1,player2,false,color);
		this.color=color;
	}
	
	void nextBestMove(){
		if(color=="red")
			state=state.nextBestMove1(limit);
		else
			state=state.nextBestMove1(limit);
	}
	
	
	
	
	
	
	Game(String color,int limit){
		initial(color,limit);
		
	}
	
}
