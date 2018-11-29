/**
 *
 * @author Sarah Mai, Ngan Nguyen
 */
public abstract class Formatter implements ReceiptFormatter
{
    protected int total;	// total price of booking(s)
	
    public String formatHeader()
    {
        return "RECEIPT\n\n";
    }
    

    public String formatFooter()
    {
        return (String.format("\n\nTOTAL DUE: $%d\n", total));
    }
}
