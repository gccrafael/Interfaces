package creditgroup;

/**
 *
 * @author Rafael
 */
public class EquityLine extends CreditAccount {
    public static final String TypeCd = "EQ";
    public static final String TypeDesc = "Equity Line";
    private double eqval;
    private double eqratio = .8;
    
    public EquityLine(String name, double eqvalue) {
        super(EquityLine.TypeCd, name, 0);
        if (super.getErrMsg().isEmpty()) {
            this.eqval = eqvalue;
            super.setCreditLimit(this.eqval * this.eqratio);
        }
    }
    
    public EquityLine(int acctno) {
        super(EquityLine.TypeCd, acctno);
        this.eqval = super.getCreditLimit() / this.eqratio;
    }
    
    @Override
    public String getAcctTypeCd() {
        return EquityLine.TypeCd;
}
    @Override
    public String getAcctTypeDesc() {
        return EquityLine.TypeDesc;
}
    public double getEquityValue() {
        return this.eqval;
    }    
}
