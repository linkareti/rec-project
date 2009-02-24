package test;

import moodle_get_students.Student;
import moodle_get_students.StudentList;
import client.MoodleWsClient;

public class TestGetStudentsByCourse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StudentList studentsList = MoodleWsClient.getStudentsByCourse("2");
		
		for(Student student : studentsList.getStudent()) {
			String imp = "Id: " + student.getId() + " Firstname: " + student.getFirstname() + " Lastname: " + student.getLastname();

			System.out.println(imp);
		}
	}
}
