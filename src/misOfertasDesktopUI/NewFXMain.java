/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misOfertasDesktopUI;

import MisOfertasDesktopEntities.Producto;
import MisOfertasDesktopEntities.Rubro;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import misOfertasDesktopController.RubroJpaController;
import misOfertasDesktopDAO.ProductoDAO;
import misOfertasDesktopDAO.RubroDAO;
//import misOfertasDesktopService.*;

/**
 *
 * @author David
 */
public class NewFXMain extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction((ActionEvent event) -> {
            ProductoDAO pDAO = new ProductoDAO();
            RubroDAO rDAO = new RubroDAO();
            Producto p = new Producto();
            Rubro r = new Rubro();
            r = rDAO.getRubroById(1L);
            Long id = pDAO.getMaxId();
            p.setProductoId(++id);
            p.setEsPerecible(1);
            p.setFechaVencimiento("30/04/2018");
            p.setIsActive("1");
            p.setNombreProducto("Test1.4");
            p.setRubro(r);
            try {
                pDAO.addProducto(p);
            } catch (Exception ex) {
                Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
