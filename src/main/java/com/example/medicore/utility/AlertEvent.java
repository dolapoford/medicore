package com.example.medicore.utility;

import com.example.medicore.entity.DeviceReading;
import org.springframework.context.ApplicationEvent;

public class AlertEvent extends ApplicationEvent {

    private DeviceReading deviceReading;

    public AlertEvent(Object source, DeviceReading deviceReading) {
        super(source);
        this.deviceReading=deviceReading;
    }

    public DeviceReading getDeviceReading(){
        return deviceReading;
    }
}
