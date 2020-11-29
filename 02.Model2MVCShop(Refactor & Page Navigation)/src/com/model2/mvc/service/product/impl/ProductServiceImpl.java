package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.vo.ProductVO;

public class ProductServiceImpl implements ProductService{

	
	private ProductDAO productDAO;
	
	public ProductServiceImpl() {
	productDAO = new ProductDAO();
	}
	
	//상품 등록
	public void addProduct(ProductVO productVO) throws Exception{
		productDAO.insertProduct(productVO);
	}
	//상품 정보 조회를 할때 
	@Override
	public ProductVO getProduct(int prodNo) throws Exception {

		return productDAO.findProduct(prodNo);
	}

	//상품 목록 조회 할때
	@Override
	public Map<String, Object> getProductList(Search search) throws Exception {
		// TODO Auto-generated method stub
		return (Map<String, Object>) productDAO.getProductList(search);
	}
	//상품 정보 수정할때
	@Override
	public void updateProduct(ProductVO productVO) throws Exception {
		productDAO.updateProduct(productVO);
		
	}

	
}
