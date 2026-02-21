# 🎓 Soft School - Course Management System

## ✅ What's Been Fixed

### Frontend & Backend Integration
- ✅ **Dual Authentication System**: 
  - Session-based auth for web pages (login, dashboards)
  - JWT-based auth for API endpoints
- ✅ **Login Page**: Works with Spring Security form login
- ✅ **Signup Page**: Allows STUDENT and TEACHER registration
- ✅ **Role-based Dashboards**: Auto-redirect based on user role
- ✅ **Proper Error/Success Messages**: Shows login errors, registration success

## 🚀 How to Use

### 1. Start the Application

```bash
docker compose up --build
```

The app will be available at: **http://localhost:8081**

### 2. Web Interface (Session-based)

**Creating Your First User:**

Since there's no admin yet, you can:
- Use signup page to create a TEACHER or STUDENT account
- Or manually insert an ADMIN into the database

**Login Flow:**
1. Go to http://localhost:8081/login
2. Enter username and password
3. You'll be redirected to your role-specific dashboard:
   - Admin → `/admin/dashboard`
   - Teacher → `/teacher/dashboard`
   - Student → `/student/dashboard`

**Signup Flow:**
1. Go to http://localhost:8081/signup
2. Fill in username, email, password
3. Choose role: **Student** or **Teacher** (Admin not allowed via signup)
4. After successful registration, login with your credentials

### 3. API Endpoints (JWT-based)

All API endpoints require JWT authentication via `Authorization: Bearer <token>` header.

#### **Authentication**

**Login (Get JWT Token):**
```bash
POST /auth/login
Content-Type: application/json

{
  "username": "teacher1",
  "password": "password123"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

**Register (Admin Only):**
```bash
POST /auth/register
Authorization: Bearer <admin-token>
Content-Type: application/json

{
  "username": "newteacher",
  "email": "teacher@school.com",
  "password": "pass123",
  "role": "TEACHER"
}
```

**Get All Users (Admin Only):**
```bash
GET /auth/users
Authorization: Bearer <admin-token>
```

#### **Course Management**

**Get All Courses (All Roles):**
```bash
GET /api/courses
Authorization: Bearer <token>
```

**Create Course (Teacher Only):**
```bash
POST /api/courses
Authorization: Bearer <teacher-token>
Content-Type: application/json

{
  "title": "Introduction to Java"
}
```

**Update Course (Teacher - Own Courses Only):**
```bash
PUT /api/courses/{courseId}
Authorization: Bearer <teacher-token>
Content-Type: application/json

{
  "title": "Advanced Java Programming"
}
```

**Delete Course:**
- **Admin**: Can delete ANY course
- **Teacher**: Can delete THEIR OWN courses only

```bash
DELETE /api/courses/{courseId}
Authorization: Bearer <token>
```

**Enroll in Course (Student Only):**
```bash
POST /api/courses/{courseId}/enroll
Authorization: Bearer <student-token>
```

## 🎯 Role Permissions Summary

### 👨‍💼 ADMIN
- ✅ Create teacher or student accounts (via API)
- ✅ Delete any course
- ✅ View all users

### 👨‍🏫 TEACHER
- ✅ Create courses
- ✅ Update their own courses
- ✅ Delete their own courses
- ✅ View all courses

### 👨‍🎓 STUDENT
- ✅ View all courses
- ✅ Enroll in courses
- ❌ Cannot create or delete courses

## 🔐 Security Features

- **BCrypt Password Encoding**: All passwords are securely hashed
- **JWT Tokens**: Expire after 1 hour (configurable in application.yaml)
- **Role-based Access Control**: Using `@PreAuthorize` annotations
- **Stateless API**: No sessions for API endpoints
- **Session-based Web**: Traditional login for web pages

## 📝 Example Test Flow

1. **Signup as a Teacher:**
   - Visit http://localhost:8081/signup
   - Username: `teacher1`, Email: `teacher@test.com`, Password: `pass123`, Role: TEACHER

2. **Login:**
   - Visit http://localhost:8081/login
   - Use the credentials above
   - You'll be redirected to Teacher Dashboard

3. **Create a course via API:**
   ```bash
   # First, get JWT token
   curl -X POST http://localhost:8081/auth/login \
     -H "Content-Type: application/json" \
     -d '{"username":"teacher1","password":"pass123"}'
   
   # Use token to create course
   curl -X POST http://localhost:8081/api/courses \
     -H "Authorization: Bearer <your-token>" \
     -H "Content-Type: application/json" \
     -d '{"title":"Spring Boot 101"}'
   ```

## 🛠️ Technology Stack

- **Backend**: Spring Boot 4.0.2, Spring Security 6
- **Database**: PostgreSQL
- **Authentication**: JWT (API) + Session (Web)
- **Frontend**: Thymeleaf, Bootstrap 5
- **Password Encoding**: BCrypt
- **Containerization**: Docker Compose

## ⚙️ Configuration

JWT settings in `application.yaml`:
```yaml
jwt:
  secret: "your-base64-secret-key"
  expiration-ms: 3600000  # 1 hour
```

## 🐛 Troubleshooting

**Issue**: Can't login to web interface
- Make sure you're using the web login form at `/login`
- Password must match exactly what you used during signup

**Issue**: API returns 401 Unauthorized
- Ensure you're including the JWT token in the Authorization header
- Check if your token has expired (1 hour default)
- Format: `Authorization: Bearer <token>`

**Issue**: Can't access dashboard
- Make sure you're logged in with the correct role
- Teachers can only access `/teacher/dashboard`
- Students can only access `/student/dashboard`
- Admins can only access `/admin/dashboard`
