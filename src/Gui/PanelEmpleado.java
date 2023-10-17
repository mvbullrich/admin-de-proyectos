package Gui;

import Controlador.Empleado;
import Controlador.Tarea;
import Service.EmpleadoService;
import Service.ServiceException;
import Service.TareaService;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelEmpleado extends JPanel {
    EmpleadoService empleadoService;
    TareaService tareaService;
    PanelManager panel;
    JPanel panelEmpleado;
    JLabel jLabel = new JLabel("Seleccione un empleado: ");
    JLabel jLabelId = new JLabel("ID: ");
    JLabel jLabelNombre = new JLabel("Nombre: ");
    JLabel jLabelCostoPorHora = new JLabel("Costo Por Hora: ");
    JTextField jTextFieldId = new JTextField();
    JTextField jTextFieldNombre = new JTextField();
    JTextField jTextFieldCostoPorHora = new JTextField();
    JComboBox<Empleado> jComboBoxEmpleados;

    JButton jButtonGuardar = new JButton("Guardar un nuevo empleado");
    JButton jButtonSalir = new JButton("Salir");
    JButton jButtonEmpleados = new JButton("Empleados");
    JButton jButtonEliminar = new JButton("Eliminar");
    JButton jButtonModificar = new JButton("Modificar");
    JButton jButtonMostrar = new JButton("Ver su tarea");
    JButton jButtonTarea = new JButton("Ir a tareas");
    JPanel jPanelBotones;
    public PanelEmpleado(PanelManager panel){
        this.panel = panel;
        inicio();
    }

    public void inicio(){
        panelEmpleado = new JPanel();
        panelEmpleado.setLayout(new GridLayout(1,1));
        jPanelBotones = new JPanel();
        jPanelBotones.setLayout(new BoxLayout(jPanelBotones, BoxLayout.Y_AXIS));

        setLayout(new BorderLayout());
        add(panelEmpleado, BorderLayout.CENTER);

        jPanelBotones.add(jButtonEmpleados);
        jPanelBotones.add(jButtonGuardar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelEmpleado panelEmpleado1 = new PanelEmpleado(panel);
                panelEmpleado1.guardarEmpleado();
                panel.mostrar(panelEmpleado1);
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

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuInicio menuInicio = new MenuInicio(panel);
                menuInicio.armarMenuInicio();
                panel.mostrar(menuInicio);
            }
        });
    }

    public void guardarEmpleado(){
        empleadoService = new EmpleadoService();
        panelEmpleado = new JPanel();
        panelEmpleado.setLayout(new GridLayout(3,1));

        jButtonGuardar = new JButton("Guardar");
        jButtonSalir = new JButton("Atrás");
        jPanelBotones = new JPanel();

        panelEmpleado.add(jLabelId);
        panelEmpleado.add(jTextFieldId);
        panelEmpleado.add(jLabelNombre);
        panelEmpleado.add(jTextFieldNombre);
        panelEmpleado.add(jLabelCostoPorHora);
        panelEmpleado.add(jTextFieldCostoPorHora);

        setLayout(new BorderLayout());
        add(panelEmpleado, BorderLayout.CENTER);

        jPanelBotones.add(jButtonGuardar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Empleado empleado = new Empleado();
                try {
                    String idText = jTextFieldId.getText();
                    String nombre = jTextFieldNombre.getText();
                    String costoPorHoraText = jTextFieldCostoPorHora.getText();
                    if (idText.isEmpty() || nombre.isEmpty() || costoPorHoraText.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
                    } else {
                        try {
                            int id = Integer.parseInt(idText);
                            empleado.setId(id);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "El ID debe ser un número entero válido.");
                            return;
                        }
                        empleado.setNombre(nombre);
                        try {
                            double costoPorHora = Double.parseDouble(costoPorHoraText);
                            if (costoPorHora < 0) {
                                JOptionPane.showMessageDialog(null, "El costo por hora debe ser un número positivo.");
                            } else {
                                empleado.setCostoPorHora(costoPorHora);
                                empleadoService.guardarEmpleado(empleado);
                                JOptionPane.showMessageDialog(null, "Empleado guardado exitosamente");
                                jTextFieldId.setText("");
                                jTextFieldNombre.setText("");
                                jTextFieldCostoPorHora.setText("");
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "El costo por hora debe ser un número válido.");
                        }
                    }
                } catch (ServiceException serEx) {
                    JOptionPane.showMessageDialog(null, "Error al guardar el empleado");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Error en los datos del empleado");
                }
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelEmpleado panelEmpleado1 = new PanelEmpleado(panel);
                panelEmpleado1.inicio();
                panel.mostrar(panelEmpleado1);
            }
        });
    }

    public void actualizarCampos(){
        Empleado empleado = (Empleado) jComboBoxEmpleados.getSelectedItem();
        if (empleado != null){
            jTextFieldId.setText(String.valueOf(empleado.getId()));
            jTextFieldNombre.setText(empleado.getNombre());
            jTextFieldCostoPorHora.setText(String.valueOf(empleado.getCostoPorHora()));
        } else {
            jTextFieldId.setText("");
            jTextFieldNombre.setText("");
            jTextFieldCostoPorHora.setText("");
        }
    }

    public void menuEmpleados(){
        empleadoService = new EmpleadoService();
        tareaService = new TareaService();
        panelEmpleado = new JPanel();
        panelEmpleado.setLayout(new GridLayout(5,2));
        jButtonSalir = new JButton("Salir");
        jPanelBotones = new JPanel();
        jComboBoxEmpleados = new JComboBox<>();
        try{
            ArrayList<Empleado> empleados = empleadoService.obtenerTodosLosEmpleados();
            for (Empleado empleado : empleados){
                jComboBoxEmpleados.addItem(empleado);
            }
        } catch (ServiceException e){
            JOptionPane.showMessageDialog(null, "Error al obtener los empleados");
        }
        actualizarCampos();

        panelEmpleado.add(jLabel);
        panelEmpleado.add(jComboBoxEmpleados);
        panelEmpleado.add(jLabelId);
        panelEmpleado.add(jTextFieldId);
        panelEmpleado.add(jLabelNombre);
        panelEmpleado.add(jTextFieldNombre);
        panelEmpleado.add(jLabelCostoPorHora);
        panelEmpleado.add(jTextFieldCostoPorHora);
        setLayout(new BorderLayout());
        add(panelEmpleado, BorderLayout.CENTER);

        jPanelBotones.add(jButtonMostrar);
        jPanelBotones.add(jButtonModificar);
        jPanelBotones.add(jButtonEliminar);
        jPanelBotones.add(jButtonTarea);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jComboBoxEmpleados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCampos();
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelEmpleado panelEmpleado1 = new PanelEmpleado(panel);
                panelEmpleado1.inicio();
                panel.mostrar(panelEmpleado1);
            }
        });

        jButtonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Empleado empleado = (Empleado) jComboBoxEmpleados.getSelectedItem();
                if (empleado != null){
                    try{
                        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar este empleado?");
                        if (confirmacion == JOptionPane.YES_OPTION){
                            empleadoService.eliminarEmpleado(empleado.getId());
                            JOptionPane.showMessageDialog(null, "Empleado eliminado exitosamente");
                            jComboBoxEmpleados.removeItem(empleado);
                            jComboBoxEmpleados.setSelectedIndex(-1);
                        }
                    } catch (ServiceException ex){
                        JOptionPane.showMessageDialog(null, "Error al eliminar el empleado");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un empleado");
                }
            }
        });

        jButtonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Empleado empleado = (Empleado) jComboBoxEmpleados.getSelectedItem();
                if (empleado != null) {
                    try {
                        String idText = jTextFieldId.getText();
                        String nombre = jTextFieldNombre.getText();
                        String costoPorHoraText = jTextFieldCostoPorHora.getText();
                        if (idText.isEmpty() || nombre.isEmpty() || costoPorHoraText.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
                        } else {
                            try {
                                int id = Integer.parseInt(idText);
                                empleado.setId(id);
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "El ID debe ser un número entero válido.");
                                return;
                            }
                            empleado.setNombre(nombre);
                            try {
                                double costoPorHora = Double.parseDouble(costoPorHoraText);
                                if (costoPorHora < 0) {
                                    JOptionPane.showMessageDialog(null, "El costo por hora debe ser un número positivo.");
                                } else {
                                    empleado.setCostoPorHora(costoPorHora);
                                    empleadoService.modificar(empleado);
                                    JOptionPane.showMessageDialog(null, "Empleado modificado exitosamente");
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "El costo por hora debe ser un número válido.");
                            }
                        }
                    } catch (ServiceException serEx) {
                        JOptionPane.showMessageDialog(null, "Error al modificar el empleado");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un empleado");
                }
            }
        });

        jButtonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Empleado empleado = (Empleado) jComboBoxEmpleados.getSelectedItem();
                if (empleado != null){
                    try{
                        empleadoService.buscarEmpleado(empleado.getId());
                        StringBuilder empleadosInfo = new StringBuilder("EMPLEADO ").append(empleado.getNombre()).append("\n");

                        ArrayList<Tarea> tareas = tareaService.obtenerTodasLasTareas();
                        boolean tieneTareaAsignada = false;
                        for (Tarea tarea : tareas){
                            int idEmp = tarea.getEmpleado_id();
                            if (idEmp == empleado.getId()){
                                empleadosInfo.append("Tarea asignada: ").append("\n");
                                empleadosInfo.append("-Titulo: ").append(tarea.getTitulo()).append("\n");
                                empleadosInfo.append("-Descripcion: ").append(tarea.getDescripcion()).append("\n");
                                tieneTareaAsignada = true;
                            }
                        }
                        if (tieneTareaAsignada == false){
                            empleadosInfo.append("No tiene ninguna tarea asignada");
                        }
                        JOptionPane.showMessageDialog(null, empleadosInfo.toString(), "Información del empleado", JOptionPane.INFORMATION_MESSAGE);
                    } catch (ServiceException ex){
                        JOptionPane.showMessageDialog(null, "Error al obtener los datos del empleado");
                    }
                }
            }
        });

        jButtonTarea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTarea panelTarea1 = new PanelTarea(panel);
                panelTarea1.menuTareas();
                panel.mostrar(panelTarea1);
            }
        });
    }
}
