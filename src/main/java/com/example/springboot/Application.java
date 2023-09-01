package com.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		// Create a RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		// Define the base URL of your Spring Boot application
		String baseUrl = "http://localhost:8080";

		// Test CRUD operations
		testCreateStudent(restTemplate, baseUrl);
		testGetAllStudents(restTemplate, baseUrl);
		testGetStudentById(restTemplate, baseUrl, 1);
		testUpdateStudent(restTemplate, baseUrl, 1);
		testDeleteStudent(restTemplate, baseUrl, 1);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplateBuilder().build();
	}

	private static void testCreateStudent(RestTemplate restTemplate, String baseUrl) {
		String url = baseUrl + "/api/students";
		System.out.println("Testing POST /api/students");

		// Create a new student object
		Student newStudent = new Student();
		newStudent.setFname("Lavanya");
		newStudent.setLname("Ravichandran");
		newStudent.setAge(20);
		newStudent.setEmail("new.student@example.com");

		// Perform the POST request to create a new student
		Student createdStudent = restTemplate.postForObject(url, newStudent, Student.class);
		System.out.println("Created student: " + createdStudent.toString());
	}

	private static void testGetAllStudents(RestTemplate restTemplate, String baseUrl) {
		String url = baseUrl + "/api/students";
		System.out.println("Testing GET /api/students");
		Student[] students = restTemplate.getForObject(url, Student[].class);
		for (Student student : students) {
			System.out.println(student.toString());
		}
	}

	private static void testGetStudentById(RestTemplate restTemplate, String baseUrl, int studentId) {
		String url = baseUrl + "/api/students/" + studentId;
		System.out.println("Testing GET /api/students/" + studentId);
		Student student = restTemplate.getForObject(url, Student.class);
		if (student != null) {
			System.out.println(student.toString());
		} else {
			System.out.println("Student not found with ID " + studentId);
		}
	}

	private static void testUpdateStudent(RestTemplate restTemplate, String baseUrl, int studentId) {
		String url = baseUrl + "/api/students/" + studentId;
		System.out.println("Testing PUT /api/students/" + studentId);

		// Retrieve the student to update
		Student studentToUpdate = restTemplate.getForObject(url, Student.class);
		if (studentToUpdate != null) {
			// Modify student data
			studentToUpdate.setFname("Ramakrishnan");
			studentToUpdate.setLname("Ravichandran");

			// Perform the PUT request to update the student
			restTemplate.put(url, studentToUpdate);
			System.out.println("Updated student: " + studentToUpdate.toString());
		} else {
			System.out.println("Student not found with ID " + studentId);
		}
	}

	private static void testDeleteStudent(RestTemplate restTemplate, String baseUrl, int studentId) {
		String url = baseUrl + "/api/students/" + studentId;
		System.out.println("Testing DELETE /api/students/" + studentId);

		// Perform the DELETE request to delete the student
		restTemplate.delete(url);
		System.out.println("Student with ID " + studentId + " deleted.");
	}

}
