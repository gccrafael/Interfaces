package creditgroup;

/**
 *
 * @author Rafael
 */
public class CreditCard extends CreditAccount{
    public static final String TypeCd = "CC";
    public static final String TypeDesc = "Credit Card";
    
    public CreditCard(String name, double climit) {
        super(CreditCard.TypeCd, name, climit);
    }
    
    public CreditCard(int acctno) {
        super(CreditCard.TypeCd, acctno);        
    }
    
    @Override
    public String getAcctTypeCd() {
        return CreditCard.TypeCd;
    }
    
    @Override
    public String getAcctTypeDesc() {
        return CreditCard.TypeDesc;
    }
}
