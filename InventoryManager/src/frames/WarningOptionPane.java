package frames;

import java.awt.Component;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import misc.FrameStrings;

public class WarningOptionPane extends JOptionPane {
	public static final String HTML_OPEN="<html>";
	public static final String HTML_CLOSE="</html>";
	public static final String FONT_OPEN="<font face = \"Arial\" size = \""+((int)(6*RootFrame.sizeMultiplier))+"\">";
	public static final String FONT_CLOSE="</font>";
	
	public WarningOptionPane() {
		super();
		UIManager.put("OptionPane.okButtonText", HTML_OPEN+FONT_OPEN+FrameStrings.valueOfStringName("optionpaneerrorok")+FONT_CLOSE+HTML_CLOSE);
	}
	
	public void showMessage(Component c,String s) {
		
		showMessageDialog(c, HTML_OPEN+FONT_OPEN+s+FONT_CLOSE+HTML_CLOSE,FrameStrings.valueOfStringName("optionpanewarningtitle"), JOptionPane.ERROR_MESSAGE);
	}
}
