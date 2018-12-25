package com.yash.oauth_client.controller;

import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;

@RestController
@RequestMapping("/oauth")
public class OauthController {

    @Value("${security.oauth2.token}")
    private String oauthTokenUrl;

    private static String sanitizeReqParam(final String reqParam) {
        if(StringUtils.isEmpty(reqParam)) {
            throw new IllegalArgumentException("Missing Request Parameters");
        }
        return HtmlUtils.htmlEscape(reqParam);
    }

    private static HttpEntity getHttpEntity(final String clientId, String clientSecret, String grantType, String username,
                                            String password) {
        MultiValueMap<String, String> headers = getHttpHeaders(grantType, clientId, clientSecret);
        MultiValueMap<String, String> body = getHttpBody(clientId, clientSecret, grantType, username, password);
        return new HttpEntity<MultiValueMap<String, String>>(body, headers);
    }

    private static MultiValueMap<String,String> getHttpBody(String clientId, String clientSecret, String grantType,
                                                            String username, String password) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        if("client_credentials".equalsIgnoreCase(grantType)) {
            body.add(OAuth2Utils.CLIENT_ID, clientId);
            body.add("client_secret", clientSecret);
        } else {
            body.add("username", username);
            body.add("password", password);
        }
        body.add(OAuth2Utils.GRANT_TYPE, grantType);
        return body;
    }

    private static MultiValueMap<String,String> getHttpHeaders(String grantType, String clientId, String clientSecret) {
        MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
        byte[] encodedBytes = Base64.encodeBase64((clientId+":"+clientSecret).getBytes());
        header.add(HttpHeaders.AUTHORIZATION, "Basic "+ new String(encodedBytes));
        header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED.toString());

        if("client_credentials".equalsIgnoreCase(grantType)) {
            header.add(HttpHeaders.PRAGMA, "no-cache");
            header.add(HttpHeaders.CONNECTION, "keep-alive");
            header.add(HttpHeaders.CACHE_CONTROL, "no-cache");
            header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED.toString());
        }
        return header;
    }

    @ApiOperation(value = "GET Authorization token", nickname = "Get Authorization token using grant types")
    @RequestMapping(value = {"/token"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<OAuth2AccessToken> getOauthTokenUsingPassword(
            @RequestHeader(value = OAuth2Utils.CLIENT_ID) final String clientId,
            @RequestHeader(value = "client_secret") final String clientSecret,
            @RequestHeader(value = OAuth2Utils.GRANT_TYPE) final String grantType,
            @RequestHeader(value = "username", required = false) final String username,
            @RequestHeader(value = "password", required = false) final String password) {
        try{
            HttpEntity entity = getHttpEntity(sanitizeReqParam(clientId), sanitizeReqParam(clientSecret),
                    grantType, null!=username ? sanitizeReqParam(username): null,
                    null!=password ? sanitizeReqParam(password): null);
            return new RestTemplate().postForEntity(oauthTokenUrl, entity, OAuth2AccessToken.class);
        } catch (Exception e){
            System.out.println("Error occured " + e.getMessage());
        }
        throw new UnauthorizedClientException(HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }
}
