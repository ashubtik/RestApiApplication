package com.rest.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messages")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Message {
    @Id
    @GeneratedValue
    @Column(name = "message_id")
    private Integer messageId;
    @Column(name = "content")
    private String content;
    @Column(name = "time_sent")
    private Timestamp time;
    @Column(name = "edited")
    private boolean edited;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
