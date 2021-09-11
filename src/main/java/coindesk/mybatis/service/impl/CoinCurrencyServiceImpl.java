package coindesk.mybatis.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import coindesk.mybatis.entity.CoinCurrency;
import coindesk.mybatis.mapper.CoinCurrencyMapper;
import coindesk.mybatis.service.ICoinCurrencyService;

@Service
public class CoinCurrencyServiceImpl extends ServiceImpl<CoinCurrencyMapper, CoinCurrency>
		implements ICoinCurrencyService {
}