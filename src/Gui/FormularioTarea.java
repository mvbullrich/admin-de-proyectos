package Gui;

import Controlador.Proyecto;
import Controlador.Tarea;
import Service.ServiceException;
import Service.TareaService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioTarea extends JPanel {
    TareaService tareaService;
    PanelManager panel;
    JPanel formularioTarea;
    JLabel jLabelId;
    JLabel jlabelTitulo;
    JLabel jLabelDescripcion;
    JLabel jLabelEstimacion;
    JLabel jLabelHorasReales;

    JTextField jTextFieldId;
    JTextField jTextFieldTitulo;
    JTextField jTextFieldDescripcion;
    JTextField jTextFieldEstimacion;
    JTextField jTextFieldHorasReales;

    JButton jButtonGuardar;
    JButton jButtonBuscar;
    JButton jButtonEliminar;
    JPanel jPanelBotones;


    public FormularioTarea(PanelManager panel){
        this.panel = panel;
        armarFormularioTarea();
    }

    public void armarFormularioTarea(){
        tareaService = new TareaService();
        formularioTarea = new JPanel();
        formularioTarea.setLayout(new GridLayout(4, 2));

        jLabelId = new JLabel("ID: ");
        jlabelTitulo = new JLabel("Titulo: ");
        jLabelDescripcion = new JLabel("Descripción:");
        jLabelEstimacion = new JLabel("Estimacion:");
        //jLabelHorasReales = new JLabel("HorasReales:");

        jTextFieldId = new JTextField(10);
        jTextFieldTitulo = new JTextField(30);
        jTextFieldDescripcion = new JTextField(50);
        jTextFieldEstimacion = new JTextField(20);
        //jTextFieldHorasReales = new JTextField(20);

        jButtonGuardar = new JButton("Guardar");
        jButtonBuscar = new JButton("Buscar");
        jButtonEliminar = new JButton("Eliminar");
        jPanelBotones = new JPanel();

        formularioTarea.add(jLabelId);
        formularioTarea.add(jTextFieldId);
        formularioTarea.add(jlabelTitulo);
        formularioTarea.add(jTextFieldTitulo);
        formularioTarea.add(jLabelDescripcion);
        formularioTarea.add(jTextFieldDescripcion);
        formularioTarea.add(jLabelEstimacion);
        formularioTarea.add(jTextFieldEstimacion);
        //formularioTarea.add(jLabelHorasReales);
        //formularioTarea.add(jTextFieldHorasReales);

        setLayout(new BorderLayout());
        add(formularioTarea, BorderLayout.CENTER);

        jPanelBotones.add(jButtonGuardar);
        jPanelBotones.add(jButtonBuscar);
        jPanelBotones.add(jButtonEliminar);
        add(jPanelBotones, BorderLayout.SOUTH);

        jButtonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarTarea();
            }
    });

        jButtonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarTarea();
            }
        });

        jButtonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarTarea();
            }
        });
}

    public void guardarTarea() {
        Tarea tarea = new Tarea();
        tarea.setTitulo(jTextFieldTitulo.getText());
        tarea.setDescripcion(jTextFieldDescripcion.getText());
        tarea.setEstimacion(Integer.parseInt(jTextFieldEstimacion.getText()));
        //tarea.setHorasReales(Integer.parseInt(jTextFieldHorasReales.getText()));
        try {
            tareaService.guardarTarea(tarea);
            JOptionPane.showMessageDialog(null, "Tarea guardada exitosamente");
        } catch (ServiceException serEx) {
            JOptionPane.showMessageDialog(null, "No se pudo guardar la tarea");
        }
    }

    public void eliminarTarea(){
        try {
            int idTarea = Integer.parseInt(jTextFieldId.getText());
            Tarea tarea = tareaService.buscarTarea(idTarea);
            if(tarea != null){
                JOptionPane.showMessageDialog(null, "Tarea encontrada:\n" +
                        "Título: " + tarea.getTitulo() + "\n" +
                        "Descripción: " + tarea.getDescripcion() + "\n" +
                        "Estimación: " + tarea.getEstimacion());
                int confirmacion = JOptionPane.showConfirmDialog(null, "¿Seguro que quiere eliminar esta tarea?");
                if (confirmacion == JOptionPane.YES_OPTION){
                    try{
                        tareaService.eliminarTarea(idTarea);
                        JOptionPane.showMessageDialog(null, "La tarea se eliminó exitosamente");;
                    } catch (NumberFormatException ex){
                        JOptionPane.showMessageDialog(null, "Ingrese un ID de tarea válido");
                    } catch(ServiceException serEx){
                        JOptionPane.showMessageDialog(null, "No se pudo eliminar la tarea");
                    }
                } else{
                    JOptionPane.showMessageDialog(null, "No se encontró ninguna tarea con ese ID");
                }
            }
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Ingrese un ID válido");
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar la tarea");
        }
    }

    public void buscarTarea(){
        try {
            int idTarea = Integer.parseInt(jTextFieldId.getText());
            Tarea tarea = tareaService.buscarTarea(idTarea);
            if (tarea != null) {
                JOptionPane.showMessageDialog(null, "Tarea encontrada:\n" +
                        "Título: " + tarea.getTitulo() + "\n" +
                        "Descripción: " + tarea.getDescripcion() + "\n" +
                        "Estimación: " + tarea.getEstimacion());
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ninguna tarea con ese ID");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingresa un ID de tarea válido");
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar la tarea");
        }
    }
    }