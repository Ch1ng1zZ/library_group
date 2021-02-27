package com.createAccaunt;

import com.dao.DaoImpl;
import com.model.Users;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class CreateAccountController implements Initializable {
 DaoImpl dao = new DaoImpl();
    @FXML
    private Button saveBtn;
    @FXML
    private Label nameLbl;
    @FXML
    private Label surnameLbl;
    @FXML
    private Label usernameLbl;
    @FXML
    private Label passwordLbl;
    @FXML
    private Label cnfrmPasswordLbl;
    @FXML
    private Label mailLbl;
    @FXML
    private Label adressLbl;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField surnameTxt;
    @FXML
    private TextField usernameTxt;
    @FXML
    private TextField passwordTxt;
    @FXML
    private TextField cnfrmPasswordTxt;
    @FXML
    private TextField mailTxt;
    @FXML
    private TextField adressTxt;
    @FXML
    private Button cancelBtn;
    @FXML
    private Label LBL;
    @FXML
    private Label warningLbl;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        
        
    }    

    @FXML
    private void saveBtnAction(ActionEvent event) {
     String name = nameTxt.getText().trim();
        String surname = surnameTxt.getText().trim();
        String username = usernameTxt.getText().trim();
        String password = passwordTxt.getText().trim();
        String confirmPassword = cnfrmPasswordTxt.getText().trim();
        String mail = mailTxt.getText().trim();
        String address = adressTxt.getText().trim();
        
        if(name.equalsIgnoreCase("")||surname.equalsIgnoreCase("")||
        username.equalsIgnoreCase("")||password.equalsIgnoreCase("")||confirmPassword.equalsIgnoreCase("")||mail.equalsIgnoreCase("")||address.equalsIgnoreCase("")){
            warningLbl.setText("Fill in all boxes");
        }
        else{
            if(password.equalsIgnoreCase(confirmPassword)){
                if(dao.checkUserbyUsername(username)){
                    warningLbl.setText("Username is already usining");
                }
                else{
                    Users user = new Users(0, name, surname, username, password, mail, address);
                    boolean isAdded = dao.createAccount(user);
                    if(isAdded){
                        warningLbl.setText("Account is created succesfuly");
                        Stage oldStage = (Stage) saveBtn.getScene().getWindow();
                        oldStage.close();
                    }else{
                        warningLbl.setText("Account doesn't created");
                    }
                }
            }
            else{
                warningLbl.setText("passwords aren't match");
            }
        }
    
    
    
    
    }

    @FXML
    private void cancelBtnAction(ActionEvent event) {
      nameTxt.setText("");
        surnameTxt.setText("");
        usernameTxt.setText("");
        passwordTxt.setText("");
        cnfrmPasswordTxt.setText("");
        mailTxt.setText("");
        adressTxt.setText("");
    
    
    
    }
    
}
