package com.univ.event_manager.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Location {
    @Column(name = "location_latitude")
    private double latitude;

    @Column(name = "location_longitude")
    private double longitude;

    @Column(name = "location_address")
    private String address;

    @Column(name = "location_title")
    private String title;
}
