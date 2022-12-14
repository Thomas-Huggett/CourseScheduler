
import java.sql.Timestamp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tommyhuggett
 */
public class ScheduleEntry implements Comparable<ScheduleEntry> {
    private String semester;
    private String courseCode;
    private String studentID;
    private String status;
    private Timestamp timestamp; 

    public ScheduleEntry(String semester, String courseCode, String studentID, String status, Timestamp timestamp) {
        this.semester = semester;
        this.courseCode = courseCode;
        this.studentID = studentID;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getSemester() {
        return semester;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
    
    public String toString(){
        String s = "";
        s += "Semester: " +semester+"CourseCode: "+courseCode+"StudentID:"+studentID+"Status:"+status;
        return s;
    }
    
    
    public int compareTo(ScheduleEntry other){
        if(other.getTimestamp().compareTo(timestamp) > 1){
            return -1;
        }
        if(other.getTimestamp().compareTo(timestamp) == 0){
            return 0;
        }
        
        return 1;
        
    }
    
}
