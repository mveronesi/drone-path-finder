package org.example.prediction;

import java.util.List;

public class MockModel implements Model {
    private boolean isOnline;

    public MockModel(boolean isOnline){
        this.isOnline = isOnline;
    }
    public MockModel() {this.isOnline = true; }

    public void setOnline() {
        isOnline = true;
    }

    public void setOffline() {
        isOnline = false;
    }

    public int predict(List<Double> reflectance) throws IllegalArgumentException {
        if(reflectance.size() != 2000)
            throw new IllegalArgumentException("Malformed reflectance list");
        return isOnline ? (int) Math.random()*7 : -1;
    }
}
