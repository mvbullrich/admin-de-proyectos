package Gui;

import Controlador.Backlog;
import Controlador.Tarea;
import Service.BacklogService;
import Service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelBacklog extends JPanel {
    BacklogService backlogService;
    PanelManager panel;
    JPanel panelBacklog;
    JLabel jLabel = new JLabel("Seleccione un backlog: ");
    JLabel jLabelId = new JLabel("ID: ");
    JTextField jTextFieldId = new JTextField();
    JLabel jLabelNombre = new JLabel("Nombre: ");
    JTextField jTextFieldNombre = new JTextField();
    JLabel jLabelDescripcion = new JLabel("Descripción: ");
    JTextField jTextFieldDescripcion = new JTextField();
    JLabel jLabelBacklog;
    JComboBox<Backlog> jComboBoxBacklog;
    JLabel jLabelTareas;
    JComboBox<Tarea> jComboBoxTareas;
    JButton jButtonGuardar;
    JButton jButtonEliminar = new JButton("Eliminar");
    JButton jButtonModificar = new JButton("Modificar");
    JButton jButtonMostrar = new JButton("Ver tareas asignadas");
    JButton jButtonSalir = new JButton("Salir");
    JButton jButtonBacklogs = new JButton("Mis backlogs");

    JPanel jPanelBotones;

    public PanelBacklog(PanelManager panel){
        this.panel = panel;
    }

    public void menu() {
        panelBacklog = new JPanel();
        panelBacklog.setLayout(new GridLayout(6,2));

        jButtonGuardar = new JButton("Guardar nuevo backlog");
        jButtonSalir = new JButton("Salir");
        jPanelBotones = new JPanel();
        jPanelBotones.setLayout(new BoxLayout(jPanelBotones, BoxLayout.Y_AXIS));

        setLayout(new BorderLayout());
        add(panelBacklog, BorderLayout.CENTER);

        jPanelBotones.add(jButtonBacklogs);
        jPanelBotones.add(jButtonGuardar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelBacklog panelBacklog1 = new PanelBacklog(panel);
                panelBacklog1.formularioGuardar();
                panel.mostrar(panelBacklog1);
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

        jButtonBacklogs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelBacklog panelBacklog1 = new PanelBacklog(panel);
                panelBacklog1.menuBacklog();
                panel.mostrar(panelBacklog1);
            }
        });
    }

    public void formularioGuardar(){
        backlogService = new BacklogService();
        panelBacklog = new JPanel();
        panelBacklog.setLayout(new GridLayout(6,2));

        jLabelNombre = new JLabel("Nombre: ");
        jLabelDescripcion = new JLabel("Descripcion: ");
        jButtonGuardar = new JButton("Guardar");
        jButtonSalir = new JButton("Atrás");

        jPanelBotones = new JPanel();

        panelBacklog.add(jLabelNombre);
        panelBacklog.add(jTextFieldNombre);
        panelBacklog.add(jLabelDescripcion);
        panelBacklog.add(jTextFieldDescripcion);

        setLayout(new BorderLayout());
        add(panelBacklog, BorderLayout.CENTER);

        jPanelBotones.add(jButtonGuardar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarBacklog();
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelBacklog panelBacklog1 = new PanelBacklog(panel);
                panelBacklog1.menu();
                panel.mostrar(panelBacklog1);
            }
        });
    }

    public void guardarBacklog() {
        try {
            String nombre = jTextFieldNombre.getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(null, "El título es obligatorio.");
                return;
            }
            Backlog backlog = new Backlog();
            backlog.setNombre(nombre);
            String descripcion = jTextFieldDescripcion.getText().trim();
            backlog.setDescripcion(descripcion);
            backlogService.guardarBacklog(backlog);
            JOptionPane.showMessageDialog(null, "Backlog guardado exitosamente");
            jTextFieldNombre.setText("");
            jTextFieldDescripcion.setText("");
        } catch (ServiceException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se pudo guardar el backlog: " + e.getMessage());
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, "Error. Se deben llenar todos los campos");
        }
    }

    public void actualizarCamposText(){
        Backlog backlog = (Backlog) jComboBoxBacklog.getSelectedItem();
        if (backlog != null){
            jTextFieldId.setText(String.valueOf(backlog.getId()));
            jTextFieldId.setEditable(false);
            jTextFieldNombre.setText(backlog.getNombre());
            jTextFieldDescripcion.setText(backlog.getDescripcion());
        } else {
            jTextFieldId.setText("");
            jTextFieldNombre.setText("");
            jTextFieldDescripcion.setText("");
        }
    }

    public void menuBacklog(){
        backlogService = new BacklogService();
        panelBacklog = new JPanel();
        panelBacklog.setLayout(new GridLayout(4,2));

        jPanelBotones = new JPanel();

        jButtonSalir = new JButton("Salir");
        jComboBoxBacklog = new JComboBox<>();
        try{
            ArrayList<Backlog> backlogs = backlogService.obtenerBacklogs();
            for (Backlog backlog : backlogs){
                jComboBoxBacklog.addItem(backlog);
            }
        } catch (ServiceException e){
            JOptionPane.showMessageDialog(null, "Error al obtener backlogs");
        }
        actualizarCamposText();
        panelBacklog.add(jLabel);
        panelBacklog.add(jComboBoxBacklog);
        panelBacklog.add(jLabelId);
        panelBacklog.add(jTextFieldId);
        panelBacklog.add(jLabelNombre);
        panelBacklog.add(jTextFieldNombre);
        panelBacklog.add(jLabelDescripcion);
        panelBacklog.add(jTextFieldDescripcion);
        setLayout(new BorderLayout());
        add(panelBacklog, BorderLayout.CENTER);

        jPanelBotones.add(jButtonMostrar);
        jPanelBotones.add(jButtonModificar);
        jPanelBotones.add(jButtonEliminar);
        jPanelBotones.add(jButtonSalir);
        add(jPanelBotones, BorderLayout.SOUTH);

        jComboBoxBacklog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCamposText();
            }
        });

        jButtonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Backlog backlogSeleccionado = (Backlog) jComboBoxBacklog.getSelectedItem();
                if (backlogSeleccionado != null) {
                    try{
                        int confirmacion = JOptionPane.showConfirmDialog(null,"¿Seguro que quieres eliminar este backlog?");
                        if (confirmacion == JOptionPane.YES_OPTION){
                            backlogService.eliminarBacklog(backlogSeleccionado.getId());
                            JOptionPane.showMessageDialog(null, "Backlog eliminado exitosamente");
                            jComboBoxBacklog.setSelectedIndex(-1);
                        }
                    } catch (ServiceException ex){
                        JOptionPane.showMessageDialog(null, "Backlog eliminado exitosamente");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un backlog");
                }
            }
        });

        jButtonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelBacklog panelBacklog1 = new PanelBacklog(panel);
                panelBacklog1.menu();
                panel.mostrar(panelBacklog1);
            }
        });

        jButtonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Backlog backlogSeleccionado = (Backlog) jComboBoxBacklog.getSelectedItem();
                String nombre = jTextFieldNombre.getText().trim();
                String descripcion = jTextFieldDescripcion.getText().trim();
                if (backlogSeleccionado != null) {
                    try {
                        if (nombre.isEmpty()) {
                            throw new IllegalArgumentException("El título es obligatorio.");
                        }
                        int confirmar = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres modificar este backlog?");
                        if (confirmar == JOptionPane.YES_OPTION) {
                            backlogSeleccionado.setId(Integer.parseInt(jTextFieldId.getText()));
                            backlogSeleccionado.setNombre(nombre);
                            backlogSeleccionado.setDescripcion(descripcion); // No importa si está vacío, ya que se permite
                            backlogService.modificar(backlogSeleccionado);
                            JOptionPane.showMessageDialog(null, "Backlog modificado exitosamente");
                        }
                    } catch (ServiceException ex) {
                        JOptionPane.showMessageDialog(null, "Error al modificar el backlog: " + ex.getMessage());
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un backlog");
                }
            }
        });

        jButtonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Backlog backlogSeleccionado = (Backlog) jComboBoxBacklog.getSelectedItem();
                if (backlogSeleccionado != null){
                    try {
                        backlogService.buscarBacklog(backlogSeleccionado.getId());
                        StringBuilder infoBacklog = new StringBuilder("BACKLOG ").append(backlogSeleccionado.getId()).append(": ").append(backlogSeleccionado.getNombre()).append("\n");

                        ArrayList<Tarea> tareas = backlogService.obtenerTareasPorBacklog(backlogSeleccionado.getId());
                        if (!tareas.isEmpty()){
                            infoBacklog.append("Tareas asignadas:\n");
                            for (Tarea tarea : tareas){
                                infoBacklog.append("- ").append(tarea.getTitulo()).append("\n");
                            }
                        } else {
                            infoBacklog.append("-No hay tareas asignadas a este backlog");
                        }
                        JOptionPane.showMessageDialog(null, infoBacklog.toString(), "Información del backlog", JOptionPane.INFORMATION_MESSAGE);
                    } catch (ServiceException ex){
                        JOptionPane.showMessageDialog(null, "Error al mostrar los datos");
                    }
                }else {
                    JOptionPane.showMessageDialog(null, "Seleccione un backlog");
                }
            }
        });
    }
}