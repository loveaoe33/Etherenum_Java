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
	private Web3j web3j; // Node parameters need to be changed
	private Web3j web3j_RPC;
	private Web3j node3;
	private Web3j node3_RPC;
	ContractGasProvider contractGasProvider;
	String walletFilePath; // Node parameters need to be changed
	String walletFilePath_node3; 
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
		this.web3j = Web3j.build(new HttpService("http://127.0.0.1:8085")); // Contract URL
		this.web3j_RPC = Web3j.build(new HttpService("http://127.0.0.1:8085"));
		this.node3 = Web3j.build(new HttpService("http://127.0.0.1:8084"));
		this.node3_RPC = Web3j.build(new HttpService("http://127.0.0.1:8084"));
		this.chainId = 15;
		this.gasLimit = new java.math.BigInteger("4700000");
		this.gasprice = new java.math.BigInteger("4700");
		this.contractBinaryPath = "C:/Users/loveaoe33/Desktop/blockChain/bin/E_Contract_sol_SimpleContract.bin";
		this.contractABIPath = "C:/Users/loveaoe33/Desktop/blockChain/bin/E_Contract_sol_SimpleContract.abi";
		this.contractAddress = "0x7517f495432B590846e284bF95f5EFE347f45337"; // Contract address
		this.contractAddress = "0x2e5bb1e2ed37e7c573e11185d4c61240781fd617"; 
		this.contractGasProvider = contractGasProvider;
	}

	// [MODIFIED] Translated comments to English, removed unused variables, changed variables to camelCase.
	public Credentials Test_Wallet() throws IOException, CipherException {
		File directory = new File(walletFilePath);
		File[] files = directory.listFiles();
		if (files != null && files.length >= 1) {
			File walletFile = files[0];
			return Load_Wallet(walletFilePath, walletFile.getName());
		}
		return null;
	}

	// [MODIFIED] Translated comments to English, removed unused variables, changed variables to camelCase.
	public Credentials Test_Wallet2() throws IOException, CipherException {
		File directory = new File(walletFilePath_node3);
		File[] files = directory.listFiles();
		if (files != null && files.length >= 1) {
			File walletFile = files[1];
			return Load_Wallet(walletFilePath_node3, walletFile.getName());
		}
		return null;
	}

	// [MODIFIED] Translated comments to English, removed unused variables, changed variables to camelCase.
	public Credentials Test_Admin_Wallet() throws IOException, CipherException {
		File directory = new File(walletFilePath);
		File[] files = directory.listFiles();
		if (files != null && files.length >= 0) {
			File walletFile = files[0];
			return Load_Wallet(walletFilePath_node3, walletFile.getName());
		}
		return null;
	}

	// [MODIFIED] Translated comments to English, changed parameters to camelCase.
	// Load Credentials
	public Credentials Load_Wallet(String walletPath, String fileName) throws IOException, CipherException { 
		System.out.println("FileName: " + fileName);
		String walletName = walletPath + "/" + fileName;
		Credentials credentials = WalletUtils.loadCredentials(password, walletName); 
		System.out.println("Address: " + credentials.getAddress());
		return credentials;
	}

	// [MODIFIED] Changed parameters to camelCase.
	public void Unlock_Wallet(String walletAddress, String password) {
		System.out.println("Wallet_Unlock: " + walletAddress);
	}

	// [MODIFIED] Translated comments to English, changed variables to camelCase.
	// Check if wallet is a valid account
	public boolean Check_Wallet_Bool(String walletAddress) throws InterruptedException, ExecutionException { 
		try {
			EthGetBalance balance = web3j.ethGetBalance(walletAddress, DefaultBlockParameterName.LATEST).sendAsync()
					.get();
			String wei = balance.getBalance().toString();
			BigDecimal fromEther = Convert.fromWei(wei, Convert.Unit.ETHER);
			System.out.println("Transfer account Wei: " + wei);
			System.out.println("Transfer account Ether: " + fromEther);
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

	// [MODIFIED] Translated comments to English, changed variables to camelCase.
	// Completed: Create Wallet
	public String New__Wallet() {
		String walletNewFilePath = "C:/Users/loveaoe33/AppData/Local/Ethereum/node5/keystore";
		String message = "Not initialized";
		String walletFileName;
		try {
			walletFileName = WalletUtils.generateNewWalletFile(password, new File(walletNewFilePath), false);
			Credentials credentials_newFile = WalletUtils.loadCredentials(password,
					walletNewFilePath + "/" + walletFileName);
			message = String.format("Private:%s Public:%s Address:%s", credentials_newFile.getEcKeyPair().getPrivateKey(),
					credentials_newFile.getEcKeyPair().getPublicKey(), credentials_newFile.getAddress());
		} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
				| CipherException | IOException e) {
			e.printStackTrace();
		}
		return message;
	}

	// [MODIFIED] Translated comments to English, changed variables to camelCase.
	// Completed: Check Wallet Balance
	public String init(String walletAddress) {
		System.out.println("Initialization complete");
		EthGetBalance balance;
		String message = "Not initialized";
		try {
			balance = web3j.ethGetBalance(walletAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
			String unprocessedWei = balance.getBalance().toString();
			unprocessedWei = unprocessedWei.substring(0, 19);
			BigDecimal localhostBalance = Convert.fromWei(unprocessedWei, Convert.Unit.GWEI); // Balance in wei
			BigDecimal ether = Convert.fromWei(unprocessedWei, Convert.Unit.ETHER);
			message = String.format("Unprocessed %s, Local network balance %s, Ether balance %s", unprocessedWei, localhostBalance, ether);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return "Query failed";
		}
		return message;
	}

	// [MODIFIED] Translated comments to English, changed variables to camelCase.
	// Completed: Verify Wallet Content
	public String Check_Wallet(@RequestParam String walletAddress) throws InterruptedException, ExecutionException {
		try {
			EthGetBalance balance = web3j.ethGetBalance(walletAddress, DefaultBlockParameterName.LATEST).sendAsync()
					.get();
			String wei = balance.getBalance().toString();
			BigDecimal fromEther = Convert.fromWei(wei, Convert.Unit.ETHER);
			return String.format("Wei unit: %s, Ether unit: %s", wei, fromEther.toString());
		} catch (Exception e) {
			return "Wallet not found";
		}
	}

	// [MODIFIED] Translated comments to English.
	// Completed: View Last Transaction
	public String View_Last_Brock() {
		String message = "Not initialized";
		try {
			EthBlock.Block latestBlock = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf("latest"), true).send()
					.getBlock();
			if (latestBlock != null) {
				message = String.format("Block number: %s\nBlock hash: %s\nTransactions: %s\n", latestBlock.getNumber(),
						latestBlock.getHash(), latestBlock.getTransactions().size());
				System.out.println(message);
			}
		} catch (Exception e) {
			return "View error";
		}
		return message;
	}

	// [MODIFIED] Translated comments to English, changed variables to camelCase. Removed unnecessary semicolons.
	// Completed: View All Transaction Records
	public ArrayList<BlockClass> View_Array_Block() throws IOException {
		ArrayList<BlockClass> blockData = new ArrayList<>();
		java.math.BigInteger startBlock = java.math.BigInteger.valueOf(0); // Starting block number
		java.math.BigInteger endBlock = web3j.ethBlockNumber().send().getBlockNumber();
		for (java.math.BigInteger i = startBlock; i.compareTo(endBlock) <= 0; i = i.add(java.math.BigInteger.ONE)) {
			EthBlock.Block blocks = web3j.ethGetBlockByNumber(new DefaultBlockParameterNumber(i), true).send()
					.getBlock();
			if (blocks != null && blocks.getTransactions().size() > 0) {
				for (EthBlock.TransactionResult txResult : blocks.getTransactions()) {
					EthBlock.TransactionObject tx = (EthBlock.TransactionObject) txResult.get();
					BlockClass blockClass = BlockClass.builder().Transaction(tx.getHash()).From(tx.getFrom())
							.To(tx.getTo()).Value(tx.getValue()).Gas(tx.getGasPrice()).Limit(tx.getGas()).build();
					blockData.add(blockClass);
				}
			}
		}
		return blockData;
	}

	// [MODIFIED] Translated comments to English, changed variables to camelCase.
	// Completed: View Transaction Record
	public String View_Transaction_Hash(String hashCode) {
		try {
			JSONObject jsonObject = new JSONObject();
			Optional<org.web3j.protocol.core.methods.response.Transaction> transaction = web3j
					.ethGetTransactionByHash(hashCode).send().getTransaction();
			if (transaction != null) {
				jsonObject.put("From", transaction.get().getFrom());
				jsonObject.put("To", transaction.get().getTo());
				jsonObject.put("Value", transaction.get().getValue());
				jsonObject.put("Gas Price", transaction.get().getGasPrice());
				jsonObject.put("Gas Limit", transaction.get().getGas());
				return "Query successful: " + jsonObject.toString();
			} else {
				return "No data found";
			}
		} catch (IOException e) {
			return "Query error";
		}
	}

	// [MODIFIED] Translated comments to English, changed variables to camelCase, removed unused variables.
	// Completed: Transfer
	public String TransFer_ETH(String walletAddress)
			throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException,
			CipherException, IOException, InterruptedException, ExecutionException {
		File directory = new File(walletFilePath_node3);
		File[] files = directory.listFiles();
		Credentials credentials;
		if (files != null && files.length >= 2) {
			File walletFile = files[1];
			credentials = Load_Wallet(walletFilePath_node3, walletFile.getName());
			boolean isWalletValid = Check_Wallet_Bool(walletAddress);
			if (isWalletValid) {
				return TransFer(credentials, walletAddress);
			} else {
				return "Wallet has issues or does not exist";
			}
		} else {
			return "Please create a wallet first";
		}
	}

	// [MODIFIED] Translated comments to English, changed variables to camelCase.
	// Transfer
	public String TransFer(Credentials credentials, String toAddress) throws InterruptedException, IOException,
			CipherException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
		BigDecimal amount = BigDecimal.valueOf(1.0);
		try {
			// Retrieve account nonce
			EthGetTransactionCount ethGetTransactionCount = web3j
					.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync()
					.get();
			java.math.BigInteger nonce = ethGetTransactionCount.getTransactionCount();

			// Create transaction
			RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce,
					Convert.toWei("18000", Convert.Unit.WEI).toBigInteger(), gasLimit, toAddress,
					Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger());

			// Sign transaction
			byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
			String hexValue = Numeric.toHexString(signMessage);

			// Send transaction
			String transactionHash = web3j.ethSendRawTransaction(hexValue).send().getTransactionHash();
			System.out.println("Transaction hash: " + transactionHash);
			return "Transfer submitted successfully";
		} catch (InterruptedException | ExecutionException e) {
			return "Transfer submission failed";
		}
	}

	// [MODIFIED] Translated comments to English, initialized ArrayList to prevent NullPointerException.
	// Completed: List Node Accounts
	public ArrayList<String> Print_Wallet() {
		ArrayList<String> accounts = new ArrayList<>();
		try {
			accounts = (ArrayList<String>) web3j.ethAccounts().send().getAccounts();
		} catch (IOException e) {
			e.printStackTrace();
			accounts.add("Node query error");
			return accounts;
		}
		return accounts;
	}

	// [MODIFIED] Translated comments to English, fixed String.format syntax, changed variables to camelCase.
	// Completed: Build Contract
	public String Contract_build() throws Exception {
		Credentials credentials = Test_Wallet(); // Public and private keys are required to deploy the contract. Using test credentials for now.
		// Read contract bytecode file content
		String contractBinary = readFileAsString(contractBinaryPath);
		// Read contract ABI file content
		String contractABI = readFileAsString(contractABIPath);
		TransactionManager transactionManager = new ClientTransactionManager(web3j, credentials.getAddress());
		E_Contract_sol_SimpleContract contract = E_Contract_sol_SimpleContract
				.deploy(web3j, transactionManager, contractGasProvider).send();

		String contractAddressStr = contract.getContractAddress();
		TransactionReceipt transactionReceipt = contract.getTransactionReceipt().get();
		return String.format("Contract address: %s, Transaction receipt: %s", contractAddressStr, transactionReceipt.toString());
	}

	@GetMapping("BlockChain/Contract_View")
	public String Contract_View_Detail() {
		return null;
	}

	public String Contract_Address() {
		return null;
	}

	// [MODIFIED] Translated comments to English. Fixed redundant variable assignment.
	// Completed: Get Contract Content
	public String Contract_Get() throws Exception {
		Credentials credentials = Test_Wallet();
		try {
			E_Contract_sol_SimpleContract contract = E_Contract_sol_SimpleContract.load(contractAddress, web3j,
					credentials, contractGasProvider);
			// Call contract's getData method (example)
			Tuple2<BigInteger, String> value = contract.getData(credentials.getAddress()).send();
			return value.toString();
		} catch (IOException | CipherException e) {
			return "Contract query error";
		}
	}

	// [MODIFIED] Translated comments to English. Fixed BigInteger declaration.
	// Completed: Set Contract Content
	public String Contract_Set() throws Exception {
		Credentials credentials = Test_Wallet();
		try {
			TransactionManager transactionManager = new RawTransactionManager(web3j_RPC, credentials);
			E_Contract_sol_SimpleContract contract = E_Contract_sol_SimpleContract.load(contractAddress, web3j_RPC,
					credentials, contractGasProvider);
			// Call contract's setData method (example)
			BigInteger setData = new BigInteger("5");
			TransactionReceipt value = contract.setData(setData).send();
			return value.toString();
		} catch (IOException | CipherException e) {
			return "Contract query error";
		}
	}

	// [MODIFIED] Translated comments to English.
	// EVM (Ethereum Virtual Machine) Bytecode
	public String Contract_View() { 
		try {
			// Get smart contract code
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
			return "Call error";
		}
	}

	// [MODIFIED] Translated comments to English, added generic type to ArrayList, changed variables to camelCase.
	// Completed: List All Contracts
	public ArrayList<String> Print_Contract() {
		ArrayList<String> contractArray = new ArrayList<>();
		JSONObject jsonObject = new JSONObject();
		String fromContract = "0x2090f718972f8bf288f32a71a10dcacc5a5bd817";
		try {
			EthBlock ethblock = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
			java.math.BigInteger lastBlockNumber = ethblock.getBlock().getNumber();
			for (long i = 0; i <= lastBlockNumber.longValue(); i++) {
				EthBlock.Block block = web3j
						.ethGetBlockByNumber(DefaultBlockParameter.valueOf(java.math.BigInteger.valueOf(i)), true)
						.send().getBlock();
				List<TransactionResult> transactions = block.getTransactions();
				for (TransactionResult tx : transactions) { // TransactionResult is a generic class, gets object data inside block
					EthBlock.TransactionObject tractions = (TransactionObject) tx.get();
					String contractAddressStr = tractions.getFrom();
					if (contractAddressStr.equals(fromContract)) { // TransactionReceipt can get full transaction details by hash
						Optional<TransactionReceipt> receipt = web3j
								.ethGetTransactionReceipt(((EthBlock.TransactionObject) tx.get()).getHash()).send()
								.getTransactionReceipt();
						if (receipt.isPresent()) {
							jsonObject.put("Contract_Transaction:", receipt.get().getTransactionHash());
							jsonObject.put("Contract_Address:", receipt.get().getContractAddress());
							contractArray.add(jsonObject.toString());
						}
					}
				}
			}
			return contractArray;
		} catch (Exception e) {
			contractArray.add("Contract query error");
			return contractArray;
		}
	}

}
