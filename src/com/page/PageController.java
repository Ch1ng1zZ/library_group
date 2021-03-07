package com.page;

import com.dao.DaoImpl;
import com.login.LoginController;
import com.model.Book;
import com.model.Users;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class PageController implements Initializable {

    private Users user;
    DaoImpl dao = new DaoImpl();
    Integer selectedId = 0;

    @FXML
    private ToggleGroup a;
    @FXML
    private TextField bookNameTxt;
    @FXML
    private Label nameLbl;
    @FXML
    private Label authorLbl;
    @FXML
    private TextField authorTxt;
    @FXML
    private TextField pageCountTxt;
    @FXML
    private TextField priceTxt;
    @FXML
    private Label PageCountLbl;
    @FXML
    private Label priceLbl;
    @FXML
    private Label languageLbl;
    @FXML
    private ComboBox<String> LngComboBox;
    @FXML
    private Button add1;
    @FXML
    private Label themeLbl;
    @FXML
    private ComboBox<String> ThmComboBox;
    @FXML
    private Button add2;
    @FXML
    private Button saveBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Label searchLbl;
    @FXML
    private TextField searchTxt;
    @FXML
    private Label priceFilterLbl;
    @FXML
    private Label minLbl1;
    @FXML
    private TextField min1Txt;
    @FXML
    private TextField max1Txt;
    @FXML
    private Label maxLbl1;
    @FXML
    private Label pageCountFilter;
    @FXML
    private Button filter1Btn;
    @FXML
    private Label minLbl2;
    @FXML
    private Label maxLbl2;
    @FXML
    private TextField min2Txt;
    @FXML
    private TextField max2Txt;
    @FXML
    private Button filter2Btn;
    @FXML
    private Button clrFilterBtn;
    @FXML
    private RadioButton soldBtn;
    @FXML
    private RadioButton unsoldBtn;
    @FXML
    private RadioButton allBtn;
    @FXML
    private Button buyBtn;
    @FXML
    private Button filterBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private TableView<Book> tableV;
    @FXML
    private Label welcomeLbl;
    @FXML
    private Label logOut;
    @FXML
    private Label hideBtn;
    @FXML
    private Label showBtn;
    @FXML
    private Label warningLbl;
    @FXML
    private TableColumn<Book, Integer> IdColumn;
    @FXML
    private TableColumn<Book, String> nameColumn;
    @FXML
    private TableColumn<Book, String> themeColumn;
    @FXML
    private TableColumn<Book, String> AuthorColumn;
    @FXML
    private TableColumn<Book, Integer> pageCountColumn;
    @FXML
    private TableColumn<Book, Double> priceColumn;
    @FXML
    private TableColumn<Book, String> languageColumn;
    @FXML
    private TableColumn<Book, String> statusColumn;
    Users u = new Users();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadLanguageCB();
        loadThemeCB();
        tableV.setVisible(false);
        welcomeLbl.setText("Welcome, " + LoginController.username2);
        loadColumn();
        loadRows();
     hideBtn.setVisible(false);
    }
    
    public void setUser(Users selectedUser) {
        this.user = selectedUser;
    }

    @FXML
    private void buyBtnActon(ActionEvent event) {
        boolean isUpdated = dao.updateBookByStatus(selectedId);
        if (isUpdated) {
            refresh();
            warningLbl.setText("Status changed succesfully!");
        } else {
            warningLbl.setText("status doesn't changed!");
        }
    }

    @FXML
    private void filterBtnAction(ActionEvent event) {
        if (soldBtn.isSelected()) {
            List<Book> soldBook = dao.filterBookByStatus("Sold");
            tableV.getItems().clear();
            tableV.getItems().addAll(soldBook);
        } else if (unsoldBtn.isSelected()) {

            List<Book> unSoldBook = dao.filterBookByStatus("Not Sold");
            tableV.getItems().clear();
            tableV.getItems().addAll(unSoldBook);
        } else if (allBtn.isSelected()) {
            refresh();
        }
    }

    @FXML
    private void deleteBtnAction(ActionEvent event) {
        if (dao.deleteBook(selectedId)) {
            refresh();
            warningLbl.setText("Deleted succesfully!");
        } else {
            warningLbl.setText("Not deleted!");
        }
    }

    public void refresh() {
        tableV.getItems().clear();
        tableV.getItems().addAll(dao.getAllBooks());
    }

    @FXML
    private void addLanguageBtn(ActionEvent event) {
        String newLanguage = JOptionPane.showInputDialog(null, "New Language");
        if (newLanguage.equalsIgnoreCase("")) {
            warningLbl.setText("Language is empty!");
        } else {
            if (LngComboBox.getItems().contains(newLanguage)) {
                warningLbl.setText("Not added! Language already exist");
            } else {
                if (LngComboBox.getItems().add(newLanguage)) {
                    warningLbl.setText("New Language successfully added!");
                }
            }
        }

    }

    @FXML
    private void languageComboboxAction(ActionEvent event) {
    }

    @FXML
    private void themeComboboxAction(ActionEvent event) {
    }

    @FXML
    private void saveBtnAction(ActionEvent event) {
        warningLbl.setText("");
        if (bookNameTxt.getText().equalsIgnoreCase("") || authorTxt.getText().equalsIgnoreCase("") || pageCountTxt.getText().equalsIgnoreCase("") || priceTxt.getText().equalsIgnoreCase("")) {
            warningLbl.setText("pleace fill in all boxes!");
        } else {
            try {
                Book newBook = new Book();
                newBook.setName(bookNameTxt.getText());
                newBook.setTheme(ThmComboBox.getSelectionModel().getSelectedItem());
                newBook.setAuthor(authorTxt.getText());
                newBook.setPage_count(Integer.parseInt(pageCountTxt.getText()));
                newBook.setAmount(Double.parseDouble(priceTxt.getText()));
                newBook.setLanguage(LngComboBox.getSelectionModel().getSelectedItem());
                if (dao.addBook(newBook)) {
                    warningLbl.setText("new book added succesfully!");
                    refresh();
                } else {
                    warningLbl.setText("book didn't added!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void uptadeBtnAction(ActionEvent event) {
        warningLbl.setText("");
        if (bookNameTxt.getText().equalsIgnoreCase("") || authorTxt.getText().equalsIgnoreCase("") || pageCountTxt.getText().equalsIgnoreCase("") || priceTxt.getText().equalsIgnoreCase("")) {
            warningLbl.setText("pleace fill in all boxes!");
        } else {
            try {
                Book updateBook = new Book();
                updateBook.setId(selectedId);
                updateBook.setName(bookNameTxt.getText());
                updateBook.setAuthor(authorTxt.getText());
                updateBook.setAmount(Double.parseDouble(priceTxt.getText()));
                updateBook.setPage_count(Integer.parseInt(pageCountTxt.getText()));
                updateBook.setTheme(ThmComboBox.getSelectionModel().getSelectedItem());
                updateBook.setLanguage(LngComboBox.getSelectionModel().getSelectedItem());
                if (dao.updateBook(updateBook)) {
                    warningLbl.setText("succesfully changed!");
                    refresh();
                } else {
                    warningLbl.setText("not changed");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void addThemeBtn(ActionEvent event) {
        String newTheme = JOptionPane.showInputDialog(null, "New Theme");
        if (newTheme.equalsIgnoreCase("")) {
            warningLbl.setText("Theme is empty!");
        } else {
            if (ThmComboBox.getItems().contains(newTheme)) {
                warningLbl.setText("Not added! Theme already exist");
            } else {
                if (ThmComboBox.getItems().add(newTheme)) {
                    warningLbl.setText("New Theme successfully added!");
                }
            }
        }
    }

    @FXML
    private void filter1BtnAction(ActionEvent event) {
        warningLbl.setText("");
        if (min1Txt.getText().trim().equalsIgnoreCase("") || max1Txt.getText().trim().equalsIgnoreCase("")) {
            warningLbl.setText("Pleace fill in all boxes!");
        } else {
            Double minAmount = Double.parseDouble(min1Txt.getText().trim());
            Double maxAmount = Double.parseDouble(max1Txt.getText().trim());
            List<Book> result = dao.filterBookByAmount(minAmount, maxAmount);
            tableV.getItems().clear();
            tableV.getItems().addAll(result);
        }
    }

    @FXML
    private void filter2BtnAction(ActionEvent event) {
        warningLbl.setText("");
        if (min2Txt.getText().trim().equalsIgnoreCase("") || max2Txt.getText().trim().equalsIgnoreCase("")) {
            warningLbl.setText("pleace fill in all boxes!");
        } else {
            Double min = Double.parseDouble(min2Txt.getText().trim());
            Double max = Double.parseDouble(max2Txt.getText().trim());
            List<Book> result = dao.filterBookByPageCount(min, max);
            tableV.getItems().clear();
            tableV.getItems().addAll(result);
        }
    }

    @FXML
    private void clearAllFltrBtnAction(ActionEvent event) {
        searchTxt.setText("");
        min1Txt.setText("");
        max1Txt.setText("");
        min2Txt.setText("");
        max2Txt.setText("");
        refresh();
    }

    @FXML
    private void logoutAction(MouseEvent event) {
        try {
            int response = JOptionPane.showConfirmDialog(null, "Are you sure?");
            if (response == JOptionPane.YES_OPTION) {
                Stage stage = (Stage) logOut.getScene().getWindow();
                stage.close();
                Stage stage1 = new Stage();
                stage1.setTitle("Login");
                stage1.getIcons().add(new Image("/com/image/user.png"));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/login/login.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void hideAction(MouseEvent event) {
        tableV.setVisible(false);
    showBtn.setVisible(true);
        hideBtn.setVisible(false);
    }

    @FXML
    private void showAction(MouseEvent event) {
        tableV.setVisible(true);
hideBtn.setVisible(true);
        showBtn.setVisible(false);
    }

    private void loadColumn() {
        IdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        AuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        pageCountColumn.setCellValueFactory(new PropertyValueFactory<>("page_count"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        languageColumn.setCellValueFactory(new PropertyValueFactory<>("language"));
        themeColumn.setCellValueFactory(new PropertyValueFactory<>("theme"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadLanguageCB() {
        List<String> language = new ArrayList<String>();
        LngComboBox.getItems().add("Azerbaijani");
        LngComboBox.getItems().add("Russian");
        LngComboBox.getItems().add("English");
        LngComboBox.getItems().add("French");
        LngComboBox.getItems().add("Germany");
        LngComboBox.getItems().add("Turkish");
        LngComboBox.getItems().add("Chinese");
        LngComboBox.getItems().add("Portuguese");
        LngComboBox.getItems().add("Japanese");
        LngComboBox.getSelectionModel().selectFirst();
    }

    private void loadThemeCB() {
        List<String> theme = new ArrayList<String>();
        ThmComboBox.getItems().add("Roman");
        ThmComboBox.getItems().add("Tale");
        ThmComboBox.getItems().add("Student book");
        ThmComboBox.getItems().add("Science");
        ThmComboBox.getItems().add("Dedective");
        ThmComboBox.getItems().add("Self improvement");
        ThmComboBox.getItems().add("Drama");
        ThmComboBox.getItems().add("Comedy");
        ThmComboBox.getItems().add("For kids");
        ThmComboBox.getSelectionModel().selectFirst();
    }

    private void loadRows() {
        tableV.getItems().addAll(dao.getAllBooks());
    }

    @FXML
    private void searchTxtKeyReleased(KeyEvent event) {
        String keyword = searchTxt.getText().toUpperCase().trim();
        if (keyword.equalsIgnoreCase("")) {
            refresh();
        } else {
            List resultList = dao.searchBook(keyword);
            tableV.getItems().clear();
            tableV.getItems().addAll(resultList);
        }
    }

    @FXML
    private void tbleVmousePressed(MouseEvent event) {
        Book book = tableV.getSelectionModel().getSelectedItem();
        bookNameTxt.setText(book.getName());
        authorTxt.setText(book.getAuthor());
        pageCountTxt.setText(book.getPage_count() + "");
        priceTxt.setText(book.getAmount() + "");
        LngComboBox.getSelectionModel().select(book.getLanguage());
        ThmComboBox.getSelectionModel().select(book.getTheme());
        selectedId = book.getId();
    }

}
