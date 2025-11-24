package BlockChain;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.AbiDefinition;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionObject;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionResult;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetCode;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import BlockChainObject.BlockClass;
import net.sf.json.JSONObject;

@Service // @Compnent是最普遍用法 @Bean可以選擇new的物件
public class EthereumComponent {
	private Web3j web3j;// 節點參數要改
	private Web3j web3j_RPC;// 節點參數要改
	private Web3j node3;// 節點參數要改
	private Web3j node3_RPC;// 節點參數要改
	ContractGasProvider contractGasProvider;
	String walletFilePath;// 節點參數要改
	String walletFilePath_node3;// 節點參數要改
	String password;
	long chainId;
	java.math.BigInteger gasLimit;
	java.math.BigInteger gasprice;
	String contractBinaryPath;
	String contractABIPath;
	String contractAddress;

	@Autowired
	public EthereumComponent(ContractGasProvider contractGasProvider) {
		this.walletFilePath = "C:/Users/loveaoe33/AppData/Local/Ethereum/node5/keystore";
		this.walletFilePath_node3 = "C:/Users/loveaoe33/AppData/Local/Ethereum/node3/keystore";
		this.password = "123456";
		this.web3j = Web3j.build(new HttpService("http://127.0.0.1:8085")); // 合約網址
		this.web3j_RPC = Web3j.build(new HttpService("http://127.0.0.1:8085")); // 合約網址

		this.node3 = Web3j.build(new HttpService("http://127.0.0.1:8084"));
		this.node3_RPC = Web3j.build(new HttpService("http://127.0.0.1:8084"));
		this.chainId = 15;
		this.gasLimit = new java.math.BigInteger("4700000");
		this.gasprice = new java.math.BigInteger("4700");
		this.contractBinaryPath = "C:/Users/loveaoe33/Desktop/blockChain/bin/E_Contract_sol_SimpleContract.bin";
		this.contractABIPath = "C:/Users/loveaoe33/Desktop/blockChain/bin/E_Contract_sol_SimpleContract.abi";
		this.contractAddress = "0x7517f495432B590846e284bF95f5EFE347f45337"; // 合约地址
		this.contractAddress = "0x2e5bb1e2ed37e7c573e11185d4c61240781fd617"; // 合约地址

		this.contractGasProvider = contractGasProvider;
	}

	public Credentials Test_Wallet() throws IOException, CipherException {
		File directory = new File(walletFilePath);
		File[] files = directory.listFiles();
		Credentials credentials;
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

	public Credentials Test_Wallet2() throws IOException, CipherException {
		File directory = new File(walletFilePath_node3);
		File[] files = directory.listFiles();
		Credentials credentials;
		String Return_Message;
		if (files != null && files.length >= 1) {
			File secondFile = files[1];

			credentials = Load_Wallet(walletFilePath_node3, secondFile.getName());
			Return_Message = String.format("私:%s公:%s地址%s", credentials.getEcKeyPair().getPrivateKey(),
					credentials.getEcKeyPair().getPublicKey(), credentials.getAddress());
			return credentials;

		}
		return null;
	}

	public Credentials Test_Admin_Wallet() throws IOException, CipherException {
		File directory = new File(walletFilePath);
		File[] files = directory.listFiles();
		Credentials credentials;
		String Return_Message;
		if (files != null && files.length >= 0) {
			File secondFile = files[0];

			credentials = Load_Wallet(walletFilePath_node3, secondFile.getName());
			Return_Message = String.format("私:%s公:%s地址%s", credentials.getEcKeyPair().getPrivateKey(),
					credentials.getEcKeyPair().getPublicKey(), credentials.getAddress());
			return credentials;

		}
		return null;
	}

	// 載入憑證
	public Credentials Load_Wallet(String Wallet_Paht, String FileName) throws IOException, CipherException { // 載入憑證
		System.out.println("FileName: " + FileName);
		String Wallet_Name = Wallet_Paht + "/" + FileName;
		Credentials credentials = WalletUtils.loadCredentials(password, Wallet_Name); // 憑證載入
		System.out.println("Address: " + credentials.getAddress());
		return credentials;
	}

	public void Unlock_Wallet(String Wallet_Address, String password) {

		System.out.println("Wallet_Unlock: " + Wallet_Address);

	}

	// 確認錢包是否有效帳戶
	public boolean Check_Wallet_Bool(String Wallet_Address) throws InterruptedException, ExecutionException { // 確認錢包是否正確
		try {
			EthGetBalance balace = web3j.ethGetBalance(Wallet_Address, DefaultBlockParameterName.LATEST).sendAsync()
					.get();
			String Wei = (balace.getBalance()).toString();
//    	    String Eth=Wei.substring(0,19);
			BigDecimal From_ether = Convert.fromWei(Wei, Convert.Unit.ETHER);
			System.out.println("轉帳帳戶Wei:" + Wei);
			System.out.println("轉帳帳戶乙太:" + From_ether);
			return true;

		} catch (Exception e) {
			return false;
		}

	}

	public String readFileAsString(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		byte[] bytes = Files.readAllBytes(path);
		return new String(bytes, StandardCharsets.UTF_8);
	}

	// 完成_創立錢包
	public String New__Wallet() {
		String wallet_New_FilePath = "C:/Users/loveaoe33/AppData/Local/Ethereum/node5/keystore";
		String Message = "尚未初始化";
		String walletFileName;
		try {
			walletFileName = WalletUtils.generateNewWalletFile(password, new File(wallet_New_FilePath), false);
			Credentials credentials_newFile = WalletUtils.loadCredentials(password,
					wallet_New_FilePath + "/" + walletFileName);
			Message = String.format("私:%s公:%s地址%s", credentials_newFile.getEcKeyPair().getPrivateKey(),
					credentials_newFile.getEcKeyPair().getPublicKey(), credentials_newFile.getAddress());
		} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
				| CipherException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Message;
	}

	// 完成_調閱錢包餘額
	public String init(String Wallet_Address) {
		System.out.println("初始化完成");
		EthGetBalance balace;
		String Message = "尚未初始化";
		try {
			balace = web3j.ethGetBalance(Wallet_Address, DefaultBlockParameterName.LATEST).sendAsync().get();
			String no_process = (balace.getBalance()).toString();
			no_process = no_process.substring(0, 19);
			BigDecimal localhostBalance = Convert.fromWei(no_process, Convert.Unit.GWEI); // wei單位餘額
			BigDecimal ether = Convert.fromWei(no_process, Convert.Unit.ETHER);
			Message = String.format("沒轉換%s,本地網路餘額%s,乙太餘額%s", no_process, localhostBalance, ether);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "調閱失敗";

		}

		return Message;

	}

	// 完成_確認錢包內容
	public String Check_Wallet(@RequestParam String Wallet_Address) throws InterruptedException, ExecutionException {// 確認錢包是否正確
		try {

			EthGetBalance balace = web3j.ethGetBalance(Wallet_Address, DefaultBlockParameterName.LATEST).sendAsync()
					.get();
			String Wei = (balace.getBalance()).toString();
//    	    String Eth=Wei.substring(0,19);
			BigDecimal From_ether = Convert.fromWei(Wei, Convert.Unit.ETHER);
			String Return_Message = String.format("Wei單位%s:,乙太單位:%s", Wei, From_ether.toString());
			return Return_Message;

		} catch (Exception e) {
			return "找無此錢包";
		}

	}

	// 完成_查看最後一筆交易
	public String View_Last_Brock() {
		String Message = "尚未初始化";
		try {
			EthBlock.Block latestBlock = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf("latest"), true).send()
					.getBlock();
			if (latestBlock != null) {
				Message = String.format("Block number: %s\nBlock hash: %s\nTransactions: %s\n", latestBlock.getNumber(),
						latestBlock.getHash(), latestBlock.getTransactions().size());
				System.out.println(Message);
			}
		} catch (Exception e) {
			return "查看錯誤";
		}
		return Message;
	}

	// 完成_調閱所有交易紀錄
	public ArrayList<BlockClass> View_Array_Block() throws IOException {
		ArrayList<BlockClass> Block_Date = new ArrayList<BlockClass>();
		java.math.BigInteger startBlock = java.math.BigInteger.valueOf(0); // 起始区块号
		java.math.BigInteger endBlock = web3j.ethBlockNumber().send().getBlockNumber();
		for (java.math.BigInteger i = startBlock; i.compareTo(endBlock) <= 0; i = i.add(java.math.BigInteger.ONE)) {
			EthBlock.Block blocks = web3j.ethGetBlockByNumber(new DefaultBlockParameterNumber(i), true).send()
					.getBlock();
			if (blocks != null && blocks.getTransactions().size() > 0) {
//				System.out.println("Block Number: " + blocks.getNumber());
//				System.out.println("Transaction size: " + blocks.getTransactions().size());
//				System.out.println("Transaction hash: " + blocks.getTransactions().toString());
//				System.out.println("block hash: " + blocks.getHash());

				for (EthBlock.TransactionResult txResult : blocks.getTransactions()) {
					EthBlock.TransactionObject tx = (EthBlock.TransactionObject) txResult.get();
					BlockClass blockClass = BlockClass.builder().Transaction(tx.getHash()).From(tx.getFrom())
							.To(tx.getTo()).Value(tx.getValue()).Gas(tx.getGasPrice()).Limit(tx.getGas()).build();
					Block_Date.add(blockClass);

				}
				;

			}
			;

		}
		;

		return Block_Date;
	}

	// 完成_調閱交易紀錄
	public String View_Transaction_Hash(String Hash_Code) {
		try {
			JSONObject jsonObject = new JSONObject();
			Optional<org.web3j.protocol.core.methods.response.Transaction> transaction = web3j
					.ethGetTransactionByHash(Hash_Code).send().getTransaction();
			if (transaction != null) {
				jsonObject.put("From", transaction.get().getFrom());
				jsonObject.put("To", transaction.get().getTo());
				jsonObject.put("Value", transaction.get().getValue());
				jsonObject.put("Gas Price", transaction.get().getGasPrice());
				jsonObject.put("Gas Limit", transaction.get().getGas());
				return "調閱成功:" + jsonObject.toString();
			} else {
				return "查無資料";
			}
		} catch (IOException e) {
			return "查尋錯誤";
		}
	}

	// 完成_轉帳
	public String TransFer_ETH(String Wallet_Address)
			throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException,
			CipherException, IOException, InterruptedException, ExecutionException {
		File directory = new File(walletFilePath_node3);
		File[] files = directory.listFiles();
		Credentials credentials;
		String Return_Message;
		if (files != null && files.length >= 2) {
			File secondFile = files[1];
			credentials = Load_Wallet(walletFilePath_node3, secondFile.getName());
			Return_Message = String.format("私:%s公:%s地址%s", credentials.getEcKeyPair().getPrivateKey(),
					credentials.getEcKeyPair().getPublicKey(), credentials.getAddress());
			boolean wallet_check = Check_Wallet_Bool(Wallet_Address);
			if (wallet_check) {
				return TransFer(credentials, Wallet_Address);
			} else {
				return "錢包有問題或不存在";

			}
		} else {
			return "請先建立錢包";

		}

	}

	// 轉帳
	public String TransFer(Credentials credentials, String to_Address) throws InterruptedException, IOException,
			CipherException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
		BigDecimal amount = BigDecimal.valueOf(1.0); // Trans_Money

		try {
			// 檢索帳戶的 nonce 值
			EthGetTransactionCount ethGetTransactionCount = web3j
					.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync()
					.get();
			java.math.BigInteger nonce = ethGetTransactionCount.getTransactionCount();

			// 交易創立
			RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce,
					Convert.toWei("18000", Convert.Unit.WEI).toBigInteger(), gasLimit, to_Address,
					Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger());

			// 簽署交易
			byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
			String hexValue = Numeric.toHexString(signMessage);

			// 發送交易
			String transactionHash = web3j.ethSendRawTransaction(hexValue).send().getTransactionHash();
			System.out.println("Transaction hash: " + transactionHash);
			return "轉帳提交成功";
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			return "轉帳提交失敗";
		}
	}

	// 完成_列出節點帳號
	public ArrayList<String> Print_Wallet() {
		ArrayList<String> account = null;
		try {
			account = (ArrayList<String>) web3j.ethAccounts().send().getAccounts();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			account.add("節點調閱錯誤");
			return account;
		}
		return account;
	}

	// 完成_合約建立
	public String Contract_build() throws Exception {
		Credentials credentials = Test_Wallet(); // 需要公私鑰才能部署合約 先使用測試
		// 读取合约字节码文件内容
		String contractBinary = readFileAsString(contractBinaryPath);
		// 读取合约 ABI 文件内容
		String contractABI = readFileAsString(contractABIPath);

		TransactionManager transactionManager = new ClientTransactionManager(web3j, credentials.getAddress());

		E_Contract_sol_SimpleContract contract = E_Contract_sol_SimpleContract
				.deploy(web3j, transactionManager, contractGasProvider).send();

		String Contract_Address = contract.getContractAddress();
		TransactionReceipt transactionReceipt = contract.getTransactionReceipt().get();
		String message = String.format("合約地址{},交易收據{}", Contract_Address, transactionReceipt.toString());

		return message;

	}


	@GetMapping("BlockChain/Contract_View")
	public String Contract_View_Detail() {
		return null;
	}

	public String Contract_Address() {
		return null;
	}

	// 完成_取得合約內容
	public String Contract_Get() throws Exception {
		Credentials credentials = credentials = Test_Wallet();
		;
		try {
			E_Contract_sol_SimpleContract contract = E_Contract_sol_SimpleContract.load(contractAddress, web3j,
					credentials, contractGasProvider);
			// 调用合约的 getValue 方法（示例）
			Tuple2<BigInteger, String> value = contract.getData(credentials.getAddress()).send();
			return value.toString();

		} catch (IOException | CipherException e) {
			// TODO Auto-generated catch block
			return "合約調閱錯誤";
		}

	}

	// 完成_設置合約內容
	public String Contract_Set() throws Exception {
		Credentials credentials = Test_Wallet();
		;
		try {
			TransactionManager transactionManager = new RawTransactionManager(web3j_RPC, credentials);

			E_Contract_sol_SimpleContract contract = E_Contract_sol_SimpleContract.load(contractAddress, web3j_RPC,
					credentials, contractGasProvider);

			// 调用合约的 getValue 方法（示例）
			java.math.BigInteger _setData = new java.math.BigInteger("5");

			TransactionReceipt value = contract.setData(_setData).send();

			return value.toString();
		} catch (IOException | CipherException e) {
			// TODO Auto-generated catch block
			return "合約調閱錯誤";
		}

	}

	public String Contract_View() { // EVM（以太坊虚拟机）字節馬
		try {
			// 获取智能合约的代码
			EthGetCode ethGetCode = web3j.ethGetCode(contractAddress, DefaultBlockParameterName.LATEST).send();

			if (ethGetCode.hasError()) {
				System.out.println("Error: " + ethGetCode.getError().getMessage());
				return "Error: " + ethGetCode.getError().getMessage();

			} else {
				String contractCode = ethGetCode.getCode();
				System.out.println("Smart Contract Code: " + contractCode);
				return "Smart Contract Code: " + contractCode;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "調用錯誤";
		}
	}

	// 完成_列出所有合約
	public ArrayList<String> Print_Contract() {
		ArrayList<String> Contract_Array = new ArrayList();
		JSONObject jsonObject = new JSONObject();
		String from_Contract = "0x2090f718972f8bf288f32a71a10dcacc5a5bd817";
		try {
			EthBlock ethblock = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
			java.math.BigInteger lastBlockNumber = ethblock.getBlock().getNumber();

			for (long i = 0; i <= lastBlockNumber.longValue(); i++) {
				EthBlock.Block block = web3j
						.ethGetBlockByNumber(DefaultBlockParameter.valueOf(java.math.BigInteger.valueOf(i)), true)
						.send().getBlock();
				List<TransactionResult> transactions = block.getTransactions();
				for (TransactionResult tx : transactions) { // TransactionResult是一个泛型类 取得區塊內的物件資料
					EthBlock.TransactionObject tractions = (TransactionObject) tx.get();
					String contractAddress = tractions.getFrom();
					if (contractAddress.equals(from_Contract)) { // TransactionReceipt可以依照hash取得完整的交易明細
						Optional<TransactionReceipt> receipt = web3j
								.ethGetTransactionReceipt(((EthBlock.TransactionObject) tx.get()).getHash()).send()
								.getTransactionReceipt();
						jsonObject.put("Contract_Transaction:", receipt.get().getTransactionHash());
						jsonObject.put("Contract_Address:", receipt.get().getContractAddress());
						Contract_Array.add(jsonObject.toString());
					}
				}
			}
			return Contract_Array;
		} catch (Exception e) {
			Contract_Array.add("合約調閱錯誤");
			return Contract_Array;
		}
	}

	

}
