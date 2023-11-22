package com.example.paoimlab3.logic;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.DecimalFormat;
import java.util.*;

public class ClassTeacher {
    private String groupName;
    private ArrayList<Teacher> teacherList;
    private Double percentFull;

    public String getGroupName() {
        return groupName;
    }

    public Integer getMaxTeachers() {
        return maxTeachers;
    }

    private Integer maxTeachers;

    public ClassTeacher(String groupName, Integer maxTeachers) {
        this.groupName = groupName;
        this.maxTeachers = maxTeachers;
        teacherList = new ArrayList<>(maxTeachers);
        percentFull = 0.0;
    }
    public String getPercentFullAsString(){
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        String formattedNumber = decimalFormat.format(percentFull);
        return formattedNumber;
    }
    public Double getPercentFull(){
        return percentFull;
    }


    public ArrayList<Teacher> getTeacherList(){
        return teacherList;
    }

    public void addTeacher(Teacher teacher) throws Exception {
        if(teacherList.size() == maxTeachers){
            throw new Exception("Max teacher number! Can't add any more teachers");
        }
        Iterator<Teacher> iterator;
        iterator = teacherList.iterator();
        while(iterator.hasNext()){
            Teacher teacher1 = iterator.next();
            if(teacher1.equals(teacher)){
                throw new Exception("This teacher exists");
            }
        }
        teacherList.add(teacher);
        percentFull = (double)teacherList.size() / (double) maxTeachers * 100.0;
    }

    public void addSalary(Teacher teacher, Double salary) throws Exception {
        Iterator<Teacher> iterator;
        iterator = teacherList.iterator();
        while(iterator.hasNext()){
            Teacher teacher1 = iterator.next();
            if(teacher.equals(teacher1)){
                teacher1.setSalary(teacher1.getSalary() + salary);
                return;
            }
        }
        throw new Exception("Teacher not Found at addTeacher");
    }

    public void removeTeacher(Teacher teacher) throws Exception {
        Iterator<Teacher> iterator;
        iterator = teacherList.iterator();
        while(iterator.hasNext()){
            Teacher teacher1 = iterator.next();
            if(teacher1.equals(teacher)){
                iterator.remove();
                percentFull = (double)teacherList.size() / (double) maxTeachers * 100.0;
                return;

            }
        }

        throw new Exception("Teacher not Found at removeTeacher");
    }


    public boolean changeCondition(Teacher teacher, TeacherCondition teacherCondition) throws Exception {
        Iterator<Teacher> iterator;
        iterator = teacherList.iterator();

        while(iterator.hasNext()){
            Teacher teacher1 = iterator.next();
            if(teacher1.equals(teacher)){
                teacher1.setTeacherCondition(teacherCondition);
                return true;
            }
        }

        throw new Exception("Teacher not Found at changeCondition");
    }

    public Teacher searchByLastName(String lastName) throws Exception {
        Iterator<Teacher> iterator;
        iterator = teacherList.iterator();
        while(iterator.hasNext()){
            Teacher teacher1 = iterator.next();
            if(lastName.compareTo(teacher1.getLastName()) == 0){
                return teacher1;
            }
        }
        throw new Exception("Teacher not Found at searchTeacher");
    }

    public Teacher searchByFirstLastName(String firstName,String lastName) throws Exception {
        Iterator<Teacher> iterator;
        iterator = teacherList.iterator();
        while(iterator.hasNext()){
            Teacher teacher1 = iterator.next();
            if(lastName.compareTo(teacher1.getLastName()) == 0 && firstName.compareTo(teacher1.getFirstName()) == 0){
                return teacher1;
            }
        }
        throw new Exception("Teacher not Found at searchByFirstLAstName Teacher");
    }

    public List<Teacher> searchPartial(String query) {
        List<Teacher> results = new ArrayList<>();
        for (Teacher teacher : teacherList) {
            if (teacher.getFirstName().contains(query) || teacher.getLastName().contains(query)) {
                results.add(teacher);
            }
        }
        return results;
    }
    public int countByCondition(TeacherCondition teacherCondition){
        int counter =0;
        for (Teacher teacher: teacherList) {
            if(teacher.getTeacherCondition() == teacherCondition){
                counter+=1;
            }

        }
        return counter;
    }
    public void summary(){
        for (Teacher teacher: teacherList) {
            System.out.println(teacher.toString());
        }
    }

    public List<Teacher> sortByLastName(){
        ArrayList<Teacher> teachersCopy = new ArrayList<>(teacherList);
        teachersCopy.sort(new Comparator<Teacher>() {
            @Override
            public int compare(Teacher o1, Teacher o2) {
                return o1.getLastName().compareTo(o2.getLastName());
            }
        });

        return teachersCopy;
    }
    public List<Teacher> sortByCondition(){
        ArrayList<Teacher> teachersCopy = new ArrayList<>(teacherList);
        teachersCopy.sort(new Comparator<Teacher>() {
            @Override
            public int compare(Teacher o1, Teacher o2) {
                return o1.getTeacherCondition().compareTo(o2.getTeacherCondition());
            }
        });
        return teachersCopy;
    }

    public List<Teacher> sortByFirstName(){
        ArrayList<Teacher> teachersCopy = new ArrayList<>(teacherList);
        teachersCopy.sort(new Comparator<Teacher>() {
            @Override
            public int compare(Teacher o1, Teacher o2) {
                return o1.getFirstName().compareTo(o2.getFirstName());
            }
        });

        return teachersCopy;
    }
    public List<Teacher> sortBySalary(){
        ArrayList<Teacher> teachersCopy = new ArrayList<>(teacherList);
        teachersCopy.sort(new Comparator<Teacher>() {
            @Override
            public int compare(Teacher o1, Teacher o2) {
                return o1.getSalary().compareTo(o2.getSalary());
            }
        });

        return  teachersCopy;
    }

    public Double maxSalary(){
        return Collections.max(teacherList, new Comparator<Teacher>() {
            @Override
            public int compare(Teacher o1, Teacher o2) {
                return o1.getSalary().compareTo(o2.getSalary());
            }
        }).getSalary();
    }

}
