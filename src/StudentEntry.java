/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tommyhuggett
 */
public class StudentEntry {
    private String StudentID;
    private String firstName;
    private String lastName;

    public StudentEntry(String StudentID, String firstName, String lastName) {
        this.StudentID = StudentID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getStudentID() {
        return StudentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    
    public String toString(){
        return (lastName + ", " + firstName);
    }
    
    
}
