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

$server->configureWSDL('moodle_get_courses', 'urn:moodle_get_courses', false, 'document');

$server->wsdl->addComplexType(
	'Course',
	'complexType',
	'struct',
	'all',
	'',
    array(
        'id' => array('name' => 'id', 'type' => 'xsd:string'),
        'sortorder' => array('name' => 'sortorder', 'type' => 'xsd:string'),
        'shortname' => array('name' => 'shortname', 'type' => 'xsd:string'),
        'fullname' => array('name' => 'fullname', 'type' => 'xsd:string')
    )
);

$server->wsdl->addComplexType(
	'CourseList',
	'complexType',
	'struct',
	'sequence',
	'',
    array(
        'course' => array('name' => 'course', 'type' => 'tns:Course', 'minOccurs' => '0', 'maxOccurs' => 'unbounded')
    )
);

$server->register(	'ws_get_all_courses',
					array(),
					array('result' => 'tns:CourseList'),
					'urn:moodle_get_courses',
					'urn:moodle_get_courses#get_all_courses',
					'document',
					'literal',
					'Moodle Get All Courses');


$server->register(	'ws_get_courses_by_category',
					array('category_id' => 'xsd:string'),
					array('result' => 'tns:CourseList'),
					'urn:moodle_get_courses',
					'urn:moodle_get_courses#get_courses_by_category',
					'document',
					'literal',
					'Moodle Get Courses By Category');

function ws_get_all_courses() {
	$courses = get_courses("all", "c.sortorder ASC", "c.id, c.sortorder, c.shortname, c.fullname");
	return array ('course' => $courses);
}

function ws_get_courses_by_category($category) {
	$courses = get_courses($category, "c.sortorder ASC", "c.id, c.sortorder, c.shortname, c.fullname");
	return array ('course' => $courses);
}


$HTTP_RAW_POST_DATA = isset($HTTP_RAW_POST_DATA) ? $HTTP_RAW_POST_DATA : '';
$server->service($HTTP_RAW_POST_DATA);

?>