package com.example.onlinefood;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Formatter;
import java.util.HashMap;

public class MainController {
    private HashMap<String, String> mapping = new HashMap<>();
    private Parent parent;
    private Scene scene;
    private Stage stage;
    @FXML
    TextField username, password;

    @FXML
    Label errorUsername, errorPass;

    @FXML
    void initialize(){
        addToMap();
    }

    @FXML
    void signup(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("signup.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void login(ActionEvent event) throws IOException {
        String u = username.getText();
        String p = password.getText();

        if(mapping.containsKey(u)) {
            errorUsername.setText("");
            String mPass = mapping.get(u);
            if(mPass.equals(p)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("home2.fxml"));
                parent = loader.load();

                HomeController home = loader.getController();
                home.initialize(u);

                scene = new Scene(parent);
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
            else {
                errorPass.setText("Incorrect Password");
            }
        }
        else{
            errorUsername.setText("Username not found");
        }
    }

    private void addToMap(){
        try{
            FileReader f = new FileReader("C:/My Project/OnlineFoodDelivariApp/src/main/java/com/example/onlinefood/users.txt");
            File file= new File("C:/My Project/OnlineFoodDelivariApp/src/main/java/com/example/onlinefood/users.txt");

            if(file.exists() && file.length() != 0){
                BufferedReader reader = new BufferedReader(f);
                String line;
                while ((line = reader.readLine()) != null) {
                    String [] parts = line.split("#");
                    mapping.put(parts[3], parts[4]);
                }
                reader.close();
            }
        }
        catch (Exception e){
            System.out.println("file not found");
        }
    }
}
