package com;

import com.github.sarxos.webcam.*;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import pieces.*;
import graphics.*;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.util.ArrayList;
import java.util.Arrays;

public class PruebaCamara {

    //public static String []vars= {"H","T","E","Y","I","P","N","X","L","A","O","K"};
    public static Character []vars = {'H','T','E','Y','I','P','N','X','L','A','O','K'};
    public static List<Character> vars2 = new ArrayList<>(Arrays.asList('H','T','E','Y','I','P','N','X','L','A','O','K'));

    
    public  static void main(String[] args) {
        /* Making a webcam preview */
        gettingPreview();
        
        Webcam webcam = getWebcam();                
        BufferedImage capture = getImage(webcam);
        BufferedImage[][] tablero = getChessTable(capture);
        
        /* Creating Board */
        StartGame.board.createSquares();      
        imagesOCR(tablero);
        StartGame.board.setVisible(true);
        
        /* Show takenimage */
        showTablero(tablero);
    }
    
    public static Webcam getWebcam(){
        Webcam webcam = Webcam.getDefault();
        List<Webcam> lista = Webcam.getWebcams();
        if(lista.size()>1){
            webcam = lista.get(1);
        }
        return webcam;
    }
    
    public static BufferedImage getImage(Webcam w){
        w.setViewSize(new Dimension(640, 480));
        w.open();
        
        BufferedImage capture = w.getImage();
        w.close();
        return capture;
    }
    
    public static BufferedImage[][] getChessTable(BufferedImage capture){
        int chunkWidth = 480/ 8; // determines the chunk width and height  
        int chunkHeight = 480 / 8;  
        BufferedImage imgs[][] = new BufferedImage[8][8]; //Image array to hold image chunks  
        
        for (int x = 0; x < 8; x++) 
        {  
            for (int y = 0; y < 8; y++) 
            {  
                imgs[x][y] = capture.getSubimage(x * chunkWidth, y * chunkHeight, chunkWidth, chunkHeight);
            }  
        }  
        return imgs;
    }
    
    public static void savetablero(BufferedImage[][] tablero) throws IOException{
        //writing mini images into image files  
        for (int i = 0; i < 8; i++) 
        {
            for (int j = 0; j < 8; j++) 
            {
                ImageIO.write(tablero[i][j], "jpg", new File("images/img" + i + j + ".jpg"));  
            }
        }
        
    }
    
    public static void showTablero(BufferedImage[][] tablero){
        JPanel panel = new JPanel();
        for(int x = 0; x < 8; x ++)
        {
            for (int y = 0; y < 8; y++) 
            {
                JLabel picLabel = new JLabel(new ImageIcon(tablero[y][x]));
                panel.add(picLabel);
            }
        }
        
        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.add(panel);
        jf.setSize(700, 600);
        jf.setVisible(true);
    }
    
    public static void imagesOCR(BufferedImage[][] images){
        Tesseract instance = Tesseract.getInstance(); 
        String result = "";
        
        try {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    result = instance.doOCR(images[i][j]);
                    textToPieces(result, i, j);
                }
            }
            
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public static void textToPieces(String imageText, int i, int j){
        
        for (int k = 0; k < 12; k++) 
            for (int l = 0; l < imageText.length(); l++) 
                if(vars[k] == imageText.charAt(l)){
                    imageText = vars[k].toString();
                    break;
                }
        
        if(imageText.equals("H")){
           StartGame.board.addPiece(new Pawn("src/pieces_images/Wpawn.png", "white", new int[]{j, i}), j, i);

        } else if(imageText.equals("X")){
           StartGame.board.addPiece(new Rook("src/pieces_images/Wrook.png", "white", new int[]{j, i}), j, i);

        } else if(imageText.equals("K")) {
           StartGame.board.addPiece(new King("src/pieces_images/Wking.png", "white", new int[]{j, i}), j, i);

        } else if(imageText.equals("O")) {
           StartGame.board.addPiece(new Queen("src/pieces_images/Wqueen.png", "white", new int[]{j, i}), j, i);

        } else if(imageText.equals("A")) {
           StartGame.board.addPiece(new Bishop("src/pieces_images/Wbishop.png", "white", new int[]{j, i}), j, i);
           
        } else if(imageText.equals("L")) {
           StartGame.board.addPiece(new Knight("src/pieces_images/Wknight.png", "white", new int[]{j, i}), j, i);

        } else if(imageText.equals("N")) {
           StartGame.board.addPiece(new Pawn("src/pieces_images/Bpawn.png", "black", new int[]{j, i}), j, i);

        } else if(imageText.equals("T")) {
           StartGame.board.addPiece(new Rook("src/pieces_images/Brook.png", "black", new int[]{j, i}), j, i);

        } else if(imageText.equals("P")) {
           StartGame.board.addPiece(new King("src/pieces_images/Bking.png", "black", new int[]{j, i}), j, i);

        } else if(imageText.equals("I")) {
           StartGame.board.addPiece(new Queen("src/pieces_images/Bqueen.png", "black", new int[]{j, i}), j, i);

        } else if(imageText.equals("Y")) {
            StartGame.board.addPiece(new Bishop("src/pieces_images/Bbishop.png", "black", new int[]{j, i}), j, i);

        } else if(imageText.equals("E")) {
            StartGame.board.addPiece(new Knight("src/pieces_images/Bknight.png", "black", new int[]{j, i}), j, i);

        } else {

        }
    }
    
    public static void gettingPreview(){
        int dimensionX = 480 / 8;
        int dimnesionY = 480 / 8;
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Webcam webcam = Webcam.getWebcams().get(1);

        webcam.setViewSize(new Dimension(640, 480));
        WebcamPanel panel = new WebcamPanel(webcam);
        
        panel.setSize(640, 480);
        panel.setLayout(null);
        
        JButton button;
        for (int i = dimensionX; i < 640; i+= dimensionX) {
            button = new JButton();
            button.setBounds(i, 0, 2, 480);
            panel.add(button); 
        }
        
        for (int j = dimnesionY; j < 480; j+= dimnesionY) {
            button= new JButton();
            button.setBounds(0, j, 480, 2);
            panel.add(button);
        }
        
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        JOptionPane.showConfirmDialog(null, "Iniciar Juego");
        frame.dispose();
        webcam.close();
    }
    
}
