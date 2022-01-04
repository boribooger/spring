package com.example.demo.repo;

import com.example.demo.domen.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessagesRepo extends CrudRepository<Message, Long> {
    List<Message> findByTag(String tag);
}
