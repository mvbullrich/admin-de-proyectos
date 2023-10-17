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
    JLabel jLabel = new JLabel("Seleccione un proyecto: ");
    JLabel jLabelTitulo = new JLabel("Titulo: ");
    JLabel jLabelDescripcion = new JLabel("Descripción:");
    JLabel jLabelFechaInicio = new JLabel("Fecha de Inicio (YYYY-MM-DD):");
    JLabel jLabelFechaFin = new JLabel("Fecha de Fin (YYYY-MM-DD):");
    JLabel jLabelId = new JLabel("ID: ");
    JTextField jTextFieldId = new JTextField();

    JTextField jTextFieldTitulo = new JTextField(30);
    JTextField jTextFieldDescripcion = new JTextField(50);
    JTextField jTextFieldFechaInicio = new JTextField(10);
    JTextField jTextFieldFechaFin = new JTextField(10);
    JComboBox<Proyecto> jComboBoxProyecto;
    JComboBox<Tarea> jComboBoxTareas = new JComboBox<>();

    JButton jButtonGuardar;
    JButton jButtonSalir = new JButton("Salir");
    JButton jButtonMisProyectos = new JButton("Mis proyectos");
    JButton jButtonEliminar = new JButton("Eliminar");
    JButton jButtonModificar = new JButton("Modificar");
    JButton jButtonMostrar = new JButton("Ver tareas asignadas");
    JButton jButtonCostoHoras = new JButton("Calcular costo horas");
    JButton jButtonCostoDinero = new JButton("Calcular costo dinero");
    JButton jButtonTareas = new JButton("Ir a tareas");
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

        jPanelBotones.add(jButtonMisProyectos);
        jPanelBotones.add(jButtonGuardar);
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

            String fechaInicioText = jTextFieldFechaInicio.getText();
            if(!fechaInicioText.isEmpty()){
                proyecto.setFechaInicio(LocalDate.parse(fechaInicioText));
            } else {
                throw new IllegalArgumentException("La fecha de inicio es obligatoria.");
            }
            String fechaFinText = jTextFieldFechaFin.getText();
            if (!fechaFinText.isEmpty()){
                proyecto.setFechaFin(LocalDate.parse(fechaFinText));
                if (proyecto.getFechaInicio().isAfter(proyecto.getFechaFin())){
                    JOptionPane.showMessageDialog(null, "La fecha de inicio no puede ser posterior a la fecha de fin.");
                    return;
                }
            } else {
                throw new IllegalArgumentException("La fecha de fin es obligatoria");
            }
            if (proyecto.getTitulo().isEmpty()){
                throw new IllegalArgumentException("El titulo es obligatorio.");
            } else if (proyecto.getDescripcion().isEmpty()) {
                proyecto.setDescripcion("");
            } else{
                proyectoService.guardarProyecto(proyecto);
                JOptionPane.showMessageDialog(null, "Proyecto guardado exitosamente");
            }
        } catch (ServiceException serEx) {
            serEx.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se pudo guardar");
        } catch (IllegalArgumentException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage());
        } catch(DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "El formato de fecha ingresado no es válido");
        }
    }

    private void actualizarCamposDeTexto() {
        Proyecto proyectoSeleccionado = (Proyecto) jComboBoxProyecto.getSelectedItem();
        if (proyectoSeleccionado != null) {
            jTextFieldId.setText(String.valueOf(proyectoSeleccionado.getId()));
            jTextFieldId.setEditable(false);
            jTextFieldTitulo.setText(proyectoSeleccionado.getTitulo());
            jTextFieldDescripcion.setText(proyectoSeleccionado.getDescripcion());
            jTextFieldFechaInicio.setText(proyectoSeleccionado.getFechaInicio().toString());
            jTextFieldFechaFin.setText(proyectoSeleccionado.getFechaFin().toString());
            jComboBoxTareas.removeAllItems();
        } else {
            jTextFieldTitulo.setText("");
            jTextFieldDescripcion.setText("");
            jTextFieldFechaInicio.setText("");
            jTextFieldFechaFin.setText("");
        }
    }

    public void menuProyectos(){
        proyectoService = new ProyectoService();
        tareaService = new TareaService();
        panelProyecto = new JPanel();
        panelProyecto.setLayout(new GridLayout(7,2));
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
        actualizarCamposDeTexto();
        panelProyecto.add(jLabel);
        panelProyecto.add(jComboBoxProyecto);
        panelProyecto.add(jLabelId);
        panelProyecto.add(jTextFieldId);
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

        jPanelBotones.add(jButtonEliminar);
        jPanelBotones.add(jButtonModificar);
        jPanelBotones.add(jButtonMostrar);
        jPanelBotones.add(jButtonTareas);
        jPanelBotones.add(jButtonCostoHoras);
        jPanelBotones.add(jButtonCostoDinero);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jComboBoxProyecto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCamposDeTexto();
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
                            jComboBoxProyecto.removeItem(proyectoSeleccionado);
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
                if (proyectoSeleccionado != null) {
                    try {
                        proyectoSeleccionado.setTitulo(jTextFieldTitulo.getText());
                        proyectoSeleccionado.setDescripcion(jTextFieldDescripcion.getText());
                        String fechaInicioText = jTextFieldFechaInicio.getText();
                        if (!fechaInicioText.isEmpty()) {
                            proyectoSeleccionado.setFechaInicio(LocalDate.parse(fechaInicioText));
                        } else {
                            throw new IllegalArgumentException("La fecha de inicio es obligatoria.");
                        }
                        String fechaFinText = jTextFieldFechaFin.getText();
                        if (!fechaFinText.isEmpty()) {
                            proyectoSeleccionado.setFechaFin(LocalDate.parse(fechaFinText));
                            if (proyectoSeleccionado.getFechaInicio().isAfter(proyectoSeleccionado.getFechaFin())) {
                                JOptionPane.showMessageDialog(null, "La fecha de inicio no puede ser posterior a la fecha de fin");
                                return;
                            }
                        } else {
                            throw new IllegalArgumentException("La fecha de fin es obligatoria.");
                        }
                        if (proyectoSeleccionado.getTitulo().isEmpty()) {
                            throw new IllegalArgumentException("El título es obligatorio.");
                        } else if (proyectoSeleccionado.getDescripcion().isEmpty()) {
                            proyectoSeleccionado.setDescripcion("");
                        }
                        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres modificar el proyecto?");
                        if (confirmacion == JOptionPane.YES_OPTION) {
                            proyectoService.modificar(proyectoSeleccionado);
                            JOptionPane.showMessageDialog(null, "Proyecto modificado exitosamente");
                        }
                    } catch (ServiceException serEx) {
                        serEx.printStackTrace();
                        JOptionPane.showMessageDialog(null, "No se pudo guardar");
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(null, "El formato de fecha ingresado no es válido");
                    }
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

        jButtonTareas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTarea panelTarea1 = new PanelTarea(panel);
                panelTarea1.menuTareas();
                panel.mostrar(panelTarea1);
            }
        });
    }
}
