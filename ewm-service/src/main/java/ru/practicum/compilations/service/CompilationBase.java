package ru.practicum.compilations.service;

import org.springframework.stereotype.Service;
import ru.practicum.base.service.BaseService;
import ru.practicum.client.StatsClient;

import ru.practicum.requests.repository.RequestRepository;

@Service
abstract class CompilationBase extends BaseService {
    public CompilationBase(RequestRepository requestRepository, StatsClient statsClient) {
        super(requestRepository, statsClient);
    }
}
