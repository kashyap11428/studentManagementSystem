
import java.util.ArrayList;

public class StudentManager {

    private ArrayList<Student> students =
            new ArrayList<>();

    private FileManager fileManager =
            new FileManager();


    public StudentManager() {

        students =
                fileManager.loadStudents();
    }


    public void addStudent(Student student) {

        students.add(student);

        fileManager.saveStudents(students);

        System.out.println(
                "Student added successfully!"
        );
    }


    public ArrayList<Student> getStudents() {

        return students;
    }


    public void viewStudents() {

        if (students.isEmpty()) {

            System.out.println(
                    "No students found."
            );

            return;
        }

        System.out.println(
                "\n===== STUDENT LIST ====="
        );

        for (Student student : students) {

            student.displayStudent();
        }
    }


    public void searchStudent(int id) {

        Student student =
                findStudent(id);

        if (student != null) {

            System.out.println(
                    "Student found:"
            );

            student.displayStudent();

        } else {

            System.out.println(
                    "Student not found."
            );
        }
    }


    public Student findStudent(int id) {

        for (Student student : students) {

            if (student.getId() == id) {

                return student;
            }
        }

        return null;
    }


    public boolean updateStudent(
            int id,
            String name,
            String email) {

        Student student =
                findStudent(id);

        if (student == null) {

            return false;
        }

        student.setName(name);

        student.setEmail(email);

        fileManager.saveStudents(students);

        return true;
    }


    public boolean deleteStudent(int id) {

        Student student =
                findStudent(id);

        if (student == null) {

            return false;
        }

        students.remove(student);

        fileManager.saveStudents(students);

        return true;
    }
}

