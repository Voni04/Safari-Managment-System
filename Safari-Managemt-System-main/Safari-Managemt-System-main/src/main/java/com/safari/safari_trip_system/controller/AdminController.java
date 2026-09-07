package com.safari.safari_trip_system.controller;

import com.safari.safari_trip_system.entity.User;
import com.safari.safari_trip_system.service.AdminService;
import com.safari.safari_trip_system.service.FeedbackService;
import com.safari.safari_trip_system.service.MessageService;
import com.safari.safari_trip_system.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    private final AdminService adminService;
    private final FeedbackService feedbackService;
    private final UserService userService; // To get tourist list
    private final MessageService messageService; // To get unread counts

    public AdminController(AdminService adminService,
                           FeedbackService feedbackService,
                           UserService userService,
                           MessageService messageService) {
        this.adminService = adminService;
        this.feedbackService = feedbackService;
        this.userService = userService;
        this.messageService = messageService;
    }

    // Dashboard
    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(Model model) {
        model.addAttribute("bookings", adminService.getConfirmedBookings());
        model.addAttribute("guides", adminService.getAllGuides());
        model.addAttribute("vehicles", adminService.getAllVehicles());
        model.addAttribute("feedbacks", feedbackService.getAllFeedbacks());
        model.addAttribute("totalRevenue", adminService.calculateTotalRevenue());
        model.addAttribute("confirmedCount", adminService.countConfirmedBookings());
        model.addAttribute("pendingCount", adminService.countPendingBookings());
        return "admin-dashboard";
    }

    // Assignments
    @PostMapping("/admin/assign")
    public String assignResources(@RequestParam("bookingId") Long bookingId,
                                  @RequestParam("guideId") Long guideId,
                                  @RequestParam("vehicleId") Long vehicleId) {
        adminService.assignResources(bookingId, guideId, vehicleId);
        return "redirect:/admin/dashboard?assigned=true";
    }

    // Feedback Reply
    @PostMapping("/admin/feedback/reply")
    public String submitAdminReply(@RequestParam("feedbackId") Long feedbackId,
                                   @RequestParam("reply") String reply) {
        feedbackService.submitAdminReply(feedbackId, reply);
        return "redirect:/admin/dashboard?replySuccess=true";
    }

    @PostMapping("/admin/feedback/reply/delete")
    public String deleteAdminReply(@RequestParam("feedbackId") Long feedbackId) {
        feedbackService.deleteAdminReply(feedbackId);
        return "redirect:/admin/dashboard?replyDeleted=true";
    }

    // UPDATED: Show All Tourists + Unread Counts
    @GetMapping("/admin/tourists")
    public String showAllTourists(Model model) {
        List<User> tourists = userService.getAllTourists();
        model.addAttribute("tourists", tourists);

        // Calculate unread messages for each tourist
        Map<String, Long> unreadCounts = new HashMap<>();
        for (User t : tourists) {
            // Count messages FROM tourist TO admin that are UNREAD
            long count = messageService.getUnreadCount(t.getUsername(), "admin");
            unreadCounts.put(t.getUsername(), count);
        }
        model.addAttribute("unreadCounts", unreadCounts);

        return "admin-tourists";
    }
}