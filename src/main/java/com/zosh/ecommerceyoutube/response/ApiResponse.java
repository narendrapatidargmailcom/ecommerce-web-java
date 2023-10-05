package com.zosh.ecommerceyoutube.response;

public class ApiResponse {
	private String message;
	private boolean Status;

	public ApiResponse() {
		// TODO Auto-generated constructor stub
	}

	public ApiResponse(String message, boolean status) {
		super();
		this.message = message;
		Status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isStatus() {
		return Status;
	}

	public void setStatus(boolean status) {
		Status = status;
	}

}
