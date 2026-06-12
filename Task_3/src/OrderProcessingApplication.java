

import java.util.Arrays;

public class OrderProcessingApplication
{

    public static void main(String[] args) {


        OrderProcessor validateOrder = order -> {

            if (order.getOrderId() == null ||
                    order.getCustomerName() == null ||
                    order.getTotalAmount() <= 0) {

                throw new RuntimeException("Invalid Order Details");
            }

            System.out.println("Order Validated Successfully");

            return order;
        };

        OrderProcessor applyCoupon = order -> {

            if ("GENAI10".equalsIgnoreCase(order.getCouponCode())) {

                double discount =
                        order.getTotalAmount() * 0.10;

                order.setTotalAmount(
                        order.getTotalAmount() - discount
                );

                System.out.println("Coupon Applied : GENAI10");
            }

            return order;
        };


        OrderProcessor calculateGST = order -> {

            double gst =
                    order.getTotalAmount() * 0.18;

            order.setTotalAmount(
                    order.getTotalAmount() + gst
            );

            System.out.println("GST Added : 18%");

            return order;
        };


        OrderProcessor confirmPayment = order -> {

            order.setPaymentStatus("SUCCESS");

            System.out.println("Payment Confirmed");

            return order;
        };


        OrderProcessor assignDeliveryPartner = order -> {

            System.out.println("Delivery Partner Assigned");

            return order;
        };

        OrderProcessor updateDeliveryStatus = order -> {

            order.setDeliveryStatus("ASSIGNED");

            System.out.println("Delivery Status Updated");

            return order;
        };


        OrderProcessor workflow =
                validateOrder
                        .andThen(applyCoupon)
                        .andThen(calculateGST)
                        .andThen(confirmPayment)
                        .andThen(assignDeliveryPartner)
                        .andThen(updateDeliveryStatus);


        Order order = new Order();

        order.setOrderId("ORD101");
        order.setCustomerName("Yogesh Tawde");
        order.setItems(Arrays.asList(
                "Laptop",
                "Mouse",
                "Keyboard"
        ));
        order.setTotalAmount(5000.0);
        order.setCouponCode("GENAI10");

        double originalAmount = order.getTotalAmount();

        Order processedOrder =
                workflow.process(order);


        System.out.println("\n===== ORDER SUMMARY =====");

        System.out.println("Order ID          : "
                + processedOrder.getOrderId());

        System.out.println("Customer Name     : "
                + processedOrder.getCustomerName());

        System.out.println("Original Amount   : "
                + originalAmount);

        System.out.println("Coupon Applied    : "
                + processedOrder.getCouponCode());

        System.out.println("GST Added         : 18%");

        System.out.println("Final Amount      : "
                + processedOrder.getTotalAmount());

        System.out.println("Payment Status    : "
                + processedOrder.getPaymentStatus());

        System.out.println("Delivery Status   : "
                + processedOrder.getDeliveryStatus());
    }
}