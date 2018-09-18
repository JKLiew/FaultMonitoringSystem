package com.example.jkliew.jk_fms;

public class SensorData {
    public String value;
    public Long timestamp;

    public SensorData(){
    }

    public SensorData(String value, Long timestamp){
        this.timestamp = timestamp;
        this.value = value;
    }

}
