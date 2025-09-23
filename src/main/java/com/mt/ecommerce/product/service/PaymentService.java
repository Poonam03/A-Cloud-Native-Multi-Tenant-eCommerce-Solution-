package com.mt.ecommerce.product.service;

import com.mt.ecommerce.product.entity.Payment;
import com.mt.ecommerce.product.model.PaymentBO;
import com.mt.ecommerce.product.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


    public PaymentBO getPayment(UUID orderID){
        Payment payment = this.paymentRepository.findByOrderId(orderID);
        PaymentBO paymentBO = new PaymentBO();
        paymentBO.setAmount(payment.getAmount());
        paymentBO.setOrderId(payment.getOrderId());
        return paymentBO;
    }

    public PaymentBO createPayment(PaymentBO paymentBO){
        Payment payment = new Payment();
        payment.setAmount(paymentBO.getAmount());
        payment.setOrderId(paymentBO.getOrderId());
        Payment savedPayment = this.paymentRepository.save(payment);
        paymentBO.setOrderId(savedPayment.getOrderId());
        paymentBO.setAmount(savedPayment.getAmount());
        return paymentBO;
    }
}
