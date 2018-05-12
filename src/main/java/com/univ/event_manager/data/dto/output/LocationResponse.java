package com.univ.event_manager.data.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationResponse {
    private double latitude;

    private double longitude;

    private String address;

    private String title;
}
