package com.velocity.model.request;

/**
 * This class contains the request data to post to the Velocity server for Velocity transaction
 * method
 * 
 * @author Vimal Kumar
 * @date 13-04-2015
 */
public class VelocityRequest {

    private String requestType;
    private String url;
    private String authToken;
    private String contentType;
    private byte[] payload;

    public String getRequestType() {
        return requestType;
    }
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getAuthToken() {
        return authToken;
    }
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public byte[] getPayload() {
        return payload;
    }
    public void setPayload(byte[] payload) {
        this.payload = payload;
    }
}
