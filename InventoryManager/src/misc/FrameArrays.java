package misc;

import java.util.HashMap;
import java.util.Map;

import frames.RootFrame;

/**
 *
 * @author Semaan
 */
public enum FrameArrays {
    
	
	reportschoicescbx("reportschoicescbx",new String[] {"Select","Sales","Purchases","Debts","Stock"},
			new String[] {"\u0627\u062e\u062a\u0627\u0631","\u0627\u0644\u0645\u0628\u064a\u0639\u0627\u062a",
					"\u0627\u0644\u0645\u0634\u062a\u0631\u064a\u0627\u062a","\u0627\u0644\u062f\u064a\u0648\u0646",
					"\u0627\u0644\u0628\u0636\u0627\u0639\u0629"}),
	reportssalestitles("reportssalestitles",new String[] {"ID","Date","Total","Discount","Subtotal"},
			new String[] {"\u0631\u0642\u0645","\u062a\u0627\u0631\u064a\u062e","\u0627\u0644\u0645\u062c\u0645\u0648\u0639",
					"\u062e\u0635\u0645","\u062d\u0627\u0635\u0644 \u0627\u0644\u062c\u0645\u0639"}),
	reportspurchasestitles("reportspurchasestitles",new String[] {"ID","Date","Supplier","Total","Discount","Subtotal"},
			new String[] {"\u0631\u0642\u0645","\u062a\u0627\u0631\u064a\u062e","\u0627\u0644\u0645\u0645\u0648\u0646",
					"\u0627\u0644\u0645\u062c\u0645\u0648\u0639","\u062e\u0635\u0645",
					"\u062d\u0627\u0635\u0644 \u0627\u0644\u062c\u0645\u0639"}),
	reportsdebtstitles("reportsdebtstitles",new String[] {"ID","Date","Customer","Total","Discount","Subtotal","Remaining","Status"},
			new String[] {"\u0631\u0642\u0645","\u062a\u0627\u0631\u064a\u062e","\u0627\u0644\u0632\u0628\u0648\u0646",
					"\u0627\u0644\u0645\u062c\u0645\u0648\u0639","\u062e\u0635\u0645",
					"\u062d\u0627\u0635\u0644 \u0627\u0644\u062c\u0645\u0639","\u0627\u0644\u0645\u062a\u0628\u0642\u064a",
					"\u0627\u0644\u062d\u0627\u0644\u0629"}),
	reportsstocktitles("reportsstocktitles",new String[] {"ID","Code","Name","Description","Quantity","Buying price","Selling price"},
			new String[] {"\u0631\u0642\u0645","\u0627\u0644\u0631\u0645\u0632","\u0627\u0644\u0627\u0633\u0645",
					"\u0627\u0644\u062a\u0641\u0627\u0635\u064a\u0644","\u0643\u0645\u064a\u0629",
					"\u0633\u0639\u0631 \u0627\u0644\u0634\u0631\u0627\u0621",
					"\u0633\u0639\u0631 \u0627\u0644\u0628\u064a\u0639"}),
	reportssuppliertitles("reportssuppliertitles",new String[] {"ID","Name","Phone"},
			new String[] {"\u0631\u0642\u0645","\u0627\u0644\u0627\u0633\u0645","\u0627\u0644\u0647\u0627\u062a\u0641"}),
	reportscustomertitles("reportscustomertitles",new String[] {"ID","Name","Phone"},
			new String[] {"\u0631\u0642\u0645","\u0627\u0644\u0627\u0633\u0645","\u0627\u0644\u0647\u0627\u062a\u0641"}),
	reportssalescbx("reportssalescbx",new String[] {"Select","By Day","By Month","By Year"},
			new String[] {"\u0627\u062e\u062a\u0627\u0631","\u062d\u0633\u0628 \u0627\u0644\u064a\u0648\u0645",
					"\u062d\u0633\u0628 \u0627\u0644\u0634\u0647\u0631","\u062d\u0633\u0628 \u0627\u0644\u0633\u0646\u0629"}),
	reportspurchasescbx("reportspurchasescbx",new String[] {"Select","By Day","By Month","By Year"},
			new String[] {"\u0627\u062e\u062a\u0627\u0631","\u062d\u0633\u0628 \u0627\u0644\u064a\u0648\u0645",
					"\u062d\u0633\u0628 \u0627\u0644\u0634\u0647\u0631","\u062d\u0633\u0628 \u0627\u0644\u0633\u0646\u0629"}),
	reportsdebtscbx("reportsdebtscbx",new String[] {"Select","By Day","By Month","By Year"},
			new String[] {"\u0627\u062e\u062a\u0627\u0631","\u062d\u0633\u0628 \u0627\u0644\u064a\u0648\u0645",
					"\u062d\u0633\u0628 \u0627\u0644\u0634\u0647\u0631","\u062d\u0633\u0628 \u0627\u0644\u0633\u0646\u0629"}),
	reportsstockcbx("reportsstockcbx",new String[] {"By Letter","A", "B", "C", "D", "E", "F", "G",
			"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z"},
			new String[] {"\u062d\u0633\u0628 \u0627\u0644\u062d\u0631\u0641","A", "B", "C", "D", "E", "F", "G",
					"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
					"U", "V", "W", "X", "Y", "Z"}),
	reportdetailstitles("reportdetailstitles",new String[] {"Name","Description","Quantity","Total"},new String[] {}),
	reportdetailsdebttitles("reportdetailsdebttitles",new String[] {"Date","Amount"},new String[] {}),
	lowstockstabletitles("lowstockstabletitles",new String[]{"Stock","Quantity"},new String[] {}),
	;
	
    
    private static final Map<String, String[]> EN_OptionArray = new HashMap<>();
    private static final Map<String, String[]> AR_OptionArray = new HashMap<>();
     
    static {
        for (FrameArrays c: values()) {
            EN_OptionArray.put(c.stringId, c.stringENValue);
            AR_OptionArray.put(c.stringId, c.stringARValue);
        }
    }
 
    public final String stringId;
    public final String[] stringENValue;
    public final String[] stringARValue;
 
    private FrameArrays(String stringId, String[] stringENValue, String[] stringARValue) {
        this.stringId = stringId;
        this.stringENValue = stringENValue;
        this.stringARValue = stringARValue;
    }
 
    public static String[] valueOfArrayName(String label) {
        switch(RootFrame.lang) {
        	case EN: return EN_OptionArray.get(label);
        	case AR: return AR_OptionArray.get(label);
        	default:break;
        }
        return null;
    }
}
 
