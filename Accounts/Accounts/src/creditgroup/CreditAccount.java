package creditgroup;

import acctinterface.Account;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Rafael
 */
public abstract class CreditAccount implements Account {
    private String acctname, typecd;
    private int AcctNo;
    private double baldue, climit;
    private String actmsg, errmsg;
    NumberFormat curr = NumberFormat.getCurrencyInstance();
    
    public CreditAccount(String typecd, String name, double startlimit) {
        this.AcctNo = 0;
        this.actmsg = "";
        this.errmsg = "";
        
        while (this.AcctNo == 0) {
            try {
                this.AcctNo = (int) (Math.random() * 1000000);
                BufferedReader in = new BufferedReader(
                                    new FileReader(
                                     typecd + "CC" + this.AcctNo + ".txt"));
                in.close();
                this.AcctNo = 0;
            } catch (IOException e) {                
                this.baldue = 0;
                this.climit = startlimit;
                this.typecd = typecd;
                this.acctname = name;
                writestatus();
                if (this.errmsg.isEmpty()) {
                   this.actmsg = typecd + " Account " + this.AcctNo + " opened.";
                   writelog(this.actmsg);
                }   
                if (!this.errmsg.isEmpty()) {
                    this.climit = 0;
                    this.AcctNo = -1;
                }
            } catch (Exception e) {
                this.errmsg = "Fatal error in constructor: " + e.getMessage();
                this.AcctNo = -1;
            }
        }        
    } //end of constructor
    public CreditAccount(String tc, int acct) {
        this.errmsg = "";
        this.actmsg = "";
        this.climit = 0;
        this.baldue = 0;
        this.AcctNo = acct;
        this.typecd = tc;
        
        try {
            BufferedReader in = new BufferedReader(
                                new FileReader(tc + this.AcctNo + ".txt"));
            this.acctname = in.readLine();
            this.climit = Double.parseDouble(in.readLine());
            this.baldue = Double.parseDouble(in.readLine());
            in.close();
            this.actmsg = "Account " + acct + " re-opened.";
        } catch (Exception e) {
            this.errmsg = "Error re-opening account: " + e.getMessage();
            this.AcctNo = -1;
        }
    }
    private void writestatus() {
        try {
            PrintWriter out = new PrintWriter(
                              new FileWriter(
                              this.typecd + this.AcctNo + ".txt"));
            out.println(this.acctname);
            out.println(this.climit);
            out.println(this.baldue);
            out.close();
        } catch (IOException e) {
            this.errmsg =
                "Error writing status file for: " + this.AcctNo;
        } catch (Exception e) {
            this.errmsg =
                "General error on " + this.AcctNo + ": " + e.getMessage();
        }  
    }
    protected void writelog(String msg) {
        try {
            Calendar cal = Calendar.getInstance();
            DateFormat df = DateFormat.getDateTimeInstance();
            String ts = df.format(cal.getTime());
            PrintWriter out = new PrintWriter(
            new FileWriter(this.typecd + "L" + this.AcctNo + ".txt",true));
            out.println(msg + "\t  " + ts);
            out.close();
        } catch (IOException e) {
            this.errmsg = "Error writing log file: " + e.getMessage();
        } catch (Exception e) {
            this.errmsg = "General error in write log: " + e.getMessage();
        }
    } //end of writelog
    @Override
    public int getAcctNo() {
        return this.AcctNo;
    }
    @Override
    public String getAcctName() {
        return this.acctname;
    }
    @Override
    public double getBalance() {
        return this.baldue;
    }
    @Override
    public void setCharge(String tc, String descrip, double amount) {
        this.errmsg = "";
        this.actmsg = "";
        this.typecd = tc;
        NumberFormat amt = NumberFormat.getCurrencyInstance();
        
        if (this.AcctNo <= 0) {
            this.errmsg = "Charge attempt on non-active account.";
            return;
        }
        
        if (amount <= 0) {
           this.actmsg = "Charge of: " + amt.format(amount) + " for " + descrip +
                    " declined - illegal amount.  ";
           writelog(this.actmsg);
        } else if((this.baldue + amount) > this.climit) {
           this.actmsg = "Charge of: " + amt.format(amount) + " for " + descrip +
                    " declined - over limit!  "; 
           writelog(this.actmsg);
        } else {
           this.baldue += amount;
           writestatus();
           if (this.errmsg.isEmpty()) {
               this.actmsg = "Charge of: " + amt.format(amount) + " for " + descrip + "  ";
               writelog(this.actmsg);
           }
        }
    }
    @Override
    public void setPayment (String tc, double amount) {
        this.errmsg = "";
        this.actmsg = "";
        this.typecd = tc;
        NumberFormat amt = NumberFormat.getCurrencyInstance();
        
        if (this.AcctNo <= 0) {
            this.errmsg = "Payment attempt on non-active account.";
            return;
        }
        
        if (amount <= 0) {
            this.actmsg = "Payment of: " + amt.format(amount) + " declined - illegal amount.  ";
            writelog(this.actmsg); 
        } else {
            this.baldue -= amount;
            writestatus();
            if (this.errmsg.isEmpty()) {
                this.actmsg = "Payment of: " + amt.format(amount);
                writelog(this.actmsg);
            }
        }
    }
    @Override
    public void setIntAction(String tc, double intrate) {
       this.errmsg = "";
       this.actmsg = "";
       this.typecd = tc;
       double intchg;
       NumberFormat p = NumberFormat.getPercentInstance();
       p.setMaximumFractionDigits(3);
       
       if (this.AcctNo <= 0) {
           this.errmsg = "Interest Charge attempt on non-active account.";
           return;
       }
       
       if (intrate <= 0) {
           this.actmsg = "Interest rate of: " + p.format(intrate) + " declined - illegal amount.  ";
           writelog(this.actmsg); 
       } else {
           intchg = this.baldue * intrate/12.00;
           this.baldue += intchg;
           writestatus();
           this.actmsg = "Interest charged of: " + curr.format(intchg) + "  ";
           writelog(this.actmsg);
           if (!this.errmsg.isEmpty()) {
               this.actmsg = "";
           }
           
       } 
    }
    @Override
    public ArrayList<String> getLog() {
        ArrayList<String> h = new ArrayList<>();
       this.errmsg = "";
       this.actmsg = "";
       String t;
       
       if (this.AcctNo <= 0) {
            this.errmsg = "Charge attempt on non-active account.";
            return h;
        }
       
       try {
           BufferedReader in = new BufferedReader(
                               new FileReader(this.typecd + "L" + this.AcctNo + ".txt"));
           t = in.readLine();
           
           while (t != null) {
              h.add(t);
              t = in.readLine();
           }
           in.close();
           this.actmsg = "History returned for account: " + this.AcctNo;
       } catch (Exception e) {
           this.errmsg = "Error reading log file: " + e.getMessage();
       }
        return h;
    }
    
    public String getErrMsg() {
        return this.errmsg;
    }
    public String getActionMsg() {
        return this.actmsg;
    }
    protected void setActionMsg(String msg) {
        this.actmsg = msg;
    }
    public double getCreditLimit() {
        return this.climit;
    }
    protected void setCreditLimit(double creditlimit) {
        this.climit = creditlimit;
        writestatus();
    }
    public double getAvailableCredit() {
        return (this.climit - this.baldue);
    }
    @Override
    public abstract String getAcctTypeCd();
    @Override
    public abstract String getAcctTypeDesc();    
}
