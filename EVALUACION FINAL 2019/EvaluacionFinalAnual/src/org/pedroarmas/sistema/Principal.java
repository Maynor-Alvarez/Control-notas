/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pedroarmas.sistema;

import java.io.InputStream;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.pedroarmas.controller.GradoController;
import org.pedroarmas.controller.MenuPrincipalController;

/**
 *
 * @author armas
 */
public class Principal extends Application {
    private String PAQUETE_VISTA = "/org/pedroarmas/view/";
    private Stage escenarioPrincipal;
    private Scene escena;
    @Override
    public void start(Stage escenarioPrincipal) {       
        this.escenarioPrincipal = escenarioPrincipal; 
        escenarioPrincipal.setTitle("Final Anual 2019");
        menuPrincipal();
        escenarioPrincipal.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public void menuPrincipal(){
        try{
            MenuPrincipalController menuPrincipal = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml",606,225);
            menuPrincipal.setEscenarioPrincipal(this);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaGrado(){
        try{
            GradoController gradoController = (GradoController) cambiarEscena("GradoView.fxml",608,416);
            gradoController.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    
    
    
    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws Exception{
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
        escena = new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        
        return resultado; 
    }
    
    
    
}
