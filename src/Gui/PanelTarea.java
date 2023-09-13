package Gui;

import Controlador.Empleado;
import Controlador.Tarea;
import Service.EmpleadoService;
import Service.ServiceException;
import Service.TareaService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class PanelTarea extends JPanel {
    TareaService tareaService;
    PanelManager panel;
    JPanel panelTarea;

    JLabel jLabelId = new JLabel("ID: ");
    JLabel jlabelTitulo = new JLabel("Título:");
    JLabel jLabelDescripcion = new JLabel("Descripción:");
    JLabel jLabelEstimacion = new JLabel("Estimación:");
    JLabel jLabelHorasReales = new JLabel("Horas Reales:");
    JLabel jLabelEmpleado = new JLabel("Empleado asignado: ");

    JTextField jTextFieldId = new JTextField(10);
    JTextField jTextFieldTitulo = new JTextField(20);
    JTextField jTextFieldDescripcion = new JTextField(50);
    JTextField jTextFieldEstimacion = new JTextField(10);
    JTextField jTextFieldHorasReales = new JTextField(10);
    JTextField jTextFieldEmpleado = new JTextField(50);

    JButton jButtonOpcionGuardar;
    JButton jButtonOpcionBuscar;
    JButton jButtonOpcionEliminar;
    JButton jButtonOpcionModificar;
    JButton jButtonOpcionAsignar;
    JButton jButtonAtras = new JButton("Atras");
    JButton jButtonSalir = new JButton("Salir");

    JButton jButtonGuardar = new JButton("Guardar");
    JButton jButtonBuscar = new JButton("Buscar");
    JButton jButtonEliminar = new JButton("Eliminar");
    JButton jButtonModificar = new JButton("Modificar");
    JButton jButtonAsignar = new JButton("Asignar");
    JButton jButtonEstados;
    JButton jButtonMostrar;
    JButton jButtonDesasignar = new JButton("Desasignar empleado");

    JPanel jPanelBotones;

    public PanelTarea(PanelManager panel) {
        this.panel = panel;
        mostrarOpciones();
    }

    public void mostrarOpciones() {
        panelTarea = new JPanel();
        panelTarea.setLayout(new GridLayout(0, 1));

        jButtonOpcionGuardar = new JButton("Guardar una nueva tarea");
        jButtonOpcionBuscar = new JButton("Buscar una tarea");
        jButtonOpcionEliminar = new JButton("Eliminar una tarea");
        jButtonOpcionModificar = new JButton("Modificar una tarea");
        jButtonOpcionAsignar = new JButton("Asignar empleado a una tarea");
        jButtonEstados = new JButton("Actualizar estados tarea");
        jButtonMostrar = new JButton("Mostrar todas");
        jPanelBotones = new JPanel();
        jPanelBotones.setLayout(new BoxLayout(jPanelBotones, BoxLayout.Y_AXIS));

        setLayout(new BorderLayout());
        add(panelTarea, BorderLayout.CENTER);

        jPanelBotones.add(jButtonOpcionGuardar);
        jPanelBotones.add(jButtonOpcionBuscar);
        jPanelBotones.add(jButtonOpcionEliminar);
        jPanelBotones.add(jButtonOpcionModificar);
        jPanelBotones.add(jButtonMostrar);
        jPanelBotones.add(jButtonOpcionAsignar);
        jPanelBotones.add(jButtonDesasignar);
        jPanelBotones.add(jButtonEstados);
        jPanelBotones.add(jButtonAtras);
        add(jPanelBotones, BorderLayout.CENTER);

        jButtonOpcionGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTarea panelTarea = new PanelTarea(panel);
                panelTarea.formularioGuardar();
                panel.mostrar(panelTarea);
            }
        });

        jButtonOpcionEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTarea panelTarea1 = new PanelTarea(panel);
                panelTarea1.formularioEliminar();
                panel.mostrar(panelTarea1);
            }
        });

        jButtonOpcionModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTarea panelTarea1 = new PanelTarea(panel);
                panelTarea1.formularioModificar();
                panel.mostrar(panelTarea1);
            }
        });

        jButtonOpcionBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTarea panelTarea1 = new PanelTarea(panel);
                panelTarea1.formularioBuscar();
                panel.mostrar(panelTarea1);
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

        jButtonOpcionAsignar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTarea panelTarea1 = new PanelTarea(panel);
                panelTarea1.formularioAsignarEmpleado();
                panel.mostrar(panelTarea1);
            }
        });

        jButtonEstados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelHistorialEstado panelHistorialEstado = new PanelHistorialEstado(panel);
                panelHistorialEstado.opciones();
                panel.mostrar(panelHistorialEstado);
            }
        });

        jButtonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTarea panelTarea1 = new PanelTarea(panel);
                panelTarea1.mostrarTodas();
                panel.mostrar(panelTarea1);
            }
        });

        jButtonDesasignar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTarea panelTarea1 = new PanelTarea(panel);
                panelTarea1.panelDesasignar();
                panel.mostrar(panelTarea1);
            }
        });
    }

    public void formularioGuardar() {
        tareaService = new TareaService();
        panelTarea = new JPanel();
        panelTarea.setLayout(new GridLayout(4, 2));

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
                guardarTarea();
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTarea panelTarea = new PanelTarea(panel);
                panelTarea.mostrarOpciones();
                panel.mostrar(panelTarea);
            }
        });
    }

    public void guardarTarea() {
        Tarea tarea = new Tarea();
        try {
            tarea.setTitulo(jTextFieldTitulo.getText());
            tarea.setDescripcion(jTextFieldDescripcion.getText());
            tarea.setEstimacion(Integer.parseInt(jTextFieldEstimacion.getText()));
            tarea.setHorasReales(Integer.parseInt((jTextFieldHorasReales.getText())));
            tarea.setEmpleado_id(0);
            if (tarea.getTitulo().isEmpty() || tarea.getDescripcion().isEmpty()) {
                throw new IllegalArgumentException();
            } else {
                tareaService.guardarTarea(tarea);
                JOptionPane.showMessageDialog(null, "Tarea guardada exitosamente");
            }
        } catch (ServiceException serEx) {
            JOptionPane.showMessageDialog(null, "No se pudo guardar la tarea");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, "Error. Se deben llenar los campos con valores correctos");
        }
    }

    public void formularioEliminar() {
        tareaService = new TareaService();
        panelTarea = new JPanel();
        panelTarea.setLayout(new GridLayout(1, 1));

        jPanelBotones = new JPanel();

        jLabelId = new JLabel("ID de la tarea: ");

        panelTarea.add(jLabelId);
        panelTarea.add(jTextFieldId);

        setLayout(new BorderLayout());
        add(panelTarea, BorderLayout.CENTER);

        jPanelBotones.add(jButtonEliminar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarTarea();
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTarea panelTarea1 = new PanelTarea(panel);
                panelTarea1.mostrarOpciones();
                panel.mostrar(panelTarea1);
            }
        });
    }

    public void eliminarTarea() {
        try {
            int idTarea = Integer.parseInt(jTextFieldId.getText());
            Tarea tarea = tareaService.buscarTarea(idTarea);
            if (tarea != null) {
                JOptionPane.showMessageDialog(null, "Tarea encontrada:\n" +
                        "ID: " + idTarea + "\n" +
                        "Título: " + tarea.getDescripcion() + "\n" +
                        "Descripción: " + tarea.getDescripcion() + "\n" +
                        "Estimacion: " + tarea.getEstimacion());
                int confirmacion = JOptionPane.showConfirmDialog(null, "Seguro que quiere eliminar esta tarea?");
                if (confirmacion == JOptionPane.YES_OPTION) {
                    try {
                        tareaService.eliminarTarea(idTarea);
                        JOptionPane.showMessageDialog(null, "Tarea eliminada exitosamente");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Ingrese un ID de tarea válido");
                    } catch (ServiceException serEx) {
                        JOptionPane.showMessageDialog(null, "No se pudo eliminar la tarea");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ninguna tarea con ese ID");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID de tarea válido");
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar la tarea");
        }
    }

    public void formularioModificar() {
        tareaService = new TareaService();
        panelTarea = new JPanel();
        panelTarea.setLayout(new GridLayout(6, 2));

        jButtonModificar = new JButton("Modificar");
        jPanelBotones = new JPanel();

        jLabelId = new JLabel("ID de la tarea:");

        panelTarea.add(jLabelId);
        panelTarea.add(jTextFieldId);

        setLayout(new BorderLayout());
        add(panelTarea, BorderLayout.CENTER);

        jPanelBotones.add(jButtonModificar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int idTarea = Integer.parseInt(jTextFieldId.getText());
                    Tarea tarea = tareaService.buscarTarea(idTarea);
                    if (tarea != null) {
                        modificarTarea(tarea);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró ninguna tarea con ese ID");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Ingrese un ID de tarea válido");
                } catch (ServiceException ex) {
                    JOptionPane.showMessageDialog(null, "Error al buscar la tarea");
                }
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTarea panelTarea1 = new PanelTarea(panel);
                panelTarea1.mostrarOpciones();
                panel.mostrar(panelTarea1);
            }
        });
    }

    public void modificarTarea(Tarea tarea) {
        tareaService = new TareaService();
        panelTarea = new JPanel();
        panelTarea.setLayout(new GridLayout(6, 2));

        jTextFieldTitulo = new JTextField(tarea.getTitulo());
        jTextFieldDescripcion = new JTextField(tarea.getDescripcion());
        jTextFieldEstimacion = new JTextField(String.valueOf(tarea.getEstimacion()));
        jTextFieldHorasReales = new JTextField(String.valueOf(tarea.getHorasReales()));

        jButtonModificar = new JButton();
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

        jPanelBotones.add(jButtonModificar);
        add(jPanelBotones, BorderLayout.SOUTH);

        int opcion = JOptionPane.showOptionDialog(null, panelTarea, "Modificar Tarea", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
        if (opcion == JOptionPane.OK_OPTION) {
            tarea.setTitulo(jTextFieldTitulo.getText());
            tarea.setDescripcion(jTextFieldDescripcion.getText());
            tarea.setEstimacion(Integer.parseInt(jTextFieldEstimacion.getText()));
            tarea.setHorasReales(Integer.parseInt(jTextFieldHorasReales.getText()));
            try {
                tareaService.modificar(tarea);
                JOptionPane.showMessageDialog(null, "La tarea se modificó exitosamente");
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(null, "Error al modificar la tarea" + ex.getMessage());
            }
        }
    }

    public void formularioBuscar() {
        tareaService = new TareaService();
        panelTarea = new JPanel();
        panelTarea.setLayout(new GridLayout(1, 1));

        jPanelBotones = new JPanel();

        jLabelId = new JLabel("ID de la tarea: ");

        panelTarea.add(jLabelId);
        panelTarea.add(jTextFieldId);

        setLayout(new BorderLayout());
        add(panelTarea, BorderLayout.CENTER);

        jPanelBotones.add(jButtonBuscar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarTarea();
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTarea panelTarea1 = new PanelTarea(panel);
                panelTarea1.mostrarOpciones();
                panel.mostrar(panelTarea1);
            }
        });
    }

    public void buscarTarea() {
        try {
            int idTarea = Integer.parseInt(jTextFieldId.getText());
            Tarea tarea = tareaService.buscarTarea(idTarea);
            if (tarea != null) {
                JOptionPane.showMessageDialog(null, "Tarea encontrada:\n" +
                        "ID: " + idTarea + "\n" +
                        "Título: " + tarea.getDescripcion() + "\n" +
                        "Descripción: " + tarea.getDescripcion() + "\n" +
                        "Estimacion: " + tarea.getEstimacion());
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ninguna tarea con ese ID");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID de tarea válido");
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar la tarea");
        }
    }

    JComboBox<Tarea> jComboBoxTarea;
    JComboBox<Empleado> jComboBoxEmpleado;
    JLabel jLabelTarea;
    JLabel jLabelEmpleadosDisponibles;
    JButton jButtonLimpiar = new JButton("Limpiar");

    public void formularioAsignarEmpleado() {
        jLabelTarea = new JLabel("Tarea: ");
        jComboBoxTarea = new JComboBox<>();

        jLabelEmpleadosDisponibles = new JLabel("Empleados:");
        jComboBoxEmpleado = new JComboBox<>();

        jButtonAsignar = new JButton("Asignar");

        tareaService = new TareaService();
        EmpleadoService empleadoService = new EmpleadoService();

        panelTarea = new JPanel();
        panelTarea.setLayout(new GridLayout(1, 1));

        jPanelBotones = new JPanel();

        panelTarea.add(jLabelTarea);
        panelTarea.add(jComboBoxTarea);

        panelTarea.add(jLabelEmpleadosDisponibles);
        panelTarea.add(jComboBoxEmpleado);

        setLayout(new BorderLayout());
        add(panelTarea, BorderLayout.CENTER);

        jPanelBotones.add(jButtonAsignar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        try {
            ArrayList<Tarea> tareas = tareaService.obtenerTareasSinEmpleadosAsignados();
            for (Tarea tarea : tareas) {
                jComboBoxTarea.addItem(tarea);
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener las tareas: " + e.getMessage());
        }

        try {
            ArrayList<Empleado> empleadosDisponibles = empleadoService.obtenerEmpleadosDisponibles();
            for (Empleado empleado : empleadosDisponibles) {
                jComboBoxEmpleado.addItem(empleado);
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener las tareas: " + e.getMessage());
        }


        jButtonAsignar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                asignarEmpleado();
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTarea panelTarea1 = new PanelTarea(panel);
                panelTarea1.mostrarOpciones();
                panel.mostrar(panelTarea1);
            }
        });

        jButtonLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarTareas();
            }
        });
    }

    public void asignarEmpleado() {
        Tarea tareaSeleccionada = (Tarea) jComboBoxTarea.getSelectedItem();
        Empleado empleadoSeleccionado = (Empleado) jComboBoxEmpleado.getSelectedItem();

        if (tareaSeleccionada != null && empleadoSeleccionado != null) {
            int tareaId = tareaSeleccionada.getId();
            int empleadoId = empleadoSeleccionado.getId();

            try {
                tareaService.asignarEmpleado(tareaId, empleadoId);
                JOptionPane.showMessageDialog(null, "Empleado asignado a la tarea correctamente");
                limpiarTareas();
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(null, "Error al asignar empleado a la tarea: " + ex.getMessage());
                limpiarTareas();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error: Tarea o empleado seleccionado inválido");
            limpiarTareas();
        }
    }

    private void limpiarTareas() {
        jComboBoxEmpleado.setSelectedIndex(-1);
        jComboBoxTarea.setSelectedIndex(-1);
    }

    public void panelDesasignar(){
        tareaService = new TareaService();
        EmpleadoService empleadoService = new EmpleadoService();

        panelTarea = new JPanel();
        panelTarea.setLayout(new GridLayout(2, 2));

        jPanelBotones = new JPanel();
        jLabelEmpleado = new JLabel("Seleccionar empleado:");
        jComboBoxEmpleado = new JComboBox<>();
        ArrayList<Empleado> empleados = new ArrayList<>();

        try {
            empleados = empleadoService.obtenerEmpleadosAsignados();
            for(Empleado empleado : empleados) {
                jComboBoxEmpleado.addItem(empleado);
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener los empleados: " + e.getMessage());
        }

        panelTarea.add(jLabelEmpleado);
        panelTarea.add(jComboBoxEmpleado);

        setLayout(new BorderLayout());
        add(panelTarea, BorderLayout.CENTER);

        jPanelBotones.add(jButtonDesasignar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonDesasignar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desasignarEmpleado();
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTarea panelTarea1 = new PanelTarea(panel);
                panelTarea1.mostrarOpciones();
                panel.mostrar(panelTarea1);
            }
        });
    }

    public void desasignarEmpleado() {
        Empleado empleadoSeleccionado = (Empleado) jComboBoxEmpleado.getSelectedItem();

        if (empleadoSeleccionado != null) {
            int confirmacion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Seguro que desea desasignar a " + empleadoSeleccionado.getNombre() + " de su tarea?\n" +
                            "ID: " + empleadoSeleccionado.getId() + "\n" +
                            "Costo por hora: " + empleadoSeleccionado.getCostoPorHora(),
                    "Confirmación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    tareaService.desasignarEmpleado(empleadoSeleccionado);
                    JOptionPane.showMessageDialog(this, "Empleado desasignado");
                } catch (ServiceException ex) {
                    JOptionPane.showMessageDialog(null, "Error al desasignar empleado: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado");
        }
    }


    public void mostrarTodas() {
        tareaService = new TareaService();
        try {
            ArrayList<Tarea> tareas = tareaService.obtenerTodasLasTareas();
            StringBuilder tareasInfo = new StringBuilder("Lista de tareas:\n\n");

            for (Tarea tarea : tareas) {
                tareasInfo.append("ID: ").append(tarea.getId()).append("\n");
                tareasInfo.append("Titulo: ").append(tarea.getTitulo()).append("\n");
                tareasInfo.append("Descripcion: ").append(tarea.getDescripcion()).append("\n");
                tareasInfo.append("Estimacion: ").append(tarea.getEstimacion()).append("\n");
                tareasInfo.append("Horas Reales: ").append(tarea.getHorasReales()).append("\n");
                tareasInfo.append("ID de empleado asignado: ").append(tarea.getEmpleado_id()).append("\n");
                tareasInfo.append("ID del proyecto: ").append(tarea.getIdProyecto()).append("\n");

                tareasInfo.append("\n");
            }
            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.setText(tareasInfo.toString());

            JScrollPane scrollPane = new JScrollPane(textArea);
            JOptionPane.showMessageDialog(null, scrollPane, "Tareas", JOptionPane.INFORMATION_MESSAGE);
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener la lista de tareas: " + e.getMessage());
        }
    }
}
