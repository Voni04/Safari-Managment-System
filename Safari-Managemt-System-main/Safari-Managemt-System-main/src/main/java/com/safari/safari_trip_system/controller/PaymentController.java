package com.safari.safari_trip_system.controller;

import com.safari.safari_trip_system.entity.Booking;
import com.safari.safari_trip_system.entity.SavedCard;
import com.safari.safari_trip_system.service.PaymentService;
import com.safari.safari_trip_system.service.SavedCardService; // Import this
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentController {

    private final PaymentService paymentService;
    private final SavedCardService savedCardService; // Add this

    public PaymentController(PaymentService paymentService, SavedCardService savedCardService) {
        this.paymentService = paymentService;
        this.savedCardService = savedCardService;
    }

    // Show Payment Page
    @GetMapping("/pay")
    public String showPaymentPage(@RequestParam("bookingId") Long bookingId,
                                  Model model,
                                  Authentication authentication) {

        Booking booking = paymentService.getBookingById(bookingId);
        model.addAttribute("booking", booking);

        // NEW: Fetch saved card if exists
        String username = authentication.getName();
        SavedCard savedCard = savedCardService.getCard(username);

        if (savedCard != null) {
            model.addAttribute("savedCard", savedCard);
        }

        return "payment";
    }

    // Process Payment Submission
    @PostMapping("/process-payment")
    public String processPayment(@RequestParam("bookingId") Long bookingId,
                                 @RequestParam("amount") Double amount,
                                 // Card Details
                                 @RequestParam("cardHolder") String holder,
                                 @RequestParam("cardNumber") String number,
                                 @RequestParam("expiry") String expiry,
                                 @RequestParam("cvv") String cvv,
                                 // Checkbox for saving
                                 @RequestParam(value = "saveCard", required = false) boolean saveCard,
                                 Authentication authentication) {

        // 1. Save Card Logic
        if (saveCard) {
            String username = authentication.getName();
            savedCardService.saveCard(username, holder, number, expiry, cvv);
        }

        // 2. Process Payment
        paymentService.processPayment(bookingId, amount);

        return "redirect:/my-bookings?paid=true";
    }
}