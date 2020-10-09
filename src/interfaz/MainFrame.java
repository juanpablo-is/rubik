package interfaz;

import java.util.ArrayList;

import controlador.*;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.ObservableFaceArray;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainFrame extends Application {

    private ToolBar toolBar;
    private ArrayList<Label> labels;
    private Rotate rotateX;
    private Rotate rotateY;
    private boolean drawMode = false;
    private Controlador ctrl;
    Group meshGroup;
    // private RotateTransition rotate;
    private int C_Rojo = 0;
    private int C_Verde = 1;
    private int C_Azul = 2;
    private int C_Amarillo = 3;
    private int C_Naranja = 4;
    private int C_Blanco = 5;
    private int C_Negro = 6;

    // frente, derecha, atras, izquierda, arriba, abajo
    private int[][] Colores = {{C_Azul, C_Negro, C_Negro, C_Rojo, C_Blanco, C_Negro}, // 1
    // { C_Rojo, C_Rojo, C_Rojo, C_Rojo, C_Rojo, C_Rojo }, // 1 AUX
    {C_Azul, C_Naranja, C_Negro, C_Negro, C_Blanco, C_Negro}, // 2
    // { C_Blanco, C_Blanco, C_Blanco, C_Blanco, C_Blanco, C_Blanco }, // 2 AUX
    {C_Azul, C_Negro, C_Negro, C_Rojo, C_Negro, C_Amarillo}, // 3
    // { C_Verde, C_Verde, C_Verde, C_Verde, C_Verde, C_Verde }, // 3 AUX
    {C_Azul, C_Naranja, C_Negro, C_Negro, C_Negro, C_Amarillo}, // 4
    // {C_Naranja,C_Naranja,C_Naranja,C_Naranja,C_Naranja,C_Naranja},//4 AUX
    {C_Negro, C_Negro, C_Verde, C_Rojo, C_Blanco, C_Negro}, // 5
    // { C_Amarillo, C_Amarillo, C_Amarillo, C_Amarillo, C_Amarillo, C_Amarillo },
    // // 5 AUX
    {C_Negro, C_Naranja, C_Verde, C_Negro, C_Blanco, C_Negro}, // 6
    // { C_Negro, C_Negro, C_Negro, C_Negro, C_Negro, C_Negro }, // 6 AUX
    {C_Negro, C_Negro, C_Verde, C_Rojo, C_Negro, C_Amarillo}, // 7
    // {C_Azul,C_Azul,C_Azul,C_Azul,C_Azul,C_Azul},//7 AUX
    {C_Negro, C_Naranja, C_Verde, C_Negro, C_Negro, C_Amarillo} // 8
// { C_Rojo, C_Rojo, C_Rojo, C_Rojo, C_Rojo, C_Rojo }, // 8 AUX
};

    private Point3D[] ubicacion = {new Point3D(-0.51f, -0.51f, -0.51f), // 1
        new Point3D(0.51f, -0.51f, -0.51f), // 2
        new Point3D(-0.51f, 0.51f, -0.51f), // 3
        new Point3D(0.51f, 0.51f, -0.51f), // 4
        new Point3D(-0.51f, -0.51f, 0.51f), // 5
        new Point3D(0.51f, -0.51f, 0.51f), // 6
        new Point3D(-0.51f, 0.51f, 0.51f), // 7
        new Point3D(0.51f, 0.51f, 0.51f), // 8
};

    private double mouseOldX, mouseOldY, mousePosX, mousePosY;

    private MeshView[][][] vistaCuboRubik;
    String secuencia = "";

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Group sceneRoot = new Group();
        SubScene subScene = new SubScene(sceneRoot, 600, 600, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.LIGHTGREY);
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateZ(-10);
        subScene.setCamera(camera);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("/files/palette.png")));

        meshGroup = new Group();

        vistaCuboRubik = new MeshView[2][2][2];
        int contador = 0;
        for (int z = 0; z < 2; z++) {
            for (int y = 0; y < 2; y++) {
                for (int x = 0; x < 2; x++) {
                    vistaCuboRubik[x][y][z] = new MeshView();
                    vistaCuboRubik[x][y][z].setMesh(crearCubo(Colores[contador]));
                    vistaCuboRubik[x][y][z].setMaterial(material);
                    vistaCuboRubik[x][y][z].getTransforms().addAll(new Translate(ubicacion[contador].getX(),
                            ubicacion[contador].getY(), ubicacion[contador].getZ()));
                    meshGroup.getChildren().add(vistaCuboRubik[x][y][z]);
                    contador++;
                }
            }
        }

        rotateX = new Rotate(30, 0, 0, 0, Rotate.X_AXIS);
        rotateY = new Rotate(20, 0, 0, 0, Rotate.Y_AXIS);
        meshGroup.getTransforms().addAll(rotateX, rotateY);

        sceneRoot.getChildren().addAll(meshGroup, new AmbientLight(Color.WHITE));

        subScene.setOnMousePressed(me -> {
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });
        subScene.setOnMouseDragged(me -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            rotateX.setAngle(rotateX.getAngle() - (mousePosY - mouseOldY));
            rotateY.setAngle(rotateY.getAngle() + (mousePosX - mouseOldX));
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
        });

        BorderPane pane = new BorderPane();

        pane.setCenter(subScene);

        Button button = new Button("Reiniciar");

        button.setOnAction(e -> {
            System.out.println(secuencia);
            ctrl.solucion(secuencia.trim().split(" "), "Servidor");
            secuencia = "";

        });

        Button solucionar = new Button("Enviar");
        solucionar.setOnAction(e -> {
            for (int i = 0; i < toolBar.getItems().size(); i++) {
                if (toolBar.getItems().get(i) instanceof TextField) {
                    TextField aux = (TextField) toolBar.getItems().get(i);
                    //ctrl.movimientosParaIp(aux.getText());
                }
            }
            ctrl.enviarMovimientos();

        });

        CheckBox checkBox = new CheckBox("Mostrar lineas");

        checkBox.setOnAction(e -> {
            drawMode = !drawMode;
            if (drawMode) {
                for (int z = 0; z < 2; z++) {
                    for (int y = 0; y < 2; y++) {
                        for (int x = 0; x < 2; x++) {
                            vistaCuboRubik[x][y][z].setDrawMode(DrawMode.LINE);
                        }
                    }
                }

            } else {
                for (int z = 0; z < 2; z++) {
                    for (int y = 0; y < 2; y++) {
                        for (int x = 0; x < 2; x++) {
                            vistaCuboRubik[x][y][z].setDrawMode(DrawMode.FILL);
                        }
                    }
                }
            }

        });

        toolBar = new ToolBar(button, solucionar, checkBox);

        toolBar.setOrientation(Orientation.VERTICAL);
        toolBar.setMinWidth(200);
        toolBar.setMaxWidth(200);
        toolBar.setPrefWidth(200);

        pane.setRight(toolBar);
        pane.setPrefSize(300, 300);

        pane.setOnKeyPressed(e -> {
            subScene.requestFocus();
            e.consume();
            if (e.getCode() == KeyCode.V) {
                if (e.isAltDown()) {
                    if (e.isControlDown()) {
                        rotacionV1();
                        rotacionV1();
                        rotacionV1();
                        secuencia = "V1 " + secuencia;
                    } else {
                        rotacionV1();
                        secuencia = "1V " + secuencia;
                    }
                } else {
                    if (e.isControlDown()) {
                        rotacionV0();
                        rotacionV0();
                        rotacionV0();
                        secuencia = "V0 " + secuencia;
                    } else {
                        rotacionV0();
                        secuencia = "0V " + secuencia;
                    }
                }

            } else if (e.getCode() == KeyCode.H) {
                if (e.isAltDown()) {
                    if (e.isControlDown()) {
                        rotacionH1();
                        rotacionH1();
                        rotacionH1();
                        secuencia = "H1 " + secuencia;
                    } else {
                        rotacionH1();
                        secuencia = "1H " + secuencia;
                    }
                } else {
                    if (e.isControlDown()) {
                        rotacionH0();
                        rotacionH0();
                        rotacionH0();
                        secuencia = "H0 " + secuencia;
                    } else {
                        rotacionH0();
                        secuencia = "0H " + secuencia;
                    }
                }
            } else if (e.getCode() == KeyCode.T) {
                if (e.isAltDown()) {
                    if (e.isControlDown()) {
                        rotacionT1();
                        rotacionT1();
                        rotacionT1();
                        secuencia = "T1 " + secuencia;

                    } else {
                        rotacionT1();
                        secuencia = "1T " + secuencia;
                    }
                } else {
                    if (e.isControlDown()) {
                        rotacionT0();
                        rotacionT0();
                        rotacionT0();
                        secuencia = "T0 " + secuencia;
                    } else {
                        rotacionT0();
                        secuencia = "0T " + secuencia;
                    }
                }
            } else if (e.getCode() == KeyCode.P) {
                ctrl.imprimir();
            }
        });

        Scene Scene = new Scene(pane);

        primaryStage.setTitle("Cubo Rubik 2x2x2");
        primaryStage.setScene(Scene);
        primaryStage.show();
        ctrl = new Controlador(this);
    }

    public void rotacionV0() {
        MeshView cubo1 = vistaCuboRubik[0][0][0];
        MeshView cubo3 = vistaCuboRubik[0][1][0];
        MeshView cubo5 = vistaCuboRubik[0][0][1];
        MeshView cubo7 = vistaCuboRubik[0][1][1];

        vistaCuboRubik[0][0][0] = cubo5;
        // RotateTransition rt = new RotateTransition(Duration.seconds(1),
        // vistaCuboRubik[0][0][0]);
        // rt.setCycleCount(1);
        // rt.setAxis(Rotate.X_AXIS);
        // rt.setByAngle(90);
        // rt.setInterpolator(Interpolator.LINEAR);
        // rt.play();

        vistaCuboRubik[0][1][0] = cubo1;
        // rt = new RotateTransition(Duration.seconds(1), vistaCuboRubik[0][1][0]);
        // rt.setCycleCount(1);
        // rt.setAxis(Rotate.X_AXIS);
        // rt.setByAngle(90);
        // rt.setInterpolator(Interpolator.LINEAR);
        // rt.play();

        vistaCuboRubik[0][0][1] = cubo7;
        // rt = new RotateTransition(Duration.seconds(1), vistaCuboRubik[0][0][1]);
        // rt.setCycleCount(1);
        // rt.setAxis(Rotate.X_AXIS);
        // rt.setByAngle(90);
        // rt.setInterpolator(Interpolator.LINEAR);
        // rt.play();

        vistaCuboRubik[0][1][1] = cubo3;
        // rt = new RotateTransition(Duration.seconds(1), vistaCuboRubik[0][1][1]);
        // rt.setCycleCount(1);
        // rt.setAxis(Rotate.X_AXIS);
        // rt.setByAngle(90);
        // rt.setInterpolator(Interpolator.LINEAR);
        // rt.play();

        // try {
        // Thread.sleep(1000);
        // } catch (InterruptedException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        vistaCuboRubik[0][0][0].getTransforms().addAll(new Translate(0, 0, -1.02));
        ObservableFaceArray Caras = ((TriangleMesh) vistaCuboRubik[0][0][0].getMesh()).getFaces();
        rotacionCaraVertical(Caras);
        vistaCuboRubik[0][1][0].getTransforms().addAll(new Translate(0, +1.02, 0));
        Caras = ((TriangleMesh) vistaCuboRubik[0][1][0].getMesh()).getFaces();
        rotacionCaraVertical(Caras);
        vistaCuboRubik[0][0][1].getTransforms().addAll(new Translate(0, -1.02, 0));
        Caras = ((TriangleMesh) vistaCuboRubik[0][0][1].getMesh()).getFaces();
        rotacionCaraVertical(Caras);
        vistaCuboRubik[0][1][1].getTransforms().addAll(new Translate(0, 0, +1.02));
        Caras = ((TriangleMesh) vistaCuboRubik[0][1][1].getMesh()).getFaces();
        rotacionCaraVertical(Caras);
        ctrl.ver(0);
    }

    public void rotacionV1() {

        MeshView cubo2 = vistaCuboRubik[1][0][0];
        MeshView cubo4 = vistaCuboRubik[1][1][0];
        MeshView cubo6 = vistaCuboRubik[1][0][1];
        MeshView cubo8 = vistaCuboRubik[1][1][1];

        vistaCuboRubik[1][0][0] = cubo6;
        vistaCuboRubik[1][0][0].getTransforms().addAll(new Translate(0, 0, -1.02));
        ObservableFaceArray Caras = ((TriangleMesh) vistaCuboRubik[1][0][0].getMesh()).getFaces();
        rotacionCaraVertical(Caras);

        vistaCuboRubik[1][1][0] = cubo2;
        vistaCuboRubik[1][1][0].getTransforms().addAll(new Translate(0, +1.02, 0));
        Caras = ((TriangleMesh) vistaCuboRubik[1][1][0].getMesh()).getFaces();
        rotacionCaraVertical(Caras);

        vistaCuboRubik[1][1][1] = cubo4;
        vistaCuboRubik[1][1][1].getTransforms().addAll(new Translate(0, 0, +1.02));
        Caras = ((TriangleMesh) vistaCuboRubik[1][1][1].getMesh()).getFaces();
        rotacionCaraVertical(Caras);

        vistaCuboRubik[1][0][1] = cubo8;
        vistaCuboRubik[1][0][1].getTransforms().addAll(new Translate(0, -1.02, 0));
        Caras = ((TriangleMesh) vistaCuboRubik[1][0][1].getMesh()).getFaces();
        rotacionCaraVertical(Caras);
        ctrl.ver(1);
    }

    public void rotacionH0() {

        MeshView cubo1 = vistaCuboRubik[0][0][0];
        MeshView cubo2 = vistaCuboRubik[1][0][0];
        MeshView cubo5 = vistaCuboRubik[0][0][1];
        MeshView cubo6 = vistaCuboRubik[1][0][1];

        vistaCuboRubik[0][0][0] = cubo5;
        // RotateTransition rt = new RotateTransition(Duration.seconds(1),
        // vistaCuboRubik[0][0][0]);
        // rt.setCycleCount(1);
        // rt.setAxis(Rotate.Y_AXIS);
        // rt.setByAngle(90);
        // rt.setInterpolator(Interpolator.LINEAR);
        // rt.play();
        vistaCuboRubik[0][0][0].getTransforms().addAll(new Translate(0, 0, -1.02));
        ObservableFaceArray Caras = ((TriangleMesh) vistaCuboRubik[0][0][0].getMesh()).getFaces();
        rotacionCaraHorizontal(Caras);

        vistaCuboRubik[1][0][0] = cubo1;
        // rt = new RotateTransition(Duration.seconds(1), vistaCuboRubik[1][0][0]);
        // rt.setCycleCount(1);
        // rt.setAxis(Rotate.Y_AXIS);
        // rt.setByAngle(90);
        // rt.setInterpolator(Interpolator.LINEAR);
        // rt.play();
        vistaCuboRubik[1][0][0].getTransforms().addAll(new Translate(+1.02, 0, 0));
        Caras = ((TriangleMesh) vistaCuboRubik[1][0][0].getMesh()).getFaces();
        rotacionCaraHorizontal(Caras);

        vistaCuboRubik[1][0][1] = cubo2;
        // rt = new RotateTransition(Duration.seconds(1), vistaCuboRubik[1][0][1]);
        // rt.setCycleCount(1);
        // rt.setAxis(Rotate.Y_AXIS);
        // rt.setByAngle(90);
        // rt.setInterpolator(Interpolator.LINEAR);
        // rt.play();
        vistaCuboRubik[1][0][1].getTransforms().addAll(new Translate(0, 0, +1.02));
        Caras = ((TriangleMesh) vistaCuboRubik[1][0][1].getMesh()).getFaces();
        rotacionCaraHorizontal(Caras);

        vistaCuboRubik[0][0][1] = cubo6;
        // rt = new RotateTransition(Duration.seconds(1), vistaCuboRubik[0][0][1]);
        // rt.setCycleCount(1);
        // rt.setAxis(Rotate.Y_AXIS);
        // rt.setByAngle(90);
        // rt.setInterpolator(Interpolator.LINEAR);
        // rt.play();
        vistaCuboRubik[0][0][1].getTransforms().addAll(new Translate(-1.02, 0, 0));
        Caras = ((TriangleMesh) vistaCuboRubik[0][0][1].getMesh()).getFaces();
        rotacionCaraHorizontal(Caras);
        ctrl.hor(0);
    }

    public void rotacionH1() {
        MeshView cubo3 = vistaCuboRubik[0][1][0];
        MeshView cubo4 = vistaCuboRubik[1][1][0];
        MeshView cubo7 = vistaCuboRubik[0][1][1];
        MeshView cubo8 = vistaCuboRubik[1][1][1];

        vistaCuboRubik[0][1][0] = cubo7;
        vistaCuboRubik[0][1][0].getTransforms().addAll(new Translate(0, 0, -1.02));
        ObservableFaceArray Caras = ((TriangleMesh) vistaCuboRubik[0][1][0].getMesh()).getFaces();
        rotacionCaraHorizontal(Caras);

        vistaCuboRubik[1][1][0] = cubo3;
        vistaCuboRubik[1][1][0].getTransforms().addAll(new Translate(+1.02, 0, 0));
        Caras = ((TriangleMesh) vistaCuboRubik[1][1][0].getMesh()).getFaces();
        rotacionCaraHorizontal(Caras);

        vistaCuboRubik[1][1][1] = cubo4;
        vistaCuboRubik[1][1][1].getTransforms().addAll(new Translate(0, 0, +1.02));
        Caras = ((TriangleMesh) vistaCuboRubik[1][1][1].getMesh()).getFaces();
        rotacionCaraHorizontal(Caras);

        vistaCuboRubik[0][1][1] = cubo8;
        vistaCuboRubik[0][1][1].getTransforms().addAll(new Translate(-1.02, 0, 0));
        Caras = ((TriangleMesh) vistaCuboRubik[0][1][1].getMesh()).getFaces();
        rotacionCaraHorizontal(Caras);
        ctrl.hor(1);
    }

    public void rotacionT0() {
        MeshView cubo1 = vistaCuboRubik[0][0][0];
        MeshView cubo2 = vistaCuboRubik[1][0][0];
        MeshView cubo3 = vistaCuboRubik[0][1][0];
        MeshView cubo4 = vistaCuboRubik[1][1][0];

        vistaCuboRubik[1][0][0] = cubo1;
        vistaCuboRubik[1][0][0].getTransforms().addAll(new Translate(+1.02, 0, 0));
        ObservableFaceArray Caras = ((TriangleMesh) vistaCuboRubik[1][0][0].getMesh()).getFaces();
        rotacionCaraTransverzal(Caras);

        vistaCuboRubik[1][1][0] = cubo2;
        vistaCuboRubik[1][1][0].getTransforms().addAll(new Translate(0, +1.02, 0));
        Caras = ((TriangleMesh) vistaCuboRubik[1][1][0].getMesh()).getFaces();
        rotacionCaraTransverzal(Caras);

        vistaCuboRubik[0][1][0] = cubo4;
        vistaCuboRubik[0][1][0].getTransforms().addAll(new Translate(-1.02, 0, 0));
        Caras = ((TriangleMesh) vistaCuboRubik[0][1][0].getMesh()).getFaces();
        rotacionCaraTransverzal(Caras);

        vistaCuboRubik[0][0][0] = cubo3;
        vistaCuboRubik[0][0][0].getTransforms().addAll(new Translate(0, -1.02, 0));
        Caras = ((TriangleMesh) vistaCuboRubik[0][0][0].getMesh()).getFaces();
        rotacionCaraTransverzal(Caras);
        ctrl.tra(0);
    }

    public void rotacionT1() {
        MeshView cubo5 = vistaCuboRubik[0][0][1];
        MeshView cubo6 = vistaCuboRubik[1][0][1];
        MeshView cubo7 = vistaCuboRubik[0][1][1];
        MeshView cubo8 = vistaCuboRubik[1][1][1];

        vistaCuboRubik[1][0][1] = cubo5;
        vistaCuboRubik[1][0][1].getTransforms().addAll(new Translate(+1.02, 0, 0));
        ObservableFaceArray Caras = ((TriangleMesh) vistaCuboRubik[1][0][1].getMesh()).getFaces();
        rotacionCaraTransverzal(Caras);

        vistaCuboRubik[1][1][1] = cubo6;
        vistaCuboRubik[1][1][1].getTransforms().addAll(new Translate(0, +1.02, 0));
        Caras = ((TriangleMesh) vistaCuboRubik[1][1][1].getMesh()).getFaces();
        rotacionCaraTransverzal(Caras);

        vistaCuboRubik[0][1][1] = cubo8;
        vistaCuboRubik[0][1][1].getTransforms().addAll(new Translate(-1.02, 0, 0));
        Caras = ((TriangleMesh) vistaCuboRubik[0][1][1].getMesh()).getFaces();
        rotacionCaraTransverzal(Caras);

        vistaCuboRubik[0][0][1] = cubo7;
        vistaCuboRubik[0][0][1].getTransforms().addAll(new Translate(0, -1.02, 0));
        Caras = ((TriangleMesh) vistaCuboRubik[0][0][1].getMesh()).getFaces();
        rotacionCaraTransverzal(Caras);
        ctrl.tra(1);
    }

    private void rotacionCaraVertical(ObservableFaceArray Caras) {
        int frente = Caras.get(1);
        int derecha = Caras.get(13);
        int atras = Caras.get(25);
        int izquierda = Caras.get(37);
        int arriba = Caras.get(49);
        int abajo = Caras.get(61);

        // Frente
        Caras.set(1, arriba);
        Caras.set(3, arriba);
        Caras.set(5, arriba);
        Caras.set(7, arriba);
        Caras.set(9, arriba);
        Caras.set(11, arriba);

        // Abajo
        Caras.set(61, frente);
        Caras.set(63, frente);
        Caras.set(65, frente);
        Caras.set(67, frente);
        Caras.set(69, frente);
        Caras.set(71, frente);

        // Atras
        Caras.set(25, abajo);
        Caras.set(27, abajo);
        Caras.set(29, abajo);
        Caras.set(31, abajo);
        Caras.set(33, abajo);
        Caras.set(35, abajo);

        // Arriba
        Caras.set(49, atras);
        Caras.set(51, atras);
        Caras.set(53, atras);
        Caras.set(55, atras);
        Caras.set(57, atras);
        Caras.set(59, atras);
    }

    private void rotacionCaraTransverzal(ObservableFaceArray Caras) {
        int frente = Caras.get(1);
        int derecha = Caras.get(13);
        int atras = Caras.get(25);
        int izquierda = Caras.get(37);
        int arriba = Caras.get(49);
        int abajo = Caras.get(61);

        // Arriba
        Caras.set(49, izquierda);
        Caras.set(51, izquierda);
        Caras.set(53, izquierda);
        Caras.set(55, izquierda);
        Caras.set(57, izquierda);
        Caras.set(59, izquierda);

        // Derecha
        Caras.set(13, arriba);
        Caras.set(15, arriba);
        Caras.set(17, arriba);
        Caras.set(19, arriba);
        Caras.set(21, arriba);
        Caras.set(23, arriba);

        // Abajo
        Caras.set(61, derecha);
        Caras.set(63, derecha);
        Caras.set(65, derecha);
        Caras.set(67, derecha);
        Caras.set(69, derecha);
        Caras.set(71, derecha);

        // Izquierda
        Caras.set(37, abajo);
        Caras.set(39, abajo);
        Caras.set(41, abajo);
        Caras.set(43, abajo);
        Caras.set(45, abajo);
        Caras.set(47, abajo);
    }

    private void rotacionCaraHorizontal(ObservableFaceArray Caras) {
        int frente = Caras.get(1);
        int derecha = Caras.get(13);
        int atras = Caras.get(25);
        int izquierda = Caras.get(37);
        int arriba = Caras.get(49);
        int abajo = Caras.get(61);

        // Frente
        Caras.set(1, izquierda);
        Caras.set(3, izquierda);
        Caras.set(5, izquierda);
        Caras.set(7, izquierda);
        Caras.set(9, izquierda);
        Caras.set(11, izquierda);

        // Derecha
        Caras.set(13, frente);
        Caras.set(15, frente);
        Caras.set(17, frente);
        Caras.set(19, frente);
        Caras.set(21, frente);
        Caras.set(23, frente);

        // Atras
        Caras.set(25, derecha);
        Caras.set(27, derecha);
        Caras.set(29, derecha);
        Caras.set(31, derecha);
        Caras.set(33, derecha);
        Caras.set(35, derecha);

        // Izquierda
        Caras.set(37, atras);
        Caras.set(39, atras);
        Caras.set(41, atras);
        Caras.set(43, atras);
        Caras.set(45, atras);
        Caras.set(47, atras);
    }

    private TriangleMesh crearCubo(int Colores[]) {
        TriangleMesh cubo = new TriangleMesh();

        // vertices
        cubo.getPoints().addAll(0.5f, 0.5f, 0.5f, // 1
                0.5f, -0.5f, 0.5f, // 2
                0.5f, 0.5f, -0.5f, // 3
                0.5f, -0.5f, -0.5f, // 4
                -0.5f, 0.5f, 0.5f, // 5
                -0.5f, -0.5f, 0.5f, // 6
                -0.5f, 0.5f, -0.5f, // 7
                -0.5f, -0.5f, -0.5f // 8
        );

        // texturas
        cubo.getTexCoords().addAll(0.07142857f, 0.5f, // Rojo 0.5/7 0
                0.21428571f, 0.5f, // Verde 1.5/7 1
                0.35714286f, 0.5f, // Azul 2.5/7 2
                0.5f, 0.5f, // Amarillo 3.5/7 3
                0.64285714f, 0.5f, // Naranja 4.5/7 4
                0.78571429f, 0.5f, // Blanco 5.5/7 5
                0.92857143f, 0.5f); // Negro 6.5/7 6

        cubo.getFaces().addAll(2, Colores[0], 3, Colores[0], 6, Colores[0], // Frente
                3, Colores[0], 7, Colores[0], 6, Colores[0],
                0, Colores[1], 1, Colores[1], 2, Colores[1], // Derecha
                2, Colores[1], 1, Colores[1], 3, Colores[1],
                0, Colores[2], 4, Colores[2], 1, Colores[2], // Atras
                4, Colores[2], 5, Colores[2], 1, Colores[2],
                4, Colores[3], 6, Colores[3], 5, Colores[3], // Izquierda
                6, Colores[3], 7, Colores[3], 5, Colores[3],
                1, Colores[4], 5, Colores[4], 3, Colores[4], // Arriba
                5, Colores[4], 7, Colores[4], 3, Colores[4],
                0, Colores[5], 2, Colores[5], 4, Colores[5], // Abajo
                2, Colores[5], 6, Colores[5], 4, Colores[5]);

        return cubo;
    }

    public void agregarLabel(String label) {
        toolBar.getItems().add(new Label(label));
        toolBar.getItems().add(new TextField());
    }
}
