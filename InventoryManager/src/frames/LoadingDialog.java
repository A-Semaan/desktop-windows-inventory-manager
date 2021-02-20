package frames;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import misc.FrameStrings;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class LoadingDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	public LoadingDialog() {
		setUndecorated(true);
		setResizable(false);
		setFont(new Font("Arial", Font.BOLD, (int)(22*RootFrame.sizeMultiplier)));
		setAlwaysOnTop(true);
		setSize((int)(380*RootFrame.sizeMultiplier), (int)(245*RootFrame.sizeMultiplier));
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.LIGHT_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel loadingpane = new JLabel(FrameStrings.valueOfStringName("loadingpane"));
			loadingpane.setHorizontalAlignment(SwingConstants.CENTER);
			loadingpane.setFont(new Font("Arial", Font.BOLD, (int)(22*RootFrame.sizeMultiplier)));
			contentPanel.add(loadingpane, BorderLayout.CENTER);
		}
	}

}
