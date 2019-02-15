<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="css/general.css">
	<link rel="stylesheet" href="css/student-form.css">
	<title>Add Student</title>
</head>

<body>
<div id="container">
<div id="content">

	<jsp:include page="header.html"/>
	
	<div id="indent">
		<div>
			<h3>Add Student</h3>
		</div>
		
		<div>
			<form action="StudentController" method="post">
			<input type="hidden" name="command" value="ADD"/>
			
			<table>
				<tr>
					<td>First Name:</td>
					<td><input name="firstName"/></td>
				</tr>
				
				<tr>
					<td>Last Name:</td>
					<td><input name="lastName"/></td>
				</tr>
				
				<tr>
					<td>Email:</td>
					<td><input name="email"/></td>
				</tr>
				
				<tr>
					<td></td>
					<td>
						<button type="submit" class="btn-save-student">Save</button>
					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
	
	<div>
	<a href="StudentController">Back to the List</a>
	</div>

</div>
</div>

</body>
</html>