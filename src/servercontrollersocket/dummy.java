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
                    c.echo("hola");
                    break;
    
            }
        }

    }

}
