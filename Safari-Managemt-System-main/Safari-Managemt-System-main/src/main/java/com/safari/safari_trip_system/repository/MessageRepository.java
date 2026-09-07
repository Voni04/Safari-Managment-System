package com.safari.safari_trip_system.repository;

import com.safari.safari_trip_system.entity.Message;
import com.safari.safari_trip_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    // Fetch conversation between two users
    @Query("SELECT m FROM Message m WHERE " +
            "(m.sender = :user1 AND m.receiver = :user2) OR " +
            "(m.sender = :user2 AND m.receiver = :user1) " +
            "ORDER BY m.timestamp ASC")
    List<Message> findChatHistory(@Param("user1") User user1, @Param("user2") User user2);

    // NEW: Count unread messages from a specific sender to a specific receiver
    @Query("SELECT COUNT(m) FROM Message m WHERE m.sender = :sender AND m.receiver = :receiver AND m.isRead = false")
    long countUnreadMessages(@Param("sender") User sender, @Param("receiver") User receiver);

    // NEW: Find all unread messages for a conversation (to mark them as read)
    List<Message> findBySenderAndReceiverAndIsReadFalse(User sender, User receiver);
}