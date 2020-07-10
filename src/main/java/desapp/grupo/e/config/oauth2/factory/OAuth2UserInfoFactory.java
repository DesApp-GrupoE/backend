package desapp.grupo.e.config.oauth2.factory;

import desapp.grupo.e.config.oauth2.model.GoogleOAuth2UserInfo;
import desapp.grupo.e.config.oauth2.model.OAuth2UserInfo;
import desapp.grupo.e.model.user.AuthProvider;
import desapp.grupo.e.service.exceptions.OAuth2AuthenticationProcessingException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(AuthProvider.google.name().equals(registrationId)) {
            return new GoogleOAuth2UserInfo(attributes);
//        } else if (registrationId.equalsIgnoreCase(AuthProvider.facebook.toString())) {
//            return new FacebookOAuth2UserInfo(attributes);
//        } else if (registrationId.equalsIgnoreCase(AuthProvider.github.toString())) {
//            return new GithubOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}