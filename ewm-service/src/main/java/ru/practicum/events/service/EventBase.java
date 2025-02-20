package ru.practicum.events.service;

import org.springframework.stereotype.Service;
import ru.practicum.base.service.BaseService;
import ru.practicum.client.StatsClient;

import ru.practicum.requests.repository.RequestRepository;

@Service
abstract class EventBase extends BaseService {
    public EventBase(RequestRepository requestRepository, StatsClient statsClient) {
        super(requestRepository, statsClient);
    }
}
