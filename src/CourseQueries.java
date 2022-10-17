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
import java.sql.Timestamp;

/**
 *
 * @author acv
 */
public class CourseQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addCourse;
    private static PreparedStatement getCourseList;
    private static PreparedStatement getDesc;
    private static PreparedStatement getStudentsInCourse;
    private static PreparedStatement dropTheCourse;
    private static PreparedStatement getName;
    private static PreparedStatement deleteCourseSchedules;
    private static ResultSet resultSet;
    private static ResultSet resultSettt;
    private static ResultSet resultSet2;
    
    public static void addCourse(String Semester, String courseCode, String description, int seats)
    {
        connection = DBConnection.getConnection();
        try
        {
            addCourse = connection.prepareStatement("insert into app.COURSE (SEMESTER, COURSECODE, DESCRIPTION, SEATS) values (?,?,?,?)");
            addCourse.setString(1, Semester);
            addCourse.setString(2, courseCode);
            addCourse.setString(3, description);
            addCourse.setString(4, String.valueOf(seats));
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<CourseEntry> getAllCourses(String str)
    {
        ArrayList<CourseEntry> allCourses = new ArrayList<CourseEntry>();
        connection = DBConnection.getConnection();
        //ArrayList<CourseEntry> allCourses = new ArrayList<CourseEntry>();
        try
        {
            getCourseList = connection.prepareStatement("select SEMESTER, COURSECODE, DESCRIPTION, SEATS from app.COURSE where SEMESTER = (?) ");
            getCourseList.setString(1,str);
            resultSet = getCourseList.executeQuery();
            
            while(resultSet.next())
            {
                CourseEntry u = new CourseEntry(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4));
                allCourses.add(u);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return allCourses;
        
    }
    public static String getDescription(String code)
    {
        String des = "";
        connection = DBConnection.getConnection();
        //ArrayList<CourseEntry> allCourses = new ArrayList<CourseEntry>();
        try
        {
            getCourseList = connection.prepareStatement("select DESCRIPTION from app.COURSE where COURSECODE = (?)");
            getCourseList.setString(1,code);
            resultSettt = getCourseList.executeQuery();
            
            while(resultSettt.next())
            {
                des = resultSettt.getString(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return des;
        
    }
    
    
    public static ArrayList<CourseEntry> getAllCourses()
    {
        ArrayList<CourseEntry> allCourses = new ArrayList<CourseEntry>();
        connection = DBConnection.getConnection();
        //ArrayList<CourseEntry> allCourses = new ArrayList<CourseEntry>();
        try
        {
            getCourseList = connection.prepareStatement("select SEMESTER, COURSECODE, DESCRIPTION, SEATS from app.COURSE");
            resultSet = getCourseList.executeQuery();
            
            while(resultSet.next())
            {
                CourseEntry u = new CourseEntry(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4));
                allCourses.add(u);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return allCourses;
        
    }
    
    
    
     public static ArrayList<String> getAllCourseCodes(String str){
        connection = DBConnection.getConnection();
        ArrayList<String> allCourseCodes = new ArrayList<String>();
        try
        {
            getCourseList = connection.prepareStatement("select COURSECODE from app.COURSE where SEMESTER = (?)");
            getCourseList.setString(1,str);
            resultSet = getCourseList.executeQuery();
            
            while(resultSet.next())
            {
                allCourseCodes.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return allCourseCodes;    
         
     }
     public static int getCourseSeats(String str, String cc){
        connection = DBConnection.getConnection();
        int s = 0;
        //ArrayList<String> allCourseCodes = new ArrayList<String>();
        try
        {
            getCourseList = connection.prepareStatement("select SEATS from app.COURSE where SEMESTER = (?) and COURSECODE = (?)");
            getCourseList.setString(1,str);
            getCourseList.setString(2,cc);
            resultSet = getCourseList.executeQuery();
            
            while(resultSet.next())
            {
                s = resultSet.getInt(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return s;    
         
     }
     
     
     
     public static ArrayList<ArrayList<StudentEntry>> DropCourse(String str, String code){
        connection = DBConnection.getConnection();
        ArrayList<ArrayList<StudentEntry>> droppedStudents = new ArrayList<ArrayList<StudentEntry>>();
        try
        {
            getStudentsInCourse = connection.prepareStatement("select STUDENTID, STATUS from app.SCHEDULE where SEMESTER = (?) and COURSECODE = (?) ");
            getStudentsInCourse.setString(1,str);
            getStudentsInCourse.setString(2,code);
            resultSet = getStudentsInCourse.executeQuery();
            droppedStudents.add(new ArrayList<StudentEntry>());
            droppedStudents.add(new ArrayList<StudentEntry>());
            //droppedStudents.get(0).add(new StudentEntry("1","2","3"));
            
            
            while(resultSet.next())
            {
                //allCourseCodes.add(resultSet.getString(1));
                
                try{
                    getName = connection.prepareStatement("select FIRSTNAME, LASTNAME from app.STUDENT where STUDENTID = (?)");
                    getName.setString(1,resultSet.getString(1));
                    resultSet2 = getName.executeQuery();
                    while(resultSet2.next()){
                        //droppedStudents.get(0).add(new StudentEntry(resultSet.getString(2),"1","1"));
                        if(resultSet.getString(2).equals("scheduled")){
                            droppedStudents.get(0).add(new StudentEntry(resultSet.getString(1),resultSet2.getString(1),resultSet2.getString(2)));
                        }
                        else{
                            
                            droppedStudents.get(1).add(new StudentEntry(resultSet.getString(1),resultSet2.getString(1),resultSet2.getString(2)));
                        }
                    }
                }
                catch(SQLException sqlException){
                    
                }
                
                
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        try
        {
            dropTheCourse = connection.prepareStatement("delete from app.COURSE where SEMESTER = (?) and COURSECODE = (?) ");
            dropTheCourse.setString(1,str);
            dropTheCourse.setString(2,code);
            dropTheCourse.executeUpdate();
            
            while(resultSet.next())
            {
                //allCourseCodes.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        try
        {
            deleteCourseSchedules = connection.prepareStatement("delete from app.SCHEDULE where SEMESTER = (?) and COURSECODE = (?) ");
            deleteCourseSchedules.setString(1,str);
            deleteCourseSchedules.setString(2,code);
            deleteCourseSchedules.executeUpdate();
            
            while(resultSet.next())
            {
                //allCourseCodes.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return droppedStudents;    
        
         
     }
     
     
}