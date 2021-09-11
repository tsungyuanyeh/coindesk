package coindesk.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import coindesk.Application;
import coindesk.mybatis.entity.CoinCurrency;
import coindesk.mybatis.service.ICoinCurrencyService;

@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class CrudTests {
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ICoinCurrencyService coinCurrencyService;

	@Autowired
	private WebApplicationContext webApplicationContext;
	MockMvc mvc; // 創建MockMvc類的物件

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	/**
	 * 測試呼叫查詢幣別對應表資料API，並顯示其內容。
	 */
	@Test
	public void test1() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/currency").accept(MediaType.APPLICATION_JSON))
				.andReturn();
		int status = result.getResponse().getStatus();
		log.debug("status:{}", status);
		String output = new String(result.getResponse().getContentAsByteArray());
		log.debug("output:{}", output);
		log.debug("output={}", JSON.toJSONString(JSON.parse(output), true));
	}

	/**
	 * 測試呼叫新增幣別對應表資料API。
	 */
	@Test
	public void test2() throws Exception {
		MvcResult result = mvc
				.perform(
						MockMvcRequestBuilders.post("/currency").accept(MediaType.APPLICATION_JSON).param("code", "XXX")
								.param("description", "測試幣別").param("symbol", "test_symbol").param("rate", "34.56"))
				.andReturn();
		int status = result.getResponse().getStatus();
		log.debug("status:{}", status);
		String output = new String(result.getResponse().getContentAsByteArray());
		log.debug("output:{}", output);
	}

	/**
	 * 測試呼叫更新幣別對應表資料API，並顯示其內容。
	 */
	@Test
	public void test3() throws Exception {
		// 取得測試資料以便測試
		CoinCurrency obj = coinCurrencyService.getOne(new QueryWrapper<CoinCurrency>().eq("code", "XXX"));
		if (obj == null) {
			log.warn("skip test3");
			return;
		}
		log.debug("ori_date=" + JSON.toJSONString(obj, true));

		MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/currency/" + obj.getId())
				.accept(MediaType.APPLICATION_JSON).param("code", "XXX").param("description", "測試幣別2")
				.param("symbol", "test_symbol2").param("rate", "65.43")).andReturn();
		int status = result.getResponse().getStatus();
		log.debug("status:{}", status);
		String output = new String(result.getResponse().getContentAsByteArray());
		log.debug("output:{}", output);

		result = mvc.perform(MockMvcRequestBuilders.get("/currency/" + obj.getId()).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		status = result.getResponse().getStatus();
		log.debug("status:{}", status);
		output = new String(result.getResponse().getContentAsByteArray());
		log.debug("output:{}", output);
		log.debug("output={}", JSON.toJSONString(JSON.parse(output), true));
	}

	/**
	 * 測試呼叫刪除幣別對應表資料API。
	 */
	@Test
	public void test4() throws Exception {
		// 取得測試資料以便測試
		CoinCurrency obj = coinCurrencyService.getOne(new QueryWrapper<CoinCurrency>().eq("code", "XXX"));
		if (obj == null) {
			log.warn("skip test3");
			return;
		}
		log.debug("ori_date=" + JSON.toJSONString(obj, true));

		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.delete("/currency/" + obj.getId()).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		int status = result.getResponse().getStatus();
		log.debug("status:{}", status);
		String output = new String(result.getResponse().getContentAsByteArray());
		log.debug("output:{}", output);
	}
}