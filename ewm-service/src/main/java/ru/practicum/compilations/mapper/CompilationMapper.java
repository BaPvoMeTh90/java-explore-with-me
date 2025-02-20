package ru.practicum.compilations.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.model.Compilation;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.mapper.EventMapper;
import ru.practicum.events.model.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompilationMapper {

    public static CompilationDto compilationToCompilationDto(Compilation compilation, List<EventShortDto> events) {
        return new CompilationDto(compilation.getId(),
                events,
                compilation.getPinned(),
                compilation.getTitle());
    }

    public static Compilation compilationDtoToCompilation(NewCompilationDto dto, List<Event> events) {
        return new Compilation(
                null,
                dto.getPinned(),
                dto.getTitle(),
                events);
    }

    public static List<CompilationDto> compilationsListToCompilationDtoList(List<Compilation> compilations,
                                                                     Map<Long, Long> confirmed, Map<Long, Long> views) {
        List<CompilationDto> result = new ArrayList<>();
        for (Compilation compilation : compilations) {
            CompilationDto compilationDto = new CompilationDto(compilation.getId(),
                    EventMapper.eventListToEventShortDtoList(new ArrayList<>(compilation.getEvents()), views, confirmed),
                    compilation.getPinned(),
                    compilation.getTitle());
            result.add(compilationDto);
        }
        return result;
    }
}
