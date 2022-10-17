/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author acv
 */
public class StudentQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addCourse;
    private static PreparedStatement getStudents;
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;
    private static PreparedStatement dropClasses;
    private static ResultSet resultSet;
    private static ResultSet resultSet2;
    private static ResultSet resultSet3;
    
    public static void addStudent(String studentID, String firstName, String lastName)
    {
        connection = DBConnection.getConnection();
        try
        {
            addCourse = connection.prepareStatement("insert into app.STUDENT (STUDENTID, FIRSTNAME, LASTNAME) values (?,?,?)");
            addCourse.setString(1, studentID);
            addCourse.setString(2, firstName);
            addCourse.setString(3, lastName);
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static StudentEntry getStudentByID(String studentID)
    {
        StudentEntry students = new StudentEntry("","","");
        connection = DBConnection.getConnection();
        try
        {
            getStudent = connection.prepareStatement("select FIRSTNAME, LASTNAME, STUDENTID from app.student where STUDENTID = (?)");
            getStudent.setString(1,studentID);
            
            resultSet2 = getStudent.executeQuery();
            
            while(resultSet2.next()){
                students = (new StudentEntry(resultSet2.getString(3),resultSet2.getString(1),resultSet2.getString(2)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return students;
    }
  
     public static ArrayList<StudentEntry> getAllStudents(){
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> allStudents = new ArrayList<StudentEntry>();
        try
        {
            getStudents = connection.prepareStatement("select STUDENTID, FIRSTNAME, LASTNAME from app.STUDENT");
            resultSet = getStudents.executeQuery();
            
            while(resultSet.next())
            {
                
                allStudents.add(new StudentEntry(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return allStudents;    
         
     }
     
     public static void DropStudent(String studentID, String curSemester)
    {
        connection = DBConnection.getConnection();
        try
        {
            dropStudent = connection.prepareStatement("delete from app.STUDENT where STUDENTID = (?)");
            dropStudent.setString(1, studentID);
            //dropStudent.setString(2,curSemester);
            dropStudent.executeUpdate();
            
            dropClasses = connection.prepareStatement("delete from app.SCHEDULE where STUDENTID = (?) and SEMESTER = (?)");
            dropClasses.setString(1, studentID);
            dropClasses.setString(2, curSemester);
            dropClasses.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
}