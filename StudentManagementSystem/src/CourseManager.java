import java.util.ArrayList;

public class CourseManager {

    private ArrayList<Course> courses =
            new ArrayList<>();

    private FileManager fileManager =
            new FileManager();


    public CourseManager() {

        courses =
                fileManager.loadCourses();
    }


    public void addCourse(Course course) {

        courses.add(course);

        fileManager.saveCourses(courses);
    }


    public ArrayList<Course> getCourses() {

        return courses;
    }


    public Course findCourse(int courseId) {

        for (Course course : courses) {

            if (course.getCourseId() == courseId) {

                return course;
            }
        }

        return null;
    }


    public boolean updateCourse(
            int courseId,
            String courseName,
            int credits) {

        Course course =
                findCourse(courseId);

        if (course == null) {

            return false;
        }

        course.setCourseName(courseName);

        course.setCredits(credits);

        fileManager.saveCourses(courses);

        return true;
    }


    public boolean deleteCourse(int courseId) {

        Course course =
                findCourse(courseId);

        if (course == null) {

            return false;
        }

        courses.remove(course);

        fileManager.saveCourses(courses);

        return true;
    }
}