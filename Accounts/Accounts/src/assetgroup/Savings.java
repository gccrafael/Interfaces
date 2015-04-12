package assetgroup;

/**
 *
 * @author Rafael
 */
public class Savings extends AssetAccount {
    public static final String TypeCd = "SV";
    public static final String TypeCdDesc = "Savings";
    
    public Savings(String name, double initdeposit) {
        super(Savings.TypeCd, name, initdeposit);
    }
    
    public Savings(int acctno) {
        super(Savings.TypeCd, acctno);        
    }
    
    @Override
    public String getAcctTypeCd() {
        return Savings.TypeCd;
      
    }
    @Override
    public String getAcctTypeDesc() {
        return Savings.TypeCdDesc;
    }
}
