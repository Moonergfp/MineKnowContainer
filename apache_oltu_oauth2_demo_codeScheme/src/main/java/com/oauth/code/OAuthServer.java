package com.oauth.code;

import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wuhuachuan on 16/5/9.
 */

@RestController
public class OAuthServer {

    @Autowired
    private OAuthIssuer oAuthIssuer;

    //token 过期时间
    private final String expires = "3600";

    /**
     * 生成 code,并且返回给 第三方应用 指定的 redirect_url
     * @param httpServletRequest
     * @param response
     * @throws OAuthProblemException
     * @throws OAuthSystemException
     * @throws IOException
     */
    @RequestMapping(value = "/oauthserver/get-code",method = RequestMethod.GET)
    public void getCode(HttpServletRequest httpServletRequest, HttpServletResponse response)
            throws OAuthProblemException, OAuthSystemException, IOException {

        try {
            //dynamically recognize an OAuth profile based on request characteristic (params,
            // method, content type etc.), perform validation
            OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(httpServletRequest);

            validateRedirectionURI(oauthRequest);

            //build OAuth response
            OAuthResponse resp = OAuthASResponse
                    .authorizationResponse(httpServletRequest,HttpServletResponse.SC_FOUND)
                    .setCode(oAuthIssuer.authorizationCode())
                    .location(oauthRequest.getRedirectURI())
                    .buildQueryMessage();

            response.sendRedirect(resp.getLocationUri());

        } catch (OAuthProblemException ex){
            final OAuthResponse resp = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_FOUND)
                    .error(ex)
                    .location(ex.getRedirectUri())
                    .buildQueryMessage();

            response.sendRedirect(resp.getLocationUri());
        }
    }

    private void validateRedirectionURI(OAuthAuthzRequest oauthRequest) {
    }


    /**
     * 生成 token,并且通过 HttpServletResponse 把 token 有关信息返回给 客户端
     * @param httpServletRequest
     * @param httpServletResponse
     * @throws OAuthSystemException
     * @throws IOException
     */
    @RequestMapping(value = "/oauthserver/get-token",method = RequestMethod.POST)
    public void getToken(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
            throws OAuthSystemException, IOException {

        try {
            OAuthTokenRequest oauthRequest = new OAuthTokenRequest(httpServletRequest);

            validateClient(oauthRequest);

            // generator token
            String accessToken = oAuthIssuer.accessToken();
            String refreshToken = oAuthIssuer.refreshToken();

            // some code
            OAuthResponse r = OAuthASResponse
                    .tokenResponse(HttpServletResponse.SC_OK)
                    .setAccessToken(accessToken)
                    .setExpiresIn(expires)
                    .setRefreshToken(refreshToken)
                    .buildBodyMessage();

            httpServletResponse.setStatus(r.getResponseStatus());
            PrintWriter pw = httpServletResponse.getWriter();
            pw.print(r.getBody());
            pw.flush();
            pw.close();

            //if something goes wrong
        } catch(OAuthProblemException ex) {

            OAuthResponse r = OAuthResponse
                    .errorResponse(401)
                    .error(ex)
                    .buildJSONMessage();

            httpServletResponse.setStatus(r.getResponseStatus());

            PrintWriter pw = httpServletResponse.getWriter();
            pw.print(r.getBody());
            pw.flush();
            pw.close();

            httpServletResponse.sendError(401);
        }
    }

    private void validateClient(OAuthTokenRequest oauthRequest) {
    }


}
