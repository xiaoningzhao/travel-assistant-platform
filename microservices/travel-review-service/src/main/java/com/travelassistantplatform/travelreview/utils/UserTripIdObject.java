package com.travelassistantplatform.travelreview.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserTripIdObject {
    private Long userId;
    private List<Long> tripList;

    public UserTripIdObject() {
        this.tripList = new ArrayList<>();
    }
}
