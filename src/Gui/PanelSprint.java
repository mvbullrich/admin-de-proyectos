package Gui;

import Controlador.Proyecto;
import Controlador.Sprint;
import Controlador.Tarea;
import Service.ServiceException;
import Service.SprintService;
import Service.TareaService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class PanelSprint extends JPanel {
    SprintService sprintService;
    PanelManager panel;
    JPanel panelSprint;

    JLabel jLabel = new JLabel("Seleccione un sprint: ");
    JLabel jLabelId = new JLabel("ID: ");
    JTextField jTextFieldId = new JTextField();
    JLabel jLabelFechaInicio = new JLabel("Fecha Inicio (YYYY-MM-DD): ");
    JTextField jTextFieldFechaInicio = new JTextField();
    JLabel jLabelFechaFin = new JLabel("Fecha Fin (YYYY-MM-DD): ");
    JTextField jTextFieldFehaFin = new JTextField();
    JComboBox<Sprint> jComboBoxSprint;

    JButton jButtonGuardar = new JButton("Guardar");
    JButton jButtonEliminar = new JButton("Eliminar");
    JButton jButtonModificar = new JButton("Modificar");
    JButton jButtonSalir;
    JButton jButtonMostrar = new JButton("Ver tareas asignadas");
    JButton jButtonSprints = new JButton("Mis sprints");

    JPanel jPanelBotones;

    public PanelSprint(PanelManager panel) {
        this.panel = panel;
    }

    public void menu() {
        panelSprint = new JPanel();
        panelSprint.setLayout(new GridLayout(6, 2));

        jButtonGuardar = new JButton("Guardar un nuevo sprint");
        jButtonSalir = new JButton("Salir");
        jPanelBotones = new JPanel();
        jPanelBotones.setLayout(new BoxLayout(jPanelBotones, BoxLayout.Y_AXIS));

        setLayout(new BorderLayout());
        add(panelSprint, BorderLayout.CENTER);

        jPanelBotones.add(jButtonSprints);
        jPanelBotones.add(jButtonGuardar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.CENTER);

        jButtonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelSprint panelSprint1 = new PanelSprint(panel);
                panelSprint1.formularioGuardar();
                panel.mostrar(panelSprint1);
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

        jButtonSprints.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelSprint panelSprint1 = new PanelSprint(panel);
                panelSprint1.menuSprints();
                panel.mostrar(panelSprint1);
            }
        });
    }

    public void formularioGuardar() {
        sprintService = new SprintService();
        panelSprint = new JPanel();
        panelSprint.setLayout(new GridLayout(6, 2));

        jLabelFechaInicio = new JLabel("Fecha inicio (YYYY-MM-DD): ");
        jLabelFechaFin = new JLabel("Fecha fin (YYYY-MM-DD): ");
        jButtonGuardar = new JButton("Guardar");
        jButtonSalir = new JButton("Atrás");

        jPanelBotones = new JPanel();

        panelSprint.add(jLabelFechaInicio);
        panelSprint.add(jTextFieldFechaInicio);
        panelSprint.add(jLabelFechaFin);
        panelSprint.add(jTextFieldFehaFin);

        setLayout(new BorderLayout());
        add(panelSprint, BorderLayout.CENTER);

        jPanelBotones.add(jButtonGuardar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarSprint();
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelSprint panelSprint1 = new PanelSprint(panel);
                panelSprint1.menu();
                panel.mostrar(panelSprint1);
            }
        });
    }

    public void guardarSprint() {
        try {
            String fechaInicioStr = jTextFieldFechaInicio.getText();
            String fechaFinStr = jTextFieldFehaFin.getText();
            if (fechaInicioStr.isEmpty() || fechaFinStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Se deben completar todos los campos");
                return;
            }
            LocalDate fechaInicio = null;
            LocalDate fechaFin = null;
            try {
                fechaInicio = LocalDate.parse(fechaInicioStr);
                fechaFin = LocalDate.parse(fechaFinStr);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(null, "El formato de fecha ingresado no es válido, debe ser YYYY-MM-DD");
                return;
            }
            if (fechaInicio.isAfter(fechaFin)) {
                JOptionPane.showMessageDialog(null, "La fecha de inicio no puede ser posterior a la fecha de fin");
                return;
            } else {
                Sprint sprint = new Sprint();
                sprint.setFechaInicio(fechaInicio);
                sprint.setFechaFin(fechaFin);
                sprintService.guardarSprint(sprint);
                JOptionPane.showMessageDialog(null, "Sprint guardado exitosamente");
            }
        } catch (ServiceException serEx) {
            serEx.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se pudo guardar");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, "Error. Se deben llenar todos los campos");
        }
    }

    public void actualizarTextField(){
        Sprint sprint = (Sprint) jComboBoxSprint.getSelectedItem();
        if (sprint != null){
            jTextFieldId.setText(String.valueOf(sprint.getId()));
            jTextFieldId.setEditable(false);
            jTextFieldFechaInicio.setText(sprint.getFechaInicio().toString());
            jTextFieldFehaFin.setText(sprint.getFechaFin().toString());
        } else {
            jTextFieldId.setText("");
            jTextFieldFechaInicio.setText("");
            jTextFieldFehaFin.setText("");
        }
    }

    public void menuSprints(){
        sprintService = new SprintService();
        panelSprint = new JPanel();
        panelSprint.setLayout(new GridLayout(4, 4));

        jPanelBotones = new JPanel();

        jButtonSalir = new JButton("Salir");
        jComboBoxSprint = new JComboBox<>();
        try{
            ArrayList<Sprint> sprints = sprintService.obtenerSprints();
            for (Sprint sprint : sprints){
                jComboBoxSprint.addItem(sprint);
            }
        } catch (ServiceException e){
            JOptionPane.showMessageDialog(null, "Error al obtener los sprints");
        }
        actualizarTextField();
        panelSprint.add(jLabel);
        panelSprint.add(jComboBoxSprint);
        panelSprint.add(jLabelId);
        panelSprint.add(jTextFieldId);
        panelSprint.add(jLabelFechaInicio);
        panelSprint.add(jTextFieldFechaInicio);
        panelSprint.add(jLabelFechaFin);
        panelSprint.add(jTextFieldFehaFin);
        setLayout(new BorderLayout());
        add(panelSprint, BorderLayout.CENTER);

        jPanelBotones.add(jButtonMostrar);
        jPanelBotones.add(jButtonModificar);
        jPanelBotones.add(jButtonEliminar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jComboBoxSprint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTextField();
            }
        });

        jButtonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sprint sprintSeleccionado = (Sprint) jComboBoxSprint.getSelectedItem();
                if (sprintSeleccionado != null){
                    try{
                        int confirmar = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar este sprint?");
                        if (confirmar == JOptionPane.YES_OPTION){
                            sprintService.eliminarSprint(sprintSeleccionado.getId());
                            JOptionPane.showMessageDialog(null, "Sprint eliminado exitosamente");
                            jComboBoxSprint.setSelectedIndex(-1);
                        }
                    } catch (ServiceException ex){
                        JOptionPane.showMessageDialog(null, "Error al eliminar");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un sprint");
                }
            }
        });

        jButtonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sprint sprintSeleccionado = (Sprint) jComboBoxSprint.getSelectedItem();
                if (sprintSeleccionado != null) {
                    try {
                        String fechaInicioStr = jTextFieldFechaInicio.getText();
                        String fechaFinStr = jTextFieldFehaFin.getText();
                        if (fechaInicioStr.isEmpty() || fechaFinStr.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Se deben completar todos los campos");
                            return;
                        }
                        LocalDate fechaInicio = null;
                        LocalDate fechaFin = null;
                        try {
                            fechaInicio = LocalDate.parse(fechaInicioStr);
                            fechaFin = LocalDate.parse(fechaFinStr);
                        } catch (DateTimeParseException ex) {
                            JOptionPane.showMessageDialog(null, "El formato de fecha ingresado no es válido, debe ser YYYY-MM-DD");
                            return;
                        }
                        if (fechaInicio.isAfter(fechaFin)) {
                            JOptionPane.showMessageDialog(null, "La fecha de inicio no puede ser posterior a la fecha de fin");
                            return;
                        }
                        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres modificar este sprint?");
                        if (confirmacion == JOptionPane.YES_OPTION) {
                            sprintSeleccionado.setFechaInicio(fechaInicio);
                            sprintSeleccionado.setFechaFin(fechaFin);
                            sprintService.modificarSprint(sprintSeleccionado);
                            JOptionPane.showMessageDialog(null, "Sprint modificado exitosamente");
                        }
                    } catch (ServiceException serEx) {
                        JOptionPane.showMessageDialog(null, "Error al modificar el sprint");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un sprint");
                }
            }
        });

        jButtonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sprint sprintSeleccionado = (Sprint) jComboBoxSprint.getSelectedItem();
                if (sprintSeleccionado != null){
                    try{
                        sprintService.buscarSpint(sprintSeleccionado.getId());
                        StringBuilder infoSprint = new StringBuilder("SPRINT ").append(sprintSeleccionado.getId()).append("\n");

                        ArrayList<Tarea> tareas = sprintService.obtenerTareasPorSprint(sprintSeleccionado.getId());
                        if (!tareas.isEmpty()){
                            infoSprint.append("Tareas asignadas:\n");
                            for (Tarea tarea : tareas){
                                infoSprint.append("- ").append(tarea.getTitulo()).append("\n");
                            }
                        } else {
                            infoSprint.append("-No hay tareas asignadas a este sprint");
                        }
                        JOptionPane.showMessageDialog(null, infoSprint.toString(), "Información del sprint", JOptionPane.INFORMATION_MESSAGE);
                    } catch (ServiceException ex){
                        JOptionPane.showMessageDialog(null, "Error al mostrar los datos del sprint");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un sprint");
                }
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelSprint panelSprint1 = new PanelSprint(panel);
                panelSprint1.menu();
                panel.mostrar(panelSprint1);
            }
        });
    }
}