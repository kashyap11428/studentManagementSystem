
import java.io.*;
import java.util.ArrayList;

public class FileManager {

    private static final String DATA_FOLDER = "data";

    private static final String STUDENT_FILE =
            DATA_FOLDER + File.separator + "students.txt";

    private static final String COURSE_FILE =
            DATA_FOLDER + File.separator + "courses.txt";

    private static final String MARK_FILE =
            DATA_FOLDER + File.separator + "marks.txt";


    public FileManager() {

        createDataFolder();

        createFile(STUDENT_FILE);
        createFile(COURSE_FILE);
        createFile(MARK_FILE);
    }


    private void createDataFolder() {

        File folder = new File(DATA_FOLDER);

        if (!folder.exists()) {
            folder.mkdirs();
        }
    }


    private void createFile(String fileName) {

        File file = new File(fileName);

        try {

            if (!file.exists()) {
                file.createNewFile();
            }

        } catch (IOException e) {

            System.out.println(
                    "Error creating file: " + fileName
            );

            e.printStackTrace();
        }
    }


    // =========================
    // STUDENTS
    // =========================

    public void saveStudents(
            ArrayList<Student> students) {

        try (
                BufferedWriter writer =
                        new BufferedWriter(
                                new FileWriter(STUDENT_FILE)
                        )
        ) {

            for (Student student : students) {

                writer.write(
                        student.getId()
                                + "|"
                                + student.getName()
                                + "|"
                                + student.getEmail()
                );

                writer.newLine();
            }

        } catch (IOException e) {

            System.out.println(
                    "Error saving students."
            );

            e.printStackTrace();
        }
    }


    public ArrayList<Student> loadStudents() {

        ArrayList<Student> students =
                new ArrayList<>();

        try (
                BufferedReader reader =
                        new BufferedReader(
                                new FileReader(STUDENT_FILE)
                        )
        ) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data =
                        line.split("\\|");

                if (data.length == 3) {

                    int id =
                            Integer.parseInt(data[0]);

                    String name =
                            data[1];

                    String email =
                            data[2];

                    students.add(
                            new Student(
                                    id,
                                    name,
                                    email
                            )
                    );
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "Error loading students."
            );

            e.printStackTrace();

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid student data found."
            );
        }

        return students;
    }


    // =========================
    // COURSES
    // =========================

    public void saveCourses(
            ArrayList<Course> courses) {

        try (
                BufferedWriter writer =
                        new BufferedWriter(
                                new FileWriter(COURSE_FILE)
                        )
        ) {

            for (Course course : courses) {

                writer.write(
                        course.getCourseId()
                                + "|"
                                + course.getCourseName()
                                + "|"
                                + course.getCredits()
                );

                writer.newLine();
            }

        } catch (IOException e) {

            System.out.println(
                    "Error saving courses."
            );

            e.printStackTrace();
        }
    }


    public ArrayList<Course> loadCourses() {

        ArrayList<Course> courses =
                new ArrayList<>();

        try (
                BufferedReader reader =
                        new BufferedReader(
                                new FileReader(COURSE_FILE)
                        )
        ) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data =
                        line.split("\\|");

                if (data.length == 3) {

                    int courseId =
                            Integer.parseInt(data[0]);

                    String courseName =
                            data[1];

                    int credits =
                            Integer.parseInt(data[2]);

                    courses.add(
                            new Course(
                                    courseId,
                                    courseName,
                                    credits
                            )
                    );
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "Error loading courses."
            );

            e.printStackTrace();

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid course data found."
            );
        }

        return courses;
    }


    // =========================
    // MARKS
    // =========================

    public void saveMarks(
            ArrayList<Mark> marks) {

        try (
                BufferedWriter writer =
                        new BufferedWriter(
                                new FileWriter(MARK_FILE)
                        )
        ) {

            for (Mark mark : marks) {

                writer.write(
                        mark.getStudentId()
                                + "|"
                                + mark.getCourseId()
                                + "|"
                                + mark.getMarks()
                );

                writer.newLine();
            }

        } catch (IOException e) {

            System.out.println(
                    "Error saving marks."
            );

            e.printStackTrace();
        }
    }


    public ArrayList<Mark> loadMarks() {

        ArrayList<Mark> marks =
                new ArrayList<>();

        try (
                BufferedReader reader =
                        new BufferedReader(
                                new FileReader(MARK_FILE)
                        )
        ) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data =
                        line.split("\\|");

                if (data.length == 3) {

                    int studentId =
                            Integer.parseInt(data[0]);

                    int courseId =
                            Integer.parseInt(data[1]);

                    double markValue =
                            Double.parseDouble(data[2]);

                    marks.add(
                            new Mark(
                                    studentId,
                                    courseId,
                                    markValue
                            )
                    );
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "Error loading marks."
            );

            e.printStackTrace();

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid marks data found."
            );
        }

        return marks;
    }
}

