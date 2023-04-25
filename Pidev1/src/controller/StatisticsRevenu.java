/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author Feryel
 */
    



import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import services.PharmacieService;



public class StatisticsRevenu  implements Initializable {
      @FXML
    private BarChart<?, ?> barchart;

    @FXML
    private AnchorPane main;
     private  Connection cnx;
    private PreparedStatement  preparedStatement;

    /**
     * Initializes the controller class.
     */
    
      public void Revenu() throws SQLException {
         String sql = "SELECT date , SUM(montant) FROM facture GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 8";
        
      try {
          XYChart.Series chartData = new XYChart.Series();
            preparedStatement = cnx.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
     while (resultSet.next()) {
         chartData.getData().add(new XYChart.Data(resultSet.getString(1),resultSet.getFloat(2)));

      }
      }
      catch (Exception e) {
      e.printStackTrace();}


         
}
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
          try {
              Revenu();
          } catch (SQLException ex) {
              Logger.getLogger(StatisticsRevenu.class.getName()).log(Level.SEVERE, null, ex);
          }
        
    }  
    
}

    
