package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.ServerException;
import java.rmi.server.ServerCloneException;

public class MenuInicio extends JPanel {
    
    PanelManager panel;
    JPanel menuInicio;
    
    JButton jButtonProyeto;
    JButton jButtonTarea;
    JButton jButtonEmpleado;
    JButton jButtonAsignar;
    JButton jButtonDesasignar;
    JButton jButtonSprints;
    JButton jButtonBacklogs;
    
    JPanel jPanelBotones;
    
    public MenuInicio(PanelManager panel){
        this.panel = panel;
        armarMenuInicio();
    }
    
    public void armarMenuInicio(){
        menuInicio = new JPanel();
        menuInicio.setLayout(new GridLayout(0, 1));

        jButtonProyeto = new JButton("Proyecto");
        jButtonEmpleado = new JButton("Empleado");
        jButtonTarea = new JButton("Tarea");
        jButtonSprints = new JButton("Sprints");
        jButtonBacklogs = new JButton("Backlogs");
        jButtonAsignar = new JButton("Panel asignación");
        jButtonDesasignar = new JButton("Panel desasignar");
        jPanelBotones = new JPanel();
        jPanelBotones.setLayout(new BoxLayout(jPanelBotones, BoxLayout.Y_AXIS));
        
        setLayout(new BorderLayout());
        add(menuInicio, BorderLayout.CENTER);

        jPanelBotones.add(jButtonProyeto);
        jPanelBotones.add(jButtonEmpleado);
        jPanelBotones.add(jButtonTarea);
        jPanelBotones.add(jButtonSprints);
        jPanelBotones.add(jButtonBacklogs);
        jPanelBotones.add(jButtonAsignar);
        jPanelBotones.add(jButtonDesasignar);
        add(jPanelBotones, BorderLayout.CENTER);

        jButtonProyeto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelProyecto1 panelProyecto = new PanelProyecto1(panel);
                panelProyecto.menu();
                panel.mostrar(panelProyecto);
            }
        });

        jButtonEmpleado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelEmpleado panelEmpleado = new PanelEmpleado(panel);
                panelEmpleado.inicio();
                panel.mostrar(panelEmpleado);
            }
        });

        jButtonTarea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTarea panelTarea1 = new PanelTarea(panel);
                panelTarea1.inicio();
                panel.mostrar(panelTarea1);
            }
        });

        jButtonAsignar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelAsignaciones panelAsignaciones = new PanelAsignaciones(panel);
                panelAsignaciones.asignacion();
                panel.mostrar(panelAsignaciones);
            }
        });

        jButtonDesasignar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelAsignaciones panelAsignaciones = new PanelAsignaciones(panel);
                panelAsignaciones.desasignar();
                panel.mostrar(panelAsignaciones);
            }
        });

        jButtonSprints.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelSprint panelSprint1 = new PanelSprint(panel);
                panelSprint1.menu();
                panel.mostrar(panelSprint1);
            }
        });

        jButtonBacklogs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelBacklog panelBacklog1 = new PanelBacklog(panel);
                panelBacklog1.menu();
                panel.mostrar(panelBacklog1);
            }
        });
    }
}
