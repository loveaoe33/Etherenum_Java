package BlockChainObject;

import java.math.BigInteger;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockClass {
	public String Transaction;
	public String From;
	public String To;
	public BigInteger Value;
	public BigInteger Gas;
	public BigInteger Limit;
	public static BlockClass blockClass;
	public ArrayList<BlockClass> BlockList=new ArrayList();
	
   
	public BlockClass setTempData(BlockClass blockClass) {
		BlockList.add(blockClass);
		return blockClass;
	}
	
	public BlockClass getTempData(BlockClass blockClass) {
		
		return blockClass;
	}
	
	public void clearTempData() {
		BlockList.clear();
	}
}
