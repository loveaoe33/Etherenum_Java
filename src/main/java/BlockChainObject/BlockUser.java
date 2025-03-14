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
public class BlockUser {
    public String userAccount;
    public String userPword;
    public int accountLevel;
    public int accountPrice;
    public String createDate;
    public String updateDate;
    public String lastTransDate;
    public String lastTransTag;
    public String createName;
    public Boolean accountIsVial;

}
