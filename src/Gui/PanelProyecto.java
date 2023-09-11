package Gui;

import Controlador.Empleado;
import Controlador.Proyecto;
import Controlador.Tarea;
import Service.EmpleadoService;
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
import java.util.List;

public class PanelProyecto extends JPanel{
    ProyectoService proyectoService;
    PanelManager panel;
    JPanel formularioProyecto;
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

    JButton jButtonOpcionGuardar;
    JButton jButtonOpcionBuscar;
    JButton jButtonOpcionEliminar;
    JButton jButtonOpcionModificar;
    JButton jButtonAtras = new JButton("Atras");
    JButton jButtonSalir = new JButton("Salir");

    JButton jButtonGuardar = new JButton("Guardar");
    JButton jButtonBuscar = new JButton("Buscar");
    JButton jButtonEliminar = new JButton("Eliminar");
    JButton jButtonModificar = new JButton("Modificar");

    JButton jButtonTareas = new JButton("Tareas");
    JButton jButtonAsignarTarea = new JButton("Asignar Tarea");

    JButton jButtonReportes = new JButton("Reportes");
    JButton jButtonCostoHoras = new JButton("Calcular Costo en Horas");
    JButton jButtonCostoDinero = new JButton("Calcular Costo en Dinero");

    JButton jButtonMostrarProyectos;
    JButton jButtonDesasignar = new JButton("Desasignar tarea");

    JPanel jPanelBotones;

    public PanelProyecto(PanelManager panel){
        this.panel = panel;
        mostrarOpciones();
    }

    public void mostrarOpciones(){
        formularioProyecto = new JPanel();
        formularioProyecto.setLayout(new GridLayout(1,0));

        jButtonOpcionGuardar = new JButton("Guardar un nuevo proyecto");
        jButtonOpcionBuscar = new JButton("Buscar un proyecto");
        jButtonOpcionEliminar = new JButton("Eliminar un proyecto");
        jButtonOpcionModificar = new JButton("Modificar un proyecto");
        jButtonMostrarProyectos = new JButton("Mostrar todos los proyectos");
        jPanelBotones = new JPanel();
        jPanelBotones.setLayout(new BoxLayout(jPanelBotones, BoxLayout.Y_AXIS));

        setLayout(new BorderLayout());
        add(formularioProyecto, BorderLayout.CENTER);

        jPanelBotones.add(jButtonOpcionGuardar);
        jPanelBotones.add(jButtonOpcionBuscar);
        jPanelBotones.add(jButtonOpcionEliminar);
        jPanelBotones.add(jButtonOpcionModificar);
        jPanelBotones.add(jButtonReportes);
        jPanelBotones.add(jButtonMostrarProyectos);
        jPanelBotones.add(jButtonTareas);
        jPanelBotones.add(jButtonAsignarTarea);
        jPanelBotones.add(jButtonDesasignar);
        jPanelBotones.add(jButtonAtras);
        add(jPanelBotones, BorderLayout.CENTER);

        jButtonOpcionGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelProyecto panelProyecto = new PanelProyecto(panel);
                panelProyecto.formularioGuardar();
                panel.mostrar(panelProyecto);
            }
        });

        jButtonOpcionBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelProyecto panelProyecto = new PanelProyecto(panel);
                panelProyecto.formularioBuscar();
                panel.mostrar(panelProyecto);
            }
        });

        jButtonOpcionEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelProyecto panelProyecto = new PanelProyecto(panel);
                panelProyecto.formularioEliminar();
                panel.mostrar(panelProyecto);
            }
        });

        jButtonOpcionModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelProyecto panelProyecto = new PanelProyecto(panel);
                panelProyecto.formularioModificar();
                panel.mostrar(panelProyecto);
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

        jButtonTareas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTarea panelTarea1 = new PanelTarea(panel);
                panelTarea1.mostrarOpciones();
                panel.mostrar(panelTarea1);
            }
        });

        jButtonAsignarTarea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelProyecto panelProyecto = new PanelProyecto(panel);
                panelProyecto.panelAsignarTareas();
                panel.mostrar(panelProyecto);
            }
        });

        jButtonMostrarProyectos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelProyecto panelProyecto = new PanelProyecto(panel);
                panelProyecto.mostrarTodos();
                panel.mostrar(panelProyecto);
            }
        });

        jButtonReportes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelProyecto panelProyecto = new PanelProyecto(panel);
                panelProyecto.reporte();
                panel.mostrar(panelProyecto);
            }
        });

        jButtonDesasignar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelProyecto panelProyecto = new PanelProyecto(panel);
                panelProyecto.panelDesasignar();
                panel.mostrar(panelProyecto);
            }
        });
    }

    public void formularioGuardar(){
        proyectoService = new ProyectoService();
        formularioProyecto = new JPanel();
        formularioProyecto.setLayout(new GridLayout(6, 2));

        jPanelBotones = new JPanel();

        formularioProyecto.add(jLabelTitulo);
        formularioProyecto.add(jTextFieldTitulo);

        formularioProyecto.add(jLabelDescripcion);
        formularioProyecto.add(jTextFieldDescripcion);

        formularioProyecto.add(jLabelFechaInicio);
        formularioProyecto.add(jTextFieldFechaInicio);

        formularioProyecto.add(jLabelFechaFin);
        formularioProyecto.add(jTextFieldFechaFin);

        setLayout(new BorderLayout());
        add(formularioProyecto, BorderLayout.CENTER);

        jPanelBotones.add(jButtonGuardar);
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
                PanelProyecto panelProyecto = new PanelProyecto(panel);
                panelProyecto.mostrarOpciones();
                panel.mostrar(panelProyecto);
            }
        });
    }

    public void guardarProyecto() {
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

    public void formularioBuscar(){
        proyectoService = new ProyectoService();
        formularioProyecto = new JPanel();
        formularioProyecto.setLayout(new GridLayout(6, 2));

        jPanelBotones = new JPanel();

        jLabelId = new JLabel("ID del proyecto: ");

        formularioProyecto.add(jLabelId);
        formularioProyecto.add(jTextFieldId);

        setLayout(new BorderLayout());
        add(formularioProyecto, BorderLayout.CENTER);

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
                PanelProyecto panelProyecto = new PanelProyecto(panel);
                panelProyecto.mostrarOpciones();
                panel.mostrar(panelProyecto);
            }
        });
    }

    public void buscarProyecto() {
        try {
            int idProyecto = Integer.parseInt((jTextFieldId.getText()));
            Proyecto proyecto = proyectoService.buscarProyecto(idProyecto);
            if (proyecto != null) {
                JOptionPane.showMessageDialog(null, "Proyecto encontrado:\n" +
                        "ID: " + proyecto.getId() + "\n" +
                        "Título: " + proyecto.getTitulo() + "\n" +
                        "Descripción: " + proyecto.getDescripcion() + "\n" +
                        "Fecha Inicio: " + proyecto.getFechaInicio() + "\n" +
                        "Fecha Fin: " + proyecto.getFechaFin());
            } else {
                JOptionPane.showMessageDialog(null, "No hay ningun proyecto con ese ID");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID de proyecto válido");
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar el proyecto");
        }
    }

    public void formularioEliminar(){
        proyectoService = new ProyectoService();
        formularioProyecto = new JPanel();
        formularioProyecto.setLayout(new GridLayout(6, 2));

        jPanelBotones = new JPanel();

        jLabelId = new JLabel("ID del proyecto: ");

        formularioProyecto.add(jLabelId);
        formularioProyecto.add(jTextFieldId);

        setLayout(new BorderLayout());
        add(formularioProyecto, BorderLayout.CENTER);

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
                PanelProyecto panelProyecto = new PanelProyecto(panel);
                panelProyecto.mostrarOpciones();
                panel.mostrar(panelProyecto);
            }
        });
    }

    public void eliminarProyecto() {
        try {
            int idProyecto = Integer.parseInt(jTextFieldId.getText());
            Proyecto proyecto = proyectoService.buscarProyecto(idProyecto);
            if (proyecto != null) {
                JOptionPane.showMessageDialog(null, "Proyecto encontrado:\n" +
                        "ID: " + proyecto.getId() + "\n" +
                        "Título: " + proyecto.getTitulo() + "\n" +
                        "Descripción: " + proyecto.getDescripcion() + "\n" +
                        "Fecha Inicio: " + proyecto.getFechaInicio() + "\n" +
                        "Fecha Fin: " + proyecto.getFechaFin());
                int confirmacion = JOptionPane.showConfirmDialog(null, "¿Seguro que quiere eliminar este proyecto?");
                if (confirmacion == JOptionPane.YES_OPTION) {
                    try {
                        proyectoService.eliminarProyecto(idProyecto);
                        JOptionPane.showMessageDialog(null, "El proyecto se eliminó exitosamente");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Ingrese un ID de proyecto válido");
                    } catch (ServiceException serEx) {
                        JOptionPane.showMessageDialog(null, "No se pudo eliminar el proyecto");
                    }
                }
            }else {
                JOptionPane.showMessageDialog(null, "No se encontró ningun proyecto con ese ID");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID de proyecto válido");
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar el proyecto");
        }
    }

    public void formularioModificar(){
        proyectoService = new ProyectoService();
        formularioProyecto = new JPanel();
        formularioProyecto.setLayout(new GridLayout(6, 2));

        jPanelBotones = new JPanel();

        jLabelId = new JLabel("ID del proyecto: ");

        formularioProyecto.add(jLabelId);
        formularioProyecto.add(jTextFieldId);

        setLayout(new BorderLayout());
        add(formularioProyecto, BorderLayout.CENTER);

        jPanelBotones.add(jButtonModificar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int idProyecto = Integer.parseInt(jTextFieldId.getText());
                    Proyecto proyecto = proyectoService.buscarProyecto(idProyecto);
                    if(proyecto != null){
                        modificarProyecto(proyecto);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró ningun proyecto con ese ID");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Ingrese un ID de proyecto válido");
                } catch (ServiceException ex) {
                    JOptionPane.showMessageDialog(null, "Error al buscar el proyecto");
                }
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelProyecto panelProyecto = new PanelProyecto(panel);
                panelProyecto.mostrarOpciones();
                panel.mostrar(panelProyecto);
            }
        });
    }

    public void modificarProyecto(Proyecto proyecto){
        proyectoService = new ProyectoService();
        formularioProyecto = new JPanel();
        formularioProyecto.setLayout(new GridLayout(6, 2));

        jLabelTitulo = new JLabel("Titulo: ");
        jLabelDescripcion = new JLabel("Descripción:");
        jLabelFechaInicio = new JLabel("Fecha de Inicio (YYYY-MM-DD):");
        jLabelFechaFin = new JLabel("Fecha de Fin (YYYY-MM-DD):");

        jTextFieldTitulo = new JTextField(proyecto.getTitulo());
        jTextFieldDescripcion = new JTextField(proyecto.getDescripcion());
        jTextFieldFechaInicio = new JTextField(proyecto.getFechaInicio().toString());
        jTextFieldFechaFin = new JTextField(proyecto.getFechaFin().toString());

        jButtonModificar = new JButton();
        jPanelBotones = new JPanel();

        formularioProyecto.add(jLabelTitulo);
        formularioProyecto.add(jTextFieldTitulo);

        formularioProyecto.add(jLabelDescripcion);
        formularioProyecto.add(jTextFieldDescripcion);

        formularioProyecto.add(jLabelFechaInicio);
        formularioProyecto.add(jTextFieldFechaInicio);

        formularioProyecto.add(jLabelFechaFin);
        formularioProyecto.add(jTextFieldFechaFin);

        setLayout(new BorderLayout());
        add(formularioProyecto, BorderLayout.CENTER);

        jPanelBotones.add(jButtonModificar);
        add(jPanelBotones, BorderLayout.SOUTH);

        int opcion = JOptionPane.showOptionDialog(null, formularioProyecto, "Modificar Proyecto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
        if(opcion == JOptionPane.OK_OPTION){
            proyecto.setTitulo(jTextFieldTitulo.getText());
            proyecto.setDescripcion(jTextFieldDescripcion.getText());
            proyecto.setFechaInicio(LocalDate.parse(jTextFieldFechaInicio.getText()));
            proyecto.setFechaFin(LocalDate.parse(jTextFieldFechaFin.getText()));
            try {
                proyectoService.modificar(proyecto);
                JOptionPane.showMessageDialog(null, "El proyecto se modificó exitosamente");
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(null, "Error al modificar el proyecto");
            }
        }
    }

    JComboBox<Proyecto> jComboBoxProyectos;
    JLabel jLabelTareas;
    JList<Tarea> jListTareas;
    ArrayList<Tarea> tareasAsignadas;
    JButton jButtonLimpiar;
    JButton jButtonAsignar;
    JComboBox<Tarea> jComboBoxTareas;


    public void panelAsignarTareas() {
        proyectoService = new ProyectoService();
        TareaService tareaService = new TareaService();
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel jLabelProyecto = new JLabel("Seleccionar Proyecto:");
        jComboBoxProyectos = new JComboBox<>();
        jComboBoxTareas = new JComboBox<>();
        tareasAsignadas = new ArrayList<>();

        try {
            ArrayList<Proyecto> proyectos = proyectoService.obtenerProyectos();
            for (Proyecto proyecto : proyectos) {
                jComboBoxProyectos.addItem(proyecto);
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener los proyectos: " + e.getMessage());
        }

        try{
            ArrayList<Tarea> tareas = tareaService.obtenerTareas();
            for (Tarea tarea : tareas){
                jComboBoxTareas.addItem(tarea);
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener las tareas: " + e.getMessage());
        }

        topPanel.add(jLabelProyecto);
        topPanel.add(jComboBoxProyectos);

        JPanel centerPanel = new JPanel(new BorderLayout());
        jLabelTareas = new JLabel("Tareas:");
        centerPanel.add(jLabelTareas, BorderLayout.NORTH);
        centerPanel.add(jComboBoxTareas, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        jButtonAsignar = new JButton("Asignar");
        jButtonLimpiar = new JButton("Limpiar");
        bottomPanel.add(jButtonAsignar);
        bottomPanel.add(jButtonLimpiar);
        bottomPanel.add(jButtonSalir);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        jButtonAsignar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                asignarTareas();
            }
        });

        jButtonLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarTareas();
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelProyecto panelProyecto = new PanelProyecto(panel);
                panelProyecto.mostrarOpciones();
                panel.mostrar(panelProyecto);
            }
        });
    }

    public void asignarTareas(){
        Proyecto proyectoSeleccionado = (Proyecto) jComboBoxProyectos.getSelectedItem();
        Tarea tareaSeleccionada = (Tarea) jComboBoxTareas.getSelectedItem();

        if (proyectoSeleccionado != null && tareaSeleccionada != null) {
            try {
                proyectoService.asignarTareaProyecto(tareaSeleccionada, proyectoSeleccionado);
                JOptionPane.showMessageDialog(this, "Tareas asignadas al proyecto.");
                limpiarTareas();
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(null, "Error al asignar la tarea: " + ex.getMessage());
                limpiarTareas();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proyecto y al menos una tarea.");
            limpiarTareas();
        }
    }

    private void limpiarTareas() {
        jComboBoxProyectos.setSelectedIndex(-1);
        jComboBoxTareas.setSelectedIndex(-1);
    }

    public void panelDesasignar(){
        proyectoService = new ProyectoService();
        TareaService tareaService = new TareaService();

        formularioProyecto = new JPanel();
        formularioProyecto.setLayout(new GridLayout(2,2));

        jPanelBotones = new JPanel();
        jLabelTareas = new JLabel("Seleccionar Tarea:");
        jComboBoxTareas = new JComboBox<>();
        tareasAsignadas = new ArrayList<>();

        try {
            tareasAsignadas = tareaService.obtenerTareasConProyectos();
            for (Tarea tarea : tareasAsignadas){
                jComboBoxTareas.addItem(tarea);
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener las tareas" + e.getMessage());
        }

        formularioProyecto.add(jLabelTareas);
        formularioProyecto.add(jComboBoxTareas);

        setLayout(new BorderLayout());
        add(formularioProyecto, BorderLayout.CENTER);

        jPanelBotones.add(jButtonDesasignar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonDesasignar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desasignarTarea();
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelProyecto panelProyecto = new PanelProyecto(panel);
                panelProyecto.mostrarOpciones();
                panel.mostrar(panelProyecto);
            }
        });
    }

    public void desasignarTarea(){
        Tarea tareaSeleccionada = (Tarea) jComboBoxTareas.getSelectedItem();

        if (tareaSeleccionada != null){
            try {
                Proyecto proyecto = proyectoService.buscarProyecto(tareaSeleccionada.getIdProyecto());
                int confirmacion = JOptionPane.showConfirmDialog(null, "Seguro que quiere desasignar esta tarea de este proyecto?\n" +
                        "ID proyecto: " + proyecto.getId() + "\n" +
                        "Titulo: " + proyecto.getTitulo() + "\n" +
                        "Descripcion: " + proyecto.getDescripcion() + "\n" +
                        "Fecha de inicio: " + proyecto.getFechaInicio());
                if(confirmacion == JOptionPane.YES_OPTION) {
                    try {
                        proyectoService.desasignarTarea(tareaSeleccionada, proyecto);
                        JOptionPane.showMessageDialog(this, "Tareas desasignada del proyecto.");
                        limpiar();
                    } catch (ServiceException ex) {
                        JOptionPane.showMessageDialog(null, "Error al desasignar la tarea: " + ex.getMessage());
                        limpiar();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Seleccione un proyecto y al menos una tarea.");
                    limpiar();
                }
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(null, "Error al buscar el proyecto" + ex.getMessage());
            }
        }
    }

    public void limpiar(){
        jComboBoxTareas.setSelectedIndex(-1);
    }

    public void mostrarTodos(){
        proyectoService = new ProyectoService();
        TareaService tareaService = new TareaService();
        try {
            ArrayList<Proyecto> proyectos = proyectoService.obtenerProyectos();
            StringBuilder proyectosInfo = new StringBuilder("Lista de Proyectos:\n\n");

            for (Proyecto proyecto : proyectos) {
                proyectosInfo.append("ID: ").append(proyecto.getId()).append("\n");
                proyectosInfo.append("Título: ").append(proyecto.getTitulo()).append("\n");
                proyectosInfo.append("Descripción: ").append(proyecto.getDescripcion()).append("\n");
                proyectosInfo.append("Fecha Inicio: ").append(proyecto.getFechaInicio()).append("\n");
                proyectosInfo.append("Fecha Fin: ").append(proyecto.getFechaFin()).append("\n");

                int idProyecto = proyecto.getId(); // Obtener el ID del proyecto actual
                ArrayList<Tarea> tareasAsignadas = tareaService.obtenerTareasAsignadas(idProyecto);

                if (!tareasAsignadas.isEmpty()) {
                    proyectosInfo.append("Tareas Asignadas:\n");
                    for (Tarea tarea : tareasAsignadas) {
                        proyectosInfo.append("- ").append(tarea.getTitulo()).append("\n");
                    }
                } else {
                    proyectosInfo.append("No hay tareas asignadas a este proyecto.\n");
                }

                proyectosInfo.append("\n");
            }

            JTextArea textArea = new JTextArea(20, 50);
            textArea.setEditable(false);
            textArea.setText(proyectosInfo.toString());

            JScrollPane scrollPane = new JScrollPane(textArea);

            JOptionPane.showMessageDialog(null, scrollPane, "Proyectos", JOptionPane.INFORMATION_MESSAGE);
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener la lista de proyectos: " + e.getMessage());
        }
    }

    public void reporte(){
        proyectoService = new ProyectoService();
        formularioProyecto = new JPanel();
        formularioProyecto.setLayout(new GridLayout(6,6));

        jPanelBotones = new JPanel();

        JLabel jLabelProyecto = new JLabel("Seleccionar Proyecto:");
        jComboBoxProyectos = new JComboBox<>();

        try{
            ArrayList<Proyecto> proyectos = proyectoService.obtenerProyectos();
            for (Proyecto proyecto:proyectos){
                jComboBoxProyectos.addItem(proyecto);
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener los proyectos: " + e.getMessage());
        }

        formularioProyecto.add(jLabelProyecto);
        formularioProyecto.add(jComboBoxProyectos);
        formularioProyecto.add(jButtonCostoHoras);
        formularioProyecto.add(jButtonCostoDinero);

        setLayout(new BorderLayout());
        add(formularioProyecto, BorderLayout.CENTER);

        jPanelBotones.add(jButtonCostoHoras);
        jPanelBotones.add(jButtonCostoDinero);
        jButtonAtras = new JButton("Atras");
        jPanelBotones.add(jButtonAtras);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonCostoHoras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularCostoHoras();
            }
        });

        jButtonCostoDinero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularCostoDinero();
            }
        });

        jButtonAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelProyecto panelProyecto = new PanelProyecto(panel);
                panelProyecto.mostrarOpciones();
                panel.mostrar(panelProyecto);
            }
        });
    }

    public void calcularCostoHoras(){
        proyectoService = new ProyectoService();
        Proyecto proyectoSeleccionado = (Proyecto) jComboBoxProyectos.getSelectedItem();
        if (proyectoSeleccionado != null) {
            try {
                double total = proyectoService.calcularCostoHoras(proyectoSeleccionado.getId());
                JOptionPane.showMessageDialog(null, "El costo total en horas del proyecto es: " + total);
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(null, "Error al calcular el costo en horas del proyecto: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un proyecto.");
        }
    }

    public void calcularCostoDinero(){
        proyectoService = new ProyectoService();
        Proyecto proyectoSeleccionado = (Proyecto) jComboBoxProyectos.getSelectedItem();
        if(proyectoSeleccionado != null) {
            try{
                double total = proyectoService.calcularCostoDinero(proyectoSeleccionado.getId());
                JOptionPane.showMessageDialog(null, "El costo total del proyecto es: " + total);
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(null, "Error al calcular el costo del proyecto: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un proyecto");
        }
    }

}
