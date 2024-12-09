/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectosteam2;

/**
 *
 * @author Junior Nuñez
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class ChatGUI extends JFrame {
    private ControlUsuarios controlUs;
    private String paactual;
    private String p2;

    private JTextArea areaMensajes;
    private JTextField campoMensaje;

    public ChatGUI(String usuarioActual, String destinatario) {
        this.paactual = usuarioActual;
        this.p2 = destinatario;
        this.controlUs = new ControlUsuarios();

        setTitle("Chat de " + usuarioActual + " con " + destinatario);
        setSize(800, 600); // Aumentamos el tamaño de la ventana
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Área de mensajes
        areaMensajes = new JTextArea();
        areaMensajes.setEditable(false);
        areaMensajes.setFont(new Font("SansSerif", Font.PLAIN, 16)); // Fuente más grande
        JScrollPane scrollPane = new JScrollPane(areaMensajes);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen adicional
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior
        JPanel panelInferior = new JPanel(new BorderLayout());
        campoMensaje = new JTextField();
        campoMensaje.setFont(new Font("SansSerif", Font.PLAIN, 16)); // Fuente más grande
        campoMensaje.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Margen interno
        panelInferior.add(campoMensaje, BorderLayout.CENTER);

        JButton botonEnviar = new JButton("Enviar");
        botonEnviar.setFont(new Font("SansSerif", Font.BOLD, 16)); // Fuente más grande
        botonEnviar.setPreferredSize(new Dimension(120, 50)); // Botón más grande
        botonEnviar.addActionListener(this::enviarMensaje);
        panelInferior.add(botonEnviar, BorderLayout.EAST);

        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen adicional
        add(panelInferior, BorderLayout.SOUTH);

        cargarHistorial();
        setLocationRelativeTo(null);
        iniciarHiloActualizacion();
    }

    private void cargarHistorial() {
        controlUs.cargarHistorialChat(paactual, p2, this);
    }

    private void enviarMensaje(ActionEvent e) {
        String mensaje = campoMensaje.getText().trim();
        if (!mensaje.isEmpty()) {
            controlUs.enviarMensaje(paactual, p2, mensaje);
            areaMensajes.append(String.format("[%s] %s: %s\n",
                    new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date()),
                    paactual, mensaje));
            campoMensaje.setText("");
        }
    }

    private void iniciarHiloActualizacion() {
        new Thread(() -> {
            long lastModified = 0;
            File archivoChat = controlUs.obtenerArchivoChat(paactual, p2);

            while (true) {
                if (archivoChat.exists() && archivoChat.lastModified() > lastModified) {
                    lastModified = archivoChat.lastModified();

                    SwingUtilities.invokeLater(() -> {
                        areaMensajes.setText("");
                        cargarHistorial();
                    });
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    public void actualizarMensajes(String mensaje) {
        areaMensajes.append(mensaje);
    }
}
