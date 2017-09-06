package eric.zeng.instinet.test;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import eric.zeng.instinet.test.service.TradeMessageService;
import eric.zeng.instinet.test.trade.Trade;

@RestController
public class TestController {
  @Autowired
  private TradeMessageService messageService;

  @RequestMapping(value = "/getTradeStatistics", method = RequestMethod.GET)
  @ResponseBody
  public StatisticsResponse getTradeStatistics(@RequestParam String symbol) {
    StatisticsResponse response = new StatisticsResponse();
    Trade trade = messageService.getLargestTradesBySymbol(symbol);
    double avgPrice = messageService.getAvgTradePriceBySymbol(symbol);
    DecimalFormat df = new DecimalFormat("#.##");
    String dx = df.format(avgPrice);
    response.setAvgPrice(Double.valueOf(dx));
    response.setLargestTrade(trade);
    response.setSymbol(symbol);
    return response;
  }

}
