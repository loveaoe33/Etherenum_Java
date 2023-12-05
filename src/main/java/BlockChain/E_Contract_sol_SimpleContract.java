package BlockChain;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.6.0.
 */
public class E_Contract_sol_SimpleContract extends Contract {
    private static final String BINARY = "6060604052606060405190810160405280606060405190810160405280602a81526020017f307832303930663731383937326638626632383866333261373161313064636181526020017f63633561356264383137000000000000000000000000000000000000000000008152508152602001606060405190810160405280602a81526020017f307834373064663238656238323661636566353735396332326564373863303081526020017f62613533653531363961000000000000000000000000000000000000000000008152508152602001606060405190810160405280602a81526020017f307837653639323535386362313963323161393161653539636538356665613081526020017f3833323330393832303100000000000000000000000000000000000000000000815250815250600190600361014492919061015d565b50341561015057600080fd5b600c6000819055506102d6565b8280548282559060005260206000209081019282156101ac579160200282015b828111156101ab57825182908051906020019061019b9291906101bd565b509160200191906001019061017d565b5b5090506101b9919061023d565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106101fe57805160ff191683800117855561022c565b8280016001018555821561022c579182015b8281111561022b578251825591602001919060010190610210565b5b5090506102399190610269565b5090565b61026691905b808211156102625760008181610259919061028e565b50600101610243565b5090565b90565b61028b91905b8082111561028757600081600090555060010161026f565b5090565b90565b50805460018160011615610100020316600290046000825580601f106102b457506102d3565b601f0160209004906000526020600020908101906102d29190610269565b5b50565b610383806102e56000396000f30060606040526000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680635b4b73a914610048578063ae55c8881461006b57600080fd5b341561005357600080fd5b6100696004808035906020019091905050610148565b005b341561007657600080fd5b6100c6600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610152565b6040518083815260200180602001828103825283818151815260200191508051906020019080838360005b8381101561010c5780820151818401526020810190506100f1565b50505050905090810190601f1680156101395780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b8060008190555050565b600061015c610343565b6101658361021a565b600260016101000a81548160ff021916908315150217905550600260019054906101000a900460ff16156101d5576000546040805190810160405280600c81526020017fe68e88e6ac8ae9809ae9818e000000000000000000000000000000000000000081525091509150610215565b60008090506040805190810160405280600f81526020017fe5b8b3e8999fe784a1e68e88e6ac8a0000000000000000000000000000000000815250915091505b915091565b600080600090505b60018054905081101561033857826040518082805190602001908083835b6020831015156102655780518252602082019150602081019050602083039250610240565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040518091039020600019166001828154811015156102a557fe5b9060005260206000209001604051808280546001816001161561010002031660029004801561030b5780601f106102e957610100808354040283529182019161030b565b820191906000526020600020905b8154815290600101906020018083116102f7575b5050915050604051809103902060001916141561032b576001915061033d565b8080600101915050610222565b600091505b50919050565b6020604051908101604052806000815250905600a165627a7a723058206f8b4f1f69971c96d2f5c58d15945efef98741ebf1cbbcff553dddf673eb32760029";

    public static final String FUNC_SETDATA = "setData";

    public static final String FUNC_GETDATA = "getData";

    @Deprecated
    protected E_Contract_sol_SimpleContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected E_Contract_sol_SimpleContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected E_Contract_sol_SimpleContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected E_Contract_sol_SimpleContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> setData(BigInteger newData) {
        final Function function = new Function(
                FUNC_SETDATA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(newData)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple2<BigInteger, String>> getData(String Wallet_Address) {
        final Function function = new Function(FUNC_GETDATA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(Wallet_Address)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple2<BigInteger, String>>(
                new Callable<Tuple2<BigInteger, String>>() {
                    @Override
                    public Tuple2<BigInteger, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<BigInteger, String>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue());
                    }
                });
    }

    public static RemoteCall<E_Contract_sol_SimpleContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(E_Contract_sol_SimpleContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<E_Contract_sol_SimpleContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(E_Contract_sol_SimpleContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<E_Contract_sol_SimpleContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(E_Contract_sol_SimpleContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<E_Contract_sol_SimpleContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(E_Contract_sol_SimpleContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static E_Contract_sol_SimpleContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new E_Contract_sol_SimpleContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static E_Contract_sol_SimpleContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new E_Contract_sol_SimpleContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static E_Contract_sol_SimpleContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new E_Contract_sol_SimpleContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static E_Contract_sol_SimpleContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new E_Contract_sol_SimpleContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }
}
