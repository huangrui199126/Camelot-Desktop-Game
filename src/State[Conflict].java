import java.util.ArrayList;
import java.util.LinkedList;


public class State {
	LinkedList <Node> board;
	LinkedList <Node> player1;
	LinkedList <Node> player2;//AI player
	boolean turnP2=false;
	int value;
	static int NodesExpanded=0;
	static int maxPrune=0;
	static int minPrune=0;
	String playerColor="red";
	State(LinkedList <Node> board,LinkedList <Node> player1,LinkedList <Node> player2,boolean turnP2,String playerColor){
		this.board=board;//copy a reference is enough final constant,never change
		this.player1=new LinkedList<Node>();
		for(Node i:player1)
			this.player1.add(new Node(i));
		this.player2=new LinkedList<Node>();
		for(Node i:player2)
			this.player2.add(new Node(i));
		this.turnP2=turnP2;
		this.value=Eval();
		this.playerColor=playerColor;
		NodesExpanded++;
	}
	State(State s){
		this.board=s.board;
		this.player1=new LinkedList<Node>();
		for(Node i:s.player1)
			this.player1.add(new Node(i));
		this.player2=new LinkedList<Node>();
		for(Node i:s.player2)
			this.player2.add(new Node(i));
		this.turnP2=s.turnP2;
		this.playerColor=s.playerColor;
		this.value=Eval();
		NodesExpanded++;
	}
	
	
	
	int Eval(){
		int sum=0;
		if(playerColor=="red"){
		for(int i=0;i<player2.size();i++)
			sum+=(50-2*player2.get(i).x-Math.abs(player2.get(i).y-3));
		for(int i=0;i<player1.size();i++)
			sum-=(50-2*(13-player1.get(i).x)-Math.abs(player1.get(i).y-3));
		
		if(player1.size()==0)sum=1000;
		if(player2.size()==0)sum=-1000;
		for(Node n:player1)
			if(n.x==13&& (n.y==3||n.y==4))
				sum=-1000;
		for(Node n:player2)
			if(n.x==0&& (n.y==3||n.y==4))
				sum=1000;
		}else{
			for(int i=0;i<player1.size();i++)
				sum-=(50-2*player1.get(i).x-Math.abs(player1.get(i).y-3));
			for(int i=0;i<player2.size();i++)
				sum+=(50-2*(13-player2.get(i).x)-Math.abs(player2.get(i).y-3));
			
			if(player1.size()==0)sum=1000;
			if(player2.size()==0)sum=-1000;
			for(Node n:player2)
				if(n.x==13&& (n.y==3||n.y==4))
					sum=1000;
			for(Node n:player1)
				if(n.x==0&& (n.y==3||n.y==4))
					sum=-1000;
		}
		return sum;
	}
	
	boolean valid(Node n){
		for(Node i:board)
			if(n.equal(i))return true;
		return false;
	}
	
	boolean OccuByP1(Node n){
		boolean occupy=false;
		for(Node i:player1)
			if(n.equal(i)){
				occupy=true;
			}
		return occupy;
	}
	boolean OccuByP2(Node n){
		boolean occupy=false;
		for(Node i:player2)
			if(n.equal(i)){
				occupy=true;
			}
		return occupy;
	}
	
	ArrayList<State> nextPossibleMove(){
		ArrayList<State> a=new ArrayList<State>();
		boolean capturing=false;
		int size1=player1.size();
		int size2=player2.size();
		if(turnP2){
			for(int l=0;l<player2.size();l++){
				//for 8 direction
				Node p2=player2.get(l);
				for(Direction d: Direction.values()){
					//plain move
					Node n1=new Node(p2.x+d.getX(),p2.y+d.getY());
					if(valid(n1)){										
						if(!(OccuByP1(n1)||OccuByP2(n1))){
							//plain move										should be 28 but get 26
							p2.x+=d.getX();
							p2.y+=d.getY();
							//copy new state to the arraylist
							
							State copy1=new State(this);
							copy1.turnP2=!copy1.turnP2;
							a.add(copy1);
							//move back
							p2.x-=d.getX();
							p2.y-=d.getY();
						}else{
						Node n2=new Node(n1.x+d.getX(),n1.y+d.getY());
						if(valid(n2) && !OccuByP1(n2)&&!OccuByP2(n2)){
							//cantering move
							if(OccuByP2(n1)){
								//move forward
								p2.x+=2*d.getX();
								p2.y+=2*d.getY();
								//copy new state to the arrayllist
								State copy=new State(this);
								copy.turnP2=!copy.turnP2;
								a.add(copy);
								//move back
								p2.x-=2*d.getX();
								p2.y-=2*d.getY();
								}
							//capturing move
							else{
								//move forward
								capturing=true;
								p2.x+=2*d.getX();
								p2.y+=2*d.getY();
								//copy new state to the arrayllist
								LinkedList<Node>p1=new LinkedList<Node>();
									for(Node i:player1)
										if(!n1.equal(i))
											p1.add(i);
								State copy=new State(board,p1,player2,!turnP2,this.playerColor);
								a.add(copy);
								//move back
								p2.x-=2*d.getX();
								p2.y-=2*d.getY();
								}
						}
					}
				}
			}
		}		
			if(capturing){
				ArrayList<State> b=new ArrayList<State>();
				for(State s:a)
					if(s.player1.size()<size1)b.add(s);
				return b;
			}
		}else{
			for(int l=0;l<player1.size();l++){
				//for 8 direction
				Node p1=player1.get(l);
				for(Direction d: Direction.values()){
					//plain move
					Node n1=new Node(p1.x+d.getX(),p1.y+d.getY());
					if(valid(n1)){										
						if(!(OccuByP1(n1)||OccuByP2(n1))){
							//plain move										should be 28 but get 26
							p1.x+=d.getX();
							p1.y+=d.getY();
							//copy new state to the arraylist
							
							State copy1=new State(this);
							copy1.turnP2=!copy1.turnP2;
							a.add(copy1);
							//move back
							p1.x-=d.getX();
							p1.y-=d.getY();
						}else{
						Node n2=new Node(n1.x+d.getX(),n1.y+d.getY());
						if(valid(n2) && !OccuByP1(n2)&&!OccuByP2(n2)){
							//cantering move
							if(OccuByP1(n1)){
								//move forward
								p1.x+=2*d.getX();
								p1.y+=2*d.getY();
								//copy new state to the arrayllist
								State copy=new State(this);
								copy.turnP2=!copy.turnP2;
								a.add(copy);
								//move back
								p1.x-=2*d.getX();
								p1.y-=2*d.getY();
								}
							//capturing move
							else{
								//move forward
								capturing=true;
								p1.x+=2*d.getX();
								p1.y+=2*d.getY();
								//copy new state to the arrayllist
								LinkedList<Node>p2=new LinkedList<Node>();
									for(Node i:player2)
										if(!n1.equal(i))
											p2.add(i);
								State copy=new State(board,player1,p2,!turnP2,this.playerColor);
								a.add(copy);
								//move back
								p1.x-=2*d.getX();
								p1.y-=2*d.getY();
							}
						}
					}
				}
			}
			}
			if(capturing){
				ArrayList<State> b=new ArrayList<State>();
				for(State s:a)
					if(s.player2.size()<size2)b.add(s);
				return b;
			}
		}
		
		return a;
	}

	
	//return nextpossible state of player1's certain node
	ArrayList<State> nextPossibleMoveOfPlayer1(Node p1){
		ArrayList<State> a=new ArrayList<State>();
		boolean capturing=false;
		int size2=player2.size();
				//for 8 direction
				for(Direction d: Direction.values()){
					//plain move
					Node n1=new Node(p1.x+d.getX(),p1.y+d.getY());
					if(valid(n1)){										
						if(!(OccuByP1(n1)||OccuByP2(n1))){
							//plain move										
							p1.x+=d.getX();
							p1.y+=d.getY();
							//copy new state to the arraylist
							
							State copy1=new State(this);
							copy1.turnP2=!copy1.turnP2;
							a.add(copy1);
							//move back
							p1.x-=d.getX();
							p1.y-=d.getY();
						}else{
						Node n2=new Node(n1.x+d.getX(),n1.y+d.getY());
						if(valid(n2) && !OccuByP1(n2)&&!OccuByP2(n2)){
							//cantering move
							if(OccuByP1(n1)){
								//move forward
								p1.x+=2*d.getX();
								p1.y+=2*d.getY();
								//copy new state to the arrayllist
								State copy=new State(this);
								copy.turnP2=!copy.turnP2;
								a.add(copy);
								//move back
								p1.x-=2*d.getX();
								p1.y-=2*d.getY();
								}
							//capturing move
							else{
								//move forward
								capturing=true;
								p1.x+=2*d.getX();
								p1.y+=2*d.getY();
								//copy new state to the arrayllist
								LinkedList<Node>p2=new LinkedList<Node>();
									for(Node i:player2)
										if(!n1.equal(i))
											p2.add(i);
								State copy=new State(board,player1,p2,!turnP2,this.playerColor);
								a.add(copy);
								//move back
								p1.x-=2*d.getX();
								p1.y-=2*d.getY();
							}
						}
					}
				}
			}
				if(capturing){
					ArrayList<State> b=new ArrayList<State>();
					for(State s:a)
						if(s.player2.size()<size2)b.add(s);
					return b;
				}
		return a;
	}
	
	State nextBestMove(int limit){
		NodesExpanded=0;
		maxPrune=0;
		minPrune=0;
		ArrayList<State>a=new ArrayList<State>();
		for(State i: this.nextPossibleMove()){
			i.min_value(limit-1,-1000,1000);
			a.add(i);
		}
		State max=a.get(0);
		for(State i:a)
			if(max.value<i.value)max=i;
		return max;
	}
	
	
	
	int max_value(int limit,int a,int b){
		if(Math.abs(Eval())==1000|| limit ==0){
			value=Eval();
			return value;
		}
		value=-1000;
		for(State i: this.nextPossibleMove()){
			int t=i.min_value(limit-1,a,b);
			value=(value>t)?value:t;
			if(value>=b){
				maxPrune++;
				return value;
			}
			a=(a>value)?a:value;
		}
		return value;	
	}
	

	int min_value(int limit,int a,int b){
		if(Math.abs(Eval())==1000|| limit ==0){
			value=Eval();
			return value;
		}
		value=1000;
		for(State i: this.nextPossibleMove()){
			int t=i.max_value(limit-1,a,b);
			value=(value<t)?value:t;
			if(value<=a){
				minPrune++;
				return value;
			}
			b=(b<value)?b:value;
		}
		return value;	
	}
	
}
