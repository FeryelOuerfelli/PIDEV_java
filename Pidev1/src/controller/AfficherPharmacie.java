package controller;

import entities.Pharmacie;
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
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.PharmacieService;
import javafx.scene.image.Image;


/**
 * FXML Controller class
 *
 * @author rouai
 */
public class AfficherPharmacie implements Initializable {
    @FXML
    private TableView<Pharmacie> table_pharmacie;
      @FXML
    private TableColumn<Pharmacie, String> nom;   
    @FXML
    private TableColumn<Pharmacie, String> adresse; 
      @FXML
    private TableColumn<Pharmacie, String> gouvernorat;   
    @FXML
    private TableColumn<Pharmacie, String> num_tel; 
   
        
    @FXML
    private TableColumn<Pharmacie, String> horaire; 
    @FXML
    private TableColumn<Pharmacie, String> etat;  
     @FXML
    private TableColumn<Pharmacie, String> services; 
      @FXML
    private TableColumn<Pharmacie, Button> editCol;

    @FXML
    private TableColumn<Pharmacie, Button> deleteCol; 
        @FXML
    private ImageView img;
  
     

    @FXML
    private TextField tf_rechercher_pharmacie;

        /**
     * Initializes the controller class.
     */
    
    ObservableList<Pharmacie> listPharmacie =  FXCollections.observableArrayList();
    PharmacieService pharmacieService = new PharmacieService();
    Pharmacie p = new Pharmacie();
    public List<Pharmacie> pharmacies;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            
            
        listPharmacie = pharmacieService.showPharmacie();
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        gouvernorat.setCellValueFactory(new PropertyValueFactory<>("gouvernorat"));
        num_tel.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
        horaire.setCellValueFactory(new PropertyValueFactory<>("horaire"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        services.setCellValueFactory(new PropertyValueFactory<>("services"));


        table_pharmacie.getItems().setAll(listPharmacie);
//           
    }   catch (SQLException ex) { 
            System.out.println(ex);
        } 
   
    
    }    
       private void OnClickedPrint(ActionEvent event) {
         PrinterJob job = PrinterJob.createPrinterJob();
       
        Node root= this.table_pharmacie;
       
       
     if(job != null){
     job.showPrintDialog(root.getScene().getWindow()); // Window must be your main Stage
     Printer printer = job.getPrinter();
     PageLayout pageLayout = printer.createPageLayout(Paper.A3, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);
     boolean success = job.printPage(pageLayout, root);
     if(success){
        job.endJob();
     }
     }
    }
     @FXML
    private void EditPharmacie(ActionEvent event) throws IOException {
        Pharmacie selectedForEdit = table_pharmacie.getSelectionModel().getSelectedItem();

        
       
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/EditPharmacie.fxml"));
             Parent parent = loader.load();
            EditPharmacie controller = (EditPharmacie) loader.getController();
            controller.inflateUI(selectedForEdit);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Editer Pharmacie");
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
     private void supprimerPharmacie(ActionEvent event) throws IOException, SQLException {
         System.out.println("btn clicked");
        Pharmacie selectedForDelete = table_pharmacie.getSelectionModel().getSelectedItem();
        PharmacieService pharmacieService= new PharmacieService();
        System.out.println("selected pharmacie id"+selectedForDelete.getIdph());
        pharmacieService.supprimerPharmacie(selectedForDelete.getIdph());
        loadData();
        handleRefresh(new ActionEvent());
        loadData();
        String title = "succes delet ";
        String message = "pharmacie supprimé avec succes";
        //TrayNotification tray = new TrayNotification();
        //tray.setTitle(title);
       // tray.setMessage(message);
        //tray.setNotificationType(NotificationType.SUCCESS);
       // tray.showAndWait();
    }


    @FXML
    private void rechercherPharmacie() {
        System.out.println("");
        try {
            pharmacies = new PharmacieService().search(tf_rechercher_pharmacie.getText());
            listPharmacie.clear();
            table_pharmacie.getItems().setAll(pharmacies);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
    //load list
    
       private void loadData() throws SQLException {
        listPharmacie.clear();
         try {
            
        listPharmacie = pharmacieService.showPharmacie();
         nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        gouvernorat.setCellValueFactory(new PropertyValueFactory<>("gouvernorat"));
        num_tel.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
        horaire.setCellValueFactory(new PropertyValueFactory<>("horaire"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
               services.setCellValueFactory(new PropertyValueFactory<>("services"));

            
        table_pharmacie.getItems().setAll(listPharmacie);
//           
    }   catch (SQLException ex) { 
             System.out.println(ex);
        }
    }
         private void handleRefresh(ActionEvent event) throws SQLException {
        loadData();
        
    }
         
    @FXML
    private void ajouterPharmacieAction(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/AjouterPharmacie.fxml"));
        Parent parent = loader.load();
        AjouterPharmacie controller = (AjouterPharmacie) loader.getController();
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Ajouter pharmacie");
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
        Pharmacie selectedP = table_pharmacie.getSelectionModel().getSelectedItem();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/Ajouterfacture.fxml"));
        Parent parent = loader.load();
        AjouterFacture controller = (AjouterFacture) loader.getController();
        controller.setPharmacieId(selectedP);
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
