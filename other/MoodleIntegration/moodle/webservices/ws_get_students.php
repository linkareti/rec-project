<?php
/*
 * Created on 2009/02/24
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once("../config.php");

require_once($CFG->dirroot . '/lib/nusoap/nusoap.php');

$server = new soap_server();

$server->configureWSDL('moodle_get_students', 'urn:moodle_get_students', false, 'document');

$server->wsdl->addComplexType(
	'Student',
	'complexType',
	'struct',
	'all',
	'',
    array(
        'id' => array('name' => 'id', 'type' => 'xsd:string'),
        'firstname' => array('name' => 'firstname', 'type' => 'xsd:string'),
        'lastname' => array('name' => 'lastname', 'type' => 'xsd:string')
    )
);

$server->wsdl->addComplexType(
	'StudentList',
	'complexType',
	'struct',
	'sequence',
	'',
    array(
        'student' => array('name' => 'student', 'type' => 'tns:Student', 'minOccurs' => '0', 'maxOccurs' => 'unbounded')
    )
);

$server->register(	'ws_get_students_by_course',
					array('course_id' => 'xsd:string'),
					array('result' => 'tns:StudentList'),
					'urn:moodle_get_students',
					'urn:moodle_get_students#get_all_students_by_course',
					'document',
					'literal',
					'Moodle Get Students By Course');

function ws_get_students_by_course($course_id) {
	$users = elab_get_students_associated_course($course_id, 'u.firstname ASC', 'u.id, u.firstname, u.lastname');
	return array ('student' => $users);
}

$HTTP_RAW_POST_DATA = isset($HTTP_RAW_POST_DATA) ? $HTTP_RAW_POST_DATA : '';
$server->service($HTTP_RAW_POST_DATA);

?>