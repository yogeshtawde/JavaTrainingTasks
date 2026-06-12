import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

class PaymentRequest
{
    private String paymentId;
    private String customerName;
    private Double amount;
    private String paymentMode;
    private String couponCode;
    private String bankName;
    private String walletName;


    public PaymentRequest(String paymentId, String customerName, Double amount, String paymentMode, String couponCode, String bankName, String walletName) {
        this.paymentId = paymentId;
        this.customerName = customerName;
        this.amount = amount;
        this.paymentMode = paymentMode;
        this.couponCode = couponCode;
        this.bankName = bankName;
        this.walletName = walletName;
    }


    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "paymentId='" + paymentId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", amount=" + amount +
                ", paymentMode='" + paymentMode + '\'' +
                ", couponCode='" + couponCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", walletName='" + walletName + '\'' +
                '}';
    }
}

public class PaymentGatewayTest
{
    public static void main(String[] args) {
        PaymentRequest request = new PaymentRequest("PAY101", "Rahul Sharma", 25000.0, "UPI", "COURSE10", "HDFC", "Paytm");

        Predicate<PaymentRequest> amountValidator = payment->payment.getAmount()>-0;
        Function<PaymentRequest, PaymentRequest> applyCoupon =
                payment -> {

                    if ("COURSE10".equalsIgnoreCase(
                            payment.getCouponCode())) {

                        double discount =
                                payment.getAmount() * 0.10;

                        payment.setAmount(
                                payment.getAmount() - discount);
                    }

                    return payment;
                };

        Function<PaymentRequest, PaymentRequest> addGatewayCharge =
                payment -> {

                    double charge =
                            payment.getAmount() * 0.02;

                    payment.setAmount(
                            payment.getAmount() + charge);

                    return payment;
                };
        Supplier<String> transactionSupplier =
                () -> "TXN" + System.currentTimeMillis();



        PaymentGateway upiGateway = payment ->

                new PaymentResponse(
                        transactionSupplier.get(),
                        "SUCCESS",
                        payment.getAmount(),
                        "UPI Payment Successful"
                );



        PaymentGateway cardGateway = payment ->

                new PaymentResponse(
                        transactionSupplier.get(),
                        "SUCCESS",
                        payment.getAmount(),
                        "Credit Card Payment Successful"
                );



        PaymentGateway netBankingGateway = payment ->

                new PaymentResponse(
                        transactionSupplier.get(),
                        "SUCCESS",
                        payment.getAmount(),
                        "Net Banking Payment Successful"
                );



        PaymentGateway walletGateway = payment ->

                new PaymentResponse(
                        transactionSupplier.get(),
                        "SUCCESS",
                        payment.getAmount(),
                        "Wallet Payment Successful"
                );



        Map<String, PaymentGateway> gateways =
                new HashMap<>();

        gateways.put("UPI", upiGateway);
        gateways.put("CARD", cardGateway);
        gateways.put("NETBANKING", netBankingGateway);
        gateways.put("WALLET", walletGateway);



        if (!amountValidator.test(request)) {

            System.out.println("Invalid Amount");
            return;
        }

        double originalAmount = request.getAmount();



        request = applyCoupon.apply(request);



        request = addGatewayCharge.apply(request);


        PaymentGateway gateway =
                gateways.get(request.getPaymentMode());

        PaymentResponse response =
                gateway.pay(request);



        System.out.println("Payment Mode : "
                + request.getPaymentMode());

        System.out.println("Original Amount : "
                + originalAmount);

        System.out.println("Coupon Applied : "
                + request.getCouponCode());

        System.out.println("Final Amount : "
                + request.getAmount());

        System.out.println(response);
    }
}
