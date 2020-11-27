package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;


public class ProductDAO { //DB에 직접 쿼리를 날려줌

	public ProductDAO() {
	}

	public void insertProduct(ProductVO productVO) throws Exception {
		
		
		Connection con = DBUtil.getConnection();
		
		
		String sql = "insert into product values "
				+ "(seq_product_prod_no.nextval, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDD')) ";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		
	
		stmt.executeUpdate();
		
		
		con.close();	
	}
	

	public ProductVO findProduct(int prodNo) throws Exception {
		
	
		Connection con = DBUtil.getConnection();
		
	
		String sql = "select * from product where prod_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);
		

		ResultSet rs = stmt.executeQuery();
		
		ProductVO productVO = null;
		while (rs.next()) {
			productVO = new ProductVO(); 
			productVO.setProdNo(rs.getInt("prod_no"));
			productVO.setProdName(rs.getString("prod_name"));
			productVO.setProdDetail(rs.getString("prod_detail"));
			productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
			productVO.setPrice(rs.getInt("price"));
			productVO.setFileName(rs.getString("IMAGE_FILE"));
			productVO.setRegDate(rs.getDate("reg_date"));
		}
		
		con.close();

		return productVO;
	}
	

	public HashMap <String, Object> getProductList(SearchVO searchVO) throws Exception {
		
	
		Connection con = DBUtil.getConnection();

		String sql = "select * from product ";
		
		PreparedStatement stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		ResultSet rs = stmt.executeQuery();
		
		rs.last();
		int total = rs.getRow();
		System.out.println("로우의 수:" + total);
		
		HashMap<String,Object> map = new HashMap<String,Object>(); 
		map.put("count", new Integer(total)); 
		
		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());
		
		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				ProductVO vo = new ProductVO();
				vo.setProdNo(rs.getInt("prod_no"));
				vo.setProdName(rs.getString("prod_name"));
				vo.setProdDetail(rs.getString("prod_detail"));
				vo.setManuDate(rs.getString("MANUFACTURE_DAY"));
				vo.setPrice(rs.getInt("price"));
				vo.setFileName(rs.getString("IMAGE_FILE"));
				vo.setRegDate(rs.getDate("reg_date"));
				
				list.add(vo);
				if (!rs.next()) { 
					break; 
				}
			}
		}
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());

		con.close();
			
		return map;
	}
	

	public void updateProduct(ProductVO productVO) throws Exception {
	
		Connection con = DBUtil.getConnection();
		
		String sql = "update product set prod_name = ?, prod_detail = ?, manufacture_day = ?, price = ?, image_file = ? "
					+ "where prod_no = ?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.setInt(6, productVO.getProdNo());
		
		
	stmt.executeUpdate();
		
		
		con.close();
	}
	
}

