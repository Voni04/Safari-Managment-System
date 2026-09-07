package com.safari.safari_trip_system.service;

import com.safari.safari_trip_system.entity.Message;
import com.safari.safari_trip_system.entity.User;
import com.safari.safari_trip_system.repository.MessageRepository;
import com.safari.safari_trip_system.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    // 1. Send a message
    public void sendMessage(String senderUsername, String receiverUsername, String content) {
        User sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User receiver = userRepository.findByUsername(receiverUsername)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        message.setRead(false); // Default

        messageRepository.save(message);
    }

    // 2. Get chat history AND Mark messages as read
    @Transactional
    public List<Message> getConversation(String viewerUsername, String otherPartyUsername) {
        User viewer = userRepository.findByUsername(viewerUsername)
                .orElseThrow(() -> new RuntimeException("User 1 not found"));

        User otherParty = userRepository.findByUsername(otherPartyUsername)
                .orElseThrow(() -> new RuntimeException("User 2 not found"));

        // A. Mark incoming messages (from otherParty -> viewer) as READ
        List<Message> unread = messageRepository.findBySenderAndReceiverAndIsReadFalse(otherParty, viewer);
        for (Message m : unread) {
            m.setRead(true);
        }
        messageRepository.saveAll(unread);

        // B. Return full history
        return messageRepository.findChatHistory(viewer, otherParty);
    }

    // 3. Count Unread Messages (For badges)
    public long getUnreadCount(String senderUsername, String receiverUsername) {
        User sender = userRepository.findByUsername(senderUsername).orElseThrow();
        User receiver = userRepository.findByUsername(receiverUsername).orElseThrow();
        return messageRepository.countUnreadMessages(sender, receiver);
    }

    // 4. Delete Message (Only if NOT read)
    public void deleteMessage(Long messageId, String requesterUsername) {
        Message msg = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        // Security: Only the sender can delete
        if (!msg.getSender().getUsername().equals(requesterUsername)) {
            throw new RuntimeException("You can only delete your own messages");
        }

        // Logic: Can only delete if receiver has NOT read it
        if (msg.isRead()) {
            throw new RuntimeException("Cannot delete. Message already seen.");
        }

        messageRepository.delete(msg);
    }
}