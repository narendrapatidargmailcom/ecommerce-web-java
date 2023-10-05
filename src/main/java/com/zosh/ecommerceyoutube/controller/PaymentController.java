package com.zosh.ecommerceyoutube.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.zosh.ecommerceyoutube.exception.OrderException;
import com.zosh.ecommerceyoutube.model.Order;
import com.zosh.ecommerceyoutube.repository.OrderRepository;
import com.zosh.ecommerceyoutube.response.ApiResponse;
import com.zosh.ecommerceyoutube.response.PaymentLinkResponse;
import com.zosh.ecommerceyoutube.service.OrderService;
import com.zosh.ecommerceyoutube.service.UserService;

@RestController
@RequestMapping("/api")
public class PaymentController {
 
	@Value("${razorpay.api.kay}")
	String apiKey;
	
	@Value("${razorpay.api.secret}")
	String apiSecret;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/payments/{orderId}")
	public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable long orderId,@RequestHeader("Authorization") String jwt) throws OrderException, RazorpayException{
		
		Order order = orderService.findOrderById(orderId);
		try {
			RazorpayClient razorpayClient =new RazorpayClient(apiKey, apiSecret);
			JSONObject paymentLinkJson = new JSONObject();
			paymentLinkJson.put("amount", order.getTotalPrice()*100);
			paymentLinkJson.put("currency","INR");
			
			JSONObject customerJson = new JSONObject();
			customerJson.put("name", order.getUser().getFirstName());
			customerJson.put("email", order.getUser().getEmail());
			paymentLinkJson.put("customerJson", customerJson);
			JSONObject notify = new JSONObject();
			notify.put("sms", true);
			notify.put("email", true);
			paymentLinkJson.put("notify", notify);

			paymentLinkJson.put("callback_url", "http://localhost:3000/payment/"+orderId);
			paymentLinkJson.put("callback_method", "get");
			
			PaymentLink paymentLink =razorpayClient.paymentLink.create(paymentLinkJson);
			
			String paymentLinkid = paymentLink.get("id");
			String paymentLinkUrl = paymentLink.get("short_url");

			PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();
			paymentLinkResponse.setPayment_url_id(paymentLinkid);
			paymentLinkResponse.setPayment_url_link(paymentLinkUrl);

			return new ResponseEntity<PaymentLinkResponse>(paymentLinkResponse,HttpStatus.CREATED);

		} catch (Exception e) {
          throw new RazorpayException(e.getMessage());
		}
		
	}
	
	@GetMapping("/payments")
	public ResponseEntity<ApiResponse> redirect(@RequestParam(name = "payment_id") String paymentId,@RequestParam(name = "order_id") Long orderId) throws OrderException, RazorpayException{
	 Order order = orderService.findOrderById(orderId);
	  RazorpayClient razorpayClient = new RazorpayClient(apiKey,apiSecret);
	  Payment payment = razorpayClient.payments.fetch(paymentId);
	  if(payment.get("status").equals("capture")) {
		  order.getPaymentDetail().setPaymentId(paymentId);
		  order.getPaymentDetail().setPaymentStatus("COMPLETED");
		  order.getPaymentDetail().setPaymentStatus("PLACED");
         orderRepository.save(order);
	  }
	  
	  ApiResponse apiResponse =new ApiResponse();
	  apiResponse.setMessage("your item got placed");
	  apiResponse.setStatus(true);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.CREATED);
		
	}
}
