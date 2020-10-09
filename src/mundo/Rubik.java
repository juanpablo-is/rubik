package mundo;

public class Rubik implements Cloneable {

    //Atributos
    private Cube cubos[][][];
    private String key;

    public Rubik() {
        cubos = new Cube[2][2][2];
        for (int i = 0; i < cubos.length; i++) {
            for (int j = 0; j < cubos[i].length; j++) {
                for (int k = 0; k < cubos[i][j].length; k++) {
                    cubos[i][j][k] = new Cube();
                }
            }
        }
        generadorKey();
//		System.out.println(key);
//		System.out.println(comprobarLlave());
    }
//----------------------------------------Rotaciones-----------------------------------------------------------

    public void hor(int disco) {
        //Filas Estaticas
        Cube aux = cubos[disco][0][0];
        cubos[disco][0][0] = cubos[disco][0][1];
        cubos[disco][0][1] = cubos[disco][1][1];
        cubos[disco][1][1] = cubos[disco][1][0];
        cubos[disco][1][0] = aux;
        //Rotacion Cubos
        cubos[disco][0][0].hor();
        cubos[disco][0][1].hor();
        cubos[disco][1][1].hor();
        cubos[disco][1][0].hor();
        //Generar nueva clave
        generadorKey();
    }

    public void ver(int disco) {
        //Columnas Estaticas
        Cube aux = cubos[0][disco][0];
        cubos[0][disco][0] = cubos[0][disco][1];
        cubos[0][disco][1] = cubos[1][disco][1];
        cubos[1][disco][1] = cubos[1][disco][0];
        cubos[1][disco][0] = aux;
        //Rotacion Cubos
        cubos[0][disco][0].ver();
        cubos[0][disco][1].ver();
        cubos[1][disco][1].ver();
        cubos[1][disco][0].ver();
        //Generar nueva clave
        generadorKey();
    }

    public void tra(int disco) {
        //Profundidad Estatica
        Cube aux = cubos[0][0][disco];
        cubos[0][0][disco] = cubos[1][0][disco];
        cubos[1][0][disco] = cubos[1][1][disco];
        cubos[1][1][disco] = cubos[0][1][disco];
        cubos[0][1][disco] = aux;
        //Rotacion Cubos
        cubos[0][0][disco].tra();
        cubos[1][0][disco].tra();
        cubos[1][1][disco].tra();
        cubos[0][1][disco].tra();
        //Generar nueva clave
        generadorKey();
    }
//----------------------------------------Movimientos Negativos--------------------------------------------

    public void nHor(int disco) {
        hor(disco);
        hor(disco);
        hor(disco);
    }

    public void nVer(int disco) {
        ver(disco);
        ver(disco);
        ver(disco);
    }

    public void nTra(int disco) {
        tra(disco);
        tra(disco);
        tra(disco);
    }
//------------------------------------Generardor y Comprobador de llave---------------------------------- 

    public void generadorKey() {
        key = cubos[0][0][0].getFrontal() + "" + cubos[0][1][0].getFrontal() + "" + cubos[1][0][0].getFrontal() + "" + cubos[1][1][0].getFrontal() + "";
        key += cubos[0][1][0].getDerecha() + "" + cubos[0][1][1].getDerecha() + "" + cubos[1][1][0].getDerecha() + "" + cubos[1][1][1].getDerecha() + "";
        key += cubos[0][0][1].getPosterior() + "" + cubos[0][1][1].getPosterior() + "" + cubos[1][0][1].getPosterior() + "" + cubos[1][1][1].getPosterior() + "";
        key += cubos[0][0][0].getIzquierdo() + "" + cubos[0][0][1].getIzquierdo() + "" + cubos[1][0][0].getIzquierdo() + "" + cubos[1][0][1].getIzquierdo() + "";
        key += cubos[0][0][0].getSuperior() + "" + cubos[0][0][1].getSuperior() + "" + cubos[0][1][0].getSuperior() + "" + cubos[0][1][1].getSuperior() + "";
        key += cubos[1][0][0].getInferior() + "" + cubos[1][0][1].getInferior() + "" + cubos[1][1][0].getInferior() + "" + cubos[1][1][1].getInferior();
    }

    public boolean comprobarLlave() {
        String vec[] = key.split("");
        for (int i = 0; i < 6; i++) {
            //System.out.println(vec[4*i] +"  "+  vec[4*i+1]+"  "+vec[4*i+2] +"  "+ vec[4*i+3]);
            if ((!vec[4 * i].equals(vec[4 * i + 1]) || !vec[4 * i + 1].equals(vec[4 * i + 2]) || !vec[4 * i + 2].equals(vec[4 * i + 3])) == true) {
                return false;
            }
        }
        return true;
    }
//--------------------------------------------Clonacion------------------------------------------------

    @Override
    public Rubik clone() {
        Rubik clon = new Rubik();
        Cube cuboClon[][][] = clon.getCuboRubik();
        for (int i = 0; i < cubos.length; i++) {
            for (int j = 0; j < cubos[i].length; j++) {
                for (int k = 0; k < cubos[i][j].length; k++) {
                    cuboClon[i][j][k].setFrontal(cubos[i][j][k].getFrontal());
                    cuboClon[i][j][k].setDerecha(cubos[i][j][k].getDerecha());
                    cuboClon[i][j][k].setIzquierdo(cubos[i][j][k].getIzquierdo());
                    cuboClon[i][j][k].setPosterior(cubos[i][j][k].getPosterior());
                    cuboClon[i][j][k].setSuperior(cubos[i][j][k].getSuperior());
                    cuboClon[i][j][k].setInferior(cubos[i][j][k].getInferior());
                }
            }
        }
        clon.generadorKey();
        return clon;
    }

    public void imprimir() {
        System.out.println("------------------Cara 0-------------------------------");
        System.out.println("cubo: 0 0 0: " + cubos[0][0][0].getIzquierdo() + " " + cubos[0][0][0].getFrontal() + " " + cubos[0][0][0].getSuperior());
        System.out.println("cubo: 0 1 0: " + cubos[0][1][0].getDerecha() + " " + cubos[0][1][0].getFrontal() + " " + cubos[0][1][0].getSuperior());
        System.out.println("cubo: 1 0 0: " + cubos[1][0][0].getIzquierdo() + " " + cubos[1][0][0].getFrontal() + " " + cubos[1][0][0].getInferior());
        System.out.println("cubo: 1 1 0: " + cubos[1][1][0].getDerecha() + " " + cubos[1][1][0].getFrontal() + " " + cubos[1][1][0].getInferior());
        System.out.println("------------------Cara 1-------------------------------");
        System.out.println("cubo: 0 0 1: " + cubos[0][0][1].getIzquierdo() + " " + cubos[0][0][1].getPosterior() + " " + cubos[0][0][1].getSuperior());
        System.out.println("cubo: 0 1 1: " + cubos[0][1][1].getDerecha() + " " + cubos[0][1][1].getPosterior() + " " + cubos[0][1][1].getSuperior());
        System.out.println("cubo: 1 0 1: " + cubos[1][0][1].getIzquierdo() + " " + cubos[1][0][1].getPosterior() + " " + cubos[1][0][1].getInferior());
        System.out.println("cubo: 1 1 1: " + cubos[1][1][1].getDerecha() + " " + cubos[1][1][1].getPosterior() + " " + cubos[1][1][1].getInferior());
    }

//--------------------------------------------Getters--------------------------------------------------
    public String getKey() {
        return key;
    }

    public Cube[][][] getCuboRubik() {
        return cubos;
    }

}
