package com.colosseo.model.alarm;

import com.colosseo.global.enums.AlarmType;
import com.colosseo.model.BaseEntity;
import com.colosseo.model.user.User;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Type;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(name = "IDX_ALARM_CREATED_AT", columnList = "created_at"),
        @Index(name = "IDX_ALARM_USER", columnList = "user_id")
})
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE alarm SET is_deleted = true WHERE id = ?")
public class Alarm extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 알람을 받는 유저
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private AlarmDetails alarmDetails;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean isDeleted;

    @Builder
    public Alarm(User user, AlarmDetails alarmDetails, AlarmType alarmType) {
        this.user = user;
        this.alarmDetails = alarmDetails;
        this.alarmType = alarmType;
    }

    public static Alarm of(User user, AlarmType alarmType, AlarmDetails alarmDetails) {
        return Alarm.builder()
                .user(user)
                .alarmType(alarmType)
                .alarmDetails(alarmDetails)
                .build();
    }
}
