package com.model2.mvc.view.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class ListProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Search search = new Search();
	
		
		int currentPage = 1;
		if (request.getParameter("currentPage") != null)
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		
		String menu = request.getParameter("menu");
		System.out.println("이건 액션에서의 mene값입니다. "+menu);
		
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		
		ProductService service = new ProductServiceImpl();
		Map<String, Object> map = service.getProductList(search);
		
		System.out.println("이건 맵값입니다 "+map);
		Page resultPage	= 
		new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListUserAction ::"+resultPage);
		
	
		request.setAttribute("menu", menu);
		request.setAttribute("list", map.get("list"));
		request.setAttribute("search", search);
		//request.setAttribute("page", page);
		request.setAttribute("resultPage", resultPage);
	
		System.out.println("이건 리스트값이야"+request.getAttribute("list"));
		
		return "forward:/product/listProduct.jsp"; 
	}

}