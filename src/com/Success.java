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

    public static void main(String[] args){

int L2 = 3;
int L1 = 1;
    for( int i = 3; i <= 60; i++ ) {
    int L = L1 + L2;
            System.out.println(L); //or whatever output function you have
    L2 = L1;
    L1 = L;
 }
    }
}
