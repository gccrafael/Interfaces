package acctinterface;
import java.util.*;

/**
 *
 * @author Rafael
 */
public interface Account {
    int getAcctNo();
    String getAcctName();
    String getAcctTypeCd();
    String getAcctTypeDesc();
    
    double getBalance();
    
    String getErrMsg();
    String getActionMsg();
    
    void setCharge(String tc, String description, double amount);
    void setPayment(String tc, double amount);
    void setIntAction(String tc, double rate);
    ArrayList<String> getLog();    
}
