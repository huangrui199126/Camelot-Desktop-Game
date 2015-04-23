
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.border.*;

import java.util.*;



public class ChessBoard {
    private static final int ROW=14;
    private static final int COLUMN=8;
    
    
    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JButton[][] chessBoardSquares = new JButton[ROW][COLUMN];
    private JPanel chessBoard;
    private JPanel text;
    private final JLabel message = new JLabel(
            "Chess Champ is ready to play!");
    private static final String COLS = "0123456789";
    
    private JButton newGame=new JButton("New");
    private JButton save=new JButton("Save");
    private JButton restore=new JButton("Restore");
    private JButton next=new JButton("next");
    
    private Node oldP1;
    private Node newP1; 
    private boolean selected=false;
    
    private JLabel depth=new JLabel("3");
    private JLabel numNodes=new JLabel("0");
    private JLabel numprunMax=new JLabel("0");
    private JLabel numprunMin=new JLabel("0");
    private Game g;
    private boolean first=true;
    ChessBoard() {
        initializeGui();
    }

    public final void initializeGui() {
        // set up the main GUI
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        tools.add(newGame); // TODO - add functionality!
        tools.add(save); // TODO - add functionality!
        tools.add(restore); // TODO - add functionality!
        tools.addSeparator();
        tools.add(next); // TODO - add functionality!
        tools.addSeparator();
        tools.add(message);
        text=new JPanel(new GridLayout(0,2));
        text.add(new JLabel("maximum depth:"));
        text.add(depth);
        text.add(new JLabel("number of nodes expanded:"));
        text.add(numNodes);
        text.add(new JLabel("number of times of prunning in max:"));
        text.add(numprunMax);
        text.add(new JLabel("number of times of prunning in min:"));
        text.add(numprunMin);
        gui.add(text, BorderLayout.LINE_END);
        
        
        chessBoard = new JPanel(new GridLayout(0, COLUMN+1));
        chessBoard.setBorder(new LineBorder(Color.BLACK));
        gui.add(chessBoard, BorderLayout.CENTER);
        
        String[] playerColors = {"red", "blue"};
		String playerColor = (String)JOptionPane.showInputDialog(
		                    null, "Please choose the color of the pieces",
		                    "Color options", JOptionPane.PLAIN_MESSAGE,
		                    null, playerColors, "Black");
		
        
        // create the chess board squares
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int ii = 0; ii < ROW; ii++) {
            for (int jj = 0; jj < COLUMN; jj++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                // our chess pieces are 64x64 px in size, so we'll
                // 'fill this in' using a transparent icon..
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);
                b.setBackground(Color.black);
                chessBoardSquares[ii][jj] = b;
            }
        }
        
        g=new Game(playerColor);
        if(playerColor=="blue")
        	g.state.turnP2=true;
        for(Node e:g.board)
        	chessBoardSquares[e.x][e.y].setBackground(Color.white);
        if(playerColor=="red"){
            for(Node e:g.state.player1){
            	chessBoardSquares[e.x][e.y].setBackground(Color.RED);
            }
            for(Node e:g.state.player2)
            	chessBoardSquares[e.x][e.y].setBackground(Color.blue);
    	}else{
    		for(Node e:g.state.player1){
            	chessBoardSquares[e.x][e.y].setBackground(Color.blue);
            }
            for(Node e:g.state.player2)
            	chessBoardSquares[e.x][e.y].setBackground(Color.red);
    	}
        //fill the chess board
        chessBoard.add(new JLabel(""));
        // fill the top row
        for (int ii = 0; ii < COLUMN; ii++) {
            chessBoard.add(
                    new JLabel(COLS.substring(ii, ii + 1),
                    SwingConstants.CENTER));
        }
        // fill the black non-pawn piece row
        for (int ii = 0; ii < ROW; ii++) {
            for (int jj = 0; jj < COLUMN; jj++) {
                switch (jj) {
                    case 0:
                        chessBoard.add(new JLabel("" + (ii ),
                                SwingConstants.CENTER));
                    default:
                        chessBoard.add(chessBoardSquares[ii][jj]);
                }
            }
        }
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                // Increment the counter and set the JButton text
            	g.initial(playerColor);
            	for(Node e:g.board)
                	chessBoardSquares[e.x][e.y].setBackground(Color.white);
            	if(playerColor=="red"){
	                for(Node e:g.state.player1){
	                	chessBoardSquares[e.x][e.y].setBackground(Color.RED);
	                }
	                for(Node e:g.state.player2)
	                	chessBoardSquares[e.x][e.y].setBackground(Color.blue);
            	}else{
            		for(Node e:g.state.player1){
	                	chessBoardSquares[e.x][e.y].setBackground(Color.blue);
	                }
	                for(Node e:g.state.player2)
	                	chessBoardSquares[e.x][e.y].setBackground(Color.red);
            	}
                numNodes.setText("");
                numprunMax.setText("");
                numprunMin.setText("");
            } 
        });
        
        
        
        //actionlistener to next button
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                     // Increment the counter and set the JButton text
				if(g.state.Eval()==1000)
					JOptionPane.showMessageDialog(chessBoard, "player lose", "game ends", 1);
				if(g.state.Eval()==-1000)
					JOptionPane.showMessageDialog(chessBoard, "player win", "game ends", 1);
            	if(g.state.turnP2){
	            	g.nextBestMove(4);
	            	for(Node e:g.board)
	                	chessBoardSquares[e.x][e.y].setBackground(Color.white);
	            	if(playerColor=="red"){
		                for(Node e:g.state.player1){
		                	chessBoardSquares[e.x][e.y].setBackground(Color.RED);
		                }
		                for(Node e:g.state.player2)
		                	chessBoardSquares[e.x][e.y].setBackground(Color.blue);
	            	}else{
	            		for(Node e:g.state.player1){
		                	chessBoardSquares[e.x][e.y].setBackground(Color.blue);
		                }
		                for(Node e:g.state.player2)
		                	chessBoardSquares[e.x][e.y].setBackground(Color.red);
	            	}
	                numNodes.setText(g.state.NodesExpanded+"");
	                numprunMax.setText(g.state.maxPrune+"");
	                numprunMin.setText(g.state.minPrune+"");
	                System.out.println(g.state.Eval());
            	}else{
            		message.setText("player's turn");
            	}
            } 
         });
      //actionlistener to chessBoard
        for(int i=0;i<chessBoardSquares.length;i++){
        	final int row=i;
        	for(int j=0;j<chessBoardSquares[0].length;j++){
        		final int column=j;
        		chessBoardSquares[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                             // Increment the counter and set the JButton text
                    	if(!g.state.turnP2){
                    		if(!selected){//choose unit to move
		                    	boolean choose=false;
		                    	for(Node n:g.state.player1){
		                    		if(n.x==row&&n.y==column){
		                    			choose=true;
		                    			oldP1=n;
		                    			selected=!selected;
		                    			message.setText("choose your move");
		                       		}
		                    	}
		                    	if(!choose)
		                    		message.setText("wrong unit,choose your unit");
		                    }else{//move to the next state
		                    	ArrayList<State>next=g.state.nextPossibleMoveOfPlayer1(oldP1);
		                    	newP1=new Node(row,column);
		                    	boolean valid=true;
		                    	for(Node n:g.state.player1)
		                    		if(newP1.equal(n))
		                    			valid=false;
		                    	if(valid){
		                    		valid=false;
			                    	for(State s:next)
			                    		for(Node n:s.player1)
			                    			if(newP1.equal(n)){
			                    				g.state=s;
			                    				for(Node e:g.board)
			                                    	chessBoardSquares[e.x][e.y].setBackground(Color.white);
			                    				if(playerColor=="red"){
			                		                for(Node e:g.state.player1){
			                		                	chessBoardSquares[e.x][e.y].setBackground(Color.RED);
			                		                }
			                		                for(Node e:g.state.player2)
			                		                	chessBoardSquares[e.x][e.y].setBackground(Color.blue);
			                	            	}else{
			                	            		for(Node e:g.state.player1){
			                		                	chessBoardSquares[e.x][e.y].setBackground(Color.blue);
			                		                }
			                		                for(Node e:g.state.player2)
			                		                	chessBoardSquares[e.x][e.y].setBackground(Color.red);
			                	            	}
			                    				valid=true;
			                    				selected=!selected;
			                    				if(g.state.Eval()==1000)
			                    					JOptionPane.showMessageDialog(chessBoard, "player lose", "game ends", 1);
			                    				if(g.state.Eval()==-1000)
			                    					JOptionPane.showMessageDialog(chessBoard, "player win", "game ends", 1);
			                    				System.out.println(g.state.Eval());
			                    				return;
			                    			}
		                    	}
		                    	if(!valid){
		                    		selected=false;
		                    		message.setText("invalid movement");}
		                    }
                    	}else{
                    		message.setText("It's not your turn, press next button");
                    	}
                    }
                 });
        	}
        }
        
    }

    public final JComponent getChessBoard() {
        return chessBoard;
    }

    public final JComponent getGui() {
        return gui;
    }

   public static void main(String[] args) {
          
        
        
	   Runnable r = new Runnable() {

            @Override
            public void run() {
                ChessBoard cb =
                        new ChessBoard();

                JFrame f = new JFrame("CameLot");
                f.add(cb.getGui());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);

                // ensures the frame is the minimum size it needs to be
                // in order display the components within it
                f.pack();
                // ensures the minimum size is enforced.
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
        
    }
    
}