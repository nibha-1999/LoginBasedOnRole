Spring Boot Role-Based Authentication
Overview
This Spring Boot application implements a role-based authentication system. The application utilizes Spring Security to manage user authentication and authorization based on assigned roles. This README provides an overview of the project structure, configuration, and instructions for running the application.


Project Structure
The project structure follows standard Spring Boot conventions:

LoginBasedOnRole/
|-- .mvn/
|   |-- wrapper/
|   |   |-- maven-wrapper.jar
|   |   |-- maven-wrapper.properties
|
|-- src/
|   |-- main/
|   |   |-- java/
|   |   |   |-- com.jwt.implementation/
|   |   |   |   |-- config/
|   |   |   |   |   |-- JwtFilter.java
|   |   |   |   |   |-- JwtGeneratorValidator.java
|   |   |   |   |   |-- SecurityConfig.java
|   |   |   |   |
|   |   |   |   |-- controller/
|   |   |   |   |   |-- RestAppController.java
|   |   |   |   |
|   |   |   |   |-- error/
|   |   |   |   |   |-- ApiError.java
|   |   |   |   |
|   |   |   |   |-- model/
|   |   |   |   |   |-- Role.java
|   |   |   |   |   |-- User.java
|   |   |   |   |   |-- UserDTO.java
|   |   |   |   |
|   |   |   |   |-- repository/
|   |   |   |   |   |-- RoleRepository.java
|   |   |   |   |   |-- UserRepository.java
|   |   |   |   |
|   |   |   |   |-- service/
|   |   |   |   |   |-- DefaultUserService.java
|   |   |   |   |   |-- DefaultUserServiceImpl.java
|   |   |   |
|   |   |   |-- JwtWithRoleApplication.java
|   |   |
|   |-- resources/
|   |   |-- application.properties
|   |
|   |-- test/
|       |-- java/
|           |-- com/
		|-- jwt/
		    -- implementation/
|                      |-- controller/
|                      |   |-- RestAppControllerTest.java
|                      |
|                      |-- service/
|                          |-- DefaultUserServiceImplTest.java
|		       |-- JwtWithRoleApplicationTests.java
|
|-- .gitignore
|-- README.md
|-- pom.xml

Configuration
Security Configuration
The security configuration is defined in the SecurityConfig.java file. It includes configuration for role-based access control and authentication.

Application Properties
Database and other application properties are specified in the application.properties file.

Running the Application
To run the application, follow these steps:

1. Clone the repository:
git clone https://github.com/nibha-1999/LoginBasedOnRole.git

2. Navigate to the project directory:
cd LoginBasedOnRole

3. Build and run the application:
Use mvn clean and run the application


The application will be accessible at http://localhost:8080.


Testing
Unit tests are implemented for the controller and service layers using JUnit. To run the tests, execute:
mvn test

Notes
Feel free to reach out to me for any clarifications or additional details. This project is designed to showcase my skills in implementing role-based authentication in Spring Boot. 










