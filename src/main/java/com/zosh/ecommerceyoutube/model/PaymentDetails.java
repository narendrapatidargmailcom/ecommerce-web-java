package com.zosh.ecommerceyoutube.model;

public class PaymentDetails {
	
	private String paymentMethod;
	private String paymentStatus;
	private String paymentId;
	private String razorpayPaymentlinkId;
	private String razorpayPaymentlinkReferenceId;
	private String razorpayPaymentlinkStatus;
	private String razorpayPaymentId;

	public PaymentDetails() {
		// TODO Auto-generated constructor stub
	}

	public PaymentDetails(String paymentMethod, String paymentStatus, String paymentId, String razorpayPaymentlinkId,
			String razorpayPaymentlinkReferenceId, String razorpayPaymentlinkStatus, String razorpayPaymentId) {
		super();
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.paymentId = paymentId;
		this.razorpayPaymentlinkId = razorpayPaymentlinkId;
		this.razorpayPaymentlinkReferenceId = razorpayPaymentlinkReferenceId;
		this.razorpayPaymentlinkStatus = razorpayPaymentlinkStatus;
		this.razorpayPaymentId = razorpayPaymentId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getRazorpayPaymentlinkId() {
		return razorpayPaymentlinkId;
	}

	public void setRazorpayPaymentlinkId(String razorpayPaymentlinkId) {
		this.razorpayPaymentlinkId = razorpayPaymentlinkId;
	}

	public String getRazorpayPaymentlinkReferenceId() {
		return razorpayPaymentlinkReferenceId;
	}

	public void setRazorpayPaymentlinkReferenceId(String razorpayPaymentlinkReferenceId) {
		this.razorpayPaymentlinkReferenceId = razorpayPaymentlinkReferenceId;
	}

	public String getRazorpayPaymentlinkStatus() {
		return razorpayPaymentlinkStatus;
	}

	public void setRazorpayPaymentlinkStatus(String razorpayPaymentlinkStatus) {
		this.razorpayPaymentlinkStatus = razorpayPaymentlinkStatus;
	}

	public String getRazorpayPaymentId() {
		return razorpayPaymentId;
	}

	public void setRazorpayPaymentId(String razorpayPaymentId) {
		this.razorpayPaymentId = razorpayPaymentId;
	}

}
