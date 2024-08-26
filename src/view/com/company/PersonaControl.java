package view.com.company;

import Connecion.ConectionBD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PersonaControl extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldNIF;
    private JTextField textFieldNombre;
    private JTextField textFieldApellido1;
    private JTextField textFieldApellido2;
    private JTextField textFieldCiudad;
    private JTextField textFieldDireccion;
    private JTextField textFieldTelefono;
    private JTextField textFieldFechaNacimiento;
    private JComboBox<String> comboBoxSexo;
    private JComboBox<String> comboBoxTipo;
    private JLabel introduceElTipoLabel;
    private JLabel introduceElNIFLabel;
    private JLabel introduceElNombreLabel;
    private JLabel introduceElPrimerApellidoLabel;
    private JLabel introduceElSegundoApellidoLabel;
    private JLabel introduceLaCiudadLabel;
    private JLabel introduceLaDirecciónLabel;
    private JLabel introduceElTeléfonoLabel;
    private JLabel introduceLaFechaDeLabel;
    private JLabel introduceElSexoLabel;
    private JButton OKButton;

    private Modo modo;

    public enum Modo {
        AÑADIR,
        ELIMINAR,
        MODIFICAR
    }

    public PersonaControl(Modo modo) {
        this.modo = modo;
        setTitle("Gestión de Personas");
        setSize(400, 400);
        setModal(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        contentPane = new JPanel(new BorderLayout());
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        fieldsPanel.add(new JLabel("NIF:"), gbc);
        gbc.gridx = 1;
        textFieldNIF = new JTextField(20);
        fieldsPanel.add(textFieldNIF, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        fieldsPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        textFieldNombre = new JTextField(20);
        fieldsPanel.add(textFieldNombre, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        fieldsPanel.add(new JLabel("Apellido 1:"), gbc);
        gbc.gridx = 1;
        textFieldApellido1 = new JTextField(20);
        fieldsPanel.add(textFieldApellido1, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        fieldsPanel.add(new JLabel("Apellido 2:"), gbc);
        gbc.gridx = 1;
        textFieldApellido2 = new JTextField(20);
        fieldsPanel.add(textFieldApellido2, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        fieldsPanel.add(new JLabel("Ciudad:"), gbc);
        gbc.gridx = 1;
        textFieldCiudad = new JTextField(20);
        fieldsPanel.add(textFieldCiudad, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        fieldsPanel.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1;
        textFieldDireccion = new JTextField(20);
        fieldsPanel.add(textFieldDireccion, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        fieldsPanel.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        textFieldTelefono = new JTextField(20);
        fieldsPanel.add(textFieldTelefono, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        fieldsPanel.add(new JLabel("Fecha Nacimiento:"), gbc);
        gbc.gridx = 1;
        textFieldFechaNacimiento = new JTextField(20);
        fieldsPanel.add(textFieldFechaNacimiento, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        fieldsPanel.add(new JLabel("Sexo:"), gbc);
        gbc.gridx = 1;
        comboBoxSexo = new JComboBox<>(new String[]{"H", "M"});
        fieldsPanel.add(comboBoxSexo, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        fieldsPanel.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        comboBoxTipo = new JComboBox<>(new String[]{"Alumno", "Profesor"});
        fieldsPanel.add(comboBoxTipo, gbc);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonOK = new JButton("OK");
        buttonCancel = new JButton("Cancelar");
        buttonsPanel.add(buttonOK);
        buttonsPanel.add(buttonCancel);

        contentPane.add(fieldsPanel, BorderLayout.CENTER);
        contentPane.add(buttonsPanel, BorderLayout.SOUTH);
        setContentPane(contentPane);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();
        setLocationRelativeTo(null);

        if (modo == Modo.ELIMINAR) {
            textFieldNombre.setEnabled(false);
            textFieldApellido1.setEnabled(false);
            textFieldApellido2.setEnabled(false);
            textFieldCiudad.setEnabled(false);
            textFieldDireccion.setEnabled(false);
            textFieldTelefono.setEnabled(false);
            textFieldFechaNacimiento.setEnabled(false);
            comboBoxSexo.setEnabled(false);
            comboBoxTipo.setEnabled(false);
        }
    }

    private void onOK() {
        String nif = textFieldNIF.getText();
        String nombre = textFieldNombre.getText();
        String apellido1 = textFieldApellido1.getText();
        String apellido2 = textFieldApellido2.getText();
        String ciudad = textFieldCiudad.getText();
        String direccion = textFieldDireccion.getText();
        String telefono = textFieldTelefono.getText();
        String fechaNacimiento = textFieldFechaNacimiento.getText();
        String sexo = (String) comboBoxSexo.getSelectedItem();
        String tipo = (String) comboBoxTipo.getSelectedItem();

        switch (modo) {
            case AÑADIR:
                if (addPersona(nif, nombre, apellido1, apellido2, ciudad, direccion, telefono, fechaNacimiento, sexo, tipo)) {
                    JOptionPane.showMessageDialog(this, "Persona añadida exitosamente.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al añadir la persona.");
                }
                break;
            case ELIMINAR:
                if (deletePersona(nif)) {
                    JOptionPane.showMessageDialog(this, "Persona eliminada exitosamente.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar la persona.");
                }
                break;
            case MODIFICAR:
                if (modifyPersona(nif, nombre, apellido1, apellido2, ciudad, direccion, telefono, fechaNacimiento, sexo)) {
                    JOptionPane.showMessageDialog(this, "Persona modificada exitosamente.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al modificar la persona.");
                }
                break;
        }

    }

    private void onCancel() {
        dispose();
    }

    private boolean addPersona(String nif, String nombre, String apellido1, String apellido2, String ciudad,
                               String direccion, String telefono, String fechaNacimiento, String sexo, String tipo) {
        String query = "INSERT INTO persona (nif, nombre, apellido1, apellido2, ciudad, direccion, telefono, fecha_nacimiento, sexo, tipo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConectionBD.getConn();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nif);
            preparedStatement.setString(2, nombre);
            preparedStatement.setString(3, apellido1);
            preparedStatement.setString(4, apellido2);
            preparedStatement.setString(5, ciudad);
            preparedStatement.setString(6, direccion);
            preparedStatement.setString(7, telefono);
            preparedStatement.setString(8, fechaNacimiento);
            preparedStatement.setString(9, sexo);
            preparedStatement.setString(10, tipo);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean deletePersona(String nif) {
        String query = "DELETE FROM persona WHERE nif = ?";
        try (Connection connection = ConectionBD.getConn();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nif);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean modifyPersona(String nif, String nombre, String apellido1, String apellido2, String ciudad,
                                  String direccion, String telefono, String fechaNacimiento, String sexo) {
        String query = "UPDATE persona SET nombre=?, apellido1=?, apellido2=?, ciudad=?, direccion=?, telefono=?, fecha_nacimiento=?, sexo=? WHERE nif=?";
        try (Connection connection = ConectionBD.getConn();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellido1);
            preparedStatement.setString(3, apellido2);
            preparedStatement.setString(4, ciudad);
            preparedStatement.setString(5, direccion);
            preparedStatement.setString(6, telefono);
            preparedStatement.setString(7, fechaNacimiento);
            preparedStatement.setString(8, sexo);
            preparedStatement.setString(9, nif);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        ConectionBD.openConn();
        PersonaControl dialog = new PersonaControl(Modo.AÑADIR);
        dialog.setVisible(true);
        ConectionBD.closeConn();
    }
}
