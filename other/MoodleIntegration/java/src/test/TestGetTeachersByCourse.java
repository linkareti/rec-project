package test;

import moodle_get_teachers.Teacher;
import moodle_get_teachers.TeacherList;
import client.MoodleWsClient;

public class TestGetTeachersByCourse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TeacherList teacherList = MoodleWsClient.getTeachersByCourse("2");
		
		for(Teacher teacher : teacherList.getTeacher()) {
			String imp = "Id: " + teacher.getId() + " Firstname: " + teacher.getFirstname() + " Lastname: " + teacher.getLastname();
			System.out.println(imp);
		}
	}

}
