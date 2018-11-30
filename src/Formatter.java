/**
 * Abstract class to implement common methods for 
 * the implementation of ReceiptFormatter interface
 * @author Sarah Mai, Ngan Nguyen
 */
public abstract class Formatter implements ReceiptFormatter
{
    protected int total;	// total price of booking(s)
	
    /**
     * Receipt header
     */
    public String formatHeader()
    {
        return "RECEIPT\n\n";
    }
    

    /**
     * Receipt Footer
     */
    public String formatFooter()
    {
        return (String.format("\n\nTOTAL DUE: $%d\n", total));
    }
}
