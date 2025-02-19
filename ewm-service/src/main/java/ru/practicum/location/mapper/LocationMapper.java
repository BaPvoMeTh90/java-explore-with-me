package ru.practicum.location.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.location.dto.LocationDto;
import ru.practicum.location.model.Location;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationMapper {
    public static Location locationDtoToLocation(LocationDto locationDto) {
        Location location = new Location();
        location.setLon(locationDto.getLon());
        location.setLat(locationDto.getLat());
        return location;
    }

    public static LocationDto locationToLocationDto(Location location) {
        LocationDto locationDto = new LocationDto();
        locationDto.setLon(location.getLon());
        locationDto.setLat(location.getLat());
        return locationDto;
    }
}

