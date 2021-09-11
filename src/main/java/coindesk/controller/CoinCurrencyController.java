package coindesk.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import coindesk.fegin.CoindiskFeignService;
import coindesk.mybatis.entity.CoinCurrency;
import coindesk.mybatis.service.ICoinCurrencyService;

@RestController
public class CoinCurrencyController {
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ICoinCurrencyService coinCurrencyService;
	@Autowired
	CoindiskFeignService coindiskFeignService;

	@RequestMapping("currencyList")
	public ModelAndView currencyList() {
		return new ModelAndView("currencyList");
	}

	@GetMapping("currency")
	public List all() {
		List<CoinCurrency> l = coinCurrencyService.list();
		return l;
	}

	@GetMapping("currency/{id}")
	public CoinCurrency byId(@PathVariable Long id) {
		return coinCurrencyService.getById(id);
	}

	@PostMapping("currency")
	public boolean add(@RequestParam Map map) {
		log.debug("map={}", JSON.toJSONString(map));
		CoinCurrency c = new CoinCurrency();
		c.setCode(MapUtils.getString(map, "code"));
		c.setSymbol(MapUtils.getString(map, "symbol"));
		c.setDescription(MapUtils.getString(map, "description"));
		c.setRate(MapUtils.getDouble(map, "rate"));
		boolean isSuccess = coinCurrencyService.save(c);
		return isSuccess;
	}

	@PutMapping("currency/{id}")
	public boolean update(@PathVariable Long id, @RequestParam Map map) {
		log.debug("map={}", JSON.toJSONString(map));
		CoinCurrency c = new CoinCurrency();
		c.setId(id);
		c.setCode(MapUtils.getString(map, "code"));
		c.setSymbol(MapUtils.getString(map, "symbol"));
		c.setDescription(MapUtils.getString(map, "description"));
		c.setRate(MapUtils.getDouble(map, "rate"));
		boolean isSuccess = coinCurrencyService.updateById(c);
		return isSuccess;
	}

	@DeleteMapping("currency/{id}")
	public boolean delete(@PathVariable Long id) {
		boolean isSuccess = coinCurrencyService.removeById(id);
		return isSuccess;
	}

	@RequestMapping("currencyInit")
	public boolean currencyInit() {
		String str = coindiskFeignService.execute();
		JSONObject obj = JSON.parseObject(str);

		JSONObject bpi = obj.getJSONObject("bpi");
		List<CoinCurrency> currencyData = bpi.values().stream().map(row -> {
			Map map = (Map) row;
			CoinCurrency c = new CoinCurrency();
			c.setCode(MapUtils.getString(map, "code"));
			c.setSymbol(MapUtils.getString(map, "symbol"));
			c.setDescription(MapUtils.getString(map, "description"));
			c.setRate(MapUtils.getDouble(map, "rate_float"));
			return c;
		}).collect(Collectors.toList());
		boolean isSuccess = coinCurrencyService.saveBatch(currencyData);
		return isSuccess;
	}
}