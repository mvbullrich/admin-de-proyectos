package Gui;

import Controlador.Empleado;
import Service.EmpleadoService;
import Service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelEmpleado extends JPanel {
    EmpleadoService empleadoService;
    PanelManager panel;
    JPanel panelEmpleado;

    JLabel jLabelId = new JLabel("ID: ");
    JLabel jLabelNombre = new JLabel("Nombre: ");
    JLabel jLabelCostoHora = new JLabel("Costo por hora: ");

    JTextField jTextFieldId = new JTextField(10);
    JTextField jTextFieldNombre = new JTextField(20);
    JTextField jTextFieldCostoHora = new JTextField(30);

    JButton jButtonOpcionGuardar = new JButton("Guardar");
    JButton jButtonOpcionBuscar = new JButton("Buscar");
    JButton jButtonOpcionEliminar = new JButton("Eliminar");
    JButton jButtonOpcionModificar= new JButton("Modificar");
    JButton jButtonAtras = new JButton("Atras");
    JButton jButtonSalir = new JButton("Salir");

    JButton jButtonGuardar = new JButton("Guardar");
    JButton jButtonBuscar = new JButton("Buscar");
    JButton jButtonEliminar = new JButton("Eliminar");
    JButton jButtonModificar = new JButton("Modificar");
    JButton jButtonMostrar = new JButton();

    JPanel jPanelBotones;

    public PanelEmpleado(PanelManager panel){
        this.panel = panel;
        mostrarOpciones();
    }

    public void mostrarOpciones(){
        panelEmpleado = new JPanel();
        panelEmpleado.setLayout(new GridLayout(1, 0));

        jButtonOpcionGuardar = new JButton("Guardar un empleado");
        jButtonOpcionBuscar = new JButton("Buscar un empleado");
        jButtonOpcionEliminar = new JButton("Eliminar un empleado");
        jButtonOpcionModificar = new JButton("Modificar un empleado");
        jButtonMostrar = new JButton("Mostrar todos");

        jPanelBotones = new JPanel();
        jPanelBotones.setLayout(new BoxLayout(jPanelBotones, BoxLayout.Y_AXIS));

        setLayout(new BorderLayout());
        add(panelEmpleado, BorderLayout.CENTER);

        jPanelBotones.add(jButtonOpcionGuardar);
        jPanelBotones.add(jButtonOpcionBuscar);
        jPanelBotones.add(jButtonOpcionEliminar);
        jPanelBotones.add(jButtonOpcionModificar);
        jPanelBotones.add(jButtonMostrar);
        jPanelBotones.add(jButtonAtras);
        add(jPanelBotones, BorderLayout.CENTER);

        jButtonOpcionGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelEmpleado panelEmpleado = new PanelEmpleado(panel);
                panelEmpleado.formularioGuardar();
                panel.mostrar(panelEmpleado);
            }
        });

        jButtonOpcionEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelEmpleado panelEmpleado = new PanelEmpleado(panel);
                panelEmpleado.formularioEliminar();
                panel.mostrar(panelEmpleado);
            }
        });

        jButtonOpcionBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelEmpleado panelEmpleado = new PanelEmpleado(panel);
                panelEmpleado.formularioBuscar();
                panel.mostrar(panelEmpleado);
            }
        });

        jButtonOpcionModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelEmpleado panelEmpleado = new PanelEmpleado(panel);
                panelEmpleado.formularioModificar();
                panel.mostrar(panelEmpleado);
            }
        });

        jButtonAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuInicio menuInicio = new MenuInicio(panel);
                menuInicio.armarMenuInicio();
                panel.mostrar(menuInicio);
            }
        });

        jButtonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelEmpleado panelEmpleado1 = new PanelEmpleado(panel);
                panelEmpleado1.mostrarTodos();
                panel.mostrar(panelEmpleado1);
            }
        });
    }

    //ACORDARSE DE AGREGAR LA VALIDACION PARA Q NO SE HAGA EMPLEADO CON MISMO ID QUE OTRO
    public void formularioGuardar(){
        empleadoService = new EmpleadoService();
        panelEmpleado = new JPanel();
        panelEmpleado.setLayout(new GridLayout(6, 2));

        jPanelBotones = new JPanel();

        panelEmpleado.add(jLabelId);
        panelEmpleado.add(jTextFieldId);

        panelEmpleado.add(jLabelNombre);
        panelEmpleado.add(jTextFieldNombre);

        panelEmpleado.add(jLabelCostoHora);
        panelEmpleado.add(jTextFieldCostoHora);

        setLayout(new BorderLayout());
        add(panelEmpleado, BorderLayout.CENTER);

        jPanelBotones.add(jButtonGuardar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarEmpleado();
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelEmpleado panelEmpleado = new PanelEmpleado(panel);
                panelEmpleado.mostrarOpciones();
                panel.mostrar(panelEmpleado);
            }
        });
    }

    public void guardarEmpleado() {
        Empleado empleado = new Empleado();
        try {
            int idEmpleado = Integer.parseInt(jTextFieldId.getText());
            empleado.setId(idEmpleado);
            empleado.setNombre(jTextFieldNombre.getText());
            empleado.setCostoPorHora(Double.parseDouble(jTextFieldCostoHora.getText()));
            empleado.setDisponible(true);

            if (idEmpleado <= 0) {
                JOptionPane.showMessageDialog(this,"El ID debe ser un número positivo");
            } else if (empleadoService.buscarEmpleado(idEmpleado) != null){
                JOptionPane.showMessageDialog(this, "Ese ID ya esta uso, por favor utilice otro");
            } else if (empleado.getNombre().isEmpty()){
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío");
            } else {
                empleadoService.guardarEmpleado(empleado);
                JOptionPane.showMessageDialog(this, "Empleado guardado exitosamente");
                jTextFieldId.setText("");
                jTextFieldNombre.setText("");
                jTextFieldCostoHora.setText("");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número válido");
        } catch (ServiceException serEx) {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el empleado: " + serEx.getMessage());
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Error. Se deben llenar todos los campos con valores correctos.");
        }
    }

    public void formularioEliminar(){
        empleadoService = new EmpleadoService();
        panelEmpleado = new JPanel();
        panelEmpleado.setLayout(new GridLayout(6, 2));

        jPanelBotones = new JPanel();

        jLabelId = new JLabel("ID del empleado: ");

        panelEmpleado.add(jLabelId);
        panelEmpleado.add(jTextFieldId);

        setLayout(new BorderLayout());
        add(panelEmpleado, BorderLayout.CENTER);

        jPanelBotones.add(jButtonEliminar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProyecto();
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelEmpleado panelEmpleado = new PanelEmpleado(panel);
                panelEmpleado.mostrarOpciones();
                panel.mostrar(panelEmpleado);
            }
        });
    }

    public void eliminarProyecto(){
        try{
            int idEmpleado = Integer.parseInt((jTextFieldId.getText()));
            Empleado empleado = empleadoService.buscarEmpleado(idEmpleado);
            if (empleado != null) {
                JOptionPane.showMessageDialog(null, "Empleado encontrado:\n" +
                        "ID: " + empleado.getId() + "\n" +
                        "Nombre: " + empleado.getNombre() + "\n" +
                        "Costo por hora: " + empleado.getCostoPorHora());
                int confirmacion = JOptionPane.showConfirmDialog(null, "¿Seguro que quiere eliminar este empleado?");
                if(confirmacion == JOptionPane.YES_OPTION) {
                    try {
                        empleadoService.eliminarEmpleado(idEmpleado);
                        JOptionPane.showMessageDialog(null, "Empleado eliminado exitosamente");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Ingrese un ID de empleado válido");
                    } catch (ServiceException serEx) {
                        JOptionPane.showMessageDialog(null, "No se pudo eliminar el empleado");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningun empleado con ese ID");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID de empleado válido");
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar el empleado");
        }
    }

    public void formularioBuscar(){
        empleadoService = new EmpleadoService();
        panelEmpleado = new JPanel();
        panelEmpleado.setLayout(new GridLayout(6, 2));

        jPanelBotones = new JPanel();

        jLabelId = new JLabel("ID del empleado:");

        panelEmpleado.add(jLabelId);
        panelEmpleado.add(jTextFieldId);

        setLayout(new BorderLayout());
        add(panelEmpleado, BorderLayout.CENTER);

        jPanelBotones.add(jButtonBuscar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProyecto();
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelEmpleado panelEmpleado = new PanelEmpleado(panel);
                panelEmpleado.mostrarOpciones();
                panel.mostrar(panelEmpleado);
            }
        });
    }

    public void buscarProyecto(){
        try {
            int idEmpleado = Integer.parseInt((jTextFieldId.getText()));
            Empleado empleado = empleadoService.buscarEmpleado(idEmpleado);
            if (empleado != null) {
                JOptionPane.showMessageDialog(null, "Empleado encontrado:\n" +
                        "ID: " + empleado.getId() + "\n" +
                        "Nombre: " + empleado.getNombre() + "\n" +
                        "Costo por hora: " + empleado.getCostoPorHora());
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningun empleado con ese ID");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID de empleado válido");
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar el empleado");
        }
    }

    public void formularioModificar(){
        empleadoService = new EmpleadoService();
        panelEmpleado = new JPanel();
        panelEmpleado.setLayout(new GridLayout(6, 2));

        jPanelBotones = new JPanel();

        jLabelId = new JLabel("ID del empleado: ");

        panelEmpleado.add(jLabelId);
        panelEmpleado.add(jTextFieldId);

        setLayout(new BorderLayout());
        add(panelEmpleado, BorderLayout.CENTER);

        jPanelBotones.add(jButtonModificar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int idEmpleado = Integer.parseInt((jTextFieldId.getText()));
                    Empleado empleado = empleadoService.buscarEmpleado(idEmpleado);
                    if (empleado != null) {
                        modificarEmpleado(empleado);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró ningun empleado con ese ID");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Ingrese un ID de empleado válido");
                } catch (ServiceException ex) {
                    JOptionPane.showMessageDialog(null, "Error al buscar el empleado");
                }
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelEmpleado panelEmpleado = new PanelEmpleado(panel);
                panelEmpleado.mostrarOpciones();
                panel.mostrar(panelEmpleado);
            }
        });
    }

    public void modificarEmpleado(Empleado empleado){
        empleadoService = new EmpleadoService();
        panelEmpleado = new JPanel();
        panelEmpleado.setLayout(new GridLayout(6, 2));

        jLabelId = new JLabel("ID: ");
        jLabelNombre = new JLabel("Nombre: ");
        jLabelCostoHora = new JLabel("Costo por hora: ");

        jTextFieldId = new JTextField(String.valueOf(empleado.getId()));
        jTextFieldNombre = new JTextField(empleado.getNombre());
        jTextFieldCostoHora = new JTextField((String.valueOf(empleado.getCostoPorHora())));

        jButtonModificar = new JButton();
        jPanelBotones = new JPanel();

        panelEmpleado.add(jLabelId);
        panelEmpleado.add(jTextFieldId);

        panelEmpleado.add(jLabelNombre);
        panelEmpleado.add(jTextFieldNombre);

        panelEmpleado.add(jLabelCostoHora);
        panelEmpleado.add(jTextFieldCostoHora);

        setLayout(new BorderLayout());
        add(panelEmpleado, BorderLayout.CENTER);

        jPanelBotones.add(jButtonModificar);
        add(jPanelBotones, BorderLayout.SOUTH);

        int opcion = JOptionPane.showOptionDialog(null, panelEmpleado, "Modificar Empleado", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
        if (opcion == JOptionPane.OK_OPTION){
            empleado.setId(Integer.parseInt(jTextFieldId.getText()));
            empleado.setNombre(jTextFieldNombre.getText());
            empleado.setCostoPorHora(Double.parseDouble(jTextFieldCostoHora.getText()));
            try {
                empleadoService.modificar(empleado);
                JOptionPane.showMessageDialog(null, "Empleado modificado exitosamente");
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(null, "No se pudo modificar el empleado");
            }
        }
    }

    public void mostrarTodos(){
        empleadoService = new EmpleadoService();
        try{
            ArrayList<Empleado> empleados = empleadoService.obtenerTodosLosEmpleados();
            StringBuilder empleadosInfo = new StringBuilder("Lista de empleados:\n\n");

            for (Empleado empleado : empleados){
                empleadosInfo.append("ID: ").append(empleado.getId()).append("\n");
                empleadosInfo.append("Nombre: ").append(empleado.getNombre()).append("\n");
                empleadosInfo.append("Costo por hora: ").append(empleado.getCostoPorHora()).append("\n");
                if(empleado.isDisponible() == true){
                    empleadosInfo.append("Disponibilidad: DISPONIBLE\n");
                } else{
                    empleadosInfo.append("Disponibilidad: NO DISPONIBLE\n");
                }

                empleadosInfo.append("\n");
            }
            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.setText(empleadosInfo.toString());

            JScrollPane scrollPane = new JScrollPane(textArea);
            JOptionPane.showMessageDialog(null, scrollPane, "Empleados", JOptionPane.INFORMATION_MESSAGE);
        } catch (ServiceException e){
            JOptionPane.showMessageDialog(null, "Error al obtener la lista de empleados: " + e.getMessage());
        }
    }
}
