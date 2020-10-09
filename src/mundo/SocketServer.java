package mundo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import com.google.gson.Gson;
import controlador.*;
import java.util.HashMap;
import javafx.application.Platform;

public final class SocketServer extends Thread {

    //Atributos
    private Queue<String> trafico;
    ArrayList<String> movimientos = new ArrayList<>();
    private final int puertoEnvio, puertoEscucha;
    private final Gson gson;
    private final HashMap<String, String> gruposConectados = new HashMap<>();
    int i = 0;
    //Relaciones
    private Controlador ctrl;
    ArrayList<String> pruebaIP;

    public SocketServer(Controlador ctrl) {
        this.ctrl = ctrl;
        trafico = new LinkedList<>();
        gson = new Gson();
        puertoEnvio = 5000;
        puertoEscucha = 5050;
        iniciarMovimientos();
//        Platform.runLater(this);
    }

    public void a() {
        i++;
        ctrl.agregaLabels("192.168.10." + i);
    }
//--------------------------------------Socket Escucha-------------------------------------------------------

    @Override
    public void run() {
        try {
            ServerSocket socketEscucha = new ServerSocket(puertoEscucha);
            while (true) {
                Socket socket = socketEscucha.accept();
                DataInputStream dato = new DataInputStream(socket.getInputStream());
                trafico.add(dato.readUTF());
                while (trafico.size() > 0) {
                    Mensaje recibido = gson.fromJson(trafico.remove(), Mensaje.class);
                    if (recibido.getOpcion() == 0) {
                        //Conectarse al servidor - mandan solo la direccion IP y equipo
                        System.out.println("Se ha conectado: [" + recibido.getGrupo() + "," + recibido.getIp() + "]");
                        //ctrl.agregaLabels(recibido.getIp() + " " + recibido.getGrupo());
                        gruposConectados.put(recibido.getIp(), recibido.getGrupo());
                        ctrl.agregarIp(recibido.getIp());
                        ctrl.movimientosParaIp(movimientos.remove((int) (Math.random() * (movimientos.size() - 1))));

                    } else if (recibido.getOpcion() == 2) {
                        //Respuesta
                        System.out.println("Solucion Encontrada por: " + gruposConectados.get(recibido.getIp()) + " en la IP: " + recibido.getIp());
                        ctrl.solucion(recibido.getSolucion(), recibido.getIp());
                        trafico = new LinkedList<>();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("No se ha podido utilizar el puerto " + puertoEscucha + " " + e.getMessage());
        }
    }

//--------------------------------------Envio Rama a Clientes-------------------------------------------------
    public void envioMensajes(Rubik cuboRubik, String movimiento, int opcion, String ip) {
        try {
//            System.out.println("Entro");
            Socket socketEnvio = new Socket(ip, puertoEnvio);
            DataOutputStream envio = new DataOutputStream(socketEnvio.getOutputStream());
            if (opcion == 1) {
                System.out.println("Envio Mensaje tipo 1.");
                Mensaje mensaje = new Mensaje(cuboRubik, movimiento, opcion);
                envio.writeUTF(gson.toJson(mensaje));
            } else if (opcion == 3) {
                Mensaje mensaje = new Mensaje(opcion);
                envio.writeUTF(gson.toJson(mensaje));
            }
        } catch (IOException e) {
            System.out.println("Problema: " + e.getMessage());
        }
    }

    public void iniciarMovimientos() {
        movimientos.add("H0");
        movimientos.add("H1");
        movimientos.add("V0");
        movimientos.add("V1");
        movimientos.add("T0");
        movimientos.add("T1");
        movimientos.add("0H");
        movimientos.add("1H");
        movimientos.add("0V");
        movimientos.add("1V");
        movimientos.add("0T");
        movimientos.add("1T");
    }
}
