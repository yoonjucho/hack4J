package com.jousterlab.theflamewithdis;


public class GetApplication {
    private String title;
    private String open;
    private long totalDl;
    private int rating;
    private String icon;
    private int pay;
    private String tel;
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public long getTotalDl() {
        return totalDl;
    }
    public void setTotalDl(long totalDl) {
        this.totalDl = totalDl;
    }
    public int getRating() {
        return rating;
    }
    
    public String getOpen() {
        return open;
    }
    
    public void setRating(int rating) {
        this.rating = rating;
    }
    public String getIcon() {
        return icon;
    }
    public void setPay(int pay){
    	this.pay=pay;
    }
    
    public int getPay() {
        return pay;
    }
    
    public String getTel() {
        return tel;
    }
    
    public void setTel(String tel) {
        this.tel = tel;
    }
    
    //여기까지 함.
    public void  setOpen(String open){
    	this.open=open;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
}

