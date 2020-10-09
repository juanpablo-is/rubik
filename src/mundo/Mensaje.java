package mundo;

public class Mensaje {

    //Atributos
    private Rubik cuboRubik;
    private String movimiento, solucion[], ip, grupo;
    private int opcion;

    /* Tipos de mensajes - Opcion
	 * 0- Enviar del Cliente al servidor IP y Nombre del Equipo
	 * 1- Enviar del Servidor al Cliente Cubo Rubik y Movimiento a realizar
	 * 2- Enviar del Cliente al servidor solucion e ip
	 * 3- Enviar del Servidor al Cliente mensaje de parar (Solucion ya encontrada)
     */
    //Mensaje tipo 0
    public Mensaje(String ip, String grupo, int opcion) {
        this.ip = ip;
        this.grupo = grupo;
        this.opcion = opcion;
    }

    //Mensaje tipo 1
    public Mensaje(Rubik cuboRubik, String movimiento, int opcion) {
        this.cuboRubik = cuboRubik;
        this.movimiento = movimiento;
        this.opcion = opcion;
    }

    //Mensaje tipo 2
    public Mensaje(String solucion[], String ip, int opcion) {
        this.ip = ip;
        this.solucion = solucion;
        this.opcion = opcion;
    }

    //Mensaje tipo 3
    public Mensaje(int opcion) {
        this.opcion = opcion;
    }

    public Rubik getCuboRubik() {
        return cuboRubik;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public String[] getSolucion() {
        return solucion;
    }

    public String getIp() {
        return ip;
    }

    public String getGrupo() {
        return grupo;
    }

    public int getOpcion() {
        return opcion;
    }

}
