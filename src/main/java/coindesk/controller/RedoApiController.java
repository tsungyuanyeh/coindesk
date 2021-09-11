package coindesk.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;

import coindesk.fegin.CoindiskFeignService;

@RestController
public class RedoApiController {
	@Autowired
	CoindiskFeignService coindiskFeignService;

	@GetMapping("currencyAPI")
	public Object execute() {
		String str = coindiskFeignService.execute();
		JSONObject obj = JSON.parseObject(str);
		Box box = new Box();

		JSONObject time = obj.getJSONObject("time");
		Date updatedISO = time.getDate("updatedISO");
		box.setUpdateDate(updatedISO);

		JSONObject bpi = obj.getJSONObject("bpi");
		List<Currency> currencyData = bpi.values().stream().map(row -> {
			Map map = (Map) row;
			Currency c = new Currency();
			c.setCode(MapUtils.getString(map, "code"));
			c.setDesc(MapUtils.getString(map, "description"));
			c.setRate(MapUtils.getString(map, "rate"));
			return c;
		}).collect(Collectors.toList());
		box.setCurrencyData(currencyData);

		return box;
	}

	class Box {
		@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
		Date updateDate;
		List<Currency> currencyData;

		public Date getUpdateDate() {
			return updateDate;
		}

		public void setUpdateDate(Date updateDate) {
			this.updateDate = updateDate;
		}

		public List<Currency> getCurrencyData() {
			return currencyData;
		}

		public void setCurrencyData(List<Currency> currencyData) {
			this.currencyData = currencyData;
		}
	}

	class Currency {
		String code;
		String desc;
		String rate;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public String getRate() {
			return rate;
		}

		public void setRate(String rate) {
			this.rate = rate;
		}
	}
}