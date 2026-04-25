package BlockChain;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import io.reactivex.Flowable;
import lib.BlockConfig;
import lib.UserPrice;
import lib.UserPrice.AdminUpdateUserRecodeEventResponse;
import lib.UserPrice.TransactionRecordEventResponse;
import lib.UserPrice.UserResgisterEventResponse;
import lib.UserPrice.Users;

@Service
@ComponentScan(basePackages = { "BlockChainObject" })
public class EthereumComponent_UserPrice {

	private UserPrice userContract;
	private ResObject resObject;
	private AdminUpdateObject adminData;
	private TransactionObject transactionObject;
	private EventService eventService;
	private BlockConfig blockConfig;

	@Autowired
	public EthereumComponent_UserPrice(ContractGasProvider contractGasProvider, ResObject resObject,
			AdminUpdateObject adminData, TransactionObject transactionObject, EventService eventService,
			BlockConfig blockConfig) throws IOException, CipherException {
		this.resObject = resObject;
		this.adminData = adminData;
		this.transactionObject = transactionObject;
		this.eventService = eventService;
		this.blockConfig = blockConfig;
		this.userContract = blockConfig.userContract;
	}

	public String readFileAsString(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		byte[] bytes = Files.readAllBytes(path);
		return new String(bytes, StandardCharsets.UTF_8);
	}

	// [MODIFIED] Translated comment to English, removed unused variables, fixed String format syntax.
	// Build UserPrice Contract
	public String Contract_UserPrice_build() throws Exception {
		String constuctorData = "UserPrice_Contract";
		// Public and private keys are required to deploy the contract. Using test credentials for now.
		org.web3j.crypto.Credentials credentials = blockConfig.node_credential();
		UserPrice userPriceContract = UserPrice
				.deploy(blockConfig.node5, credentials, blockConfig.contractGasProvider, constuctorData).send();
		String contractAddress = userPriceContract.getContractAddress();
		TransactionReceipt transactionReceipt = userPriceContract.getTransactionReceipt().get();
		String message = String.format("Contract address: %s, Transaction receipt: %s", contractAddress, transactionReceipt.toString());
		return message;
	}

	// [MODIFIED] Changed variable names to camelCase. Wrapped userContract call inside try-catch to properly handle exceptions.
	public String Price_setUser() throws Exception {
		String nodeAddress = "123";
		String userAccount = "loveaoe44";
		String userPassword = "love20720";
		BigInteger accountLevel = BigInteger.valueOf(0);
		BigInteger userInitPrice = BigInteger.valueOf(50);
		String creatDate = "20250101";
		String updateDate = "20250101";
		String lastTransDate = "20250101";
		String lastTransTag = "default";
		String creatName = "Leo";
		Boolean isVal = false;
		try {
			TransactionReceipt data = userContract.setUser(nodeAddress, userAccount, userPassword, accountLevel,
					userInitPrice, creatDate, updateDate, lastTransDate, lastTransTag, creatName, isVal).send();
			return ("0x1".equals(data.getStatus())) ? "setUser Success" : "setUser fail";
		} catch (Exception e) {
			return "Node fail";
		}
	}

	// [MODIFIED] Changed variable names to camelCase. Simplified return statement.
	public String Price_getTest() throws Exception {
		RemoteCall<String> data = userContract.getTest();
		return data.send();
	}

	// [MODIFIED] Fixed typo 'NodeAddres' to 'nodeAddress' and 'arrover' to 'approve'. Converted to camelCase.
	public String Price_approveUser() {
		String nodeAddress = "123";
		String admin = "loveaoe33";
		String account = "loveaoe44";
		String approvDate = "20250102";
		Boolean isVal = true;
		try {
			TransactionReceipt data = userContract.approvUser(nodeAddress, admin, account, approvDate, isVal).send();
			return ("0x1".equals(data.getStatus())) ? "approve Success" : "approve fail";
		} catch (Exception e) {
			return "Node fail";
		}
	}

	// [MODIFIED] Changed variable names to camelCase. Fixed typo 'Sucess' to 'Success'. Removed unused TransData.
	public String Price_getUser() throws Exception {
		String nodeAddress = "123";
		String account = "loveaoe44";
		RemoteCall<String> data = userContract.getUser(nodeAddress, account);
		data.send();
		return "Success";
	}

	// [MODIFIED] Changed variable names to camelCase. Removed unused variables. Fixed typo 'Sucess'. Simplified return statement.
	public String Price_updateUser() {
		String nodeAddress = "123";
		String admin = "loveaoe33";
		String updateAccount = "loveaoe44";
		String userPword = "love30720";
		String priceRemark = "Admin Update";
		String updateDate = "20210102";
		String createName = "Leo";
		Boolean accountIsVal = false;
		try {
			TransactionReceipt data = userContract.updateUser(nodeAddress, admin, updateAccount, userPword,
					blockConfig.gasLimit, blockConfig.gasPrice, priceRemark, updateDate, createName, accountIsVal)
					.send();
			return ("0x1".equals(data.getStatus())) ? "Insert Success" : "Insert fail";
		} catch (Exception e) {
			return "Node fail";
		}
	}

	// [MODIFIED] Changed variable names to camelCase. Fixed typo 'FromAccoun'. Fixed 'Sucess' -> 'Success'.
	public String Price_transPrice() {
		String fromAccount = "loveaoe33";
		BigInteger transPrice = BigInteger.valueOf(500);
		String transAccount = "loveaoe44";
		String transRemark = "test transfer";
		String transDate = "20250101";
		try {
			TransactionReceipt data = userContract
					.transPrice(fromAccount, transPrice, transAccount, transRemark, transDate).send();
			return ("0x1".equals(data.getStatus())) ? "Transfer Success" : "Transfer fail";
		} catch (Exception e) {
			return "Node fail";
		}
	}

	// [MODIFIED] Added English comment, fixed return typo 'Sucess' -> 'Success'.
	public String Price_getTransactionRecord() { // Get block transaction event
		TransactionRecordEventResponse data = userContract.getTransactionRecordEventFromLog(null);
		return "Success";
	}

	// [MODIFIED] Added English comment, fixed return typo 'Sucess' -> 'Success'.
	public String Price_getAdminUpdateUserRecord() { // Get block update event
		return "Success";
	}

	// [MODIFIED] Fixed typo 'Sucesss' to 'Success', fixed comment to English.
	public String Price_getUserApprovLog() throws InterruptedException { // Get block register event
		Flowable<UserResgisterEventResponse> data = userContract.userResgisterEventFlowable(blockConfig.getfilter());
		data.subscribe(event -> {
			synchronized (resObject) {
				resObject.setAdminAccount(event.adminAccount);
				resObject.setApprovDate(event.approvDate);
				resObject.setResAccount(event.resAccount);
				eventService.set_InitRes(resObject);
			}
		}, throwable -> System.out.println("Error: " + throwable.getMessage()),
				() -> System.out.println("Completed the data: " + eventService.get_AllRes()));
		return "Success";
	}

	// [MODIFIED] Fixed typo 'Sucesss' to 'Success', fixed comment to English.
	public String Price_getTransactionRecordLog() throws InterruptedException { // Get transaction record log event
		Flowable<TransactionRecordEventResponse> data = userContract
				.transactionRecordEventFlowable(blockConfig.getfilter());
		data.subscribe(event -> {
			synchronized (transactionObject) {
				transactionObject.setFromAccount(event.fromAccount);
				transactionObject.setToAccount(event.toAccount);
				transactionObject.setPrice(event.price.intValue());
				transactionObject.setTransRemark(event.transRemark);
				transactionObject.setTransDate(event.transDate);
				eventService.set_AddTrans(transactionObject);
			}
		}, throwable -> System.out.println("Error: " + throwable.getMessage()), () -> System.out.println("Completed"));
		return "Success";
	}

	// [MODIFIED] Removed unused 'filter'. Fixed typo 'Sucesss' to 'Success', fixed comment to English.
	public String Price_getAdminUpdateLog() throws InterruptedException { // Get admin update log event
		Flowable<AdminUpdateUserRecodeEventResponse> data = userContract
				.adminUpdateUserRecodeEventFlowable(blockConfig.getfilter());
		data.subscribe(event -> {
			synchronized (adminData) {
				adminData.setUpdateAccount(event.updateAccount);
				adminData.setOldData(event.oldData);
				adminData.setUpdateAdmin(event.updateAdmin);
				adminData.setUpdateData(event.updateData);
				eventService.set_InitAdmin(adminData);
			}
		}, throwable -> System.out.println("Error: " + throwable.getMessage()), () -> System.out.println("Completed"));
		return "Success";
	}

}
