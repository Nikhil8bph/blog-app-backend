package com.devmistri.blog.message.response;


public class ResponseMessage {
	private String responseMessage;
	private boolean success;
	
	public ResponseMessage(String responseMessage, boolean success) {
		this.responseMessage = responseMessage;
		this.success = success;
	}
	
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	@Override
	public String toString() {
		return "ResponseMessage [responseMessage=" + responseMessage + ", success=" + success + "]";
	}
	
}
