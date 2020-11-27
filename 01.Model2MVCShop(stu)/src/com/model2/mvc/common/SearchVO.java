package com.model2.mvc.common;


public class SearchVO {
	
	private int page; //검색할수있는 페이지의 수 
	String searchCondition; //상품명 0 상품번호 1 
	String searchKeyword;// 검색할때 사용한 키워드
	int pageUnit;  //한페이지에 한번에 보이는 게시물을숫자를 나타냄
	
	public SearchVO(){
	}
	
	public int getPageUnit() {
		return pageUnit;
	}
	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}
	
	public int getPage() { 
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}

	public String getSearchCondition() { 
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	public String getSearchKeyword() { 
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
}