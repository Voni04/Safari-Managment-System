package com.safari.safari_trip_system.service;

import com.safari.safari_trip_system.entity.Booking;
import com.safari.safari_trip_system.entity.Feedback;
import com.safari.safari_trip_system.repository.BookingRepository;
import com.safari.safari_trip_system.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final BookingRepository bookingRepository;

    public FeedbackService(FeedbackRepository feedbackRepository, BookingRepository bookingRepository) {
        this.feedbackRepository = feedbackRepository;
        this.bookingRepository = bookingRepository;
    }

    // 1. Tourist submits feedback
    public void submitFeedback(Long bookingId, Integer rating, String comments) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Feedback feedback = new Feedback();
        feedback.setBooking(booking);
        feedback.setRating(rating);
        feedback.setComments(comments);
        feedback.setSubmitDate(LocalDateTime.now());

        feedbackRepository.save(feedback);
    }

    // 2. Admin replies to feedback
    public void submitAdminReply(Long feedbackId, String reply) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));

        feedback.setAdminReply(reply);
        feedbackRepository.save(feedback);
    }

    // 3. Get all feedbacks (For Admin Dashboard)
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    // 4. Delete Feedback (Tourist Action) - NEW
    public void deleteFeedback(Long feedbackId) {
        if (!feedbackRepository.existsById(feedbackId)) {
            throw new RuntimeException("Feedback not found");
        }
        feedbackRepository.deleteById(feedbackId);
    }

    // 5. Delete Admin Reply (Admin Action) - NEW
    public void deleteAdminReply(Long feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));

        feedback.setAdminReply(null); // Clear the reply
        feedbackRepository.save(feedback); // Save the update
    }
}