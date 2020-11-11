package com.smart.covid.domain;

import java.util.List;

public class NewsG {
	
	public int getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}



	public List<NewsApiItem> getArticles() {
		return articles;
	}

	public void setArticles(List<NewsApiItem> articles) {
		this.articles = articles;
	}



	private int totalResults;

    private List<NewsApiItem> articles;
    
    private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "NewsG [totalResults=" + totalResults + ", articles=" + articles + ", status=" + status + "]";
	}
	
	

}
