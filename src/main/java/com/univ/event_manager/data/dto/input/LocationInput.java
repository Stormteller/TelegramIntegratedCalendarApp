package com.univ.event_manager.data.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationInput {
    private double latitude;

    private double longitude;

    private String address;

    private String title;
}
