package BlockChainObject;

import java.math.BigInteger;

import org.springframework.context.annotation.Scope;
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
@Scope("prototype")//new class
public class ResObject {
    String resAccount;
    String adminAccount;
    String approvDate;
}
