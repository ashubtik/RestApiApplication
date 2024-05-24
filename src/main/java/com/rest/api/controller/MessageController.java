package com.rest.api.controller;

import com.rest.api.dto.MessageDTO;
import com.rest.api.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/messages")
    public ResponseEntity<List<MessageDTO>> getMessages() {
        return ResponseEntity.ok(this.messageService.getAllMessages());
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<MessageDTO> getMessage(@PathVariable Integer messageId) {
        return ResponseEntity.ok(this.messageService.getMessageById(messageId));
    }

    @GetMapping("/users/{userId}/messages")
    public ResponseEntity<List<MessageDTO>> getUserMassages(@PathVariable Integer userId) {
        return ResponseEntity.ok(this.messageService.getAllUserMessages(userId));
    }

    @PostMapping("/users/{userId}/messages")
    public ResponseEntity<MessageDTO> postMessage(
            @PathVariable Integer userId,
            @RequestBody MessageDTO message) {
        return new ResponseEntity<>(this.messageService.createMessage(userId, message), HttpStatus.CREATED);
    }

    @PutMapping("/messages/{messageId}")
    public ResponseEntity<MessageDTO> putMessage(@PathVariable Integer messageId,
                                              @RequestBody MessageDTO message) {
        return ResponseEntity.ok(this.messageService.updateMessage(messageId, message));
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<MessageDTO> deleteMessage(@PathVariable Integer messageId) {
        this.messageService.deleteMessage(messageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
