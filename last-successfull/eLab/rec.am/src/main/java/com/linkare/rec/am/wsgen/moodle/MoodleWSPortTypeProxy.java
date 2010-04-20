package com.linkare.rec.am.wsgen.moodle;

public class MoodleWSPortTypeProxy implements com.linkare.rec.am.wsgen.moodle.MoodleWSPortType {
  private String _endpoint = null;
  private com.linkare.rec.am.wsgen.moodle.MoodleWSPortType moodleWSPortType = null;
  
  public MoodleWSPortTypeProxy() {
    _initMoodleWSPortTypeProxy();
  }
  
  public MoodleWSPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initMoodleWSPortTypeProxy();
  }
  
  private void _initMoodleWSPortTypeProxy() {
    try {
      moodleWSPortType = (new com.linkare.rec.am.wsgen.moodle.MoodleWSLocator()).getMoodleWSPort();
      if (moodleWSPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)moodleWSPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)moodleWSPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (moodleWSPortType != null)
      ((javax.xml.rpc.Stub)moodleWSPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.linkare.rec.am.wsgen.moodle.MoodleWSPortType getMoodleWSPortType() {
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType;
  }
  
  public com.linkare.rec.am.wsgen.moodle.LoginReturn login(java.lang.String username, java.lang.String password) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.login(username, password);
  }
  
  public boolean logout(java.math.BigInteger client, java.lang.String sesskey) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.logout(client, sesskey);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetCoursesReturn get_course(java.math.BigInteger client, java.lang.String sesskey, java.lang.String courseid, java.lang.String idfield) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_course(client, sesskey, courseid, idfield);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetCoursesReturn get_course_byid(java.math.BigInteger client, java.lang.String sesskey, java.lang.String info) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_course_byid(client, sesskey, info);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetCoursesReturn get_course_byidnumber(java.math.BigInteger client, java.lang.String sesskey, java.lang.String info) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_course_byidnumber(client, sesskey, info);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetGroupsReturn get_groups_bycourse(java.math.BigInteger client, java.lang.String sesskey, java.lang.String courseid, java.lang.String idfield) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_groups_bycourse(client, sesskey, courseid, idfield);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetGroupsReturn get_group_byid(java.math.BigInteger client, java.lang.String sesskey, java.lang.String info, java.math.BigInteger courseid) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_group_byid(client, sesskey, info, courseid);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetGroupsReturn get_groups_byname(java.math.BigInteger client, java.lang.String sesskey, java.lang.String info, java.math.BigInteger courseid) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_groups_byname(client, sesskey, info, courseid);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetUsersReturn get_user(java.math.BigInteger client, java.lang.String sesskey, java.lang.String userid, java.lang.String idfield) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_user(client, sesskey, userid, idfield);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditUsersOutput edit_users(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.EditUsersInput users) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.edit_users(client, sesskey, users);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditCoursesOutput add_course(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.CourseDatum course) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.add_course(client, sesskey, course);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditUsersOutput add_user(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.UserDatum user) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.add_user(client, sesskey, user);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditGroupsOutput add_group(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.GroupDatum group) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.add_group(client, sesskey, group);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditGroupingsOutput add_grouping(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.GroupingDatum grouping) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.add_grouping(client, sesskey, grouping);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditCoursesOutput delete_course(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value, java.lang.String id) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.delete_course(client, sesskey, value, id);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditUsersOutput delete_user(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value, java.lang.String id) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.delete_user(client, sesskey, value, id);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditGroupsOutput delete_group(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value, java.lang.String id) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.delete_group(client, sesskey, value, id);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditGroupingsOutput delete_grouping(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value, java.lang.String id) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.delete_grouping(client, sesskey, value, id);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditCoursesOutput update_course(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.CourseDatum course, java.lang.String idfield) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.update_course(client, sesskey, course, idfield);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditUsersOutput update_user(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.UserDatum user, java.lang.String idfield) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.update_user(client, sesskey, user, idfield);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditGroupsOutput update_group(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.GroupDatum group, java.lang.String idfield) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.update_group(client, sesskey, group, idfield);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditGroupingsOutput update_grouping(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.GroupingDatum grouping, java.lang.String idfield) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.update_grouping(client, sesskey, grouping, idfield);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditCategoriesOutput add_category(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.CategoryDatum category) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.add_category(client, sesskey, category);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditSectionsOutput add_section(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.SectionDatum section) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.add_section(client, sesskey, section);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditSectionsOutput update_section(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.SectionDatum section, java.lang.String idfield) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.update_section(client, sesskey, section, idfield);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditLabelsOutput add_label(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.LabelDatum label) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.add_label(client, sesskey, label);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditForumsOutput add_forum(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.ForumDatum forum) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.add_forum(client, sesskey, forum);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditAssignmentsOutput add_assignment(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.AssignmentDatum assignment) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.add_assignment(client, sesskey, assignment);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditDatabasesOutput add_database(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.DatabaseDatum database) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.add_database(client, sesskey, database);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditWikisOutput add_wiki(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.WikiDatum wiki) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.add_wiki(client, sesskey, wiki);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditPagesWikiOutput add_pagewiki(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.PageWikiDatum page) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.add_pagewiki(client, sesskey, page);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetUsersReturn get_users(java.math.BigInteger client, java.lang.String sesskey, java.lang.String[] userids, java.lang.String idfield) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_users(client, sesskey, userids, idfield);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetUsersReturn get_teachers(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value, java.lang.String id) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_teachers(client, sesskey, value, id);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetUsersReturn get_students(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value, java.lang.String id) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_students(client, sesskey, value, id);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditCoursesOutput edit_courses(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.EditCoursesInput courses) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.edit_courses(client, sesskey, courses);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetCoursesReturn get_courses(java.math.BigInteger client, java.lang.String sesskey, java.lang.String[] courseids, java.lang.String idfield) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_courses(client, sesskey, courseids, idfield);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetResourcesReturn get_resources(java.math.BigInteger client, java.lang.String sesskey, java.lang.String[] courseids, java.lang.String idfield) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_resources(client, sesskey, courseids, idfield);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetResourcesReturn get_instances_bytype(java.math.BigInteger client, java.lang.String sesskey, java.lang.String[] courseids, java.lang.String idfield, java.lang.String type) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_instances_bytype(client, sesskey, courseids, idfield, type);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetGradesReturn get_grades(java.math.BigInteger client, java.lang.String sesskey, java.lang.String userid, java.lang.String userfield, java.lang.String[] courseids, java.lang.String courseidfield) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_grades(client, sesskey, userid, userfield, courseids, courseidfield);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetGradesReturn get_course_grades(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value, java.lang.String id) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_course_grades(client, sesskey, value, id);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetGradesReturn get_user_grades(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value, java.lang.String id) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_user_grades(client, sesskey, value, id);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EnrolStudentsReturn enrol_students(java.math.BigInteger client, java.lang.String sesskey, java.lang.String courseid, java.lang.String courseidfield, java.lang.String[] userids, java.lang.String useridfield) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.enrol_students(client, sesskey, courseid, courseidfield, userids, useridfield);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EnrolStudentsReturn unenrol_students(java.math.BigInteger client, java.lang.String sesskey, java.lang.String courseid, java.lang.String courseidfield, java.lang.String[] userids, java.lang.String useridfield) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.unenrol_students(client, sesskey, courseid, courseidfield, userids, useridfield);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetRolesReturn get_roles(java.math.BigInteger client, java.lang.String sesskey) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_roles(client, sesskey);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetRolesReturn get_role_byid(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_role_byid(client, sesskey, value);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetRolesReturn get_role_byname(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_role_byname(client, sesskey, value);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetEventsReturn get_events(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger eventtype, java.math.BigInteger ownerid) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_events(client, sesskey, eventtype, ownerid);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetLastChangesReturn get_last_changes(java.math.BigInteger client, java.lang.String sesskey, java.lang.String courseid, java.lang.String idfield, java.math.BigInteger limit) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_last_changes(client, sesskey, courseid, idfield, limit);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetCategoriesReturn get_categories(java.math.BigInteger client, java.lang.String sesskey) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_categories(client, sesskey);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetCategoriesReturn get_category_byid(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_category_byid(client, sesskey, value);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetCategoriesReturn get_category_byname(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_category_byname(client, sesskey, value);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetCoursesReturn get_my_courses(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger uid, java.lang.String sort) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_my_courses(client, sesskey, uid, sort);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetCoursesReturn get_my_courses_byidnumber(java.math.BigInteger client, java.lang.String sesskey, java.lang.String uinfo, java.lang.String sort) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_my_courses_byidnumber(client, sesskey, uinfo, sort);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetCoursesReturn get_my_courses_byusername(java.math.BigInteger client, java.lang.String sesskey, java.lang.String uinfo, java.lang.String sort) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_my_courses_byusername(client, sesskey, uinfo, sort);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetCoursesReturn get_courses_bycategory(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger categoryid) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_courses_bycategory(client, sesskey, categoryid);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetSectionsReturn get_sections(java.math.BigInteger client, java.lang.String sesskey, java.lang.String[] courseids, java.lang.String idfield) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_sections(client, sesskey, courseids, idfield);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetUsersReturn get_user_byusername(java.math.BigInteger client, java.lang.String sesskey, java.lang.String userinfo) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_user_byusername(client, sesskey, userinfo);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetUsersReturn get_user_byidnumber(java.math.BigInteger client, java.lang.String sesskey, java.lang.String userinfo) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_user_byidnumber(client, sesskey, userinfo);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetUsersReturn get_user_byid(java.math.BigInteger client, java.lang.String sesskey, java.lang.String userinfo) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_user_byid(client, sesskey, userinfo);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetUsersReturn get_users_bycourse(java.math.BigInteger client, java.lang.String sesskey, java.lang.String idcourse, java.lang.String idfield, java.math.BigInteger idrole) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_users_bycourse(client, sesskey, idcourse, idfield, idrole);
  }
  
  public java.math.BigInteger count_users_bycourse(java.math.BigInteger client, java.lang.String sesskey, java.lang.String idcourse, java.lang.String idfield, java.math.BigInteger idrole) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.count_users_bycourse(client, sesskey, idcourse, idfield, idrole);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetUsersReturn get_group_members(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger groupid) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_group_members(client, sesskey, groupid);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetUsersReturn get_grouping_members(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger groupid) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_grouping_members(client, sesskey, groupid);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetGroupsReturn get_my_group(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger uid, java.math.BigInteger courseid) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_my_group(client, sesskey, uid, courseid);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetGroupsReturn get_my_groups(java.math.BigInteger client, java.lang.String sesskey, java.lang.String uid, java.lang.String idfield) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_my_groups(client, sesskey, uid, idfield);
  }
  
  public java.math.BigInteger get_my_id(java.math.BigInteger client, java.lang.String sesskey) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_my_id(client, sesskey);
  }
  
  public java.lang.String get_version(java.math.BigInteger client, java.lang.String sesskey) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_version(client, sesskey);
  }
  
  public boolean has_role_incourse(java.math.BigInteger client, java.lang.String sesskey, java.lang.String iduser, java.lang.String iduserfield, java.lang.String idcourse, java.lang.String idcoursefield, java.math.BigInteger idrole) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.has_role_incourse(client, sesskey, iduser, iduserfield, idcourse, idcoursefield, idrole);
  }
  
  public java.math.BigInteger get_primaryrole_incourse(java.math.BigInteger client, java.lang.String sesskey, java.lang.String iduser, java.lang.String iduserfield, java.lang.String idcourse, java.lang.String idcoursefield) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_primaryrole_incourse(client, sesskey, iduser, iduserfield, idcourse, idcoursefield);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetActivitiesReturn get_activities(java.math.BigInteger client, java.lang.String sesskey, java.lang.String iduser, java.lang.String iduserfield, java.lang.String idcourse, java.lang.String idcoursefield, java.math.BigInteger idlimit) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_activities(client, sesskey, iduser, iduserfield, idcourse, idcoursefield, idlimit);
  }
  
  public java.math.BigInteger count_activities(java.math.BigInteger client, java.lang.String sesskey, java.lang.String value1, java.lang.String id1, java.lang.String value2, java.lang.String id2) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.count_activities(client, sesskey, value1, id1, value2, id2);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetAssignmentSubmissionsReturn get_assignment_submissions(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger assignmentid, java.lang.String[] userids, java.lang.String useridfield, java.math.BigInteger timemodified) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_assignment_submissions(client, sesskey, assignmentid, userids, useridfield, timemodified);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditLabelsOutput edit_labels(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.EditLabelsInput labels) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.edit_labels(client, sesskey, labels);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditGroupsOutput edit_groups(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.EditGroupsInput groups) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.edit_groups(client, sesskey, groups);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditAssignmentsOutput edit_assignments(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.EditAssignmentsInput assignments) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.edit_assignments(client, sesskey, assignments);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditDatabasesOutput edit_databases(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.EditDatabasesInput databases) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.edit_databases(client, sesskey, databases);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditCategoriesOutput edit_categories(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.EditCategoriesInput categories) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.edit_categories(client, sesskey, categories);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditSectionsOutput edit_sections(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.EditSectionsInput sections) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.edit_sections(client, sesskey, sections);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditForumsOutput edit_forums(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.EditForumsInput forums) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.edit_forums(client, sesskey, forums);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditWikisOutput edit_wikis(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.EditWikisInput wikis) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.edit_wikis(client, sesskey, wikis);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditPagesWikiOutput edit_pagesWiki(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.EditPagesWikiInput pagesWiki) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.edit_pagesWiki(client, sesskey, pagesWiki);
  }
  
  public com.linkare.rec.am.wsgen.moodle.AffectRecord affect_course_to_category(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger courseid, java.math.BigInteger categoryid) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.affect_course_to_category(client, sesskey, courseid, categoryid);
  }
  
  public com.linkare.rec.am.wsgen.moodle.AffectRecord affect_label_to_section(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger labelid, java.math.BigInteger sectionid) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.affect_label_to_section(client, sesskey, labelid, sectionid);
  }
  
  public com.linkare.rec.am.wsgen.moodle.AffectRecord affect_forum_to_section(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger forumid, java.math.BigInteger sectionid, java.math.BigInteger groupmode) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.affect_forum_to_section(client, sesskey, forumid, sectionid, groupmode);
  }
  
  public com.linkare.rec.am.wsgen.moodle.AffectRecord affect_section_to_course(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger sectionid, java.math.BigInteger courseid) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.affect_section_to_course(client, sesskey, sectionid, courseid);
  }
  
  public com.linkare.rec.am.wsgen.moodle.AffectRecord affect_user_to_group(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger userid, java.math.BigInteger groupid) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.affect_user_to_group(client, sesskey, userid, groupid);
  }
  
  public com.linkare.rec.am.wsgen.moodle.AffectRecord affect_group_to_course(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger groupid, java.math.BigInteger coursid) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.affect_group_to_course(client, sesskey, groupid, coursid);
  }
  
  public com.linkare.rec.am.wsgen.moodle.AffectRecord affect_wiki_to_section(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger wikiid, java.math.BigInteger sectionid, java.math.BigInteger groupmode, java.math.BigInteger visible) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.affect_wiki_to_section(client, sesskey, wikiid, sectionid, groupmode, visible);
  }
  
  public com.linkare.rec.am.wsgen.moodle.AffectRecord affect_database_to_section(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger databaseid, java.math.BigInteger sectionid) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.affect_database_to_section(client, sesskey, databaseid, sectionid);
  }
  
  public com.linkare.rec.am.wsgen.moodle.AffectRecord affect_assignment_to_section(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger assignmentid, java.math.BigInteger sectionid, java.math.BigInteger groupmode) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.affect_assignment_to_section(client, sesskey, assignmentid, sectionid, groupmode);
  }
  
  public com.linkare.rec.am.wsgen.moodle.AffectRecord affect_user_to_course(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger userid, java.math.BigInteger courseid, java.lang.String rolename) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.affect_user_to_course(client, sesskey, userid, courseid, rolename);
  }
  
  public com.linkare.rec.am.wsgen.moodle.AffectRecord affect_pageWiki_to_wiki(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger pageid, java.math.BigInteger wikiid) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.affect_pageWiki_to_wiki(client, sesskey, pageid, wikiid);
  }
  
  public com.linkare.rec.am.wsgen.moodle.AffectRecord remove_user_from_course(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger userid, java.math.BigInteger courseid, java.lang.String rolename) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.remove_user_from_course(client, sesskey, userid, courseid, rolename);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetGroupsReturn get_all_groups(java.math.BigInteger client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_all_groups(client, sesskey, fieldname, fieldvalue);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetAllForumsReturn get_all_forums(java.math.BigInteger client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_all_forums(client, sesskey, fieldname, fieldvalue);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetAllLabelsReturn get_all_labels(java.math.BigInteger client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_all_labels(client, sesskey, fieldname, fieldvalue);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetAllWikisReturn get_all_wikis(java.math.BigInteger client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_all_wikis(client, sesskey, fieldname, fieldvalue);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetAllPagesWikiReturn get_all_pagesWiki(java.math.BigInteger client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_all_pagesWiki(client, sesskey, fieldname, fieldvalue);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetAllAssignmentsReturn get_all_assignments(java.math.BigInteger client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_all_assignments(client, sesskey, fieldname, fieldvalue);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetAllDatabasesReturn get_all_databases(java.math.BigInteger client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_all_databases(client, sesskey, fieldname, fieldvalue);
  }
  
  public com.linkare.rec.am.wsgen.moodle.AffectRecord remove_user_from_group(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger userid, java.math.BigInteger groupid) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.remove_user_from_group(client, sesskey, userid, groupid);
  }
  
  public com.linkare.rec.am.wsgen.moodle.AffectRecord affect_group_to_grouping(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger groupid, java.math.BigInteger groupingid) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.affect_group_to_grouping(client, sesskey, groupid, groupingid);
  }
  
  public com.linkare.rec.am.wsgen.moodle.AffectRecord remove_group_from_grouping(java.math.BigInteger client, java.lang.String sesskey, java.math.BigInteger groupid, java.math.BigInteger groupingid) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.remove_group_from_grouping(client, sesskey, groupid, groupingid);
  }
  
  public com.linkare.rec.am.wsgen.moodle.GetAllGroupingsReturn get_all_groupings(java.math.BigInteger client, java.lang.String sesskey, java.lang.String fieldname, java.lang.String fieldvalue) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.get_all_groupings(client, sesskey, fieldname, fieldvalue);
  }
  
  public com.linkare.rec.am.wsgen.moodle.EditGroupingsOutput edit_groupings(java.math.BigInteger client, java.lang.String sesskey, com.linkare.rec.am.wsgen.moodle.EditGroupingsInput groupings) throws java.rmi.RemoteException{
    if (moodleWSPortType == null)
      _initMoodleWSPortTypeProxy();
    return moodleWSPortType.edit_groupings(client, sesskey, groupings);
  }
  
  
}