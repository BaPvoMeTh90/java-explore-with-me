package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.mapper.EndpointHitMapper;
import ru.practicum.model.EndpointHit;
import ru.practicum.dto.ViewStats;
import ru.practicum.exception.ValidationException;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    @Override
    public void saveHit(EndpointHitDto hitDto) {
        EndpointHit hit = EndpointHitMapper.toEntity(hitDto);
        statsRepository.save(hit);
        log.info("Сохранили запрос {}", hit);
    }

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        if (end.isBefore(start)) {
            throw new ValidationException("Дата начала периода не может быть ранее даты окончания периода.");
        }
        return statsRepository.getStats(start, end, uris, unique);
    }
}