package frames;

import javax.swing.JFrame;

public class FrameRunner extends Thread {
	
	private static RootFrame frame;
	
	public FrameRunner() {
		frame = new RootFrame(this);
	}
	
	@Override
	public void run() {
		
		frame.setVisible(true);
	}
	
	public void notifyLanguageChanged() {
		frame.dispose();
		frame = new RootFrame(this);
		frame.setVisible(true);
	}
}
