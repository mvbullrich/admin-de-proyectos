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
    TareaService tareaService;
    PanelManager panel;
    JPanel panelSprint;

    JLabel jLabelId;
    JTextField jTextFieldId = new JTextField();
    JLabel jLabelFechaInicio;
    JTextField jTextFieldFechaInicio = new JTextField();
    JLabel jLabelFechaFin;
    JTextField jTextFieldFehaFin = new JTextField();
    JLabel jLabelTarea;
    JComboBox<Tarea> jComboBoxTarea;
    JLabel jLabelSprint;
    JComboBox<Sprint> jComboBoxSprint;

    JButton jButtonGuardar;
    JButton jButtonEliminar;
    JButton jButtonModificar;
    JButton jButtonBuscar;
    JButton jButtonSalir;
    JButton jButtonAgregarTarea;
    JButton jButtonQuitarTarea;

    JPanel jPanelBotones;

    public PanelSprint(PanelManager panel) {
        this.panel = panel;
        menu();
    }

    public void menu() {
        panelSprint = new JPanel();
        panelSprint.setLayout(new GridLayout(6, 2));

        jButtonGuardar = new JButton("Guardar un nuevo sprint");
        jButtonEliminar = new JButton("Eliminar un sprint");
        jButtonModificar = new JButton("Modificar un sprint");
        jButtonBuscar = new JButton("Buscar un sprint");
        jButtonAgregarTarea = new JButton("Agregar tarea a un sprint");
        jButtonQuitarTarea = new JButton("Eliminar tarea de un sprint");
        jPanelBotones = new JPanel();
        jPanelBotones.setLayout(new BoxLayout(jPanelBotones, BoxLayout.Y_AXIS));

        setLayout(new BorderLayout());
        add(panelSprint, BorderLayout.CENTER);

        jPanelBotones.add(jButtonGuardar);
        jPanelBotones.add(jButtonEliminar);
        jPanelBotones.add(jButtonModificar);
        jPanelBotones.add(jButtonBuscar);
        jPanelBotones.add(jButtonAgregarTarea);
        jPanelBotones.add(jButtonQuitarTarea);
        add(jPanelBotones, BorderLayout.CENTER);

        jButtonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelSprint panelSprint1 = new PanelSprint(panel);
                panelSprint1.formularioGuardar();
                panel.mostrar(panelSprint1);
            }
        });

        jButtonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelSprint panelSprint1 = new PanelSprint(panel);
                panelSprint1.formularioEliminar();
                panel.mostrar(panelSprint1);
            }
        });

        jButtonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelSprint panelSprint1 = new PanelSprint(panel);
                panelSprint1.formularioModificar();
                panel.mostrar(panelSprint1);
            }
        });

        jButtonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelSprint panelSprint1 = new PanelSprint(panel);
                panelSprint1.formularioBuscar();
                panel.mostrar(panelSprint1);
            }
        });

        jButtonAgregarTarea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelSprint panelSprint1 = new PanelSprint(panel);
                panelSprint1.formularioAgregarTarea();
                panel.mostrar(panelSprint1);
            }
        });

        jButtonQuitarTarea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelSprint panelSprint1 = new PanelSprint(panel);
                panelSprint1.formularioQuitarTarea();
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
            LocalDate fechaInicio = LocalDate.parse(fechaInicioStr);
            LocalDate fechaFin = LocalDate.parse(fechaFinStr);
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
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "El formato de fecha ingresado no es válido, debe ser YYYY-MM-DD");
        }
    }

    public void formularioEliminar() {
        sprintService = new SprintService();
        panelSprint = new JPanel();
        panelSprint.setLayout(new GridLayout(6, 2));

        jPanelBotones = new JPanel();

        jLabelId = new JLabel("ID del Sprint: ");
        jButtonEliminar = new JButton("Eliminar");
        jButtonSalir = new JButton("Atrás");

        panelSprint.add(jLabelId);
        panelSprint.add(jTextFieldId);

        setLayout(new BorderLayout());
        add(panelSprint, BorderLayout.CENTER);

        jPanelBotones.add(jButtonEliminar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarSprint();
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

    public void eliminarSprint(){
        try{
            String idSprintStr = jTextFieldId.getText();
            if (idSprintStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese un ID de sprint válido.");
                return;
            }

            int idSprint = Integer.parseInt(idSprintStr);
            Sprint sprint = sprintService.buscarSpint(idSprint);
            if (sprint != null){
                JOptionPane.showMessageDialog(null, "Sprint encontrado:\n" +
                        "ID: " + sprint.getId() + "\n" +
                        "Fecha Inicio: " + sprint.getFechaInicio() + "\n" +
                        "Fecha Fin: " + sprint.getFechaFin());
                int confirmacion = JOptionPane.showConfirmDialog(null, "¿Seguro que quiere eliminar este proyecto?");
                if (confirmacion == JOptionPane.YES_OPTION) {
                    try {
                        sprintService.eliminarSprint(idSprint);
                        JOptionPane.showMessageDialog(null, "El sprint se eliminó exitosamente");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Ingrese un ID de sprint válido");
                    } catch (ServiceException serEx) {
                        JOptionPane.showMessageDialog(null, "No se pudo eliminar el sprint");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún sprint con ese ID.");
            }
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Ingrese un ID de sprint válido.");
        } catch (ServiceException ex){
            JOptionPane.showMessageDialog(null, "Error al buscar el sprint.");
        }
    }

    public void formularioModificar(){
        sprintService = new SprintService();
        panelSprint = new JPanel();
        panelSprint.setLayout(new GridLayout(6,2));

        jPanelBotones = new JPanel();

        jLabelId = new JLabel("ID del sprint: ");
        jButtonModificar = new JButton("Modificar");
        jButtonSalir = new JButton("Salir");

        panelSprint.add(jLabelId);
        panelSprint.add(jTextFieldId);

        setLayout(new BorderLayout());
        add(panelSprint, BorderLayout.CENTER);

        jPanelBotones.add(jButtonModificar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelSprint panelSprint1 = new PanelSprint(panel);
                panelSprint1.menu();
                panel.mostrar(panelSprint1);
            }
        });

        jButtonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int idSprint = Integer.parseInt(jTextFieldId.getText());
                    Sprint sprint = sprintService.buscarSpint(idSprint);
                    if (sprint != null){
                        modificarSprint(sprint);
                    } else{
                        JOptionPane.showMessageDialog(null, "No se encontró ningun sprint con ese ID.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Ingrese un ID de sprint válido");
                } catch (ServiceException ex) {
                    JOptionPane.showMessageDialog(null, "Error al buscar el sprint");
                }
            }
        });
    }

    public void modificarSprint(Sprint sprint){
        sprintService = new SprintService();
        panelSprint = new JPanel();
        panelSprint.setLayout(new GridLayout(6,2));

        jLabelFechaInicio = new JLabel("Fecha Inicio (YYYY-MM-DD): ");
        jLabelFechaFin = new JLabel("Fecha Fin (YYYY-MM-DD): ");

        jTextFieldFechaInicio = new JTextField(sprint.getFechaInicio().toString());
        jTextFieldFehaFin = new JTextField(sprint.getFechaFin().toString());

        jButtonModificar = new JButton("Modificar");
        jPanelBotones = new JPanel();

        panelSprint.add(jLabelFechaInicio);
        panelSprint.add(jTextFieldFechaInicio);
        panelSprint.add(jLabelFechaFin);
        panelSprint.add(jTextFieldFehaFin);

        setLayout(new BorderLayout());
        add(panelSprint, BorderLayout.CENTER);

        jPanelBotones.add(jButtonModificar);
        add(jPanelBotones, BorderLayout.SOUTH);

        int opcion = JOptionPane.showOptionDialog(null, panelSprint, "Modificar Sprint", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
        if(opcion == JOptionPane.OK_OPTION){
            sprint.setFechaInicio(LocalDate.parse(jTextFieldFechaInicio.getText()));
            sprint.setFechaFin(LocalDate.parse(jTextFieldFehaFin.getText()));
            try {
                sprintService.modificarSprint(sprint);
                JOptionPane.showMessageDialog(null, "El sprint se modificó exitosamente");
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(null, "Error al modificar el sprint");
            }
        }
    }

    public void formularioBuscar(){
        sprintService = new SprintService();
        panelSprint = new JPanel();
        panelSprint.setLayout(new GridLayout(6, 2));

        jPanelBotones = new JPanel();

        jLabelId = new JLabel("ID del sprint: ");
        jButtonBuscar = new JButton("Buscar");
        jButtonSalir = new JButton("Atrás");

        panelSprint.add(jLabelId);
        panelSprint.add(jTextFieldId);

        setLayout(new BorderLayout());
        add(panelSprint, BorderLayout.CENTER);

        jPanelBotones.add(jButtonBuscar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarSprint();
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

    public void buscarSprint(){
        try{
            int idSprint = Integer.parseInt(jTextFieldId.getText());
            Sprint sprint = sprintService.buscarSpint(idSprint);
            if(sprint != null){
                JOptionPane.showMessageDialog(null, "Sprint encontrado:\n" +
                        "ID: " + sprint.getId() + "\n" +
                        "Fecha Inicio: " + sprint.getFechaInicio() + "\n" +
                        "Fecha Fin: " + sprint.getFechaFin());
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún sprint con ese ID.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID de sprint válido");
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar el sprint");
        }
    }

    public void formularioAgregarTarea(){
        sprintService = new SprintService();
        panelSprint = new JPanel();
        panelSprint.setLayout(new GridLayout(2, 2));

        jLabelSprint = new JLabel("Sprint: ");
        jComboBoxSprint = new JComboBox<>();
        jLabelTarea = new JLabel("Tarea: ");
        jComboBoxTarea = new JComboBox<>();

        jButtonAgregarTarea = new JButton("Agregar tarea");
        jButtonSalir = new JButton("Atrás");
        jPanelBotones = new JPanel();

        panelSprint.add(jLabelSprint);
        panelSprint.add(jComboBoxSprint);
        panelSprint.add(jLabelTarea);
        panelSprint.add(jComboBoxTarea);

        setLayout(new BorderLayout());
        add(panelSprint, BorderLayout.CENTER);

        jPanelBotones.add(jButtonAgregarTarea);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        try {
            ArrayList<Tarea> tareas = sprintService.obtenerTareaSinSprint();
            for (Tarea tarea : tareas) {
                jComboBoxTarea.addItem(tarea);
            }
        } catch (ServiceException e){
            JOptionPane.showMessageDialog(null, "Error al obtener las tareas: " + e.getMessage());
        }
        try {
            ArrayList<Sprint> sprints = sprintService.obtenerSprints();
            for (Sprint sprint : sprints){
                jComboBoxSprint.addItem(sprint);
            }
        } catch (ServiceException e){
            JOptionPane.showMessageDialog(null, "Error al obtener sprints");
        }

        jButtonAgregarTarea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarTarea();
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

    public void agregarTarea(){
        Tarea tareaSeleccionada = (Tarea) jComboBoxTarea.getSelectedItem();
        Sprint sprintSeleccionado = (Sprint) jComboBoxSprint.getSelectedItem();

        if (tareaSeleccionada != null || sprintSeleccionado != null){
            int idTarea = tareaSeleccionada.getId();
            int idSprint = sprintSeleccionado.getId();
            try{
                sprintService.agregarTarea(idSprint, idTarea);
                JOptionPane.showMessageDialog(null, "Tarea agregada al sprint exitosamente");
                jComboBoxTarea.setSelectedIndex(-1);
                jComboBoxSprint.setSelectedIndex(-1);
            } catch (ServiceException e){
                JOptionPane.showMessageDialog(null, "Error al agregar la tarea.");
                jComboBoxTarea.setSelectedIndex(-1);
                jComboBoxSprint.setSelectedIndex(-1);
            }
        }
    }

    public void formularioQuitarTarea(){
        sprintService = new SprintService();

        panelSprint = new JPanel();
        panelSprint.setLayout(new GridLayout(2, 2));

        jPanelBotones = new JPanel();
        jLabelTarea = new JLabel("Seleccione un tarea: ");
        jComboBoxTarea = new JComboBox<>();
        jButtonQuitarTarea = new JButton("Eliminar tarea");
        jButtonSalir = new JButton("Atrás");
        ArrayList<Tarea> tareas = new ArrayList<>();
        try {
            tareas = sprintService.obtenerTareasConSprint();
            for (Tarea tarea : tareas){
                jComboBoxTarea.addItem(tarea);
            }
        } catch (ServiceException e){
            JOptionPane.showMessageDialog(null, "Error al obtener las tareas.");
        }
        panelSprint.add(jLabelTarea);
        panelSprint.add(jComboBoxTarea);
        setLayout(new BorderLayout());
        add(panelSprint, BorderLayout.CENTER);

        jPanelBotones.add(jButtonQuitarTarea);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonQuitarTarea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitarTarea();
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

    public void quitarTarea(){
        Tarea tareaSeleccionada = (Tarea) jComboBoxTarea.getSelectedItem();
        int idSprint = tareaSeleccionada.getId_sprint();
        try{
            Sprint sprint = sprintService.buscarSpint(idSprint);if (tareaSeleccionada != null) {
                int confirmacion = JOptionPane.showConfirmDialog(
                        null,
                        "¿Seguro que quieres borrar la tarea ID: " + tareaSeleccionada.getId() +
                                ", titulo: " + tareaSeleccionada.getTitulo() + " del sprint ID: " + sprint.getId(),
                        "Confirmacion",
                        JOptionPane.YES_NO_OPTION
            );
                if (confirmacion == JOptionPane.YES_OPTION){
                    try{
                        sprintService.quitarTarea(tareaSeleccionada);
                        JOptionPane.showMessageDialog(this, "Tarea eliminada correctamente");
                    } catch (ServiceException ex){
                        JOptionPane.showMessageDialog(null, "Error al eliminar la tarea: " + ex.getMessage());
                    }
                }
            }
        } catch (ServiceException e){
            JOptionPane.showMessageDialog(null, "Error al buscar el sprint");
        }
    }
}