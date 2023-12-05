package BlockChain;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.CipherException;

@RestController
public class EthereumController {

	@Autowired
	private final EthereumComponent ethereumComponent;

	public EthereumController(EthereumComponent ethereumComponent) {
		this.ethereumComponent = ethereumComponent;
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
	@GetMapping("EthereumController/Check_Wallet")
	public String Check_Wallet() throws InterruptedException, ExecutionException {
		String Message = String.format("錢包確認完成:%s",
				ethereumComponent.Check_Wallet("0x1552188f25218561a5154a98e9d0fe53c1d31e3f"));
		return Message;

	}
	



	@CrossOrigin  //完成
	@GetMapping("EthereumController/View_Last_Brock")
	public String View_Last_Brock() {
		String Message = String.format("區塊調閱完成:%s", ethereumComponent.View_Last_Brock());
		return Message;

	}

	@CrossOrigin   //完成
	@GetMapping("EthereumController/View_Array_Block")
	public ArrayList<String> View_Array_Block() throws IOException {
		ArrayList<String> Block_Date = new ArrayList<String>();
		Block_Date = ethereumComponent.View_Array_Block();
		return Block_Date;

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
	@GetMapping("EthereumController/Contract_build")
	public String Contract_build() throws Exception {
		String Message = ethereumComponent.Contract_build();
		return Message;

	}

	@CrossOrigin
	@GetMapping("EthereumController/Contract_Get")
	public String Contract_Get() throws Exception {

		String Message = ethereumComponent.Contract_Get();

		return Message;

	}

	@CrossOrigin
	@GetMapping("EthereumController/Contract_Set")
	public String Contract_Set() throws Exception {
		String Message = ethereumComponent.Contract_Set();

		return Message;

	}

	@CrossOrigin
	@GetMapping("EthereumController/Contract_View")
	public String Contract_View() {
		String Message = ethereumComponent.Contract_View();
		return Message;

	}

	@CrossOrigin
	@GetMapping("EthereumController/Contract_Address")
	public ArrayList<String> Contract_Address() {
		ArrayList<String> Contract_Data = new ArrayList<String>();
		Contract_Data = ethereumComponent.Print_Contract();
		return Contract_Data;

	}
	
	

}
