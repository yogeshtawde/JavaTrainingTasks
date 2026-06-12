import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class PlacementFilteringSystem
{
    public static void main(String[] args)
    {
        List<Student> students = Arrays.asList(
                new Student(101, "Rahul Sharma", "B.Tech IT", 2024, 78.5, 0, 4.5, Arrays.asList("Java", "Spring Boot", "SQL"), true, true),
                new Student(102, "Priya Verma", "B.Tech CSE", 2023, 82.0, 0, 4.7, Arrays.asList("Python", "Django", "SQL"), true, true),
                new Student(103, "Amit Patel", "B.Tech IT", 2021, 75.0, 0, 4.6, Arrays.asList("Java", "SQL"), true, true),
                new Student(104, "Sneha Reddy", "B.Tech CSE", 2024, 58.0, 0, 4.8, Arrays.asList("Python", "Flask"), true, true),
                new Student(105, "Vikram Singh", "B.Tech IT", 2025, 70.0, 2, 4.3, Arrays.asList("Java", "React"), true, true),
                new Student(106, "Anjali Deshmukh", "B.Tech CSE", 2024, 85.0, 0, 3.2, Arrays.asList("Java", "Spring"), true, true),
                new Student(107, "Karan Mehta", "B.Tech IT", 2023, 76.0, 0, 4.4, Arrays.asList("Python", "SQL"), false, true),
                new Student(108, "Pooja Nair", "B.Tech CSE", 2024, 88.0, 0, 4.9, Arrays.asList("Java", "AWS"), true, false),
                new Student(109, "Rohan Gupta", "B.Tech IT", 2024, 72.0, 0, 4.2, Arrays.asList("HTML", "CSS", "JavaScript"), true, true),
                new Student(110, "Meera Joshi", "B.Tech CSE", 2020, 55.0, 3, 3.0, Arrays.asList("HTML", "CSS"), false, false)
        );

        PlacementRule graduationCheck =
                student -> student.getGraduationYear() >= 2022;

        PlacementRule percentageCheck =
                student -> student.getPercentage() >= 60;

        PlacementRule backLogCheck =
                student -> student.getBacklogs() == 0;

        PlacementRule mockRatingCheck =
                student -> student.getMockRating() >= 4.0;

        PlacementRule isResumeAvailable =
                Student::getResumeAvailable;

        PlacementRule feePaidCheck =
                student -> Boolean.TRUE.equals(student.getFeePaid());

        PlacementRule javaOrPython =
                student -> student.getSkills().contains("Java")
                        || student.getSkills().contains("Python");


        PlacementRule finalRule =
                graduationCheck.and(percentageCheck)
                        .and(backLogCheck)
                        .and(mockRatingCheck)
                        .and(isResumeAvailable)
                        .and(feePaidCheck)
                        .and(javaOrPython);


        System.out.println("Eligible Students for Placement:");

        int count = 1;

        List<String> rejectedStudents = new ArrayList<>();

        for (Student student : students)
        {
            List<String> failedRules = new ArrayList<>();

            if (!graduationCheck.validate(student))
            {
                failedRules.add("Graduation year before 2022");
            }

            if (!percentageCheck.validate(student))
            {
                failedRules.add("Percentage below 60");
            }

            if (!backLogCheck.validate(student))
            {
                failedRules.add("Backlogs available");
            }

            if (!mockRatingCheck.validate(student))
            {
                failedRules.add("Mock rating below 4");
            }

            if (!isResumeAvailable.validate(student))
            {
                failedRules.add("Resume missing");
            }

            if (!feePaidCheck.validate(student))
            {
                failedRules.add("Fee not paid");
            }

            if (!javaOrPython.validate(student))
            {
                failedRules.add("Java/Python skill missing");
            }


            if (failedRules.isEmpty())
            {
                String skill = student.getSkills().contains("Java")
                        ? "Java Fullstack"
                        : "Python Fullstack";

                System.out.println(
                        count++ + ". "
                                + student.getStudentName()
                                + " - "
                                + skill
                                + " - Rating: "
                                + student.getMockRating()
                );
            }
            else
            {
                rejectedStudents.add(
                        student.getStudentName()
                                + " - Reason: "
                                + String.join(", ", failedRules)
                );
            }
        }

        System.out.println("\nRejected Students:");
        rejectedStudents.forEach(System.out::println);
    }
}
