package com.colosseo.dto.alarm;

import com.colosseo.dto.user.UserDto;
import com.colosseo.global.enums.AlarmType;
import com.colosseo.model.alarm.Alarm;
import com.colosseo.model.alarm.AlarmDetails;
import com.colosseo.model.user.User;
import lombok.Builder;

import java.time.LocalDateTime;

public class AlarmResponse {
    private UserDto userDto;
    private AlarmType alarmType;
    private AlarmDetails alarmDetails;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public AlarmResponse(UserDto userDto, AlarmType alarmType, AlarmDetails alarmDetails, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userDto = userDto;
        this.alarmType = alarmType;
        this.alarmDetails = alarmDetails;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static AlarmResponse toDto(Alarm alarm) {
        return AlarmResponse.builder()
                .userDto(alarm.getUser().toDto())
                .alarmDetails(alarm.getAlarmDetails())
                .alarmType(alarm.getAlarmType())
                .createdAt(alarm.getCreatedAt())
                .updatedAt(alarm.getUpdatedAt())
                .build();
    }
}
