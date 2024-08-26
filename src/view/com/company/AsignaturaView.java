package view.com.company;

import Connecion.ConectionBD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AsignaturaView extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldID;
    private JTextField textFieldNombre;
    private JButton OKButton;
    private JTextField textFieldCreditos;
    private JTextField textFieldTipo;
    private JTextField textFieldCurso;
    private JTextField textFieldCuatrimestre;
    private JTextField textFieldIDProfesor;
    private JTextField textFieldIDGrado;

    private Modo modo;

    public enum Modo {
        AÑADIR,
        ELIMINAR,
        MODIFICAR
    }

    public AsignaturaView(Modo modo) {
        this.modo = modo;
        setTitle("Gestión de Asignaturas");
        setSize(500, 400);
        setModal(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        contentPane = new JPanel(new BorderLayout());

        JPanel fieldsPanel = new JPanel(new GridLayout(8, 2));

        fieldsPanel.add(new JLabel("ID:"));
        fieldsPanel.add(textFieldID);
        fieldsPanel.add(new JLabel("Nombre:"));
        fieldsPanel.add(textFieldNombre);
        fieldsPanel.add(new JLabel("Créditos:"));
        fieldsPanel.add(textFieldCreditos);
        fieldsPanel.add(new JLabel("Tipo:"));
        fieldsPanel.add(textFieldTipo);
        fieldsPanel.add(new JLabel("Curso:"));
        fieldsPanel.add(textFieldCurso);
        fieldsPanel.add(new JLabel("Cuatrimestre:"));
        fieldsPanel.add(textFieldCuatrimestre);
        fieldsPanel.add(new JLabel("ID Profesor:"));
        fieldsPanel.add(textFieldIDProfesor);
        fieldsPanel.add(new JLabel("ID Grado:"));
        fieldsPanel.add(textFieldIDGrado);

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

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
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
            textFieldCreditos.setEnabled(false);
            textFieldTipo.setEnabled(false);
            textFieldCurso.setEnabled(false);
            textFieldCuatrimestre.setEnabled(false);
            textFieldIDProfesor.setEnabled(false);
            textFieldIDGrado.setEnabled(false);
        }
    }

    private void onOK() {
        String id = textFieldID.getText();
        String nombre = textFieldNombre.getText();
        String creditos = textFieldCreditos.getText();
        String tipo = textFieldTipo.getText();
        String curso = textFieldCurso.getText();
        String cuatrimestre = textFieldCuatrimestre.getText();
        String idProfesor = textFieldIDProfesor.getText();
        String idGrado = textFieldIDGrado.getText();

        switch (modo) {
            case AÑADIR:
                if (addAsignatura(id, nombre, creditos, tipo, curso, cuatrimestre, idProfesor, idGrado)) {
                    JOptionPane.showMessageDialog(this, "Asignatura añadida exitosamente.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al añadir la asignatura.");
                }
                break;
            case ELIMINAR:
                if (deleteAsignatura(id)) {
                    JOptionPane.showMessageDialog(this, "Asignatura eliminada exitosamente.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar la asignatura.");
                }
                break;
            case MODIFICAR:
                if (modifyAsignatura(id, nombre, creditos, tipo, curso, cuatrimestre, idProfesor, idGrado)) {
                    JOptionPane.showMessageDialog(this, "Asignatura modificada exitosamente.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al modificar la asignatura.");
                }
                break;
        }
    }

    private boolean addAsignatura(String id, String nombre, String creditos, String tipo, String curso,
                                  String cuatrimestre, String idProfesor, String idGrado) {
        String query = "INSERT INTO asignatura (id, nombre, creditos, tipo, curso, cuatrimestre, id_profesor, id_grado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConectionBD.getConn();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, nombre);
            preparedStatement.setString(3, creditos);
            preparedStatement.setString(4, tipo);
            preparedStatement.setString(5, curso);
            preparedStatement.setString(6, cuatrimestre);
            preparedStatement.setString(7, idProfesor);
            preparedStatement.setString(8, idGrado);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean deleteAsignatura(String id) {
        String query = "DELETE FROM asignatura WHERE id = ?";
        try (Connection connection = ConectionBD.getConn();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean modifyAsignatura(String id, String nombre, String creditos, String tipo, String curso,
                                     String cuatrimestre, String idProfesor, String idGrado) {
        String query = "UPDATE asignatura SET nombre = ?, creditos = ?, tipo = ?, curso = ?, cuatrimestre = ?, " +
                "id_profesor = ?, id_grado = ? WHERE id = ?";
        try (Connection connection = ConectionBD.getConn();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, creditos);
            preparedStatement.setString(3, tipo);
            preparedStatement.setString(4, curso);
            preparedStatement.setString(5, cuatrimestre);
            preparedStatement.setString(6, idProfesor);
            preparedStatement.setString(7, idGrado);
            preparedStatement.setString(8, id);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        ConectionBD.openConn();
        AsignaturaView dialog = new AsignaturaView(Modo.AÑADIR);
        dialog.setVisible(true);
        ConectionBD.closeConn();
    }
}