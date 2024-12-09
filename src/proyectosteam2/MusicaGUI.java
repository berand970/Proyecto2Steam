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
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MusicaGUI extends JFrame {
    private final JPanel panelGlobal;
    private final JPanel panelBibliotecaPersonal;
    private final Musica gestorMusica;
    private final String usuario;
    private String rutaCancionSeleccionada = null;
    private final boolean esAdmin;

    public MusicaGUI(String usuario, boolean esAdmin) {
        this.usuario = usuario;
        this.esAdmin = esAdmin;
        this.gestorMusica = Musica.getInstance();

        setTitle("Spotify - Usuario: " + usuario);
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Cabecera con título de la aplicación
        JPanel panelTitulos = new JPanel(new GridLayout(1, 2));
        JLabel labelGlobal = new JLabel("Catalogo Global", SwingConstants.CENTER);
        JLabel labelBiblioteca = new JLabel("Mi Biblioteca", SwingConstants.CENTER);
        labelGlobal.setFont(new Font("Arial", Font.BOLD, 18));
        labelBiblioteca.setFont(new Font("Arial", Font.BOLD, 18));
        labelGlobal.setForeground(Color.WHITE);
        labelBiblioteca.setForeground(Color.WHITE);
        panelTitulos.add(labelGlobal);
        panelTitulos.add(labelBiblioteca);
        panelTitulos.setBackground(new Color(34, 34, 34));
        add(panelTitulos, BorderLayout.NORTH);

        // Panel para mostrar las canciones
        panelGlobal = new JPanel();
        panelGlobal.setLayout(new BoxLayout(panelGlobal, BoxLayout.Y_AXIS));
        JScrollPane scrollGlobal = new JScrollPane(panelGlobal);

        panelBibliotecaPersonal = new JPanel();
        panelBibliotecaPersonal.setLayout(new BoxLayout(panelBibliotecaPersonal, BoxLayout.Y_AXIS));
        JScrollPane scrollBiblioteca = new JScrollPane(panelBibliotecaPersonal);

        JPanel panelCentral = new JPanel(new GridLayout(1, 2));
        panelCentral.add(scrollGlobal);
        panelCentral.add(scrollBiblioteca);
        add(panelCentral, BorderLayout.CENTER);

        // Panel con botones de acciones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.setBackground(new Color(50, 50, 50));
        JButton botonAgregarGlobal = new JButton("Añadir Canción Global");
        JButton botonAgregarBiblioteca = new JButton("Añadir a Biblioteca");
        JButton botonEliminarBiblioteca = new JButton("Eliminar de Biblioteca");
        JButton botonReproducir = new JButton("Reproducir");
        JButton botonDetener = new JButton("Detener");
        JButton botonVolver = new JButton("Volver al Menu Principal");

        if (esAdmin) {
            panelBotones.add(botonAgregarGlobal);
        }
        panelBotones.add(botonAgregarBiblioteca);
        panelBotones.add(botonEliminarBiblioteca);
        panelBotones.add(botonReproducir);
        panelBotones.add(botonDetener);
        panelBotones.add(botonVolver);
        add(panelBotones, BorderLayout.SOUTH);

        // Eventos de los botones
        botonAgregarGlobal.addActionListener(e -> agregarCancionGlobal());
        botonAgregarBiblioteca.addActionListener(e -> agregarAColeccionPersonal());
        botonEliminarBiblioteca.addActionListener(e -> eliminarDeBiblioteca());
        botonReproducir.addActionListener(e -> reproducirCancion());
        botonDetener.addActionListener(e -> {
            if (gestorMusica.hayCancionEnReproduccion()) {
                gestorMusica.detenerCancion();
                JOptionPane.showMessageDialog(this, "Reproducción detenida.", "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No hay ninguna canción en reproducción.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        botonVolver.addActionListener(e -> {
            dispose();
            new Menu(usuario).setVisible(true);
        });

        // Cargar canciones al iniciar
        cargarCancionesGlobales();
        cargarBibliotecaPersonal();

        panelGlobal.setBackground(new Color(45, 45, 45));
        panelBibliotecaPersonal.setBackground(new Color(45, 45, 45));
        panelBotones.setBackground(new Color(30, 30, 30));
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    private void cargarCancionesGlobales() {
        panelGlobal.removeAll();
        gestorMusica.cargarCancionesGlobales(this);
        panelGlobal.revalidate();
        panelGlobal.repaint();
    }

    private void cargarBibliotecaPersonal() {
        panelBibliotecaPersonal.removeAll();
        gestorMusica.cargarBibliotecaPersonal(usuario, this);
        panelBibliotecaPersonal.revalidate();
        panelBibliotecaPersonal.repaint();
    }

    private void agregarCancionGlobal() {
        JTextField campoTitulo = new JTextField();
        JTextField campoArtista = new JTextField();
        JTextField campoAlbum = new JTextField();
        JTextField campoDuracion = new JTextField();
        JFileChooser fileChooserMp3 = new JFileChooser();
        JFileChooser fileChooserCaratula = new JFileChooser();

        Object[] mensaje = {
            "Título:", campoTitulo,
            "Artista:", campoArtista,
            "Álbum:", campoAlbum,
            "Duración (en segundos):", campoDuracion,
            "Archivo MP3:", fileChooserMp3,
            "Carátula (JPG/PNG):", fileChooserCaratula
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Agregar Canción al Catálogo Global", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                String titulo = campoTitulo.getText();
                String artista = campoArtista.getText();
                String album = campoAlbum.getText();
                int duracion = Integer.parseInt(campoDuracion.getText());
                File archivoMp3 = fileChooserMp3.getSelectedFile();
                File archivoCaratula = fileChooserCaratula.getSelectedFile();

                // Validaciones
                if (titulo.isEmpty() || artista.isEmpty() || album.isEmpty() || archivoMp3 == null || archivoCaratula == null) {
                    JOptionPane.showMessageDialog(this, "Por favor llena todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validar extensión de archivo MP3
                if (!archivoMp3.getName().toLowerCase().endsWith(".mp3")) {
                    JOptionPane.showMessageDialog(this, "El archivo no es un MP3. Selecciona uno válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean resultado = gestorMusica.agregarCancionGlobal(titulo, artista, album, duracion, archivoMp3.getAbsolutePath(), archivoCaratula);
                if (resultado) {
                    JOptionPane.showMessageDialog(this, "Canción añadida al catálogo global.");
                    cargarCancionesGlobales();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al añadir la canción.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Duración inválida. Ingresa un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void agregarAColeccionPersonal() {
        if (rutaCancionSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una canción del catálogo global.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String titulo = null;
        String artista = null;
        String album = null;
        int duracion = 0;
        String rutaMp3 = null;
        byte[] caratulaBytes = null;

        File carpetaGlobal = new File(Musica.CARPETA_GLOBAL);
        File[] archivos = carpetaGlobal.listFiles();
        if (archivos != null) {
            for (File archivo : archivos) {
                try (RandomAccessFile raf = new RandomAccessFile(archivo, "r")) {
                    String tituloActual = raf.readUTF();
                    String artistaActual = raf.readUTF();
                    String albumActual = raf.readUTF();
                    int duracionActual = raf.readInt();
                    String rutaMp3Actual = raf.readUTF();
                    int tamanioCaratula = raf.readInt();
                    byte[] caratulaActual = new byte[tamanioCaratula];
                    raf.readFully(caratulaActual);

                    if (rutaMp3Actual.equals(rutaCancionSeleccionada)) {
                        titulo = tituloActual;
                        artista = artistaActual;
                        album = albumActual;
                        duracion = duracionActual;
                        rutaMp3 = rutaMp3Actual;
                        caratulaBytes = caratulaActual;
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (titulo == null || rutaMp3 == null) {
            JOptionPane.showMessageDialog(this, "Esta canción ya está en tu biblioteca.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean resultado = gestorMusica.agregarCancionBiblioteca(usuario, titulo, artista, album, duracion, rutaMp3, caratulaBytes);
        if (resultado) {
            JOptionPane.showMessageDialog(this, "Canción añadida a tu biblioteca.");
            cargarBibliotecaPersonal();
        } else {
            JOptionPane.showMessageDialog(this, "Error al añadir la canción a tu biblioteca.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarDeBiblioteca() {
        if (rutaCancionSeleccionada == null) {
            return;
        }

        File carpetaGlobal = new File(Musica.CARPETA_GLOBAL);
        File[] archivosGlobales = carpetaGlobal.listFiles();
        if (archivosGlobales != null) {
            for (File archivo : archivosGlobales) {
                try (RandomAccessFile raf = new RandomAccessFile(archivo, "r")) {
                    raf.readUTF(); // leer título
                    raf.readUTF(); // leer artista
                    raf.readUTF(); // leer álbum
                    raf.readInt(); // leer duración
                    String rutaMp3 = raf.readUTF(); // leer ruta

                    if (rutaCancionSeleccionada.equals(rutaMp3)) {
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (gestorMusica.getRutaCancionActual() != null &&
            gestorMusica.getRutaCancionActual().equals(rutaCancionSeleccionada)) {
            gestorMusica.detenerCancion();
        }

        boolean resultado = gestorMusica.eliminarCancionBiblioteca(usuario, rutaCancionSeleccionada);
        if (resultado) {
            JOptionPane.showMessageDialog(this, "Canción eliminada de tu biblioteca.");
            cargarBibliotecaPersonal();
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar la canción de tu biblioteca.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void reproducirCancion() {
        if (rutaCancionSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una canción para reproducir.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        gestorMusica.reproducirCancion(rutaCancionSeleccionada);
    }
}
