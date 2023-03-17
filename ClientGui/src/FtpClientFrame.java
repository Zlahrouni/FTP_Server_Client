import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane; 
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FtpClientFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	PrintStream ps;
	BufferedReader br;
	private JScrollPane scrollPane;

	private JTextArea resultArea;
	private JTextField commandField;

	private Socket socket;
	private Client client;

	public FtpClientFrame() {
		
		super("TransferEase Client");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create the menu bar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// "authentication" menu item
		JMenuItem authenticationMenuItem = new JMenuItem("Authentication");
		authenticationMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showLoginDialog();
			}
		});
		// quit menu
		JMenuItem quitMenuItem = new JMenuItem("Quit");
		quitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ps.println("bye");
				client.passOk = false;
				client.userOk = false;
				client.username = "";
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		// user Menu
		JMenu userMenu = new JMenu("User");
		userMenu.add(authenticationMenuItem);
		userMenu.add(quitMenuItem);
		menuBar.add(userMenu);
		
		// Terminal menu
	    JMenu terminalMenu = new JMenu("Terminal");

	    // Clear menu item
	    JMenuItem clearMenuItem = new JMenuItem("Clear");
	    clearMenuItem.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	resultArea.setText("");
	            
	        }
	    });
	    terminalMenu.add(clearMenuItem);
	    menuBar.add(terminalMenu);

		JPanel mainPanel = new JPanel(new BorderLayout());

		resultArea = new JTextArea();
		resultArea.setEditable(false);
		scrollPane = new JScrollPane(resultArea); // wrap the text area in a scroll pane
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());

		mainPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel inputPanel = new JPanel(new BorderLayout());
		commandField = new JTextField();

		commandField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String command = commandField.getText();
					executeCommand(command);
				}
			}
		});

		inputPanel.add(commandField, BorderLayout.CENTER);
		JButton executeButton = new JButton("Execute");
		executeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = commandField.getText();
				executeCommand(command);
			}
		});

		inputPanel.add(executeButton, BorderLayout.EAST);
		mainPanel.add(inputPanel, BorderLayout.SOUTH);

		getContentPane().add(mainPanel);

		setVisible(true);
		client = new Client(resultArea);
		checkConnexion();
	}

	private void executeCommand(String command) {
		checkConnexion();

		if (socket.isConnected()) {
			if(ps != null && br != null) {
				if (command != null && !command.isEmpty()) {
					resultArea.append(client.username +" $> " + command + "\n");
					scrollToBottom(); 
					resultArea.setCaretPosition(resultArea.getDocument().getLength());

					client.commande(br, ps, command);
					commandField.setText("");
				}
			}
		} else {
			resultArea.append("You're not connected, set your user uthentication");
		}

	}
	
	private void scrollToBottom() {
	    JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
	    verticalScrollBar.setValue(verticalScrollBar.getMaximum());
	}


	private void checkConnexion() {

		if (socket == null || socket.isClosed() || !socket.isConnected()) {
			String host = JOptionPane.showInputDialog("Enter host:");
			if (host == null || host.isEmpty()) {
				resultArea.append("Error: invalid host\n");
				
				return;
			}
			try {
				socket = new Socket(host, 2121);
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				ps = new PrintStream(socket.getOutputStream());

				// resultArea.append(welcomeMessage);
			} catch (IOException e) {
				resultArea.append("Error: " + e.getMessage() + "\n");
				return;
			} finally {

				if (socket != null && !socket.isClosed()) {
					client.affichage(br);
					resultArea.append("To set the password go to User > Authentication\n");
				}
			}

		}
		/*
		 * if((!client.userOk && !client.passOk)) { showLoginDialog();
		 * 
		 * }
		 */

	}

	public void showLoginDialog() {
		JDialog loginDialog = new JDialog(this, "Login", ModalityType.MODELESS);

		JPanel panel = new JPanel(new GridLayout(0, 1));
		JTextField usernameField = new JTextField();
		JPasswordField passwordField = new JPasswordField();
		panel.add(new JLabel("Username:"));
		panel.add(usernameField);
		panel.add(new JLabel("Password:"));
		panel.add(passwordField);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());
				client.commande(br, ps, "user " + username);
				client.commande(br, ps, "pass " + password);
				loginDialog.dispose();
				if (client.userOk && client.passOk) {
					client.username = username;
				} else {
					client.username = "";
				}
			}
		});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loginDialog.dispose();
			}
		});
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		loginDialog.add(panel, BorderLayout.CENTER);
		loginDialog.add(buttonPanel, BorderLayout.SOUTH);
		loginDialog.pack();
		loginDialog.setLocationRelativeTo(this);
		loginDialog.setVisible(true);
		
		
	}

	public static void main(String[] args) {
		new FtpClientFrame();
	}
}
