package florianWidder.StudentID18999061.assesment1.client.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ErrorUI extends JDialog {

    /**
     *
     */
    private static final long serialVersionUID = -5541572110324103057L;
    private final JPanel contentPanel = new JPanel();

    /**
     * Create the dialog.
     */
    public ErrorUI(final String errorText) {
	setTitle("Error");
	setBounds(100, 100, 450, 300);
	getContentPane().setLayout(new BorderLayout());
	contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	getContentPane().add(contentPanel, BorderLayout.CENTER);
	contentPanel.setLayout(new BorderLayout(0, 0));
	{
	    final JLabel lblErrortext = new JLabel(errorText);
	    lblErrortext.setVerticalAlignment(SwingConstants.TOP);
	    lblErrortext.setHorizontalAlignment(SwingConstants.LEFT);
	    contentPanel.add(lblErrortext, BorderLayout.CENTER);
	}
	{
	    final JPanel buttonPane = new JPanel();
	    buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
	    getContentPane().add(buttonPane, BorderLayout.SOUTH);
	    {
		final JButton okButton = new JButton("OK");
		okButton.addActionListener(arg0 -> dispose());
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	    }
	}
	pack();
    }

}
