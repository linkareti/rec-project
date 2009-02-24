package test;

import moodle_get_courses.Course;
import moodle_get_courses.CourseList;
import client.MoodleWsClient;

public class TestGetAllCourses {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CourseList courseList = MoodleWsClient.getAllCourses();
		
		for(Course course : courseList.getCourse()) {
			String imp = "Id: " + course.getId() + " Shortname: " + course.getShortname() 
				+ " Fullname: " + course.getFullname();
			System.out.println(imp);
		}

	}

}
