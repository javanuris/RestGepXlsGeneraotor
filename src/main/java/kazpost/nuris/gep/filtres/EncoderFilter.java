package kazpost.nuris.gep.filtres;


import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
@Order(1)
public class EncoderFilter implements Filter{

private String code = "UTF-16";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Init!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println(servletRequest.getCharacterEncoding() + " : Before Character Encodings");
        System.out.println(servletRequest.getContentLength() + " : Content Length");

        String codeReq = servletRequest.getCharacterEncoding();
        if (code != null && !code.equalsIgnoreCase(codeReq)) {
            servletRequest.setCharacterEncoding(code);
            servletResponse.setCharacterEncoding(code);
        }
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println(servletRequest.getAttributeNames().hasMoreElements());
        System.out.println(servletRequest.getCharacterEncoding() + " : After Character Encodings");
    }

    @Override
    public void destroy() {
        System.out.println("Destroy!");
    }
}
