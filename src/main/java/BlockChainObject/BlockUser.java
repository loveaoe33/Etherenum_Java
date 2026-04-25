package BlockChainObject;

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
public class BlockUser {
	// [MODIFIED] Changed fields from public to private for proper encapsulation.
	// Removed unused imports. Fixed typo in variable name 'accountIsVial' to 'accountIsVal'.
    private String userAccount;
    private String userPword;
    private int accountLevel;
    private int accountPrice;
    private String createDate;
    private String updateDate;
    private String lastTransDate;
    private String lastTransTag;
    private String createName;
    private Boolean accountIsVal;

}
