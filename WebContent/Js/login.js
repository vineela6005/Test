function formValidation() {
	var uid = document.loginform.id;
	var passid = document.loginform.upass;
	if (userid_validation(uid, 5, 12)) {
		if (passid_validation(passid, 7, 12)) {

			return true;
		}
	}
	return false;
}
function userid_validation(id, mx, my) {
	var format="[A-Z][a-z]";
	var uid=id.match(format);
	var uid_len = id.value.length;
	if (uid_len == 0 || uid_len >= my || uid_len < mx || uid==false) {
		alert("User Id should start with capital not be empty , length be between " + mx + " to "
				+ my);
		id.focus();
		return false;
	}
	return true;
}
function passid_validation(upass, mx, my) {
	var passid_len = upass.value.length;
	if (passid_len == 0 || passid_len >= my || passid_len < mx) {
		alert("Password should not be empty / length be between " + mx + " to "
				+ my);
		upass.focus();
		return false;
	}
	return true;
}