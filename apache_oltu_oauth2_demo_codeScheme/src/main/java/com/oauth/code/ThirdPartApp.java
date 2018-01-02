package com.oauth.code;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthAuthzResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wuhuachuan on 16/5/9.
 */

@RestController
public class ThirdPartApp {

    private final String oauthServer_getCode_url = "http://localhost:8080/oauthserver/get-code";
    private final String oauthServer_getToken_url = "http://localhost:8080/oauthserver/get-token";
    private final String redirect_url = "http://localhost:8080/thirdpart/token";
    private final String tokenfail_url = "http://localhost:8080/thirdpart/tokenfail";
    private final String client_id = "whc_client_id";
    private final String client_secret = "whc_client_secret";
    private final String response_type = "code";

    /**
     * 第一步调用该方法
     * 作用:请求 oauth 服务器的code,oauth服务器返回code之后,会调用 redirect_url.
     * 这里的 redirect_url = http://localhost:8080/thirdpart/token ,
     * 所以说 oauth 服务器会调用 下面的 token 方法.
     * @throws IOException
     * @throws OAuthSystemException
     */
    @RequestMapping(value = "/thirdpart/code",method = RequestMethod.GET)
    public void code() throws IOException, OAuthSystemException {

        OAuthClientRequest request = OAuthClientRequest
                .authorizationLocation(oauthServer_getCode_url)
                .setClientId(client_id)
                .setRedirectURI(redirect_url)
                .setResponseType(response_type)
                .buildQueryMessage();

        GetMethod getMethod = new GetMethod(request.getLocationUri());
        getMethod.setFollowRedirects(true);

        HttpClient httpClient = new HttpClient();
        httpClient.executeMethod(getMethod);
    }

    /**
     * 该方法会被 oauth 服务器调用,同时 oauth 服务器传回来 code.
     * 然后该方法紧接着会继续请求 oauth 服务器, 利用 code,拿到 token.
     * @param httpServletRequest
     * @param response
     * @throws OAuthProblemException
     * @throws OAuthSystemException
     * @throws IOException
     */
    @RequestMapping(value = "/thirdpart/token",method = RequestMethod.GET)
    public void token(HttpServletRequest httpServletRequest, HttpServletResponse response)
            throws OAuthProblemException, OAuthSystemException, IOException {

        OAuthAuthzResponse oar = OAuthAuthzResponse.oauthCodeAuthzResponse(httpServletRequest);
        String code = oar.getCode();

        OAuthClientRequest request = OAuthClientRequest
                .tokenLocation(oauthServer_getToken_url)
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setClientId(client_id)
                .setClientSecret(client_secret)
                .setRedirectURI(tokenfail_url)
                .setCode(code)
                .buildQueryMessage();

        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

        //Ps:这里之所以 有个 github 的东西,不需要管,因为从源码得知没有影响,
        //关键这里需要取得 token 等信息, GitHubTokenResponse 相当于帮我们写了
        //如果我们需要自定义,那么参考这个 GitHubTokenResponse 继承相关类即可
        GitHubTokenResponse oAuthResponse = oAuthClient.accessToken(request, GitHubTokenResponse.class);

        String accessToken = oAuthResponse.getAccessToken();
        Long expiresIn = oAuthResponse.getExpiresIn();

        //这里便拿到了 token, 可以做持久化.
        System.out.println("accessToken = " + accessToken);
        System.out.println("expiresIn = " + expiresIn);
    }

    /**
     * 这里正常情况下都不会调用,但是假如上述的 拿 token 发生未知错误,则会响应该方法.
     * 该方法的 url 也是由上一步 token 方法 传给了 oauth 服务器
     */
    @RequestMapping(value = "/thirdpart/tokenfail",method = RequestMethod.GET)
    public void tokenfail(){
        System.out.println("get token fail!");
    }
}
