package controller;

import entities.Facture;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.Date;
import java.util.HashMap;
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
import javafx.print.PageOrientation;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.FactureService;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import utils.MyConnection;


/**
 * FXML Controller class
 *
 * @author feryel
 */
public class AfficherFacture implements Initializable {
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
    private Tab factures;

    @FXML
    private Tab flous;

    @FXML
    private Tab home;
        @FXML
    private Label labelstat;

    @FXML
    private Label labelstat2;
    
    @FXML
    private Label labelstat3;
    @FXML
    private TableView<Facture> table_Facture;
      @FXML
    private TableColumn<Facture, Integer > idf;   
    @FXML
    private TableColumn<Facture, Float> montant; 
      @FXML
    private TableColumn<Facture, Date> date;   
          @FXML
    private TableColumn<Facture, Integer> idph; 
    @FXML
    private TableColumn<Facture, String> image_signature;   
    @FXML
    private TableColumn<Facture, String> num_facture; 
     @FXML
    private TableColumn<Facture, String>etat; 
      @FXML
    private Button pdffacturebtn;
    @FXML
    private Button pdffacture;
    @FXML
        private BarChart<String, Number> barChart;    
      @FXML
        private LineChart<String, Float> LineChart; 
      @FXML
    private PieChart chart;
 

       private  Connection cnx;
    private PreparedStatement  preparedStatement;
   
  
  
      public AfficherFacture() {
        MyConnection bd=MyConnection.getInstance();
        cnx=bd.getCnx();
    

      }
        /**
     * Initializes the controller class.
     */
    
    ObservableList<Facture> listFacture =  FXCollections.observableArrayList();
    FactureService FactureService = new FactureService();
    Facture f = new Facture();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
try {
            String queryChartData = "SELECT date, COUNT(*) AS nbfactures FROM facture GROUP BY date";
             preparedStatement = cnx.prepareStatement(queryChartData);
            ResultSet rs =  preparedStatement.executeQuery();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("factures par jour");
            while (rs.next()) {
                String date = rs.getString("date");
                int nbfactures = rs.getInt("nbfactures");
                series.getData().add(new XYChart.Data<>(date, nbfactures));
            }
            barChart.getData().add(series);
           preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
try {
            String queryChartData = "SELECT MONTH(date) as mois , SUM(montant) AS total FROM facture GROUP BY date";

             preparedStatement = cnx.prepareStatement(queryChartData);
            ResultSet rs =  preparedStatement.executeQuery();
            XYChart.Series <String, Float> series = new XYChart.Series<>();
            series.setName("Croissance du revenu");
              DateFormatSymbols dfs = new DateFormatSymbols();
    String[] months = dfs.getMonths();

    // Mapper les numéros de mois aux noms de mois correspondants
    HashMap<Integer, String> monthNames = new HashMap<>();
    for (int i = 0; i < 12; i++) {
        monthNames.put(i+1, months[i]);
    }
            while (rs.next()) {
                int mois = rs.getInt("mois");
                float total = rs.getInt("total");
                        String monthName = monthNames.get(mois);

                series.getData().add(new XYChart.Data<>(monthName, total));
            }
            LineChart.getData().add(series);
           preparedStatement.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
           ObservableList<PieChart.Data> pieChartData = null;
            try {
            pieChartData = FXCollections.observableArrayList(
            new PieChart.Data("Nombre des Factures Payées " , FactureService.CalculP()),
           new PieChart.Data("Nombre de Factures Non Payées",  FactureService.CalculNonP()));
         // pieChartData.get(0).getNode().setStyle("-fx-pie-color: #1E90FF;"); // Payées 
           //pieChartData.get(1).getNode().setStyle("-fx-pie-color: #87CEFA;"); // Non payées 


            } catch (SQLException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            chart.setData(pieChartData);
      // final Label caption = new Label("Heloooo");
        //caption.setTextFill(Color.BLACK);
      //  caption.setStyle("-fx-font: 24 arial;");            
            for (PieChart.Data d : chart.getData()) {
    d.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
        labelstat.setText(String.format(" %s : %d", d.getName(), (int)d.getPieValue()));
    });
                   //  caption.setTranslateX(e.getSceneX());
               // caption.setTranslateY(e.getSceneY());
               // caption.setText(String.valueOf(data.getPieValue()) + "%");
 }
        try {
            

        listFacture = FactureService.showFacture();
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        montant.setCellValueFactory(new PropertyValueFactory<>("montant"));
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
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        montant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
       
            
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
