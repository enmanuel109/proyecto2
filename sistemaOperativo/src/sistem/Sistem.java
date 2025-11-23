/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sistem;

/**
 *
 * @author Cantarero
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

public class Sistem extends ValidacionUsuarios {

    private JFrame frame;
    private JLabel lblMensaje;
    private JPanel panelFormulario;
    private JPopupMenu menu;
    private JButton btnMenu;

    public Sistem() {
        // Crear JFrame
        frame = new JFrame("Pantalla Completa con Imagen");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        ImageIcon fondoIcon = new ImageIcon("src/IMGS/Fondo.png");
        Image imgFondo = fondoIcon.getImage().getScaledInstance(
                Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height,
                Image.SCALE_SMOOTH
        );
        JLabel fondoLabel = new JLabel(new ImageIcon(imgFondo));
        fondoLabel.setBounds(0, 0,
                Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height);
        frame.add(fondoLabel);

        ImageIcon imgPerfil = new ImageIcon("src/IMGS/PerfilGeneral.png");
        Image imgEscalada = imgPerfil.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel labelImagen = new JLabel(new ImageIcon(imgEscalada));
        labelImagen.setBounds(680, 180, 200, 200);
        fondoLabel.add(labelImagen);

        lblMensaje = new JLabel("");
        lblMensaje.setBounds(645, 360, 350, 25);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 16));
        lblMensaje.setForeground(Color.YELLOW);
        fondoLabel.add(lblMensaje);

        ImageIcon iconoMenu = new ImageIcon("src/IMGS/IconomenuLogin.png");
        Image imgMenu = iconoMenu.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        iconoMenu = new ImageIcon(imgMenu);

        btnMenu = new JButton(iconoMenu);
        btnMenu.setBounds(1450, 705, 50, 50);
        btnMenu.setFocusPainted(false);
        btnMenu.setContentAreaFilled(false);
        btnMenu.setBorderPainted(false);
        fondoLabel.add(btnMenu);

        menu = new JPopupMenu();
        btnMenu.addActionListener(e -> menu.show(btnMenu, 0, -menu.getPreferredSize().height));

        if (Usuario.getUsuarios().size() == 1) {
            mostrarLoginAdmin(fondoLabel);
        } else {
            mostrarFormularioRegistrarse(fondoLabel);
        }

        frame.setVisible(true);
    }

    private void limpiarFormulario(JLabel fondoLabel) {
        if (panelFormulario != null) {
            fondoLabel.remove(panelFormulario);
            panelFormulario = null;
            fondoLabel.revalidate();
            fondoLabel.repaint();
        }
    }

    private void actualizarMenuSegunFormulario(String formularioActivo) {
        menu.removeAll();

        JMenuItem crearUsuarioItem = new JMenuItem("Crear Usuario");
        crearUsuarioItem.addActionListener(e -> mostrarFormularioCrear(panelFormulario.getParent() instanceof JLabel ? (JLabel) panelFormulario.getParent() : null));

        JMenuItem registrarseItem = new JMenuItem("Registrarse");
        registrarseItem.addActionListener(e -> mostrarFormularioRegistrarse(panelFormulario.getParent() instanceof JLabel ? (JLabel) panelFormulario.getParent() : null));

        JMenuItem cerrar = new JMenuItem("Cerrar Windows");
        cerrar.addActionListener(e -> System.exit(0));

        switch (formularioActivo) {
            case "LoginAdmin":
                menu.add(crearUsuarioItem);
                break;
            case "CrearUsuario":
                menu.add(registrarseItem);
                break;
            case "Registrarse":
                menu.add(crearUsuarioItem);
                break;
            default:
                menu.add(crearUsuarioItem);
                break;
        }

        menu.addSeparator();
        menu.add(cerrar);
    }

    // Formularios
    private void mostrarLoginAdmin(JLabel fondoLabel) {
        limpiarFormulario(fondoLabel);
        actualizarMenuSegunFormulario("LoginAdmin");

        panelFormulario = new JPanel();
        panelFormulario.setLayout(null);
        panelFormulario.setBounds(645, 400, 370, 170);
        panelFormulario.setOpaque(false);

        JLabel lblNombre = new JLabel(Usuario.administrador.getNombre());
        lblNombre.setBounds(0, 0, 300, 40);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 20));
        lblNombre.setForeground(Color.WHITE);
        panelFormulario.add(lblNombre);

        JPasswordField campo = new JPasswordField();
        campo.setBounds(0, 50, 280, 40);
        campo.setFont(new Font("Arial", Font.BOLD, 20));
        campo.setEchoChar('*');
        panelFormulario.add(campo);

        ImageIcon ojoIcon = new ImageIcon("src/IMGS/OJO.png");
        Image imgOjo = ojoIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ojoIcon = new ImageIcon(imgOjo);

        JButton btnOjo = new JButton(ojoIcon);
        btnOjo.setBounds(290, 50, 40, 40);
        btnOjo.setFocusPainted(false);
        btnOjo.setContentAreaFilled(false);
        btnOjo.setBorderPainted(false);
        btnOjo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                campo.setEchoChar((char) 0);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                campo.setEchoChar('*');
            }
        });
        panelFormulario.add(btnOjo);

        JButton btnLogin = new JButton("Ingresar");
        btnLogin.setBounds(0, 100, 280, 40);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 18));
        panelFormulario.add(btnLogin);

        btnLogin.addActionListener(e -> {
            String passIngresada = new String(campo.getPassword());
            if (passIngresada.equals(Usuario.administrador.getContraseña())) {
                frame.dispose();
                new Escritorio();
            } else {
                JOptionPane.showMessageDialog(frame, "Contraseña incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        fondoLabel.add(panelFormulario);
        fondoLabel.revalidate();
        fondoLabel.repaint();
    }

    private void mostrarFormularioRegistrarse(JLabel fondoLabel) {
        limpiarFormulario(fondoLabel);
        actualizarMenuSegunFormulario("Registrarse");

        panelFormulario = new JPanel();
        panelFormulario.setLayout(null);
        panelFormulario.setBounds(645, 400, 370, 170);
        panelFormulario.setOpaque(false);

        JLabel lblUsuario = new JLabel("Escriba su Usuario:");
        lblUsuario.setBounds(0, 0, 150, 25);
        lblUsuario.setForeground(Color.WHITE);
        panelFormulario.add(lblUsuario);

        JTextField txtUsuario = new JTextField();
        txtUsuario.setBounds(0, 25, 280, 30);
        panelFormulario.add(txtUsuario);

        JLabel lblContrasena = new JLabel("Escriba su Contraseña:");
        lblContrasena.setBounds(0, 60, 150, 25);
        lblContrasena.setForeground(Color.WHITE);
        panelFormulario.add(lblContrasena);

        JPasswordField txtContrasena = new JPasswordField();
        txtContrasena.setBounds(0, 85, 280, 30);
        panelFormulario.add(txtContrasena);

        ImageIcon ojoIcon2 = new ImageIcon("src/IMGS/OJO.png");
        Image imgOjo2 = ojoIcon2.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ojoIcon2 = new ImageIcon(imgOjo2);

        JButton btnOjo2 = new JButton(ojoIcon2);
        btnOjo2.setBounds(290, 85, 30, 30);
        btnOjo2.setFocusPainted(false);
        btnOjo2.setContentAreaFilled(false);
        btnOjo2.setBorderPainted(false);
        btnOjo2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                txtContrasena.setEchoChar((char) 0);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                txtContrasena.setEchoChar('*');
            }
        });
        panelFormulario.add(btnOjo2);

        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setBounds(30, 130, 200, 30);
        panelFormulario.add(btnEntrar);

        if (Usuario.getUsuarios().size() == 1) {
            txtUsuario.setText(Usuario.administrador.getNombre());
            txtUsuario.setEditable(false);
        }

        btnEntrar.addActionListener(e -> {
            String nombre = txtUsuario.getText().trim();
            String contrasena = new String(txtContrasena.getPassword()).trim();
            if (nombre.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Campos vacíos");
                return;
            }

            boolean encontrado = false;
            for (Usuario u : Usuario.getUsuarios()) {
                if (u.getNombre().equals(nombre) && u.getContraseña().equals(contrasena)) {
                    encontrado = true;
                    break;
                }
            }

            if (encontrado) {
                frame.dispose();
                new Escritorio();
            } else {
                JOptionPane.showMessageDialog(frame, "Usuario o contraseña incorrectos", "", JOptionPane.ERROR_MESSAGE);
            }
        });

        fondoLabel.add(panelFormulario);
        fondoLabel.revalidate();
        fondoLabel.repaint();
    }

    private void mostrarFormularioCrear(JLabel fondoLabel) {
        limpiarFormulario(fondoLabel);
        actualizarMenuSegunFormulario("CrearUsuario");

        panelFormulario = new JPanel();
        panelFormulario.setLayout(null);
        panelFormulario.setBounds(645, 400, 370, 170);
        panelFormulario.setOpaque(false);

        JLabel lblUsuario = new JLabel("Usuario nuevo:");
        lblUsuario.setBounds(0, 0, 150, 25);
        lblUsuario.setForeground(Color.WHITE);
        panelFormulario.add(lblUsuario);

        JTextField txtUsuario = new JTextField();
        txtUsuario.setBounds(0, 25, 280, 30);
        panelFormulario.add(txtUsuario);

        JLabel lblContrasena = new JLabel("Contraseña nueva:");
        lblContrasena.setBounds(0, 60, 150, 25);
        lblContrasena.setForeground(Color.WHITE);
        panelFormulario.add(lblContrasena);

        JPasswordField txtContrasena = new JPasswordField();
        txtContrasena.setBounds(0, 85, 280, 30);
        panelFormulario.add(txtContrasena);

        ImageIcon ojoIcon3 = new ImageIcon("src/IMGS/OJO.png");
        Image imgOjo3 = ojoIcon3.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ojoIcon3 = new ImageIcon(imgOjo3);

        JButton btnOjo3 = new JButton(ojoIcon3);
        btnOjo3.setBounds(290, 85, 30, 30);
        btnOjo3.setFocusPainted(false);
        btnOjo3.setContentAreaFilled(false);
        btnOjo3.setBorderPainted(false);
        btnOjo3.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                txtContrasena.setEchoChar((char) 0);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                txtContrasena.setEchoChar('*');
            }
        });
        panelFormulario.add(btnOjo3);

        JButton btnCrear = new JButton("Crear Cuenta");
        btnCrear.setBounds(30, 130, 200, 30);
        panelFormulario.add(btnCrear);

        btnCrear.addActionListener(e -> {
            String nombre = txtUsuario.getText().trim();
            String contrasena = new String(txtContrasena.getPassword()).trim();
            if (!ValidarName(nombre)) {
                JOptionPane.showMessageDialog(frame, "Nombre inválido o ya existe");
                return;
            }
            if (!validarPassword(contrasena)) {
                JOptionPane.showMessageDialog(frame, "Contraseña inválida. Debe tener 5 caracteres, un número, una mayúscula y un carácter especial.");
                return;
            }

            Usuario nuevoUsuario = new Usuario(nombre, contrasena);
            Usuario.getUsuarios().add(nuevoUsuario);

            frame.dispose();
            new Escritorio();
        });

        fondoLabel.add(panelFormulario);
        fondoLabel.revalidate();
        fondoLabel.repaint();
    }

    private void crearMenu(JLabel fondoLabel) {
        ImageIcon iconoMenu = new ImageIcon("src/IMGS/IconomenuLogin.png");
        Image imgMenu = iconoMenu.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        iconoMenu = new ImageIcon(imgMenu);

        btnMenu = new JButton(iconoMenu);
        btnMenu.setBounds(1450, 705, 50, 50);
        btnMenu.setFocusPainted(false);
        btnMenu.setContentAreaFilled(false);
        btnMenu.setBorderPainted(false);
        fondoLabel.add(btnMenu);

        menu = new JPopupMenu();

        btnMenu.addActionListener(e -> menu.show(btnMenu, 0, -menu.getPreferredSize().height));
    }

    public static void main(String[] args) {
        new Sistem();
    }
}