package lib;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import BlockChainObject.AdminUpdateObject;
import BlockChainObject.EventService;
import BlockChainObject.ResObject;
import BlockChainObject.TransactionObject;
import io.reactivex.Flowable;
import lib.UserPrice.UserResgisterEventResponse;

public class BlockConfig {
	public Web3j node5;// node5 value
	public Web3j node5_RPC;
	public Web3j node3;//node3 value
	public Web3j node3_RPC;
	public UserPrice userContract;
	public ResObject resObject;
	public AdminUpdateObject adminData;
	public TransactionObject transactionObject;
	public EventService eventService;
	public ContractGasProvider contractGasProvider;
	public String walletFilePath;//wallet Path
	public String walletFilePath_node3;
	public String password;
	public long chainId;
	public java.math.BigInteger gasLimit;
	public java.math.BigInteger gasPrice;
	public String contractBinaryPath;
	public String contractABIPath;
	public String contractAddress;

	public BlockConfig() throws IOException, CipherException {
		Node_Init();
	}
	
	public EthFilter getfilter() {
		EthFilter filter = new EthFilter(
			    DefaultBlockParameterName.EARLIEST,
			    DefaultBlockParameterName.LATEST,
			    contractAddress
			);	
		return filter;
	}
	
	


	// load Wallet
	public org.web3j.crypto.Credentials Load_Wallet(String Wallet_Paht, String FileName)
			throws IOException, CipherException { // 
		String Wallet_Name = Wallet_Paht + "/" + FileName;
		org.web3j.crypto.Credentials credentials = WalletUtils.loadCredentials(password, Wallet_Name); // 憑證載入
		System.out.println("Certificate: Load Wallet Sucess " );
		return credentials;
	}
	// Load Certificate
	public org.web3j.crypto.Credentials node_credential() throws IOException, CipherException {
		File directory = new File(walletFilePath);
		File[] files = directory.listFiles();
		org.web3j.crypto.Credentials credentials;
		String Return_Message;
		if (files != null && files.length >= 1) {
			File secondFile = files[0];
			credentials = Load_Wallet(walletFilePath, secondFile.getName());
			Return_Message = String.format("私:%s公:%s地址%s", credentials.getEcKeyPair().getPrivateKey(),
					credentials.getEcKeyPair().getPublicKey(), credentials.getAddress());
			return credentials;
		}
		return null;
	}

	public void Node_Init() throws IOException, CipherException {

		this.walletFilePath = "C:/Users/loveaoe33/AppData/Local/Ethereum/node5/keystore";
		this.walletFilePath_node3 = "C:/Users/loveaoe33/AppData/Local/Ethereum/node3/keystore";
		this.password = "123456";
		this.node5 = Web3j.build(new HttpService("http://127.0.0.1:8085")); // Contract_IpAddress
		this.node5_RPC = Web3j.build(new HttpService("http://127.0.0.1:8085")); // 合約網址

		this.node3 = Web3j.build(new HttpService("http://127.0.0.1:8084"));
		this.node3_RPC = Web3j.build(new HttpService("http://127.0.0.1:8084"));
		this.chainId = 15;
		this.gasLimit = new java.math.BigInteger("4700000");
		this.gasPrice = new java.math.BigInteger("4700");
		this.contractBinaryPath = "C:/Users/loveaoe33/Desktop/hard-Contrac/artifacts/contracts/abi/UserPrice.bin";
		this.contractABIPath = "C:/Users/loveaoe33/Desktop/hard-Contrac/artifacts/contracts/abi/UserPrice.abi";
		this.contractAddress = "0x2220d0e9fc7fb74bccf55d50625ee83f6805dc9e"; // 合约地址
		DefaultGasProvider gasProvider = new DefaultGasProvider() {
			@Override
			public BigInteger getGasLimit() {
				return BigInteger.valueOf(5000000); // 增加 gas 限额
			}
		};
		this.contractGasProvider = gasProvider;
		userContract = UserPrice.load(contractAddress, node5, node_credential(), gasProvider);

	}

}
