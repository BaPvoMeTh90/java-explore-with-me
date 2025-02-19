package ru.practicum.events.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.events.dto.EventDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.EventUpdateUser;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.model.Event;
import ru.practicum.location.mapper.LocationMapper;
import ru.practicum.location.model.Location;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMapper {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static EventDto eventToEventDto(Event event, Long confirmedRequests, Long views) {
        String publishedOn = null;
        if (event.getPublishedOn() != null) {
            publishedOn = event.getPublishedOn().format(formatter);
        }
        return new EventDto(
                event.getId(),
                event.getTitle(),
                event.getAnnotation(),
                CategoryMapper.categoryToCategoryDto(event.getCategory()),
                event.getPaid(),
                event.getEventDate().format(formatter),
                UserMapper.userToUserShortDto(event.getInitiator()),
                event.getDescription(),
                event.getParticipantLimit(),
                event.getState(),
                event.getCreatedOn().format(formatter),
                LocationMapper.locationToLocationDto(event.getLocation()),
                event.getRequestModeration(),
                confirmedRequests,
                publishedOn,
                views
        );
    }

    public static Event newEventDtoToEvent(NewEventDto dto, Category category, User user, Location location) {

        return new Event(
                0L,
                user,
                dto.getAnnotation(),
                category,
                LocalDateTime.now(),
                dto.getDescription(),
                dto.getEventDate(),
                location,
                dto.getPaid(),
                dto.getParticipantLimit(),
                null,
                dto.getRequestModeration(),
                null,
                dto.getTitle());
    }

    public static Event eventToEventUpdateUser(Event event, EventUpdateUser eventUpdateDto) {
        if (eventUpdateDto.getAnnotation() != null) {
            event.setAnnotation(eventUpdateDto.getAnnotation());
        }
        if (eventUpdateDto.getDescription() != null) {
            event.setDescription(eventUpdateDto.getDescription());
        }
        if (eventUpdateDto.getLocation() != null) {
            LocationMapper.locationDtoToLocation(eventUpdateDto.getLocation());
        }
        if (eventUpdateDto.getPaid() != null) {
            event.setPaid(eventUpdateDto.getPaid());
        }
        if (eventUpdateDto.getParticipantLimit() != null) {
            event.setParticipantLimit(eventUpdateDto.getParticipantLimit());
        }
        if (eventUpdateDto.getRequestModeration() != null) {
            event.setRequestModeration(eventUpdateDto.getRequestModeration());
        }
        if (eventUpdateDto.getTitle() != null) {
            event.setTitle(eventUpdateDto.getTitle());
        }
        return event;
    }

    public static List<EventShortDto> eventListToEventShortDtoList(List<Event> events,
                                                            Map<Long, Long> viewStatMap,
                                                            Map<Long, Long> confirmedRequests) {
        List<EventShortDto> dtos = new ArrayList<>();
        for (Event event : events) {
            Long views = viewStatMap.getOrDefault(event.getId(), 0L);
            Long confirmedRequestsCount = confirmedRequests.getOrDefault(event.getId(), 0L);
            dtos.add(new EventShortDto(
                    event.getAnnotation(),
                    CategoryMapper.categoryToCategoryDto(event.getCategory()),
                    confirmedRequestsCount,
                    event.getEventDate().format(formatter),
                    event.getId(),
                    UserMapper.userToUserShortDto(event.getInitiator()),
                    event.getPaid(),
                    event.getTitle(),
                    views
            ));
        }
        dtos.sort(Comparator.comparing(EventShortDto::getViews).reversed());
        return dtos;
    }

    public static EventShortDto eventToEventShortDto(Event event, Long views, Long confirmed) {
        return new EventShortDto(
                event.getAnnotation(),
                CategoryMapper.categoryToCategoryDto(event.getCategory()),
                confirmed,
                event.getEventDate().format(formatter),
                event.getId(),
                UserMapper.userToUserShortDto(event.getInitiator()),
                event.getPaid(),
                event.getTitle(),
                views);
    }
}
