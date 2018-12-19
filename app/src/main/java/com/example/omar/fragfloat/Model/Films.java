package com.example.omar.fragfloat.Model;

import java.util.List;

/**
 * Created by Omar on 12/18/2018.
 */

public class Films {

    private String status;
    private int count;
    private List<ResultsBean> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

}
