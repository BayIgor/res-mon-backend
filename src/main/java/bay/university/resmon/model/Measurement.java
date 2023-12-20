package bay.university.resmon.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.io.Serializable;
import java.util.Date;

@Document("measurement")
public class Measurement implements Serializable {
    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;

    @Field(name = "meterId")
    private String meterId;

    @Field(name = "measDateTime")
    private Date measDateTime;

    @Field(name = "timerWorkHour")
    private String timerWorkHour;

    @Field(name = "consumption")
    private String consumption;

    @Field(name = "hour")
    private String hour;

    @Field(name = "heatConsumptionQ1")
    private String heatConsumptionQ1;

    @Field(name = "heatConsumptionQ2")
    private String heatConsumptionQ2;

    @Field(name = "coolantFlowV1")
    private String coolantFlowV1;

    @Field(name = "coolantFlowV2")
    private String coolantFlowV2;

    @Field(name = "instantConsumptionG1")
    private String instantConsumptionG1;

    @Field(name = "instantConsumptionG2")
    private String instantConsumptionG2;

    @Field(name = "coolantTemperatureT1")
    private String coolantTemperatureT1;

    @Field(name = "coolantTemperatureT2")
    private String coolantTemperatureT2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    public Date getMeasDateTime() {
        return measDateTime;
    }

    public void setMeasDateTime(Date measDateTime) {
        this.measDateTime = measDateTime;
    }

    public String getTimerWorkHour() {
        return timerWorkHour;
    }

    public void setTimerWorkHour(String timerWorkHour) {
        this.timerWorkHour = timerWorkHour;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getHeatConsumptionQ1() {
        return heatConsumptionQ1;
    }

    public void setHeatConsumptionQ1(String heatConsumptionQ1) {
        this.heatConsumptionQ1 = heatConsumptionQ1;
    }

    public String getHeatConsumptionQ2() {
        return heatConsumptionQ2;
    }

    public void setHeatConsumptionQ2(String heatConsumptionQ2) {
        this.heatConsumptionQ2 = heatConsumptionQ2;
    }

    public String getCoolantFlowV1() {
        return coolantFlowV1;
    }

    public void setCoolantFlowV1(String coolantFlowV1) {
        this.coolantFlowV1 = coolantFlowV1;
    }

    public String getCoolantFlowV2() {
        return coolantFlowV2;
    }

    public void setCoolantFlowV2(String coolantFlowV2) {
        this.coolantFlowV2 = coolantFlowV2;
    }

    public String getInstantConsumptionG1() {
        return instantConsumptionG1;
    }

    public void setInstantConsumptionG1(String instantConsumptionG1) {
        this.instantConsumptionG1 = instantConsumptionG1;
    }

    public String getInstantConsumptionG2() {
        return instantConsumptionG2;
    }

    public void setInstantConsumptionG2(String instantConsumptionG2) {
        this.instantConsumptionG2 = instantConsumptionG2;
    }

    public String getCoolantTemperatureT1() {
        return coolantTemperatureT1;
    }

    public void setCoolantTemperatureT1(String coolantTemperatureT1) {
        this.coolantTemperatureT1 = coolantTemperatureT1;
    }

    public String getCoolantTemperatureT2() {
        return coolantTemperatureT2;
    }

    public void setCoolantTemperatureT2(String coolantTemperatureT2) {
        this.coolantTemperatureT2 = coolantTemperatureT2;
    }
}
