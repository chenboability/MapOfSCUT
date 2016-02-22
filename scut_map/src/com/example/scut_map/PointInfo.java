package com.example.scut_map;

import javax.swing.ImageIcon;

public class PointInfo {
	public int type;
	public String message ;
	public String detail ;
	public int pointX  , pointY ;
	public int fpointX , fpointY ;
	public int mark;
	public ImageIcon icon = new ImageIcon(getClass().getResource("/images/red.png"));
	public int flag = 0;

	public PointInfo() {
	}
	public PointInfo(PointInfo newOne) {
		this.pointX = newOne.getPointX();
		this.pointY = newOne.getPointY(); 
		this.type = newOne.getType();
		this.mark = newOne.getMark();
	}
	public PointInfo(int x,int y,String message,String detail,int mark,int type) {
		pointX = fpointX = x;
		pointY = fpointY = y;
		this.message = message;
		this.detail = detail;
		this.type = type;
		this.mark = mark;
	}

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getPointX() {
		return pointX;
	}

	public int getFpointX() {
		return fpointX;
	}

	public void setFpointX(int fpointX) {
		this.fpointX = fpointX;
	}

	public void setPointX(int pointX) {
		this.pointX = pointX;
	}

	public int getFpointY() {
		return fpointY;
	}

	public void setFpointY(int fpointY) {
		this.fpointY = fpointY;
	}

	public int getPointY() {
		return pointY;
	}

	public void setPointY(int pointY) {
		this.pointY = pointY;
	}
	public int getMark() {
		return mark;
	}
	public void setMark(int mark) {
		this.mark = mark;
	}
	

}
