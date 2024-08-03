package mahmud.com.crudapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    private TableColumn<Student, String> colCourse;

    @FXML
    private TableColumn<Student, String> colGender;

    @FXML
    private TableView<Student> tableStudent;

    @FXML
    private TextField txtFirst;

    @FXML
    private TextField txtLast;

    @FXML
    private TextField txtMiddle;

    @FXML
    private CheckBox chkBCA;

    @FXML
    private CheckBox chkBSC;

    @FXML
    private RadioButton radFemale;

    @FXML
    private RadioButton radMale;

    @FXML
    private ToggleGroup genderGroup;

    int id = 0;

    @FXML
    void SaveClick(ActionEvent event) throws SQLException {
        con = DBConnection.connect();
        String sql = "insert into crud(firstname,secondname,lastname, course, gender) values(?,?,?,?,?)";
        st = con.prepareStatement(sql);
        st.setString(1, txtFirst.getText());
        st.setString(2, txtMiddle.getText());
        st.setString(3, txtLast.getText());
        st.setString(4, getCourse());
        st.setString(5, getGender());
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
        st.setInt(1, id);
        st.executeUpdate();
        showStudent();
        clear();
    }

    @FXML
    void updateClick(ActionEvent event) throws SQLException {
        con = DBConnection.connect();
        String sql = "update crud set firstname = ?, secondname = ?, lastname = ?, course = ?, gender = ? where cid = ?";
        st = con.prepareStatement(sql);
        st.setString(1, txtFirst.getText());
        st.setString(2, txtMiddle.getText());
        st.setString(3, txtLast.getText());
        st.setString(4, getCourse());
        st.setString(5, getGender());
        st.setInt(6, id);
        st.executeUpdate();
        showStudent();
        clear();
    }

    private String getCourse() {
        StringBuilder courses = new StringBuilder();
        if (chkBCA.isSelected()) {
            courses.append("BCA");
        }
        if (chkBSC.isSelected()) {
            if (courses.length() > 0) {
                courses.append(", ");
            }
            courses.append("BSC");
        }
        return courses.toString();
    }

    private String getGender() {
        RadioButton selected = (RadioButton) genderGroup.getSelectedToggle();
        return selected != null ? selected.getText() : "";
    }

    void clear() {
        txtFirst.setText("");
        txtMiddle.setText("");
        txtLast.setText("");
        chkBCA.setSelected(false);
        chkBSC.setSelected(false);
        genderGroup.selectToggle(null); // Clear radio button selection
        btnSave.setDisable(false);
    }

    public ObservableList<Student> getStudent() {
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
                student.setCourse(rs.getString("course"));
                student.setGender(rs.getString("gender"));
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
        colFirst.setCellValueFactory(new PropertyValueFactory<Student, String>("fname"));
        colMiddle.setCellValueFactory(new PropertyValueFactory<Student, String>("mname"));
        colLast.setCellValueFactory(new PropertyValueFactory<Student, String>("lname"));
        colCourse.setCellValueFactory(new PropertyValueFactory<Student, String>("course"));
        colGender.setCellValueFactory(new PropertyValueFactory<Student, String>("gender"));
    }

    @FXML
    void getData(MouseEvent event) {
        Student student = tableStudent.getSelectionModel().getSelectedItem();
        if (student != null) {
            id = student.getId();
            txtFirst.setText(student.getFname());
            txtMiddle.setText(student.getMname());
            txtLast.setText(student.getLname());
            String course = student.getCourse();
            chkBCA.setSelected(course.contains("BCA"));
            chkBSC.setSelected(course.contains("BSC"));
            if ("Male".equals(student.getGender())) {
                radMale.setSelected(true);
            } else if ("Female".equals(student.getGender())) {
                radFemale.setSelected(true);
            }
            btnSave.setDisable(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genderGroup = new ToggleGroup();
        radMale.setToggleGroup(genderGroup);
        radFemale.setToggleGroup(genderGroup);

        showStudent();
    }
}
