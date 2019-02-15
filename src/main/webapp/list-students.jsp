<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css" integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz" crossorigin="anonymous">
	<link rel="stylesheet" href="css/general.css"/>
	<link rel="stylesheet" href="css/list-students.css"/>
	<title>List of Students</title>
</head>

<body>
<div id="container">
<div id="content">

	<jsp:include page="header.html"/>
	
	<div id="flex">
		<form class="form-inline" action="add-student.jsp">
			<button type="submit" class="btn-add-student">Add Student</button>
		</form>
		
		<form id="search" class="form-inline" action="StudentController">
			<input type="hidden" name="command" value="SEARCH"/>
			<input name="search-params" placeholder="Search by name"/>
			<button type="submit">
				<i class="fas fa-search"></i>
			</button>
		</form>
	</div>
	
	<div>
	<table>
		<tr>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Email</th>
			<th>Action</th>
		</tr>
		
		<c:choose>
		<c:when test="${not empty students}">
			<c:forEach var="student" items="${students}">
			
				<c:url var="loadStudentLink" value="StudentController">
					<c:param name="command" value="LOAD"/>
					<c:param name="id" value="${student.id}"/>
				</c:url>
				
				<tr>
					<td>${student.firstName}</td>
					<td>${student.lastName}</td>
					<td>${student.email}</td>
					<td>
						<a href="${loadStudentLink}">Update</a> |
						<form class="form-inline" action="StudentController" method="post">
							<input type="hidden" name="command" value="DELETE"/>
							<input type="hidden" name="id" value="${student.id}"/>
							<button type="submit" class="link"
							onclick="return confirm('Do you want to delete this student?')">Delete</button>
						</form>
					</td>
				</tr>	
			</c:forEach>
		</c:when>
		
		<c:otherwise>
			<tr>
			<td id="no-students" colspan="4">
				No Students Added
			</td>
			</tr>
		</c:otherwise>
		</c:choose>
		
	</table>
	</div>
	
</div>
</div>

</body>
</html>