package com.login;

import com.dao.DaoImpl;
import com.model.Users;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    DaoImpl dao = new DaoImpl();
    
    @FXML
    private Label lbl;
    @FXML
    private ImageView img;
    @FXML
    private TextField loginTxt;
    @FXML
    private PasswordField pwdTxt;
    @FXML
    private Label crtAcc;
    @FXML
    private Button loginBtn;
    @FXML
    private Label warningLBL;
    @FXML
    private ImageView warningImg1;
    @FXML
    private ImageView warningImg2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void crtAccAction(MouseEvent event) throws IOException {
        Stage stage = new Stage();
       stage.setTitle("Create Account");
       stage.getIcons().add(new Image("/com/image/createAccaunt.png"));
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/createAccaunt/createAccount.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void loginBtnAction(ActionEvent event) {
  String username = loginTxt.getText().trim();
        String password = pwdTxt.getText().trim();
        if (username.equalsIgnoreCase("") || password.equalsIgnoreCase("")) {
            warningLBL.setText("fill in boxes!");
        } else {
            Users user = dao.checkUser(username, password);
            if (user == null) {
                warningLBL.setText("Username or Password is wrong!");
            } else {
                try {
                    Stage stage = new Stage();
                    stage.setTitle("Main Page");
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/page/page.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    Stage oldStage =  (Stage) loginBtn.getScene().getWindow();
                    oldStage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        

    }
    }

    @FXML
    private void checkLogin(KeyEvent event) {
       warningLBL.setText("");
        if (loginTxt.getText().trim().equalsIgnoreCase("")) {
             warningImg1.setVisible(true);
        } else {
           warningImg1.setVisible(false);
            if (loginTxt.getText().trim().contains(",")) {
                warningLBL.setText("Login contains ' , ' ");
                warningImg1.setVisible(true);
            }

        }
    
    
    
    }

    @FXML
    private void checkPassword(KeyEvent event) {
     warningLBL.setText("");
        if (pwdTxt.getText().trim().equalsIgnoreCase("")) {
            warningImg2.setVisible(true);
        } else {
            warningImg2.setVisible(false);
            if (pwdTxt.getText().trim().contains(",")) {
                warningLBL.setText("password contains ' , ' ");
                warningImg2.setVisible(true);
            }

        }
    }

}
