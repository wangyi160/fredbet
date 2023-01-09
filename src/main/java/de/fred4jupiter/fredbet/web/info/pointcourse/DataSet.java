package de.fred4jupiter.fredbet.web.info.pointcourse;

import java.util.ArrayList;
import java.util.List;

public class DataSet {

    private final String label;

    private final List<Double> data = new ArrayList<>();

    public DataSet(String label) {
        this.label = label;
    }

    public void addData(Double value) {
        this.data.add(value);
    }

    public String getLabel() {
        return label;
    }

    public List<Double> getData() {
        return data;
    }
}
