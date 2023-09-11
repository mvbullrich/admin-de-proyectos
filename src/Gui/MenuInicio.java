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
        jPanelBotones = new JPanel();
        
        setLayout(new BorderLayout());
        add(menuInicio, BorderLayout.CENTER);

        jPanelBotones.add(jButtonProyeto);
        jPanelBotones.add(jButtonEmpleado);
        add(jPanelBotones, BorderLayout.CENTER);

        jButtonProyeto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelProyecto panelProyecto = new PanelProyecto(panel);
                panelProyecto.mostrarOpciones();
                panel.mostrar(panelProyecto);
            }
        });

        jButtonEmpleado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelEmpleado panelEmpleado = new PanelEmpleado(panel);
                panelEmpleado.mostrarOpciones();
                panel.mostrar(panelEmpleado);
            }
        });
    }
}
