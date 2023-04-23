package controller;

import entities.Facture;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.FactureService;

/**
 * FXML Controller class
 *
 * @author rouai
 */
public class AfficherFacture implements Initializable {
    @FXML
    private TableView<Facture> table_Facture;
      @FXML
    private TableColumn<Facture, Integer > idf;   
    @FXML
    private TableColumn<Facture, Float> montant; 
      @FXML
    private TableColumn<Facture, Date> date;   
    @FXML
    private TableColumn<Facture, String> image_signature;   
    @FXML
    private TableColumn<Facture, Integer> num_facture; 
     @FXML
    private TableColumn<Facture, String>etat; 
   
  
  
     


        /**
     * Initializes the controller class.
     */
    
    ObservableList<Facture> listFacture =  FXCollections.observableArrayList();
    FactureService FactureService = new FactureService();
    Facture f = new Facture();
    public List<Facture> factures;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            
            
        listFacture = FactureService.showFacture();
        montant.setCellValueFactory(new PropertyValueFactory<>("Montant"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
         etat.setCellValueFactory(new PropertyValueFactory<>("etat"));

       


        table_Facture.getItems().setAll(listFacture);
//           
    }   catch (SQLException ex) { 
            System.out.println(ex);
        } 
    
    }    
    
       @FXML
    private void EditFacture(ActionEvent event) throws IOException {
        Facture selectedForEdit = table_Facture.getSelectionModel().getSelectedItem();
        
       
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/EditFacture.fxml"));
            Parent parent = loader.load();

            EditerFacture editf = (EditerFacture) loader.getController();
            editf.inflateUI(selectedForEdit);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Edit Facture");
            stage.setScene(new Scene(parent));
            stage.show();
            stage.setOnHiding((e) -> {
                try {
                    handleRefresh(new ActionEvent());
                    loadData();
                   
                    
                }catch (SQLException ex) {
                    System.out.println(ex);
                   // Logger.getLogger("gg").log(Level.SEVERE, null, ex);
                }
            });
            
            // Hide this current window (if this is what you want)
           // ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    } 
    @FXML
     private void supprimerFacture(ActionEvent event) throws IOException, SQLException {
         System.out.println("btn clicked");
        Facture selectedForDelete = table_Facture.getSelectionModel().getSelectedItem();
        FactureService FactureService= new FactureService();
        System.out.println("selected Facture id"+selectedForDelete.getIDf());
        FactureService.supprimerFacture(selectedForDelete.getIDf());
        loadData();
        handleRefresh(new ActionEvent());
        loadData();
        String title = "succes delet ";
        String message = "Facture supprimé avec succes";
        //TrayNotification tray = new TrayNotification();
        //tray.setTitle(title);
       // tray.setMessage(message);
        //tray.setNotificationType(NotificationType.SUCCESS);
       // tray.showAndWait();
    }


    //load list
    
       private void loadData() throws SQLException {
        listFacture.clear();
         try {
            
        listFacture = FactureService.showFacture();
        idf.setCellValueFactory(new PropertyValueFactory<>("ID"));
        montant.setCellValueFactory(new PropertyValueFactory<>("Montant"));
        date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        image_signature.setCellValueFactory(new PropertyValueFactory<>("Signature"));
        num_facture.setCellValueFactory(new PropertyValueFactory<>("numéro facture"));
       
            
        table_Facture.getItems().setAll(listFacture);
//           
    }   catch (SQLException ex) { 
             System.out.println(ex);
        }
    }
         private void handleRefresh(ActionEvent event) throws SQLException {
        loadData();
        
    }
         
    @FXML
    private void ajouterFactureAction(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/AjouterFacture.fxml"));
        Parent parent = loader.load();
        AjouterFacture controller = (AjouterFacture) loader.getController();
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Ajouter Facture");
        stage.setScene(new Scene(parent));
        stage.show();
                 stage.setOnHiding((e) -> {
                try {
                    handleRefresh(new ActionEvent());
                    loadData();
                   
                    
                }catch (SQLException ex) {
                    System.out.println(ex);
                   // Logger.getLogger("gg").log(Level.SEVERE, null, ex);
                }
            });

    }

    @FXML
    void ajouterfacture(ActionEvent event) throws IOException {
        Facture selectedP = table_Facture.getSelectionModel().getSelectedItem();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/Ajouterfacture.fxml"));
        Parent parent = loader.load();
        AjouterFacture controller = (AjouterFacture) loader.getController();
        controller.setIDf(selectedP);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Ajouter facture");
        stage.setScene(new Scene(parent));
        stage.show();
                 stage.setOnHiding((e) -> {
                try {
                    handleRefresh(new ActionEvent());
                    loadData();
                   
                    
                }catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                   // Logger.getLogger("gg").log(Level.SEVERE, null, ex);
                }
            });
    }
   
        
    }
