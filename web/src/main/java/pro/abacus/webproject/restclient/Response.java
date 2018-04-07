package pro.abacus.webproject.restclient;

import java.util.ArrayList;

import org.springframework.util.Assert;

public class Response {

	private SuccessResponse success;
	private ContentsResponse contents;
	
	public Response() {
		
	}

	public Response(SuccessResponse success, ContentsResponse contents) {
		Assert.notNull(success, "Field success must not be null");
		Assert.notNull(contents, "Contens must not be null");
		this.contents = contents;
		this.success = success;
	}

	public SuccessResponse getSuccess() {
		return success;
	}

	public void setSuccess(SuccessResponse success) {
		this.success = success;
	}

	public ContentsResponse getContents() {
		return contents;
	}

	public void setContents(ContentsResponse contents) {
		this.contents = contents;
	}

	public boolean isResponseSuccessful() {
		return success.getTotal() > 0;	
	}
	
	public Quote getDefaultQuote(){
		return new Quote("Once you stop learning, you start dying", "Albert Einstein");
	}

	public Quote getQuote() {
		
		ArrayList<Quote> quotes = contents.getQuotes();
		if (isResponseSuccessful() && quotes.size() > 0) {
			return quotes.get(0);
		}
		return getDefaultQuote();
	}

}
