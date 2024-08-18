package org.girishtechuniverse.service;

import java.util.Random;

import org.girishtechuniverse.bindings.Quote;
import org.girishtechuniverse.props.AppProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private AppProps appProps;

//	private String url = "https://type.fit/api/quotes";

	private Quote[] quotes = null;
	
	Random random = new Random();

	
	
	@Override
	public String getQuote() {

		String text ="";
		if (quotes == null) {


			RestTemplate rt = new RestTemplate();

			ResponseEntity<String> forEntity = rt.getForEntity(appProps.getMessages().get("quoteUrl"), String.class);

			String body = forEntity.getBody();

			ObjectMapper mapper = new ObjectMapper();

			try {
				quotes = mapper.readValue(body, Quote[].class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			int nextInt = random.nextInt(quotes.length - 1);

			text = quotes[nextInt].getText();
			}
		return text;
	}

}
