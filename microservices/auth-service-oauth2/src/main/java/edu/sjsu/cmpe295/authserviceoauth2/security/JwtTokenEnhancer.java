package edu.sjsu.cmpe295.authserviceoauth2.security;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        AuthUser user = (AuthUser) authentication.getPrincipal();
        Map<String, Object> userInfo = new HashMap<>();

        userInfo.put("email", user.getUsername());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(userInfo);
        return accessToken;
    }
}
