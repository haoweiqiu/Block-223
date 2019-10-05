package ca.mcgill.ecse223.block.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOBlock;

public class BlockUpdatePanel extends JPanel {

	// Label variables
	private JLabel titleLabel;
	private JLabel redLabel;
	private JLabel blueLabel;
	private JLabel greenLabel;
	private JLabel pointsLabel;
	private JLabel blastLabel;

	// Text fields
	private JTextField redField;
	private JTextField blueField;
	private JTextField greenField;
	private JTextField pointsField;
	private JTextField blastField;

	// Block selected
	private TOBlock block;

	// Buttons
	private JButton apply;

	private static final long serialVersionUID = -6972463307911531592L;

	public BlockUpdatePanel(TOBlock block ) {
		super();
		this.block = block;
		init();
	}

	public void init() {
		titleLabel = new JLabel("Update the Block");
		titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 20));

		// Change values
		redLabel = new JLabel("Red: ");
		redLabel.setFont(new Font(redLabel.getFont().getName(), Font.PLAIN, 18));
		redField = new JTextField("" + block.getRed());

		blueLabel = new JLabel("Blue: ");
		blueLabel.setFont(new Font(blueLabel.getFont().getName(), Font.PLAIN, 18));
		blueField = new JTextField("" + block.getBlue());

		greenLabel = new JLabel("Green: ");
		greenLabel.setFont(new Font(greenLabel.getFont().getName(), Font.PLAIN, 18));
		greenField = new JTextField(""+block.getGreen());

		pointsLabel = new JLabel("Points: ");
		pointsLabel.setFont(new Font(pointsLabel.getFont().getName(), Font.PLAIN, 18));
		pointsField = new JTextField("" + block.getPoints());

		blastLabel = new JLabel("Blast Size: ");
		blastLabel.setFont(new Font(blastLabel.getFont().getName(), Font.PLAIN, 18));
		blastField = new JTextField("" + block.getBlastRadius());
		apply = new JButton("Confirm");

		// Action listeners
		apply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					Block223Controller.updateBlock(block.getId(), Integer.parseInt(redField.getText()),
							Integer.parseInt(greenField.getText()), Integer.parseInt(blueField.getText()),
							Integer.parseInt(pointsField.getText()));
		
				} catch (InvalidInputException err) {
					System.out.println(err.getMessage());
				}
				try {
					Block223Controller.updateBlockBlast(block.getId(), Integer.parseInt(blastField.getText()));

				} catch (InvalidInputException err) {
					System.out.println(err.getMessage());
				}
				Block223Page.getPage().goBack();
			}
		});

		// Define layout
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addComponent(titleLabel)
				.addComponent(apply)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup()
						.addComponent(redLabel)
						.addComponent(greenLabel)
						.addComponent(blueLabel)
						.addComponent(pointsLabel)
						.addComponent(blastLabel))
					.addGroup(layout.createParallelGroup()
						.addComponent(redField, 125, 125, 125)
						.addComponent(greenField, 125, 125, 125)
						.addComponent(blueField, 125, 125, 125)
						.addComponent(pointsField, 125, 125, 125)
						.addComponent(blastField, 125, 125, 125))
					));

		layout.linkSize(SwingConstants.VERTICAL, redLabel, redField, greenField, blueField, pointsField, blastField);
		layout.linkSize(SwingConstants.HORIZONTAL, redLabel, redField, greenField, blueField, pointsField, blastField);

		layout.setVerticalGroup(layout.createSequentialGroup()
			.addComponent(titleLabel)
				.addGroup(layout.createParallelGroup()
					.addComponent(redLabel)
					.addComponent(redField))
				.addGroup(layout.createParallelGroup()
					.addComponent(greenLabel)
					.addComponent(greenField))
				.addGroup(layout.createParallelGroup()
					.addComponent(blueLabel)
					.addComponent(blueField))
				.addGroup(layout.createParallelGroup()
					.addComponent(pointsLabel)
					.addComponent(pointsField))
				.addGroup(layout.createParallelGroup()
					.addComponent(blastLabel)
					.addComponent(blastField))
			.addComponent(apply));

	}
}
