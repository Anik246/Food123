package com.example.onlinefood;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.Random;

import java.io.*;
import java.util.Scanner;







public class Checkout {

    private Parent parent;
    private Scene scene;
    private Stage stage;
    private Socket client;
    private BufferedWriter writer;
    private BufferedReader reader;
    private User user;
    int OTP;

    @FXML
    Label finalPrice, userName, fileName,labelText;
@FXML
TextField phone,verifyotp;




  public void initialize(Socket sc, String user, String price) throws IOException {
        client = sc;
        userName.setText(user);
        userName.setVisible(false);
        finalPrice.setText(price);

        writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

    }
    public void back(MouseEvent mouseEvent) throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("Cart.fxml"));
        parent = loader.load();

        stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void credit(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("signup2.fxml"));
        parent = loader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }
    int count=0;
    public void bkash(ActionEvent event) {

        labelText.setText("Enter your bkash number");
count++;

    }


    public void nagad(ActionEvent event) {
        labelText.setText("Enter your nagad number");

        count++;
    }

    public void cashOnDelivary(ActionEvent event) {

        count++;

    }
    public void proceed(ActionEvent event) throws IOException {
        try{
            // Construct data
            String apiKey = "apikey=" + "NjIzNDRlMzY2MTMwNGE2ZTU1NGY3NjsdksMjQ4NDQ=";
            Random rand= new Random();
          //  OTP=rand.nextInt(999999);
            String name=labelText.getText();
          //  String message = "&message=" + "OTP From"+name+" is"+OTP;
            String sender = "&sender=" +labelText.getText();
            String numbers = "&numbers=" +phone.getText();

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
            String data = apiKey + numbers + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();
            Alert a= new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("OTP send successfully");
            a.showAndWait();
            // return stringBuffer.toString();
        } catch (Exception e) {
            Alert a= new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("OTP send successfully");
            a.showAndWait();


        }
    }
String str="123456";
    public void verify(ActionEvent event) {
        if(verifyotp.getText().length()==str.length()){
            Alert a= new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("your payment is successfully done");
            a.showAndWait();
        }
        else {
            Alert a= new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Wrong OTP");
            a.showAndWait();
        }
    }
    public void orderPlaceBtn(ActionEvent actionEvent) throws IOException{



        if(count != 0 ){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("review.fxml"));
            parent = loader.load();
            Review r= loader.getController();
            try{
                FileReader f = new FileReader("C:/My Project/OnlineFoodDelivariApp/src/main/java/com/example/onlinefood/address.txt");
                File file= new File("C:/My Project/OnlineFoodDelivariApp/src/main/java/com/example/onlinefood/address.txt");

                if(file.exists() && file.length() != 0){
                    BufferedReader reader = new BufferedReader(f);
                    String line;
                    while ((line = reader.readLine()) != null) {

                     r.initialize(line);
                    }
                    reader.close();
                }
            }
            catch (Exception e){
                System.out.println("file not found");
            }

            stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        }

         else{
labelText.setText("invalid");
            Alert a= new Alert(Alert.AlertType.WARNING);
            a.setTitle("Please select payment option !!!!");
            a.showAndWait();

        }
    }


}

