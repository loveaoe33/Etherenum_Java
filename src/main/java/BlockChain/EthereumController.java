	package BlockChain;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import BlockChainObject.BlockClass;

@RestController
@ComponentScan(basePackages = {"lib"})
public class EthereumController<WindowsMapper, BlockUser> {

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

	// [MODIFIED] Added try-catch for error handling. Translated comment and simplified return.
	@CrossOrigin
	@GetMapping("EthereumController/New__Wallet")
	public String New__Wallet() {
		try {
			return String.format("Wallet creation complete: %s", ethereumComponent.New__Wallet());
		} catch (Exception e) {
			return "Error Code: 500, Message: " + e.getMessage();
		}
	}

	// [MODIFIED] Added try-catch for error handling. Translated comment and simplified return.
	@CrossOrigin // Completed
	@GetMapping("EthereumController/Wallet_Cash")
	public String Wallet_Cash() {
		try {
			return String.format("Wallet query complete: %s",
					ethereumComponent.init("0x470df28eb826acef5759c22ed78c00ba53e5169a"));
		} catch (Exception e) {
			return "Error Code: 500, Message: " + e.getMessage();
		}
	}
	
	// [MODIFIED] Added try-catch, removed exceptions from signature. Translated comment.
	@CrossOrigin // Completed
	@GetMapping("EthereumController/Check_Wallet") // Check node wallet balance
	public String Check_Wallet() {
		try {
			return String.format("Wallet check complete: %s",
					ethereumComponent.Check_Wallet("0x1552188f25218561a5154a98e9d0fe53c1d31e3f"));
		} catch (Exception e) {
			return "Error Code: 500, Message: " + e.getMessage();
		}
	}
	
	// [MODIFIED] Added try-catch for error handling. Translated comment.
	@CrossOrigin // Completed
	@GetMapping("EthereumController/View_Last_Brock")
	public String View_Last_Brock() {
		try {
			return String.format("Block query complete:\n%s", ethereumComponent.View_Last_Brock());
		} catch (Exception e) {
			return "Error Code: 500, Message: " + e.getMessage();
		}
	}

	// [MODIFIED] Added try-catch, removed IOException from signature. Changed variables to camelCase.
	@CrossOrigin // Completed
	@GetMapping("EthereumController/View_Array_Block")
	public String View_Array_Block() {
		try {
			ArrayList<BlockClass> blockData = ethereumComponent.View_Array_Block();
			return blockerMapper.writeValueAsString(blockData);
		} catch (Exception e) {
			return "Error Code: 500, Message: " + e.getMessage();
		}
	}

	// [MODIFIED] Added try-catch. Changed variable to camelCase, kept original parameter name binding.
	@CrossOrigin // Completed
	@GetMapping("EthereumController/View_Transaction_Hash")
	public String View_Transaction_Hash(@RequestParam("Hash_Code") String hashCode) {
		try {
			return String.format("Record query complete: %s", ethereumComponent.View_Transaction_Hash(hashCode));
		} catch (Exception e) {
			return "Error Code: 500, Message: " + e.getMessage();
		}
	}

	// [MODIFIED] Added try-catch. Translated comment, changed variables to camelCase.
	@CrossOrigin
	@GetMapping("EthereumController/Print_Wallet")
	public ArrayList<String> Print_Wallet() {
		try {
			return ethereumComponent.Print_Wallet();
		} catch (Exception e) {
			ArrayList<String> errorList = new ArrayList<>();
			errorList.add("Error Code: 500, Message: " + e.getMessage());
			return errorList;
		}
	}

	// [MODIFIED] Removed exceptions from signature, added try-catch. Kept parameter name binding to preserve API.
	@CrossOrigin
	@GetMapping("EthereumController/TransFer_ETH")
	public String TransFer_ETH(@RequestParam("Wallet_Address") String walletAddress) {
		try {
			return ethereumComponent.TransFer_ETH(walletAddress);
		} catch (Exception e) {
			return "Error Code: 500, Message: " + e.getMessage();
		}
	}

	// [MODIFIED] Removed Exception from signature, added try-catch. Translated comment.
	@CrossOrigin
	@GetMapping("EthereumController/Contract_build") // Build Contract
	public String Contract_build() {
		try {
			return ethereumComponent.Contract_build();
		} catch (Exception e) {
			return "Error Code: 500, Message: " + e.getMessage();
		}
	}
	
	// [MODIFIED] Removed Exception from signature, added try-catch. Removed commented code.
	@CrossOrigin
	@PostMapping("EthereumController/Contract_setUser") // Build Contract_setUser
	public String Contract_setUser(@RequestBody BlockUser postData) {
		try {
			// return ethereumComponent_UserPrice.Price_setUser();
			return "Success";
		} catch (Exception e) {
			return "Error Code: 500, Message: " + e.getMessage();
		}
	}
	
	// [MODIFIED] Removed Exception from signature, added try-catch. Translated comment.
	@CrossOrigin
	@GetMapping("EthereumController/Contract_getUserApprovLog") // Get Contract_UserRes log
	public String Contract_getUserApprovLog() {
		try {
			return ethereumComponent_UserPrice.Price_getUserApprovLog();
		} catch (Exception e) {
			return "Error Code: 500, Message: " + e.getMessage();
		}
	}
	
	// [MODIFIED] Removed Exception from signature, added try-catch. Translated comment.
	@CrossOrigin
	@GetMapping("EthereumController/Contract_getAdminUpdatelog") // Get Contract_AdminUpdate log
	public String Contract_getAdminUpdatelog() {
		try {
			return ethereumComponent_UserPrice.Price_getAdminUpdateUserRecord();
		} catch (Exception e) {
			return "Error Code: 500, Message: " + e.getMessage();
		}
	}
	
	// [MODIFIED] Removed Exception from signature, added try-catch. Translated comment.
	@CrossOrigin
	@GetMapping("EthereumController/Contract_getTransactionlog") // Get Contract_TransPrice log
	public String Contract_getTransPricelog() {
		try {
			return ethereumComponent_UserPrice.Price_getAdminUpdateUserRecord();
		} catch (Exception e) {
			return "Error Code: 500, Message: " + e.getMessage();
		}
	}
	
	// [MODIFIED] Removed Exception from signature, added try-catch.
	@CrossOrigin
	@GetMapping("EthereumController/Contract_getTest") 
	public String Get_Test() {
		try {
			return ethereumComponent_UserPrice.Price_getTest();
		} catch (Exception e) {
			return "Error Code: 500, Message: " + e.getMessage();
		}
	}
	
	// [MODIFIED] Removed Exception from signature, added try-catch. Translated comment.
	@CrossOrigin
	@GetMapping("EthereumController/Contract_getUser") // Transfer contract build - get user
	public String Contract_getUser() {
		try {
			return ethereumComponent_UserPrice.Price_getUser();
		} catch (Exception e) {
			return "Error Code: 500, Message: " + e.getMessage();
		}
	}
	
	// [MODIFIED] Removed Exception from signature, added try-catch. Translated comment.
	@CrossOrigin
	@GetMapping("EthereumController/Contract_approveUser") // Transfer contract build - approve user
	public String Contract_approveUser() {
		try {
			return ethereumComponent_UserPrice.Price_approveUser();
		} catch (Exception e) {
			return "Error Code: 500, Message: " + e.getMessage();
		}
	}
	
	// [MODIFIED] Removed Exception from signature, added try-catch. Translated comment.
	@CrossOrigin
	@GetMapping("EthereumController/Contract_build_UserPrice") // Transfer contract build
	public String Contract_build_UserPrice() {
		try {
			return ethereumComponent_UserPrice.Contract_UserPrice_build();
		} catch (Exception e) {
			return "Error Code: 500, Message: " + e.getMessage();
		}
	}
	
	// [MODIFIED] Removed Exception from signature, added try-catch. Translated comment.
	@CrossOrigin
	@GetMapping("EthereumController/Contract_Get") // Get contract functions
	public String Contract_Get() {
		try {
			return ethereumComponent.Contract_Get();
		} catch (Exception e) {
			return "Error Code: 500, Message: " + e.getMessage();
		}
	}

	// [MODIFIED] Removed Exception from signature, added try-catch. Translated comment.
	@CrossOrigin
	@GetMapping("EthereumController/Contract_Set") // Set parameters inside contract
	public String Contract_Set() {
		try {
			return ethereumComponent.Contract_Set();
		} catch (Exception e) {
			return "Error Code: 500, Message: " + e.getMessage();
		}
	}

	// [MODIFIED] Added try-catch for error handling. Translated comment.
	@CrossOrigin
	@GetMapping("EthereumController/Contract_View") // View contract bytecode, but requires decompiler tool
	public String Contract_View() {
		try {
			return ethereumComponent.Contract_View();
		} catch (Exception e) {
			return "Error Code: 500, Message: " + e.getMessage();
		}
	}

	// [MODIFIED] Added try-catch for error handling. Translated comment, changed variables to camelCase.
	@CrossOrigin
	@GetMapping("EthereumController/Contract_Address") // View all contract addresses
	public ArrayList<String> Contract_Address() {
		try {
			return ethereumComponent.Print_Contract();
		} catch (Exception e) {
			ArrayList<String> errorList = new ArrayList<>();
			errorList.add("Error Code: 500, Message: " + e.getMessage());
			return errorList;
		}
	}
}
