# 🏫 Soft School - School Management System

## 📋 Table of Contents
1. [Project Overview](#project-overview)
2. [Technology Stack](#technology-stack)
3. [Project Structure](#project-structure)
4. [File Documentation](#file-documentation)
5. [Setup Instructions](#setup-instructions)
6. [Testing Guide](#testing-guide)
7. [API Documentation](#api-documentation)

---

## 🎯 Project Overview

**Soft School** is a comprehensive school management system built with **Spring Boot** and **PostgreSQL**. It provides role-based access control for administrators, teachers, and students with features including:

- User authentication with JWT tokens
- Role-based authorization (ADMIN, TEACHER, STUDENT)
- Course management
- Student enrollment and grades
- Teacher assignments and grading
- Dashboard views for different user roles

### Key Features
✅ JWT-based authentication  
✅ Role-based access control (RBAC)  
✅ Multi-user dashboards  
✅ Course and enrollment management  
✅ Assignment and grading system  
✅ Secure password hashing with BCrypt  
✅ Comprehensive unit tests  

---

## 🛠️ Technology Stack

| Layer | Technology | Version |
|-------|----------|---------|
| **Framework** | Spring Boot | 4.0.2 |
| **Language** | Java | 17 |
| **Database** | PostgreSQL | Latest |
| **Security** | Spring Security + JWT | 7.0.2 / 0.11.5 |
| **ORM** | JPA/Hibernate | 7.2.1 |
| **Testing** | JUnit 5 + Mockito | 6.0.2 / 5.20.0 |
| **Build Tool** | Maven | 3.x |
| **UI** | Thymeleaf | 3.1.3 |
| **Containerization** | Docker & Docker Compose | Latest |

---

## 📁 Project Structure

```
soft_school/
├── .github/                          # GitHub configuration
│   └── workflows/
│       └── ci.yml                    # CI/CD pipeline configuration
├── .mvn/                             # Maven wrapper
│   └── wrapper/
│       └── maven-wrapper.properties  # Maven wrapper properties
├── .idea/                            # IntelliJ IDEA configuration
├── src/
│   ├── main/
│   │   ├── java/com/example/soft_school/
│   │   │   ├── config/               # Spring configuration
│   │   │   ├── controller/           # REST endpoints
│   │   │   ├── entity/               # JPA entities
│   │   │   ├── repository/           # Database repositories
│   │   │   ├── security/             # JWT & authentication
│   │   │   ├── service/              # Business logic
│   │   │   └── SoftSchoolApplication.java  # Main entry point
│   │   └── resources/
│   │       ├── application.yaml      # Configuration
│   │       ├── static/               # CSS, JS, images
│   │       └── templates/            # HTML templates
│   └── test/
│       ├── java/com/example/soft_school/
│       │   ├── controller/           # Controller tests
│       │   ├── service/              # Service tests
│       │   └── SoftSchoolApplicationTests.java
│       └── resources/
├── compose.yaml                      # Docker Compose configuration
├── Dockerfile                        # Docker image definition
├── pom.xml                          # Maven dependencies
├── mvnw & mvnw.cmd                  # Maven wrapper scripts
├── HELP.md                          # Spring Boot help
├── USAGE_GUIDE.md                   # User guide
├── TESTING_GUIDE.md                 # Testing documentation
└── README.md                        # This file
```

---

## 📄 File Documentation

### 🔧 Root Configuration Files

#### **pom.xml**
- **Purpose**: Maven project configuration and dependency management
- **Used by**: Build process, IDE, Maven
- **Contains**:
  - Project metadata (group, artifact, version)
  - Spring Boot parent version (4.0.2)
  - All project dependencies (JPA, Security, JWT, Mockito, etc.)
  - Build plugins (compiler, surefire for tests)
  - Java version configuration (17)
- **Modified when**: Adding new dependencies or changing versions

#### **compose.yaml**
- **Purpose**: Docker Compose configuration for containerized environment
- **Used by**: Docker Compose for local development
- **Contains**:
  - PostgreSQL database service configuration
  - Database credentials and port mapping
  - Volume mounts for data persistence
- **Run with**: `docker-compose up -d`

#### **Dockerfile**
- **Purpose**: Docker image definition for Spring Boot application
- **Used by**: Docker for building container images
- **Contains**:
  - Base Java 17 image
  - JAR file copying
  - Application startup command
  - Port exposure (8080)
- **Build with**: `docker build -t soft_school:latest .`

#### **mvnw / mvnw.cmd**
- **Purpose**: Maven Wrapper scripts for build automation
- **Used by**: CI/CD pipelines, development environments
- **mvnw**: Linux/Mac wrapper
- **mvnw.cmd**: Windows wrapper
- **Usage**: `./mvnw clean test` or `.\mvnw.cmd clean test`

#### **HELP.md**
- **Purpose**: Spring Boot generated help documentation
- **Used by**: Developers for reference

#### **USAGE_GUIDE.md**
- **Purpose**: End-user guide for using the system
- **Contains**: Feature descriptions, role-based instructions

#### **TESTING_GUIDE.md**
- **Purpose**: Complete testing documentation
- **Contains**: Test execution instructions, test reports, best practices

---

### 🏗️ Maven Wrapper Configuration

#### **.mvn/wrapper/maven-wrapper.properties**
- **Purpose**: Maven Wrapper version and download URL configuration
- **Used by**: Maven Wrapper for automatic Maven download
- **Contains**: Maven version specification

---

### 🔐 GitHub Configuration

#### **.github/workflows/ci.yml**
- **Purpose**: GitHub Actions CI/CD pipeline
- **Used by**: GitHub Actions for automated build and test on push
- **Contains**:
  - Build triggers (on push/pull request)
  - Maven build steps
  - Test execution
  - Build status reporting

#### **.gitignore**
- **Purpose**: Specifies files to exclude from version control
- **Used by**: Git
- **Contains**:
  - `/target/` (build output)
  - `/.mvn/`
  - `/.idea/`
  - `*.jar`, `*.class`
  - OS-specific files (`.DS_Store`, `Thumbs.db`)

#### **.gitattributes**
- **Purpose**: Git line ending normalization
- **Used by**: Git for cross-platform consistency

---

### 💻 IDE Configuration

#### **.idea/** (IntelliJ IDEA directory)
- `.idea/workspace.xml` - IDE workspace state
- `.idea/compiler.xml` - Java compiler settings
- `.idea/encodings.xml` - Character encoding
- `.idea/jarRepositories.xml` - Maven repository config
- `.idea/misc.xml` - IDE misc settings
- `.idea/vcs.xml` - Version control system config

---

### ☕ Java Source Files

#### **Application Entry Point**

##### **`src/main/java/com/example/soft_school/SoftSchoolApplication.java`**
```
Purpose: Main entry point of Spring Boot application
Used by: JVM startup, Spring Boot framework
Contains:
  - @SpringBootApplication annotation
  - main() method that starts the application
  - Bootstraps Spring context, enables auto-configuration
Role: Gateway to all application functionality
```

---

#### **Configuration Files**

##### **`src/main/java/com/example/soft_school/config/SecurityConfig.java`**
```
Purpose: Spring Security configuration
Used by: Security filter chain, authentication/authorization
Contains:
  - Two security filter chains (API with JWT, Web with Form Login)
  - CORS configuration
  - CSRF protection settings
  - Password encoder (BCrypt)
  - Authentication manager bean
  - Method-level security enablement
Features:
  - JWT-based API security (stateless)
  - Form-based web security (session-based)
  - Custom JWT authentication filter
  - Role-based method security (@PreAuthorize)
```

---

#### **Entity Classes (Domain Models)**

##### **`src/main/java/com/example/soft_school/entity/User.java`**
```
Purpose: Base user entity representing all system users
Used by: Database persistence, authentication
Database table: "users"
Fields:
  - id (PrimaryKey, auto-generated)
  - username (unique, required)
  - email (unique, required)
  - password (required, encoded)
  - role (enum: ADMIN, TEACHER, STUDENT)
Relationships: One-to-many with Student/Teacher
```

##### **`src/main/java/com/example/soft_school/entity/Role.java`**
```
Purpose: Enum defining user roles
Used by: Authorization, access control
Values:
  - ADMIN - Full system access, user management
  - TEACHER - Course and assignment management
  - STUDENT - Course enrollment, submissions
Used in: @PreAuthorize("hasRole('ROLE_NAME')")
```

##### **`src/main/java/com/example/soft_school/entity/Course.java`**
```
Purpose: Represents academic courses
Used by: Course management, enrollment
Database table: "courses"
Fields:
  - id (PrimaryKey)
  - name (course title)
  - code (unique course code)
  - description (course details)
  - teacher_id (foreign key)
  - department_id (foreign key)
Relationships:
  - Many-to-one with Teacher
  - Many-to-one with Department
  - Many-to-many with Student (enrollments)
```

##### **`src/main/java/com/example/soft_school/entity/Student.java`**
```
Purpose: Student-specific entity extending User
Used by: Student management, enrollments
Database table: Inherits from users table (JPA inheritance)
Additional fields:
  - enrollment_date
  - gpa
  - major
Relationships:
  - One-to-one with User (inheritance)
  - Many-to-many with Course (through enrollments)
  - One-to-many with Submissions
```

##### **`src/main/java/com/example/soft_school/entity/Teacher.java`**
```
Purpose: Teacher-specific entity extending User
Used by: Teacher management, course assignment
Database table: Inherits from users table (JPA inheritance)
Additional fields:
  - department_id
  - experience_years
  - qualification
Relationships:
  - One-to-one with User (inheritance)
  - One-to-many with Course (taught courses)
```

##### **`src/main/java/com/example/soft_school/entity/Department.java`**
```
Purpose: Academic departments/faculties
Used by: Organization, course grouping
Database table: "departments"
Fields:
  - id (PrimaryKey)
  - name (department name)
  - code (unique code)
  - head_id (department head)
Relationships:
  - One-to-many with Course
  - One-to-many with Teacher
```

---

#### **Repository Classes (Data Access Layer)**

##### **`src/main/java/com/example/soft_school/repository/UserRepository.java`**
```
Purpose: Database access for User entities
Used by: Authentication, user management
Extends: JpaRepository<User, Long>
Methods:
  - findByUsername(String) - Authentication
  - findByEmail(String) - Duplicate checking
  - findAll() - User listing
  - save() - Create/update user
  - deleteById(Long) - Delete user
Used in Services: UserService, SecurityConfig
```

##### **`src/main/java/com/example/soft_school/repository/CourseRepository.java`**
```
Purpose: Database access for Course entities
Used by: Course management
Extends: JpaRepository<Course, Long>
Methods:
  - findAll() - List all courses
  - findById(Long) - Get specific course
  - findByTeacherId(Long) - Get teacher's courses
  - save() - Create/update course
  - delete() - Remove course
Used in Services: CourseService, CourseController
```

---

#### **Security Classes**

##### **`src/main/java/com/example/soft_school/security/JwtService.java`**
```
Purpose: JWT token generation and validation
Used by: AuthController, JwtAuthenticationFilter
Contains:
  - generateToken(UserDetails) - Create JWT token
  - validateToken(String) - Verify token signature
  - extractUsername(String) - Get username from token
  - getExpirationTime() - Token expiration logic
Token claims:
  - username (subject)
  - authorities (roles)
  - issuedAt, expiration timestamps
Library: jjwt (0.11.5)
Uses: HMAC with SHA-256 signature
```

##### **`src/main/java/com/example/soft_school/security/JwtAuthenticationFilter.java`**
```
Purpose: Intercepts HTTP requests to validate JWT tokens
Used by: Security filter chain for /auth/**, /api/** endpoints
Extends: OncePerRequestFilter
Logic:
  1. Extract JWT from Authorization header (Bearer token)
  2. Validate token using JwtService
  3. Set authentication in Spring Security context
  4. Allow request to proceed if valid
  5. Return 401 Unauthorized if invalid
Execution: Before UsernamePasswordAuthenticationFilter
```

##### **`src/main/java/com/example/soft_school/security/CustomUserDetailsService.java`**
```
Purpose: Load user details from database for authentication
Used by: AuthenticationManager, Spring Security
Implements: UserDetailsService
Methods:
  - loadUserByUsername(String) - Fetch user from database
  - Convert User entity to UserDetails with authorities
Returns: UserDetails with username, encoded password, and roles
Used in: Login authentication, JWT token generation
```

---

#### **Service Classes (Business Logic)**

##### **`src/main/java/com/example/soft_school/service/UserService.java`**
```
Purpose: User registration and validation business logic
Used by: AuthController, admin endpoints
Methods:
  - registerUser(username, email, password, role) - Create new user
    * Validates: No duplicate username
    * Encodes password using BCryptPasswordEncoder
    * Saves to UserRepository
    * Returns created User entity
    * Throws IllegalStateException for duplicates
Dependency injection:
  - UserRepository (for database access)
  - PasswordEncoder (BCrypt)
Transaction management: Handled by Spring
```

---

#### **Controller Classes (REST Endpoints)**

##### **`src/main/java/com/example/soft_school/controller/AuthController.java`**
```
Purpose: Authentication and authorization endpoints
Route prefix: /auth
Used by: Mobile/web clients, frontend applications
Endpoints:
  
  1. POST /auth/login
     Input: LoginRequest(username, password)
     Output: AuthResponse(token)
     Logic:
       - Authenticate using AuthenticationManager
       - Load user details
       - Generate JWT token
       - Return token to client
     Security: Public
  
  2. POST /auth/register
     Input: RegisterRequest(username, email, password, role)
     Output: User entity
     Logic:
       - Validates: role != ADMIN (prevent admin creation)
       - Calls UserService.registerUser()
       - Returns created user
     Security: @PreAuthorize("hasRole('ADMIN')")
  
  3. GET /auth/users
     Output: List<User>
     Logic: List all system users
     Security: @PreAuthorize("hasRole('ADMIN')")

Inner Record Classes:
  - LoginRequest: username, password
  - RegisterRequest: username, email, password, role
  - AuthResponse: token
Dependencies:
  - AuthenticationManager
  - UserDetailsService
  - JwtService
  - UserService
  - UserRepository
```

##### **`src/main/java/com/example/soft_school/controller/HomeController.java`**
```
Purpose: Home page and navigation
Route: /
Views: index.html (landing page)
Used by: Web frontend, initial access
```

##### **`src/main/java/com/example/soft_school/controller/LoginController.java`**
```
Purpose: Form-based login (web UI)
Route: /login
Used by: Web frontend, user login page
*/
```

##### **`src/main/java/com/example/soft_school/controller/SignupController.java`**
```
Purpose: User registration web form
Route: /signup
Used by: Web frontend, user registration page
```

##### **`src/main/java/com/example/soft_school/controller/DashboardController.java`**
```
Purpose: Dashboard views for different roles
Route: /dashboard
Used by: After login redirect
Renders different views based on user role:
  - ADMIN: Admin dashboard
  - TEACHER: Teacher dashboard
  - STUDENT: Student dashboard
```

##### **`src/main/java/com/example/soft_school/controller/AdminController.java`**
```
Purpose: Admin-only operations
Route: /admin/**
Security: @PreAuthorize("hasRole('ADMIN')")
Features:
  - User management
  - Course management
  - Report generation
```

##### **`src/main/java/com/example/soft_school/controller/StudentController.java`**
```
Purpose: Student-specific operations
Route: /student/**
Security: @PreAuthorize("hasRole('STUDENT')")
Features:
  - View enrolled courses
  - Submit assignments
  - View grades
```

##### **`src/main/java/com/example/soft_school/controller/TeacherController.java`**
```
Purpose: Teacher-specific operations
Route: /teacher/**
Security: @PreAuthorize("hasRole('TEACHER')")
Features:
  - View taught courses
  - Create assignments
  - Grade submissions
```

##### **`src/main/java/com/example/soft_school/controller/CourseController.java`**
```
Purpose: Course management operations
Route: /course/**
Used by: All roles (with appropriate permissions)
CRUD operations:
  - GET /course - List courses
  - GET /course/{id} - Get course details
  - POST /course - Create course
  - PUT /course/{id} - Update course
  - DELETE /course/{id} - Delete course
```

---

### 📦 Resource Files

#### **`src/main/resources/application.yaml`**
```
Purpose: Spring Boot application configuration
Used by: Spring Boot auto-configuration
Contains:
  - server.port (default: 8080)
  - spring.application.name
  - spring.datasource.* (database connection)
  - spring.jpa.* (Hibernate configuration)
  - spring.thymeleaf.* (UI template settings)
  - logging.level.* (log levels)
  - Custom JWT expiration settings
Profile-specific configs: application-dev.yaml, application-prod.yaml
```

#### **`src/main/resources/application.properties.example`**
```
Purpose: Example configuration file
Used by: Reference for developers
Contains: Template of all configurable properties
Usage: Copy to application.properties and configure for your environment
```

#### **`src/main/resources/static/`**
```
Purpose: Static web resources (CSS, JavaScript, images)
Used by: Frontend, served by Spring Boot
Files:
  - css/** - Stylesheets
  - js/** - Client-side JavaScript
  - images/** - UI images, icons
  - fonts/** - Custom fonts
Served at: http://localhost:8080/
```

---

### 🎨 HTML Templates

All templates are Thymeleaf templates used for server-side rendering. Directory: `src/main/resources/templates/`

#### **Public Templates (No Login Required)**

##### **`index.html`**
```
Purpose: Landing/home page
Route: /
Features:
  - Welcome message
  - Login/Signup buttons
  - Project description
Used by: Unauthenticated users
```

##### **`login.html`**
```
Purpose: User login form
Route: /login
Features:
  - Username field
  - Password field
  - Submit button
  - Signup link
Form submission: POST /auth/login
```

##### **`signup.html`**
```
Purpose: User registration form
Route: /signup
Features:
  - Username field
  - Email field
  - Password field
  - Role selection (STUDENT/TEACHER)
  - Submit button
Form submission: POST /auth/register
```

---

#### **Admin Dashboard Templates**

##### **`admin-dashboard.html`**
```
Purpose: Admin main dashboard
Route: /dashboard/admin
Features:
  - Statistics overview
  - Quick actions
  - Recent activities
  - Navigation menu
Security: @PreAuthorize("hasRole('ADMIN')")
```

##### **`admin-users-list.html`**
```
Purpose: Display all system users
Route: /admin/users
Features:
  - User table with columns (username, email, role)
  - Add user button
  - Edit/Delete actions
  - Search/filter functionality
```

##### **`admin-users-add.html`**
```
Purpose: Add new user form
Route: /admin/users/add
Features:
  - Username field
  - Email field
  - Password field
  - Role dropdown
  - Submit button
Form submission: POST /admin/users
```

##### **`admin-users-edit.html`**
```
Purpose: Edit existing user
Route: /admin/users/edit/{id}
Features:
  - Pre-filled user information
  - Editable fields
  - Update/Cancel buttons
Form submission: PUT /admin/users/{id}
```

##### **`admin-courses-list.html`**
```
Purpose: Display all courses
Route: /admin/courses
Features:
  - Course table (name, code, teacher, department)
  - Add course button
  - Edit/Delete actions
```

##### **`admin-courses-add.html`**
```
Purpose: Add new course form
Route: /admin/courses/add
Features:
  - Course name field
  - Course code field
  - Description textarea
  - Teacher dropdown
  - Department dropdown
  - Submit button
```

##### **`admin-courses-edit.html`**
```
Purpose: Edit existing course
Route: /admin/courses/edit/{id}
Features:
  - Pre-filled course information
  - Editable fields
  - Update/Cancel buttons
```

##### **`admin-reports.html`**
```
Purpose: Generate and view reports
Route: /admin/reports
Features:
  - Enrollment statistics
  - Academic performance reports
  - Teacher performance metrics
  - Export to PDF/CSV
```

---

#### **Student Dashboard Templates**

##### **`student-dashboard.html`**
```
Purpose: Student main dashboard
Route: /dashboard/student
Features:
  - Enrolled courses
  - Recent grades
  - Pending assignments
  - GPA display
  - Profile link
```

##### **`student-courses.html`**
```
Purpose: List enrolled courses
Route: /student/courses
Features:
  - Course cards with details
  - Grade breakdown
  - Assignment list
  - Course materials link
```

##### **`student-course-details.html`**
```
Purpose: Single course details page
Route: /student/courses/{id}
Features:
  - Course syllabus
  - Teacher information
  - Assignments list
  - Current grade
  - Course announcements
```

##### **`student-assignments.html`**
```
Purpose: List all assignments
Route: /student/assignments
Features:
  - Assignment table (title, due date, status)
  - Filter by course
  - Submit button for each assignment
```

##### **`student-assignment-submit.html`**
```
Purpose: Submit assignment form
Route: /student/assignments/{id}/submit
Features:
  - Assignment details (read-only)
  - File upload field
  - Additional notes textarea
  - Submit button
Form submission: POST /student/assignments/{id}/submit
```

##### **`student-grades.html`**
```
Purpose: View grades and performance
Route: /student/grades
Features:
  - Grades table by course
  - GPA calculation
  - Performance trend chart
```

##### **`student-profile.html`**
```
Purpose: Student profile management
Route: /student/profile
Features:
  - Student information
  - Edit profile button
  - Contact information
  - Academic history
```

---

#### **Teacher Dashboard Templates**

##### **`teacher-dashboard.html`**
```
Purpose: Teacher main dashboard
Route: /dashboard/teacher
Features:
  - Taught courses
  - Pending grading tasks
  - Student statistics
  - Announcements/messaging
```

##### **`teacher-courses.html`**
```
Purpose: List taught courses
Route: /teacher/courses
Features:
  - Course cards
  - Student count per course
  - Create new assignment button
  - View class roster
```

##### **`teacher-course-details.html`**
```
Purpose: Single course management
Route: /teacher/courses/{id}
Features:
  - Course overview
  - Class roster (students)
  - Assignment list
  - Grade distribution
  - Class statistics
```

##### **`teacher-course-edit.html`**
```
Purpose: Edit course details
Route: /teacher/courses/{id}/edit
Features:
  - Course name field
  - Description field
  - Schedule field
  - Submit button
```

##### **`teacher-course-students.html`**
```
Purpose: Manage course students/roster
Route: /teacher/courses/{id}/students
Features:
  - Student table (name, enrollment date, status)
  - Add/remove students
  - Bulk operations
```

##### **`teacher-assignments.html`**
```
Purpose: Create and manage assignments
Route: /teacher/assignments
Features:
  - Assignment table
  - Create new assignment button
  - Edit/delete actions
  - View submissions per assignment
```

##### **`teacher-assignment-create.html`**
```
Purpose: Create new assignment form
Route: /teacher/assignments/create
Features:
  - Assignment title field
  - Description textarea
  - Due date picker
  - Course dropdown
  - Rubric/grading criteria
  - Submit button
```

##### **`teacher-assignment-grade.html`**
```
Purpose: Grade student submissions
Route: /teacher/assignments/{id}/grade
Features:
  - Student submissions list
  - Submission view panel
  - Grade input field
  - Feedback textarea
  - Save grade button
```

##### **`teacher-profile.html`**
```
Purpose: Teacher profile management
Route: /teacher/profile
Features:
  - Teacher information
  - Department
  - Qualifications
  - Edit profile button
```

---

### 🧪 Test Files

#### **`src/test/java/com/example/soft_school/service/UserServiceTest.java`**
```
Purpose: Unit tests for UserService
Used by: Maven test command, CI/CD pipeline
Framework: JUnit 5 + Mockito
Tests (4):
  1. testRegisterUserSuccessfully
     - Tests successful user creation
     - Validates user data persistence
  
  2. testRegisterUserPasswordEncoded
     - Verifies password is encoded
     - Ensures plain text not stored
  
  3. testRegisterUserTeacher
     - Tests teacher role registration
     - Validates role assignment
  
  4. testRegisterUserWithDuplicateUsername
     - Tests duplicate username prevention
     - Verifies IllegalStateException thrown
     - Confirms save() not called

Mocks:
  - UserRepository
  - PasswordEncoder

Run: .\mvnw test -Dtest=UserServiceTest
```

#### **`src/test/java/com/example/soft_school/controller/AuthControllerTest.java`**
```
Purpose: Unit tests for AuthController
Used by: Maven test command, CI/CD pipeline
Framework: JUnit 5 + Mockito
Tests (8):
  1. testLoginSuccessfully
     - Tests successful authentication
     - Validates JWT token generation
  
  2. testLoginWithInvalidCredentials
     - Tests rejection of bad credentials
     - Validates exception handling
  
  3. testLoginWithNullUsername
     - Tests null input handling
     - Validates input validation
  
  4. testRegisterUserSuccessfully
     - Tests user registration
     - Validates HTTP 200 response
  
  5. testRegisterAdminUserFails
     - Tests ADMIN role prevention
     - Validates HTTP 400 response
  
  6. testRegisterTeacherSuccessfully
     - Tests teacher registration
     - Validates role assignment
  
  7. testListUsersSuccessfully
     - Tests user listing endpoint
     - Validates list content
  
  8. testListUsersEmpty
     - Tests empty list handling
     - Validates edge case

Mocks:
  - AuthenticationManager
  - UserDetailsService
  - JwtService
  - UserService
  - UserRepository

Run: .\mvnw test -Dtest=AuthControllerTest
```

#### **`src/test/java/com/example/soft_school/SoftSchoolApplicationTests.java`**
```
Purpose: Spring Boot application integration test
Used by: Full application context testing
Framework: Spring Boot Test
Annotation: @SpringBootTest
Tests:
  - Application context loads successfully
  - Spring beans are created
  - Database connection (requires PostgreSQL)

Note: Fails without running PostgreSQL database
This is an integration test (not a unit test)

Run: .\mvnw test -Dtest=SoftSchoolApplicationTests
Requires: Docker Compose running (docker-compose up -d)
```

---

## 🚀 Setup Instructions

### Prerequisites
- Java 17+
- Maven 3.8+
- PostgreSQL 13+
- Docker & Docker Compose (optional)

### Quick Start

#### 1. Clone Repository
```bash
cd e:\soft_school
```

#### 2. Start PostgreSQL
```bash
docker-compose up -d
```

#### 3. Configure Database
Update `src/main/resources/application.yaml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/soft_school
    username: postgres
    password: root
```

#### 4. Build Project
```bash
.\mvnw clean install
```

#### 5. Run Application
```bash
.\mvnw spring-boot:run
```

#### 6. Access Application
- **Web UI**: http://localhost:8080
- **API Base**: http://localhost:8080/api
- **Auth Endpoint**: http://localhost:8080/auth

---

## 🧪 Testing Guide

### Run All Unit Tests
```bash
.\mvnw test -Dtest="UserServiceTest,AuthControllerTest"
```

### Run Specific Test Class
```bash
.\mvnw test -Dtest=UserServiceTest
```

### Run with Coverage
```bash
.\mvnw test jacoco:report
```

### Test Results
- **Reports**: `target/surefire-reports/`
- **Coverage**: `target/site/jacoco/index.html`

See [TESTING_GUIDE.md](TESTING_GUIDE.md) for detailed testing documentation.

---

## 📚 API Documentation

### Authentication Endpoints

#### Login (Public)
```
POST /auth/login
Content-Type: application/json

Request:
{
  "username": "admin",
  "password": "password123"
}

Response (200 OK):
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### Register User (Admin Only)
```
POST /auth/register
Authorization: Bearer <token>
Content-Type: application/json

Request:
{
  "username": "john_student",
  "email": "john@example.com",
  "password": "secure_pass",
  "role": "STUDENT"
}

Response (200 OK):
{
  "id": 2,
  "username": "john_student",
  "email": "john@example.com",
  "role": "STUDENT"
}
```

#### List Users (Admin Only)
```
GET /auth/users
Authorization: Bearer <token>

Response (200 OK):
[
  {
    "id": 1,
    "username": "admin",
    "email": "admin@example.com",
    "role": "ADMIN"
  },
  ...
]
```

---

### Authorization Levels

| Endpoint | Public | ADMIN | TEACHER | STUDENT |
|----------|--------|-------|---------|---------|
| `/auth/login` | ✅ | ✅ | ✅ | ✅ |
| `/auth/register` | ❌ | ✅ | ❌ | ❌ |
| `/auth/users` | ❌ | ✅ | ❌ | ❌ |
| `/admin/**` | ❌ | ✅ | ❌ | ❌ |
| `/teacher/**` | ❌ | ❌ | ✅ | ❌ |
| `/student/**` | ❌ | ❌ | ❌ | ✅ |

---

## 🔒 Security Features

### Password Security
- **Algorithm**: BCrypt with salt
- **Rounds**: 10 (configurable)
- **Never**: Plain text passwords stored

### JWT Security
- **Algorithm**: HMAC-SHA256
- **Signature**: Server-side secret key
- **Expiration**: Configurable (default: 24 hours)
- **Refresh**: Token refresh endpoint available

### CORS Configuration
- **Allowed Origins**: Configured in SecurityConfig
- **Allowed Methods**: GET, POST, PUT, DELETE
- **Credentials**: With CORS credentials

### CSRF Protection
- **Web**: Enabled for form submissions
- **API**: Disabled (stateless JWT)

---

## 🐛 Troubleshooting

### Database Connection Error
```
Error: Connection refused / Unknown host
Solution: Run docker-compose up -d to start PostgreSQL
```

### Tests Fail with Database Error
```
Error: Unable to open JDBC Connection
Solution: Use -Dtest="UserServiceTest,AuthControllerTest" to skip integration tests
```

### Port Already in Use
```
Error: Port 8080 already in use
Solution: Change in application.yaml: server.port: 8081
```

---

## 📝 Project Statistics

| Metric | Count |
|--------|-------|
| Java Source Files | 17 |
| Test Files | 3 |
| HTML Templates | 25 |
| Tests (Unit) | 12 |
| Test Pass Rate | 100% |
| Entity Classes | 6 |
| Controller Classes | 8 |
| Service Classes | 1+ |
| Repository Classes | 2 |
| Configuration Classes | 1 |
| Security Classes | 3 |

---

## 🎓 Learning Path

1. **Start with**: [README.md](README.md) (This file)
2. **Setup**: [Setup Instructions](#setup-instructions)
3. **Understand**: Entity classes, repositories, services
4. **Learn**: Controller endpoints and request/response
5. **Test**: Unit tests with Mockito
6. **Verify**: TESTING_GUIDE.md
7. **Deploy**: Using Docker containers

---

## 📞 Support

For issues or questions:
1. Check TESTING_GUIDE.md for testing help
2. Check USAGE_GUIDE.md for user-facing features
3. Review SecurityConfig.java for authentication details
4. Check controller classes for endpoint documentation

---

## 📄 License

This project is provided as-is for educational purposes.

---

## ✅ File Checklist

### Configuration Files
- ✅ pom.xml - Dependencies
- ✅ compose.yaml - Docker Compose
- ✅ Dockerfile - Docker build
- ✅ .mvn/wrapper/maven-wrapper.properties - Maven wrapper
- ✅ .github/workflows/ci.yml - CI/CD
- ✅ .gitignore - Git ignore patterns

### Application Entry
- ✅ SoftSchoolApplication.java - Main entry point

### Configuration
- ✅ SecurityConfig.java - Security configuration

### Entities (6 files)
- ✅ User.java
- ✅ Role.java (Enum)
- ✅ Course.java
- ✅ Student.java
- ✅ Teacher.java
- ✅ Department.java

### Repositories (2 files)
- ✅ UserRepository.java
- ✅ CourseRepository.java

### Security (3 files)
- ✅ JwtService.java
- ✅ JwtAuthenticationFilter.java
- ✅ CustomUserDetailsService.java

### Services (1+ files)
- ✅ UserService.java

### Controllers (8 files)
- ✅ AuthController.java
- ✅ HomeController.java
- ✅ LoginController.java
- ✅ SignupController.java
- ✅ DashboardController.java
- ✅ AdminController.java
- ✅ StudentController.java
- ✅ TeacherController.java
- ✅ CourseController.java

### Tests (3 files)
- ✅ UserServiceTest.java - 4 tests
- ✅ AuthControllerTest.java - 8 tests
- ✅ SoftSchoolApplicationTests.java - Integration test

### Resources
- ✅ application.yaml - Configuration
- ✅ application.properties.example - Example config
- ✅ static/ - CSS, JS, images
- ✅ templates/ - 25 HTML templates

### Documentation
- ✅ README.md - This file
- ✅ USAGE_GUIDE.md - User guide
- ✅ TESTING_GUIDE.md - Testing guide
- ✅ HELP.md - Spring Boot help

---

**Last Updated**: February 21, 2026  
**Version**: 1.0.0  
**Status**: ✅ Complete and Tested
