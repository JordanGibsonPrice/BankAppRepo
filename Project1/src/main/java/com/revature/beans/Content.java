package com.revature.beans;

public class Content {
	private byte[] content;
	private int appNum;
	
	public Content(byte[] content) {
		this.setContent(content);
	}

	public Content() {
		// TODO Auto-generated constructor stub
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] bs) {
		this.content = bs;
	}

	public int getAppNum() {
		return appNum;
	}

	public void setAppNum(int appNum) {
		this.appNum = appNum;
	}

}
