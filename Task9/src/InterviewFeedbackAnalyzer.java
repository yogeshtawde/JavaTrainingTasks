import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InterviewFeedbackAnalyzer {

    public static void main(String[] args) {

        List<InterviewFeedback> students = Arrays.asList(

                new InterviewFeedback(101, "Ravi", 8.0, 6.5, 7.5, 8.0, 9.0,
                        Arrays.asList("What is JVM?", "Explain Spring Boot"),
                        Arrays.asList("Java", "Problem Solving"),
                        Arrays.asList("Communication", "System Design")),

                new InterviewFeedback(102, "Priya", 9.0, 8.5, 8.0, 9.0, 8.5,
                        Arrays.asList("Explain REST API", "Difference between List and Set"),
                        Arrays.asList("Communication", "Coding"),
                        Arrays.asList("Microservices")),

                new InterviewFeedback(103, "Arjun", 5.0, 4.5, 5.5, 5.0, 5.0,
                        Arrays.asList("Java 8 Features"),
                        Arrays.asList("Basic Coding"),
                        Arrays.asList("Confidence", "Communication")
                ),

                new InterviewFeedback(104, "Sneha", 3.5, 4.0, 3.0, 3.5, 4.0,
                        Arrays.asList("Oops Concepts"),
                        Arrays.asList("Positive Attitude"),
                        Arrays.asList("Coding", "Technical Knowledge"))
        );


        Function<InterviewFeedback, Double> overallRatingCalculator =
                feedback ->
                        (feedback.getTechnicalRating()
                                + feedback.getCommunicationRating()
                                + feedback.getCodingRating()
                                + feedback.getConfidenceRating()
                                + feedback.getProblemSolvingRating())
                                / 5;



        Function<Double, String> performanceStatus =
                rating -> {

                    if (rating >= 8)
                        return "Excellent";
                    else if (rating >= 6)
                        return "Good";
                    else if (rating >= 4)
                        return "Average";
                    else
                        return "Needs Improvement";
                };

        Predicate<InterviewFeedback> placementEligibility =
                feedback -> {

                    double overall =
                            overallRatingCalculator.apply(feedback);

                    return overall >= 6
                            && feedback.getCodingRating() >= 6;
                };

        Consumer<InterviewFeedback> reportGenerator =
                feedback -> {

                    double overall = overallRatingCalculator.apply(feedback);
                    String status = performanceStatus.apply(overall);
                    boolean eligible = placementEligibility.test(feedback);

                    System.out.println("Student : "+ feedback.getStudentName());
                    System.out.println("Overall Rating : " + String.format("%.2f", overall));
                    System.out.println("Performance : " + status);
                    System.out.println("Placement Eligible : " + (eligible ? "Yes" : "No"));

                    if (!eligible) {
                        System.out.println("Suggestions : " + generateSuggestions(feedback));
                    }
                };
        students.forEach(reportGenerator);



        Map<String, List<InterviewFeedback>> groupedData =
                students.stream()
                        .collect(Collectors.groupingBy(student -> performanceStatus.apply(overallRatingCalculator.apply(student))
                        ));

        groupedData.forEach((status, list) -> {

            System.out.println("\n" + status);

            list.forEach(student ->
                    System.out.println(student.getStudentName()));
        });

        students.stream()
                .sorted(
                        Comparator.comparing(
                                overallRatingCalculator
                        ).reversed()
                )
                .forEach(student -> {

                    double rating =
                            overallRatingCalculator.apply(student);

                    System.out.println(
                            student.getStudentName()
                                    + " -> "
                                    + String.format("%.2f", rating));
                });


        System.out.println("\n\n===== NON ELIGIBLE STUDENTS =====");

        students.stream()
                .filter(placementEligibility.negate())
                .forEach(student -> {

                    System.out.println("\nStudent : " + student.getStudentName());
                    System.out.println("Suggestion : " + generateSuggestions(student));
                });
    }



    private static String generateSuggestions(
            InterviewFeedback feedback) {

        List<String> suggestions =
                new ArrayList<>();

        if (feedback.getCommunicationRating() < 6) {
            suggestions.add(
                    "Improve communication skills");
        }

        if (feedback.getCodingRating() < 6) {
            suggestions.add("Practice coding problems daily");
        }

        if (feedback.getTechnicalRating() < 6) {
            suggestions.add("Strengthen core Java and Spring concepts");
        }

        if (feedback.getConfidenceRating() < 6) {
            suggestions.add("Improve interview confidence");
        }

        if (feedback.getProblemSolvingRating() < 6) {
            suggestions.add("Practice problem-solving exercises");
        }

        if (suggestions.isEmpty()) {
            return "Ready for placement";
        }

        return String.join(", ", suggestions);
    }
}