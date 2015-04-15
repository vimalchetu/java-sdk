package com.velocity.utility;

import java.io.IOException;

import org.apache.axis.encoding.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.velocity.constants.VelocityConstants;
import com.velocity.model.request.VelocityRequest;
import com.velocity.model.response.VelocityResponse;

/**
 * Utility class to post request transaction to Velocity Server using apache
 * HttpClient either by any method GET, POST, PUT etc. and this will convert
 * Velocity Server response to VelocityResponse obj contains server response
 * status code, result etc.,
 * 
 * @author Vimal Kumar
 * @date 14-April-2015
 */
public class VelocityConnection {
    private static final Logger LOG = Logger.getLogger(VelocityConnection.class);

    /**
     * This method will post request data with session token to server
     * 
     * @param velocityRequest
     * @return
     */
    public static VelocityResponse connectPost(VelocityRequest velocityRequest) {
        String requestType = velocityRequest.getRequestType();
        String url = velocityRequest.getUrl();
        String authToken = velocityRequest.getAuthToken();
        String contentType = velocityRequest.getContentType();
        byte[] payload = velocityRequest.getPayload();
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try{
            httpclient = HttpClients.createDefault();
            HttpRequestBase httpRequest = null;
            if(requestType.equals(VelocityConstants.GET_METHOD)){
                httpRequest = new HttpGet(url);
            }else if(requestType.equals(VelocityConstants.POST_METHOD)){
                httpRequest = new HttpPost(url);
                if(payload != null){
                    ((HttpPost) httpRequest).setEntity(new ByteArrayEntity(payload));
                }
            }else if(requestType.equals(VelocityConstants.PUT_METHOD)){
                httpRequest = new HttpPut(url);
                if(payload != null){
                    ((HttpPut) httpRequest).setEntity(new ByteArrayEntity(payload));
                }
            }
            if(authToken != null){
                // Encrypting the Identity Token
                authToken = new String(Base64.encode((authToken + ":").getBytes()));
                httpRequest.addHeader("Authorization", "Basic " + authToken);
            }
            httpRequest.addHeader("Content-type", contentType);
            // Known issue: defining this causes server to reply with no content.
            httpRequest.addHeader("Accept", "");
            response = httpclient.execute(httpRequest);
            VelocityResponse velocityResponse = new VelocityResponse();
            velocityResponse.setStatusCode(response.getStatusLine().getStatusCode());
            // Get hold of the response entity
            HttpEntity responseEntity = response.getEntity();
            // If the response does not enclose an entity, there is no need
            // to worry about connection release
            if(responseEntity != null){
                velocityResponse.setMessage(response.getStatusLine().getReasonPhrase());
                velocityResponse.setResult(EntityUtils.toString(responseEntity));
                LOG.debug("VelocityResponse...>>>" + velocityResponse);
                return velocityResponse;
            }
        }catch (final ClientProtocolException e){
            LOG.error("Error occured to invoking Velocity API", e);
        }catch (final IOException e){
            LOG.error("Error occured to invoking Velocity API", e);
        }catch (final Exception e){
            LOG.error("Error occured to invoking Velocity API", e);
        }finally{
            try{
                httpclient.close();
                response.close();
            }catch (IOException ex){
                LOG.error("Error closing Http connection ", ex);
            }
        }
        return null;
    }
}
