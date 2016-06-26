package graphics;

/**
 *
 * @author gerald
 */
import com.PruebaCamara;
import com.serialsample;
import static com.serialsample.difference;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.CompoundBorder;

import logic.*;

public class Board extends JFrame implements MouseListener
{
	
    public static JPanel[][] squares = new JPanel[9][9];
    public static pieces.Piece pieceSelected;
    public static boolean isHumanTurn = true;
    
    public Board() 
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 700);
        setLayout(new GridLayout(9, 8));
    }
    
    public void createSquares()
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                JPanel panel = new JPanel();
                panel.addMouseListener(this);
                panel.setBackground(getColor(i, j));
                panel.setName(i + "," + j);
                add(panel);
                squares[i][j] = panel;
            }
        }
        
        JPanel panel = new JPanel();
        JButton button = new JButton("Turno terminado");
        panel.add(button);
        add(panel);
        squares[0][8] = panel;

        button.addActionListener((ActionEvent e) -> {
            MakeMove.makeArrayForTree();
            AlphaBethaPruning prune = new AlphaBethaPruning(MakeMove.matrixPieces);
            MakeMove.newBoardMachine = prune.YourTurn("black");
            System.out.println("Finished Chess");
            
            /* Send info to Arduino */
            serialsample main = new serialsample();
            main.initialize();
            String[] palabras = difference(MakeMove.matrixPieces, MakeMove.newBoardMachine);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
            }
            //String[] palabras = {"Hola","Adios"};
            for (String palabra : palabras) {
                try {
                    main.escribir(palabra);
                } catch (IOException ex) {
                    Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                }
            while(serialsample.response == null){}
                serialsample.response = null;
            }

            System.out.println("Started");
            main.close();
            this.dispose();
            PruebaCamara.main(null);
        });
    }
    
    
    public Color getColor(int x, int y)
    {
        return (x + y) % 2 == 0 ? Color.WHITE : Color.GRAY;
    }
    
    public void addPiece(pieces.Piece p, int x, int y)
    {
        squares[x][y].add(p);
    }
    
    public void removePiece(int x, int y)
    {
        squares[x][y].remove(0);
    }
    
    public void repaintBoard()
    {
    	paintAll(getGraphics());
    }
    
    public static void cleanBoard()
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
            	squares[i][j].setBorder(null);            
            }
        }
    	
    }
      
    /* Mouse Events */
    @Override
    public void mouseClicked(MouseEvent e)
    {    	
    	if(isHumanTurn){
    		
	    	/* 0 means there is not a piece, 1 means there is a piece */
	    	int squareHasPiece = e.getComponent().getAccessibleContext().getAccessibleChildrenCount();
	    	
	        /* You have selected a piece */
	        if(squareHasPiece != 0 && PossibleMoves.possibleMoves.isEmpty()){
	            pieces.Piece piece = (pieces.Piece) e.getComponent().getAccessibleContext().getAccessibleChild(0);
	            PossibleMoves.reviewPiece(piece);
	            pieceSelected = piece;
	        }
	        
	        /* You had made a move */
        	/* If the JPanel that is wanted to move is in the possibleMoves, do it */
	        else if(PossibleMoves.possibleMoves.contains(e.getComponent())){
            	MakeMove.movePiece(pieceSelected, e.getComponent().getName());
            	cleanBoard();
	        	PossibleMoves.possibleMoves.clear();
	        }
	        
	        /* If you have clicked a panel that doesnt has nothing */
	        else{
	        	cleanBoard();
	        	PossibleMoves.possibleMoves.clear();
	        }
    	} 
    }

    
    public static void setBorders(int x, int y)
    {
    	if(isHumanTurn){
	    	Board.squares[x][y].setBorder(new CompoundBorder(
	        	    BorderFactory.createMatteBorder(0, 0, 0, 0, Color.RED), 
	        	    BorderFactory.createMatteBorder(3, 3, 3, 3, Color.DARK_GRAY)));
    	}
    }
    
    @Override
    public void mousePressed(MouseEvent e){}

    @Override
    public void mouseReleased(MouseEvent e){}

    @Override
    public void mouseEntered(MouseEvent e){}

    @Override
    public void mouseExited(MouseEvent e){}
   
}
