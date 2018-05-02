package eg.edu.alexu.csd.oop.db.cs69.View;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import eg.edu.alexu.csd.oop.db.cs69.Control.Control;


public class GUI {

	private JFrame frame;
	JTextField textField;
	public JCheckBox chckbxNewCheckBox;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					final GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Database");
		frame.setBounds(100, 100, 415, 230);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setBounds(86, 32, 303, 28);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		final JLabel lblNewLabel = new JLabel("Enter Query");
		lblNewLabel.setBounds(10, 36, 85, 21);
		frame.getContentPane().add(lblNewLabel);
		chckbxNewCheckBox = new JCheckBox("true");
		chckbxNewCheckBox.setSelected(true);
		chckbxNewCheckBox.setBounds(86, 82, 97, 23);
		frame.getContentPane().add(chckbxNewCheckBox);
		JLabel lblNewLabel_1 = new JLabel("Drob if exist");
		lblNewLabel_1.setBounds(10, 86, 80, 14);
		frame.getContentPane().add(lblNewLabel_1);



		final JButton btnNewButton = new JButton("Submit");


		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				final String query = textField.getText();
				final Control c = new Control(query);
				}
		});
		btnNewButton.setBounds(148, 133, 89, 23);
		frame.getContentPane().add(btnNewButton);
	}
}