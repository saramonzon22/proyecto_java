package Controler.com.company;
import view.com.company.AsignaturaView;
import view.com.company.PersonaControl.Modo;

import Connecion.ConectionBD;
import model.com.company.ModelAsignaturas;
import model.com.company.ModelPersonas;
import view.com.company.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.event.*;

public class ControllerEntrada implements ActionListener, MouseListener, WindowListener, KeyListener {

    private final ViewPanelEntrada frEntrada = new ViewPanelEntrada();
    private final DefaultTableModel m = null;
    private TableRowSorter<DefaultTableModel> rowSorter;


    public ControllerEntrada() {
        iniciarVentana();
        iniciarEventos();
        prepararBaseDatos();
        rowSorter = new TableRowSorter<>(m);
    }

    private void filterTable() {
        String text = frEntrada.getTextField1().getText();
        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }

    public void iniciarVentana() {
        frEntrada.setVisible(true);
    }

    public void iniciarEventos() {

        frEntrada.getAsignaturasButton().addActionListener(this::actionPerformed);
        frEntrada.getTextField1().addKeyListener(this);
        frEntrada.getPersonasButton().addActionListener(this::actionPerformed);
        frEntrada.getPanelEntrada().addMouseListener(this);
        frEntrada.getTable1().addMouseListener(this);
        frEntrada.addWindowListener(this);
        frEntrada.getTable1().setRowSorter(rowSorter);
        frEntrada.getTextField1().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                filterTable();

            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                filterTable();

            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                filterTable();

            }
        });
    }

    public void prepararBaseDatos() {
        ModelPersonas entrada = new ModelPersonas();
        frEntrada.getTable1().setModel(entrada.CargaDatos(m));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String entrada = e.getActionCommand();

        switch (entrada) {

            case "Asignaturas":
                ModelAsignaturas modelAsignaturas = new ModelAsignaturas();
                DefaultTableModel modeloTablaAsignaturas = new DefaultTableModel();
                modeloTablaAsignaturas = modelAsignaturas.CargaDatos(modeloTablaAsignaturas);
                frEntrada.getTable1().setModel(modeloTablaAsignaturas);

                Object[] opciones = {"Añadir Asignatura", "Modificar Asignatura", "Eliminar Asignatura"};
                int opcionSeleccionada = JOptionPane.showOptionDialog(null,
                        "Selecciona una opción",
                        "Gestión de Asignaturas",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opciones,
                        opciones[0]);

                AsignaturaView.Modo modo = null;
                switch (opcionSeleccionada) {
                    case 0:
                        modo = AsignaturaView.Modo.AÑADIR;
                        break;
                    case 1:
                        modo = AsignaturaView.Modo.MODIFICAR;
                        break;
                    case 2:
                        modo = AsignaturaView.Modo.ELIMINAR;
                        break;
                }

                if (modo != null) {
                    AsignaturaView asignaturaDialog = new AsignaturaView(modo);
                    asignaturaDialog.setSize(1000, 600);
                    asignaturaDialog.setLocation(400, 300);
                    asignaturaDialog.setVisible(true);
                    prepararBaseDatos();
                }
                break;

            case "Personas":
                ModelPersonas modelPersonas = new ModelPersonas();
                DefaultTableModel modeloTablaPersonas = new DefaultTableModel();
                modeloTablaPersonas = modelPersonas.CargaDatos(modeloTablaPersonas);
                frEntrada.getTable1().setModel(modeloTablaPersonas);

                Object[] opcionesPersona = {"Añadir Persona", "Modificar Persona", "Eliminar Persona"};
                int opcionSeleccionadaPersona = JOptionPane.showOptionDialog(null,
                        "Selecciona una opción",
                        "Gestión de Personas",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opcionesPersona,
                        opcionesPersona[0]);

                PersonaControl.Modo modoPersona = null;
                switch (opcionSeleccionadaPersona) {
                    case 0:
                        modoPersona = PersonaControl.Modo.AÑADIR;
                        break;
                    case 1:
                        modoPersona = PersonaControl.Modo.MODIFICAR;
                        break;
                    case 2:
                        modoPersona = PersonaControl.Modo.ELIMINAR;
                        break;
                }

                if (modoPersona != null) {
                    PersonaControl personaDialog = new PersonaControl(modoPersona);
                    personaDialog.setSize(1000, 600);
                    personaDialog.setLocation(400, 300);
                    personaDialog.setVisible(true);
                    prepararBaseDatos();
                }
                break;

            case "Añadir Asignaturas":
                modelAsignaturas = new ModelAsignaturas();
                AsignaturaView asignaturaViewAdd = new AsignaturaView(AsignaturaView.Modo.AÑADIR);
                asignaturaViewAdd.setVisible(true);
                prepararBaseDatos();
                frEntrada.getTable1().setModel(modelAsignaturas.CargaDatos(new DefaultTableModel()));

                break;

            case "Eliminar Asignatura":
                modelAsignaturas = new ModelAsignaturas();
                AsignaturaView asignaturaViewDelete = new AsignaturaView(AsignaturaView.Modo.ELIMINAR);
                asignaturaViewDelete.setVisible(true);
                prepararBaseDatos();
                frEntrada.getTable1().setModel(modelAsignaturas.CargaDatos(new DefaultTableModel()));
                break;

            case "Modificar Asignatura":
                modelAsignaturas = new ModelAsignaturas();
                AsignaturaView asignaturaViewModify = new AsignaturaView(AsignaturaView.Modo.MODIFICAR);
                asignaturaViewModify.setVisible(true);
                prepararBaseDatos();
                frEntrada.getTable1().setModel(modelAsignaturas.CargaDatos(new DefaultTableModel()));
                break;

            case "Insertar Persona":
                modelPersonas = new ModelPersonas();
                PersonaControl personaControlAdd = new PersonaControl(Modo.AÑADIR);
                personaControlAdd.setVisible(true);
                prepararBaseDatos();
                frEntrada.getTable1().setModel(modelPersonas.CargaDatos(new DefaultTableModel()));
                break;

            case "Eliminar Persona":
                modelPersonas = new ModelPersonas();
                PersonaControl personaControlDelete= new PersonaControl(Modo.ELIMINAR);
                personaControlDelete.setVisible(true);
                prepararBaseDatos();
                frEntrada.getTable1().setModel(modelPersonas.CargaDatos(new DefaultTableModel()));
                break;

            case "Modificar Persona":
                modelPersonas = new ModelPersonas();
                PersonaControl personaControlModify = new PersonaControl(Modo.MODIFICAR);
                personaControlModify.setVisible(true);
                prepararBaseDatos();
                frEntrada.getTable1().setModel(modelPersonas.CargaDatos(new DefaultTableModel()));
                break;

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 3) {
            JOptionPane.showMessageDialog(null, "ha pisado el botón derecho");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("ha salido del programa");
        frEntrada.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ConectionBD.closeConn();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
