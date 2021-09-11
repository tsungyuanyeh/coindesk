package coindesk.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "CoindiskFeignService", url = "${apiUrl}")
public interface CoindiskFeignService {
	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	String execute();

	// XXX 暫時跳過轉換物件
//	@RequestMapping(method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	Map execute();
}
