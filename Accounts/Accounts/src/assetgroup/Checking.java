
package assetgroup;

/**
 *
 * @author Rafael
 */
public class Checking extends AssetAccount {
    public static final String TypeCd = "CK";
    public static final String TypeCdDesc = "Checking";
    
    public Checking(String name, double initdeposit) {
        super(Checking.TypeCd, name, initdeposit);
    }
    
    public Checking(int acctno) {
        super(Checking.TypeCd, acctno);        
    }
    
    @Override
    public String getAcctTypeCd() {
        return Checking.TypeCd;
    }
    @Override
    public String getAcctTypeDesc() {
        return Checking.TypeCdDesc;
    }
    @Override
    public void setIntAction(String tc, double intrate) {        
        String msg = "Interest request on non-interest account.";
        super.setTypeCd(tc);
        super.setActionMsg(msg);
        super.writelog(msg);
    }

    public void setPayment(double amount) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
  
   
    
}
