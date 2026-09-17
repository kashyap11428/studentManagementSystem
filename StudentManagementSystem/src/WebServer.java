
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class WebServer {

    private static StudentManager manager =
            new StudentManager();

    private static CourseManager courseManager =
            new CourseManager();

    private static MarkManager markManager =
            new MarkManager();

    public static void main(String[] args)
            throws IOException {

        HttpServer server =
                HttpServer.create(
                        new InetSocketAddress(8080),
                        0
                );

        // =========================
        // FRONTEND
        // =========================

        server.createContext(
                "/",
                exchange -> {

                    if ("GET".equals(
                            exchange.getRequestMethod())) {

                        serveFrontend(exchange);

                    } else {

                        sendResponse(
                                exchange,
                                "Method not allowed",
                                405
                        );
                    }
                }
        );

        // =========================
        // ALL API REQUESTS
        // =========================

        server.createContext(
                "/api",
                exchange -> {

                    String method =
                            exchange.getRequestMethod();

                    String path =
                            exchange.getRequestURI()
                                    .getPath();

                    try {

                        // =========================
                        // GET ALL DATA
                        // =========================

                        if (path.equals("/api/data")
                                && method.equals("GET")) {

                            sendAllData(exchange);

                            return;
                        }

                        // =========================
                        // STUDENTS
                        // =========================

                        if (path.equals("/api/students")) {

                            if (method.equals("GET")) {

                                sendStudents(exchange);
                                return;
                            }

                            if (method.equals("POST")) {

                                addStudent(exchange);
                                return;
                            }
                        }

                        if (path.startsWith(
                                "/api/students/")) {

                            int id =
                                    Integer.parseInt(
                                            path.substring(
                                                    "/api/students/"
                                                            .length()
                                            )
                                    );

                            if (method.equals("PUT")) {

                                updateStudent(
                                        exchange,
                                        id
                                );

                                return;
                            }

                            if (method.equals("DELETE")) {

                                deleteStudent(
                                        exchange,
                                        id
                                );

                                return;
                            }
                        }

                        // =========================
                        // COURSES
                        // =========================

                        if (path.equals("/api/courses")) {

                            if (method.equals("GET")) {

                                sendCourses(exchange);
                                return;
                            }

                            if (method.equals("POST")) {

                                addCourse(exchange);
                                return;
                            }
                        }

                        if (path.startsWith(
                                "/api/courses/")) {

                            int courseId =
                                    Integer.parseInt(
                                            path.substring(
                                                    "/api/courses/"
                                                            .length()
                                            )
                                    );

                            if (method.equals("PUT")) {

                                updateCourse(
                                        exchange,
                                        courseId
                                );

                                return;
                            }

                            if (method.equals("DELETE")) {

                                deleteCourse(
                                        exchange,
                                        courseId
                                );

                                return;
                            }
                        }

                        // =========================
                        // MARKS
                        // =========================

                        if (path.equals("/api/marks")) {

                            if (method.equals("GET")) {

                                sendMarks(exchange);
                                return;
                            }

                            if (method.equals("POST")) {

                                addMark(exchange);
                                return;
                            }
                        }

                        if (path.startsWith(
                                "/api/marks/")) {

                            String remaining =
                                    path.substring(
                                            "/api/marks/"
                                                    .length()
                                    );

                            String[] parts =
                                    remaining.split("/");

                            if (parts.length == 2) {

                                int studentId =
                                        Integer.parseInt(
                                                parts[0]
                                        );

                                int courseId =
                                        Integer.parseInt(
                                                parts[1]
                                        );

                                if (method.equals("PUT")) {

                                    updateMark(
                                            exchange,
                                            studentId,
                                            courseId
                                    );

                                    return;
                                }

                                if (method.equals("DELETE")) {

                                    deleteMark(
                                            exchange,
                                            studentId,
                                            courseId
                                    );

                                    return;
                                }
                            }
                        }

                        // =========================
                        // NOT FOUND
                        // =========================

                        sendResponse(
                                exchange,
                                "API endpoint not found.",
                                404
                        );

                    } catch (NumberFormatException e) {

                        sendResponse(
                                exchange,
                                "Invalid ID.",
                                400
                        );

                    } catch (Exception e) {

                        e.printStackTrace();

                        sendResponse(
                                exchange,
                                "Server error: "
                                        + e.getMessage(),
                                500
                        );
                    }
                }
        );

        server.start();

        System.out.println(
                "================================="
        );

        System.out.println(
                " Student Management System"
        );

        System.out.println(
                " Server started successfully!"
        );

        System.out.println(
                " Open: http://localhost:8080"
        );

        System.out.println(
                "================================="
        );
    }

    // =====================================================
    // STUDENTS
    // =====================================================

    private static void sendStudents(
            HttpExchange exchange)
            throws IOException {

        StringBuilder json =
                new StringBuilder();

        json.append("[");

        ArrayList<Student> students =
                manager.getStudents();

        for (int i = 0;
             i < students.size();
             i++) {

            Student student =
                    students.get(i);

            json.append("{");

            json.append(
                    "\"id\":"
                            + student.getId()
            );

            json.append(",");

            json.append(
                    "\"name\":\""
                            + escapeJson(
                            student.getName()
                    )
                            + "\""
            );

            json.append(",");

            json.append(
                    "\"email\":\""
                            + escapeJson(
                            student.getEmail()
                    )
                            + "\""
            );

            json.append("}");

            if (i < students.size() - 1) {

                json.append(",");
            }
        }

        json.append("]");

        sendJsonResponse(
                exchange,
                json.toString()
        );
    }

    private static void addStudent(
            HttpExchange exchange)
            throws IOException {

        String body =
                readRequestBody(exchange);

        int id =
                getJsonInt(
                        body,
                        "id"
                );

        String name =
                getJsonString(
                        body,
                        "name"
                );

        String email =
                getJsonString(
                        body,
                        "email"
                );

        if (name == null ||
                name.trim().isEmpty()) {

            sendResponse(
                    exchange,
                    "Student name cannot be empty.",
                    400
            );

            return;
        }

        if (email == null ||
                email.trim().isEmpty()) {

            sendResponse(
                    exchange,
                    "Email cannot be empty.",
                    400
            );

            return;
        }

        if (manager.findStudent(id) != null) {

            sendResponse(
                    exchange,
                    "Student ID already exists.",
                    400
            );

            return;
        }

        Student student =
                new Student(
                        id,
                        name,
                        email
                );

        manager.addStudent(student);

        sendResponse(
                exchange,
                "Student added successfully!",
                200
        );
    }

    private static void updateStudent(
            HttpExchange exchange,
            int id)
            throws IOException {

        String body =
                readRequestBody(exchange);

        String name =
                getJsonString(
                        body,
                        "name"
                );

        String email =
                getJsonString(
                        body,
                        "email"
                );

        if (name == null ||
                name.trim().isEmpty()) {

            sendResponse(
                    exchange,
                    "Student name cannot be empty.",
                    400
            );

            return;
        }

        if (email == null ||
                email.trim().isEmpty()) {

            sendResponse(
                    exchange,
                    "Email cannot be empty.",
                    400
            );

            return;
        }

        boolean updated =
                manager.updateStudent(
                        id,
                        name,
                        email
                );

        if (updated) {

            sendResponse(
                    exchange,
                    "Student updated successfully!",
                    200
            );

        } else {

            sendResponse(
                    exchange,
                    "Student not found.",
                    404
            );
        }
    }

    private static void deleteStudent(
            HttpExchange exchange,
            int id)
            throws IOException {

        boolean deleted =
                manager.deleteStudent(id);

        if (deleted) {

            sendResponse(
                    exchange,
                    "Student deleted successfully!",
                    200
            );

        } else {

            sendResponse(
                    exchange,
                    "Student not found.",
                    404
            );
        }
    }

    // =====================================================
    // COURSES
    // =====================================================

    private static void sendCourses(
            HttpExchange exchange)
            throws IOException {

        StringBuilder json =
                new StringBuilder();

        json.append("[");

        ArrayList<Course> courses =
                courseManager.getCourses();

        for (int i = 0;
             i < courses.size();
             i++) {

            Course course =
                    courses.get(i);

            json.append("{");

            json.append(
                    "\"courseId\":"
                            + course.getCourseId()
            );

            json.append(",");

            json.append(
                    "\"courseName\":\""
                            + escapeJson(
                            course.getCourseName()
                    )
                            + "\""
            );

            json.append(",");

            json.append(
                    "\"credits\":"
                            + course.getCredits()
            );

            json.append("}");

            if (i < courses.size() - 1) {

                json.append(",");
            }
        }

        json.append("]");

        sendJsonResponse(
                exchange,
                json.toString()
        );
    }

    private static void addCourse(
            HttpExchange exchange)
            throws IOException {

        String body =
                readRequestBody(exchange);

        int courseId =
                getJsonInt(
                        body,
                        "courseId"
                );

        String courseName =
                getJsonString(
                        body,
                        "courseName"
                );

        int credits =
                getJsonInt(
                        body,
                        "credits"
                );

        if (courseName == null ||
                courseName.trim().isEmpty()) {

            sendResponse(
                    exchange,
                    "Course name cannot be empty.",
                    400
            );

            return;
        }

        if (credits < 1 ||
                credits > 10) {

            sendResponse(
                    exchange,
                    "Credits must be between 1 and 10.",
                    400
            );

            return;
        }

        if (courseManager.findCourse(courseId)
                != null) {

            sendResponse(
                    exchange,
                    "Course ID already exists.",
                    400
            );

            return;
        }

        Course course =
                new Course(
                        courseId,
                        courseName,
                        credits
                );

        courseManager.addCourse(course);

        sendResponse(
                exchange,
                "Course added successfully!",
                200
        );
    }

    private static void updateCourse(
            HttpExchange exchange,
            int courseId)
            throws IOException {

        String body =
                readRequestBody(exchange);

        String courseName =
                getJsonString(
                        body,
                        "courseName"
                );

        int credits =
                getJsonInt(
                        body,
                        "credits"
                );

        if (courseName == null ||
                courseName.trim().isEmpty()) {

            sendResponse(
                    exchange,
                    "Course name cannot be empty.",
                    400
            );

            return;
        }

        if (credits < 1 ||
                credits > 10) {

            sendResponse(
                    exchange,
                    "Credits must be between 1 and 10.",
                    400
            );

            return;
        }

        boolean updated =
                courseManager.updateCourse(
                        courseId,
                        courseName,
                        credits
                );

        if (updated) {

            sendResponse(
                    exchange,
                    "Course updated successfully!",
                    200
            );

        } else {

            sendResponse(
                    exchange,
                    "Course not found.",
                    404
            );
        }
    }

    private static void deleteCourse(
            HttpExchange exchange,
            int courseId)
            throws IOException {

        boolean deleted =
                courseManager.deleteCourse(
                        courseId
                );

        if (deleted) {

            sendResponse(
                    exchange,
                    "Course deleted successfully!",
                    200
            );

        } else {

            sendResponse(
                    exchange,
                    "Course not found.",
                    404
            );
        }
    }

    // =====================================================
    // MARKS
    // =====================================================

    private static void sendMarks(
            HttpExchange exchange)
            throws IOException {

        StringBuilder json =
                new StringBuilder();

        json.append("[");

        ArrayList<Mark> marks =
                markManager.getMarks();

        int index = 0;

        for (Mark mark : marks) {

            Student student =
                    manager.findStudent(
                            mark.getStudentId()
                    );

            Course course =
                    courseManager.findCourse(
                            mark.getCourseId()
                    );

            if (student == null ||
                    course == null) {

                continue;
            }

            if (index > 0) {

                json.append(",");
            }

            json.append("{");

            json.append(
                    "\"studentId\":"
                            + mark.getStudentId()
            );

            json.append(",");

            json.append(
                    "\"studentName\":\""
                            + escapeJson(
                            student.getName()
                    )
                            + "\""
            );

            json.append(",");

            json.append(
                    "\"courseId\":"
                            + mark.getCourseId()
            );

            json.append(",");

            json.append(
                    "\"courseName\":\""
                            + escapeJson(
                            course.getCourseName()
                    )
                            + "\""
            );

            json.append(",");

            json.append(
                    "\"marks\":"
                            + mark.getMarks()
            );

            json.append(",");

            json.append(
                    "\"grade\":\""
                            + mark.getGrade()
                            + "\""
            );

            json.append("}");

            index++;
        }

        json.append("]");

        sendJsonResponse(
                exchange,
                json.toString()
        );
    }

    private static void addMark(
            HttpExchange exchange)
            throws IOException {

        String body =
                readRequestBody(exchange);

        int studentId =
                getJsonInt(
                        body,
                        "studentId"
                );

        int courseId =
                getJsonInt(
                        body,
                        "courseId"
                );

        double marks =
                getJsonDouble(
                        body,
                        "marks"
                );

        if (manager.findStudent(studentId)
                == null) {

            sendResponse(
                    exchange,
                    "Student does not exist.",
                    400
            );

            return;
        }

        if (courseManager.findCourse(courseId)
                == null) {

            sendResponse(
                    exchange,
                    "Course does not exist.",
                    400
            );

            return;
        }

        if (marks < 0 ||
                marks > 100) {

            sendResponse(
                    exchange,
                    "Marks must be between 0 and 100.",
                    400
            );

            return;
        }

        Mark mark =
                new Mark(
                        studentId,
                        courseId,
                        marks
                );

        boolean added =
                markManager.addMark(mark);

        if (added) {

            sendResponse(
                    exchange,
                    "Marks added successfully!",
                    200
            );

        } else {

            sendResponse(
                    exchange,
                    "Marks already exist for this student and course.",
                    400
            );
        }
    }

    private static void updateMark(
            HttpExchange exchange,
            int studentId,
            int courseId)
            throws IOException {

        String body =
                readRequestBody(exchange);

        double marks =
                getJsonDouble(
                        body,
                        "marks"
                );

        if (marks < 0 ||
                marks > 100) {

            sendResponse(
                    exchange,
                    "Marks must be between 0 and 100.",
                    400
            );

            return;
        }

        boolean updated =
                markManager.updateMark(
                        studentId,
                        courseId,
                        marks
                );

        if (updated) {

            sendResponse(
                    exchange,
                    "Marks updated successfully!",
                    200
            );

        } else {

            sendResponse(
                    exchange,
                    "Marks record not found.",
                    404
            );
        }
    }

    private static void deleteMark(
            HttpExchange exchange,
            int studentId,
            int courseId)
            throws IOException {

        boolean deleted =
                markManager.deleteMark(
                        studentId,
                        courseId
                );

        if (deleted) {

            sendResponse(
                    exchange,
                    "Marks deleted successfully!",
                    200
            );

        } else {

            sendResponse(
                    exchange,
                    "Marks record not found.",
                    404
            );
        }
    }

    // =====================================================
    // ALL DATA
    // =====================================================

    private static void sendAllData(
            HttpExchange exchange)
            throws IOException {

        StringBuilder json =
                new StringBuilder();

        json.append("{");

        // Students
        json.append("\"students\":[");

        ArrayList<Student> students =
                manager.getStudents();

        for (int i = 0;
             i < students.size();
             i++) {

            Student student =
                    students.get(i);

            json.append("{");

            json.append(
                    "\"id\":"
                            + student.getId()
            );

            json.append(",");

            json.append(
                    "\"name\":\""
                            + escapeJson(
                            student.getName()
                    )
                            + "\""
            );

            json.append(",");

            json.append(
                    "\"email\":\""
                            + escapeJson(
                            student.getEmail()
                    )
                            + "\""
            );

            json.append("}");

            if (i < students.size() - 1) {
                json.append(",");
            }
        }

        json.append("],");

        // Courses
        json.append("\"courses\":[");

        ArrayList<Course> courses =
                courseManager.getCourses();

        for (int i = 0;
             i < courses.size();
             i++) {

            Course course =
                    courses.get(i);

            json.append("{");

            json.append(
                    "\"courseId\":"
                            + course.getCourseId()
            );

            json.append(",");

            json.append(
                    "\"courseName\":\""
                            + escapeJson(
                            course.getCourseName()
                    )
                            + "\""
            );

            json.append(",");

            json.append(
                    "\"credits\":"
                            + course.getCredits()
            );

            json.append("}");

            if (i < courses.size() - 1) {
                json.append(",");
            }
        }

        json.append("],");

        // Marks
        json.append("\"marks\":[");

        ArrayList<Mark> marks =
                markManager.getMarks();

        int index = 0;

        for (Mark mark : marks) {

            json.append("{");

            json.append(
                    "\"studentId\":"
                            + mark.getStudentId()
            );

            json.append(",");

            json.append(
                    "\"courseId\":"
                            + mark.getCourseId()
            );

            json.append(",");

            json.append(
                    "\"marks\":"
                            + mark.getMarks()
            );

            json.append("}");

            index++;

            if (index < marks.size()) {
                json.append(",");
            }
        }

        json.append("]");

        json.append("}");

        sendJsonResponse(
                exchange,
                json.toString()
        );
    }

    // =====================================================
    // FRONTEND
    // =====================================================

    private static void serveFrontend(
            HttpExchange exchange)
            throws IOException {

        String path =
                exchange.getRequestURI()
                        .getPath();

        if (path.equals("/")) {

            path = "/index.html";
        }

        String filePath =
                "frontend" + path;

        File file =
                new File(filePath);

        if (!file.exists() ||
                file.isDirectory()) {

            sendResponse(
                    exchange,
                    "File not found",
                    404
            );

            return;
        }

        String contentType =
                "text/plain";

        if (path.endsWith(".html")) {

            contentType =
                    "text/html; charset=UTF-8";

        } else if (path.endsWith(".css")) {

            contentType =
                    "text/css; charset=UTF-8";

        } else if (path.endsWith(".js")) {

            contentType =
                    "application/javascript; charset=UTF-8";
        }

        exchange.getResponseHeaders()
                .set(
                        "Content-Type",
                        contentType
                );

        exchange.sendResponseHeaders(
                200,
                file.length()
        );

        try (
                InputStream input =
                        new FileInputStream(file);

                OutputStream output =
                        exchange.getResponseBody()
        ) {

            byte[] buffer =
                    new byte[4096];

            int bytesRead;

            while ((bytesRead =
                    input.read(buffer)) != -1) {

                output.write(
                        buffer,
                        0,
                        bytesRead
                );
            }
        }
    }

    // =====================================================
    // REQUEST BODY
    // =====================================================

    private static String readRequestBody(
            HttpExchange exchange)
            throws IOException {

        return new String(
                exchange.getRequestBody()
                        .readAllBytes(),
                StandardCharsets.UTF_8
        );
    }

    // =====================================================
    // SIMPLE JSON PARSER
    // =====================================================

    private static String getJsonString(
            String json,
            String key) {

        String search =
                "\"" + key + "\"";

        int keyIndex =
                json.indexOf(search);

        if (keyIndex == -1) {
            return null;
        }

        int colonIndex =
                json.indexOf(
                        ":",
                        keyIndex
                );

        if (colonIndex == -1) {
            return null;
        }

        int start =
                json.indexOf(
                        "\"",
                        colonIndex
                );

        if (start == -1) {
            return null;
        }

        int end =
                start + 1;

        while (end < json.length()) {

            if (json.charAt(end) == '"' &&
                    json.charAt(end - 1) != '\\') {

                break;
            }

            end++;
        }

        if (end >= json.length()) {
            return null;
        }

        return json.substring(
                start + 1,
                end
        )
                .replace(
                        "\\\"",
                        "\""
                )
                .replace(
                        "\\\\",
                        "\\"
                );
    }

    private static int getJsonInt(
            String json,
            String key) {

        String value =
                getJsonValue(
                        json,
                        key
                );

        return Integer.parseInt(
                value
        );
    }

    private static double getJsonDouble(
            String json,
            String key) {

        String value =
                getJsonValue(
                        json,
                        key
                );

        return Double.parseDouble(
                value
        );
    }

    private static String getJsonValue(
            String json,
            String key) {

        String search =
                "\"" + key + "\"";

        int keyIndex =
                json.indexOf(search);

        if (keyIndex == -1) {
            throw new IllegalArgumentException(
                    "Missing field: " + key
            );
        }

        int colonIndex =
                json.indexOf(
                        ":",
                        keyIndex
                );

        if (colonIndex == -1) {
            throw new IllegalArgumentException(
                    "Invalid JSON"
            );
        }

        int start =
                colonIndex + 1;

        while (
                start < json.length() &&
                Character.isWhitespace(
                        json.charAt(start)
                )
        ) {

            start++;
        }

        int end = start;

        while (
                end < json.length() &&
                json.charAt(end) != ',' &&
                json.charAt(end) != '}'
        ) {

            end++;
        }

        return json.substring(
                start,
                end
        ).trim();
    }

    // =====================================================
    // JSON ESCAPING
    // =====================================================

    private static String escapeJson(
            String text) {

        if (text == null) {
            return "";
        }

        return text
                .replace(
                        "\\",
                        "\\\\"
                )
                .replace(
                        "\"",
                        "\\\""
                );
    }

    // =====================================================
    // JSON RESPONSE
    // =====================================================

    private static void sendJsonResponse(
            HttpExchange exchange,
            String response)
            throws IOException {

        byte[] bytes =
                response.getBytes(
                        StandardCharsets.UTF_8
                );

        exchange.getResponseHeaders()
                .set(
                        "Content-Type",
                        "application/json; charset=UTF-8"
                );

        exchange.sendResponseHeaders(
                200,
                bytes.length
        );

        try (
                OutputStream output =
                        exchange.getResponseBody()
        ) {

            output.write(bytes);
        }
    }

    // =====================================================
    // NORMAL RESPONSE
    // =====================================================

    private static void sendResponse(
            HttpExchange exchange,
            String response,
            int statusCode)
            throws IOException {

        byte[] bytes =
                response.getBytes(
                        StandardCharsets.UTF_8
                );

        exchange.getResponseHeaders()
                .set(
                        "Content-Type",
                        "text/plain; charset=UTF-8"
                );

        exchange.sendResponseHeaders(
                statusCode,
                bytes.length
        );

        try (
                OutputStream output =
                        exchange.getResponseBody()
        ) {

            output.write(bytes);
        }
    }
}
