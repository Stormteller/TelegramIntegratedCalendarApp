package com.univ.event_manager.data.converter;

import com.univ.event_manager.data.dto.output.LocationResponse;
import com.univ.event_manager.data.entity.Location;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LocationToDtoConverter implements Converter<Location, LocationResponse> {
    @Override
    public LocationResponse convert(Location location) {
        return LocationResponse.builder()
                .address(location.getAddress())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .title(location.getTitle())
                .build();
    }
}
