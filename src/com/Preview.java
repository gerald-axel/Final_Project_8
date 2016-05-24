/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import static com.PruebaCamara.getWebcam;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.geom.Line2D;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Gerald Alba
 */
public class Preview extends JFrame{
    
    public static void main(String[] args) {
        
        /*This is a preview of the image that the webcam is going to take */
        JFrame frame = new JFrame();
        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Webcam webcam = Webcam.getWebcams().get(1);

        webcam.setViewSize(new Dimension(640, 480));
        WebcamPanel panel = new WebcamPanel(webcam);
        
        panel.setSize(webcam.getImage().getWidth(), webcam.getImage().getHeight());
        panel.setLayout(null);
        
        JButton button;
        for (int i = 80; i < panel.getWidth(); i+= 80) {
            button = new JButton();
            button.setBounds(i, 0, 2, 480);
            panel.add(button); 
        }
        
        for (int j = 60; j < panel.getHeight(); j+= 60) {
            button= new JButton();
            button.setBounds(0, j, 640, 2);
            panel.add(button);
        }
        
        frame.add(panel);
        frame.setVisible(true);
    }
    
}
