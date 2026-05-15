package com.example.medicore.utility;

import com.example.medicore.entity.DeviceReading;
import com.example.medicore.entity.IoTDevice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AlertEventListener {


    @Async
    @EventListener
    public void onAlertEvent(AlertEvent event){
        DeviceReading deviceReading = event.getDeviceReading();
        IoTDevice device = deviceReading.getDevice();

        log.warn("🚨 ALERT — PatientLinked: {}, Device: {} ({}), Reading: {} {}, Time: {}",
                             device.getPatient() != null,
                 device.getName(),
                 device.getDeviceType(),
                 deviceReading.getValue(),
                 deviceReading.getUnit(),
                 deviceReading.getRecordedAt()
         );
                device.getName(),
                device.getDeviceType(),
                deviceReading.getValue(),
                deviceReading.getUnit(),
                deviceReading.getRecordedAt()
        );

        // Future hooks — uncomment as you build them out
        // emailService.sendAlert(deviceReading);
        // kafkaProducer.publishAlert(deviceReading);
        // notificationService.push(deviceReading);

    }

}
