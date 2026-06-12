import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoanEligibilityRuleEngine
{
    public static void main(String[] args)
    {
        List<Customer> customers = Arrays.asList(
                new Customer(109, "Rohan Gupta", 19, 70000.0, 800, 5000.0, "Salaried", 500000.0),
                new Customer(110, "Meera Joshi", 30, 45000.0, 790, 4000.0, "Salaried", 500000.0),
                new Customer(111, "Arjun Kumar", 32, 90000.0, 650, 5000.0, "Salaried", 600000.0),
                new Customer(112, "Neha Agarwal", 29, 70000.0, 780, 35000.0, "Salaried", 800000.0),
                new Customer(113, "Suresh Rao", 40, 80000.0, 790, 5000.0, "Business", 2000000.0),
                new Customer(114, "Deepak Yadav", 35, 85000.0, 800, 5000.0, "Self-Employed", 700000.0),
                new Customer(115, "Kavita Sharma", 65, 100000.0, 850, 5000.0, "Business", 1000000.0),
                new Customer(116, "Ritesh Patil", 27, 62000.0, 760, 28000.0, "Salaried", 700000.0)
        );

        LoanRule ageRule = c->c.getAge()>=21 && c.getAge()<=60;
        LoanRule salaryRule = c->c.getMonthlySalary()>=60000.0;
        LoanRule creditScoreRule=c->c.getCreditScore()>=750;
        LoanRule existingEmiRule = c->c.getExistingEmi()<c.getMonthlySalary()*40/100;
        LoanRule loanAmountRule  = c->c.getRequestedLoanAmount()<c.getMonthlySalary()*20;
        LoanRule employmentTypeRule=c->c.getEmploymentType().equalsIgnoreCase("SALARIED") || c.getEmploymentType().equalsIgnoreCase("BUSINESS");


        LoanRule finalRule = ageRule.and(salaryRule)
                .and(creditScoreRule)
                .and(existingEmiRule)
                .and(loanAmountRule)
                .and(employmentTypeRule);

        for(Customer customer : customers)
        {
            List<String> failedRules = new ArrayList<>();

            if(!ageRule.validate(customer))
            {
                failedRules.add("Age must be between 21 and 60");
            }

            if(!salaryRule.validate(customer))
            {
                failedRules.add("Monthly salary must be at least 60000");
            }

            if(!creditScoreRule.validate(customer))
            {
                failedRules.add("Credit score is below 750");
            }

            if(!existingEmiRule.validate(customer))
            {
                failedRules.add("Existing EMI is more than allowed limit");
            }

            if(!loanAmountRule.validate(customer))
            {
                failedRules.add("Requested loan amount exceeds eligibility");
            }

            if(!employmentTypeRule.validate(customer))
            {
                failedRules.add("Employment type is not eligible");
            }

            System.out.println("\n----------------------------------");
            System.out.println("Customer: " + customer.getCustomerName());

            if(failedRules.isEmpty())
            {
                System.out.println("Loan Status: APPROVED");
                System.out.println("Reason: All eligibility conditions satisfied");
            }
            else
            {
                System.out.println("Loan Status: REJECTED");
                System.out.println("Failed Rules:");

                failedRules.forEach(rule ->
                        System.out.println("- " + rule));
            }
        }
    }
}
