package se.esss.litterbox.testing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JTextField;

import sun.net.www.content.text.plain;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class FileTouch {

	private JFrame frame;
	private JPanel buttonPanel;
	private JPanel returnDataPanel;
	private JTextArea returnDataTextArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileTouch window = new FileTouch();
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
	public FileTouch() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		//frame.setBounds(100, 100, 450, 89);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		buttonPanel = new JPanel();
		frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
		
		JButton pingButton = new JButton("Ping Google");
		buttonPanel.add(pingButton);
		
		returnDataPanel = new JPanel();
		frame.getContentPane().add(returnDataPanel, BorderLayout.SOUTH);
		
		returnDataTextArea = new JTextArea();
		returnDataTextArea.setRows(20);
		returnDataTextArea.setColumns(40);
		returnDataPanel.add(returnDataTextArea);
		pingButton.addActionListener(touchFBtnAL);
		frame.pack();
	}
	
	public ActionListener touchFBtnAL = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			class UpdateThread implements Runnable {
				BufferedReader reader;
				UpdateThread(BufferedReader r) {this.reader = r;}
				@Override
				public void run() {
					String line = "";
					try {
						while ((line = this.reader.readLine()) != null) {
							returnDataTextArea.append(line + "\n");
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				Process p = Runtime.getRuntime().exec("ping -c 10 www.google.com");
				Thread t = new Thread(new UpdateThread(new BufferedReader(
						new InputStreamReader(p.getInputStream()))));
				t.start();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	};
}
