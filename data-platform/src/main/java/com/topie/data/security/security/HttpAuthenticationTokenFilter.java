package com.topie.data.security.security;

import com.topie.data.security.utils.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class HttpAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    @Value("${security.token.header}")
    private String tokenHeader;

    @Value("${security.token.front}")
    private String frontToken;

    private TokenUtils tokenUtils;

    private UserCache userCache;

    public void setTokenUtils(TokenUtils tokenUtils) {
        this.tokenUtils = tokenUtils;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authToken = httpRequest.getHeader(this.tokenHeader);
        if (StringUtils.isEmpty(authToken) && httpRequest.getCookies() != null) {
            for (Cookie cookie : httpRequest.getCookies()) {
                if (frontToken.equals(cookie.getName())) {
                    authToken = cookie.getValue();
                }
            }
        }
        String username = null;
        if (StringUtils.isNotEmpty(authToken)) {
            username = this.tokenUtils.getUsernameFromToken(authToken);
        }
        if (username != null && !this.tokenUtils.isTokenExpired(authToken)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userCache.getUserFromCache(username);
            if (userDetails != null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }

    public UserCache getUserCache() {
        return userCache;
    }

    public void setUserCache(UserCache userCache) {
        this.userCache = userCache;
    }

}
