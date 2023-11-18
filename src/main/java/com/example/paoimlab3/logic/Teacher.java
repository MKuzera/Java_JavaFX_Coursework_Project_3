package com.example.paoimlab3.logic;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class Teacher implements Comparable<Teacher> {
    private String firstName;
    private String lastName;
    private Date yearOfBirth;
    private TeacherCondition teacherCondition;
    private Double salary;

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getSalary() {
        return salary;
    }

    public Teacher(String firstName, String lastName, Date yearOfBirth, TeacherCondition teacherCondition, Double salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.teacherCondition = teacherCondition;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", teacherCondition=" + teacherCondition +
                ", salary=" + salary +
                '}';
    }

    public Date getYearOfBirth() {
        return yearOfBirth;
    }

    public String getYearOfBirthYYYYMMDD() {
        int year = yearOfBirth.getYear() ; // Dodaj 1900, ponieważ metoda getYear() zwraca rok od 1900
        int month = yearOfBirth.getMonth() ; // Dodaj 1, ponieważ miesiące są indeksowane od 0
        int day = yearOfBirth.getDate();

        return String.format("%04d.%02d.%02d", year, month, day);
    }


    public TeacherCondition getTeacherCondition() {
        return teacherCondition;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setTeacherCondition(TeacherCondition teacherCondition) {
        this.teacherCondition = teacherCondition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(firstName, teacher.firstName) && Objects.equals(lastName, teacher.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, yearOfBirth);
    }

    @Override
    public int compareTo(Teacher o) {
        return this.lastName.compareTo(o.getLastName());
    }
}
