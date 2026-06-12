public interface PaymentGateway
{
    PaymentResponse pay(PaymentRequest request);
}
