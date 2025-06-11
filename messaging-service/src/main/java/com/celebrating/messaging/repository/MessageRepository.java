package com.celebrating.messaging.repository;

import com.celebrating.messaging.model.Message;
import org.springframework.data.cassandra.repository.CassandraRepository;
import java.util.UUID;

public interface MessageRepository extends CassandraRepository<Message, UUID> {
}