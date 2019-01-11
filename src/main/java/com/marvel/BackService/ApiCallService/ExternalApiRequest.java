package com.marvel.BackService.ApiCallService;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class ExternalApiRequest {

    private static final Logger log = LoggerFactory.getLogger(ExternalApiRequest.class);
    private static final String publicKey = "9f88def893427a95b30cda3fd8ed931e";
    private static final String privateKey = "cbd21d8b412e761fb59338fa6e914f408af39d39";

    private String byteToStringConversion(byte[] hash){
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    private String generateHash(String timestamp) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] bytesOfMessage = (timestamp + privateKey + publicKey).getBytes("UTF-8");
        byte[] procesedMessage = MessageDigest.getInstance("MD5").digest(bytesOfMessage);
        return this.byteToStringConversion(procesedMessage);
    }

     private String GenerateAuthParams() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String timestamp = String.valueOf(new Date().getTime());
        return  "?apikey=" + publicKey + "&hash=" + generateHash(timestamp) + "&ts=" + timestamp;
    }

    public  <O> O GetRequest(String url, Class<O> type ) throws RestClientException, UnsupportedEncodingException, NoSuchAlgorithmException {
        url += this.GenerateAuthParams();
        log.info("-----Requesting To----- : " + url);
        return (new RestTemplate()).getForObject(url, type);
    }

    public  <O> O GetRequest(String url, Class<O> type, String name ) throws RestClientException, UnsupportedEncodingException, NoSuchAlgorithmException {
        url += this.GenerateAuthParams() + "&name=" + name;
        log.info("-----Requesting To----- : " + url);
        return (new RestTemplate()).getForObject(url, type);
    }
}
