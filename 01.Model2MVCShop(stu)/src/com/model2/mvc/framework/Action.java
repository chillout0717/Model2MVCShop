package com.model2.mvc.framework;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class Action {
	
	private ServletContext servletContext;
	
	public Action(){ //추상class 	
	}
	
	public ServletContext getServletContext() { // web app안에서 값을 얻어올떄 사용\]iu함
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) { // 
		this.servletContext = servletContext;
	}

	public abstract String execute(HttpServletRequest request, HttpServletResponse response) throws Exception ;
	//일을 시키기 위한 execute() 
}