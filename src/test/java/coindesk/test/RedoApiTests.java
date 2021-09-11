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

import coindesk.Application;
import coindesk.mybatis.service.ICoinCurrencyService;

@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class RedoApiTests {
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
	 * 測試呼叫資料轉換的API，並顯示其內容。
	 */
	@Test
	public void test6() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/currencyAPI").accept(MediaType.APPLICATION_JSON))
				.andReturn();
		int status = result.getResponse().getStatus();
		log.debug("status:{}", status);
		String output = new String(result.getResponse().getContentAsByteArray());
		log.debug("output:{}", output);
		log.debug("output={}", JSON.toJSONString(JSON.parse(output), true));
	}
}