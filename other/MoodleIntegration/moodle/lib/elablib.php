<?php
/**
 * 
 * Library of functions for moodle/eLab integration
 * 
 *  
 */
 
/**
 * This function is a generic one to get users associated with a course
 * 
 * Use elab_get_students_associated_course or elab_get_teachers_associated_course
 */
function elab_get_users_associated_course_by_role_name($courseid, $rolename, $sort='u.firstname ASC', $fields='u.*') {
	global $CFG;

    if (empty($sort)) {
        $sortstatement = "";
    } else {
        $sortstatement = "ORDER BY $sort";
    }
	$sql = "SELECT $fields
              FROM {$CFG->prefix}course c
                JOIN {$CFG->prefix}context ctx
                  ON (c.id = ctx.instanceid 
                    AND ctx.contextlevel=". CONTEXT_COURSE ." AND c.id = $courseid)
                JOIN {$CFG->prefix}role_assignments ra
                  ON (ra.contextid = ctx.id)
                JOIN {$CFG->prefix}role r 
                  ON (ra.roleid = r.id AND r.name = '$rolename')
                JOIN {$CFG->prefix}user u
                  ON (u.id = ra.userid)
                $sortstatement";

	$users = get_records_sql($sql);
	return $users;	
}

/**
 * 
 * Gives a list of students enrolled in a course
 */
function elab_get_students_associated_course($courseid, $sort='u.firstname ASC', $fields='u.*') {
	return elab_get_users_associated_course_by_role_name($courseid, 'Student', $sort='u.firstname ASC', $fields='u.*');
}

/**
 * 
 * Gives a list of teachers bounded to a course
 */
function elab_get_teachers_associated_course($courseid, $sort='u.firstname ASC', $fields='u.*') {
	return elab_get_users_associated_course_by_role_name($courseid, 'Teacher', $sort='u.firstname ASC', $fields='u.*');
}
 
?>
