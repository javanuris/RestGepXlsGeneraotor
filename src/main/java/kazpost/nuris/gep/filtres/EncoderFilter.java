package kazpost.nuris.gep.filtres;


import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class EncoderFilter implements Filter{

private String code = "UTF-8";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String codeReq = httpServletRequest.getCharacterEncoding();
        if (code != null && !code.equalsIgnoreCase(codeReq)) {
            httpServletRequest.setCharacterEncoding(code);
            httpServletResponse.setCharacterEncoding(code);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
      }

    @Override
    public void destroy() {

    }
}
