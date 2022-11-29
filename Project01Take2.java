import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Project01Take2 {
    String coursePrefix;
    int courseNumber;
    String courseTitle;
    int numOfCredits;

    static Connection conn;
    public void keepTrackProgram(String coursePrefix, int courseNumber, String courseTitle, int numOfCredits) {
    }

    public static void main(String[] args) throws IOException, SQLException {
        
        //create a new scanner
        Scanner sc = new Scanner(System.in);

        //declare variables type
        String action = "";
        String mainCommand= "";


        //check input commands == a or not, if not then print the prompt
        while (!mainCommand.equals("q")) {
            System.out.println(">>>");
            mainCommand = sc.nextLine();

            action = mainCommand.substring(0, 1);
            if (action.equals("q")) {
                break;
            }
            //take input command string after the cases ac, as, am....
            String object = mainCommand.substring(2 ,3);

            switch (action.trim()) {
                case "a": //for add
                    switch (object.trim()) {
                        case "c": //for add course
                        	 String courseString = mainCommand.substring(4, mainCommand.length());
                             String[] courseArray = courseString.split(" ");
                             String insertCourseStatement = "Insert into course (course_prefix, course_number, course_title, course_credit) values ("+
                                     "'"+courseArray[0]+"',"+courseArray[1]+",'"+ courseArray[2]+"',"+courseArray[3] + ")";
                             try {
                                 conn = DBConnector.makeConnection();
                                 conn.createStatement().executeUpdate(insertCourseStatement);
                                 conn.close();
                             } catch (SQLException e) {
                            	 System.err.println("not allow duplicate course number");
                                 conn.close();
                             }
                            break;

                        case "g": //grade
                        	String gradeString = mainCommand.substring(4, mainCommand.length());
                            String[] gradeArray = gradeString.split(" ");
                            String insertGradeStatement = "Insert into gradetype (grade_type, grade_point) values ("+
                                    "'"+gradeArray[0]+"',"+gradeArray[1]+ ")";
                            System.out.println(insertGradeStatement);
                            try {
                                conn = DBConnector.makeConnection();
                                conn.createStatement().executeUpdate(insertGradeStatement);
                                conn.close();
                            } catch (SQLException e) {
                            	System.err.println("not allow duplicate gradetype");
                                conn.close();
                            }

                            break;

                        case "m": //semester code
                        	String semesterString = mainCommand.substring(4, mainCommand.length());
                            String[] semesterArray = semesterString.split(" ");
                            String insertSemesterStatement = "insert into semester (semester_code, year, description)"
                            		+ "values ('"+semesterArray[0]+"',"+semesterArray[1]+", '"+semesterArray[2]+"')";
                            System.out.println(insertSemesterStatement);
                            try {
                                conn = DBConnector.makeConnection();
                                conn.createStatement().executeUpdate(insertSemesterStatement);
                                conn.close();
                            } catch (SQLException e) {
                                System.err.println("not allow duplicate semester  code");
                                conn.close();
                            }

                            break;

                        case "s": // student info.

                        	String studentInfoString = mainCommand.substring(4, mainCommand.length());
                            String[] studentInfoArray = studentInfoString.split(" ");
                            String insertstudentInfoStatement = "INSERT Into students (lastname, firstname, phone_number)"
                            		+ "Values ('"+studentInfoArray[0]+"', '"+studentInfoArray[1]+"', '"+studentInfoArray[2]+"')";
                            System.out.println(insertstudentInfoStatement);
                            try {
                                conn = DBConnector.makeConnection();
                                conn.createStatement().executeUpdate(insertstudentInfoStatement);
                                conn.close();
                            } catch (SQLException e) {
                            	System.err.println("not allow duplicate lastname, firstname");
                                conn.close();
                            }
                            break;

                        case "t": //taken course
                        	String takenCourseString = mainCommand.substring(4, mainCommand.length());
                            String[] takenCourseArray = takenCourseString.split(" ");
                            String insertTakenCourse = "insert into taken_course(lastname, firstname, course_prefix,course_number,grade_type,semester_code)"
                            		+ "values ('"+ takenCourseArray[0]+"','"+ takenCourseArray[1]+"', '"+ takenCourseArray[2]+"', "+ takenCourseArray[3]+", '"+ takenCourseArray[4]+"','"+ takenCourseArray[5]+"' )";
                            System.out.println(insertTakenCourse);
                            try {
                                conn = DBConnector.makeConnection();
                                conn.createStatement().executeUpdate(insertTakenCourse);
                                conn.close();
                            } catch (SQLException e) {
                            	System.err.println("not allow duplicate taken_course");
                                conn.close();
                            }
                    }
                    break;

                case "l":// for listing -- read the file
                    switch (object.trim()) {
                        case "c": //for list all course
                            conn = DBConnector.makeConnection();
                            String getAllCourses = "SELECT * FROM course";
                            ResultSet courseRows = conn.createStatement().executeQuery(getAllCourses);
                            
                        	while (courseRows.next()) {
                                System.out.println(courseRows.getString("id") + " " + courseRows.getString("course_prefix") + " " + courseRows.getString("course_number") + " " + courseRows.getString("course_title") + " " + courseRows.getString("course_credit"));
                            }
                        	conn.close();
                            break;

                        case "g": //for list all grade
                        	conn = DBConnector.makeConnection();
                            String getAllGrades = "SELECT * FROM gradetype";
                            ResultSet gradeRows = conn.createStatement().executeQuery(getAllGrades);
                            
                        	while (gradeRows.next()) {
                                System.out.println(gradeRows.getString("id") + " " + gradeRows.getString("grade_type") + " " + gradeRows.getString("grade_point"));
                            }
                        	conn.close();
                            break;

                        case "m": //list all semester
                        	conn = DBConnector.makeConnection();
                            String getAllSemester = "SELECT * FROM semester";
                            ResultSet semesterRows = conn.createStatement().executeQuery(getAllSemester);
                            
                        	while (semesterRows.next()) {
                                System.out.println(semesterRows.getString("id") + " " + semesterRows.getString("semester_code") + " " + semesterRows.getString("year")+ " " + semesterRows.getString("description") );
                            }
                        	conn.close();

                            break;

                        case "s": //for list all student info
                        	conn = DBConnector.makeConnection();
                            String getAllStudentInfo = "SELECT * FROM students";
                            ResultSet studentsRows = conn.createStatement().executeQuery(getAllStudentInfo);
                            
                        	while (studentsRows.next()) {
                                System.out.println(studentsRows.getString("id") + " " + studentsRows.getString("lastname") + " " + studentsRows.getString("firstname")+ " " + studentsRows.getString("phone_number") );
                            }
                        	conn.close();
                            break;

                        case "t": //for list all course
                        	conn = DBConnector.makeConnection();
                            String getAllTakenCourse = "SELECT * FROM taken_course";
                            ResultSet takenCourseRows = conn.createStatement().executeQuery(getAllTakenCourse);
                            
                        	while (takenCourseRows.next()) {
                                System.out.println(takenCourseRows.getString("id") + " " + takenCourseRows.getString("lastname") + " " + takenCourseRows.getString("firstname")+ " " + takenCourseRows.getString("course_prefix") + " " + takenCourseRows.getString("course_number") +" " + takenCourseRows.getString("grade_type") +" " + takenCourseRows.getString("semester_code") );
                            }
                        	conn.close();
                            break;
                    }
                    break;

                case "t": //read transcript
                    String fullName = mainCommand.replaceFirst(action, "").trim(); //eliminate t character
                    String inputLastName = fullName.split(" ")[0].trim();
                    String inputFirstName= fullName.split(" ")[1].trim();

                    String query = "SELECT tc.*, s.year,s.description, gt.grade_point, c.course_title,  c.course_credit FROM taken_course tc "
                    		+ "left join semester s on tc.semester_code = s.semester_code "
                    		+ "left join gradetype gt on tc.grade_type = gt.grade_type "
                    		+ "left join course c on tc.course_number = c.course_number "
                    		+ "where lastname = '"+inputLastName+"' AND firstname = '"+inputFirstName+"' "
                    		+ "order by semester_code";
                    conn = DBConnector.makeConnection();
                    ResultSet takenCourseRows = conn.createStatement().executeQuery(query);
                    
                    int totalHour  = 0;
                    double totalGpa = 0.0;
                    String semesterTitle =  "";
                    while (takenCourseRows.next()) {
                        String takenCourseCode = takenCourseRows.getString("course_prefix");
                        String takenCourseNumber =takenCourseRows.getString("course_number");
                        String takenCourseGrade = takenCourseRows.getString("grade_type");
                        String takenCoursePoint = takenCourseRows.getString("grade_point");
                        String takenCourseName = takenCourseRows.getString("course_title");
                        String takenCredit = takenCourseRows.getString("course_credit");

                        totalHour  = totalHour + Integer.valueOf(takenCredit);
                        totalGpa = totalGpa +  ( Integer.valueOf(takenCredit)  *  Double.valueOf(takenCoursePoint));
                        
                        String temp = takenCourseRows.getString("description") +" " +takenCourseRows.getString("year");
                        if (!temp.equals(semesterTitle)) {
                        	semesterTitle = temp;
                        	System.out.println("============ Semester:" +semesterTitle+ " ==============");
                        }
                        String finalString = takenCourseCode+takenCourseNumber+" "+takenCourseName+" ("+takenCredit+") "+takenCourseGrade;
                        System.out.println(finalString);
                        
                    }
                    System.out.println("____________________________");
                    System.out.println("STUDENT HOURS COMPLETED: " + totalHour);
                    System.out.printf("STUDENT GPA: %,.5f \n",  totalGpa/totalHour);
                    conn.close();
                    break;
                    
                case "d": //delete
                	 String studentNameForDelete = mainCommand.replaceFirst(action, "").trim(); //delete student 
                     String inputLastNameForDelete = studentNameForDelete.split(" ")[0].trim();
                     String inputFirstNameForDelete= studentNameForDelete.split(" ")[1].trim();
                     String deleteStudennt = "Delete FROM students WHERE lastname = '"+inputLastNameForDelete+"' AND firstname = '"+inputFirstNameForDelete+"'";

                     String deleteTakeCourse = "Delete from taken_course Where lastname = '"+inputLastNameForDelete+"' AND firstname = '"+inputFirstNameForDelete+"'";
                   //System.out.print(deleteStudentRecord);
                       try {
                           conn = DBConnector.makeConnection();
                           conn.createStatement().executeUpdate(deleteStudennt);
                           conn.createStatement().executeUpdate(deleteTakeCourse);
                           
                           conn.close();
                       } catch (SQLException e) {
                           e.printStackTrace();
                           conn.close();
                       }
                    
                default:
                    //do something
            }
        }
    }
}
