package coindesk.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;

import coindesk.Application;
import coindesk.fegin.CoindiskFeignService;
import coindesk.mybatis.service.ICoinCurrencyService;

@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class ApiTests {
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ICoinCurrencyService coinCurrencyService;
	@Autowired
	CoindiskFeignService coindiskFeignService;

	@Autowired
	private WebApplicationContext webApplicationContext;
	MockMvc mvc; // 創建MockMvc類的物件

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	/**
	 * 測試呼叫coindeskAPI成功，並顯示其內容。
	 */
	@Test
	public void test5() throws Exception {
		String str = coindiskFeignService.execute();
		log.debug("output:{}", str);
		log.debug("output={}", JSON.toJSONString(JSON.parse(str), true));
	}
}