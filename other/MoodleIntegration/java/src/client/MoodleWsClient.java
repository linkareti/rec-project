package client;

import moodle_get_courses.CourseList;
import moodle_get_courses.MoodleGetCourses;
import moodle_get_courses.WsGetAllCoursesRequestType;
import moodle_get_courses.WsGetAllCoursesResponseType;
import moodle_get_courses.WsGetCoursesByCategoryRequestType;
import moodle_get_courses.WsGetCoursesByCategoryResponseType;
import moodle_get_students.MoodleGetStudents;
import moodle_get_students.StudentList;
import moodle_get_students.WsGetStudentsByCourseRequestType;
import moodle_get_teachers.MoodleGetTeachers;
import moodle_get_teachers.TeacherList;
import moodle_get_teachers.WsGetTeachersByCourseRequestType;
import moodleauth.Moodleauth;
import moodleauth.WsautheticateRequestType;
import moodleauth.WsautheticateResponseType;

public class MoodleWsClient {
	public static boolean authenticate(String username, String password) {
		WsautheticateRequestType request = new WsautheticateRequestType();
		request.setLogin(username);
		request.setPassword(password);
		
		WsautheticateResponseType response = (new Moodleauth()).getMoodleauthPort()
			.wsautheticate(request);
		
		return response.isResult();
	}
	
	public static CourseList getAllCourses() {
		WsGetAllCoursesResponseType response =
			(new MoodleGetCourses()).getMoodleGetCoursesPort().
				wsGetAllCourses(new WsGetAllCoursesRequestType());
		
		return response.getResult();
		
	}
	
	public static CourseList getAllCoursesByCategory(String categoryId) {
		WsGetCoursesByCategoryRequestType request = new WsGetCoursesByCategoryRequestType();
		request.setCategoryId(categoryId);
		
		return (new MoodleGetCourses()).getMoodleGetCoursesPort()
			.wsGetCoursesByCategory(request).getResult();
		
	}
	
	public static StudentList getStudentsByCourse(String courseId) {
		WsGetStudentsByCourseRequestType request = new WsGetStudentsByCourseRequestType();
		request.setCourseId(courseId);
		
		return (new MoodleGetStudents()).getMoodleGetStudentsPort()
			.wsGetStudentsByCourse(request).getResult(); 
	}
	
	public static TeacherList getTeachersByCourse(String courseId) {
		WsGetTeachersByCourseRequestType request = new WsGetTeachersByCourseRequestType();
		request.setCourseId(courseId);
		
		return (new MoodleGetTeachers()).getMoodleGetTeachersPort()
			.wsGetTeachersByCourse(request).getResult();
	}
}
