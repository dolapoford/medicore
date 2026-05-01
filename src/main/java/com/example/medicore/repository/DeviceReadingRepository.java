package com.example.medicore.repository;

import com.example.medicore.entity.DeviceReading;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DeviceReadingRepository extends JpaRepository<DeviceReading,Long> {

    Page<DeviceReading> findByDeviceId(Long deviceId, Pageable pageable);

    List<DeviceReading> findByIsAnomalyTrueAndRecordedAtAfter(LocalDateTime since);

    @Query("SELECT r FROM DeviceReading r WHERE r.device.id = :deviceId AND r.recordedAt BETWEEN :from AND :to ORDER BY r.recordedAt DESC")
    List<DeviceReading> findReadingsInRange(
            @Param("deviceId") Long deviceId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

}
