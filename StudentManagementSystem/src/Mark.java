public class Mark {

    private int studentId;
    private int courseId;
    private double marks;

    public Mark(
            int studentId,
            int courseId,
            double marks) {

        this.studentId = studentId;
        this.courseId = courseId;
        this.marks = marks;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public String getGrade() {

        if (marks >= 90) {
            return "S";
        }

        if (marks >= 80) {
            return "A";
        }

        if (marks >= 70) {
            return "B";
        }

        if (marks >= 60) {
            return "C";
        }

        if (marks >= 50) {
            return "D";
        }

        if (marks >= 40) {
            return "E";
        }

        return "F";
    }
}