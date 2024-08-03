package mahmud.com.crudapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import mahmud.com.crudapp.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Register {

    @FXML
    private Button btnRegister;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void registerEvent(ActionEvent event) throws SQLException {

    }
    static Connection con = null;
    static PreparedStatement pst = null;
    static ResultSet rs = null;

    // Method to verify user login
    // Method to retrieve hashed password from the database
    public static String getHashedPassword(String username) {
        String hashedPassword = null;
        try{
            con = DBConnection.connect();
            String query = "SELECT password FROM users WHERE username = ?";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    hashedPassword = rs.getString("password");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashedPassword;
    }
}
