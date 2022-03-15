package com.saber.apigateway;

public enum FilterOrderType {
	PRE(1),
	
	POST(2),
	
	ROUTE(3);
	
	private int order;
	
	FilterOrderType(int order) {
		this.order = order;
	}
	
	public int getOrder() {
		return order;
	}
}
