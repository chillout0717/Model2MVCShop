package com.model2.mvc.framework;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class Action {
	
	private ServletContext servletContext;
	
	public Action(){ //�߻�class 	
	}
	
	public ServletContext getServletContext() { // web app�ȿ��� ���� ���Ë� ���\]iu��
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) { // 
		this.servletContext = servletContext;
	}

	public abstract String execute(HttpServletRequest request, HttpServletResponse response) throws Exception ;
	//���� ��Ű�� ���� execute() 
}