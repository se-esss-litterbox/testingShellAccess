package se.esss.litterbox.testing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.event.ActionEvent;

public class ReportEnvironment {

	private JFrame frame;
	private JTextArea returnTextArea;

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
		btnPrintEnvironmentVariables.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPanel.add(btnPrintEnvironmentVariables);
		
		JPanel readbackPanel = new JPanel();
		frame.getContentPane().add(readbackPanel, BorderLayout.CENTER);
		
		returnTextArea = new JTextArea();
		returnTextArea.setColumns(40);
		returnTextArea.setRows(30);
		readbackPanel.add(returnTextArea);
		
		frame.pack();
	}
	
	public ActionListener getEnvAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			class UpdateThread implements Runnable {
				BufferedReader reader;
				UpdateThread(BufferedReader r) {this.reader = r;}
				@Override
				public void run() {
					String line = "";
					try {
						while ((line = this.reader.readLine()) != null) {
							returnTextArea.append(line + "\n");
						}
					} catch (IOException e) {e.printStackTrace();}
				}
			}
			try {
				returnTextArea.setText("");
				ProcessBuilder pBuilder = new ProcessBuilder("/usr/bin/env");
				Process p = pBuilder.start();
				Thread t = new Thread(new UpdateThread(new BufferedReader(new InputStreamReader(p.getInputStream()))));
				t.start();
			} catch (IOException e1) {e1.printStackTrace();}
		}
	};

}
