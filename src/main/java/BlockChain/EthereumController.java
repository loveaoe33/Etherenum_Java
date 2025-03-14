package BlockChain;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.CipherException;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.fasterxml.jackson.databind.ObjectMapper;
import BlockChainObject.BlockClass;
import lib.UserPrice.Users;
import net.sf.json.JSON;
import net.sf.json.groovy.GJson;

@RestController
@ComponentScan(basePackages = {"lib"})
public class EthereumController<WindowsMapper> {

	@Autowired
	private final EthereumComponent ethereumComponent;
	private final EthereumComponent_UserPrice ethereumComponent_UserPrice;
    private ObjectMapper blockerMapper=new ObjectMapper();
	public EthereumController(EthereumComponent ethereumComponent,EthereumComponent_UserPrice ethereumComponent_UserPrice) {
		this.ethereumComponent = ethereumComponent;
		this.ethereumComponent_UserPrice=ethereumComponent_UserPrice;
	}

	@CrossOrigin
	@GetMapping("EthereumController/test")
	public String test() {
		return "123";
	}

	@CrossOrigin
	@GetMapping("EthereumController/New__Wallet")
	public String New__Wallet() throws InvalidAlgorithmParameterException {
		String Message = String.format("錢包建立完成:%s", ethereumComponent.New__Wallet());
		return Message;

	}

	@CrossOrigin  //完成
	@GetMapping("EthereumController/Wallet_Cash")
	public String Wallet_Cash() {
		String Message = String.format("錢包查詢完成:%s",
				ethereumComponent.init("0x470df28eb826acef5759c22ed78c00ba53e5169a"));
		return Message;

	}
	
	@CrossOrigin  //完成
	@GetMapping("EthereumController/Check_Wallet")  //確認節點錢包餘額
	public String Check_Wallet() throws InterruptedException, ExecutionException {
		String Message = String.format("錢包確認完成:%s",
				ethereumComponent.Check_Wallet("0x1552188f25218561a5154a98e9d0fe53c1d31e3f"));
		return Message;

	}
	



	@CrossOrigin  //完成
	@GetMapping("EthereumController/View_Last_Brock")
	public String View_Last_Brock() {
		String Message = String.format("區塊調閱完成:\n%s", ethereumComponent.View_Last_Brock());
		System.out.println(Message);
		return Message;

	}

	@CrossOrigin   //完成
	@GetMapping("EthereumController/View_Array_Block")
	public String View_Array_Block() throws IOException {
		ArrayList<BlockClass> Block_Date = new ArrayList<BlockClass>();
		Block_Date = ethereumComponent.View_Array_Block();
		String Result=blockerMapper.writeValueAsString(Block_Date);

		return Result;

	}

	@CrossOrigin   //完成
	@GetMapping("EthereumController/View_Transaction_Hash")
	public String View_Transaction_Hash(@RequestParam String Hash_Code) {
		String Message = String.format("紀錄調閱完成:%s", ethereumComponent.View_Transaction_Hash(Hash_Code));
		return Message;

	}

	@CrossOrigin
	@GetMapping("EthereumController/Print_Wallet")
	public ArrayList<String> Print_Wallet() {
		ArrayList<String> Block_Date = new ArrayList<String>();
		Block_Date = ethereumComponent.Print_Wallet();
		return Block_Date;

	}

	@CrossOrigin
	@GetMapping("EthereumController/TransFer_ETH")
	public String TransFer_ETH(@RequestParam String Wallet_Address)
			throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException,
			CipherException, IOException, InterruptedException, ExecutionException {
		String Message = ethereumComponent.TransFer_ETH(Wallet_Address);
		return Message;
	}

	@CrossOrigin
	@GetMapping("EthereumController/Contract_build") //build Contract
	public String Contract_build() throws Exception {
		String Message = ethereumComponent.Contract_build();
		return Message;

	}
	
	@CrossOrigin
	@GetMapping("EthereumController/Contract_setUser") //build Contract_setUser
	public String Contract_setUser() throws Exception {
		String Message = ethereumComponent_UserPrice.Price_setUser();
		return Message;

	}
	
	@CrossOrigin
	@GetMapping("EthereumController/Contract_getUserApprovLog") //get Contract_UserRes log
	public String Contract_getUserApprovLog() throws Exception {
		String Message = ethereumComponent_UserPrice.Price_getUserApprovLog();
		return Message;

	}
	
	@CrossOrigin
	@GetMapping("EthereumController/Contract_getAdminUpdatelog") //get Contract_AdminUpdate log
	public String Contract_getAdminUpdatelog() throws Exception {
		String Message = ethereumComponent_UserPrice.Price_getAdminUpdateUserRecord();
		return Message;

	}
	
	
	@CrossOrigin
	@GetMapping("EthereumController/Contract_getTransactionlog") //get Contract_TransPrice log
	public String Contract_getTransPricelog() throws Exception {
		String Message = ethereumComponent_UserPrice.Price_getAdminUpdateUserRecord();
		return Message;

	}
	
	
	@CrossOrigin
	@GetMapping("EthereumController/Contract_getTest") 
	public String Get_Test() throws Exception {
		String Message = ethereumComponent_UserPrice.Price_getTest();
		return Message;
		
	}
	

	
	@CrossOrigin
	@GetMapping("EthereumController/Contract_getUser") //轉帳合約建立_取得使用者
	public String Contract_getUser() throws Exception {
		String Message = ethereumComponent_UserPrice.Price_getUser();
		return Message;

	}
	
	@CrossOrigin
	@GetMapping("EthereumController/Contract_approveUser") //轉帳合約建立_取得使用者
	public String Contract_approveUser() throws Exception {
		String Message = ethereumComponent_UserPrice.Price_approveUser();
		return Message;

	}
	
	
	
	
	@CrossOrigin
	@GetMapping("EthereumController/Contract_build_UserPrice") //轉帳合約建立
	public String Contract_build_UserPrice() throws Exception {
		String Message = ethereumComponent_UserPrice.Contract_UserPrice_build();
		return Message;

	}
	
	
	

	@CrossOrigin
	@GetMapping("EthereumController/Contract_Get") //取得合約內功能
	public String Contract_Get() throws Exception {

		String Message = ethereumComponent.Contract_Get();

		return Message;

	}

	@CrossOrigin
	@GetMapping("EthereumController/Contract_Set")  //設置合約內參數
	public String Contract_Set() throws Exception {
		String Message = ethereumComponent.Contract_Set();

		return Message;

	}

	@CrossOrigin
	@GetMapping("EthereumController/Contract_View")   //看見合約字節碼，但需要反編譯工具
	public String Contract_View() {
		String Message = ethereumComponent.Contract_View();
		return Message;

	}

	@CrossOrigin
	@GetMapping("EthereumController/Contract_Address")  //看見所有合約地址
	public ArrayList<String> Contract_Address() {
		ArrayList<String> Contract_Data = new ArrayList<String>();
		Contract_Data = ethereumComponent.Print_Contract();
		return Contract_Data;

	}
	
	

}
