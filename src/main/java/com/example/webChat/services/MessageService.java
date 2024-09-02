package com.example.webChat.services;

import com.example.webChat.entities.Message;
import com.example.webChat.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getLastNMessages() {
        return messageRepository.findTop10ByOrderByIdDesc();
    }

    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

}
