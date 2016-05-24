/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

/**
 *
 * @author Gerald Alba
 */
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

class Success extends JFrame{

    public Success(){
        JPanel panel=new JPanel();
        getContentPane().add(panel);
        setSize(450,450);

        JButton button =new JButton("press");
        panel.add(button);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Line2D lin = new Line2D.Float(100, 100, 250, 260);
        g2.draw(lin);
    }

    public static void main(String []args){
        Success s=new Success();
        s.setVisible(true);
    }
}
