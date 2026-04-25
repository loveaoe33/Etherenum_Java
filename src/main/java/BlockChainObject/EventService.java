package BlockChainObject;

import java.util.ArrayList;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@ComponentScan(basePackages = { "lib" })
public class EventService {
	private TransactionEventObject transData;
	private ResEventObject resData;
	private AdminUpdateEventObject updateData;
    private ObjectMapper objectMapper;



	public EventService(TransactionEventObject transactionEventObject, ResEventObject resEventObject, AdminUpdateEventObject adminUpdateEventObject) {
		this.transData = transactionEventObject;
		this.resData = resEventObject;
		this.updateData=adminUpdateEventObject;
		this.objectMapper=new ObjectMapper();
	}

	// [MODIFIED] Added try-catch for error handling. Removed throws JsonProcessingException.
	// Initialize response event data
	public void set_InitRes(ResObject data) {
		try {
			String jsonString = objectMapper.writeValueAsString(data);
			resData.initArrayDaya(jsonString);
		} catch (Exception e) {
			System.out.println("Error Code: 500, Message: " + e.getMessage());
		}
	}

	// [MODIFIED] Added try-catch for error handling. Removed throws JsonProcessingException.
	// Initialize transaction event data
	public void set_InitTrans(TransactionObject data) {
		try {
			String jsonString = objectMapper.writeValueAsString(data);
			transData.initArrayDaya(jsonString);
		} catch (Exception e) {
			System.out.println("Error Code: 500, Message: " + e.getMessage());
		}
	}

	// [MODIFIED] Added try-catch for error handling. Removed throws JsonProcessingException.
	// Initialize admin update event data
	public void set_InitAdmin(AdminUpdateObject data) {
		try {
			String jsonString = objectMapper.writeValueAsString(data);
			updateData.initArrayDaya(jsonString);
		} catch (Exception e) {
			System.out.println("Error Code: 500, Message: " + e.getMessage());
		}
	}

	// [MODIFIED] Added try-catch for error handling. Removed throws JsonProcessingException.
	// Add response event data
	public void set_AddRes(ResObject data) {
		try {
			String jsonString = objectMapper.writeValueAsString(data);
			resData.setArrayData(jsonString);
		} catch (Exception e) {
			System.out.println("Error Code: 500, Message: " + e.getMessage());
		}
	}

	// [MODIFIED] Added try-catch for error handling. Removed throws JsonProcessingException.
	// Add transaction event data
	public void set_AddTrans(TransactionObject data) {
		try {
			String jsonString = objectMapper.writeValueAsString(data);
			transData.setArrayData(jsonString);
		} catch (Exception e) {
			System.out.println("Error Code: 500, Message: " + e.getMessage());
		}
	}

	// [MODIFIED] Added try-catch for error handling. Removed throws JsonProcessingException.
	// Add admin update event data
	public void set_AddAdmin(AdminUpdateObject data) {
		try {
			String jsonString = objectMapper.writeValueAsString(data);
			updateData.setArrayData(jsonString);
		} catch (Exception e) {
			System.out.println("Error Code: 500, Message: " + e.getMessage());
		}
	}

	// [MODIFIED] Added English comments.
	// Get all response event data
	public ArrayList<String> get_AllRes() {
		return resData.getArrayData();
	}

	// [MODIFIED] Added English comments.
	// Get all transaction event data
	public  ArrayList<String> get_AllTrans() {
		return transData.getArrayData();
	}

	// [MODIFIED] Added English comments.
	// Get all admin update event data
	public  ArrayList<String> get_AllAdmin() {
		return updateData.getArrayData();
	}

}
