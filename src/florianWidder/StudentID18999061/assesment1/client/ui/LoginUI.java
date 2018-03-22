package florianWidder.StudentID18999061.assesment1.client.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import florianWidder.StudentID18999061.assesment1.client.ClientMain;
import florianWidder.StudentID18999061.assesment1.shared.model.User;

public class LoginUI extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField inputUsername;
	private JTextField inputServerIp;
	private JTextField inputServerPort;

	/**
	 * Create the dialog.
	 */
	public LoginUI() {
		setTitle("Chat Login");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel txtUsername = new JLabel("Username:");
			GridBagConstraints gbc_txtUsername = new GridBagConstraints();
			gbc_txtUsername.anchor = GridBagConstraints.EAST;
			gbc_txtUsername.insets = new Insets(0, 0, 5, 5);
			gbc_txtUsername.gridx = 0;
			gbc_txtUsername.gridy = 0;
			contentPanel.add(txtUsername, gbc_txtUsername);
		}
		{
			inputUsername = new JTextField();
			GridBagConstraints gbc_inputUsername = new GridBagConstraints();
			gbc_inputUsername.insets = new Insets(0, 0, 5, 0);
			gbc_inputUsername.fill = GridBagConstraints.HORIZONTAL;
			gbc_inputUsername.gridx = 1;
			gbc_inputUsername.gridy = 0;
			contentPanel.add(inputUsername, gbc_inputUsername);
			inputUsername.setColumns(10);
		}
		{
			JLabel txtServerIP = new JLabel("Server IP:");
			GridBagConstraints gbc_txtServerIP = new GridBagConstraints();
			gbc_txtServerIP.anchor = GridBagConstraints.EAST;
			gbc_txtServerIP.insets = new Insets(0, 0, 5, 5);
			gbc_txtServerIP.gridx = 0;
			gbc_txtServerIP.gridy = 1;
			contentPanel.add(txtServerIP, gbc_txtServerIP);
		}
		{
			inputServerIp = new JTextField();
			inputServerIp.setText("localhost");
			GridBagConstraints gbc_inputServerIp = new GridBagConstraints();
			gbc_inputServerIp.insets = new Insets(0, 0, 5, 0);
			gbc_inputServerIp.fill = GridBagConstraints.HORIZONTAL;
			gbc_inputServerIp.gridx = 1;
			gbc_inputServerIp.gridy = 1;
			contentPanel.add(inputServerIp, gbc_inputServerIp);
			inputServerIp.setColumns(10);
		}
		{
			JLabel txtServerPort = new JLabel("Server Port: ");
			GridBagConstraints gbc_txtServerPort = new GridBagConstraints();
			gbc_txtServerPort.insets = new Insets(0, 0, 0, 5);
			gbc_txtServerPort.anchor = GridBagConstraints.EAST;
			gbc_txtServerPort.gridx = 0;
			gbc_txtServerPort.gridy = 2;
			contentPanel.add(txtServerPort, gbc_txtServerPort);
		}
		{
			inputServerPort = new JTextField();
			inputServerPort.setText("12345");
			GridBagConstraints gbc_inputServerPort = new GridBagConstraints();
			gbc_inputServerPort.fill = GridBagConstraints.HORIZONTAL;
			gbc_inputServerPort.gridx = 1;
			gbc_inputServerPort.gridy = 2;
			contentPanel.add(inputServerPort, gbc_inputServerPort);
			inputServerPort.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new BorderLayout(0, 0));
			{
				JButton okButton = new JButton("Login");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						ClientMain.setIP(getInputServerIp().getText());
						ClientMain.setPort(Integer.parseInt(getInputServerPort().getText()));
						ClientMain.setUser(new User(getInputUsername().getText()));
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton, BorderLayout.WEST);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton, BorderLayout.EAST);
			}

			pack();

			while (isShowing())
				;
		}
	}

	public JTextField getInputServerIp() {
		return inputServerIp;
	}

	public JTextField getInputServerPort() {
		return inputServerPort;
	}

	public JTextField getInputUsername() {
		return inputUsername;
	}
}
