package mahmud.com.crudapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import mahmud.com.crudapp.DBConnection;
import mahmud.com.crudapp.model.Student;



import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    Connection con;
    PreparedStatement st;
    ResultSet rs;
    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<Student, Integer> colID;

    @FXML
    private TableColumn<Student, String> colFirst;

    @FXML
    private TableColumn<Student, String> colLast;

    @FXML
    private TableColumn<Student, String> colMiddle;

    @FXML
    private TextField txtFirst;

    @FXML
    private TextField txtLast;

    @FXML
    private TextField txtMiddle;

    int id = 0;
    @FXML
    void SaveClick(ActionEvent event) throws SQLException {
        con = DBConnection.connect();
        String sql = "insert into crud(firstname,secondname,lastname) values(?,?,?)";
        st = con.prepareStatement(sql);
        st.setString(1, txtFirst.getText());
        st.setString(2, txtMiddle.getText());
        st.setString(3, txtLast.getText());
        st.executeUpdate();
        showStudent();
        clear();
    }

    @FXML
    void clearClick(ActionEvent event) {
        clear();
    }

    @FXML
    void deleteClick(ActionEvent event) throws SQLException {
        con = DBConnection.connect();
        String sql = "delete from crud where cid = ?";
        st = con.prepareStatement(sql);
        st.setInt(1,id);
        st.executeUpdate();
        showStudent();
        clear();
    }
    void clear(){
        txtFirst.setText("");
        txtMiddle.setText("");
        txtLast.setText("");
        btnSave.setDisable(false);
    }
    @FXML
    void updateClick(ActionEvent event) throws SQLException {
        con = DBConnection.connect();
        String sql = "update crud set firstname = ?,secondname = ?,lastname = ? where cid = ?";
        st = con.prepareStatement(sql);
        st.setString(1, txtFirst.getText());
        st.setString(2, txtMiddle.getText());
        st.setString(3, txtLast.getText());
        st.setInt(4,id);
        st.executeUpdate();
        showStudent();
        clear();
    }
    public ObservableList<Student> getStudent(){
        ObservableList<Student> students = FXCollections.observableArrayList();
        String query = "SELECT * FROM crud";
        try {
            con = DBConnection.connect();
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("cid"));
                student.setFname(rs.getString("firstname"));
                student.setMname(rs.getString("secondname"));
                student.setLname(rs.getString("lastname"));
                students.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }
    public void showStudent() {
        ObservableList<Student> list = getStudent();
        tableStudent.setItems(list);
        colID.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));
        colFirst.setCellValueFactory(new PropertyValueFactory<Student,String>("fname"));
        colMiddle.setCellValueFactory(new PropertyValueFactory<Student,String>("mname"));
        colLast.setCellValueFactory(new PropertyValueFactory<Student,String>("lname"));
    }
    @FXML
    void getData(MouseEvent event) {
        Student student = tableStudent.getSelectionModel().getSelectedItem();
        if (student != null) {
            id = student.getId();
            txtFirst.setText(student.getFname());
            txtMiddle.setText(student.getMname());
            txtLast.setText(student.getLname());
            btnSave.setDisable(true);

        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showStudent();
    }
}
