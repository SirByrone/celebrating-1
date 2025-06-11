package com.celebrating.messaging.model;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table("messages")
public class Message {
    @PrimaryKey
    private UUID id = Uuids.timeBased();
    private String senderId;
    private String recipientId;
    private String content;
    private Instant timestamp;

    // Getters and Setters
}