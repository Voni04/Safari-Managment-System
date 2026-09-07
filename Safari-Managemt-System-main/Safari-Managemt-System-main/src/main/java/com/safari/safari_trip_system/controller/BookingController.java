package com.safari.safari_trip_system.controller;

import com.safari.safari_trip_system.service.BookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Show the Booking Page
    @GetMapping("/book")
    public String showBookingPage(Model model) {
        model.addAttribute("packages", bookingService.getAllPackages());
        return "book";
    }

    // Handle the Booking Form Submission
    @PostMapping("/book")
    public String processBooking(@RequestParam("packageId") Long packageId,
                                 @RequestParam("bookingDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                 Authentication authentication) {

        String username = authentication.getName();
        bookingService.createBooking(username, packageId, date);

        return "redirect:/?bookingSuccess";
    }

    // Show "My Bookings" Page
    @GetMapping("/my-bookings")
    public String showMyBookings(Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("bookings", bookingService.getUserBookings(username));
        return "my-bookings";
    }

    // Handle Cancellation (NEW FEATURE)
    @PostMapping("/booking/cancel")
    public String cancelBooking(@RequestParam("bookingId") Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return "redirect:/my-bookings?cancelled=true";
    }
}
