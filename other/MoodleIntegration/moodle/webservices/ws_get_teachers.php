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

$server->configureWSDL('moodle_get_teachers', 'urn:moodle_get_teachers', false, 'document');

$server->wsdl->addComplexType(
	'Teacher',
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
	'TeacherList',
	'complexType',
	'struct',
	'sequence',
	'',
    array(
        'teacher' => array('name' => 'teacher', 'type' => 'tns:Teacher', 'minOccurs' => '0', 'maxOccurs' => 'unbounded')
    )
);

$server->register(	'ws_get_teachers_by_course',
					array('course_id' => 'xsd:string'),
					array('result' => 'tns:TeacherList'),
					'urn:moodle_get_teachers',
					'urn:moodle_get_teachers#get_all_teachers_by_course',
					'document',
					'literal',
					'Moodle Get Teachers By Course');

function ws_get_teachers_by_course($course_id) {
	$users = elab_get_teachers_associated_course($course_id, 'u.firstname ASC', 'u.id, u.firstname, u.lastname');
	return array ('teacher' => $users);
}

$HTTP_RAW_POST_DATA = isset($HTTP_RAW_POST_DATA) ? $HTTP_RAW_POST_DATA : '';
$server->service($HTTP_RAW_POST_DATA);

?>
