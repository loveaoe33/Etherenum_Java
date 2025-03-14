package BlockChain;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EventObject;
import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import BlockChainObject.AdminUpdateObject;
import BlockChainObject.EventService;
import BlockChainObject.ResObject;
import BlockChainObject.TransactionObject;
import ch.qos.logback.core.subst.Token.Type;
import io.reactivex.Flowable;
import jnr.ffi.provider.BadType;
import jnr.unixsocket.Credentials;
import lib.UserPrice;
import lib.UserPrice.AdminUpdateUserRecodeEventResponse;
import lib.UserPrice.TransactionRecordEventResponse;
import lib.UserPrice.UserResgisterEventResponse;
import lib.UserPrice.Users;

@Service
@ComponentScan(basePackages = {"BlockChainObject","BlockChainObject"})
public class EthereumComponent_UserPrice {
	private Web3j node5;// 節點參數要改
	private Web3j node5_RPC;// 節點參數要改
	private Web3j node3;// 節點參數要改
	private Web3j node3_RPC;// 節點參數要改
	private UserPrice userContract;
	private ResObject resObject;
	private AdminUpdateObject adminData;
	private TransactionObject transactionObject;
	private EventService eventService;
//	private 
//    private 


	ContractGasProvider contractGasProvider;
	String walletFilePath;// 節點參數要改
	String walletFilePath_node3;// 節點參數要改
	String password;
	long chainId;
	java.math.BigInteger gasLimit;
	java.math.BigInteger gasPrice;
	String contractBinaryPath;
	String contractABIPath;
	String contractAddress;

	@Autowired
	public EthereumComponent_UserPrice(ContractGasProvider contractGasProvider,ResObject resObject,AdminUpdateObject adminData,TransactionObject transactionObject,EventService eventService) throws IOException, CipherException {
		this.resObject=resObject;
		this.adminData=adminData;
		this.transactionObject=transactionObject;
		this.eventService=eventService;
		Node_Init();
	}

	public String readFileAsString(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		byte[] bytes = Files.readAllBytes(path);
		return new String(bytes, StandardCharsets.UTF_8);
	}

	// 完成_合約建立
	public String Contract_UserPrice_build() throws Exception {
		String constuctorData = "UserPrcie_Contract";
		BigInteger value = BigInteger.ZERO;
		org.web3j.crypto.Credentials credentials = node_credential(); // 需要公私鑰才能部署合約 先使用測試
//		// 读取合约字节码文件内容
//		String contractBinary = readFileAsString(contractBinaryPath);
//		// 读取合约 ABI 文件内容
//		String contractABI = readFileAsString(contractABIPath);
		TransactionManager transactionManager = new ClientTransactionManager(node5, credentials.getAddress());
//		E_Contract_sol_SimpleContract contract = E_Contract_sol_SimpleContract
//				.deploy(web3j, transactionManager, contractGasProvider).send();				
		UserPrice userPriceContract = UserPrice.deploy(node5, credentials, contractGasProvider, constuctorData).send();
		String Contract_Address = userPriceContract.getContractAddress();
		TransactionReceipt transactionReceipt = userPriceContract.getTransactionReceipt().get();
		String message = String.format("合約地址{},交易收據{}", Contract_Address, transactionReceipt.toString());

		return message;

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

	// 載入憑證
	public org.web3j.crypto.Credentials Load_Wallet(String Wallet_Paht, String FileName)
			throws IOException, CipherException { // 載入憑證
		System.out.println("FileName: " + FileName);
		String Wallet_Name = Wallet_Paht + "/" + FileName;
		org.web3j.crypto.Credentials credentials = WalletUtils.loadCredentials(password, Wallet_Name); // 憑證載入
		System.out.println("Address: " + credentials.getAddress());
		return credentials;
	}

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

	public String Price_setUser() throws Exception {
		String NodeAddress = "123";
		String UserAccount = "loveaoe44";
		String UserPassword = "love20720";
		BigInteger AccountLevel = BigInteger.valueOf(0);
		BigInteger UserInitPrice = BigInteger.valueOf(50);
		String CreatDate = "20250101";
		String UpdateDate = "20250101";
		String LastTransDate = "20250101";
		String LastTransTag = "default";
		String CreatName = "Leo";
		Boolean IsVal = false;
		String Result;
		TransactionReceipt data = userContract.setUser(NodeAddress, UserAccount, UserPassword, AccountLevel,
				UserInitPrice, CreatDate, UpdateDate, LastTransDate, LastTransTag, CreatName, IsVal).send();
		try {
			Result = ("0x1".equals(data.getStatus())) ? "setUser Sucess" : "setUser fail";
			return Result;
		} catch (Exception e) {
			return "Node fail";
		}

	}

	public String Price_getTest() throws Exception {
		RemoteCall<String> data = userContract.getTest();
		String TransData = data.send();
		return TransData;
	}

	public String Price_approveUser() {
		String NodeAddres = "123";
		String Admin = "loveaoe33";
		String Account = "loveaoe44";
		String ApprovDate = "20250102";
		Boolean IsVal = true;
		String Result;
		try {
			TransactionReceipt data = userContract.approvUser(NodeAddres, Admin, Account, ApprovDate, IsVal).send();
			Result = ("0x1".equals(data.getStatus())) ? "arrover Sucess" : "arrover fail";
			return Result;
		} catch (Exception e) {
			return "Node fail";
		}
	}

	public String Price_getUser() throws Exception {
		String NodeAddress = "123";
		String Account = "loveaoe44";
		RemoteCall<String> data = userContract.getUser(NodeAddress, Account);
		String TransData = data.send();

		System.out.println(TransData);
		return "Sucess";
	}

	public String Price_updateUser() {
		String NodeAddress = "123";
		String Admin = "loveaoe33";
		String UpdateAccount = "loveaoe44";
		String UserPword = "love30720";
		BigInteger AccountLevel = BigInteger.valueOf(1);
		BigInteger AccountPrice = BigInteger.valueOf(300);
		String PriceRemark = "Admin Update";
		String UpdateDate = "20210102";
		String CreateName = "Leo";
		Boolean AccountIsVal = false;
		String Result;
		try {

			TransactionReceipt data = userContract.updateUser(NodeAddress, Admin, UpdateAccount, UserPword, gasLimit,
					gasPrice, PriceRemark, UpdateDate, CreateName, AccountIsVal).send();
			Result = ("0x1".equals(data.getStatus())) ? "Insert Sucess" : "Insert fail";
			return Result;

		} catch (Exception e) {
			return "Node fail";

		}

	}

	public String Price_transPrice() {
		String FromAccoun = "loveaoe33";
		BigInteger TransPrice = BigInteger.valueOf(500);
		String TransAccount = "loveaoe44";
		String TransRemark = "test transfer";
		String TransDate = "20250101";
		String Result;
		try {

			TransactionReceipt data = userContract
					.transPrice(FromAccoun, TransPrice, TransAccount, TransRemark, TransDate).send();
			Result = ("0x1".equals(data.getStatus())) ? "Transfer Sucess" : "Transfer fail";
			return Result;
		} catch (Exception e) {
			return "Node fail";

		}
	}

	public String Price_getTransactionRecord() {   //get block Trans event
		TransactionRecordEventResponse data = userContract.getTransactionRecordEventFromLog(null);
			
	      return "Sucess";

	}

	public String Price_getAdminUpdateUserRecord() {//get block Update event
      return "Sucess";
	}
	public String Price_getUserApprovLog() throws InterruptedException {//get block Resgister event
		EthFilter filter = new EthFilter(
			    DefaultBlockParameterName.EARLIEST,
			    DefaultBlockParameterName.LATEST,
			    contractAddress
			);	
		Flowable<UserResgisterEventResponse> data = userContract.userResgisterEventFlowable(filter);
		
		
		
		
		data.subscribe(event -> {
			synchronized (resObject) {
				resObject.setAdminAccount(event.adminAccount);
				resObject.setApprovDate(event.approvDate);
				resObject.setResAccount(event.resAccount);
				eventService.set_InitRes(resObject);
				System.out.println("Comleted the data:"+eventService.get_AllRes());
			}
        },
        throwable->System.out.println("Error"+throwable.getMessage()),
        ()->System.out.println("Comleted the data:"+eventService.get_AllRes())
        );
		
		return "Sucesss";

	}
	
	
	public String Price_getTransactionRecordLog() throws InterruptedException {//get block Resgister event
		EthFilter filter = new EthFilter(
			    DefaultBlockParameterName.EARLIEST,
			    DefaultBlockParameterName.LATEST,
			    contractAddress
			);	
		Flowable<TransactionRecordEventResponse> data = userContract.transactionRecordEventFlowable(filter);
		
		data.subscribe(event -> {
			synchronized (transactionObject) {
				transactionObject.setFromAccount(event.fromAccount);
				transactionObject.setToAccount(event.toAccount);
				transactionObject.setPrice(event.price.intValue());
				transactionObject.setTransRemark(event.transRemark);
				transactionObject.setTransDate(event.transDate);
				System.out.println("Comleted the data:"+eventService.get_AllTrans());
			}
        },
        throwable->System.out.println("Error"+throwable.getMessage()),
        ()->System.out.println("Comleted")
        );
		
		return "Sucesss";

	}
	
	
	public String Price_getAdminUpdateLog() throws InterruptedException {//get block Resgister event
		EthFilter filter = new EthFilter(
			    DefaultBlockParameterName.EARLIEST,
			    DefaultBlockParameterName.LATEST,
			    contractAddress
			);	
		Flowable<AdminUpdateUserRecodeEventResponse> data = userContract.adminUpdateUserRecodeEventFlowable(filter);
		data.subscribe(event -> {
			synchronized (adminData) {    
			        adminData.setUpdateAccount(event.updateAccount);
			        adminData.setOldData(event.oldData);
			        adminData.setUpdateAdmin(event.updateAdmin);
			        adminData.setUpdateData(event.updateData);
			        eventService.set_InitAdmin(adminData);
				System.out.println("Comleted the data:"+eventService.get_AllAdmin());
			}
        },
        throwable->System.out.println("Error"+throwable.getMessage()),
        ()->System.out.println("Comleted")
        );
		
		return "Sucesss";

	}

}
