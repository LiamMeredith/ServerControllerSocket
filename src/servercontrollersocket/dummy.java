/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servercontrollersocket;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.physicballs.items.*;

/**
 *
 * @author Liam-Portatil
 */
public class dummy {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ServerControllerSocket c = new ServerControllerSocket();
        Scanner i = new Scanner(System.in);
        boolean exit = false;
        int option = 0;
        while (!exit) {
            System.out.println("Menu:");
            System.out.println("1. Echo");
            System.out.println("0. exit");
            option = i.nextInt();
            i = new Scanner(System.in);
            switch (option) {
                case 1:
                    c.openServer(3, 2);
                    break;
                case 2:
                    c.getSetting();
                    break;
                case 3:
                    int[][] p = new int[4][2];
                    int[] a = {0, 0};
                    //pantalla uno
                    p[0][0] = 0;
                    p[0][1] = 0;
                    //pantalla dos
                    p[1][0] = 1;
                    p[1][1] = 0;
                    //pantalla tres
                    p[2][0] = 2;
                    p[2][1] = 0;
                    //pantalla cuatro
                    p[3][0] = 1;
                    p[3][1] = 1;
                    c.setPlantilla(p);
                    break;
                case 4:
                    c.startServer();
                    break;
                case 5:
                    int[] w1 = {0, 0};
                    int[] w2 = {1, 0};
                    c.moveWindow(w1, w2);
                    break;
                case 6:
                    int[] w3 = {1, 0};
                    int[] w4 = {2, 0};
                    c.moveWindow(w3, w4);
                    break;
            }
        }

    }

}
