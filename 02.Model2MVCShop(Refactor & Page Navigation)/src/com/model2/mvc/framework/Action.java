package com.model2.mvc.framework;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class Action {
	
	///Field
	private ServletContext servletContext;
	
	///Constructor
	public Action() {
		System.out.println("�׼ǿ� �����߽��ϴ�");
	}
	
	///Method
	public ServletContext getServletContext() {
		return servletContext;
	}
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public abstract String execute(HttpServletRequest request, HttpServletResponse response) throws Exception ;
}
