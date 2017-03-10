package wtf.cruft.api;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest hsr = (HttpServletRequest) servletRequest;
        if (hsr.getRequestURI().equals("/api/login")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // Disregard this implementation, it's just to see if things work.
        String header = hsr.getHeader("Authorization");
        if (header != null) {
            String[] split = header.split(" ");
            String token = split[1];
            System.out.println(token);
            try {
                Jwts.parser()
                        .setSigningKey("secret")
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject().equals("user");
            } catch (SignatureException ex) {
                System.out.println("this is wrong!!!");
                throw ex;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
