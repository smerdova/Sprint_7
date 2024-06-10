package ru.praktikum.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderCancelRequest {
    private Integer track;

    public OrderCancelRequest(Integer track) {
        this.track = track;
    }

    public Integer getTrack() {
        return track;
    }

    public void setTrack(Integer track) {
        this.track = track;
    }
}
