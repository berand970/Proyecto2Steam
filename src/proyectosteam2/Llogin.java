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
import java.awt.event.ActionListener;

public class Llogin extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private ControlUsuarios userManager;
    
    public Llogin() {
        userManager = new ControlUsuarios();
        setTitle("Iniciar Sesión");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Espaciado entre los componentes

        // Fondo de la ventana
        getContentPane().setBackground(new Color(40, 40, 40));
        
        // Título
        JLabel titleLabel = new JLabel("Iniciar Sesión");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        // Usuario label y campo
        JLabel usernameLabel = new JLabel("Usuario:");
        usernameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        usernameField.setPreferredSize(new Dimension(200, 30));
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBackground(new Color(60, 60, 60));
        usernameField.setForeground(Color.WHITE);
        usernameField.setCaretColor(Color.WHITE);  // Color del cursor
        usernameField.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1));
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(usernameField, gbc);

        // Contraseña label y campo
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(200, 30));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBackground(new Color(60, 60, 60));
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1));
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(passwordField, gbc);

        // Botón de login
        loginButton = new JButton("Iniciar Sesión");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(70, 130, 180)); // Color de fondo
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(150, 40));
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1));
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (userManager.loginUser(username, password)) {
                    new Menu(username).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
                }
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(loginButton, gbc);

        // Botón de registro
        registerButton = new JButton("Registrar");
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setBackground(new Color(60, 179, 113)); // Color de fondo
        registerButton.setForeground(Color.WHITE);
        registerButton.setPreferredSize(new Dimension(150, 40));
        registerButton.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1));
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SingIn().setVisible(true);
                dispose();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(registerButton, gbc);

        // Centrar la ventana
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new Llogin().setVisible(true);
    }
}
