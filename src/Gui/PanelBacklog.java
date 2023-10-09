package Gui;

import Controlador.Backlog;
import Controlador.Tarea;
import Service.BacklogService;
import Service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelBacklog extends JPanel {
    BacklogService backlogService;
    PanelManager panel;
    JPanel panelBacklog;
    JLabel jLabelId = new JLabel("ID: ");
    JTextField jTextFieldId = new JTextField();
    JLabel jLabelNombre = new JLabel("Nombre: ");
    JTextField jTextFieldNombre = new JTextField();
    JLabel jLabelDescripcion = new JLabel("Descripción: ");
    JTextField jTextFieldDescripcion = new JTextField();
    JLabel jLabelBacklog;
    JComboBox<Backlog> jComboBoxBacklog;
    JLabel jLabelTareas;
    JComboBox<Tarea> jComboBoxTareas;
    JButton jButtonGuardar;
    JButton jButtonEliminar = new JButton("Eliminar");
    JButton jButtonModificar = new JButton("Modificar");
    JButton jButtonMostrar = new JButton("Mostrar datos");
    JButton jButtonSalir = new JButton("Salir");
    JButton jButtonAgregar;
    JButton jButtonBacklogs = new JButton("Mis backlogs");

    JPanel jPanelBotones;

    public PanelBacklog(PanelManager panel){
        this.panel = panel;
    }

    public void menu() {
        panelBacklog = new JPanel();
        panelBacklog.setLayout(new GridLayout(6,2));

        jButtonGuardar = new JButton("Guardar nuevo backlog");
        jButtonSalir = new JButton("Salir");
        jPanelBotones = new JPanel();
        jPanelBotones.setLayout(new BoxLayout(jPanelBotones, BoxLayout.Y_AXIS));

        setLayout(new BorderLayout());
        add(panelBacklog, BorderLayout.CENTER);

        jPanelBotones.add(jButtonGuardar);
        jPanelBotones.add(jButtonBacklogs);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelBacklog panelBacklog1 = new PanelBacklog(panel);
                panelBacklog1.formularioGuardar();
                panel.mostrar(panelBacklog1);
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuInicio menuInicio = new MenuInicio(panel);
                menuInicio.armarMenuInicio();
                panel.mostrar(menuInicio);
            }
        });

        jButtonBacklogs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelBacklog panelBacklog1 = new PanelBacklog(panel);
                panelBacklog1.menuBacklog();
                panel.mostrar(panelBacklog1);
            }
        });
    }

    public void formularioGuardar(){
        backlogService = new BacklogService();
        panelBacklog = new JPanel();
        panelBacklog.setLayout(new GridLayout(6,2));

        jLabelNombre = new JLabel("Nombre: ");
        jLabelDescripcion = new JLabel("Descripcion: ");
        jButtonGuardar = new JButton("Guardar");
        jButtonSalir = new JButton("Atrás");

        jPanelBotones = new JPanel();

        panelBacklog.add(jLabelNombre);
        panelBacklog.add(jTextFieldNombre);
        panelBacklog.add(jLabelDescripcion);
        panelBacklog.add(jTextFieldDescripcion);

        setLayout(new BorderLayout());
        add(panelBacklog, BorderLayout.CENTER);

        jPanelBotones.add(jButtonGuardar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarBacklog();
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelBacklog panelBacklog1 = new PanelBacklog(panel);
                panelBacklog1.menu();
                panel.mostrar(panelBacklog1);
            }
        });
    }

    public void guardarBacklog(){
        try{
            String nombre = jTextFieldNombre.getText();
            String descripcion = jTextFieldDescripcion.getText();
            if (nombre.isEmpty() || descripcion.isEmpty()){
                JOptionPane.showMessageDialog(null, "Se deben completar todos los campos obligatoriamente.");
                return;
            }
            else{
                Backlog backlog = new Backlog();
                backlog.setNombre(nombre);
                backlog.setDescripcion(descripcion);
                backlogService.guardarBacklog(backlog);
                JOptionPane.showMessageDialog(null, "Backlog guardado exitosamente");
                jTextFieldNombre.setText("");
                jTextFieldDescripcion.setText("");
            }
        } catch (ServiceException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se pudo guardar el backlog");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, "Error. Se deben llenar todos los campos");
        }
    }

    public void menuBacklog(){
        backlogService = new BacklogService();
        panelBacklog = new JPanel();
        panelBacklog.setLayout(new GridLayout(6,2));

        jPanelBotones = new JPanel();

        jButtonSalir = new JButton("Salir");
        jComboBoxBacklog = new JComboBox<>();
        try{
            ArrayList<Backlog> backlogs = backlogService.obtenerBacklogs();
            for (Backlog backlog : backlogs){
                jComboBoxBacklog.addItem(backlog);
            }
        } catch (ServiceException e){
            JOptionPane.showMessageDialog(null, "Error al obtener backlogs");
        }
        panelBacklog.add(jComboBoxBacklog);
        setLayout(new BorderLayout());
        add(panelBacklog, BorderLayout.CENTER);

        jPanelBotones.add(jButtonEliminar);
        jPanelBotones.add(jButtonModificar);
        jPanelBotones.add(jButtonMostrar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Backlog backlogSeleccionado = (Backlog) jComboBoxBacklog.getSelectedItem();
                if (backlogSeleccionado != null) {
                    try{
                        int confirmacion = JOptionPane.showConfirmDialog(null,"¿Seguro que quieres eliminar este backlog?");
                        if (confirmacion == JOptionPane.YES_OPTION){
                            backlogService.eliminarBacklog(backlogSeleccionado.getId());
                            JOptionPane.showMessageDialog(null, "Backlog eliminado exitosamente");
                            jComboBoxBacklog.setSelectedIndex(-1);
                        }
                    } catch (ServiceException ex){
                        JOptionPane.showMessageDialog(null, "Backlog eliminado exitosamente");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un backlog");
                }
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelBacklog panelBacklog1 = new PanelBacklog(panel);
                panelBacklog1.menu();
                panel.mostrar(panelBacklog1);
            }
        });

        jButtonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Backlog backlogSeleccionado = (Backlog) jComboBoxBacklog.getSelectedItem();
                if (backlogSeleccionado != null){
                    PanelBacklog panelBacklog1 = new PanelBacklog(panel);
                    panelBacklog1.modificarBacklog(backlogSeleccionado);
                    panel.mostrar(panelBacklog1);
                }else {
                    JOptionPane.showMessageDialog(null, "Seleccione un backlog");
                }
            }
        });

        jButtonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Backlog backlogSeleccionado = (Backlog) jComboBoxBacklog.getSelectedItem();
                if (backlogSeleccionado != null){
                    try {
                        backlogService.buscarBacklog(backlogSeleccionado.getId());
                        StringBuilder infoBacklog = new StringBuilder("BACKLOG\n");
                        infoBacklog.append("ID: ").append(backlogSeleccionado.getId()).append("\n");
                        infoBacklog.append("Nombre: ").append(backlogSeleccionado.getNombre()).append("\n");
                        infoBacklog.append("Descripcion: ").append(backlogSeleccionado.getDescripcion()).append("\n");

                        ArrayList<Tarea> tareas = backlogService.obtenerTareasPorBacklog(backlogSeleccionado.getId());
                        if (!tareas.isEmpty()){
                            infoBacklog.append("Tareas asignadas:\n");
                            for (Tarea tarea : tareas){
                                infoBacklog.append("- ").append(tarea.getTitulo()).append("\n");
                            }
                        } else {
                            infoBacklog.append("-No hay tareas asignadas a este backlog");
                        }
                        JOptionPane.showMessageDialog(null, infoBacklog.toString(), "Información del backlog", JOptionPane.INFORMATION_MESSAGE);
                    } catch (ServiceException ex){
                        JOptionPane.showMessageDialog(null, "Error al mostrar los datos");
                    }
                }else {
                    JOptionPane.showMessageDialog(null, "Seleccione un backlog");
                }
            }
        });
    }

    public void modificarBacklog(Backlog backlog){
        backlogService = new BacklogService();
        panelBacklog = new JPanel();
        panelBacklog.setLayout(new GridLayout(3,2));

        jTextFieldId = new JTextField(String.valueOf(backlog.getId()));
        jTextFieldId.setEditable(false);
        jTextFieldNombre = new JTextField(backlog.getNombre());
        jTextFieldDescripcion = new JTextField(backlog.getDescripcion());

        jButtonModificar = new JButton("Modificar");
        jButtonSalir = new JButton("Atrás");
        jPanelBotones = new JPanel();

        panelBacklog.add(jLabelId);
        panelBacklog.add(jTextFieldId);
        panelBacklog.add(jLabelNombre);
        panelBacklog.add(jTextFieldNombre);
        panelBacklog.add(jLabelDescripcion);
        panelBacklog.add(jTextFieldDescripcion);
        setLayout(new BorderLayout());
        add(panelBacklog, BorderLayout.CENTER);

        jPanelBotones.add(jButtonModificar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = jTextFieldNombre.getText();
                String descripcion = jTextFieldDescripcion.getText();
                if (nombre.isEmpty() || descripcion.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Error. Todos los campos deben estar llenos");
                } else {
                    try {
                        backlog.setNombre(nombre);
                        backlog.setDescripcion(descripcion);
                        backlogService.modificar(backlog);
                        JOptionPane.showMessageDialog(null, "Backlog modificado exitosamente");
                    } catch (ServiceException ex){
                        JOptionPane.showMessageDialog(null, "Error al modificar el backlog");
                    }
                }
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelBacklog panelBacklog1 = new PanelBacklog(panel);
                panelBacklog1.menuBacklog();
                panel.mostrar(panelBacklog1);
            }
        });
    }
}