package florianWidder.StudentID18999061.assesment1.client.ui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import florianWidder.StudentID18999061.assesment1.client.ClientMain;
import florianWidder.StudentID18999061.assesment1.shared.model.User;

public class ClientUI extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblUsername;
	private JLabel lblServer;
	private JTextField inputMessage;
	private JTable tblUsers;
	private JTable tblChat;
	private JComboBox<String> cmbBxReciver;
	private JButton btnSend;

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("serial")
	public ClientUI() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				try {
					ClientMain.getConnection().logout();
				} catch (IOException e) {
				}
			}
		});
		setTitle("Chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 35, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JPanel headerPanel = new JPanel();
		GridBagConstraints gbc_headerPanel = new GridBagConstraints();
		gbc_headerPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_headerPanel.insets = new Insets(0, 0, 5, 5);
		gbc_headerPanel.gridx = 0;
		gbc_headerPanel.gridy = 0;
		contentPane.add(headerPanel, gbc_headerPanel);
		GridBagLayout gbl_headerPanel = new GridBagLayout();
		gbl_headerPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_headerPanel.rowHeights = new int[] { 0, 0 };
		gbl_headerPanel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_headerPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		headerPanel.setLayout(gbl_headerPanel);

		JLabel TxtUsername = new JLabel("Username: ");
		GridBagConstraints gbc_TxtUsername = new GridBagConstraints();
		gbc_TxtUsername.insets = new Insets(0, 0, 0, 5);
		gbc_TxtUsername.gridx = 0;
		gbc_TxtUsername.gridy = 0;
		headerPanel.add(TxtUsername, gbc_TxtUsername);

		lblUsername = new JLabel(ClientMain.getUser().getUsername());
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.insets = new Insets(0, 0, 0, 5);
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 0;
		headerPanel.add(lblUsername, gbc_lblUsername);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.fill = GridBagConstraints.HORIZONTAL;
		gbc_horizontalStrut.insets = new Insets(0, 0, 0, 5);
		gbc_horizontalStrut.gridx = 2;
		gbc_horizontalStrut.gridy = 0;
		headerPanel.add(horizontalStrut, gbc_horizontalStrut);

		JLabel txtServer = new JLabel("Server: ");
		GridBagConstraints gbc_txtServer = new GridBagConstraints();
		gbc_txtServer.fill = GridBagConstraints.BOTH;
		gbc_txtServer.insets = new Insets(0, 0, 0, 5);
		gbc_txtServer.gridx = 3;
		gbc_txtServer.gridy = 0;
		headerPanel.add(txtServer, gbc_txtServer);

		lblServer = new JLabel(ClientMain.getIP() + ":" + ClientMain.getPort());
		GridBagConstraints gbc_lblServer = new GridBagConstraints();
		gbc_lblServer.gridx = 4;
		gbc_lblServer.gridy = 0;
		headerPanel.add(lblServer, gbc_lblServer);

		JLabel txtUsers = new JLabel("Users:");
		GridBagConstraints gbc_txtUsers = new GridBagConstraints();
		gbc_txtUsers.fill = GridBagConstraints.VERTICAL;
		gbc_txtUsers.insets = new Insets(0, 0, 5, 0);
		gbc_txtUsers.gridx = 1;
		gbc_txtUsers.gridy = 0;
		contentPane.add(txtUsers, gbc_txtUsers);

		JPanel mainPanel = new JPanel();
		GridBagConstraints gbc_mainPanel = new GridBagConstraints();
		gbc_mainPanel.insets = new Insets(0, 0, 5, 5);
		gbc_mainPanel.fill = GridBagConstraints.BOTH;
		gbc_mainPanel.gridx = 0;
		gbc_mainPanel.gridy = 1;
		contentPane.add(mainPanel, gbc_mainPanel);
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[] { 0, 0 };
		gbl_mainPanel.rowHeights = new int[] { 0, 0 };
		gbl_mainPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_mainPanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		mainPanel.setLayout(gbl_mainPanel);

		tblChat = new JTable();
		tblChat.setShowVerticalLines(false);
		tblChat.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null }, },
				new String[] { "Other", "OtherMessage", "MyMessage", "Me" }) {
			Class[] columnTypes = new Class[] { String.class, String.class, String.class, String.class };

			boolean[] columnEditables = new boolean[] { false, false, false, false };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		GridBagConstraints gbc_tblChat = new GridBagConstraints();
		gbc_tblChat.fill = GridBagConstraints.BOTH;
		gbc_tblChat.gridx = 0;
		gbc_tblChat.gridy = 0;
		mainPanel.add(tblChat, gbc_tblChat);

		tblUsers = new JTable();
		tblUsers.setShowVerticalLines(false);
		tblUsers.setModel(new DefaultTableModel(new Object[][] { { null }, { null }, { null }, { null }, { null },
				{ null }, { null }, { null }, { null }, { null }, }, new String[] { "User" }) {
			Class[] columnTypes = new Class[] { String.class };

			boolean[] columnEditables = new boolean[] { false };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tblUsers.setCellSelectionEnabled(true);
		GridBagConstraints gbc_tblUsers = new GridBagConstraints();
		gbc_tblUsers.fill = GridBagConstraints.BOTH;
		gbc_tblUsers.insets = new Insets(0, 0, 5, 0);
		gbc_tblUsers.gridx = 1;
		gbc_tblUsers.gridy = 1;
		contentPane.add(tblUsers, gbc_tblUsers);

		JPanel footerPanel = new JPanel();
		GridBagConstraints gbc_footerPanel = new GridBagConstraints();
		gbc_footerPanel.insets = new Insets(0, 0, 0, 5);
		gbc_footerPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_footerPanel.gridx = 0;
		gbc_footerPanel.gridy = 2;
		contentPane.add(footerPanel, gbc_footerPanel);
		GridBagLayout gbl_footerPanel = new GridBagLayout();
		gbl_footerPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_footerPanel.rowHeights = new int[] { 0, 0 };
		gbl_footerPanel.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_footerPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		footerPanel.setLayout(gbl_footerPanel);

		inputMessage = new JTextField();
		GridBagConstraints gbc_inputMessage = new GridBagConstraints();
		gbc_inputMessage.insets = new Insets(0, 0, 0, 5);
		gbc_inputMessage.fill = GridBagConstraints.BOTH;
		gbc_inputMessage.gridx = 0;
		gbc_inputMessage.gridy = 0;
		footerPanel.add(inputMessage, gbc_inputMessage);
		inputMessage.setColumns(10);

		cmbBxReciver = new JComboBox<String>();
		cmbBxReciver.setModel(
				new DefaultComboBoxModel(new String[] { "Username 1", "Username 2", "Username 3", "Broadcast" }));
		GridBagConstraints gbc_cmbBxReciver = new GridBagConstraints();
		gbc_cmbBxReciver.fill = GridBagConstraints.BOTH;
		gbc_cmbBxReciver.gridx = 1;
		gbc_cmbBxReciver.gridy = 0;
		footerPanel.add(cmbBxReciver, gbc_cmbBxReciver);

		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.fill = GridBagConstraints.BOTH;
		gbc_btnSend.gridx = 1;
		gbc_btnSend.gridy = 2;
		contentPane.add(btnSend, gbc_btnSend);
		refreshUsers(new User[0]);
	}

	public JButton getBtnSend() {
		return btnSend;
	}

	public JComboBox<String> getCmbBxReciver() {
		return cmbBxReciver;
	}

	public JTextField getInputMessage() {
		return inputMessage;
	}

	public JLabel getLblServer() {
		return lblServer;
	}

	public JLabel getLblUsername() {
		return lblUsername;
	}

	public JTable getTblChat() {
		return tblChat;
	}

	public JTable getTblUsers() {
		return tblUsers;
	}

	public synchronized void refreshUsers(User[] users) {
		String[][] unames = new String[users.length][1];
		cmbBxReciver.removeAllItems();
		for (int i = 0; i < users.length; i++) {
			unames[i][0] = users[i].getUsername();
			cmbBxReciver.addItem(users[i].getUsername());
		}
		tblUsers.removeAll();
		tblUsers.setModel(new DefaultTableModel(unames, new String[1]));
		cmbBxReciver.addItem("Broadcast");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
}
