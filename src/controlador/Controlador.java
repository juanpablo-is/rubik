package controlador;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import interfaz.*;
import mundo.Rubik;
import mundo.SocketServer;

public class Controlador extends Thread {

    private MainFrame interfazApp;
    private SocketServer socket;
    private Rubik cuboRubik;
    private ArrayList<String> movimientosParaIp;
    private ArrayList<String> direccionesIP;

    public Controlador(MainFrame interfazApp) {
        cuboRubik = new Rubik();
        this.interfazApp = interfazApp;
        direccionesIP = new ArrayList<String>();
        movimientosParaIp = new ArrayList<String>();
        socket = new SocketServer(this);
        socket.start();
    }

    public void agregaLabels(String label) {
        interfazApp.agregarLabel(label);
    }

    public void solucion(String solucion[], String grupo) {
        HiloSolucionador hilo = new HiloSolucionador(interfazApp, solucion);
        hilo.start();
    }

    public void hor(int disco) {
        cuboRubik.hor(disco);
    }

    public void ver(int disco) {
        cuboRubik.ver(disco);
    }

    public void tra(int disco) {
        cuboRubik.tra(disco);
    }

    public void imprimir() {
//        cuboRubik.imprimir();
        socket.a();
    }

    public void movimientosParaIp(String movimiento) {
        movimientosParaIp.add(movimiento);
    }

    public void enviarMovimientos() {
        socket.iniciarMovimientos();
        for (int i = 0; i < movimientosParaIp.size(); i++) {
            socket.envioMensajes(cuboRubik, "H0", 1, direccionesIP.get(i));
            System.out.println("Para la ip: " + direccionesIP.get(i) + " se le ha enviado el movimiento: " + movimientosParaIp.get(i));
        }
    }

    public void agregarIp(String ip) {
        direccionesIP.add(ip);
    }

}
