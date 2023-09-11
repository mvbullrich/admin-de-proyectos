package Gui;

import Controlador.Empleado;
import Controlador.HistorialEstado;
import Controlador.Tarea;
import Service.EmpleadoService;
import Service.HistorialEstadoService;
import Service.ServiceException;
import Service.TareaService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;

public class PanelHistorialEstado extends JPanel {
    HistorialEstadoService historialEstadoService;
    PanelManager panel;
    JPanel panelEstados;

    JLabel jLabelTareas = new JLabel();
    JComboBox<Tarea> jComboBoxTareas = new JComboBox<>();

    JLabel jLabelEstado = new JLabel("Estado:");
    JTextField jTextFieldEstado = new JTextField();
    JLabel jLabelFecha = new JLabel("Fecha: (yyyy-MM-dd)");
    JTextField jTextFieldFecha = new JTextField();
    JLabel jLabelResponsable = new JLabel("Empleado responsable:");
    JComboBox jComboBoxEmpleado = new JComboBox<>();

    JPanel jPanelBotones;
    JButton jButtonActualizar;
    JButton jButtonGuardarEstado;
    JButton jButtonAtras;
    JButton jButtonMostrar;

    public PanelHistorialEstado(PanelManager panel) {
        this.panel = panel;
        opciones();
    }

    public void opciones(){
        panelEstados = new JPanel();
        panelEstados.setLayout(new GridLayout(5, 1));

        jButtonActualizar = new JButton("Actualizar estado de una tarea");
        jButtonAtras = new JButton("Atras");
        jButtonMostrar = new JButton("Mostrar todos");

        jPanelBotones = new JPanel();
        jPanelBotones.setLayout(new BoxLayout(jPanelBotones, BoxLayout.Y_AXIS));

        setLayout(new BorderLayout());
        add(panelEstados, BorderLayout.CENTER);

        jPanelBotones.add(jButtonActualizar);
        jPanelBotones.add(jButtonMostrar);
        jPanelBotones.add(jButtonAtras);
        add(jPanelBotones, BorderLayout.CENTER);

        jButtonActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelHistorialEstado panelEstado = new PanelHistorialEstado(panel);
                panelEstado.formularioGuardarEstado();
                panel.mostrar(panelEstado);
            }
        });
        jButtonAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTarea panelTarea = new PanelTarea(panel);
                panelTarea.mostrarOpciones();
                panel.mostrar(panelTarea);
            }
        });
        jButtonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelHistorialEstado panelHistorialEstado = new PanelHistorialEstado(panel);
                panelHistorialEstado.mostrarHistoriales();
                panel.mostrar(panelHistorialEstado);
            }
        });
    }

    TareaService tareaService = new TareaService();
    EmpleadoService empleadoService = new EmpleadoService();
    public void formularioGuardarEstado(){
        historialEstadoService = new HistorialEstadoService();
        tareaService = new TareaService();
        panelEstados = new JPanel();
        panelEstados.setLayout(new GridLayout(5,2));

        jPanelBotones = new JPanel();

        jLabelTareas = new JLabel("Seleccione una tarea");
        jComboBoxTareas = new JComboBox<>();
        jButtonGuardarEstado = new JButton("Guardar");
        jButtonAtras = new JButton("Atras");

        try{
            ArrayList<Tarea> tareas = tareaService.obtenerTodasLasTareas();
            for(Tarea tarea:tareas){
                jComboBoxTareas.addItem(tarea);
            }
        } catch (ServiceException e){
            JOptionPane.showMessageDialog(null, "Error al obtener las tareas: " + e.getMessage());
        }

        try{
            ArrayList<Empleado> empleados = empleadoService.obtenerTodosLosEmpleados();
            for(Empleado empleado:empleados){
                jComboBoxEmpleado.addItem(empleado);
            }
        } catch (ServiceException e){
            JOptionPane.showMessageDialog(null, "Error al obtener los empleados: " + e.getMessage());
        }

        panelEstados.add(jLabelTareas);
        panelEstados.add(jComboBoxTareas);
        panelEstados.add(jLabelEstado);
        panelEstados.add(jTextFieldEstado);
        panelEstados.add(jLabelFecha);
        panelEstados.add(jTextFieldFecha);
        panelEstados.add(jLabelResponsable);
        panelEstados.add(jComboBoxEmpleado);

        panelEstados.add(jButtonGuardarEstado);
        panelEstados.add(jButtonAtras);

        setLayout(new BorderLayout());
        add(panelEstados, BorderLayout.CENTER);

        jPanelBotones.add(jButtonGuardarEstado);
        jPanelBotones.add(jButtonAtras);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonGuardarEstado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarEstado();
            }
        });
        jButtonAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelHistorialEstado panelHistorialEstado = new PanelHistorialEstado(panel);
                panelHistorialEstado.opciones();
                panel.mostrar(panelHistorialEstado);
            }
        });
    }

    public void guardarEstado(){
        Tarea tareaSeleccionada = (Tarea) jComboBoxTareas.getSelectedItem();
        String estado = jTextFieldEstado.getText();
        String fechaTexto = jTextFieldFecha.getText();
        Empleado empleadoSeleccionado = (Empleado) jComboBoxEmpleado.getSelectedItem();
        try {
            Date fecha = Date.valueOf(fechaTexto);

            HistorialEstado historialEstado = new HistorialEstado();
            historialEstado.setIdTarea(tareaSeleccionada.getId());
            historialEstado.setEstado(estado);
            historialEstado.setFecha(fecha);
            historialEstado.setIdResponsable(empleadoSeleccionado.getId());

            try{
                historialEstadoService.guardarHistorialEstado(historialEstado);
                JOptionPane.showMessageDialog(null, "Estado guardado correctamente");
            } catch (ServiceException ex){
                JOptionPane.showMessageDialog(null, "Error al guardar el estado: " + ex.getMessage());
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto");
        }
    }

    JLabel jLabelIdTarea;
    JLabel jLabelIdResposable;
    JLabel jLabelEstado1;
    ArrayList<HistorialEstado> historiales = new ArrayList<>();
    public void mostrarHistoriales(){
        historialEstadoService = new HistorialEstadoService();
        panelEstados = new JPanel();

        try {
            ArrayList<HistorialEstado> historiales = historialEstadoService.obtenerDatos();
            StringBuilder historialesInfo = new StringBuilder("Historial de Estados:\n\n");

            for (HistorialEstado historial : historiales) {
                historialesInfo.append("ID tarea: ").append(historial.getIdTarea()).append("\n");
                historialesInfo.append("Titulo de la tarea: ").append(historial.getTituloTarea()).append("\n");
                historialesInfo.append("Estado: ").append(historial.getEstado()).append("\n");
                historialesInfo.append("Fecha: ").append(historial.getFecha()).append("\n");
                historialesInfo.append("ID responsable: ").append(historial.getIdResponsable()).append("\n");
                historialesInfo.append("Nombre del responsable: ").append(historial.getNombreResponsable()).append("\n");

                historialesInfo.append("\n");
            }

            JTextArea textArea = new JTextArea(20, 50);
            textArea.setEditable(false);
            textArea.setText(historialesInfo.toString());

            JScrollPane scrollPane = new JScrollPane(textArea);

            JOptionPane.showMessageDialog(null, scrollPane, "Historial de Estados", JOptionPane.INFORMATION_MESSAGE);

        } catch (ServiceException e){
            JOptionPane.showMessageDialog(null, "Error al obtener los historiales: " + e.getMessage());
        }


    }
}
