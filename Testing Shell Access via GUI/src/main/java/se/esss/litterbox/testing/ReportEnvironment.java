package se.esss.litterbox.testing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class ReportEnvironment {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReportEnvironment window = new ReportEnvironment();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ReportEnvironment() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel btnPanel = new JPanel();
		frame.getContentPane().add(btnPanel, BorderLayout.NORTH);
		
		JButton btnPrintEnvironmentVariables = new JButton("Print Environment Variables");
		btnPanel.add(btnPrintEnvironmentVariables);
		
		JPanel readbackPanel = new JPanel();
		frame.getContentPane().add(readbackPanel, BorderLayout.CENTER);
		
		JTextArea textArea = new JTextArea();
		textArea.setColumns(40);
		textArea.setRows(30);
		readbackPanel.add(textArea);
		
		frame.pack();
	}

}
