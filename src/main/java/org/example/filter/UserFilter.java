package org.example.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/user")
public class UserFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String idParam = httpServletRequest.getParameter("id");

        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                if (id < 0) {
                    httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный id");
                    return;
                }
            } catch (NumberFormatException e) {
                httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Поле id должно быть int");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
