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


import com.jfoenix.controls.JFXButton;

import java.net.URL;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.NumberAxisBuilder;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.swing.JTable;
import services.FactureService;
import services.PharmacieService;


/**
 * FXML Controller class
 *
 * @author HP-PC
 */
public class Statistics implements Initializable {
    

    @FXML
    private TabPane admintabpane;
    @FXML
    private Tab home;
    @FXML
    private Tab statistique;
    @FXML
    private Tab tabgerercompte;
    @FXML
    private Tab tabptsfidelite;
    @FXML
    private Tab tabpromotion;
    @FXML
    private Tab tabparking;
    @FXML
    private JFXButton btngerercompte;
    @FXML
    private JFXButton ptsfidelite;
    @FXML
    private JFXButton btnpromotion;
    @FXML
    private JFXButton btnparking;
    @FXML
    private PieChart chart;
    @FXML
    private Label labelstat;
    @FXML
    private Button imprimer;

    /**
     * Initializes the controller class.
     */
    @Override
 public void initialize(URL url, ResourceBundle rb) {
        FactureService FactureService = new FactureService();
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
}
}