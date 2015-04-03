package com.fusion.ea.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fusion.ea.entity.File;
import com.fusion.ea.entity.User;

public class EAInterceptor implements HandlerInterceptor {

	// before the actual handler will be executed
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (SecurityContextHolder.getContext().getAuthentication().getDetails() instanceof User
				&& !request.getRequestURI().contains("file/select")
				&& !request.getRequestURI().contains("file/select")) {
			File sFile = (File) request.getSession().getAttribute(
					"selectedFile");
			if (sFile == null) {
				response.sendRedirect(request.getContextPath()+"/master/file/select");
				return false;
			}
		}
		return true;
	}

	// after the handler is executed
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// System.out.println("afterCompletion...");
	}

}
