package logic;

import graphics.Board;
import pieces.Piece;

public class MakeMove
{
	public static pieces.Piece [][]matrixPieces; 
	public static pieces.Piece [][]newBoardMachine;
	
	public MakeMove(){}
	
	public static void movePiece(Piece piece, String coordenatesToMove)
	{
		String []coord = coordenatesToMove.split(",");
		int newCoordenateX = Integer.parseInt(coord[0]);
		int newCoordenateY = Integer.parseInt(coord[1]);
						
		int oldPieceCoordenates[] = piece.getCoordenates();
		
		reviewIfIsEating(newCoordenateX, newCoordenateY);
		generateNewPiece(newCoordenateX, newCoordenateY, piece);
		graphics.StartGame.board.removePiece(oldPieceCoordenates[0], oldPieceCoordenates[1]);
		graphics.StartGame.board.repaintBoard();
                PossibleMoves.possibleMoves.clear();
		changeTurn();
		
		/*if(!Board.isHumanTurn)
		{
			makeArrayForTree();
			AlphaBethaPruning prune = new AlphaBethaPruning(matrixPieces);
			newBoardMachine = prune.YourTurn("black");
			removeAllPieces();
			newBoard();
        	PossibleMoves.possibleMoves.clear();
			graphics.StartGame.board.repaintBoard();
			changeTurn();
		}
		*/
	}
	
	public static void generateNewPiece(int newCoordenateX, int newCoordenateY, Piece piece)
	{
		String colorImage = piece.getTeam().equals("white") ? "W" : "B";
		
        switch(piece.getType()){
	        case "Pawn":
	            graphics.StartGame.board.addPiece(new pieces.Pawn("src/main/java/pieces_images/" + colorImage + "pawn.png", piece.getTeam(), new int[]{newCoordenateX, newCoordenateY}), newCoordenateX, newCoordenateY);
	    		
	            if(!(newCoordenateX == 1 || newCoordenateX == 6)){
	    			((Piece) Board.squares[newCoordenateX][newCoordenateY].getAccessibleContext().getAccessibleChild(0)).setMovesCounter();
	    		} 
	            
	            break;
	        
	        case "Knight":
	            graphics.StartGame.board.addPiece(new pieces.Knight("src/main/java/pieces_images/" + colorImage + "knight.png", piece.getTeam(), new int[]{newCoordenateX, newCoordenateY}), newCoordenateX, newCoordenateY);
	            break;
	            
	        case "King":
	            graphics.StartGame.board.addPiece(new pieces.King("src/main/java/pieces_images/" + colorImage + "king.png", piece.getTeam(), new int[]{newCoordenateX, newCoordenateY}), newCoordenateX, newCoordenateY);
	            break;
	            
	        case "Bishop":
	            graphics.StartGame.board.addPiece(new pieces.Bishop("src/main/java/pieces_images/" + colorImage + "bishop.png", piece.getTeam(), new int[]{newCoordenateX, newCoordenateY}), newCoordenateX, newCoordenateY);
	            break;
	            
	        case "Rook":
	            graphics.StartGame.board.addPiece(new pieces.Rook("src/main/java/pieces_images/" + colorImage + "rook.png", piece.getTeam(), new int[]{newCoordenateX, newCoordenateY}), newCoordenateX, newCoordenateY);
	            break;
	            
	        case "Queen":
	            graphics.StartGame.board.addPiece(new pieces.Queen("src/main/java/pieces_images/" + colorImage + "queen.png", piece.getTeam(), new int[]{newCoordenateX, newCoordenateY}), newCoordenateX, newCoordenateY);
	            break;
        }
        
	}
	
	public static void reviewIfIsEating(int x, int y)
	{
        int squarePiece = Board.squares[x][y].getAccessibleContext().getAccessibleChildrenCount();
        if(squarePiece != 0){
    		graphics.StartGame.board.removePiece(x, y);
        }
	}
	
	public static void changeTurn()
	{
		Board.isHumanTurn = Board.isHumanTurn ? false : true;
	}
	
	public static void makeArrayForTree()
	{
		matrixPieces = new pieces.Piece[8][8];
		
		for (int i = 0; i < 8; i++) 
		{
			for (int j = 0; j < 8; j++) 
			{
				matrixPieces[i][j] = (pieces.Piece) Board.squares[i][j].getAccessibleContext().getAccessibleChild(0);
			}
		}
	}
	
	public static void removeAllPieces()
	{
		for (int i = 0; i < 8; i++) 
		{
			for (int j = 0; j < 8; j++) 
			{
		    	int squareHasPiece = Board.squares[i][j].getAccessibleContext().getAccessibleChildrenCount();
		    	
		    	if(squareHasPiece != 0){
					graphics.StartGame.board.removePiece(i, j);
		    	}
			}
		}
	}

	public static void newBoard()
	{		
		for (int i = 0; i < 8; i++) 
		{
			for (int j = 0; j < 8; j++) 
			{
				if(newBoardMachine[i][j] != null){
					generateNewPiece(i, j, newBoardMachine[i][j]);
				}
			}
		}
	}
}