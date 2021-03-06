package me.imniu.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 截取出请求参数，实现伪静态 
 * 请求路径：/tags/tomcat 
 * 处理成为：/tags?name=tomcat
 */
@WebFilter("/tags/*")
public class TagsFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(TagsFilter.class); 
    public TagsFilter() {
    }
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String url = req.getServletPath();
		if(!url.equals("/tags")){
			url = "/tags?name=" + url.replace("/tags/", "");
		}
		logger.info("Filter："+url);
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
