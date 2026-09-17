
import java.util.ArrayList;

public class MarkManager {

    private ArrayList<Mark> marks =
            new ArrayList<>();

    private FileManager fileManager =
            new FileManager();


    public MarkManager() {

        marks =
                fileManager.loadMarks();
    }


    public boolean addMark(Mark mark) {

        if (findMark(
                mark.getStudentId(),
                mark.getCourseId()
        ) != null) {

            return false;
        }

        marks.add(mark);

        fileManager.saveMarks(marks);

        return true;
    }


    public ArrayList<Mark> getMarks() {

        return marks;
    }


    public Mark findMark(
            int studentId,
            int courseId) {

        for (Mark mark : marks) {

            if (mark.getStudentId() == studentId &&
                    mark.getCourseId() == courseId) {

                return mark;
            }
        }

        return null;
    }


    public boolean updateMark(
            int studentId,
            int courseId,
            double newMarks) {

        Mark mark =
                findMark(
                        studentId,
                        courseId
                );

        if (mark == null) {

            return false;
        }

        mark.setMarks(newMarks);

        fileManager.saveMarks(marks);

        return true;
    }


    public boolean deleteMark(
            int studentId,
            int courseId) {

        Mark mark =
                findMark(
                        studentId,
                        courseId
                );

        if (mark == null) {

            return false;
        }

        marks.remove(mark);

        fileManager.saveMarks(marks);

        return true;
    }


    public double getAverageForStudent(
            int studentId) {

        double total = 0;

        int count = 0;

        for (Mark mark : marks) {

            if (mark.getStudentId() == studentId) {

                total += mark.getMarks();

                count++;
            }
        }

        if (count == 0) {

            return 0;
        }

        return total / count;
    }


    public String getPerformanceGrade(
            int studentId) {

        double average =
                getAverageForStudent(studentId);


        if (average >= 90) {
            return "S";
        }

        if (average >= 80) {
            return "A";
        }

        if (average >= 70) {
            return "B";
        }

        if (average >= 60) {
            return "C";
        }

        if (average >= 50) {
            return "D";
        }

        if (average >= 40) {
            return "E";
        }

        return "F";
    }
}
