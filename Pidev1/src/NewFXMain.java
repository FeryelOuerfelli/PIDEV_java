/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import controller.AfficherFacture;
import controller.AfficherPharmacie;
import controller.DashboardAdmin;
import controller.AjouterFacture;
import controller.AjouterPharmacie;
import controller.Patient;
import controller.Statistics;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author feryel
 */
public class NewFXMain extends Application {
    
    @Override
    public void start(Stage primaryStage){
       /* try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./gui/MedecinDashbord.fxml"));
        AnchorPane root = loader.load();
        MedecinDashbordController controller = loader.getController();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }*/
try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("./gui/AfficherPharmacie.fxml"));
            AnchorPane root = loader.load();
           AfficherPharmacie controller = loader.getController();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            
        }catch(IOException ex){
            System.err.println(ex.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}