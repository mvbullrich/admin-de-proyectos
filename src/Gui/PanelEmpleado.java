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
    JButton jButtonMostrar = new JButton("Mostrar datos");
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

        jPanelBotones.add(jButtonGuardar);
        jPanelBotones.add(jButtonEmpleados);
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
                    if (jTextFieldId.getText().isEmpty() || jTextFieldNombre.getText().isEmpty() || jTextFieldCostoPorHora.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
                    } else {
                        empleado.setId(Integer.parseInt(jTextFieldId.getText()));
                        empleado.setNombre(jTextFieldNombre.getText());

                        try {
                            double costoPorHora = Double.parseDouble(jTextFieldCostoPorHora.getText());
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

    public void menuEmpleados(){
        empleadoService = new EmpleadoService();
        tareaService = new TareaService();
        panelEmpleado = new JPanel();
        panelEmpleado.setLayout(new GridLayout(1,6));
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
        panelEmpleado.add(jComboBoxEmpleados);
        setLayout(new BorderLayout());
        add(panelEmpleado, BorderLayout.CENTER);

        jPanelBotones.add(jButtonEliminar);
        jPanelBotones.add(jButtonModificar);
        jPanelBotones.add(jButtonMostrar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

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
                            jComboBoxEmpleados.setSelectedIndex(-1);
                            jComboBoxEmpleados.updateUI();
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
                if (empleado != null){
                    PanelEmpleado panelEmpleado1 = new PanelEmpleado(panel);
                    panelEmpleado1.modificarEmpleado(empleado);
                    panel.mostrar(panelEmpleado1);
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
                        StringBuilder empleadosInfo = new StringBuilder("EMPLEADO:\n");
                        empleadosInfo.append("ID: ").append(empleado.getId()).append("\n");
                        empleadosInfo.append("Nombre: ").append(empleado.getNombre()).append("\n");
                        empleadosInfo.append("Costo por hora: ").append(empleado.getCostoPorHora()).append("\n");

                        ArrayList<Tarea> tareas = tareaService.obtenerTodasLasTareas();
                        boolean tieneTareaAsignada = false;
                        for (Tarea tarea : tareas){
                            int idEmp = tarea.getEmpleado_id();
                            if (idEmp == empleado.getId()){
                                empleadosInfo.append("Tarea asignada: ").append(tarea.getTitulo()).append("\n");
                                tieneTareaAsignada = true;
                            }
                        }
                        if (tieneTareaAsignada == false){
                            empleadosInfo.append("Este empleado no tiene ninguna tarea asignada");
                        }
                        JOptionPane.showMessageDialog(null, empleadosInfo.toString(), "Información del empleado", JOptionPane.INFORMATION_MESSAGE);
                    } catch (ServiceException ex){
                        JOptionPane.showMessageDialog(null, "Error al obtener los datos del empleado");
                    }
                }
            }
        });
    }

    public void modificarEmpleado(Empleado empleado) {
        empleadoService = new EmpleadoService();
        panelEmpleado = new JPanel();
        panelEmpleado.setLayout(new GridLayout(3, 2));

        jTextFieldId = new JTextField(String.valueOf(empleado.getId()));
        jTextFieldNombre = new JTextField(empleado.getNombre());
        jTextFieldCostoPorHora = new JTextField(String.valueOf(empleado.getCostoPorHora()));

        jButtonModificar = new JButton("Modificar");
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

        jPanelBotones.add(jButtonModificar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    empleado.setId(Integer.parseInt(jTextFieldId.getText()));
                    empleado.setNombre(jTextFieldNombre.getText());
                    empleado.setCostoPorHora(Double.parseDouble(jTextFieldCostoPorHora.getText()));
                    empleadoService.modificar(empleado);
                    JOptionPane.showMessageDialog(null, "Empleado modificado exitosamente");
                } catch (ServiceException ex) {
                    JOptionPane.showMessageDialog(null, "Error al modificar el empleado");
                }
                jTextFieldId.setText("");
                jTextFieldNombre.setText("");
                jTextFieldCostoPorHora.setText("");
            }
        });
        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelEmpleado panelEmpleado1 = new PanelEmpleado(panel);
                panelEmpleado1.menuEmpleados();
                panel.mostrar(panelEmpleado1);
            }
        });
    }
}
