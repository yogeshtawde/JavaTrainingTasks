public class Enrollment {

    private Integer enrollmentId;
    private Student student;
    private Course course;

    public Enrollment(Integer enrollmentId,
                      Student student,
                      Course course) {

        this.enrollmentId = enrollmentId;
        this.student = student;
        this.course = course;
    }

    public Integer getEnrollmentId() {
        return enrollmentId;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }
}