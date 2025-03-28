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
import lib.BlockConfig;
import lib.UserPrice;
import lib.UserPrice.AdminUpdateUserRecodeEventResponse;
import lib.UserPrice.TransactionRecordEventResponse;
import lib.UserPrice.UserResgisterEventResponse;
import lib.UserPrice.Users;

@Service
@ComponentScan(basePackages = {"BlockChainObject","BlockChainObject"})
public class EthereumComponent_UserPrice {

	private UserPrice userContract;
	private ResObject resObject;
	private AdminUpdateObject adminData;
	private TransactionObject transactionObject;
	private EventService eventService;
	private BlockConfig blockConfig;
//	private 
//    private 


	@Autowired
	public EthereumComponent_UserPrice(ContractGasProvider contractGasProvider,ResObject resObject,AdminUpdateObject adminData,TransactionObject transactionObject,EventService eventService,BlockConfig blockConfig) throws IOException, CipherException {
		this.resObject=resObject;
		this.adminData=adminData;
		this.transactionObject=transactionObject;
		this.eventService=eventService;
		this.blockConfig=blockConfig;
		this.userContract=blockConfig.userContract;
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
		org.web3j.crypto.Credentials credentials = blockConfig.node_credential(); // 需要公私鑰才能部署合約 先使用測試
		TransactionManager transactionManager = new ClientTransactionManager(blockConfig.node5, credentials.getAddress());			
		UserPrice userPriceContract = UserPrice.deploy(blockConfig.node5, credentials, blockConfig.contractGasProvider, constuctorData).send();
		String Contract_Address = userPriceContract.getContractAddress();
		TransactionReceipt transactionReceipt = userPriceContract.getTransactionReceipt().get();
		String message = String.format("合約地址{},交易收據{}", Contract_Address, transactionReceipt.toString());
		return message;

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

			TransactionReceipt data = userContract.updateUser(NodeAddress, Admin, UpdateAccount, UserPword, blockConfig.gasLimit,
					blockConfig.gasPrice, PriceRemark, UpdateDate, CreateName, AccountIsVal).send();
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
		Flowable<UserResgisterEventResponse> data = userContract.userResgisterEventFlowable(blockConfig.getfilter());
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

		Flowable<TransactionRecordEventResponse> data = userContract.transactionRecordEventFlowable(blockConfig.getfilter());
		
		data.subscribe(event -> {
			synchronized (transactionObject) {
				transactionObject.setFromAccount(event.fromAccount);
				transactionObject.setToAccount(event.toAccount);
				transactionObject.setPrice(event.price.intValue());
				transactionObject.setTransRemark(event.transRemark);
				transactionObject.setTransDate(event.transDate);
				eventService.set_AddTrans(transactionObject);

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
			    blockConfig.contractAddress
			);	
		Flowable<AdminUpdateUserRecodeEventResponse> data = userContract.adminUpdateUserRecodeEventFlowable(blockConfig.getfilter());
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
