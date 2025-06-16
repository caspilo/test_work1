package org.example.pojo;

public class Current {
    private double temp_c;
    private CurrentCondition condition;

    public double getTempC() {
        return temp_c;
    }

    public void setTempC(double temp_c) {
        this.temp_c = temp_c;
    }

    public CurrentCondition getCondition() {
        return condition;
    }

    public void setCondition(CurrentCondition condition) {
        this.condition = condition;
    }
}
