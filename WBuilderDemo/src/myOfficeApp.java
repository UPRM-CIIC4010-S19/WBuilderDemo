import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class myOfficeApp implements ActionListener{

	private JFrame frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField txtSubtotal, txtTaxes, txtTotal;
	private double dailyRate, subTotal, taxes, total ;
	private static final double TAX_RATE = 0.115; // 11.5%
	private static final double REG_RATE = 79.99;
	private static final double STD_RATE = 59.99;
	private static final double SPN_RATE = 49.99;
	private static final double WIFI_RATE = 5.99;
	private static final double ROOM_RATE = 12.99;
	private static final double FOOD_RATE = 19.99;
	private static final Integer[] DAYOPTIONS_INTEGERS = new Integer[]{6, 12,18,24,36};
	NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
	private JLabel lblTitle;
	private JButton btnExit, btnCalculate,btnClear;
	private JRadioButton rdbtnRegular, rdbtnStudent, rdbtnSponsored;
	private JCheckBox chckbxWifi, chckbxRoom, chckbxFood;
	private JComboBox<Integer> comboBox;
	private JMenuItem mntmAbout, mntmQuit, mntmFaqs, mntmSearch;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					myOfficeApp window = new myOfficeApp();
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
	public myOfficeApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnMyoffice = new JMenu("myOffice");
		menuBar.add(mnMyoffice);
		
		mntmAbout = new JMenuItem("About myOffice");
		mnMyoffice.add(mntmAbout);
		
		mntmQuit = new JMenuItem(" Quit myOffice");
		mnMyoffice.add(mntmQuit);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		mntmFaqs = new JMenuItem("FAQs");
		mnHelp.add(mntmFaqs);
		
		mntmSearch = new JMenuItem("Search");
		mnHelp.add(mntmSearch);
		
		lblTitle = new JLabel("Office Reservation App");
		lblTitle.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(Color.BLUE);
		frame.getContentPane().add(lblTitle, BorderLayout.NORTH);
		
		JPanel buttonPanel = new JPanel();
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		JPanel mainPanel = new JPanel();
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new GridLayout(2, 2, 0, 0));
		
		JPanel membershipPanel = new JPanel();
		membershipPanel.setBorder(new TitledBorder(null, "Membership Type", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		mainPanel.add(membershipPanel);
		membershipPanel.setLayout(new BoxLayout(membershipPanel, BoxLayout.Y_AXIS));
		
		// RADIO BUTTONS
		rdbtnRegular = new JRadioButton("Regular");
		rdbtnRegular.setSelected(true);
		rdbtnRegular.addActionListener(this); // Handled by this class -- EXAMPLE # 1
		buttonGroup.add(rdbtnRegular);
		membershipPanel.add(rdbtnRegular);
		
		rdbtnStudent = new JRadioButton("Student");
		HandlerHelper helper = new HandlerHelper(); // Instance of Private Inner class -- EXAMPLE # 2
		rdbtnStudent.addChangeListener(helper); // Object "helper" is event listener
		buttonGroup.add(rdbtnStudent);
		membershipPanel.add(rdbtnStudent);
		
		rdbtnSponsored = new JRadioButton("Sponsored");
		rdbtnSponsored.addActionListener(new ActionListener() { // Anonymous Inner Class -- EXAMPLE #3
			public void actionPerformed(ActionEvent e) {
				calculateButtonActionPerformed(e);  // Implements actionPerformed by calling a method
			}                                       // (near the end of the file)
		});
		buttonGroup.add(rdbtnSponsored);
		membershipPanel.add(rdbtnSponsored); 
		
		
		JPanel daysPanel = new JPanel();
		daysPanel.setBorder(new TitledBorder(null, "Service (36 days max.)",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		mainPanel.add(daysPanel);
		
		// COMBO BOX 
		comboBox = new JComboBox<Integer>();
		comboBox.addActionListener(new ActionListener() { // Anonymous Inner Class -- EXAMPLE #4
			public void actionPerformed(ActionEvent e) {  // Implements actionPerformed by embedding code
				                                          // NOTE: Calculate Button uses this same approach
				//Daily Rate
				if (rdbtnRegular.isSelected()) dailyRate = REG_RATE;
				if (rdbtnStudent.isSelected()) dailyRate = STD_RATE;
				if (rdbtnSponsored.isSelected()) dailyRate = SPN_RATE;
				
				//Options
				if (chckbxWifi.isSelected()) dailyRate += WIFI_RATE;
				if (chckbxRoom.isSelected()) dailyRate += ROOM_RATE;
				if (chckbxFood.isSelected()) dailyRate += FOOD_RATE;
				
				//Calculate cost
				subTotal = dailyRate * (int)comboBox.getSelectedItem();
				taxes = subTotal * TAX_RATE;
				total = taxes + subTotal;
				
				//Display Results
				txtSubtotal.setText(String.format("%,.2f", subTotal));
				txtTaxes.setText(currencyFormat.format(taxes));
				txtTotal.setText(currencyFormat.format(total));
			}
		});
		comboBox.setModel(new DefaultComboBoxModel<Integer>(DAYOPTIONS_INTEGERS));
		daysPanel.add(comboBox);
		
		JPanel optionsPanel = new JPanel();
		optionsPanel.setBorder(new TitledBorder(null, "Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		mainPanel.add(optionsPanel);
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
		
		// CHECK BOXES
		                                       // REUSE existing handlers -- Example #5
		chckbxWifi = new JCheckBox("Wi-Fi");
		chckbxWifi.addChangeListener(helper);  // Reuse ChangeListener created for rdbtnStudent
		optionsPanel.add(chckbxWifi);
		
		chckbxRoom = new JCheckBox("Conference Room");
		chckbxRoom.addChangeListener(helper);  // Reuse ChangeListener created for rdbtnStudent
		optionsPanel.add(chckbxRoom);
		
		chckbxFood = new JCheckBox("Coffee & Snacks");
		chckbxFood.addChangeListener(helper);  // Reuse ChangeListener created for rdbtnStudent
		optionsPanel.add(chckbxFood);
		
		JPanel resultsPanel = new JPanel();
		resultsPanel.setBorder(new TitledBorder(null, "Charges", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		mainPanel.add(resultsPanel);
		resultsPanel.setLayout(new GridLayout(3, 2, 0, 0));
		
		JLabel lblSubtotal = new JLabel("Sub-Total");
		lblSubtotal.setHorizontalAlignment(SwingConstants.RIGHT);
		resultsPanel.add(lblSubtotal);
		
		txtSubtotal = new JTextField();
		txtSubtotal.setEditable(false);
		resultsPanel.add(txtSubtotal);
		txtSubtotal.setColumns(10);
		
		JLabel lblTaxes = new JLabel("Taxes");
		lblTaxes.setHorizontalAlignment(SwingConstants.RIGHT);
		resultsPanel.add(lblTaxes);
		
		txtTaxes = new JTextField();
		txtTaxes.setEditable(false);
		resultsPanel.add(txtTaxes);
		txtTaxes.setColumns(10);
		
		JLabel lblTotal = new JLabel("Total");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		resultsPanel.add(lblTotal);
		
		txtTotal = new JTextField();
		txtTotal.setEditable(false);
		resultsPanel.add(txtTotal);
		txtTotal.setColumns(10);
		
		// CALCULATE BUTTON
		btnCalculate = new JButton("Calculate");
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Daily Rate
				if (rdbtnRegular.isSelected()) dailyRate = REG_RATE;
				if (rdbtnStudent.isSelected()) dailyRate = STD_RATE;
				if (rdbtnSponsored.isSelected()) dailyRate = SPN_RATE;
				
				//Options
				if (chckbxWifi.isSelected()) dailyRate += WIFI_RATE;
				if (chckbxRoom.isSelected()) dailyRate += ROOM_RATE;
				if (chckbxFood.isSelected()) dailyRate += FOOD_RATE;
				
				//Calculate cost
				subTotal = dailyRate * (int)comboBox.getSelectedItem();
						//Integer.parseInt(comboBox.getSelectedItem().toString());
				taxes = subTotal * TAX_RATE;
				total = taxes + subTotal;
				
				//Display Results
				txtSubtotal.setText(String.format("%,.2f", subTotal));
				txtTaxes.setText(currencyFormat.format(taxes));
				txtTotal.setText(currencyFormat.format(total));
			}
		});
		buttonPanel.add(btnCalculate);

 		
		// CLEAR BUTTON
		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnRegular.setSelected(true);
				chckbxWifi.setSelected(false);
				chckbxRoom.setSelected(false);
				chckbxFood.setSelected(false);
				comboBox.setSelectedIndex(0);
				txtSubtotal.setText(null);
				txtTaxes.setText(null);
				txtTotal.setText(null);
				
			}
		});
		buttonPanel.add(btnClear);
		
		
		// EXIT BUTTON
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		buttonPanel.add(btnExit);
	}

	// ActionPerformed Method managed by myOfficeApp (this Class)
	@Override
	public void actionPerformed(ActionEvent e) {
		calculateButtonActionPerformed(e);
	}

	private void calculateButtonActionPerformed(ActionEvent e) {
		//Daily Rate
		if (rdbtnRegular.isSelected()) dailyRate = REG_RATE;
		if (rdbtnStudent.isSelected()) dailyRate = STD_RATE;
		if (rdbtnSponsored.isSelected()) dailyRate = SPN_RATE;
		
		//Options
		if (chckbxWifi.isSelected()) dailyRate += WIFI_RATE;
		if (chckbxRoom.isSelected()) dailyRate += ROOM_RATE;
		if (chckbxFood.isSelected()) dailyRate += FOOD_RATE;
		
		//Calculate cost
		subTotal = dailyRate * (int)comboBox.getSelectedItem();
		taxes = subTotal * TAX_RATE;
		total = taxes + subTotal;
		
		//Display Results
		txtSubtotal.setText(String.format("%,.2f", subTotal));
		txtTaxes.setText(currencyFormat.format(taxes));
		txtTotal.setText(currencyFormat.format(total));
	}
	
	private class HandlerHelper implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			calculateButtonStateChanged(e);
			
		}

		private void calculateButtonStateChanged(ChangeEvent e) {
			//Daily Rate
			if (rdbtnRegular.isSelected()) dailyRate = REG_RATE;
			if (rdbtnStudent.isSelected()) dailyRate = STD_RATE;
			if (rdbtnSponsored.isSelected()) dailyRate = SPN_RATE;
			
			//Options
			if (chckbxWifi.isSelected()) dailyRate += WIFI_RATE;
			if (chckbxRoom.isSelected()) dailyRate += ROOM_RATE;
			if (chckbxFood.isSelected()) dailyRate += FOOD_RATE;
			
			//Calculate cost
			subTotal = dailyRate * (int)comboBox.getSelectedItem();
			taxes = subTotal * TAX_RATE;
			total = taxes + subTotal;
			
			//Display Results
			txtSubtotal.setText(String.format("%,.2f", subTotal));
			txtTaxes.setText(currencyFormat.format(taxes));
			txtTotal.setText(currencyFormat.format(total));
		}
		
	}

}
