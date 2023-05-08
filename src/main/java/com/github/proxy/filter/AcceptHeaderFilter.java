package com.github.proxy.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.proxy.model.dto.GithubProxyMessageDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AcceptHeaderFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);
        if (acceptHeader != null && acceptHeader.equals(MediaType.APPLICATION_XML_VALUE)) {
            String errorMessage = "Invalid Accept header, 'application/xml' is not supported.";
            response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(GithubProxyMessageDto.from(HttpStatus.NOT_ACCEPTABLE.value(), errorMessage)));
            return;
        }
        filterChain.doFilter(request, response);
    }

}