<?php
/*
 * Created on 2009/01/15
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
require_once("../config.php");

require_once($CFG->dirroot . '/lib/nusoap/nusoap.php');

$server = new soap_server();

$server->configureWSDL('moodleauth', 'urn:moodleauth', false, 'document');

$server->register(	'wsautheticate',
					array('login' => 'xsd:string', 'password' => 'xsd:string'),
					array('result' => 'xsd:boolean'),
					'urn:moodleauth',
					'urn:moodleauth#autheticate',
					'document',
					'literal',
					'Moodle Authentication');

function wsautheticate($login, $password) {
	return authenticate_user_login($login, $password);
}
	
$HTTP_RAW_POST_DATA = isset($HTTP_RAW_POST_DATA) ? $HTTP_RAW_POST_DATA : '';
$server->service($HTTP_RAW_POST_DATA);

?>
