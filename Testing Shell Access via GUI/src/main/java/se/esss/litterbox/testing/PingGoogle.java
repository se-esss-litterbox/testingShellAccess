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
import javax.swing.JSlider;

public class PingGoogle {

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
					PingGoogle window = new PingGoogle();
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
	public PingGoogle() {
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
		
		unthreadedPingBtn = new JButton("Unthreaded Ping");
		unthreadedPingBtn.addActionListener(unThreadedPingAction);
		buttonPanel.add(unthreadedPingBtn);
		
		pingNumSlider = new JSlider();
		pingNumSlider.setValue(25);
		pingNumSlider.setMaximum(50);
		pingNumSlider.setSnapToTicks(true);
		pingNumSlider.setPaintTicks(true);
		pingNumSlider.setPaintLabels(true);
		pingNumSlider.setMajorTickSpacing(10);
		pingNumSlider.setMinorTickSpacing(1);
		pingNumSlider.setMinimum(1);
		buttonPanel.add(pingNumSlider);
		
		JButton threadedPingBtn = new JButton("Threaded Ping");
		buttonPanel.add(threadedPingBtn);
		
		returnDataPanel = new JPanel();
		frame.getContentPane().add(returnDataPanel, BorderLayout.SOUTH);
		
		returnDataTextArea = new JTextArea();
		returnDataTextArea.setRows(20);
		returnDataTextArea.setColumns(40);
		returnDataPanel.add(returnDataTextArea);
		threadedPingBtn.addActionListener(threadedPingAction);
		frame.pack();
	}
	
	public ActionListener unThreadedPingAction = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				returnDataTextArea.setText("");
				Process p = Runtime.getRuntime().exec("ping -c " + pingNumSlider.getValue() + " www.google.com");
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line = "";
				while ((line = reader.readLine()) != null) {
					returnDataTextArea.append(line + "\n");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	};
	
	public ActionListener threadedPingAction = new ActionListener() {
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
							returnDataTextArea.append(line + "\n");
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				returnDataTextArea.setText("");
				Process p = Runtime.getRuntime().exec("ping -c " + pingNumSlider.getValue() + " www.google.com");
				Thread t = new Thread(new UpdateThread(new BufferedReader(
						new InputStreamReader(p.getInputStream()))));
				t.start();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	};
	private JSlider pingNumSlider;
	private JButton unthreadedPingBtn;
}
