/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servercontrollersocket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.physicballs.items.*;

/**
 *
 * @author Liam-Portatil
 */
public class ServerControllerSocket extends Thread {

    /**
     * Global parameters
     */
    private static final int PORT = 11111;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean live = true;

    /**
     * Server Controller socket constructor
     */
    public ServerControllerSocket() {
        try {
            Socket socketConnection = new Socket(getServerIP().getHostAddress(), 11111);
            out = new ObjectOutputStream(socketConnection.getOutputStream());
            in = new ObjectInputStream(socketConnection.getInputStream());
            Register();
            this.start();
        } catch (IOException ex) {
            System.out.println("refused server connection");
        }

    }

    @Override
    public void run() {
        while (live) {
            try {
                Object o = in.readObject();
                if (o instanceof Status) {
                    if (((Status) o).ID >= 500) {
                        //Se ha producido un error
                        System.out.println(((Status) o).description);
                        live = false;
                    } else if (((Status) o).ID == 2) {
                        System.out.println(((Status) o).description);
                    }
                } else if (o instanceof Peticion) {
                    switch (((Peticion) o).getAccion()) {

                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
            }
        }
    }

    public void echo(String str) {
        try {
            Peticion p = new Peticion("echo");
            p.pushData(str);
            out.writeObject(p);
        } catch (IOException ex) {

        }
    }

    public void openServer(int x, int y) {
        try {
            Peticion p = new Peticion("open_map");
            p.pushData(x);
            p.pushData(y);
            out.writeObject(p);
        } catch (IOException ex) {

        }
    }

    public void setPlantilla(int[][] plantilla) {
        try {
            Peticion p = new Peticion("set_plantilla");
            p.pushData(plantilla);
            out.writeObject(p);
        } catch (IOException ex) {

        }
    }

    private void Register() {
        try {
            out.writeObject("server_controller");
        } catch (IOException ex) {
        }
    }

    /**
     * Finds the IP of the server using the available port
     *
     * @return
     */
    public InetAddress getServerIP() {
        InetAddress ip = null;
        try {
            DatagramSocket c = new DatagramSocket();
            c.setBroadcast(true);
            c.setSoTimeout(5000);
            byte[] sendData = "/ping".getBytes();
            try {
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), PORT);
                c.send(sendPacket);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();
                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue; // Don't want to broadcast to the loopback interface
                }
                for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                    InetAddress broadcast = interfaceAddress.getBroadcast();
                    if (broadcast == null) {
                        continue;
                    }
                    try {
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 11111);
                        c.send(sendPacket);
                    } catch (Exception e) {
                        System.err.println("ERROR SENDING BROADCAST PACKET");
                    }
                }
            }
            byte[] recvBuf = new byte[15000];
            DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
            c.receive(receivePacket);

            String message = new String(receivePacket.getData()).trim();
            if (message.equals("/ping")) {
                ip = receivePacket.getAddress();
                System.out.println("DISCOVERED IP: " + ip);
            }
            //Close the port!
            c.close();
        } catch (IOException ex) {
            System.err.println("BROADCAST TIMED OUT");
        }
        return ip;
    }
}
