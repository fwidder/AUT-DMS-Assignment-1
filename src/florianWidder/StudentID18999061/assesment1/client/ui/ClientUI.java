package florianWidder.StudentID18999061.assesment1.client.ui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import florianWidder.StudentID18999061.assesment1.client.ClientMain;
import florianWidder.StudentID18999061.assesment1.shared.model.BroadcastMessage;
import florianWidder.StudentID18999061.assesment1.shared.model.ConnectMessage;
import florianWidder.StudentID18999061.assesment1.shared.model.DisconnectMessage;
import florianWidder.StudentID18999061.assesment1.shared.model.Message;
import florianWidder.StudentID18999061.assesment1.shared.model.MessageTo;
import florianWidder.StudentID18999061.assesment1.shared.model.User;

public class ClientUI extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static String getRandomColor() {
	final Random random = new Random();
	final String[] letters = "0123456789ABCDEF".split("");
	String color = "#";
	for (int i = 0; i < 6; i++) {
	    color += letters[Math.round(random.nextFloat() * 15)];
	}
	return color;
    }

    private final JPanel contentPane;
    private final JLabel lblUsername;
    private final JLabel lblServer;
    JTextField inputMessage;
    private final JTable tblUsers;
    JComboBox<String> cmbBxReciver;
    JButton btnSend;

    private final JLabel chat;

    private final HashMap<String, String> colors = new HashMap<>();

    private final JScrollPane mainPanel;

    /**
     * Create the frame.
     */
    public ClientUI() {
	addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(final WindowEvent arg0) {
		try {
		    ClientMain.getConnection().logout();
		} catch (final IOException e) {
		    e.printStackTrace();
		}
	    }
	});
	setTitle("Chat");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 450, 300);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	final GridBagLayout gbl_contentPane = new GridBagLayout();
	gbl_contentPane.columnWidths = new int[] { 0, 0, 0 };
	gbl_contentPane.rowHeights = new int[] { 35, 0, 0, 0 };
	gbl_contentPane.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
	gbl_contentPane.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
	contentPane.setLayout(gbl_contentPane);

	final JPanel headerPanel = new JPanel();
	final GridBagConstraints gbc_headerPanel = new GridBagConstraints();
	gbc_headerPanel.fill = GridBagConstraints.HORIZONTAL;
	gbc_headerPanel.insets = new Insets(0, 0, 5, 5);
	gbc_headerPanel.gridx = 0;
	gbc_headerPanel.gridy = 0;
	contentPane.add(headerPanel, gbc_headerPanel);
	final GridBagLayout gbl_headerPanel = new GridBagLayout();
	gbl_headerPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
	gbl_headerPanel.rowHeights = new int[] { 0, 0 };
	gbl_headerPanel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
	gbl_headerPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
	headerPanel.setLayout(gbl_headerPanel);

	final JLabel TxtUsername = new JLabel("Username: ");
	final GridBagConstraints gbc_TxtUsername = new GridBagConstraints();
	gbc_TxtUsername.insets = new Insets(0, 0, 0, 5);
	gbc_TxtUsername.gridx = 0;
	gbc_TxtUsername.gridy = 0;
	headerPanel.add(TxtUsername, gbc_TxtUsername);

	lblUsername = new JLabel(ClientMain.getUser().getUsername());
	final GridBagConstraints gbc_lblUsername = new GridBagConstraints();
	gbc_lblUsername.insets = new Insets(0, 0, 0, 5);
	gbc_lblUsername.gridx = 1;
	gbc_lblUsername.gridy = 0;
	headerPanel.add(lblUsername, gbc_lblUsername);

	final Component horizontalStrut = Box.createHorizontalStrut(20);
	final GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
	gbc_horizontalStrut.fill = GridBagConstraints.HORIZONTAL;
	gbc_horizontalStrut.insets = new Insets(0, 0, 0, 5);
	gbc_horizontalStrut.gridx = 2;
	gbc_horizontalStrut.gridy = 0;
	headerPanel.add(horizontalStrut, gbc_horizontalStrut);

	final JLabel txtServer = new JLabel("Server: ");
	final GridBagConstraints gbc_txtServer = new GridBagConstraints();
	gbc_txtServer.fill = GridBagConstraints.BOTH;
	gbc_txtServer.insets = new Insets(0, 0, 0, 5);
	gbc_txtServer.gridx = 3;
	gbc_txtServer.gridy = 0;
	headerPanel.add(txtServer, gbc_txtServer);

	lblServer = new JLabel(ClientMain.getIP() + ":" + ClientMain.getPort());
	final GridBagConstraints gbc_lblServer = new GridBagConstraints();
	gbc_lblServer.gridx = 4;
	gbc_lblServer.gridy = 0;
	headerPanel.add(lblServer, gbc_lblServer);

	final JLabel txtUsers = new JLabel("Users:");
	final GridBagConstraints gbc_txtUsers = new GridBagConstraints();
	gbc_txtUsers.fill = GridBagConstraints.VERTICAL;
	gbc_txtUsers.insets = new Insets(0, 0, 5, 0);
	gbc_txtUsers.gridx = 1;
	gbc_txtUsers.gridy = 0;
	contentPane.add(txtUsers, gbc_txtUsers);

	chat = new JLabel("<html><h3>Welcome to my Chatserver<h3><br><p>");
	chat.setVerticalAlignment(SwingConstants.TOP);

	mainPanel = new JScrollPane(chat);
	final GridBagConstraints gbc_mainPanel = new GridBagConstraints();
	gbc_mainPanel.insets = new Insets(0, 0, 5, 5);
	gbc_mainPanel.fill = GridBagConstraints.BOTH;
	gbc_mainPanel.gridx = 0;
	gbc_mainPanel.gridy = 1;
	mainPanel.getVerticalScrollBar()
		.addAdjustmentListener(e -> e.getAdjustable().setValue(e.getAdjustable().getMaximum()));
	contentPane.add(mainPanel, gbc_mainPanel);

	tblUsers = new JTable();

	tblUsers.setShowVerticalLines(false);
	tblUsers.setModel(new DefaultTableModel(new Object[][] {}, new String[] {}));
	tblUsers.setCellSelectionEnabled(true);
	final GridBagConstraints gbc_tblUsers = new GridBagConstraints();
	gbc_tblUsers.fill = GridBagConstraints.BOTH;
	gbc_tblUsers.insets = new Insets(0, 0, 5, 0);
	gbc_tblUsers.gridx = 1;
	gbc_tblUsers.gridy = 1;
	contentPane.add(tblUsers, gbc_tblUsers);

	final JPanel footerPanel = new JPanel();
	final GridBagConstraints gbc_footerPanel = new GridBagConstraints();
	gbc_footerPanel.insets = new Insets(0, 0, 0, 5);
	gbc_footerPanel.fill = GridBagConstraints.HORIZONTAL;
	gbc_footerPanel.gridx = 0;
	gbc_footerPanel.gridy = 2;
	contentPane.add(footerPanel, gbc_footerPanel);
	final GridBagLayout gbl_footerPanel = new GridBagLayout();
	gbl_footerPanel.columnWidths = new int[] { 0, 0, 0 };
	gbl_footerPanel.rowHeights = new int[] { 0, 0 };
	gbl_footerPanel.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
	gbl_footerPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
	footerPanel.setLayout(gbl_footerPanel);

	inputMessage = new JTextField();
	inputMessage.addKeyListener(new KeyAdapter() {
	    @Override
	    public void keyReleased(final KeyEvent arg0) {
		if (arg0.getKeyCode() == 10) {
		    btnSend.doClick();
		}
	    }
	});
	final GridBagConstraints gbc_inputMessage = new GridBagConstraints();
	gbc_inputMessage.insets = new Insets(0, 0, 0, 5);
	gbc_inputMessage.fill = GridBagConstraints.BOTH;
	gbc_inputMessage.gridx = 0;
	gbc_inputMessage.gridy = 0;
	footerPanel.add(inputMessage, gbc_inputMessage);
	inputMessage.setColumns(10);

	cmbBxReciver = new JComboBox<>();
	final GridBagConstraints gbc_cmbBxReciver = new GridBagConstraints();
	gbc_cmbBxReciver.fill = GridBagConstraints.BOTH;
	gbc_cmbBxReciver.gridx = 1;
	gbc_cmbBxReciver.gridy = 0;
	footerPanel.add(cmbBxReciver, gbc_cmbBxReciver);

	btnSend = new JButton("Send");
	btnSend.addActionListener(arg0 -> {
	    Message m = null;
	    if (cmbBxReciver.getSelectedItem().equals("Broadcast")) {
		m = new BroadcastMessage();
	    } else {
		m = new MessageTo((String) cmbBxReciver.getSelectedItem());
	    }
	    try {
		m.setSender(ClientMain.getUser());
		m.setPayload(inputMessage.getText());
		inputMessage.setText("");
		ClientMain.getConnection().sendMessage(m);
	    } catch (IOException | NullPointerException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	});
	final GridBagConstraints gbc_btnSend = new GridBagConstraints();
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

    public JScrollPane getMainPanel() {
	return mainPanel;
    }

    public JTable getTblUsers() {
	return tblUsers;
    }

    public void newMessage(final Message m) {
	String html = chat.getText();
	html += "<font color=\"" + colors.get(m.getSender().getUsername()) + "\">";
	if (m.getSender() != ClientMain.getUser()) {
	    if (m instanceof MessageTo) {
		final MessageTo msg = (MessageTo) m;
		html += msg.getSender().getUsername() + " (privat): " + msg.getPayload() + "</font><br>";
		chat.setText(html);
		return;
	    }
	    if (m instanceof BroadcastMessage) {
		final BroadcastMessage msg = (BroadcastMessage) m;
		html += msg.getSender().getUsername() + " (broadcast): " + msg.getPayload() + "</font><br>";
		chat.setText(html);
		return;
	    }
	    if (m instanceof ConnectMessage) {
		final ConnectMessage msg = (ConnectMessage) m;
		html += msg.getSender().getUsername() + " has joined the Server." + "</font><br>";
		chat.setText(html);
		return;
	    }
	    if (m instanceof DisconnectMessage) {
		final DisconnectMessage msg = (DisconnectMessage) m;
		html += msg.getSender().getUsername() + " has left the Server." + "</font><br>";
		chat.setText(html);
		return;
	    }
	} else {
	    if (m instanceof MessageTo) {
		final MessageTo msg = (MessageTo) m;
		html += "You → " + msg.getRec() + " (privat): " + msg.getPayload() + "</font><br>";
		chat.setText(html);
		return;
	    }
	    if (m instanceof BroadcastMessage) {
		final BroadcastMessage msg = (BroadcastMessage) m;
		html += "You " + "(broadcast): " + msg.getPayload() + "</font><br>";
		chat.setText(html);
		return;
	    }
	    if (m instanceof ConnectMessage) {
		html += "You have joined the Server." + "</font><br>";
		chat.setText(html);
		getMainPanel().getVerticalScrollBar().setValue(getMainPanel().getVerticalScrollBar().getMaximum());
		return;
	    }
	    if (m instanceof DisconnectMessage) {
		chat.setText(html);
		html += "You have left the Server." + "</font><br>";
		getMainPanel().getVerticalScrollBar().setValue(getMainPanel().getVerticalScrollBar().getMaximum());
		return;
	    }
	}
    }

    public synchronized void refreshUsers(final User[] users) {
	final String[][] unames = new String[users.length][1];
	final String selected = (String) cmbBxReciver.getSelectedItem();
	cmbBxReciver.removeAllItems();
	for (int i = 0; i < users.length; i++) {
	    unames[i][0] = users[i].getUsername();
	    cmbBxReciver.addItem(users[i].getUsername());
	}
	tblUsers.removeAll();
	tblUsers.setModel(new DefaultTableModel(unames, new String[1]));
	cmbBxReciver.addItem("Broadcast");
	cmbBxReciver.getModel().setSelectedItem(selected);
	for (final User u : users) {
	    if (!colors.containsKey(u.getUsername())) {
		colors.put(u.getUsername(), ClientUI.getRandomColor());
	    }
	}
    }
}
