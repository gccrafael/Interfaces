package assetgroup;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Calendar;

/**
 *
 * @author rafa
 */
public class MoneyMarket extends AssetAccount
{
    public static final String TypeCd = "MM";
    public static final String TypeDesc = "Money Market";
    private String actmsg, errmsg;
    
    public MoneyMarket(String name, double initdeposit) {
        super(MoneyMarket.TypeCd, name, initdeposit);
    }
    
    public MoneyMarket(int acctno) {
        super(MoneyMarket.TypeCd, acctno);        
    }
        
    @Override
    public String getAcctTypeCd() {
        return MoneyMarket.TypeCd;
    }

    @Override
    public String getAcctTypeDesc() {
        return MoneyMarket.TypeDesc;
    }
    
    public void writeLogWithd(String tc, int acctno) {
        try {
            Calendar cal = Calendar.getInstance();
            DateFormat df = DateFormat.getDateTimeInstance();
            String ts = df.format(cal.getTime());
            PrintWriter out = new PrintWriter(
            new FileWriter("W" + tc + "L" + acctno + ".txt",true));
            out.println("Limit of 3 withdrawals per month exceeded." + "\t" + ts);
            out.println("Charge of: $25.00");
            out.close();
        } catch (IOException e) {
            this.errmsg = "Error writing log file: " + e.getMessage();
        } catch (Exception e) {
            this.errmsg = "General error in write log: " + e.getMessage();
        }
    } //end of writeLogWithd
    
    public void writeStatusWithd(String tc, int acctno) {        
        try {
            PrintWriter out = new PrintWriter(
                              new FileWriter(
                              this.TypeCd + acctno + ".txt"));
            out.println(super.getAcctName());
            out.println(super.getBalance() - 25);
            out.close();
            super.setBalance(super.getBalance() - 25);
        } catch (IOException e) {
            this.errmsg =
                "Error writing status file for: " + acctno;
        } catch (Exception e) {
            this.errmsg =
                "General error on " + acctno + ": " + e.getMessage();
        }  
    }
    
    public String getErrMsglogMM() {
        return this.errmsg;
    }
    
    public String getActionMsgMM() {
        return this.actmsg;
    }
    
    public double getBalanceMM() {
        return super.getBalance();
    }    
}
