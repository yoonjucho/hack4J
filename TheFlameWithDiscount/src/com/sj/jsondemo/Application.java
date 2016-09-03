package com.sj.jsondemo;

public class Application {
    private String title;
    private String open;
    private long totalDl;
    private double rating;
    private String icon;
    private int pay;
    private String tel;
    ///menuinfo table start
    private String menu;
    private String price;
    private String type;
    private String id;
    private String menuid;
    private int menu_number;
    public int getMenu_number() {
		return menu_number;
	}

	public void setMenu_number(int menu_number) {
		this.menu_number = menu_number;
	}

	//overview
    private String style;
    private String recommend;
    private String price_range;
    private String operating_hours;
    private String phone;
    private String fratures;
    private String res_name;
    private String description;
    //review
    private String review;
    private int disagree;
    private String time;
	private int agree;
	private String reviewnumber;
	private int reviewrating;
	

	//cart
	private String account;
	private String status;
	private int uploading_id;
	private int itemcount;
	private int totalprice;
	private String resname;
	//uesr table
	private String usertel;
	private String position;
	private int point;
	private String grade;
	private int logincount;
	//
	private int food_count;
	private String anal_account;
	
	public String getAnal_account() {
		return anal_account;
	}

	public void setAnal_account(String anal_account) {
		this.anal_account = anal_account;
	}

	public int getFood_count() {
		return food_count;
	}

	public void setFood_count(int food_count) {
		this.food_count = food_count;
	}

	public String getUsertel() {
		return usertel;
	}

	public void setUsertel(String usertel) {
		this.usertel = usertel;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getLogincount() {
		return logincount;
	}

	public void setLogincount(int logincount) {
		this.logincount = logincount;
	}
	
	public String getResname() {
		return resname;
	}

	public void setResname(String resname) {
		this.resname = resname;
	}

	public int getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}

	public int getItemcount() {
		return itemcount;
	}

	public void setItemcount(int itemcount) {
		this.itemcount = itemcount;
	}

	public int getUploading_id() {
		return uploading_id;
	}

	public void setUploading_id(int uploading_id) {
		this.uploading_id = uploading_id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    public String getreviewnumber() {
		return reviewnumber;
	}

	public void setreviewnumber(String reviewnumber) {
		this.reviewnumber = reviewnumber;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

    public int getAgree() {
		return agree;
	}

	public void setAgree(int agree) {
		this.agree = agree;
	}

	public int getDisagree() {
		return disagree;
	}

	public void setDisagree(int disagree) {
		this.disagree = disagree;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getStyle() {
        return style;
    }
    
    public void setStyle(String style) {
        this.style = style;
    }
    
    public String getRecommend() {
        return recommend;
    }
    
    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }
    public String getPrice_range() {
        return price_range;
    }
    
    public void setPrice_range(String price_range) {
        this.price_range = price_range;
    }
    
    public String getOperating_hours() {
        return operating_hours;
    }
    
    public void setOperating_hours(String operating_hours) {
        this.operating_hours = operating_hours;
    }
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getFratures() {
        return fratures;
    }
    
    public void setFratures(String fratures) {
        this.fratures = fratures;
    }
    
    public String getres_name() {
        return res_name;
    }
    
    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setId(String id) {
    	this.id=id;
    }
    
    public String getId(){
    	return id;
    }
    
    public long getTotalDl() {
        return totalDl;
    }
    public void setTotalDl(long totalDl) {
        this.totalDl = totalDl;
    }
    public double getRating() {
        return rating;
    }
    
    public String getOpen() {
        return open;
    }
    
    public void setRating(double rating) {
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
    
    public String getMenu() {
        return menu;
    }
    
    public void setMenu(String menu) {
        this.menu = menu;
    }
    
    public String getPrice() {
        return price;
    }
    
    public void setPrice(String price) {
        this.price = price;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getMenuid() {
        return menuid;
    }
    
    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }   
	public int getReviewrating() {
		return reviewrating;
	}

	public void setReviewrating(int reviewrating) {
		this.reviewrating = reviewrating;
	}
    
}
