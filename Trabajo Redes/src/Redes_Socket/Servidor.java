/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Redes_Socket;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

/**
 *
 * @author Rolas
 */
public class Servidor extends Frame implements ActionListener, Runnable, KeyListener {

    ServerSocket servidor;
    Socket soket;
    BufferedReader lectura;
    BufferedWriter escritura;
    TextField mensaje;
    TextField usuario;
    Button salir, limpiar;
    Label etiquetaUsuario;
    List contenedorMensajes;
    Panel contenedorVistaMensajes = null;
    Panel contenedorMensajeUsuario = null;
    Panel contenedordMensajeSalir = null;
    Panel contenedorUsuarioLimpiar = null;
    Panel contenedorPrincipal = null;

    public void run() {
        try {
            soket.setSoTimeout(1);
        } catch (Exception e) {
        }
        while (true) {
            try {
                contenedorMensajes.add(lectura.readLine());
            } catch (Exception h) {
            }
            if (contenedorMensajes.getItemCount() == 7) {
                contenedorMensajes.remove(0);
            }

        }
    }

    public Servidor(String m) {
        super(m);

        contenedorPrincipal = new Panel();
        contenedorVistaMensajes = new Panel();
        contenedorMensajeUsuario = new Panel();
        contenedordMensajeSalir = new Panel();
        contenedorUsuarioLimpiar = new Panel();

        contenedorPrincipal.setLayout(new GridLayout(2, 1));
        contenedorVistaMensajes.setLayout(new GridLayout(1, 1));
        contenedorMensajeUsuario.setLayout(new GridLayout(2, 1));
        contenedordMensajeSalir.setLayout(new FlowLayout());
        contenedorUsuarioLimpiar.setLayout(new FlowLayout());


        salir = new Button("Salir");
        limpiar = new Button("Limpiar");
        salir.addActionListener(this);
        limpiar.addActionListener(this);
        contenedorMensajes = new List(50);
        mensaje = new TextField(43);
        usuario = new TextField(10);
        etiquetaUsuario = new Label("Nombre: ");
        etiquetaUsuario.setForeground(Color.white);
        usuario.addKeyListener(this);
        mensaje.addKeyListener(this);
        contenedorVistaMensajes.add(contenedorMensajes);
        contenedordMensajeSalir.add(mensaje);
        contenedordMensajeSalir.add(salir);
        contenedorUsuarioLimpiar.add(etiquetaUsuario);
        contenedorUsuarioLimpiar.add(usuario);
        contenedorUsuarioLimpiar.add(limpiar);
        contenedorMensajeUsuario.add(contenedordMensajeSalir);
        contenedorMensajeUsuario.add(contenedorUsuarioLimpiar);
        contenedorPrincipal.add(contenedorVistaMensajes);
        contenedorPrincipal.add(contenedorMensajeUsuario);

        this.add(contenedorPrincipal);
        setBackground(Color.darkGray);
        setSize(380, 300);
        setLocation(0, 0);
        setVisible(true);
        setResizable(false);
        usuario.requestFocus();
        try {
            servidor = new ServerSocket(100);
            soket = servidor.accept();
            lectura = new BufferedReader(new InputStreamReader(soket.getInputStream()));
            escritura = new BufferedWriter(new OutputStreamWriter(soket.getOutputStream()));
            escritura.write("Bienbenido cliente");
            escritura.newLine();
            escritura.flush();
            Thread th;
            th = new Thread(this);
            th.start();


        } catch (Exception e) {
        }
    }

    public static void main(String args[]) {
        new Servidor("Servidor");

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(salir)) {
            System.exit(0);
        } else if (e.getSource().equals(limpiar)) {
            usuario.setText(" ");
            usuario.setEditable(true);
        }

    }

    public void keyPressed(KeyEvent ke) {
        if (mensaje.equals(ke.getSource())) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                try {
                    escritura.write(usuario.getText() + "╣" + mensaje.getText());
                    escritura.newLine();
                    escritura.flush();
                } catch (Exception m) {
                }

                contenedorMensajes.add(usuario.getText() + "╣" + mensaje.getText());
                mensaje.setText("");

            }
        } else if (usuario.equals(ke.getSource())) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                usuario.setEditable(false);
                mensaje.requestFocus();
            }
        }

    }

    public void keyReleased(KeyEvent ke) {
    }

    public void keyTyped(KeyEvent ke) {
    }
}
