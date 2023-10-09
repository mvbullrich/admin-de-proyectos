package Gui;

import javax.swing.*;
import java.awt.*;

public class PanelManager {
    private PanelTarea panelTarea;
    private PanelEmpleado panelEmpleado;
    private MenuInicio menuInicio;
    private PanelProyecto1 panelProyecto1;

    JFrame ventana;
    public PanelManager(){
        ventana = new JFrame();
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panelTarea = new PanelTarea(this);
        panelEmpleado = new PanelEmpleado(this);
        menuInicio = new MenuInicio(this);
        panelProyecto1 = new PanelProyecto1(this);

        //mostrar(formularioTarea);
        //mostrar(formularioEmpleado);
        mostrar(menuInicio);
    }


    public void mostrar(JPanel panel){
        ventana.getContentPane().removeAll();
        ventana.getContentPane().add(BorderLayout.SOUTH,panel);
        ventana.getContentPane().validate();
        ventana.getContentPane().repaint();
        ventana.show();
        ventana.pack();
    }

}
