package BlockChainObject;

import java.util.ArrayList;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
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

	public void set_InitRes(ResObject data) throws JsonProcessingException {
		String jsonString=objectMapper.writeValueAsString(data);
		resData.initArrayDaya(jsonString);
	}

	public void set_InitTrans(TransactionObject data) throws JsonProcessingException {
		String jsonString=objectMapper.writeValueAsString(data);
		transData.initArrayDaya(null);
	}

	public void set_InitAdmin(AdminUpdateObject data) throws JsonProcessingException {
		String jsonString=objectMapper.writeValueAsString(data);
		updateData.initArrayDaya(null);
	}

	public void set_AddRes(ResObject data) throws JsonProcessingException {
		String jsonString=objectMapper.writeValueAsString(data);
		resData.setArrayData(null);
	}

	public void set_AddTrans(TransactionObject data) throws JsonProcessingException {
		String jsonString=objectMapper.writeValueAsString(data);
		transData.setArrayData(null);
	}

	public void set_AddAdmin(AdminUpdateObject data) throws JsonProcessingException {
		String jsonString=objectMapper.writeValueAsString(data);
		updateData.setArrayData(null);
	}

	public ArrayList<String> get_AllRes() {
		return resData.getArrayData();
	}

	public  ArrayList<String> get_AllTrans() {
		return transData.getArrayData();
	}

	public  ArrayList<String> get_AllAdmin() {
		return updateData.getArrayData();
	}

}
