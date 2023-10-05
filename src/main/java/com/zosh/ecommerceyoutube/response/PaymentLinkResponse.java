package com.zosh.ecommerceyoutube.response;

public class PaymentLinkResponse {
 private String payment_url_id;
 private String payment_url_link;
 
 public PaymentLinkResponse() {
	// TODO Auto-generated constructor stub
}

public PaymentLinkResponse(String payment_url_id, String payment_url_link) {
	super();
	this.payment_url_id = payment_url_id;
	this.payment_url_link = payment_url_link;
}

public String getPayment_url_id() {
	return payment_url_id;
}

public void setPayment_url_id(String payment_url_id) {
	this.payment_url_id = payment_url_id;
}

public String getPayment_url_link() {
	return payment_url_link;
}

public void setPayment_url_link(String payment_url_link) {
	this.payment_url_link = payment_url_link;
}
 
}
