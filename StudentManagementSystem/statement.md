Project Statement

Project

Student Management System

The Student Management System is a web-based project that I made for my Programming in Java course.

The idea for the Student Management System is simple: I wanted to build one application where student details, courses, marks and performance could be handled in one place. While working on the Student Management System I also wanted to use the Java concepts that I had learned in class of making a project that only focused on the user interface.

The Student Management System has a web interface. The main processing is done using Java. The information entered through the website is handled by the Java backend. Stored in files.

Problem Statement

Student information can become difficult to manage when details such as courses and marks have to be maintained. For a small number of students updating records manually can take time and can make the information harder to organize.

The Student Management System provides a way to keep these records together. A user can. Modify student details manage courses enter marks and check a students overall performance through the Student Management System.

Objectives

The main things I wanted to achieve with this project were:

- Create a working student management application.

- Store student information such as ID, name and email.

- Add and manage courses.

- Record marks for different students and courses.

- Calculate average marks and display the corresponding grade.

- Keep the data available even after closing and reopening the application.

- Use the Java concepts from the course in a project.

Technologies Used

The project was built using:

- Java

- HTML

- CSS

- JavaScript

- Java HttpServer

- ArrayList

- Java File I/O

Main Features

Student Management

The Student Management System student section allows the user to add students and view their details. Existing student information can also be searched, edited or deleted.

Course Management

The Student Management System course section allows courses to be added to the system along with their course name and credits. The user can also. Remove an existing course.

Marks Management

The Student Management System marks section allows marks to be entered for a student for a course. The marks can later be. Removed if required.

Performance

The Student Management System performance section uses the entered marks to calculate the students average. Based on the average the Student Management System displays the grade.

Java Concepts Applied

One of the purposes of this project was to use the Java concepts covered in the course.

Classes and Objects

The Student Management System uses classes for students, courses, marks and the operations related to them. Objects of these classes are used when working with the records.

Encapsulation

The Student Management System keeps the variables inside the classes. Getter and setter methods are used whenever the values need to be accessed or changed.

Collections

The Student Management System uses ArrayList to keep track of students, courses and marks while the application is running.

Exception Handling

The Student Management System uses try-catch blocks in places where errors can occur especially while working with files or converting input values.

File I/O

The Student Management System does not use a database. The current version stores the information in text files. Java File I/O writes the data to these files. Loads it again when the Student Management System starts.

HTTP Communication

The Student Management System uses the Java HttpServer as the backend server. It receives requests from the frontend processes them and sends the required response back.

Working of the Project

The basic flow of the Student Management System is:

User

↓

Web Interface

↓

Java Web Server

↓

Manager Classes

↓

File Manager

↓

Data Files

For example when a user adds a student from the website the frontend sends the information to the Java server. The Java server passes the operation to the manager class and the information is then saved using the file manager.

The same basic process is followed for courses and marks.

Data Storage

The current version of the Student Management System uses Java File I/O for storing data.

The following files are used:

students.txt

courses.txt

marks.txt

These files are kept inside the data folder. When the Student Management System starts again the existing information is loaded from the files.

This was chosen because the Student Management System is an academic application and did not require a database for its current purpose.

Expected Outcome

The expected result of the Student Management System is a working application through which student academic information can be added and managed without having to handle records manually.

The project also brings together parts of Java programming into one application. Of using each concept separately the classes, collections, file handling, exception handling and server are used as different parts of the same system.

Learning Outcome

This project helped me understand how Java concepts work when they are used together in an application.

While building the Student Management System I got experience with classes and objects, encapsulation, ArrayList, file handling, exception handling and HTTP communication. I also got an idea of how a frontend and backend communicate with each other.

The Student Management System was also useful for understanding that a working application requires components to work together rather than just writing individual programs for each concept.

Future Scope

There are things that can be added if the Student Management System is developed further:

- Connect the application to MySQL using JDBC.

- Add login and authentication.

- Add access for students and administrators.

- Add attendance management.

- Generate detailed student performance reports.

- Allow reports to be exported.

- Add stronger input validation.

- Add options for tracking student performance.

The Student Management System is an academic project that I developed to apply the concepts learned in Programming, in Java.

The Student Management System currently provides management of students, courses, marks and performance. Importantly it gave me an opportunity to use different Java concepts together in one working application and understand how a small frontend-backend system is put together.
