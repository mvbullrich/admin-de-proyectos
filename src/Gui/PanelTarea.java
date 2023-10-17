package Gui;

import Controlador.Empleado;
import Controlador.HistorialEstado;
import Controlador.Proyecto;
import Controlador.Tarea;
import Service.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.sql.Date;
import java.util.ArrayList;

public class PanelTarea extends JPanel {
    TareaService tareaService;
    EmpleadoService empleadoService;
    HistorialEstadoService historialEstadoService;
    ProyectoService proyectoService;
    PanelManager panel;
    JPanel panelTarea;

    JLabel jLabel = new JLabel("Seleccione una tarea: ");
    JLabel jLabelId = new JLabel("ID: ");
    JLabel jlabelTitulo = new JLabel("Título:");
    JLabel jLabelDescripcion = new JLabel("Descripción:");
    JLabel jLabelEstimacion = new JLabel("Estimación:");
    JLabel jLabelHorasReales = new JLabel("Horas Reales:");
    JTextField jTextFieldId = new JTextField(10);
    JTextField jTextFieldTitulo = new JTextField(20);
    JTextField jTextFieldDescripcion = new JTextField(50);
    JTextField jTextFieldEstimacion = new JTextField(10);
    JTextField jTextFieldHorasReales = new JTextField(10);
    JComboBox<Tarea> jComboBoxTareas;
    JComboBox jComboBoxEstado;
    JComboBox<Empleado> jComboBoxEmpleados;
    JLabel jLabelEstado = new JLabel("Seleccione un estado: ");
    JLabel jLabelEmpleado = new JLabel("Empleado responsable");
    JLabel jLabelFecha = new JLabel("Fecha (YYYY-MM-DD): ");
    JTextField jTextFieldFecha = new JTextField();

    JButton jButtonGuardar = new JButton("Guardar una nueva tarea");
    JButton jButtonMisTareas = new JButton("Mis tareas");
    JButton jButtonSalir = new JButton("Salir");
    JButton jButtonEliminar = new JButton("Eliminar");
    JButton jButtonModificar = new JButton("Modificar");
    JButton jButtonMostrar = new JButton("Mostrar datos");
    JButton jButtonEstado = new JButton("Actualizar estado");
    JButton jButtonMostrarEstados = new JButton("Mostrar estados");
    JButton jButtonKanban = new JButton("Tablero Kanban");
    JButton jButtonEmpleados = new JButton("Ir a empleados");

    JPanel jPanelBotones;

    public PanelTarea(PanelManager panel){
        this.panel = panel;
        inicio();
    }

    public void inicio(){
        panelTarea = new JPanel();
        panelTarea.setLayout(new GridLayout(3,1));
        jPanelBotones = new JPanel();
        jPanelBotones.setLayout(new BoxLayout(jPanelBotones, BoxLayout.Y_AXIS));
        setLayout(new BorderLayout());
        add(panelTarea, BorderLayout.CENTER);

        jPanelBotones.add(jButtonMisTareas);
        jPanelBotones.add(jButtonGuardar);
        jPanelBotones.add(jButtonKanban);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTarea panelTarea1 = new PanelTarea(panel);
                panelTarea1.formularioGuardar();
                panel.mostrar(panelTarea1);
            }
        });

        jButtonMisTareas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTarea panelTarea1 = new PanelTarea(panel);
                panelTarea1.menuTareas();
                panel.mostrar(panelTarea1);
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

        jButtonKanban.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableroKanban();
            }
        });
    }

    public void formularioGuardar(){
        tareaService = new TareaService();
        panelTarea = new JPanel();
        panelTarea.setLayout(new GridLayout(4, 2));

        jButtonGuardar = new JButton("Guardar");
        jButtonSalir = new JButton("Atrás");
        jPanelBotones = new JPanel();

        panelTarea.add(jlabelTitulo);
        panelTarea.add(jTextFieldTitulo);
        panelTarea.add(jLabelDescripcion);
        panelTarea.add(jTextFieldDescripcion);
        panelTarea.add(jLabelEstimacion);
        panelTarea.add(jTextFieldEstimacion);
        panelTarea.add(jLabelHorasReales);
        panelTarea.add(jTextFieldHorasReales);

        setLayout(new BorderLayout());
        add(panelTarea, BorderLayout.CENTER);

        jPanelBotones.add(jButtonGuardar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tarea tarea = new Tarea();
                try {
                    tarea.setTitulo(jTextFieldTitulo.getText());
                    tarea.setDescripcion(jTextFieldDescripcion.getText());
                    String estimacionText = jTextFieldEstimacion.getText();
                    int estimacion = 0;
                    try {
                        if (!estimacionText.isEmpty()) {
                            estimacion = Integer.parseInt(estimacionText);
                        }
                    } catch (NumberFormatException ex) {
                        throw new IllegalArgumentException("La estimación debe ser un número válido.");
                    }
                    tarea.setEstimacion(estimacion);
                    String horasRealesText = jTextFieldHorasReales.getText();
                    int horasReales = 0;
                    try {
                        if (!horasRealesText.isEmpty()) {
                            horasReales = Integer.parseInt(horasRealesText);
                        }
                    } catch (NumberFormatException ex) {
                        throw new IllegalArgumentException("Las horas reales deben ser un número válido.");
                    }
                    tarea.setHorasReales(horasReales);
                    if (tarea.getTitulo().isEmpty()) {
                        throw new IllegalArgumentException("El título es un campo obligatorio.");
                    }
                    tareaService.guardarTarea(tarea);
                    JOptionPane.showMessageDialog(null, "Tarea guardada exitosamente");
                    jTextFieldTitulo.setText("");
                    jTextFieldDescripcion.setText("");
                    jTextFieldEstimacion.setText("");
                    jTextFieldHorasReales.setText("");
                } catch (ServiceException serEx) {
                    JOptionPane.showMessageDialog(null, "No se pudo guardar la tarea");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTarea panelTarea = new PanelTarea(panel);
                panelTarea.inicio();
                panel.mostrar(panelTarea);
            }
        });
    }

    public void actualizarTextField(){
        Tarea tareaSeleccionada = (Tarea) jComboBoxTareas.getSelectedItem();
        if (tareaSeleccionada != null){
            jTextFieldId.setText(String.valueOf(tareaSeleccionada.getId()));
            jTextFieldId.setEditable(false);
            jTextFieldTitulo.setText(tareaSeleccionada.getTitulo());
            jTextFieldDescripcion.setText(tareaSeleccionada.getDescripcion());
            jTextFieldEstimacion.setText(String.valueOf(tareaSeleccionada.getEstimacion()));
            jTextFieldHorasReales.setText(String.valueOf(tareaSeleccionada.getHorasReales()));
        } else {
            jTextFieldId.setText("");
            jTextFieldTitulo.setText("");
            jTextFieldDescripcion.setText("");
            jTextFieldEstimacion.setText("");
            jTextFieldHorasReales.setText("");
        }
    }

    public void menuTareas(){
        tareaService = new TareaService();
        empleadoService = new EmpleadoService();
        proyectoService = new ProyectoService();
        panelTarea = new JPanel();
        panelTarea.setLayout(new GridLayout(6,2));
        jButtonSalir = new JButton("Salir");
        jPanelBotones = new JPanel();
        jComboBoxTareas = new JComboBox<>();
        try{
            ArrayList<Tarea> tareas = tareaService.obtenerTodasLasTareas();
            for (Tarea tarea : tareas){
                jComboBoxTareas.addItem(tarea);
            }
        } catch (ServiceException e){
            JOptionPane.showMessageDialog(null, "Error al obtener las tareas");
        }
        actualizarTextField();
        panelTarea.add(jLabel);
        panelTarea.add(jComboBoxTareas);
        panelTarea.add(jLabelId);
        panelTarea.add(jTextFieldId);
        panelTarea.add(jlabelTitulo);
        panelTarea.add(jTextFieldTitulo);
        panelTarea.add(jLabelDescripcion);
        panelTarea.add(jTextFieldDescripcion);
        panelTarea.add(jLabelEstimacion);
        panelTarea.add(jTextFieldEstimacion);
        panelTarea.add(jLabelHorasReales);
        panelTarea.add(jTextFieldHorasReales);
        setLayout(new BorderLayout());
        add(panelTarea, BorderLayout.CENTER);

        jPanelBotones.add(jButtonMostrarEstados);
        jPanelBotones.add(jButtonEstado);
        jPanelBotones.add(jButtonMostrar);
        jPanelBotones.add(jButtonModificar);
        jPanelBotones.add(jButtonEliminar);
        jPanelBotones.add(jButtonEmpleados);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jComboBoxTareas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTextField();
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTarea panelTarea1 = new PanelTarea(panel);
                panelTarea1.inicio();
                panel.mostrar(panelTarea1);
            }
        });

        jButtonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tarea tareaSeleccionada = (Tarea) jComboBoxTareas.getSelectedItem();
                if(tareaSeleccionada != null){
                    try{
                        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar esta tarea?");
                        if (confirmacion == JOptionPane.YES_OPTION){
                            tareaService.eliminarTarea(tareaSeleccionada.getId());
                            JOptionPane.showMessageDialog(null, "La tarea se eliminó exitosamente");
                            jComboBoxTareas.removeItem(tareaSeleccionada);
                            jComboBoxTareas.setSelectedIndex(-1);
                        }
                    } catch (ServiceException ex){
                        JOptionPane.showMessageDialog(null, "Error al eliminar la tarea");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una tarea");
                }
            }
        });

        jButtonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tarea tarea = (Tarea) jComboBoxTareas.getSelectedItem();
                if (tarea != null) {
                    String titulo = jTextFieldTitulo.getText();
                    String descripcion = jTextFieldDescripcion.getText();
                    String estimacionText = jTextFieldEstimacion.getText();
                    String horasRealesText = jTextFieldHorasReales.getText();
                    int estimacion = 0;
                    int horasReales = 0;
                    if (titulo.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "El título no puede estar vacío");
                        return;
                    }
                    try {
                        if (!estimacionText.isEmpty()) {
                            estimacion = Integer.parseInt(estimacionText);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "La estimación debe ser un número válido.");
                        return;
                    }
                    try {
                        if (!horasRealesText.isEmpty()) {
                            horasReales = Integer.parseInt(horasRealesText);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Las horas reales deben ser un número válido.");
                        return;
                    }
                    if (descripcion.isEmpty()) {
                        descripcion = "";
                    }
                    int confirmacion = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres modificar esta tarea?");
                    if (confirmacion == JOptionPane.YES_OPTION) {
                        tarea.setTitulo(titulo);
                        tarea.setDescripcion(descripcion);
                        tarea.setEstimacion(estimacion);
                        tarea.setHorasReales(horasReales);
                        try {
                            tareaService.modificar(tarea);
                            JOptionPane.showMessageDialog(null, "La tarea se modificó exitosamente");
                        } catch (ServiceException ex) {
                            JOptionPane.showMessageDialog(null, "Error al modificar la tarea: " + ex.getMessage());
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una tarea");
                }
            }
        });

        jButtonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tarea tareaSeleccionada = (Tarea) jComboBoxTareas.getSelectedItem();
                if (tareaSeleccionada != null){
                    try {
                        tareaService.buscarTarea(tareaSeleccionada.getId());
                        StringBuilder tareasInfo = new StringBuilder("TAREA: ").append(tareaSeleccionada.getTitulo()).append("\n\n");

                        int idEmpleado = tareaSeleccionada.getEmpleado_id();
                        if (idEmpleado != 0) {
                            tareasInfo.append("ID de empleado asignado: ").append(tareaSeleccionada.getEmpleado_id()).append("\n");
                            Empleado empleado = empleadoService.buscarEmpleado(tareaSeleccionada.getEmpleado_id());
                            tareasInfo.append("Nombre del empleado: ").append(empleado.getNombre()).append("\n");
                        } else {
                            tareasInfo.append("-No hay ningun empleado asignado a esta tarea.\n");
                        }
                        tareasInfo.append("\n");
                        int proyectoId = tareaSeleccionada.getIdProyecto();
                        if (proyectoId != 0){
                            tareasInfo.append("ID del proyecto: ").append(tareaSeleccionada.getIdProyecto()).append("\n");
                            Proyecto proyecto = proyectoService.buscarProyecto(tareaSeleccionada.getIdProyecto());
                            tareasInfo.append("Titulo proyecto: ").append(proyecto.getTitulo()).append("\n");
                        } else {
                            tareasInfo.append("-Esta tarea no esta asignada a ningun proyecto.\n");
                        }
                        JOptionPane.showMessageDialog(null, tareasInfo.toString(), "Información de la tarea", JOptionPane.INFORMATION_MESSAGE);
                    } catch (ServiceException ex){
                        JOptionPane.showMessageDialog(null, "Error al obtener los datos de la tarea");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una tarea");
                }
            }
        });

        jButtonEstado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tarea tarea = (Tarea) jComboBoxTareas.getSelectedItem();
                if (tarea != null){
                    PanelTarea panelTarea1 = new PanelTarea(panel);
                    panelTarea1.estados(tarea);
                    panel.mostrar(panelTarea1);
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una tarea");
                }
            }
        });

        jButtonMostrarEstados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historialEstadoService = new HistorialEstadoService();
                Tarea tareaSeleccionada = (Tarea) jComboBoxTareas.getSelectedItem();
                if (tareaSeleccionada != null){
                    try {
                        ArrayList<HistorialEstado> historial = historialEstadoService.obtenerHistorialPorTarea(tareaSeleccionada.getId());
                        StringBuilder historialInfo = new StringBuilder("HISTORIAL DE ESTADOS:\n");
                        int contador = 1;
                        for (HistorialEstado historialEstado : historial){
                            historialInfo.append("Modificación: ").append(contador).append("\n");
                            historialInfo.append("ID tarea: ").append(historialEstado.getIdTarea()).append("\n");
                            historialInfo.append("Estado: ").append(historialEstado.getEstado()).append("\n");
                            historialInfo.append("Fecha: ").append(historialEstado.getFecha()).append("\n");
                            historialInfo.append("ID responsable: ").append(historialEstado.getIdResponsable()).append("\n");
                            Empleado empleado = empleadoService.buscarEmpleado(historialEstado.getIdResponsable());
                            historialInfo.append("Nombre del responsable: ").append(empleado.getNombre()).append("\n");
                            contador += 1;
                            historialInfo.append("\n");
                        }
                        JOptionPane.showMessageDialog(null, historialInfo.toString(), "Historial Estado", JOptionPane.INFORMATION_MESSAGE);
                    } catch (ServiceException ex){
                        JOptionPane.showMessageDialog(null, "Error al obtener el historial de estados");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una tarea");
                }
            }
        });

        jButtonEmpleados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelEmpleado panelEmpleado1 = new PanelEmpleado(panel);
                panelEmpleado1.menuEmpleados();
                panel.mostrar(panelEmpleado1);
            }
        });
    }

    public void estados(Tarea tarea){
        tareaService = new TareaService();
        empleadoService = new EmpleadoService();
        historialEstadoService = new HistorialEstadoService();

        panelTarea = new JPanel();
        panelTarea.setLayout(new GridLayout(5,2));
        jPanelBotones = new JPanel();

        jComboBoxEmpleados = new JComboBox<>();
        jComboBoxEstado = new JComboBox<>();
        jComboBoxEstado.addItem("Por hacer");
        jComboBoxEstado.addItem("En progreso");
        jComboBoxEstado.addItem("Completada");

        jButtonEstado = new JButton("Actualizar estado");
        jButtonSalir = new JButton("Atrás");

        try {
            ArrayList<Empleado> empleadoArrayList = empleadoService.obtenerTodosLosEmpleados();
            for (Empleado empleado : empleadoArrayList){
                jComboBoxEmpleados.addItem(empleado);
            }
        } catch (ServiceException e){
            JOptionPane.showMessageDialog(null, "Error al obtener los empleados");
        }

        panelTarea.add(jLabelEstado);
        panelTarea.add(jComboBoxEstado);
        panelTarea.add(jLabelFecha);
        panelTarea.add(jTextFieldFecha);
        panelTarea.add(jLabelEmpleado);
        panelTarea.add(jComboBoxEmpleados);

        setLayout(new BorderLayout());
        add(panelTarea, BorderLayout.CENTER);

        jPanelBotones.add(jButtonEstado);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonEstado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String estado = jComboBoxEstado.getSelectedItem().toString();
                String fechaTexto = jTextFieldFecha.getText();
                Empleado empleadoSeleccionado = (Empleado) jComboBoxEmpleados.getSelectedItem();
                try{
                    java.sql.Date fecha = Date.valueOf(fechaTexto);
                    HistorialEstado historialEstado = new HistorialEstado();
                    historialEstado.setIdTarea(tarea.getId());
                    historialEstado.setEstado(estado);
                    historialEstado.setFecha(fecha);
                    historialEstado.setIdResponsable(empleadoSeleccionado.getId());

                    try{
                        historialEstadoService.guardarHistorialEstado(historialEstado);
                        JOptionPane.showMessageDialog(null, "Estado de la tarea guardado exitosamente");
                        jComboBoxEstado.setSelectedIndex(-1);
                        jTextFieldFecha.setText("");
                        jComboBoxEmpleados.setSelectedIndex(-1);
                    } catch (ServiceException ex){
                        JOptionPane.showMessageDialog(null, "Error al guardar el estado");
                    }
                } catch (IllegalArgumentException ex){
                    JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto");
                }
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTarea panelTarea1 = new PanelTarea(panel);
                panelTarea1.menuTareas();
                panel.mostrar(panelTarea1);
            }
        });
    }

    public void tableroKanban() {
        tareaService = new TareaService();
        historialEstadoService = new HistorialEstadoService();
        panelTarea = new JPanel();
        panelTarea.setLayout(new GridLayout(1, 3));
        StringBuilder kanban = new StringBuilder("ESTADOS\n\n");
        try {
            ArrayList<Tarea> tareasPorHacer = historialEstadoService.obtenerTareasPorEstado("Por hacer");
            ArrayList<Tarea> tareasEnProgreso = historialEstadoService.obtenerTareasPorEstado("En progreso");
            ArrayList<Tarea> tareasCompletadas = historialEstadoService.obtenerTareasPorEstado("Completada");

            kanban.append("Por hacer: \n");
            for (Tarea tarea : tareasPorHacer){
                kanban.append("-").append(tarea.getTitulo()).append("\n");
            }
            kanban.append("\nEn progreso:\n");
            for (Tarea tarea : tareasEnProgreso){
                kanban.append("-").append(tarea.getTitulo()).append("\n");
            }
            kanban.append("\nCompletadas:\n");
            for (Tarea tarea : tareasCompletadas){
                kanban.append("-").append(tarea.getTitulo()).append("\n");
            }
            JTextArea kanbanTextArea = new JTextArea(kanban.toString());
            JScrollPane scrollPane = new JScrollPane(kanbanTextArea);

            JOptionPane.showMessageDialog(null, scrollPane, "Tablero Kanban", JOptionPane.PLAIN_MESSAGE);
        } catch (ServiceException ex){
            JOptionPane.showMessageDialog(null, "Error al obtener los datos");
        }
    }
}
