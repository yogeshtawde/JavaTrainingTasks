import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CourseRecommendationSystem
{
    public static void main(String[] args)
    {
        Student student = new Student(1, "Suresh", "B.Tech",
                Arrays.asList("Java", "SQL"), 3, "Java", 20000.0);
        List<Course> courses = Arrays.asList(
                new Course(101, "Java Fullstack", "Java", "Advanced", 25000.0, "6 months", 4.8),
                new Course(102, "Spring Boot Microservices", "Java", "Advanced", 18000.0, "4 months", 4.7),
                new Course(103, "Python Fullstack", "Python", "Advanced", 22000.0, "6 months", 4.6),
                new Course(104, "Data Science with Python", "Python", "Advanced", 30000.0, "8 months", 4.9),
                new Course(105, "Java Basics", "Java", "Beginner", 15000.0, "3 months", 4.2)
        );

        Predicate<Course> affordable = c -> c.getFee() <= student.getBudget();

        Function<Student, List<Course>> recommendCourses = s -> courses.stream()
                .filter(c -> s.getSkills().contains(c.getTechnology())) // skill match
                .filter(affordable)
                .filter(c -> (s.getExperience() == 0 && c.getLevel().equalsIgnoreCase("Beginner")) ||
                        (s.getExperience() >= 3 && c.getLevel().equalsIgnoreCase("Advanced")))
                .sorted(Comparator.comparing(Course::getRating).reversed())
                .collect(Collectors.toList());


        Consumer<Course> printCourse = c -> System.out.println(
                c.getCourseName() + " - ₹" + c.getFee() + " - Rating: " + c.getRating()
        );

        List<Course> recommended = recommendCourses.apply(student);
        System.out.println("Recommended Courses for " + student.getStudentName() + ":");
        recommended.forEach(printCourse);
    }
}
