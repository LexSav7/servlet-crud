package com.lexsav.studentapp;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//TODO Make round corners of "Students List" table
//TODO Make sure no empty data can be added

@WebServlet("/StudentController")
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Resource(name="jdbc/students")
	private DataSource dataSource;
	private StudentDbUtil studentDbUtil;
	
	@Override
	public void init() throws ServletException {
		studentDbUtil = new StudentDbUtil(dataSource);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String command = request.getParameter("command");
		
		if (command == null) {
			command = "LIST";
		}
		
		try {
			switch(command) {
				case "LIST": 
					listStudents(request, response);
					break;
					
				case "LOAD": 
					loadStudent(request, response);
					break;
					
				case "SEARCH":
					searchStudents(request, response);
					break;
					
				default:
					listStudents(request, response);
					break;
			}
			
		} catch(Exception e) {
			throw new ServletException(e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String command = request.getParameter("command");
		
		try {
			switch(command) {
				case "ADD":
					addStudent(request, response);
					break;

				case "UPDATE":
					updateStudent(request, response);
					break;

				case "DELETE":
					deleteStudent(request, response);
					break;
					
				default:
					listStudents(request, response);
			}
		
		} catch(Exception e) {
			throw new ServletException(e);
		}
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) 
			throws Exception{
		
		List<Student> students = studentDbUtil.listStudents();
		request.setAttribute("students", students);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("list-students.jsp");
		dispatcher.forward(request, response);
	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		
		String id = request.getParameter("id");
		Student student = studentDbUtil.loadStudent(id);		
		request.setAttribute("student", student);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("update-student.jsp");
		dispatcher.forward(request, response);
	}

	private void searchStudents(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		
		String search = request.getParameter("search-params");
		String[] params = null;
		
		List<Student> students = null;
		List<Student> selectedStudents = new ArrayList<>();
		
		if (search != null) {
			params = search.trim().split("\\s+");
			
			if (params.length > 0) {
				/*
				Already making request attribute for value in the search box.
				So the user could see their already entered value request on the search result page
				*/
				String searchParams = "";
				for (int i = 0; i < params.length; i++) {
					searchParams += params[i] + " ";
					params[i] = params[i].toLowerCase();
				}
				
				request.setAttribute("search", searchParams.trim());
				students = studentDbUtil.listStudents();
			}

			if (params.length == 1) {
				for (Student student : students) {
					
					String[] fullName = new String[2];
					fullName[0] = student.getFirstName().toLowerCase();
					fullName[1] = student.getLastName().toLowerCase();
					
					if (params[0].equals(fullName[0]) ||
						params[0].equals(fullName[1])) {
						
						selectedStudents.add(student);
					}
				}
			}
			
			if (params.length == 2) {
				for (Student student : students) {
					
					String[] fullName = new String[2];
					fullName[0] = student.getFirstName().toLowerCase();
					fullName[1] = student.getLastName().toLowerCase();
					
					if ((params[0].equals(fullName[0]) ||
						 params[0].equals(fullName[1])) 
							&&
						(params[1].equals(fullName[0]) ||
						 params[1].equals(fullName[1]))) {
						
						selectedStudents.add(student);
					}
				}
			}
		}
		
		request.setAttribute("students", selectedStudents);
		RequestDispatcher dispatcher = request.getRequestDispatcher("search-results.jsp");
		dispatcher.forward(request, response);
	}
	
	private void addStudent(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		studentDbUtil.addStudent(firstName, lastName, email);
		response.sendRedirect("StudentController");
	}
	
	private void updateStudent(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		
		String id = request.getParameter("id");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		studentDbUtil.updateStudent(id, firstName, lastName, email);
		response.sendRedirect("StudentController");
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		studentDbUtil.deleteStudent(id);
		response.sendRedirect("StudentController");
	}
}
