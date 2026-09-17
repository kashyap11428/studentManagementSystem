# Student Management System

A simple web-based Student Management System developed using Java, HTML, CSS, and JavaScript. The project helps manage student details, courses, marks, and academic performance through a single web interface.

## Project Description

The Student Management System is designed to make basic student and academic record management easier and more organized.

The system allows users to:

- Add, view, search, update, and delete students
- Add, view, update, and delete courses
- Add, view, update, and delete marks
- Calculate student average marks
- Display the overall performance grade
- Store data so that it remains available after restarting the application

The frontend provides the user interface, while the Java backend handles the main operations and communicates with the frontend through HTTP requests.

Java File I/O is used for storing student, course, and marks data in text files.

## Features

### Student Management

- Add new students
- View all students
- Search students
- Edit student details
- Delete students

### Course Management

- Add courses
- View courses
- Edit course details
- Delete courses

### Marks Management

- Add marks for a student and course
- View recorded marks
- Update marks
- Delete marks
- Automatically determine grades

### Performance

The system calculates the average marks of a student and displays the corresponding overall grade.

The grading system used is:

| Marks | Grade |
|-------|-------|
| 90 - 100 | S |
| 80 - 89 | A |
| 70 - 79 | B |
| 60 - 69 | C |
| 50 - 59 | D |
| 40 - 49 | E |
| Below 40 | F |

## Technologies Used

### Backend

- Java
- Java HTTP Server
- Java Collections
- Java File I/O
- Exception Handling

### Frontend

- HTML
- CSS
- JavaScript

### Storage

- Text files using Java File I/O

## Project Structure

```text
StudentManagementSystem/
│
├── src/
│   ├── Main.java
│   ├── Student.java
│   ├── StudentManager.java
│   ├── Course.java
│   ├── CourseManager.java
│   ├── Mark.java
│   ├── MarkManager.java
│   ├── FileManager.java
│   └── WebServer.java
│
├── frontend/
│   └── index.html
│
├── data/
│   ├── students.txt
│   ├── courses.txt
│   └── marks.txt
│
├── out/
│
├── .gitignore
└── README.md
