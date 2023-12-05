package BlockChain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;


@Configuration
public class EthereumBean {

	@Bean
	public ContractGasProvider ContractBean() {
		return new ContractGasProvider() {
			
			@Override
			public java.math.BigInteger getGasPrice() {
				// TODO Auto-generated method stub
				return java.math.BigInteger.valueOf(1000L);
			}
	
			@Override
			public java.math.BigInteger getGasLimit(String contractFunc) {
				// TODO Auto-generated method stub
				return java.math.BigInteger.valueOf(1000000);
			}
	
			@Override
			public java.math.BigInteger getGasLimit() {
				// TODO Auto-generated method stub
				return null;
			}
	
			@Override
			public java.math.BigInteger getGasPrice(String contractFunc) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
	
	
	
}
