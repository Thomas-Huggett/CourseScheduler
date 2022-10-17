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
import java.util.Calendar;
import java.sql.Timestamp;

/**
 *
 * @author acv
 */
public class ScheduleQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addSchedule;
    private static PreparedStatement getSemesterList;
    private static PreparedStatement getCount;
    private static PreparedStatement getScheduledStudents;
    private static ResultSet resultSet;
    private static ResultSet resultSett;
    private static ResultSet resultSet3;
    
    public static void addScheduleEntry(ScheduleEntry sched)
    {
        connection = DBConnection.getConnection();
        try
        {
            addSchedule = connection.prepareStatement("insert into app.SCHEDULE (SEMESTER,STUDENTID,COURSECODE,STATUS,TIMESTAMP) values (?,?,?,?,?)");
            addSchedule.setString(1,sched.getSemester());
            addSchedule.setString(2,sched.getStudentID());
            addSchedule.setString(3,sched.getCourseCode());
            addSchedule.setString(4,sched.getStatus());
            addSchedule.setString(5,sched.getTimestamp().toString());
            addSchedule.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static void MoveOffWaitlist(ScheduleEntry sched)
    {
        connection = DBConnection.getConnection();
        try
        {
            addSchedule = connection.prepareStatement("delete from app.SCHEDULE where SEMESTER = (?) and STUDENTID = (?) and COURSECODE = (?) ");
            addSchedule.setString(1,sched.getSemester());
            addSchedule.setString(2,sched.getStudentID());
            addSchedule.setString(3,sched.getCourseCode());
            
            addSchedule.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String str, String ID){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedules = new ArrayList<ScheduleEntry>();
        try
        {
            getSemesterList = connection.prepareStatement("select SEMESTER, STUDENTID, COURSECODE, STATUS, TIMESTAMP from app.SCHEDULE where SEMESTER = (?) and STUDENTID = (?)");
            getSemesterList.setString(1,str);
            getSemesterList.setString(2,ID);
            resultSet = getSemesterList.executeQuery();
            
            while(resultSet.next())
            {
                ScheduleEntry entry = new ScheduleEntry(resultSet.getString(1),resultSet.getString(3),resultSet.getString(2),resultSet.getString(4),resultSet.getTimestamp(5));
                schedules.add(entry);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return schedules;
        
    }
    
    public static int getScheduledStudentCount(String str, String code){
        connection = DBConnection.getConnection();
        int i = 0;
        try
        {
            getCount = connection.prepareStatement("SELECT STATUS FROM app.SCHEDULE where SEMESTER = (?) and COURSECODE=(?)");
            getCount.setString(1, str);
            getCount.setString(2,code);
            resultSett = getCount.executeQuery();
            
            while(resultSett.next()){
                i++;
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return i;
        
    }
    /*
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode) 
    {
        ArrayList<ScheduleEntry> scheduled = new ArrayList<ScheduleEntry>();
        connection = DBConnection.getConnection();
        try
        {
            getScheduledStudents = connection.prepareStatement("select SEMESTER, STUDENTID, COURSECODE, TIMESTAMP, STATUS from APP.SCHEDULE where SEMESTER = (?) and COURSECODE = (?)");
            getScheduledStudents.setString(1,semester);
            getScheduledStudents.setString(2,courseCode);
            resultSet3 = getScheduledStudents.executeQuery();
            
            while(resultSet3.next()){
                Timestamp ti = resultSet3.getTimestamp(5);
                
                
                //scheduled.add(new ScheduleEntry(resultSet3.getString(1),resultSet3.getString(2),resultSet3.getString(3),resultSet.getString(4),resultSet.getTimestamp(5)));
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return scheduled;
    }
*/
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String str, String courseCode){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedules = new ArrayList<ScheduleEntry>();
        try
        {
            getSemesterList = connection.prepareStatement("select SEMESTER, STUDENTID, COURSECODE, STATUS, TIMESTAMP from app.SCHEDULE where SEMESTER = (?) and COURSECODE = (?)");
            getSemesterList.setString(1,str);
            getSemesterList.setString(2,courseCode);
            resultSet = getSemesterList.executeQuery();
            
            while(resultSet.next())
            {
                ScheduleEntry entry = new ScheduleEntry(resultSet.getString(1),resultSet.getString(3),resultSet.getString(2),resultSet.getString(4),resultSet.getTimestamp(5));
                schedules.add(entry);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return schedules;
        
    }
    
    public static void removeCourseEntry(String ID, String code,String s){
        connection = DBConnection.getConnection();
        
        try
        {
            getCount = connection.prepareStatement("DELETE FROM app.SCHEDULE where SEMESTER = (?) and COURSECODE=(?) and STUDENTID = (?)");
            getCount.setString(1, s);
            getCount.setString(2,code);
            getCount.setString(3,ID);
            getCount.executeUpdate();
            
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        
    }
    
    
}
