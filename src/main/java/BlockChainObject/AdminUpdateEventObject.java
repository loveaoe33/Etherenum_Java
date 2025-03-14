package BlockChainObject;

import java.math.BigInteger;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AdminUpdateEventObject implements  EmitEvent_Interface{

	ArrayList<String> evenData=new ArrayList<String>();
	@Override
	public String initArrayDaya(String data) {
		// TODO Auto-generated method stub
		evenData.add(data);
		return null;
	}

	@Override
	public ArrayList<String> getArrayData() {
		// TODO Auto-generated method stub
		return evenData;
	}

	@Override
	public String setArrayData(String data) {
		// TODO Auto-generated method stub
		evenData.add(data);
		return null;
	}

	@Override
	public String getOnEvendata(String data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String transJsonString(String data) {
		// TODO Auto-generated method stub
		return null;
	}

}
