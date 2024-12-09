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
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    public Menu(String nombreUsuario) {
        setTitle("Menu Principal - Bienvenido " + nombreUsuario);
        setSize(800, 600); // Aumentamos el tamaño de la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20, 20)); // Añadimos más espacio entre los paneles

        // Panel de opciones con un diseño más estilizado en columna
        JPanel panelOpciones = new JPanel();
        panelOpciones.setLayout(new GridLayout(4, 1, 20, 20)); // 4 filas, 1 columna
        panelOpciones.setBackground(new Color(50, 50, 50)); // Fondo oscuro

        // Íconos y redimensionados (cambiamos el orden de los íconos)
        ImageIcon chatIcon = new ImageIcon(new ImageIcon("src/fotos/mail.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        ImageIcon steamIcon = new ImageIcon(new ImageIcon("src/fotos/logoSteam.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        ImageIcon spotifyIcon = new ImageIcon(new ImageIcon("src/fotos/logospotify.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        ImageIcon adminIcon = new ImageIcon(new ImageIcon("src/fotos/ajustes.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));

        // Botones con íconos y texto
        JButton botonChat = new JButton("Chat", chatIcon);
        JButton botonSteam = new JButton("Steam", steamIcon);
        JButton botonSpotify = new JButton("Spotify", spotifyIcon);
        JButton botonAdmin = new JButton("Administrador", adminIcon);

        // Personalización de los botones
        botonChat.setBackground(new Color(45, 45, 45)); // Color de fondo más oscuro
        botonSteam.setBackground(new Color(45, 45, 45));
        botonSpotify.setBackground(new Color(45, 45, 45));
        botonAdmin.setBackground(new Color(45, 45, 45));

        botonChat.setFont(new Font("Arial", Font.BOLD, 20)); // Fuente más grande y en negrita
        botonSteam.setFont(new Font("Arial", Font.BOLD, 20));
        botonSpotify.setFont(new Font("Arial", Font.BOLD, 20));
        botonAdmin.setFont(new Font("Arial", Font.BOLD, 20));

        // Redondeamos los bordes de los botones
        botonChat.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2, true));
        botonSteam.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2, true));
        botonSpotify.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2, true));
        botonAdmin.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2, true));

        // Añadimos los botones al panel
        panelOpciones.add(botonChat);
        panelOpciones.add(botonSteam);
        panelOpciones.add(botonSpotify);
        panelOpciones.add(botonAdmin);

        // Panel inferior con el botón de logout
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelInferior.setBackground(new Color(50, 50, 50)); // Fondo oscuro

        JButton botonLogout = new JButton("Cerrar Sesión");
        botonLogout.setFont(new Font("Arial", Font.BOLD, 24)); // Fuente más grande
        botonLogout.setPreferredSize(new Dimension(250, 60)); // Botón más grande
        botonLogout.setBackground(new Color(230, 50, 50)); // Color de fondo para el botón de logout
        botonLogout.setForeground(Color.WHITE); // Texto blanco
        botonLogout.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2, true)); // Borde blanco

        // Añadimos el botón de logout
        panelInferior.add(botonLogout);

        // Añadimos los paneles al marco
        add(panelOpciones, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        // Acción para cerrar sesión
        botonLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Llogin().setVisible(true);
                dispose();
            }
        });

        // Acción para abrir Spotify
        botonSpotify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean esAdmin = new ControlUsuarios().esAdmin(nombreUsuario);
                new MusicaGUI(nombreUsuario, esAdmin).setVisible(true);
                dispose();
            }
        });

        // Acción para abrir el chat
        botonChat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListaChats(nombreUsuario).setVisible(true);
                dispose();
            }
        });

        // Acción para abrir Steam
        botonSteam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControlUsuarios userManager = new ControlUsuarios();
                boolean esAdmin = userManager.esAdmin(nombreUsuario);
                new SteamGUI(nombreUsuario, esAdmin).setVisible(true);
                dispose();
            }
        });

        // Acción para abrir el panel de administrador
        botonAdmin.addActionListener(e -> {
            ControlUsuarios userManager = new ControlUsuarios();
            if (userManager.esAdmin(nombreUsuario)) {
                dispose();
                new ControlAdmin(nombreUsuario).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "No tienes permisos para acceder al panel de administrador.", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Centrar la ventana
        setLocationRelativeTo(null);
    }
}
