package com.example.medicore.utility;

import com.example.medicore.entity.DeviceReading;
import com.example.medicore.repository.DeviceReadingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledAlertJob {

    private final DeviceReadingRepository deviceReadingRepository;

    @Scheduled(cron = "0 */5 * * * *")
    public void scanRecentAnomalies(){
        List<DeviceReading> recentAnomalies = deviceReadingRepository.findByIsAnomalyTrueAndRecordedAtAfter(
                java.time.LocalDateTime.now().minusMinutes(5)
        );


        if (recentAnomalies.isEmpty()){
            log.debug("no anomalies found");
            return;
        }

        for (DeviceReading anomaly : recentAnomalies){
            log.warn("Anomaly detected - Device Id: {}, Type:{}, Value: {} {}", anomaly.getDevice().getId(), anomaly.getDevice().getDeviceType(),anomaly.getValue(),anomaly.getUnit());
        }


    }


}
