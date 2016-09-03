package com.sj.jsondemo;

import java.util.List;

public interface FetchMenuListener {
    public void onFetchComplete2(List<Application> data);
    public void onFetchFailure2(String msg);
}
