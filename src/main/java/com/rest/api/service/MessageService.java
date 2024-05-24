package com.rest.api.service;

import com.rest.api.dto.MessageDTO;
import com.rest.api.entity.Message;
import com.rest.api.exeptions.ResourceNotFoundException;
import com.rest.api.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

import static com.rest.api.mapper.MessageMapper.*;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserService userService;

    public MessageDTO getMessageById(Integer messageId) {
        return convertToMessageDto(getMessageFromDB(messageId));
    }

    public List<MessageDTO> getAllMessages() {
        return convertToMessageDtoList(this.messageRepository.findAll());
    }

    public List<MessageDTO> getAllUserMessages(Integer userId) {
        return convertToMessageDtoList(this.messageRepository.findByUserId(
                this.userService.getUserFromDB(userId)));
    }

    public MessageDTO createMessage(Integer userId, MessageDTO messageDTO) {
        var user = this.userService.getUserFromDB(userId);
        Message message = new Message();
        message.setContent(messageDTO.getContent());
        message.setEdited(false);
        message.setTime(new Timestamp(System.currentTimeMillis()));
        message.setUser(user);
        return convertToMessageDto(this.messageRepository.save(message));
    }

    public MessageDTO updateMessage(Integer messageId, MessageDTO newMessage) {
        var oldMessage = getMessageFromDB(messageId);
        oldMessage.setTime(new Timestamp(System.currentTimeMillis()));
        oldMessage.setContent(newMessage.getContent());
        oldMessage.setEdited(true);
        return convertToMessageDto(this.messageRepository.save(oldMessage));
    }

    public void deleteMessage(Integer messageId) {
        this.messageRepository.deleteById(messageId);
    }

    private Message getMessageFromDB(Integer messageId) {
        return this.messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Message with id: " + messageId + " doesn't exist"));
    }
}
