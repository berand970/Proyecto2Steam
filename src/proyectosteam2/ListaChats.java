/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectosteam2;

/**
 *
 * @author Junior Nuñes
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ListaChats extends JFrame {
    private ControlUsuarios userManager;
    private String usuarioActual;

    private JPanel panelUsuarios;

    public ListaChats(String usuarioActual) {
        this.usuarioActual = usuarioActual;
        this.userManager = new ControlUsuarios();

        setTitle("Usuarios Disponibles para Chat");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        panelUsuarios = new JPanel();
        panelUsuarios.setLayout(new BoxLayout(panelUsuarios, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panelUsuarios);
        add(scrollPane, BorderLayout.CENTER);

        JButton botonVolver = new JButton("Volver al Menu Principal");
        botonVolver.addActionListener(e -> {
            dispose();
            new Menu(usuarioActual).setVisible(true);
        });
        add(botonVolver, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
        userManager.obtenerUsuariosDisponibles(usuarioActual, this);
    }

    public void agregarUsuario(String username) {
        JButton botonUsuario = new JButton(username);
        botonUsuario.addActionListener(e -> abrirChat(username));
        panelUsuarios.add(botonUsuario);
        panelUsuarios.revalidate();
        panelUsuarios.repaint();
    }

    private void abrirChat(String destinatario) {
    // JFrame para el usuario actual
    ChatGUI userPOV = new ChatGUI(usuarioActual, destinatario);
    userPOV.setVisible(true);

    // JFrame para el destinatario
    ChatGUI recipientPOV = new ChatGUI(destinatario, usuarioActual);
    recipientPOV.setVisible(true);
}
}