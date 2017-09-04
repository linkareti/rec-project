
function test_connection_to_multicasts(prop) {
	var result = true;
	var servers = prop.split(",");
	var message = "";

	for ( var i = 0; i < servers.length - 1; i += 2) {
		if (result) {
			result = result && socket_connect(servers[i], servers[i + 1]);
			socket_disconnect();
			if (message != "") {
				message += "<br />";
			}
			message += servers[i] + ":" + servers[i + 1];
			if (result) {
				message += " ok";
			} else {
				message += " not ok";
			}
		}
	}

	var message_field = document.getElementById('the_message');
	message_field.innerHTML = message;
	if (result) {
		message_field.className = 'elab_info';
	} else {
		message_field.className = 'elab_error';
	}
}

function hideMulticastServerMessage() {
	document.getElementById('elab_launch_form:multicastServerMessage').style.display = "none";
}
