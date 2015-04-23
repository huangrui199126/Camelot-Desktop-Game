
public enum Direction {
	UpperLeft(-1,-1),
	Upper(0,-1),
	UpperRight(1,-1),
	Left(-1,0),
	Right(1,0),
	DownLeft(-1,1),
	Down(0,1),
	DownRight(1,1);
	
	private final int x;
	private final int y;
	
	Direction(int x,int y){
		this.x=x;
		this.y=y;
	}
	int getX(){
		return x;
	}
	int getY(){
		return y;
	}
}
