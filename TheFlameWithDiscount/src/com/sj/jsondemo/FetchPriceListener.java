package com.sj.jsondemo;

import java.util.List;

public interface FetchPriceListener {
    public void onFetchComplete_Price(List<Application> data);
    public void onFetchFailure_Price(String msg);
}
