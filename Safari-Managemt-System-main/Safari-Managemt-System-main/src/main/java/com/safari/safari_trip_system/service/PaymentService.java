package com.safari.safari_trip_system.service;

import com.safari.safari_trip_system.designpatterns.decorator.BaseBookingPrice;
import com.safari.safari_trip_system.designpatterns.decorator.PriceComponent;
import com.safari.safari_trip_system.designpatterns.decorator.WithTaxDecorator;
import com.safari.safari_trip_system.designpatterns.observer.BookingSubject;
import com.safari.safari_trip_system.designpatterns.observer.EmailNotificationObserver;
import com.safari.safari_trip_system.designpatterns.observer.SmsNotificationObserver;
import com.safari.safari_trip_system.designpatterns.singleton.TaxConfig;
import com.safari.safari_trip_system.entity.Booking;
import com.safari.safari_trip_system.entity.Payment;
import com.safari.safari_trip_system.repository.BookingRepository;
import com.safari.safari_trip_system.repository.PaymentRepository;
import org.springframework.mail.javamail.JavaMailSender; // Import this
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;
    private final JavaMailSender javaMailSender; // New field

    // Observer Subject
    private final BookingSubject bookingSubject;

    // Inject JavaMailSender here
    public PaymentService(BookingRepository bookingRepository,
                          PaymentRepository paymentRepository,
                          JavaMailSender javaMailSender) {
        this.bookingRepository = bookingRepository;
        this.paymentRepository = paymentRepository;
        this.javaMailSender = javaMailSender;

        // Initialize Observer logic
        this.bookingSubject = new BookingSubject();
        // Pass the mail sender to the Email Observer
        this.bookingSubject.attach(new EmailNotificationObserver(javaMailSender));
        this.bookingSubject.attach(new SmsNotificationObserver());
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    @Transactional
    public void processPayment(Long bookingId, Double rawAmount) {
        Booking booking = getBookingById(bookingId);

        // --- PATTERN 1 & 3 USE: Decorator + Singleton ---
        double taxRate = TaxConfig.getInstance().getTaxRate();

        PriceComponent calculation = new BaseBookingPrice(rawAmount);
        calculation = new WithTaxDecorator(calculation, taxRate);

        double finalAmount = calculation.getCost();

        System.out.println("--- PATTERN CHECK ---");
        System.out.println("Base Price: " + rawAmount);
        System.out.println("Tax Rate (Singleton): " + taxRate);
        System.out.println("Final Price (Decorator): " + finalAmount);
        System.out.println("---------------------");

        // Save Payment
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(finalAmount);
        payment.setPaymentDate(LocalDateTime.now());

        paymentRepository.save(payment);

        booking.setStatus("CONFIRMED");
        bookingRepository.save(booking);

        // --- PATTERN 2 & 4 USE: Observer + Factory ---
        bookingSubject.notifyObservers(booking);
    }
}