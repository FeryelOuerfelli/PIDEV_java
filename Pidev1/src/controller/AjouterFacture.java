package controller;


import entities.Facture;
import entities.Pharmacie;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.FactureService;
import utils.MyConnection;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;


public class AjouterFacture implements Initializable{

    @FXML
    private Button btn_submit;

    @FXML
    private DatePicker tf_date;

    @FXML
    private TextField tf_montant;

    @FXML
    private TextField  tf_num_facture;
     @FXML
    private TextField  tf_image_signature;
     
 @FXML
   private TextField  tf_etat;


 @FXML
   private TextField tf_ordonnance ;
        @FXML
        private Button browseButtonImage;
    private File selectedFile;
    private Image selectedImage;
 
    private final Connection cnx;
    private PreparedStatement ste;
    
    private int pharmacieId;
       @FXML
        private AnchorPane myAnchorPane;

            @FXML
    private void handleBrowseButtonAction(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
    selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            selectedImage = new Image(selectedFile.toURI().toString());
            ImageView imageView = new ImageView(selectedImage);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(165);
            imageView.setFitHeight(130);
            myAnchorPane.getChildren().add(imageView);
        }
    }

    public AjouterFacture() {
        MyConnection bd=MyConnection.getInstance();
        cnx=bd.getCnx();
    }
    @Override
     public void initialize(URL url, ResourceBundle rb) {
        tf_date.setValue(LocalDate.now());

    }
  
     public void setPharmacieId(Pharmacie p){
        this.pharmacieId = p.getIdph();
    }

    @FXML
    void Valider(ActionEvent event)  {
               SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        

     try{
         
         if(tf_num_facture.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez une date de debut");
            alert.showAndWait();  
        }
        
                else{  
             try {
               
                 Date date = dateFormat.parse(tf_date.getValue().toString());
                     float montant = Float.parseFloat(tf_montant.getText());
                    String num_facture = tf_num_facture.getText();
                      String image_signature = null;
                       String etat =  tf_etat.getText();
                      int ordonnance = Integer.parseInt(tf_ordonnance.getText());
                      try {
        image_signature = selectedFile.getName();
        
    } catch (NullPointerException ex) {
        System.out.println("Erreur : Fichier image introuvable");
        return;
    }

                 System.out.println("date controller"+date);
                 
                Facture f = new Facture(date,num_facture,montant,image_signature,etat,ordonnance,pharmacieId);
                FactureService factureService = new FactureService();
                factureService.ajouterFacture(f);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("AJOUT AVEC SUCCES");
                alert.setHeaderText(null);
                alert.setContentText("Facture ajouté avec succès");
                alert.showAndWait();

             } catch (ParseException ex) {
                 System.out.println(ex.getMessage());
             }
   

        }}

        catch(RuntimeException ex)
        {
             Alert alert = new Alert(Alert.AlertType.ERROR," Les informations sont Invalides ou incompletes Veuillez les verifiers ",ButtonType.CLOSE);
            alert.showAndWait();
        }
        String title = "succes ";
        String message = "facture ajouté avec succes";
    }
    @FXML
    private void AfficherFactureAction(ActionEvent event) throws IOException {

    
    Stage stageEdit = (Stage)((Node)event.getSource()).getScene().getWindow();

    stageEdit.hide();
    }

    void setIDf(Facture selectedP) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


  

}
