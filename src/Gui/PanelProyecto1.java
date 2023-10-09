package Gui;

import Controlador.Proyecto;
import Controlador.Tarea;
import Service.ProyectoService;
import Service.ServiceException;
import Service.TareaService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class PanelProyecto1 extends JPanel{
    ProyectoService proyectoService;
    TareaService tareaService;
    PanelManager panel;
    JPanel panelProyecto;
    JLabel jLabelId = new JLabel("ID: ");
    JLabel jLabelTitulo = new JLabel("Titulo: ");
    JLabel jLabelDescripcion = new JLabel("Descripción:");
    JLabel jLabelFechaInicio = new JLabel("Fecha de Inicio (YYYY-MM-DD):");
    JLabel jLabelFechaFin = new JLabel("Fecha de Fin (YYYY-MM-DD):");

    JTextField jTextFieldId = new JTextField(10);
    JTextField jTextFieldTitulo = new JTextField(30);
    JTextField jTextFieldDescripcion = new JTextField(50);
    JTextField jTextFieldFechaInicio = new JTextField(10);
    JTextField jTextFieldFechaFin = new JTextField(10);
    JComboBox<Proyecto> jComboBoxProyecto;

    JButton jButtonGuardar;
    JButton jButtonSalir = new JButton("Salir");
    JButton jButtonMisProyectos = new JButton("Mis proyectos");
    JButton jButtonEliminar = new JButton("Eliminar");
    JButton jButtonModificar = new JButton("Modificar");
    JButton jButtonMostrar = new JButton("Mostrar datos");
    JButton jButtonCostoHoras = new JButton("Calcular costo horas");
    JButton jButtonCostoDinero = new JButton("Calcular costo dinero");
    JPanel jPanelBotones;

    public PanelProyecto1(PanelManager panel){
        this.panel = panel;
        menu();
    }

    public void menu(){
        panelProyecto = new JPanel();
        panelProyecto.setLayout(new GridLayout(3,1));
        jButtonGuardar = new JButton("Guardar un nuevo proyecto");
        jPanelBotones = new JPanel();
        jPanelBotones.setLayout(new BoxLayout(jPanelBotones, BoxLayout.Y_AXIS));

        setLayout(new BorderLayout());
        add(panelProyecto, BorderLayout.CENTER);

        jPanelBotones.add(jButtonGuardar);
        jPanelBotones.add(jButtonMisProyectos);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelProyecto1 panelProyecto1 = new PanelProyecto1(panel);
                panelProyecto1.formularioGuardar();
                panel.mostrar(panelProyecto1);
            }
        });

        jButtonMisProyectos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelProyecto1 panelProyecto1 = new PanelProyecto1(panel);
                panelProyecto1.menuProyectos();
                panel.mostrar(panelProyecto1);
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

    public void formularioGuardar(){
        proyectoService = new ProyectoService();
        panelProyecto = new JPanel();
        panelProyecto.setLayout(new GridLayout(6, 2));

        jPanelBotones = new JPanel();

        panelProyecto.add(jLabelTitulo);
        panelProyecto.add(jTextFieldTitulo);

        panelProyecto.add(jLabelDescripcion);
        panelProyecto.add(jTextFieldDescripcion);

        panelProyecto.add(jLabelFechaInicio);
        panelProyecto.add(jTextFieldFechaInicio);

        panelProyecto.add(jLabelFechaFin);
        panelProyecto.add(jTextFieldFechaFin);

        setLayout(new BorderLayout());
        add(panelProyecto, BorderLayout.CENTER);

        jPanelBotones.add(jButtonGuardar);
        jButtonSalir = new JButton("Atrás");
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProyecto();
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelProyecto1 panelProyecto1 = new PanelProyecto1(panel);
                panelProyecto1.menu();
                panel.mostrar(panelProyecto1);
            }
        });
    }

    public void guardarProyecto(){
        Proyecto proyecto = new Proyecto();
        try {
            proyecto.setTitulo(jTextFieldTitulo.getText());
            proyecto.setDescripcion(jTextFieldDescripcion.getText());
            proyecto.setFechaInicio(LocalDate.parse(jTextFieldFechaInicio.getText()));
            proyecto.setFechaFin(LocalDate.parse(jTextFieldFechaFin.getText()));
            if (proyecto.getTitulo().isEmpty() || proyecto.getDescripcion().isEmpty()){
                throw new IllegalArgumentException();

            }
            if (proyecto.getFechaInicio().isAfter(proyecto.getFechaFin())){
                JOptionPane.showMessageDialog(null, "La fecha de inicio no puede ser posterior a la fecha de fin");
                return;
            }
            else{
                proyectoService.guardarProyecto(proyecto);
                JOptionPane.showMessageDialog(null, "Proyecto guardado exitosamente");
                jTextFieldTitulo.setText("");
                jTextFieldDescripcion.setText("");
                jTextFieldFechaInicio.setText("");
                jTextFieldFechaFin.setText("");
            }
        } catch (ServiceException serEx) {
            serEx.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se pudo guardar");
        } catch (IllegalArgumentException ex){
            JOptionPane.showMessageDialog(null,"Error. Se deben llenar todos los campos");
        } catch(DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "El formato de fecha ingresado no es válido");
        }
    }

    public void menuProyectos(){
        proyectoService = new ProyectoService();
        tareaService = new TareaService();
        panelProyecto = new JPanel();
        panelProyecto.setLayout(new GridLayout(1,8));
        jButtonSalir = new JButton("Salir");
        jPanelBotones = new JPanel();
        jComboBoxProyecto = new JComboBox<>();
        try{
            ArrayList<Proyecto> proyectos = proyectoService.obtenerProyectos();
            for (Proyecto proyecto : proyectos){
                jComboBoxProyecto.addItem(proyecto);
            }
        } catch (ServiceException e){
            JOptionPane.showMessageDialog(null, "Error al obtener los proyectos");
        }
        panelProyecto.add(jComboBoxProyecto);
        setLayout(new BorderLayout());
        add(panelProyecto, BorderLayout.CENTER);

        jPanelBotones.add(jButtonEliminar);
        jPanelBotones.add(jButtonModificar);
        jPanelBotones.add(jButtonMostrar);
        jPanelBotones.add(jButtonCostoHoras);
        jPanelBotones.add(jButtonCostoDinero);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        //Proyecto proyecto = (Proyecto) jComboBoxProyecto.getSelectedItem();

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelProyecto1 panelProyecto1 = new PanelProyecto1(panel);
                panelProyecto1.menu();
                panel.mostrar(panelProyecto1);
            }
        });

        jButtonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Proyecto proyectoSeleccionado = (Proyecto) jComboBoxProyecto.getSelectedItem();
                if(proyectoSeleccionado != null){
                    try{
                        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar este proyecto?");
                        if (confirmacion == JOptionPane.YES_OPTION){
                            proyectoService.eliminarProyecto(proyectoSeleccionado.getId());
                            JOptionPane.showMessageDialog(null, "Proyecto eliminado exitosamente");
                            jComboBoxProyecto.setSelectedIndex(-1);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Ingrese un ID de proyecto válido");
                    } catch (ServiceException ex){
                        JOptionPane.showMessageDialog(null, "Error al eliminar el proyecto");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un proyecto");
                }
            }
        });

        jButtonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Proyecto proyectoSeleccionado = (Proyecto) jComboBoxProyecto.getSelectedItem();
                if (proyectoSeleccionado != null){
                    PanelProyecto1 panelProyecto1 = new PanelProyecto1(panel);
                    panelProyecto1.modificarProyecto(proyectoSeleccionado);
                    panel.mostrar(panelProyecto1);
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un proyecto");
                }
            }
        });

        jButtonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Proyecto proyectoSeleccionado = (Proyecto) jComboBoxProyecto.getSelectedItem();
                if (proyectoSeleccionado != null) {
                    try {
                        int idProyecto = proyectoSeleccionado.getId();
                        proyectoService.buscarProyecto(idProyecto);
                        StringBuilder proyctosInfo = new StringBuilder("PROYECTO:\n");
                        proyctosInfo.append("ID: ").append(proyectoSeleccionado.getId()).append("\n");
                        proyctosInfo.append("Titulo: ").append(proyectoSeleccionado.getTitulo()).append("\n");
                        proyctosInfo.append("Descripcion: ").append(proyectoSeleccionado.getDescripcion()).append("\n");
                        proyctosInfo.append("Fecha Inicio: ").append(proyectoSeleccionado.getFechaInicio()).append("\n");
                        proyctosInfo.append("Fecha Fin: ").append(proyectoSeleccionado.getFechaFin()).append("\n");

                        ArrayList<Tarea> tareas = tareaService.obtenerTareasAsignadas(idProyecto);
                        if (!tareas.isEmpty()) {
                            proyctosInfo.append("Tareas asignadas:\n");
                            for (Tarea tarea : tareas) {
                                proyctosInfo.append("- ").append(tarea.getTitulo()).append("\n");
                            }
                        } else {
                            proyctosInfo.append("No hay tareas asignadas a este proyecto");
                        }
                        JOptionPane.showMessageDialog(null, proyctosInfo.toString(), "Información del Proyecto", JOptionPane.INFORMATION_MESSAGE);
                    } catch (ServiceException ex) {
                        JOptionPane.showMessageDialog(null, "Error al obtener los datos");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un proyecto");
                }
            }
        });

        jButtonCostoHoras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Proyecto proyecto = (Proyecto) jComboBoxProyecto.getSelectedItem();
                if (proyecto != null){
                    try {
                        double total = proyectoService.calcularCostoHoras(proyecto.getId());
                        JOptionPane.showMessageDialog(null, "Costo en horas del proyecto " +proyecto.getTitulo()+": "+total);
                    } catch (ServiceException ex){
                        JOptionPane.showMessageDialog(null, "Error al calcular el costo en horas");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un proyecto");
                }
            }
        });

        jButtonCostoDinero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Proyecto proyecto = (Proyecto) jComboBoxProyecto.getSelectedItem();
                if (proyecto != null){
                    try{
                        double total = proyectoService.calcularCostoDinero(proyecto.getId());
                        JOptionPane.showMessageDialog(null, "El costo total del proyecto " + proyecto.getTitulo() + " es: " + total);
                    } catch (ServiceException ex){
                        JOptionPane.showMessageDialog(null, "Error al calcular el costo del proyecto");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un proyecto");
                }
            }
        });
    }

    public void modificarProyecto(Proyecto proyecto){
        proyectoService = new ProyectoService();
        panelProyecto = new JPanel();
        panelProyecto.setLayout(new GridLayout(6, 2));

        jTextFieldTitulo = new JTextField(proyecto.getTitulo());
        jTextFieldDescripcion = new JTextField(proyecto.getDescripcion());
        jTextFieldFechaInicio = new JTextField(proyecto.getFechaInicio().toString());
        jTextFieldFechaFin = new JTextField(proyecto.getFechaFin().toString());

        jButtonModificar = new JButton("Modificar");
        jButtonSalir = new JButton("Atrás");
        jPanelBotones = new JPanel();

        panelProyecto.add(jLabelTitulo);
        panelProyecto.add(jTextFieldTitulo);
        panelProyecto.add(jLabelDescripcion);
        panelProyecto.add(jTextFieldDescripcion);
        panelProyecto.add(jLabelFechaInicio);
        panelProyecto.add(jTextFieldFechaInicio);
        panelProyecto.add(jLabelFechaFin);
        panelProyecto.add(jTextFieldFechaFin);

        setLayout(new BorderLayout());
        add(panelProyecto, BorderLayout.CENTER);

        jPanelBotones.add(jButtonModificar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    proyecto.setTitulo(jTextFieldTitulo.getText());
                    proyecto.setDescripcion(jTextFieldDescripcion.getText());
                    proyecto.setFechaInicio(LocalDate.parse(jTextFieldFechaInicio.getText()));
                    proyecto.setFechaFin(LocalDate.parse(jTextFieldFechaFin.getText()));
                    proyectoService.modificar(proyecto);
                    JOptionPane.showMessageDialog(null, "El proyecto se modificó exitosamente");
                } catch (ServiceException ex) {
                    JOptionPane.showMessageDialog(null, "Error al modificar el proyecto");
                } catch (IllegalArgumentException ex){
                    JOptionPane.showMessageDialog(null,"Error. Se deben llenar todos los campos");
                } catch(DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(null, "El formato de fecha ingresado no es válido");
                }
                jTextFieldTitulo.setText("");
                jTextFieldDescripcion.setText("");
                jTextFieldFechaInicio.setText("");
                jTextFieldFechaFin.setText("");
            }
        });
        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelProyecto1 panelProyecto1 = new PanelProyecto1(panel);
                panelProyecto1.menuProyectos();
                panel.mostrar(panelProyecto1);
            }
        });
    }
}
