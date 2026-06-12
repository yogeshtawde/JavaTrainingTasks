import java.util.*;
import java.util.function.*;

class Employee {
    private Integer employeeId;
    private String employeeName;
    private String department;
    private String role;
    private Integer experience;
    private Double salary;
    private Double performanceRating;

    public Employee(Integer employeeId, String employeeName, String department,
                    String role, Integer experience, Double salary, Double performanceRating) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.department = department;
        this.role = role;
        this.experience = experience;
        this.salary = salary;
        this.performanceRating = performanceRating;
    }


    public Integer getEmployeeId()
    {
        return employeeId;
    }
    public String getEmployeeName()
    {
        return employeeName;
    }
    public String getDepartment()
    {
        return department;
    }
    public String getRole()
    {
        return role;
    }
    public Integer getExperience()
    {
        return experience;
    }
    public Double getSalary()
    {
        return salary;
    }
    public Double getPerformanceRating()
    {
        return performanceRating;
    }
    public void setSalary(Double salary)
    {
        this.salary = salary;
    }
}

@FunctionalInterface
interface SalaryProcessor
{
    double process(Employee employee);
}

public class SalaryProcessingEngine
{
    public static void main(String[] args)
    {
        List<Employee> employees = Arrays.asList(
                new Employee(101, "Ravi", "IT", "Developer", 6, 60000.0, 4.7),
                new Employee(102, "Meena", "QA", "Tester", 3, 30000.0, 3.5),
                new Employee(103, "Arjun", "IT", "Developer", 2, 40000.0, 2.8),
                new Employee(104, "Priya", "HR", "Manager", 8, 50000.0, 4.2)
        );


        Predicate<Employee> highRating = e -> e.getPerformanceRating() >= 4.5;
        Predicate<Employee> experienced = e -> e.getExperience() >= 5;
        Predicate<Employee> isDeveloper = e -> e.getRole().equalsIgnoreCase("Developer");
        Predicate<Employee> isTester = e -> e.getRole().equalsIgnoreCase("Tester");
        Predicate<Employee> lowRating = e -> e.getPerformanceRating() < 3;


        Function<Employee, Double> hike20 = e -> e.getSalary() * 1.20;
        Function<Employee, Double> hike15 = e -> e.getSalary() * 1.15;
        Function<Employee, Double> hike10 = e -> e.getSalary() * 1.10;
        Function<Employee, Double> hike8  = e -> e.getSalary() * 1.08;
        Function<Employee, Double> noHike = e -> e.getSalary();


        Consumer<Employee> printDetails = e ->
        {
            System.out.println("Employee: " + e.getEmployeeName());
            System.out.println("Role: " + e.getRole());
            System.out.println("Final Salary: " + e.getSalary());
        };


        Comparator<Employee> bySalary = Comparator.comparing(Employee::getSalary);
        for (Employee e : employees)
        {
            double newSalary;
            String hikeApplied;

            if (lowRating.test(e))
            {
                newSalary = noHike.apply(e);
                hikeApplied = "0% (No hike)";
            }
            else if (highRating.test(e))
            {
                newSalary = hike20.apply(e);
                hikeApplied = "20%";
            }
            else if (experienced.test(e))
            {
                newSalary = hike15.apply(e);
                hikeApplied = "15%";
            }
            else if (isDeveloper.test(e))
            {
                newSalary = hike10.apply(e);
                hikeApplied = "10%";
            }
            else if (isTester.test(e))
            {
                newSalary = hike8.apply(e);
                hikeApplied = "8%";
            }
            else
            {
                newSalary = e.getSalary();
                hikeApplied = "0%";
            }

            System.out.println("Employee: " + e.getEmployeeName());
            System.out.println("Role: " + e.getRole());
            System.out.println("Old Salary: " + e.getSalary());
            System.out.println("Hike Applied: " + hikeApplied);
            e.setSalary(newSalary);
            System.out.println("Final Salary: " + e.getSalary());

        }
        employees.sort(bySalary);
        System.out.println("\nEmployees sorted by final salary:");
        employees.forEach(printDetails);
    }
}
