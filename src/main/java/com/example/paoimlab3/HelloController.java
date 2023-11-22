package com.example.paoimlab3;

import com.example.paoimlab3.logic.ClassContainer;
import com.example.paoimlab3.logic.ClassTeacher;
import com.example.paoimlab3.logic.Teacher;
import com.example.paoimlab3.logic.TeacherCondition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class HelloController {


    @FXML
    private TextFlow textFlowtextfilter;
    @FXML
    private TextField textFieldtextfilter;



    @FXML
    private TextFlow textFlowClassInput;
    @FXML
    private TextField textFieldClassInput;
    @FXML
    private TextFlow textFlowPercent;


    @FXML
    private TextFlow textFlowClassMAX;
    @FXML
    private TextField textFieldClassMAX;

    @FXML
    private TextFlow textFlow1;
    @FXML
    private TextField textField1;

    @FXML
    private TextFlow textFlow2;
    @FXML
    private TextField textField2;

    @FXML
    private TextFlow textFlow3;
    @FXML
    private TextField textField3;

    @FXML
    private TextFlow textFlow4;
    @FXML
    private TextField textField4;
    @FXML
    private TextFlow textFlow5;

    @FXML
    private ListView<String> TeacherList;

    @FXML
    private ListView<String> ClassList;
    @FXML
    private ListView<String> conditionList;

    @FXML
    private TextFlow textFlowKomunikat;


    private Teacher selectedTeacher;

    ClassContainer classContainer;

    @FXML
    private ListView<String> sortTeacherList;

    @FXML
    private ListView<String> sortClassList;

    public void initialize() {

        textFlowtextfilter.getChildren().clear();
        textFlowtextfilter.getChildren().add(new Text("Find"));
        textFieldtextfilter.clear();

        textFieldtextfilter.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().toString().equals("ENTER") && textFieldtextfilter.isFocused()) {
                handleEnterKeyPress(textFieldtextfilter.getText());
                event.consume();
            }
        });

        textFlow1.getChildren().clear();
        textField1.setText("Name");

        textFlowPercent.getChildren().clear();
        textFlowPercent.getChildren().add(new Text("Class full: "));

        textFlow1.getChildren().clear();
        textFlow1.getChildren().add(new Text("First Name"));
        textField1.setText("");

        textFlow2.getChildren().clear();
        textFlow2.getChildren().add(new Text("Last Name"));
        textField2.setText("");

        textFlow3.getChildren().clear();
        textFlow3.getChildren().add(new Text("Salary"));
        textField3.setText("");

        textFlow4.getChildren().clear();
        textFlow4.getChildren().add(new Text("Birth YYYY.MM.DD"));
        textField4.setText("");

        textFlow5.getChildren().clear();
        textFlow5.getChildren().add(new Text("Condition"));

        textFlowClassInput.getChildren().clear();
        textFlowClassInput.getChildren().add(new Text("Class name"));
        textFieldClassInput.setText("");

        textFlowClassMAX.getChildren().clear();
        textFlowClassMAX.getChildren().add(new Text("Max teachers"));
        textFieldClassMAX.setText("");

        textFlowKomunikat.getChildren().clear();
        textFlowKomunikat.getChildren().add(new Text("Hi"));

        ObservableList<String> items = TeacherCondition.convertEnumToObservableList();
        conditionList.setItems(items);

        List<String> sortByList = Arrays.asList("First Name", "Last Name", "Salary", "Condition");
        ObservableList<String> sortBy = FXCollections.observableArrayList(sortByList);
        sortTeacherList.setItems(sortBy);

        List<String> sortByListClass = Arrays.asList("Name", "Percent");
        ObservableList<String> sortByClass = FXCollections.observableArrayList(sortByListClass);
        sortClassList.setItems(sortByClass);


        initStartClassesAndTeachers();
        ClassList.setItems(getClassConteinersToDisplay());


        // Obsluga zdarzenia gdy klikne na ClassList i cos wybiore zmienia sie teacherList
        ClassList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedClass) -> {
            if (selectedClass != null) {
                System.out.println("Zaznaczono klasę: " + selectedClass);
                TeacherList.setItems(getTeachersToDisplay(selectedClass));
                selectedTeacher =null;

                String percentValue = classContainer.getContainer().get(selectedClass.toString()).getPercentFull().toString();
                System.out.println(percentValue);

                StringBuilder str = new StringBuilder();
                str.append("Class full: ").append(percentValue.toString().substring(0,Math.min(percentValue.toString().length(),4))).append("%");
                Text textNode = new Text(str.toString());
                textFlowPercent.getChildren().clear();
                textFlowPercent.getChildren().add(textNode);
            }
        });

        TeacherList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedClass) -> {
            if(selectedClass!=null){
                System.out.println("Zaznaczono osobe: " + selectedClass);
                String[] s = selectedClass.split(" ");
                try {
                    Teacher teacher = classContainer.getContainer().get(ClassList.getSelectionModel().getSelectedItem()).searchByFirstLastName(s[0],s[1]);
                    selectedTeacher = teacher;
                    textField1.setText(teacher.getFirstName());
                    textField2.setText(teacher.getLastName());
                    textField3.setText(teacher.getSalary().toString());
                    textField4.setText(teacher.getYearOfBirthYYYYMMDD());
                    conditionList.getSelectionModel().select(teacher.getTeacherCondition().toString());


                }
                catch (Exception e){
                    textFlowKomunikat.getChildren().clear();
                    textFlowKomunikat.getChildren().add(new Text(e.toString()));
                }


            }
        });






    }

    private void handleEnterKeyPress(String text) {
        if(ClassList.getSelectionModel().getSelectedItem().isEmpty()){
            textFlowKomunikat.getChildren().clear();
            textFlowKomunikat.getChildren().add(new Text("No class selected"));
        }

        List<Teacher> teachers = classContainer.getContainer().get(ClassList.getSelectionModel().getSelectedItem().toString()).searchPartial(text);
        ObservableList<String> keyList = FXCollections.observableArrayList();

        for (Teacher t: teachers) {
            keyList.add(t.getFirstName() + " " + t.getLastName());
        }
        TeacherList.setItems(keyList);


    }

    private ObservableList<String> getClassConteinersToDisplay(){
        Map<String, ClassTeacher> container = classContainer.getContainer();
        ObservableList<String> keyList = FXCollections.observableArrayList();

        for (ClassTeacher cl : classContainer.getContainer().values()) {
            keyList.add(cl.getGroupName());
        }

        return  keyList;
    }

    private ObservableList<String> getTeachersToDisplay(String key){

        ClassTeacher classTeacher = classContainer.getContainer().get(key);
        ObservableList<String> keyList = FXCollections.observableArrayList();

        for (Teacher teacher : classTeacher.getTeacherList()) {
            keyList.add(teacher.getFirstName() +" "+ teacher.getLastName());
        }

        return  keyList;
    }
    public void refresh() {
        // Odśwież listę klas
        ObservableList<String> classItems = getClassConteinersToDisplay();
        ClassList.setItems(classItems);

        // Sprawdź, czy coś jest zaznaczone w liście klas
        String selectedClass = ClassList.getSelectionModel().getSelectedItem();

        // Jeśli coś jest zaznaczone, odśwież listę nauczycieli dla wybranej klasy
        if (selectedClass != null) {
            ObservableList<String> teacherItems = getTeachersToDisplay(selectedClass);
            TeacherList.setItems(teacherItems);
        } else {
            // Jeśli nic nie jest zaznaczone, wyczyść listę nauczycieli
            TeacherList.setItems(FXCollections.observableArrayList());
        }
    }
    private void initStartClassesAndTeachers() {
        classContainer = new ClassContainer();
        classContainer.addClass("Pierwsza klasa",6);
        classContainer.addClass("Druga klasa",3);
        classContainer.addClass("Trzecia klasa",5);

        Teacher teacher1 = new Teacher("Mateusz","Kuzera",new Date(2003,4,9), TeacherCondition.OBECNY,5000.0);
        Teacher teacher2 = new Teacher("Kacper","Kowalczyk",new Date(2002,2,4), TeacherCondition.CHORY,6000.0);
        Teacher teacher3 = new Teacher("Wiktor","Nowak",new Date(2001,3,19), TeacherCondition.OBECNY,8000.0);
        Teacher teacher4 = new Teacher("Ala","Osysko",new Date(2000,10,29), TeacherCondition.DELEGACJA,2000.0);
        Teacher teacher5 = new Teacher("Piotr","Ostry",new Date(2003,2,5), TeacherCondition.OBECNY,3000.0);
        Teacher teacher6 = new Teacher("Wiktoria","Majcher",new Date(2000,2,9), TeacherCondition.NIEOBECNY,5100.0);
        Teacher teacher7 = new Teacher("Krzysztof","Dab",new Date(2006,10,1), TeacherCondition.DELEGACJA,6700.0);
        Teacher teacher8 = new Teacher("Adam","Nowacki",new Date(2000,7,9), TeacherCondition.OBECNY,10000.0);

        try {

            classContainer.getContainer().get("Pierwsza klasa").addTeacher(teacher1);
            classContainer.getContainer().get("Pierwsza klasa").addTeacher(teacher2);
            classContainer.getContainer().get("Pierwsza klasa").addTeacher(teacher3);
            classContainer.getContainer().get("Pierwsza klasa").addTeacher(teacher4);

            classContainer.getContainer().get("Druga klasa").addTeacher(teacher5);
            classContainer.getContainer().get("Druga klasa").addTeacher(teacher6);
            classContainer.getContainer().get("Druga klasa").addTeacher(teacher7);

            classContainer.getContainer().get("Trzecia klasa").addTeacher(teacher8);

        } catch (Exception e) {
            System.out.println(e);
        }


    }

    public void handleButtonClickRemove(ActionEvent actionEvent) {
        if(!isTeacherSelected()){
            textFlowKomunikat.getChildren().clear();
            textFlowKomunikat.getChildren().add(new Text("No teacher selected"));
            return;
        }
        try {
            classContainer.getContainer().get(ClassList.getSelectionModel().getSelectedItem()).removeTeacher(selectedTeacher);
            textFlowKomunikat.getChildren().clear();
            textFlowKomunikat.getChildren().add(new Text("Teacher removed"));
            selectedTeacher = null;
        } catch (Exception e) {
            textFlowKomunikat.getChildren().clear();
            textFlowKomunikat.getChildren().add(new Text(e.toString()));
        }
        refresh();
    }

    public void handleButtonClickEdit(ActionEvent actionEvent) {
        if(!checkInputData()){
            return;
        }
        if(!isTeacherSelected()){
            textFlowKomunikat.getChildren().clear();
            textFlowKomunikat.getChildren().add(new Text("No teacher selected"));
            return;
        }
        try {
            Date date = createDateFromYYYYMMDD(textField4.getText());
            System.out.println(date.toString());
            Teacher teacher = new Teacher(textField1.getText() , textField2.getText(),date,TeacherCondition.fromString(conditionList.getSelectionModel().getSelectedItem().toString()),Double.parseDouble(textField3.getText()));
            classContainer.getContainer().get(ClassList.getSelectionModel().getSelectedItem()).removeTeacher(selectedTeacher);
            classContainer.getContainer().get(ClassList.getSelectionModel().getSelectedItem()).addTeacher(teacher);
            System.out.println(teacher.getYearOfBirth().toString());
            System.out.println(teacher.getYearOfBirthYYYYMMDD());
            textFlowKomunikat.getChildren().clear();
            textFlowKomunikat.getChildren().add(new Text("Teacher edited"));
        } catch (Exception e) {
            textFlowKomunikat.getChildren().clear();
            textFlowKomunikat.getChildren().add(new Text(e.toString()));
        }
        refresh();
    }
    public void handleButtonClickAdd(ActionEvent actionEvent) {

        if(!checkInputData()){
            return;
        }

            try {
                Date date = createDateFromYYYYMMDD(textField4.getText());
                System.out.println(conditionList.getSelectionModel().getSelectedItem().toString());
                Teacher teacher = new Teacher(textField1.getText() , textField2.getText(),date,TeacherCondition.fromString(conditionList.getSelectionModel().getSelectedItem().toString()),Double.parseDouble(textField3.getText()));
                classContainer.getContainer().get(ClassList.getSelectionModel().getSelectedItem()).addTeacher(teacher);
                textFlowKomunikat.getChildren().clear();
                textFlowKomunikat.getChildren().add(new Text("Teacher added"));
            } catch (Exception e) {
                textFlowKomunikat.getChildren().clear();
                textFlowKomunikat.getChildren().add(new Text(e.toString()));
            }
        refresh();

    }
    private boolean isTeacherSelected(){
        if(selectedTeacher == null){
            return false;
        }
        return true;
    }
    private boolean checkInputData() {
        if(textField1.getText().isEmpty()){
            textFlowKomunikat.getChildren().clear();
            textFlowKomunikat.getChildren().add(new Text("Name is empty"));
            return false;
        }
        else if(textField2.getText().isEmpty()){
            textFlowKomunikat.getChildren().clear();
            textFlowKomunikat.getChildren().add(new Text("Last name is empty"));
            return false;
        }
        else if(textField3.getText().isEmpty()){
            textFlowKomunikat.getChildren().clear();
            textFlowKomunikat.getChildren().add(new Text("Salary is empty"));
            return false;
        }
        else if(!isOKSalary((textField3.getText()))){
            textFlowKomunikat.getChildren().clear();
            textFlowKomunikat.getChildren().add(new Text("Invalid Salary"));
            return false;
        }
        else if(textField4.getText().isEmpty()){
            textFlowKomunikat.getChildren().clear();
            textFlowKomunikat.getChildren().add(new Text("Date is empty"));
            return false;
        }
        else if(!isValidDate(textField4.getText())){
            textFlowKomunikat.getChildren().clear();
            textFlowKomunikat.getChildren().add(new Text("Invalid Birth Date"));
            return false;
        }
        else if(ClassList.getSelectionModel().isEmpty()){
            textFlowKomunikat.getChildren().clear();
            textFlowKomunikat.getChildren().add(new Text("No class selected"));
            return false;
        }
        else if(conditionList.getSelectionModel().isEmpty()){
            textFlowKomunikat.getChildren().clear();
            textFlowKomunikat.getChildren().add(new Text("No condition selected"));
            return false;
        }
        return true;
    }


    public Date createDateFromYYYYMMDD(String dateString) throws Exception {
        if(!isValidDate(dateString)){
            throw new Exception();
        }
        System.out.println(dateString);
        try {
            String[] split = dateString.split("\\.");
            System.out.println(split[0]+ " "+ split[1]+ " " + split[2]);
            Date date = new Date(Integer.parseInt(split[0]), Integer.parseInt(split[1]),Integer.parseInt(split[2]));
            return date;
        } catch (Exception e) {
            textFlowKomunikat.getChildren().clear();
            textFlowKomunikat.getChildren().add(new Text(e.toString()));
        }
        return null;
    }

    private boolean isOKSalary(String text) {
        try {

            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }

    // tworzy date z imputu uzytkownika -> jak dostaje błąd to zwraca false oznaczajac ze data jest niepoprawna
    public static boolean isValidDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        dateFormat.setLenient(false);

        try {

            Date date = dateFormat.parse(dateString);

            Calendar calendar = Calendar.getInstance();
            calendar.setLenient(false);
            calendar.setTime(date);
            date = calendar.getTime();
            return true;
        } catch (ParseException | IllegalArgumentException e) {

            return false;
        }
    }

    public boolean handleButtonClickAddClass(ActionEvent actionEvent) {
        if(textFieldClassInput.getText().isEmpty()){
            textFlowKomunikat.getChildren().clear();
            textFlowKomunikat.getChildren().add(new Text("Empty class name"));
            return false;
        }
        else if(textFieldClassMAX.getText().isEmpty()){
            textFlowKomunikat.getChildren().clear();
            textFlowKomunikat.getChildren().add(new Text("Empty Max"));
            return false;
        }
        else{
            try {
                Integer i = Integer.parseInt(textFieldClassMAX.getText());
            }
            catch (Exception e){
                textFlowKomunikat.getChildren().clear();
                textFlowKomunikat.getChildren().add(new Text("Invalid max"));
                return false;
            }
        }

        try{
            classContainer.getContainer().put(textFieldClassInput.getText(),new ClassTeacher(textFieldClassInput.getText(),Integer.parseInt(textFieldClassMAX.getText())));
        }
        catch (Exception e){
            textFlowKomunikat.getChildren().clear();
            textFlowKomunikat.getChildren().add(new Text("Cant create a new class"));
            return false;
        }
        refresh();
        return true;
    }

    public void handleButtonClickRemoveClass(ActionEvent actionEvent) {

        try{
            classContainer.getContainer().remove(ClassList.getSelectionModel().getSelectedItem().toString());
            refresh();
        }
        catch (Exception e){
            textFlowKomunikat.getChildren().clear();
            textFlowKomunikat.getChildren().add(new Text("Error at remove class"));
        }
    }

    public void handleButtonClickSort(ActionEvent actionEvent) {
        if(sortTeacherList.getSelectionModel().getSelectedItem().isEmpty()){
            textFlowKomunikat.getChildren().clear();
            textFlowKomunikat.getChildren().add(new Text("Select sort by"));
            return;
        } else if (ClassList.getSelectionModel().getSelectedItem().isEmpty()) {
            textFlowKomunikat.getChildren().clear();
            textFlowKomunikat.getChildren().add(new Text("Select class to be sorted"));
            return;
        }
        List<Teacher> list = new ArrayList<>(classContainer.getContainer().get(ClassList.getSelectionModel().getSelectedItem().toString()).getTeacherList());
        for (Teacher t: list) {
            System.out.println(t.toString());
        }
        String choice = sortTeacherList.getSelectionModel().getSelectedItem().toString();

        if(choice.equals("Salary")) {
            list = classContainer.getContainer().get(ClassList.getSelectionModel().getSelectedItem().toString()).sortBySalary();
            for (Teacher t: list) {
                System.out.println(t.toString());
            }
        }
        else if (choice.equals("First Name")) {
            list = classContainer.getContainer().get(ClassList.getSelectionModel().getSelectedItem().toString()).sortByFirstName();
        }else if (choice.equals("Last Name")) {
            list = classContainer.getContainer().get(ClassList.getSelectionModel().getSelectedItem().toString()).sortByLastName();
        }
        else if (choice.equals("Condition")) {
            list = classContainer.getContainer().get(ClassList.getSelectionModel().getSelectedItem().toString()).sortByCondition();
        }


        ObservableList<String> keyList = FXCollections.observableArrayList();

            for (Teacher teacher : list) {
                keyList.add(teacher.getFirstName() + " " + teacher.getLastName());
            }
        refresh();

        System.out.println(keyList);
        TeacherList.setItems(keyList);

    }

    public void handleButtonClickSortClass(ActionEvent actionEvent) {
        if(sortClassList.getSelectionModel().getSelectedItem().isEmpty()){
            textFlowKomunikat.getChildren().clear();
            textFlowKomunikat.getChildren().add(new Text("Select sort by"));
            return;
        }
        String choice = sortClassList.getSelectionModel().getSelectedItem().toString();

        Map<String, ClassTeacher> t = classContainer.getContainer();

        if(choice.equals("Name")) {
            Map<String, ClassTeacher> sortedMap = t.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.comparing(ClassTeacher::getGroupName)))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (e1, e2) -> e1,
                            LinkedHashMap::new
                    ));

            // Tworzenie ObservableList dla kluczy
            ObservableList<String> keyList = FXCollections.observableArrayList(sortedMap.keySet());
            ClassList.setItems(keyList);
        }

        else if (choice.equals("Percent")) {
            Map<String, ClassTeacher> sortedMap = t.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.comparing(ClassTeacher::getPercentFull)))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (e1, e2) -> e1,
                            LinkedHashMap::new
                    ));

            // Tworzenie ObservableList dla kluczy
            ObservableList<String> keyList = FXCollections.observableArrayList(sortedMap.keySet());
            ClassList.setItems(keyList);
        }


    }
}