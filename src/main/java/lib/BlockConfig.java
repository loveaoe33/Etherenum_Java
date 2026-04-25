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


public class BlockConfig {
	public Web3j node5; // Node 5 value
	public Web3j node5_RPC;
	public Web3j node3; // Node 3 value
	public Web3j node3_RPC;
	public UserPrice userContract;
	public ContractGasProvider contractGasProvider;
	public String walletFilePath; // Wallet Path
	public String walletFilePath_node3;
	public String password;
	public long chainId;
	public java.math.BigInteger gasLimit;
	public java.math.BigInteger gasPrice;
	public String contractBinaryPath;
	public String contractABIPath;
	public String contractAddress;

	public BlockConfig() throws IOException, CipherException {
		initNode();
	}
	
	// [MODIFIED] Kept original name 'getfilter' to avoid breaking existing calls, but translated comments.
	// Get Ethereum filter
	public EthFilter getfilter() {
		EthFilter filter = new EthFilter(
			    DefaultBlockParameterName.EARLIEST,
			    DefaultBlockParameterName.LATEST,
			    contractAddress
			);	
		return filter;
	}
	

	// [MODIFIED] Renamed to camelCase 'loadWallet', translated comments, and renamed parameters.
	// Load Wallet
	public org.web3j.crypto.Credentials loadWallet(String walletPath, String fileName)
			throws IOException, CipherException { 
		String walletName = walletPath + "/" + fileName;
		org.web3j.crypto.Credentials credentials = WalletUtils.loadCredentials(password, walletName); // Load credentials
		System.out.println("Certificate: Load Wallet Success");
		return credentials;
	}

	// [MODIFIED] Kept original name to maintain API compatibility. Removed unused 'Return_Message' variable.
	// Load Certificate
	public org.web3j.crypto.Credentials node_credential() throws IOException, CipherException {
		File directory = new File(walletFilePath);
		File[] files = directory.listFiles();
		org.web3j.crypto.Credentials credentials;
		if (files != null && files.length >= 1) {
			File walletFile = files[0];
			credentials = loadWallet(walletFilePath, walletFile.getName());
			return credentials;
		}
		return null;
	}

	// [MODIFIED] Renamed to 'initNode' (camelCase). Translated comments to English.
	public void initNode() throws IOException, CipherException {

		this.walletFilePath = "C:/Users/loveaoe33/AppData/Local/Ethereum/node5/keystore";
		this.walletFilePath_node3 = "C:/Users/loveaoe33/AppData/Local/Ethereum/node3/keystore";
		this.password = "123456";
		this.node5 = Web3j.build(new HttpService("http://127.0.0.1:8085")); // Contract IpAddress
		this.node5_RPC = Web3j.build(new HttpService("http://127.0.0.1:8085")); // Contract URL

		this.node3 = Web3j.build(new HttpService("http://127.0.0.1:8084"));
		this.node3_RPC = Web3j.build(new HttpService("http://127.0.0.1:8084"));
		this.chainId = 15;
		this.gasLimit = new java.math.BigInteger("4700000");
		this.gasPrice = new java.math.BigInteger("4700");
		this.contractBinaryPath = "C:/Users/loveaoe33/Desktop/hard-Contrac/artifacts/contracts/abi/UserPrice.bin";
		this.contractABIPath = "C:/Users/loveaoe33/Desktop/hard-Contrac/artifacts/contracts/abi/UserPrice.abi";
		this.contractAddress = "0x2220d0e9fc7fb74bccf55d50625ee83f6805dc9e"; // Contract address
		DefaultGasProvider gasProvider = new DefaultGasProvider() {
			@Override
			public BigInteger getGasLimit() {
				return BigInteger.valueOf(5000000); // Increase gas limit
			}
		};
		this.contractGasProvider = gasProvider;
		userContract = UserPrice.load(contractAddress, node5, node_credential(), gasProvider);

	}

}
