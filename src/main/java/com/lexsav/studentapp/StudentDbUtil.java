package com.lexsav.studentapp;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDbUtil {
	
	private DataSource dataSource;

	public StudentDbUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<Student> listStudents() throws Exception {
		String sql = "SELECT * FROM students ORDER BY id";
		List<Student> students = new ArrayList<>();
		
		try(
			Connection conn = dataSource.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
		){
			while(rs.next()) {
				
				String id  = rs.getString("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				
				students.add(new Student(id, firstName, lastName, email));
			}
		}
		
		return students;
	}

	public void addStudent(String firstName, String lastName, String email) 
			throws Exception {
		
		String sql = "INSERT INTO students(first_name, last_name, email) "
				   + "VALUES(?, ?, ?)";
		
		try(
			Connection conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
		){
			stmt.setString(1, firstName);
			stmt.setString(2, lastName);
			stmt.setString(3, email);
			
			stmt.executeUpdate();
		}
	}

	public Student loadStudent(String id) throws Exception {
		
		Student student = null;
		String sql = "SELECT * FROM students WHERE id = " + id;
		
		try(
			Connection conn = dataSource.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
		){
			rs.next();
			
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			String email = rs.getString("email");
			
			student = new Student(id, firstName, lastName, email);
		}
		
		return student;
	}

	public void updateStudent(String id, String firstName, String lastName, String email) 
			throws Exception {
		
		String sql = "UPDATE students SET "
				   + "first_name = ?, last_name = ?, email = ? "
				   + "WHERE id = ?";
		
		try(
			Connection conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
		){
			stmt.setString(1, firstName);
			stmt.setString(2, lastName);
			stmt.setString(3, email);
			stmt.setString(4, id);
			
			stmt.executeUpdate();
		}
	}

	public void deleteStudent(String id) throws Exception {
		String sql = "DELETE FROM students WHERE id = " + id;
		
		try(
			Connection conn = dataSource.getConnection();
			Statement stmt = conn.createStatement();
		){
			stmt.executeUpdate(sql);
		}
	}
	
	

}
