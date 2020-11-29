package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.common.Page;

public class ProductDAO { //DB에 직접 쿼리를 날려줌

	public ProductDAO() {
		System.out.println("DAO왔습니다");
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
	

	public Map<String , Object> getProductList(Search search) throws Exception {
		
		Map<String , Object>  map =	new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		// Original Query 구성
		String sql = "select * from product ";
		
		if (search.getSearchCondition() != null) {
			if ( search.getSearchCondition().equals("0") &&  !search.getSearchKeyword().equals("") ) {
				sql += " WHERE PROD_NO = '" + search.getSearchKeyword()+"'";
			} else if ( search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("")) {
				sql += " WHERE PROD_NAME ='" + search.getSearchKeyword()+"'";
			}
		}
		sql += " ORDER BY PROD_NO";
		
		System.out.println("::Original SQL :: " + sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql); //회원수 
		System.out.println("ProductDAO :: totalCount  :: " + totalCount);
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
	
		System.out.println(search);

		List<ProductVO> list = new ArrayList<ProductVO>();
		
		while(rs.next()){
			ProductVO productVO = new ProductVO();
			productVO.setProdNo(rs.getInt("PROD_NO"));
			productVO.setProdName(rs.getString("prod_name"));
			productVO.setProdDetail(rs.getString("prod_detail"));
			productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
			productVO.setPrice(rs.getInt("price"));
			productVO.setFileName(rs.getString("IMAGE_FILE"));
			productVO.setRegDate(rs.getDate("reg_date"));
			list.add(productVO);
		}
		
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);

		rs.close();
		pStmt.close();
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
		
		
		int confirm = stmt.executeUpdate();
		if (confirm == 1) {
			System.out.println("product update 완료!");
			System.out.println(productVO);
		} else {
			System.out.println("product update 실패!");
		}
		
		con.close();
	}
	
	private int getTotalCount(String sql) throws Exception {
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}

	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
				"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
								" 	FROM (	"+sql+" ) inner_table "+
								"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
				"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
	
	System.out.println("ProductDAO :: make SQL :: "+ sql);	
	
	return sql;
	
	}
}

