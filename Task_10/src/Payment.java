public class Payment {

    private Integer paymentId;
    private Integer studentId;
    private Double amount;
    private Boolean paid;

    public Payment(Integer paymentId,
                   Integer studentId,
                   Double amount,
                   Boolean paid) {

        this.paymentId = paymentId;
        this.studentId = studentId;
        this.amount = amount;
        this.paid = paid;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public Double getAmount() {
        return amount;
    }

    public Boolean getPaid() {
        return paid;
    }
}