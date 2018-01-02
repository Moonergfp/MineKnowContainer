package com.oauth.code;

import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.issuer.UUIDValueGenerator;
import org.apache.oltu.oauth2.as.issuer.ValueGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wuhuachuan on 16/5/9.
 */
@Configuration
public class Config {

    @Bean
    public ValueGenerator createGenerator(){
        return new UUIDValueGenerator();
    }

    @Bean
    public OAuthIssuer createIssuer(ValueGenerator valueGenerator){
        return new OAuthIssuerImpl(valueGenerator);
    }
}
