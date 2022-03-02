package com.saber.apigateway;

public enum FilterOrderType {
	PRE(-1),
	
	POST(0),
	
	ROUTE(1);
	
	private int order;
	
	FilterOrderType(int order) {
		this.order = order;
	}
	
	public int getOrder() {
		return order;
	}
}
