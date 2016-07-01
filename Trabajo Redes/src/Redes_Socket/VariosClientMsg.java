/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Redes_Socket;

/**
 *
 * @author Rolas
 */
import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class VariosClientMsg {

    static DataInputStream in = null;
    static DataInputStream stdIn = new DataInputStream(System.in);

    public static void main(String[] args) throws IOException {
        Socket kkSocket = null;
        PrintStream out = null;
        try {
            kkSocket = new Socket("12.168.100.3", 100);
            out = new PrintStream(kkSocket.getOutputStream());
            in = new DataInputStream(kkSocket.getInputStream());

        } catch (UnknownHostException e) {
            System.err.println("host desconocido");
            System.exit(1);

        } catch (IOException e) {
            System.err.println("nosepuede concta con el host");
            System.exit(1);

        }
        String fromUser;
        Receive R = new Receive();
        R.start();
        while (R.isAlive()) {
            fromUser = stdIn.readLine();
            if ((fromUser != null) && (R.isAlive())) {
                System.out.println("Cliente: " + fromUser);
                out.println(fromUser);
            }
        }
        out.close();
        in.close();
        stdIn.close();
        kkSocket.close();


    }

    static class Receive extends Thread {

        public Receive() {
        }
        String fromServer;

        public void run() {
            try {
                while ((fromServer = in.readLine()) != null) {
                    System.out.println("Server: " + fromServer);
                    if (fromServer.equals("Bye")) {
                        break;
                    }
                }

            } catch (IOException e) {
            }
        }
    }
}