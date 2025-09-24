package com.mt.ecommerce.product.service;

import com.mt.ecommerce.product.entity.Payment;
import com.mt.ecommerce.product.model.PaymentBO;
import com.mt.ecommerce.product.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


    public List<PaymentBO> getPayment(UUID vendorId){
        return this.paymentRepository.findAllByCreditorId(vendorId)
                .stream().map(payment -> {
                    PaymentBO paymentBO = new PaymentBO();
                    paymentBO.setAmount(payment.getAmount());
                    paymentBO.setOrderId(payment.getOrderId());
                    paymentBO.setUserName(payment.getDebitorId());
                    paymentBO.setId(payment.getId());
                    return paymentBO;
                })
                .collect(Collectors.toList());

    }

    public PaymentBO createPayment(PaymentBO paymentBO){
        Payment payment = new Payment();
        payment.setAmount(paymentBO.getAmount());
        payment.setOrderId(paymentBO.getOrderId());
        payment.setCreditorId(paymentBO.getVendorId());
        payment.setDebitorId(paymentBO.getUserName());
        Payment savedPayment = this.paymentRepository.save(payment);
        paymentBO.setOrderId(savedPayment.getOrderId());
        paymentBO.setAmount(savedPayment.getAmount());
        return paymentBO;
    }
}
