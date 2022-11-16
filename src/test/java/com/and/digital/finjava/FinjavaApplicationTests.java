package com.and.digital.finjava;

import com.and.digital.finjava.model.StockWrapper;
import com.and.digital.finjava.service.StockService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FinjavaApplicationTests {

	@Autowired
	private StockService stockService;

	@Test
	void contextLoads() throws IOException {
		final StockWrapper stock = stockService.findStock("AAPL");
		System.out.println(stock.getStock());

		final BigDecimal price = stockService.findPrice(stock);
		System.out.println(price);

		final BigDecimal change = stockService.findLastChangeInPercent(stock);
		System.out.println(change);

		final BigDecimal changeFromAvg200 = stockService.findChangeFromAvg200InPercent(stock);
		System.out.println(changeFromAvg200);
	}

	@Test
	void multipleStocks() throws IOException, InterruptedException {
			final List<StockWrapper> stocks = stockService.findStocks(Arrays.asList("AAPL", "GOOG", "MSFT"));
			findPrices(stocks);

			Thread.sleep(61000);
			final StockWrapper amzn = stockService.findStock("AMZN");
			stocks.add(amzn);

			System.out.println(this.stockService.findPrice(amzn));
			findPrices(stocks);
	}

	private void findPrices(List<StockWrapper> stocks){
		stocks.forEach(stock -> {
				try {
					System.out.println(stockService.findPrice(stock));
				} catch (IOException e) {
					//
				}
			}
		);
	}

}
