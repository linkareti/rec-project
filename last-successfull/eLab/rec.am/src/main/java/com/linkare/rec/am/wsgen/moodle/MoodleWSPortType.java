/**
 * MoodleWSPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linkare.rec.am.wsgen.moodle;

public interface MoodleWSPortType extends java.rmi.Remote {

    /**
     * MoodleWS Client Login
     */
    public com.linkare.rec.am.wsgen.moodle.LoginReturn login(java.lang.String username, java.lang.String password) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Client Logout
     */
    public boolean logout(java.math.BigInteger client, java.lang.String sesskey) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get Course Information
     */
    public com.linkare.rec.am.wsgen.moodle.GetCoursesReturn get_course(java.math.BigInteger client, java.lang.String sesskey, java.lang.String courseid, java.lang.String idfield) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get Course Information
     */
    public com.linkare.rec.am.wsgen.moodle.GetCoursesReturn get_course_byid(java.math.BigInteger client, java.lang.String sesskey, java.lang.String info) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get Course Information
     */
    public com.linkare.rec.am.wsgen.moodle.GetCoursesReturn get_course_byidnumber(java.math.BigInteger client, java.lang.String sesskey, java.lang.String info) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get Course groups
     */
    public com.linkare.rec.am.wsgen.moodle.GetGroupsReturn get_groups_bycourse(java.math.BigInteger client, java.lang.String sesskey, java.lang.String courseid, java.lang.String idfield) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get Course Information
     */
    public com.linkare.rec.am.wsgen.moodle.GetGroupsReturn get_group_byid(java.math.BigInteger client, java.lang.String sesskey, java.lang.String info, java.math.BigInteger courseid) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get Course Information
     */
    public com.linkare.rec.am.wsgen.moodle.GetGroupsReturn get_groups_byname(java.math.BigInteger client, java.lang.String sesskey, java.lang.String info, java.math.BigInteger courseid) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get one User Information
     */
    public com.linkare.rec.am.wsgen.moodle.GetUsersReturn get_user(java.math.BigInteger client, java.lang.String sesskey, java.lang.String userid, java.lang.String idfield) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Edit Users Information
     */
    public com.linkare.rec.am.wsgen.moodle.EditUsersOutput edit_users(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.EditUsersInput users) throws java.rmi.RemoteException;

    /**
     * MoodleWS: add on course
     */
    public com.linkare.rec.am.wsgen.moodle.EditCoursesOutput add_course(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.CourseDatum course) throws java.rmi.RemoteException;

    /**
     * MoodleWS: add on course
     */
    public com.linkare.rec.am.wsgen.moodle.EditUsersOutput add_user(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.UserDatum user) throws java.rmi.RemoteException;

    /**
     * MoodleWS: add on course
     */
    public com.linkare.rec.am.wsgen.moodle.EditGroupsOutput add_group(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.GroupDatum group) throws java.rmi.RemoteException;

    /**
     * MoodleWS: add on course
     */
    public com.linkare.rec.am.wsgen.moodle.EditGroupingsOutput add_grouping(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.GroupingDatum grouping) throws java.rmi.RemoteException;

    /**
     * MoodleWS: add on course
     */
    public com.linkare.rec.am.wsgen.moodle.EditCoursesOutput delete_course(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value, java.lang.String id) throws java.rmi.RemoteException;

    /**
     * MoodleWS: add on course
     */
    public com.linkare.rec.am.wsgen.moodle.EditUsersOutput delete_user(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value, java.lang.String id) throws java.rmi.RemoteException;

    /**
     * MoodleWS: add on course
     */
    public com.linkare.rec.am.wsgen.moodle.EditGroupsOutput delete_group(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value, java.lang.String id) throws java.rmi.RemoteException;

    /**
     * MoodleWS: add on course
     */
    public com.linkare.rec.am.wsgen.moodle.EditGroupingsOutput delete_grouping(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value, java.lang.String id) throws java.rmi.RemoteException;

    /**
     * MoodleWS: add on course
     */
    public com.linkare.rec.am.wsgen.moodle.EditCoursesOutput update_course(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.CourseDatum course, java.lang.String idfield) throws java.rmi.RemoteException;

    /**
     * MoodleWS: add on course
     */
    public com.linkare.rec.am.wsgen.moodle.EditUsersOutput update_user(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.UserDatum user, java.lang.String idfield) throws java.rmi.RemoteException;

    /**
     * MoodleWS: add on course
     */
    public com.linkare.rec.am.wsgen.moodle.EditGroupsOutput update_group(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.GroupDatum group, java.lang.String idfield) throws java.rmi.RemoteException;

    /**
     * MoodleWS: add on course
     */
    public com.linkare.rec.am.wsgen.moodle.EditGroupingsOutput update_grouping(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.GroupingDatum grouping, java.lang.String idfield) throws java.rmi.RemoteException;

    /**
     * MoodleWS: add a course category
     */
    public com.linkare.rec.am.wsgen.moodle.EditCategoriesOutput add_category(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.CategoryDatum category) throws java.rmi.RemoteException;

    /**
     * MoodleWS: add a course section
     */
    public com.linkare.rec.am.wsgen.moodle.EditSectionsOutput add_section(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.SectionDatum section) throws java.rmi.RemoteException;

    /**
     * MoodleWS: add a course section
     */
    public com.linkare.rec.am.wsgen.moodle.EditSectionsOutput update_section(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.SectionDatum section, java.lang.String idfield) throws java.rmi.RemoteException;

    /**
     * MoodleWS: add a label
     */
    public com.linkare.rec.am.wsgen.moodle.EditLabelsOutput add_label(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.LabelDatum label) throws java.rmi.RemoteException;

    /**
     * MoodleWS: add a forum
     */
    public com.linkare.rec.am.wsgen.moodle.EditForumsOutput add_forum(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.ForumDatum forum) throws java.rmi.RemoteException;

    /**
     * MoodleWS: add an assignment
     */
    public com.linkare.rec.am.wsgen.moodle.EditAssignmentsOutput add_assignment(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.AssignmentDatum assignment) throws java.rmi.RemoteException;

    /**
     * MoodleWS: add a course category
     */
    public com.linkare.rec.am.wsgen.moodle.EditDatabasesOutput add_database(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.DatabaseDatum database) throws java.rmi.RemoteException;

    /**
     * MoodleWS: add a course category
     */
    public com.linkare.rec.am.wsgen.moodle.EditWikisOutput add_wiki(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.WikiDatum wiki) throws java.rmi.RemoteException;

    /**
     * MoodleWS: add a course category
     */
    public com.linkare.rec.am.wsgen.moodle.EditPagesWikiOutput add_pagewiki(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.PageWikiDatum page) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get Users Information
     */
    public com.linkare.rec.am.wsgen.moodle.GetUsersReturn get_users(java.math.BigInteger client, java.lang.String sesskey, java.lang.String[] userids, java.lang.String idfield) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get course teachers
     */
    public com.linkare.rec.am.wsgen.moodle.GetUsersReturn get_teachers(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value, java.lang.String id) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get course students
     */
    public com.linkare.rec.am.wsgen.moodle.GetUsersReturn get_students(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value, java.lang.String id) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Edit Courses Information
     */
    public com.linkare.rec.am.wsgen.moodle.EditCoursesOutput edit_courses(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.EditCoursesInput courses) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get Courses Information
     */
    public com.linkare.rec.am.wsgen.moodle.GetCoursesReturn get_courses(java.math.BigInteger client, java.lang.String sesskey, java.lang.String[] courseids, java.lang.String idfield) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get resources in courses
     */
    public com.linkare.rec.am.wsgen.moodle.GetResourcesReturn get_resources(java.math.BigInteger client, java.lang.String sesskey, java.lang.String[] courseids, java.lang.String idfield) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get resources in courses
     */
    public com.linkare.rec.am.wsgen.moodle.GetResourcesReturn get_instances_bytype(java.math.BigInteger client, java.lang.String sesskey, java.lang.String[] courseids, java.lang.String idfield, java.lang.String type) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get User Grades in some courses
     */
    public com.linkare.rec.am.wsgen.moodle.GetGradesReturn get_grades(java.math.BigInteger client, java.lang.String sesskey, java.lang.String userid, java.lang.String userfield, java.lang.String[] courseids, java.lang.String courseidfield) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get all Users Grades in one course
     */
    public com.linkare.rec.am.wsgen.moodle.GetGradesReturn get_course_grades(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value, java.lang.String id) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get User Grades in all courses
     */
    public com.linkare.rec.am.wsgen.moodle.GetGradesReturn get_user_grades(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value, java.lang.String id) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Enrol students in a course
     */
    public com.linkare.rec.am.wsgen.moodle.EnrolStudentsReturn enrol_students(java.math.BigInteger client, java.lang.String sesskey, java.lang.String courseid, java.lang.String courseidfield, java.lang.String[] userids, java.lang.String useridfield) throws java.rmi.RemoteException;

    /**
     * MoodleWS: UnEnrol students in a course
     */
    public com.linkare.rec.am.wsgen.moodle.EnrolStudentsReturn unenrol_students(java.math.BigInteger client, java.lang.String sesskey, java.lang.String courseid, java.lang.String courseidfield, java.lang.String[] userids, java.lang.String useridfield) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get All roles defined in Moodle
     */
    public com.linkare.rec.am.wsgen.moodle.GetRolesReturn get_roles(java.math.BigInteger client, java.lang.String sesskey) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get one role defined in Moodle
     */
    public com.linkare.rec.am.wsgen.moodle.GetRolesReturn get_role_byid(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get one role defined in Moodle
     */
    public com.linkare.rec.am.wsgen.moodle.GetRolesReturn get_role_byname(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get Moodle s events
     */
    public com.linkare.rec.am.wsgen.moodle.GetEventsReturn get_events(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger eventtype, java.math.BigInteger ownerid) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get last changes to a Moodle s
     * 				course
     */
    public com.linkare.rec.am.wsgen.moodle.GetLastChangesReturn get_last_changes(java.math.BigInteger client, java.lang.String sesskey, java.lang.String courseid, java.lang.String idfield, java.math.BigInteger limit) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get Moodle course categories
     */
    public com.linkare.rec.am.wsgen.moodle.GetCategoriesReturn get_categories(java.math.BigInteger client, java.lang.String sesskey) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get one category defined in Moodle
     */
    public com.linkare.rec.am.wsgen.moodle.GetCategoriesReturn get_category_byid(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get one category defined in Moodle
     */
    public com.linkare.rec.am.wsgen.moodle.GetCategoriesReturn get_category_byname(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get Courses user identified by id
     * 				is member of
     */
    public com.linkare.rec.am.wsgen.moodle.GetCoursesReturn get_my_courses(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger uid, java.lang.String sort) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get Courses current user identified
     * 				by idnumber is member of
     */
    public com.linkare.rec.am.wsgen.moodle.GetCoursesReturn get_my_courses_byidnumber(java.math.BigInteger client, java.lang.String sesskey, java.lang.String uinfo, java.lang.String sort) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get Courses current user identified
     * 				by username is member of
     */
    public com.linkare.rec.am.wsgen.moodle.GetCoursesReturn get_my_courses_byusername(java.math.BigInteger client, java.lang.String sesskey, java.lang.String uinfo, java.lang.String sort) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get Courses belonging to category
     */
    public com.linkare.rec.am.wsgen.moodle.GetCoursesReturn get_courses_bycategory(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger categoryid) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get Course sections
     */
    public com.linkare.rec.am.wsgen.moodle.GetSectionsReturn get_sections(java.math.BigInteger client, java.lang.String sesskey, java.lang.String[] courseids, java.lang.String idfield) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get user info from Moodle user
     * 				login
     */
    public com.linkare.rec.am.wsgen.moodle.GetUsersReturn get_user_byusername(java.math.BigInteger client, java.lang.String sesskey, java.lang.String userinfo) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get user info from Moodle user id
     * 				number
     */
    public com.linkare.rec.am.wsgen.moodle.GetUsersReturn get_user_byidnumber(java.math.BigInteger client, java.lang.String sesskey, java.lang.String userinfo) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get user info from Moodle user id
     */
    public com.linkare.rec.am.wsgen.moodle.GetUsersReturn get_user_byid(java.math.BigInteger client, java.lang.String sesskey, java.lang.String userinfo) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get users having a role in a course
     */
    public com.linkare.rec.am.wsgen.moodle.GetUsersReturn get_users_bycourse(java.math.BigInteger client, java.lang.String sesskey, java.lang.String idcourse, java.lang.String idfield, java.math.BigInteger idrole) throws java.rmi.RemoteException;

    /**
     * MoodleWS: count users having a role in a
     * 				course
     */
    public java.math.BigInteger count_users_bycourse(java.math.BigInteger client, java.lang.String sesskey, java.lang.String idcourse, java.lang.String idfield, java.math.BigInteger idrole) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get users members of a group in
     * 				course
     */
    public com.linkare.rec.am.wsgen.moodle.GetUsersReturn get_group_members(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger groupid) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get users members of a grouping in
     * 				course
     */
    public com.linkare.rec.am.wsgen.moodle.GetUsersReturn get_grouping_members(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger groupid) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get user group in course
     */
    public com.linkare.rec.am.wsgen.moodle.GetGroupsReturn get_my_group(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger uid, java.math.BigInteger courseid) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get user groups in all Moodle site
     */
    public com.linkare.rec.am.wsgen.moodle.GetGroupsReturn get_my_groups(java.math.BigInteger client, java.lang.String sesskey, java.lang.String uid, java.lang.String idfield) throws java.rmi.RemoteException;

    /**
     * MoodleWS: get current user Moodle internal id
     * 				(helper)
     */
    public java.math.BigInteger get_my_id(java.math.BigInteger client, java.lang.String sesskey) throws java.rmi.RemoteException;

    /**
     * MoodleWS: get current version
     */
    public java.lang.String get_version(java.math.BigInteger client, java.lang.String sesskey) throws java.rmi.RemoteException;

    /**
     * MoodleWS: check if user has a given role in a
     * 				given course
     */
    public boolean has_role_incourse(java.math.BigInteger client, java.lang.String sesskey, java.lang.String iduser, java.lang.String iduserfield, java.lang.String idcourse, java.lang.String idcoursefield, java.math.BigInteger idrole) throws java.rmi.RemoteException;

    /**
     * MoodleWS: returns user s primary role in a
     * 				given course
     */
    public java.math.BigInteger get_primaryrole_incourse(java.math.BigInteger client, java.lang.String sesskey, java.lang.String iduser, java.lang.String iduserfield, java.lang.String idcourse, java.lang.String idcoursefield) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get user most recent activities in
     * 				a Moodle course
     */
    public com.linkare.rec.am.wsgen.moodle.GetActivitiesReturn get_activities(java.math.BigInteger client, java.lang.String sesskey, java.lang.String iduser, java.lang.String iduserfield, java.lang.String idcourse, java.lang.String idcoursefield, java.math.BigInteger idlimit) throws java.rmi.RemoteException;

    /**
     * MoodleWS: count user most recent activities
     * 				in a Moodle course
     */
    public java.math.BigInteger count_activities(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value1, java.lang.String id1, java.lang.String value2, java.lang.String id2) throws java.rmi.RemoteException;

    /**
     * MoodleWS: get files submitted
     * 				in a Moodle assignment
     */
    public com.linkare.rec.am.wsgen.moodle.GetAssignmentSubmissionsReturn get_assignment_submissions(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger assignmentid, java.lang.String[] userids, java.lang.String useridfield, java.math.BigInteger timemodified) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Edit Label Information
     */
    public com.linkare.rec.am.wsgen.moodle.EditLabelsOutput edit_labels(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.EditLabelsInput labels) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Edit Groups Information
     */
    public com.linkare.rec.am.wsgen.moodle.EditGroupsOutput edit_groups(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.EditGroupsInput groups) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Edit Assignment Information
     */
    public com.linkare.rec.am.wsgen.moodle.EditAssignmentsOutput edit_assignments(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.EditAssignmentsInput assignments) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Edit databases Information
     */
    public com.linkare.rec.am.wsgen.moodle.EditDatabasesOutput edit_databases(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.EditDatabasesInput databases) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Edit Categories Information
     */
    public com.linkare.rec.am.wsgen.moodle.EditCategoriesOutput edit_categories(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.EditCategoriesInput categories) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Edit section Information
     */
    public com.linkare.rec.am.wsgen.moodle.EditSectionsOutput edit_sections(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.EditSectionsInput sections) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Edit Forum Information
     */
    public com.linkare.rec.am.wsgen.moodle.EditForumsOutput edit_forums(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.EditForumsInput forums) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Edit Wikis Information
     */
    public com.linkare.rec.am.wsgen.moodle.EditWikisOutput edit_wikis(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.EditWikisInput wikis) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Edit Page of Wiki Information
     */
    public com.linkare.rec.am.wsgen.moodle.EditPagesWikiOutput edit_pagesWiki(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.EditPagesWikiInput pagesWiki) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Affect Course To Category
     */
    public com.linkare.rec.am.wsgen.moodle.AffectRecord affect_course_to_category(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger courseid, java.math.BigInteger categoryid) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Affect Label to Section
     */
    public com.linkare.rec.am.wsgen.moodle.AffectRecord affect_label_to_section(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger labelid, java.math.BigInteger sectionid) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Affect Forum to Section
     */
    public com.linkare.rec.am.wsgen.moodle.AffectRecord affect_forum_to_section(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger forumid, java.math.BigInteger sectionid, java.math.BigInteger groupmode) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Affect Section To Course
     */
    public com.linkare.rec.am.wsgen.moodle.AffectRecord affect_section_to_course(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger sectionid, java.math.BigInteger courseid) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Affect a user to group
     */
    public com.linkare.rec.am.wsgen.moodle.AffectRecord affect_user_to_group(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger userid, java.math.BigInteger groupid) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Affect a group to course
     */
    public com.linkare.rec.am.wsgen.moodle.AffectRecord affect_group_to_course(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger groupid, java.math.BigInteger coursid) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Affect a wiki to section
     */
    public com.linkare.rec.am.wsgen.moodle.AffectRecord affect_wiki_to_section(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger wikiid, java.math.BigInteger sectionid, java.math.BigInteger groupmode, java.math.BigInteger visible) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Affect a database to section
     */
    public com.linkare.rec.am.wsgen.moodle.AffectRecord affect_database_to_section(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger databaseid, java.math.BigInteger sectionid) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Affect a section to assignment
     */
    public com.linkare.rec.am.wsgen.moodle.AffectRecord affect_assignment_to_section(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger assignmentid, java.math.BigInteger sectionid, java.math.BigInteger groupmode) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Affect user to the course
     */
    public com.linkare.rec.am.wsgen.moodle.AffectRecord affect_user_to_course(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger userid, java.math.BigInteger courseid, java.lang.String rolename) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Affect a page of wiki to a wiki
     */
    public com.linkare.rec.am.wsgen.moodle.AffectRecord affect_pageWiki_to_wiki(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger pageid, java.math.BigInteger wikiid) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Remove the role specified of the
     * 				user in the course
     */
    public com.linkare.rec.am.wsgen.moodle.AffectRecord remove_user_from_course(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger userid, java.math.BigInteger courseid, java.lang.String rolename) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get All Groups
     */
    public com.linkare.rec.am.wsgen.moodle.GetGroupsReturn get_all_groups(java.math.BigInteger client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get All Forums
     */
    public com.linkare.rec.am.wsgen.moodle.GetAllForumsReturn get_all_forums(java.math.BigInteger client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get All Labels
     */
    public com.linkare.rec.am.wsgen.moodle.GetAllLabelsReturn get_all_labels(java.math.BigInteger client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get All wikis
     */
    public com.linkare.rec.am.wsgen.moodle.GetAllWikisReturn get_all_wikis(java.math.BigInteger client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get All Pages Wikis
     */
    public com.linkare.rec.am.wsgen.moodle.GetAllPagesWikiReturn get_all_pagesWiki(java.math.BigInteger client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get All Assignments
     */
    public com.linkare.rec.am.wsgen.moodle.GetAllAssignmentsReturn get_all_assignments(java.math.BigInteger client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get All Databases
     */
    public com.linkare.rec.am.wsgen.moodle.GetAllDatabasesReturn get_all_databases(java.math.BigInteger client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue) throws java.rmi.RemoteException;

    /**
     * MoodleWS: unAffect a user to group
     */
    public com.linkare.rec.am.wsgen.moodle.AffectRecord remove_user_from_group(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger userid, java.math.BigInteger groupid) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Affect a group to grouping
     */
    public com.linkare.rec.am.wsgen.moodle.AffectRecord affect_group_to_grouping(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger groupid, java.math.BigInteger groupingid) throws java.rmi.RemoteException;

    /**
     * MoodleWS: unAffect a group to grouping
     */
    public com.linkare.rec.am.wsgen.moodle.AffectRecord remove_group_from_grouping(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger groupid, java.math.BigInteger groupingid) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Get All groupings
     */
    public com.linkare.rec.am.wsgen.moodle.GetAllGroupingsReturn get_all_groupings(java.math.BigInteger client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue) throws java.rmi.RemoteException;

    /**
     * MoodleWS: Edit Groups Information
     */
    public com.linkare.rec.am.wsgen.moodle.EditGroupingsOutput edit_groupings(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.EditGroupingsInput groupings) throws java.rmi.RemoteException;
}
