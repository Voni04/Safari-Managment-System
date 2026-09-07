package com.safari.safari_trip_system.controller;

import com.safari.safari_trip_system.service.FeedbackService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    // Show Feedback Form (For Tourist)
    @GetMapping("/feedback/add")
    public String showFeedbackForm(@RequestParam("bookingId") Long bookingId, Model model) {
        model.addAttribute("bookingId", bookingId);
        return "feedback-form";
    }

    // Process Feedback Submission
    @PostMapping("/feedback/add")
    public String submitFeedback(@RequestParam("bookingId") Long bookingId,
                                 @RequestParam("rating") Integer rating,
                                 @RequestParam("comments") String comments) {

        feedbackService.submitFeedback(bookingId, rating, comments);
        return "redirect:/my-bookings?feedbackSuccess=true";
    }

    // Handle Delete Feedback (NEW)
    @PostMapping("/feedback/delete")
    public String deleteFeedback(@RequestParam("feedbackId") Long feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
        return "redirect:/my-bookings?feedbackDeleted=true";
    }
}