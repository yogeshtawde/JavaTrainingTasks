import java.util.List;

public class InterviewFeedback
{

    private Integer studentId;
    private String studentName;
    private Double technicalRating;
    private Double communicationRating;
    private Double codingRating;
    private Double confidenceRating;
    private Double problemSolvingRating;
    private List<String> questionsAsked;
    private List<String> strengths;
    private List<String> weaknesses;

    public InterviewFeedback(Integer studentId, String studentName, Double technicalRating, Double communicationRating, Double codingRating, Double confidenceRating, Double problemSolvingRating, List<String> questionsAsked, List<String> strengths, List<String> weaknesses) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.technicalRating = technicalRating;
        this.communicationRating = communicationRating;
        this.codingRating = codingRating;
        this.confidenceRating = confidenceRating;
        this.problemSolvingRating = problemSolvingRating;
        this.questionsAsked = questionsAsked;
        this.strengths = strengths;
        this.weaknesses = weaknesses;
    }
}
