public class Student {

    private Integer studentId;
    private String studentName;
    private String admissionMonth;

    public Student(Integer studentId,
                   String studentName,
                   String admissionMonth) {

        this.studentId = studentId;
        this.studentName = studentName;
        this.admissionMonth = admissionMonth;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getAdmissionMonth() {
        return admissionMonth;
    }
}