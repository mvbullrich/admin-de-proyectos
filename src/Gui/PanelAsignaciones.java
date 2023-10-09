package Gui;

import Controlador.*;
import Gui.PanelManager;
import Service.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelAsignaciones extends JPanel {
    TareaService tareaService;
    EmpleadoService empleadoService;
    ProyectoService proyectoService;
    SprintService sprintService;
    BacklogService backlogService;
    PanelManager panel;
    JPanel panelAsignacion;

    JComboBox<String> jComboBoxOpciones;
    JComboBox jComboBox1;
    JComboBox jComboBox2;
    JComboBox jComboBox3;
    JTextField jTextFieldInfo;

    JLabel jLabelOpcion = new JLabel("Seleccione una opción: ");
    JLabel jLabel0 = new JLabel("Quiero asignar a un: ");
    JLabel jLabel1 = new JLabel("Agregar a: ");
    JLabel jLabel2 = new JLabel("A: ");
    JButton jButtonAsignar = new JButton("Asignar");
    JButton jButtonSalir = new JButton("Salir");
    JPanel jPanelBotones;

    public PanelAsignaciones(PanelManager panel){
        this.panel = panel;
    }

    public void asignacion(){
        proyectoService = new ProyectoService();
        tareaService = new TareaService();
        empleadoService = new EmpleadoService();
        sprintService = new SprintService();
        backlogService = new BacklogService();
        panelAsignacion = new JPanel();
        panelAsignacion.setLayout(new GridLayout(4,2));

        jPanelBotones = new JPanel();

        jComboBox1 = new JComboBox<>();
        jComboBox2 = new JComboBox<>();
        jComboBox3 = new JComboBox<>();
        jComboBoxOpciones = new JComboBox();
        jComboBoxOpciones.addItem("Tarea");
        jComboBoxOpciones.addItem("Empleado");


        panelAsignacion.add(jLabelOpcion);
        panelAsignacion.add(jComboBoxOpciones);
        panelAsignacion.add(jLabel0);
        panelAsignacion.add(jComboBox1);
        panelAsignacion.add(jLabel1);
        panelAsignacion.add(jComboBox2);
        panelAsignacion.add(jLabel2);
        panelAsignacion.add(jComboBox3);
        setLayout(new BorderLayout());
        add(panelAsignacion, BorderLayout.CENTER);

        jPanelBotones.add(jButtonAsignar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jComboBoxOpciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jComboBox1.removeAllItems();
                    jComboBox2.removeAllItems();
                    jComboBox3.removeAllItems();
                    String seleccion = (String) jComboBoxOpciones.getSelectedItem();
                    if ("Tarea".equals(seleccion)){
                        jComboBox1.addItem("Proyecto");
                        jComboBox1.addItem("Sprint");
                        jComboBox1.addItem("Backlog");
                    } else if ("Empleado".equals(seleccion)) {
                        jComboBox1.removeAllItems();
                        ArrayList<Empleado> empleados = empleadoService.obtenerEmpleadosDisponibles();
                        for (Empleado empleado : empleados){
                            jComboBox2.addItem(empleado);
                        }
                        ArrayList<Tarea> tareas = tareaService.obtenerTareasSinEmpleadosAsignados();
                        for (Tarea tarea : tareas){
                            jComboBox3.addItem(tarea);
                        }
                    }
                } catch (ServiceException ex){
                    JOptionPane.showMessageDialog(null, "Error al obtener los datos");
                }
            }
        });

        jComboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String eleccion = (String) jComboBox1.getSelectedItem();
                    if (eleccion != null) { // Verificar si eleccion no es null
                        jComboBox3.removeAllItems();
                        jComboBox2.removeAllItems();
                        if (eleccion.equals("Proyecto")) {
                            ArrayList<Proyecto> proyectos = proyectoService.obtenerProyectos();
                            for (Proyecto proyecto : proyectos) {
                                jComboBox3.addItem(proyecto);
                            }
                            ArrayList<Tarea> tareas = tareaService.obtenerTareas();
                            for (Tarea tarea : tareas) {
                                jComboBox2.addItem(tarea);
                            }
                        } else if (eleccion.equals("Sprint")) {
                            ArrayList<Sprint> sprints = sprintService.obtenerSprints();
                            for (Sprint sprint : sprints) {
                                jComboBox3.addItem(sprint);
                            }
                            ArrayList<Tarea> tareas = sprintService.obtenerTareaSinSprint();
                            for (Tarea tarea : tareas) {
                                jComboBox2.addItem(tarea);
                            }
                        } else if (eleccion.equals("Backlog")){
                            ArrayList<Backlog> backlogs = backlogService.obtenerBacklogs();
                            for (Backlog backlog : backlogs){
                                jComboBox3.addItem(backlog);
                            }
                            ArrayList<Tarea> tareas = backlogService.obtenerTareasSinBacklog();
                            for (Tarea tarea : tareas){
                                jComboBox2.addItem(tarea);
                            }
                        }
                    }
                } catch (ServiceException ex) {
                    JOptionPane.showMessageDialog(null, "Error al obtener los datos");
                }
            }
        });

        jButtonAsignar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = (String) jComboBoxOpciones.getSelectedItem();
                Object seleccionCombo2 = jComboBox2.getSelectedItem();
                Object seleccionCombo3 = jComboBox3.getSelectedItem();

                if ("Tarea".equals(seleccion)) {
                    if (seleccionCombo2 instanceof Tarea && seleccionCombo3 instanceof Proyecto) {
                        Tarea tarea = (Tarea) seleccionCombo2;
                        Proyecto proyecto = (Proyecto) seleccionCombo3;
                        try {
                            proyectoService.asignarTareaProyecto(tarea, proyecto);
                            JOptionPane.showMessageDialog(null, "La tarea '" + tarea.getTitulo() + "' fue asignada al proyecto " + proyecto.getTitulo());
                            jComboBoxOpciones.setSelectedIndex(-1);
                            jComboBox2.setSelectedIndex(-1);
                            jComboBox3.setSelectedIndex(-1);
                        } catch (ServiceException ex) {
                            JOptionPane.showMessageDialog(null, "Error al asignar la tarea al proyecto");
                        }
                    } else if (seleccionCombo2 instanceof Tarea && seleccionCombo3 instanceof Sprint) {
                        Tarea tarea = (Tarea) seleccionCombo2;
                        Sprint sprint = (Sprint) seleccionCombo3;
                        try {
                            sprintService.agregarTarea(sprint.getId(), tarea.getId());
                            JOptionPane.showMessageDialog(null, "La tarea '" + tarea.getTitulo() + "' fue asignada al Sprint ID " + sprint.getId());
                            jComboBoxOpciones.setSelectedIndex(-1);
                            jComboBox2.setSelectedIndex(-1);
                            jComboBox3.setSelectedIndex(-1);
                        } catch (ServiceException ex) {
                            JOptionPane.showMessageDialog(null, "Error al asignar la tarea al Sprint");
                        }
                    } else if (seleccionCombo2 instanceof Tarea && seleccionCombo3 instanceof  Backlog){
                        Tarea tarea = (Tarea) seleccionCombo2;
                        Backlog backlog = (Backlog) seleccionCombo3;
                        try{
                            backlogService.agregarTarea(backlog.getId(), tarea.getId());
                            JOptionPane.showMessageDialog(null, "La tarea '" + tarea.getTitulo() + "' fue asignada al backlog ID: " + backlog.getId());
                            jComboBoxOpciones.setSelectedIndex(-1);
                            jComboBox2.setSelectedIndex(-1);
                            jComboBox3.setSelectedIndex(-1);
                        } catch (ServiceException ex){
                            JOptionPane.showMessageDialog(null, "Error al asignar la tarea al backlog");
                        }
                    }
                } else if ("Empleado".equals(seleccion)) {
                    if (seleccionCombo2 instanceof Empleado && seleccionCombo3 instanceof Tarea) {
                        Empleado empleado = (Empleado) seleccionCombo2;
                        Tarea tarea = (Tarea) seleccionCombo3;
                        try {
                            tareaService.asignarEmpleado(tarea.getId(), empleado.getId());
                            JOptionPane.showMessageDialog(null, "Se asignó a " + empleado.getNombre() + " a la tarea " + tarea.getTitulo());
                            jComboBoxOpciones.setSelectedIndex(-1);
                            jComboBox2.setSelectedIndex(-1);
                            jComboBox3.setSelectedIndex(-1);
                        } catch (ServiceException serEx) {
                            JOptionPane.showMessageDialog(null, "Error al asignar el empleado a la tarea");
                        }
                    }
                }
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
    }

    public void desasignar() {
        proyectoService = new ProyectoService();
        tareaService = new TareaService();
        empleadoService = new EmpleadoService();
        sprintService = new SprintService();
        backlogService = new BacklogService();
        panelAsignacion = new JPanel();
        panelAsignacion.setLayout(new GridLayout(4, 2));

        jComboBox1 = new JComboBox();
        jComboBox2 = new JComboBox<>();
        jComboBoxOpciones = new JComboBox();
        jComboBoxOpciones.addItem("Tarea");
        jComboBoxOpciones.addItem("Empleado");

        jLabel0 = new JLabel("Quiero desasignar de un: ");
        jLabel1 = new JLabel("Desasignar a: ");
        jLabel2 = new JLabel("De: ");
        jTextFieldInfo = new JTextField();
        jTextFieldInfo.setEditable(false);
        panelAsignacion.add(jLabelOpcion);
        panelAsignacion.add(jComboBoxOpciones);
        panelAsignacion.add(jLabel0);
        panelAsignacion.add(jComboBox1);
        panelAsignacion.add(jLabel1);
        panelAsignacion.add(jComboBox2);
        panelAsignacion.add(jLabel2);
        panelAsignacion.add(jTextFieldInfo);
        setLayout(new BorderLayout());
        add(panelAsignacion, BorderLayout.CENTER);

        jButtonAsignar = new JButton("Desasignar");
        jPanelBotones = new JPanel();
        jPanelBotones.add(jButtonAsignar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jComboBoxOpciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jComboBox1.removeAllItems();
                    jComboBox2.removeAllItems();
                    String seleccion = (String) jComboBoxOpciones.getSelectedItem();
                    if ("Tarea".equals(seleccion)) {
                        jComboBox1.addItem("Proyecto");
                        jComboBox1.addItem("Sprint");
                        jComboBox1.addItem("Backlog");
                    } else if ("Empleado".equals(seleccion)) {
                        ArrayList<Empleado> empleados = empleadoService.obtenerEmpleadosAsignados();
                        for (Empleado empleado : empleados) {
                            jComboBox2.addItem(empleado);
                        }
                    }
                } catch (ServiceException ex) {
                    JOptionPane.showMessageDialog(null, "Error al obtener los datos");
                }
            }
        });

        jComboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String eleccion = (String) jComboBox1.getSelectedItem();
                    if (eleccion != null) { // Verificar si eleccion no es null
                        jComboBox2.removeAllItems();
                        //jComboBox1.removeAllItems();

                        if (eleccion.equals("Proyecto")) {
                            ArrayList<Tarea> tareas = tareaService.obtenerTareasConProyectos();
                            for (Tarea tarea : tareas) {
                                jComboBox2.addItem(tarea);
                            }
                        } else if (eleccion.equals("Sprint")) {
                            ArrayList<Tarea> tareas = sprintService.obtenerTareasConSprint();
                            for (Tarea tarea : tareas) {
                                jComboBox2.addItem(tarea);
                            }
                        } else if (eleccion.equals("Backlog")){
                            ArrayList<Tarea> tareas = backlogService.obtenerTareasConBacklog();
                            for (Tarea tarea : tareas){
                                jComboBox2.addItem(tarea);
                            }
                        }
                    }
                } catch (ServiceException ex) {
                    JOptionPane.showMessageDialog(null, "Error al obtener los datos");
                }
            }
        });


        jComboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = (String) jComboBoxOpciones.getSelectedItem();
                Object seleccionCombo2 = jComboBox2.getSelectedItem();
                String eleccion = (String) jComboBox1.getSelectedItem();

                try{
                    if ("Tarea".equals(seleccion) && seleccionCombo2 instanceof Tarea) {
                        Tarea tareaSeleccionada = (Tarea) seleccionCombo2;
                        if ("Proyecto".equals(eleccion)){
                            Proyecto proyecto = proyectoService.buscarProyecto(tareaSeleccionada.getIdProyecto());
                            jTextFieldInfo.setText(proyecto.getTitulo());
                        } else if ("Sprint".equals(eleccion)){
                            Sprint sprint = sprintService.buscarSpint(tareaSeleccionada.getId_sprint());
                            jTextFieldInfo.setText(String.valueOf(sprint.getId()));
                        } else if ("Backlog".equals(eleccion)){
                            Backlog backlog = backlogService.buscarBacklog(tareaSeleccionada.getId_backlog());
                            jTextFieldInfo.setText(String.valueOf(backlog.getId()));
                        }
                    } else if ("Empleado".equals(seleccion) && seleccionCombo2 instanceof Empleado) {
                        Empleado empleadoSeleccionado = (Empleado) seleccionCombo2;
                        Tarea tarea = tareaService.buscarTareaPorEmpleado(empleadoSeleccionado.getId());
                        jTextFieldInfo.setText(tarea.getTitulo());
                    } else {
                        jTextFieldInfo.setText("");
                    }
                } catch (ServiceException ex){
                    JOptionPane.showMessageDialog(null, "Error al obtener los datos");
                }
            }
        });

        jButtonAsignar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = (String) jComboBoxOpciones.getSelectedItem();
                Object seleccionCombo2 = jComboBox2.getSelectedItem();

                if ("Tarea".equals(seleccion)) {
                    if (seleccionCombo2 instanceof Tarea) {
                        Tarea tarea = (Tarea) seleccionCombo2;
                        try {
                            if ("Proyecto".equals(jComboBox1.getSelectedItem())) {
                                proyectoService.desasignarTarea(tarea);
                                JOptionPane.showMessageDialog(null, "La tarea " + tarea.getTitulo() + " fue desasignada del proyecto");
                            } else if ("Sprint".equals(jComboBox1.getSelectedItem())) {
                                sprintService.quitarTarea(tarea);
                                JOptionPane.showMessageDialog(null, "La tarea " + tarea.getTitulo() + " fue desasignada del sprint");
                            } else if ("Backlog".equals(jComboBox1.getSelectedItem())){
                                backlogService.eliminarTarea(tarea);
                                JOptionPane.showMessageDialog(null, "La tarea '"+tarea.getTitulo()+"' fue desasignada del backlog");
                            }
                            jComboBoxOpciones.setSelectedIndex(-1);
                            jComboBox2.setSelectedIndex(-1);
                            jTextFieldInfo.setText("");
                        } catch (ServiceException ex) {
                            JOptionPane.showMessageDialog(null, "Error al desasignar la tarea");
                        }
                    }
                } else if ("Empleado".equals(seleccion) && seleccionCombo2 instanceof Empleado) {
                    Empleado empleado = (Empleado) seleccionCombo2;
                    try {
                        tareaService.desasignarEmpleado(empleado);
                        JOptionPane.showMessageDialog(null, empleado.getNombre() + " fue desasignado de su tarea");
                        jComboBoxOpciones.setSelectedIndex(-1);
                        jComboBox2.setSelectedIndex(-1);
                        jTextFieldInfo.setText("");
                    } catch (ServiceException ex) {
                        JOptionPane.showMessageDialog(null, "Error al desasignar este empleado de su tarea");
                    }
                }
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
    }
}
