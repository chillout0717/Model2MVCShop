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
	
	//��ǰ ���
	public void addProduct(ProductVO productVO) throws Exception{
		productDAO.insertProduct(productVO);
	}
	//��ǰ ���� ��ȸ�� �Ҷ� 
	@Override
	public ProductVO getProduct(int prodNo) throws Exception {

		return productDAO.findProduct(prodNo);
	}

	//��ǰ ��� ��ȸ �Ҷ�
	@Override
	public Map<String, Object> getProductList(Search search) throws Exception {
		// TODO Auto-generated method stub
		return (Map<String, Object>) productDAO.getProductList(search);
	}
	//��ǰ ���� �����Ҷ�
	@Override
	public void updateProduct(ProductVO productVO) throws Exception {
		productDAO.updateProduct(productVO);
		
	}

	
}
