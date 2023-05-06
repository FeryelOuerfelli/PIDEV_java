package controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Pharmacie;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.PharmacieService;
import utils.MyConnection;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author feryel
 */
public class AfficherPharmacie implements Initializable {
    
    @FXML
    private Button btnfac;

    @FXML
    private Button btnpharma;
    
    
    
    @FXML
    void btnfacaction(ActionEvent event) throws IOException {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/AfficherFacture.fxml"));
        Parent parent = loader.load();
        AfficherFacture controller = (AfficherFacture) loader.getController();
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Afficher Facture");
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
    void btnpharmaaction(ActionEvent event) throws IOException {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/AfficherPharmacie.fxml"));
        Parent parent = loader.load();
        AfficherPharmacie controller = (AfficherPharmacie) loader.getController();
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Afficher Pharmacie");
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
    private Button  printbtn;
      @FXML
    private Button  downloadbtn;
        @FXML
    private ImageView img;
    private final Connection cnx;
    private PreparedStatement prepared;
    private  ResultSet resultat;
  
     

    @FXML
    private TextField tf_rechercher_pharmacie;
      @FXML
        private BarChart<String, Number> barChart;    

    private PreparedStatement  preparedStatement;
  

    public AfficherPharmacie() {
        MyConnection bd=MyConnection.getInstance();
        cnx=bd.getCnx();
    }
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
    
        } 
                
       catch (SQLException ex) { 
            System.out.println(ex);
        }
    }
   
    
     
     @FXML
       private void OnClickedPrint(ActionEvent event) {
         PrinterJob job = PrinterJob.createPrinterJob();
       
        Node root= this.table_pharmacie;
       
       
     if(job != null){
     job.showPrintDialog(root.getScene().getWindow()); // Window must be your main Stage
     Printer printer = job.getPrinter();
     PageLayout pageLayout = printer.createPageLayout(Paper.A3, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM );
     boolean success = job.printPage(pageLayout, root);
     if(success){
        job.endJob();
     }
     }
    }
       @FXML
 public void download() throws SQLException  {
    FileChooser fileChooser = new FileChooser();// Create a new FileChooser object
    fileChooser.setTitle("Télecharger La liste des Pharmacies");// Set the title of the dialog box       
    fileChooser.setInitialFileName("Pharmacies.xlsx");// Set the default file name
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");  // Set the file extension filter
    fileChooser.getExtensionFilters().add(extFilter);
    File file = fileChooser.showSaveDialog(new Stage());// Get the file path from the user     
    try {
        createExcelFile(file.getAbsolutePath()); // Call the method to create the Excel file and pass the file path
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
}
private void createExcelFile(String filePath) throws IOException, SQLException {

    Workbook workbook = new XSSFWorkbook(); // Create a new sheet 
    Sheet sheet = workbook.createSheet("Pharmacies"); // Create a new sheet
    Row headerRow = sheet.createRow(0); // Create a header row
        headerRow.createCell(0).setCellValue("Nom");
        headerRow.createCell(1).setCellValue("Adresse");
        headerRow.createCell(2).setCellValue("Gouvernorat");
        headerRow.createCell(3).setCellValue("Numéro de télephone");
        headerRow.createCell(4).setCellValue("Email");
        headerRow.createCell(5).setCellValue("Etat");
        headerRow.createCell(6).setCellValue("Horaire");
        headerRow.createCell(7).setCellValue("Services");
                
        // Add data to the sheet
        int rowNum = 1;
        List<Pharmacie> listPharmacie ;
        PharmacieService PharmacieService = new PharmacieService();
        listPharmacie= PharmacieService.showPharmacie();
        for (Pharmacie ph : listPharmacie) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(ph.getNom());
            row.createCell(1).setCellValue(ph.getAdresse());
            row.createCell(2).setCellValue(ph.getGouvernorat());
            row.createCell(3).setCellValue(ph.getNum_tel());
            row.createCell(4).setCellValue(ph.getEmail());
            row.createCell(5).setCellValue(ph.getEtat());
            row.createCell(6).setCellValue(ph.getHoraire());
            row.createCell(7).setCellValue(ph.getServices());
           
        }   // Auto-size the columns
        for (int i = 0; i < 8; i++) {
            sheet.autoSizeColumn(i);
        }  
    try (FileOutputStream fileOut = new FileOutputStream(filePath) // Save the workbook to the specified file path
    ) {
        workbook.write(fileOut);
        fileOut.close();
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
    
