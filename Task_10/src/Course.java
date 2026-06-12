public class Course {

    private Integer courseId;
    private String courseName;
    private Double courseFee;
    private String trainerName;

    public Course(Integer courseId,
                  String courseName,
                  Double courseFee,
                  String trainerName) {

        this.courseId = courseId;
        this.courseName = courseName;
        this.courseFee = courseFee;
        this.trainerName = trainerName;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public Double getCourseFee() {
        return courseFee;
    }

    public String getTrainerName() {
        return trainerName;
    }
}