package controlador;

import interfaz.*;

public class HiloSolucionador extends Thread {

    private MainFrame interfazApp;
    private String solucion[];

    public HiloSolucionador(MainFrame interfazApp, String[] solucion) {
        this.interfazApp = interfazApp;
        this.solucion = solucion;
    }

    @Override
    public void run() {
        for (int i = 0; i < solucion.length; i++) {
            if (solucion[i].equals("H0")) {
                interfazApp.rotacionH0();
            } else if (solucion[i].equals("0H")) {
                interfazApp.rotacionH0();
                interfazApp.rotacionH0();
                interfazApp.rotacionH0();
            } else if (solucion[i].equals("H1")) {
                interfazApp.rotacionH1();
            } else if (solucion[i].equals("1H")) {
                interfazApp.rotacionH1();
                interfazApp.rotacionH1();
                interfazApp.rotacionH1();
            } else if (solucion[i].equals("V0")) {
                interfazApp.rotacionV0();
            } else if (solucion[i].equals("0V")) {
                interfazApp.rotacionV0();
                interfazApp.rotacionV0();
                interfazApp.rotacionV0();
            } else if (solucion[i].equals("V1")) {
                interfazApp.rotacionV1();
            } else if (solucion[i].equals("1V")) {
                interfazApp.rotacionV1();
                interfazApp.rotacionV1();
                interfazApp.rotacionV1();
            } else if (solucion[i].equals("T0")) {
                interfazApp.rotacionT0();
            } else if (solucion[i].equals("0T")) {
                interfazApp.rotacionT0();
                interfazApp.rotacionT0();
                interfazApp.rotacionT0();
            } else if (solucion[i].equals("T1")) {
                interfazApp.rotacionT1();
            } else if (solucion[i].equals("1T")) {
                interfazApp.rotacionT1();
                interfazApp.rotacionT1();
                interfazApp.rotacionT1();
            }
            try {
                sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
