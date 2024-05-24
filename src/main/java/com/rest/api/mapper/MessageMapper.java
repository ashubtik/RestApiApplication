package com.rest.api.mapper;

import com.rest.api.dto.MessageDTO;
import com.rest.api.entity.Message;

import java.util.List;
import java.util.stream.Collectors;

public class MessageMapper {

    public static MessageDTO convertToMessageDto(Message message) {
        return new MessageDTO(message.getContent(), UserMapper.convertUserToDto(message.getUser()));
    }

    public static List<MessageDTO> convertToMessageDtoList(List<Message> messages) {
        return messages.stream()
                .map(MessageMapper::convertToMessageDto)
                .collect(Collectors.toList());
    }
}
