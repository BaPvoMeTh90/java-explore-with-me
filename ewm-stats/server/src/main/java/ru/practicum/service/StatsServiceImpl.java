package ru.practicum.service;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class StatsServiceImpl implements StatsService {

    @Override
    public EndpointHitDto hit(EndpointHitDto endpointHitDto) {
        return null;
    }

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, String uris, Boolean unique) {
        return List.of();
    }
}
