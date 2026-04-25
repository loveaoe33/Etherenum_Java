package BlockChainObject;

import java.math.BigInteger;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
	
	// [MODIFIED] Changed to private for encapsulation. Applied camelCase naming convention.
	// Added @JsonProperty to ensure JSON output remains compatible with the frontend.
	@JsonProperty("Transaction")
	private String transaction;
	
	@JsonProperty("From")
	private String from;
	
	@JsonProperty("To")
	private String to;
	
	@JsonProperty("Value")
	private BigInteger value;
	
	@JsonProperty("Gas")
	private BigInteger gas;
	
	@JsonProperty("Limit")
	private BigInteger limit;
	
	// [MODIFIED] Removed unused static instance as it is an anti-pattern in a POJO / Spring Component.
	// public static BlockClass blockClass;
	
	// [MODIFIED] Changed to private, applied camelCase, and added missing generic diamond operator <>.
	private ArrayList<BlockClass> blockList = new ArrayList<>();
	
	// [MODIFIED] Added try-catch and null check for safety. Translated comment.
	// Set temporary data
	public BlockClass setTempData(BlockClass blockClass) {
		try {
			if (blockClass != null && this.blockList != null) {
				this.blockList.add(blockClass);
			}
		} catch (Exception e) {
			System.out.println("Error Code: 500, Message: " + e.getMessage());
		}
		return blockClass;
	}
	
	// [MODIFIED] Translated comment. (Note: this method simply returns the input itself).
	// Get temporary data
	public BlockClass getTempData(BlockClass blockClass) {
		return blockClass;
	}
	
	// [MODIFIED] Added try-catch and null check for safety. Translated comment.
	// Clear temporary data
	public void clearTempData() {
		try {
			if (this.blockList != null) {
				this.blockList.clear();
			}
		} catch (Exception e) {
			System.out.println("Error Code: 500, Message: " + e.getMessage());
		}
	}
}
