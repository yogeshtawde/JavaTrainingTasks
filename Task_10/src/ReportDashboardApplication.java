

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class ReportDashboardApplication {

    public static void main(String[] args) {


        Course java = new Course(101, "Java Fullstack", 50000.0, "Rahul");

        Course python = new Course(102, "Python Fullstack", 45000.0, "Priya");

        Course devops = new Course(103, "DevOps", 40000.0, "Rahul");

        Course dataScience = new Course(104, "Data Science", 60000.0, "Sneha");

        Course aws = new Course(105, "AWS Cloud", 55000.0, "Priya");

        Course react = new Course(106, "React JS", 35000.0, "Kiran");

        List<Course> courses = Arrays.asList(java, python, devops, dataScience, aws, react);



        Student s1 = new Student(1, "Amit", "JAN");

        Student s2 = new Student(2, "Ravi", "JAN");

        Student s3 = new Student(3, "Priya", "FEB");

        Student s4 = new Student(4, "Sneha", "MAR");

        Student s5 = new Student(5, "Vijay", "MAR");

        Student s6 = new Student(6, "Anjali", "APR");

        Student s7 = new Student(7, "Karan", "APR");

        Student s8 = new Student(8, "Pooja", "MAY");

        List<Student> students = Arrays.asList(
                s1, s2, s3, s4,
                s5, s6, s7, s8
        );



        List<Enrollment> enrollments = Arrays.asList(

                new Enrollment(1, s1, java),
                new Enrollment(2, s2, java),

                new Enrollment(3, s3, python),

                new Enrollment(4, s4, devops),

                new Enrollment(5, s5, dataScience),

                new Enrollment(6, s6, aws),

                new Enrollment(7, s7, react),

                new Enrollment(8, s8, dataScience),

                new Enrollment(9, s2, aws),

                new Enrollment(10, s3, java)
        );



        List<Payment> payments = Arrays.asList(

                new Payment(1, 1, 50000.0, true),

                new Payment(2, 2, 50000.0, true),

                new Payment(3, 3, 45000.0, false),

                new Payment(4, 4, 40000.0, true),

                new Payment(5, 5, 60000.0, false),

                new Payment(6, 6, 55000.0, true),

                new Payment(7, 7, 35000.0, true),

                new Payment(8, 8, 60000.0, false)
        );



        Supplier<String> dashboardTitle =
                () -> "========== ADMIN REPORT DASHBOARD ==========";

        System.out.println(dashboardTitle.get());



        Consumer<String> printer =
                System.out::println;


        Function<List<Payment>, Double> totalRevenueFunction =
                paymentList ->
                        paymentList.stream()
                                .filter(Payment::getPaid)
                                .mapToDouble(Payment::getAmount)
                                .sum();



        Predicate<Payment> pendingPaymentPredicate =
                payment -> !payment.getPaid();



        ReportGenerator<Payment, Double> totalRevenueReport =
                paymentList ->
                        paymentList.stream()
                                .filter(Payment::getPaid)
                                .collect(Collectors.summingDouble(
                                        Payment::getAmount
                                ));

        Double totalRevenue =
                totalRevenueReport.generate(payments);

        printer.accept("\nTOTAL REVENUE REPORT");
        printer.accept("----------------------------");
        printer.accept("Total Revenue : ₹" + totalRevenue);



        ReportGenerator<Enrollment, Map<String, Long>>
                courseWiseEnrollmentReport =

                enrollmentList ->
                        enrollmentList.stream()
                                .collect(
                                        Collectors.groupingBy(
                                                enrollment ->
                                                        enrollment.getCourse()
                                                                .getCourseName(),
                                                Collectors.counting()
                                        )
                                );

        Map<String, Long> courseWiseData =
                courseWiseEnrollmentReport.generate(
                        enrollments
                );

        printer.accept("\nCOURSE WISE ENROLLMENT REPORT");
        printer.accept("--------------------------------");

        courseWiseData.forEach(
                (course, count) ->
                        printer.accept(
                                course + " : "
                                        + count
                                        + " Students"
                        )
        );



        ReportGenerator<Payment, Double> pendingPaymentReport =
                paymentList ->
                        paymentList.stream()
                                .filter(pendingPaymentPredicate)
                                .collect(
                                        Collectors.summingDouble(
                                                Payment::getAmount
                                        )
                                );

        Double pendingAmount =
                pendingPaymentReport.generate(payments);

        printer.accept("\nPENDING PAYMENT REPORT");
        printer.accept("---------------------------");
        printer.accept(
                "Pending Payments : ₹"
                        + pendingAmount
        );



        ReportGenerator<Course,
                Map<String, List<String>>> trainerWiseReport =

                courseList ->
                        courseList.stream()
                                .collect(
                                        Collectors.groupingBy(
                                                Course::getTrainerName,
                                                Collectors.mapping(
                                                        Course::getCourseName,
                                                        Collectors.toList()
                                                )
                                        )
                                );

        Map<String, List<String>> trainerData =
                trainerWiseReport.generate(courses);

        printer.accept("\nTRAINER WISE COURSE REPORT");
        printer.accept("--------------------------------");

        trainerData.forEach(
                (trainer, courseList) ->
                        printer.accept(
                                trainer + " -> "
                                        + courseList
                        )
        );



        ReportGenerator<Student,
                Map<String, Long>> monthlyAdmissionReport =

                studentList ->
                        studentList.stream()
                                .collect(
                                        Collectors.groupingBy(
                                                Student::getAdmissionMonth,
                                                Collectors.counting()
                                        )
                                );

        Map<String, Long> monthlyData =
                monthlyAdmissionReport.generate(students);

        printer.accept("\nMONTHLY ADMISSION REPORT");
        printer.accept("--------------------------------");

        monthlyData.forEach(
                (month, count) ->
                        printer.accept(
                                month + " : "
                                        + count
                                        + " Admissions"
                        )
        );



        ReportGenerator<Course,
                List<Course>> topFeeCoursesReport =

                courseList ->
                        courseList.stream()
                                .sorted(
                                        Comparator.comparing(
                                                Course::getCourseFee
                                        ).reversed()
                                )
                                .limit(5)
                                .collect(Collectors.toList());

        List<Course> topCourses =
                topFeeCoursesReport.generate(courses);

        printer.accept("\nTOP 5 HIGH FEE COURSES");
        printer.accept("--------------------------------");

        topCourses.forEach(course ->
                printer.accept(
                        course.getCourseName()
                                + " -> ₹"
                                + course.getCourseFee()
                )
        );


        printer.accept("\nTRAINER WISE COURSE COUNT");
        printer.accept("--------------------------------");

        Map<String, Long> trainerCourseCount =
                courses.stream()
                        .collect(
                                Collectors.groupingBy(
                                        Course::getTrainerName,
                                        Collectors.counting()
                                )
                        );

        trainerCourseCount.forEach(
                (trainer, count) ->
                        printer.accept(
                                trainer + " -> "
                                        + count
                                        + " Courses"
                        )
        );



        printer.accept("\nPAID STUDENTS REPORT");


        List<Integer> paidStudentIds =
                payments.stream()
                        .filter(Payment::getPaid)
                        .map(Payment::getStudentId)
                        .collect(Collectors.toList());

        students.stream()
                .filter(student ->
                        paidStudentIds.contains(
                                student.getStudentId()
                        ))
                .forEach(student ->
                        printer.accept(
                                student.getStudentName()
                        )
                );



        printer.accept("\nDASHBOARD SUMMARY");
        printer.accept("--------------------------------");

        printer.accept(
                "Total Students : "
                        + students.size()
        );

        printer.accept(
                "Total Courses : "
                        + courses.size()
        );

        printer.accept(
                "Total Enrollments : "
                        + enrollments.size()
        );

        printer.accept(
                "Revenue Generated : ₹"
                        + totalRevenue
        );

        printer.accept(
                "Pending Revenue : ₹"
                        + pendingAmount
        );
    }
}