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

public class VariosServerMsg {

    Vector clientList = new Vector(50);
    int clientCount = 0;

    public static void main(String[] args) throws IOException {
        VariosServerMsg frame = new VariosServerMsg();

    }

    public VariosServerMsg() throws IOException {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        int user = 0;
        boolean listening = true;

        try {
            serverSocket = new ServerSocket(4444);

        } catch (IOException e) {
            System.exit(1);

        }
        while (listening) {
            user++;
            System.err.println("En Esepera de clientes" + user);
            clientSocket = serverSocket.accept();
            MultiServerThread kkms = new MultiServerThread(clientSocket);
            kkms.start();
            System.err.println("fuera uno");

        }

    }

    public class MultiServerThread extends Thread {

        private Socket socket = null;
        int myID;

        public MultiServerThread(Socket socket) {
            super("MultiServerThread");
            this.socket = socket;

        }
        DataInputStream stdin = new DataInputStream(System.in);

        public void run() {
            try {
                DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                PrintStream out = new PrintStream(new BufferedOutputStream(socket.getOutputStream(), 1024), true);
                clientList.add(out);
                myID = (clientCount);
                clientCount++;
                PrintStream out2;
                String inputLine, outputLine;
                outputLine = "Conectado al servidor";
                out.println(outputLine);

                while ((inputLine = in.readLine()) != null)/////FALTO
                {


                    System.out.println("cliene" + inputLine);
                    for (int i = 0; i < clientCount; i++) {
                        out2 = (PrintStream) clientList.elementAt(i);
                        out2.println(inputLine);
                    }
                    if (outputLine.equals("Bye")) {
                        break;
                    }
                }
                clientList.removeElementAt(myID);
                clientCount--;
                out.close();
                in.close();
                socket.close();
                System.out.println("Cerrado..fuera");

            } catch (SocketException e) {
                System.out.println("cliente " + (myID) + "desconectado");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
