package energyConsumption;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;


import saveEnergy.StoreEnergy;

import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;
import javax.swing.ImageIcon;



public class UiEnergy {

	private JFrame frmExperimentalEnergyConsumption;
	private JTextField electricBillField;
	private JTextField applianceField;
	private JTextField powerField;
	private JTextField dailyUsageField;
	private JTable table;
	private JTextField totalEnergyField;
	private JTextField totalCostField;
	private JTextField textField_2;
	private JTextField origEnergyUsedField;
	private JTextField origCostEnergyField;
	private JTextField percentEnergyUsedField;
	private JTextField percentCostEnergyField;
    private JTextField energyCostField;
    
	static FileWriter filewriter;
	StoreEnergy st = new StoreEnergy();
	double[] usedEnergy = new double[100];
	double[] costCons = new double[100];
	int inc = 0;
	String appliances;
	double usageT = 0, energyCF = 0, energyUsed = 0, totalCostF = 0, euPercent = 0, ecPercent = 0;
	 
	
	public void uiMain() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UiEnergy window = new UiEnergy();
					window.frmExperimentalEnergyConsumption.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public UiEnergy() {
		init();
	}
	
	public void init() {
		initialize();
	}
	
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frmExperimentalEnergyConsumption = new JFrame();
		frmExperimentalEnergyConsumption.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Eclipse\\workspaze\\Assignment16\\src\\iconsave.png"));
		frmExperimentalEnergyConsumption.setTitle("Experimental Energy Consumption Calculator");
		frmExperimentalEnergyConsumption.getContentPane().setBackground(Color.RED);
		frmExperimentalEnergyConsumption.setBounds(100, 100, 782, 504);
		frmExperimentalEnergyConsumption.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmExperimentalEnergyConsumption.getContentPane().setLayout(null);
		
		try {
			filewriter = new FileWriter("D:\\Eclipse\\workspaze\\Assignment16\\src\\Appliances.csv", true);
		}catch (IOException exp) {
			exp.printStackTrace();
	    }
		
		inputBill();
		addAppliances();
		originalValue();
		expResults();
		percentDifference();
		tableEnergy();
		
		JButton btnCalculate = new JButton("CALCULATE");
		btnCalculate.setBounds(262, 250, 126, 27);
		frmExperimentalEnergyConsumption.getContentPane().add(btnCalculate);
		btnCalculate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { 
				
				
					double usageTotal = 0;
					for(int i = 0; i < inc; i++) {
						usageTotal += usedEnergy[i];
					}
					usageT = Math.round(usageTotal*100.0)/100.0;
					double energyC = st.getOrigBill() / usageTotal;
					energyCF = Math.round(energyC*100.0)/100.0;
					
					double totalCost = 0;
					for(int i = 0; i < inc; i++) {
						costCons[i] = energyC * usedEnergy[i];
						totalCost += costCons[i];
					}
					
					totalCostF = Math.round(totalCost*100.0)/100.0;
					expResults();
					
					
					double euPercentP = ((usageT - st.getOrigUsed())/st.getOrigUsed()) * 100;
					euPercent = Math.round(euPercentP*100.0)/100.0;
					double ecPercentP = ((totalCostF - st.getOrigBill())/st.getOrigBill()) * 100;
					ecPercent = Math.round(ecPercentP*100.0)/100.0;;
					percentDifference();
									
			}
		});
		
		
		JButton btnExit = new JButton("EXIT");
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					filewriter.close();
				}catch(Exception erz) {
					
				}
				
				System.exit(0);
			}
		});
		btnExit.setBounds(667, 250, 89, 23);
		frmExperimentalEnergyConsumption.getContentPane().add(btnExit);
		
		JButton btnReset = new JButton("RESET");
		btnReset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frmExperimentalEnergyConsumption.setVisible(false);
				MainEnergy.main(new String[] {});
			}
		});
		btnReset.setBounds(568, 250, 89, 23);
		frmExperimentalEnergyConsumption.getContentPane().add(btnReset);
		
		JLabel lblNewLabel_9 = new JLabel("");
		lblNewLabel_9.setIcon(new ImageIcon("D:\\Eclipse\\workspaze\\Assignment16\\src\\save.png"));
		lblNewLabel_9.setBounds(577, 10, 132, 119);
		frmExperimentalEnergyConsumption.getContentPane().add(lblNewLabel_9);
			
	}//end of initialize
	
	
	public void inputBill() {
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.ORANGE);
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 10, 242, 98);
		frmExperimentalEnergyConsumption.getContentPane().add(panel_1);
		panel_1.setLayout(null);
			
		JLabel lblNewLabel = new JLabel("Electric Bill Amount:");
		lblNewLabel.setBounds(10, 11, 117, 14);
		panel_1.add(lblNewLabel);
		
		electricBillField = new JTextField();
		electricBillField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				
				if(!Character.isDigit(c)) {
					e.consume();
				}
			}
		});
		electricBillField.setBounds(127, 8, 107, 20);
		panel_1.add(electricBillField);
		electricBillField.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("kWh Consumption:");
		lblNewLabel_8.setBounds(10, 36, 107, 14);
		panel_1.add(lblNewLabel_8);
		
		textField_2 = new JTextField();
		textField_2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				
				if(!Character.isDigit(c)) {
					e.consume();
				}
			}
		});
		textField_2.setBounds(127, 33, 107, 20);
		panel_1.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnNewButton = new JButton("SET");
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
				
				if((electricBillField.getText().length()==0)||(textField_2.getText().length()==0)) {
    				JOptionPane.showMessageDialog(null,"A Field is empty.");
    				}
    				else {
    					double elecBill = Double.parseDouble(electricBillField.getText());
    					double elecUsed = Double.parseDouble(textField_2.getText());
    					st.origBill(elecBill, elecUsed);
    					originalValue();
    					electricBillField.setText("");
    					textField_2.setText("");
    					}
				
				}catch(NumberFormatException exp) { 
	        		
        			System.out.println("Exception caught! "+ exp);
				}finally { 
    				System.out.println("Program recovers & continues.. "); 
    				}
			}
		});
		btnNewButton.setBounds(145, 64, 89, 23);
		panel_1.add(btnNewButton);
	}
	
	
	public void addAppliances() {
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.ORANGE);
		panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(10, 119, 242, 154);
		frmExperimentalEnergyConsumption.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Appliance:");
		lblNewLabel_1.setBounds(32, 11, 68, 14);
		panel_2.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Power(W):");
		lblNewLabel_2.setBounds(32, 36, 68, 14);
		panel_2.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Daily Usage(h):");
		lblNewLabel_3.setBounds(10, 61, 90, 14);
		panel_2.add(lblNewLabel_3);
		
		applianceField = new JTextField();
		applianceField.setBounds(104, 8, 107, 20);
		panel_2.add(applianceField);
		applianceField.setColumns(10);
		
		powerField = new JTextField();
		powerField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				
				if(!Character.isDigit(c)) {
					e.consume();
				}
			}
		});
		powerField.setBounds(104, 33, 107, 20);
		panel_2.add(powerField);
		powerField.setColumns(10);
		
		dailyUsageField = new JTextField();
		dailyUsageField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				
				if(!Character.isDigit(c)) {
					e.consume();
				}
			}
		});
		dailyUsageField.setBounds(104, 58, 107, 20);
		panel_2.add(dailyUsageField);
		dailyUsageField.setColumns(10);
		
		JButton btnAdd = new JButton("ADD");
		tableEnergy();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		Object[] row = new Object[5];
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					
					if((applianceField.getText().length()==0)||(powerField.getText().length()==0)||(dailyUsageField.getText().length()==0)) {
        				JOptionPane.showMessageDialog(null,"A Field is empty.");
        				}
        				else {
        					String applName = applianceField.getText();
        					double applPower = Double.parseDouble(powerField.getText());
        					double applUsage = Double.parseDouble(dailyUsageField.getText());
        					
        					double monUsage = applUsage * 30;
        					double eneUsed = applPower * 1/1000 * monUsage;
        					double eneUsedF = Math.round(eneUsed*100.0)/100.0;
        					usedEnergy[inc] = eneUsedF;
        					inc++;
        					
        					row[0] = applName;
        					row[1] = applPower;
        					row[2] = applUsage;
        					row[3] = monUsage;
        					row[4] = eneUsedF;

        				    model.addRow(row);
        					
        					appliances = applName + "," + applPower + "," + applUsage + "," + monUsage + "," + eneUsedF;
        					
        					filewriter.append(appliances);
        					filewriter.append('\n');
        					
        					applianceField.setText("");
        					powerField.setText("");
        					dailyUsageField.setText("");
        					}
					
					
					}catch(NumberFormatException exp) { 
		        		
	        			System.out.println("Exception caught! "+ exp);
	        			
					}catch(IOException exp) {
				    	System.out.println("Exception caught! "+ exp);
				  
					}finally { 
	    				System.out.println("Program recovers & continues.. "); 
	    				}
				}
		});
		btnAdd.setBounds(104, 88, 107, 28);
		panel_2.add(btnAdd);
	}
	
	
	public void originalValue(){
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.ORANGE);
		panel_3.setLayout(null);
		panel_3.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Original Value", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(262, 11, 242, 92);
		frmExperimentalEnergyConsumption.getContentPane().add(panel_3);
		
		JLabel lblNewLabel_4_1 = new JLabel("Total Energy Used:");
		lblNewLabel_4_1.setBounds(22, 25, 117, 14);
		panel_3.add(lblNewLabel_4_1);
		
		JLabel lblNewLabel_5_1 = new JLabel("Total Cost of");
		lblNewLabel_5_1.setBounds(10, 50, 77, 14);
		panel_3.add(lblNewLabel_5_1);
		
		JLabel lblNewLabel_6_1 = new JLabel("Energy Consumption:");
		lblNewLabel_6_1.setBounds(10, 64, 129, 14);
		panel_3.add(lblNewLabel_6_1);
		
		origEnergyUsedField = new JTextField();
		origEnergyUsedField.setText("P"+ st.getOrigBill());
		origEnergyUsedField.setEditable(false);
		origEnergyUsedField.setColumns(10);
		origEnergyUsedField.setBounds(133, 22, 86, 20);
		panel_3.add(origEnergyUsedField);
	
		origCostEnergyField = new JTextField();
		origCostEnergyField.setText("P"+ st.getOrigUsed());
		origCostEnergyField.setEditable(false);
		origCostEnergyField.setColumns(10);
		origCostEnergyField.setBounds(133, 61, 86, 20);
		panel_3.add(origCostEnergyField);
	}
	
	
	public void expResults() {
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.ORANGE);
		panel_4.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Experimental Results", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_4.setBounds(262, 114, 242, 125);
		frmExperimentalEnergyConsumption.getContentPane().add(panel_4);
		panel_4.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("Total Energy Used:");
		lblNewLabel_4.setBounds(22, 25, 114, 14);
		panel_4.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Total Cost of");
		lblNewLabel_5.setBounds(10, 50, 77, 14);
		panel_4.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Energy Consumption:");
		lblNewLabel_6.setBounds(10, 64, 126, 14);
		panel_4.add(lblNewLabel_6);
		
		totalEnergyField = new JTextField();
		totalEnergyField.setText("P" + usageT);
		totalEnergyField.setEditable(false);
		totalEnergyField.setBounds(134, 22, 86, 20);
		panel_4.add(totalEnergyField);
		totalEnergyField.setColumns(10);
		
		totalCostField = new JTextField();
		totalCostField.setText("P" + totalCostF);
		totalCostField.setEditable(false);
		totalCostField.setBounds(134, 61, 86, 20);
		panel_4.add(totalCostField);
		totalCostField.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Energy Cost:");
		lblNewLabel_7.setBounds(47, 96, 89, 14);
		panel_4.add(lblNewLabel_7);
		
		energyCostField = new JTextField();
		energyCostField.setText("P" + energyCF);
		energyCostField.setEditable(false);
		energyCostField.setBounds(134, 93, 86, 20);
		panel_4.add(energyCostField);
		energyCostField.setColumns(10);
	}
	
	
	public void percentDifference() {
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.ORANGE);
		panel_5.setLayout(null);
		panel_5.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Percent Difference", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_5.setBounds(514, 147, 242, 92);
		frmExperimentalEnergyConsumption.getContentPane().add(panel_5);
		
		JLabel lblNewLabel_4_1_1 = new JLabel("Total Energy Used:");
		lblNewLabel_4_1_1.setBounds(22, 25, 117, 14);
		panel_5.add(lblNewLabel_4_1_1);
		
		JLabel lblNewLabel_5_1_1 = new JLabel("Total Cost of");
		lblNewLabel_5_1_1.setBounds(10, 50, 77, 14);
		panel_5.add(lblNewLabel_5_1_1);
		
		JLabel lblNewLabel_6_1_1 = new JLabel("Energy Consumption:");
		lblNewLabel_6_1_1.setBounds(10, 64, 129, 14);
		panel_5.add(lblNewLabel_6_1_1);
		
		percentEnergyUsedField = new JTextField();
		percentEnergyUsedField.setText(Math.abs(euPercent) + "%");
		percentEnergyUsedField.setEditable(false);
		percentEnergyUsedField.setColumns(10);
		percentEnergyUsedField.setBounds(133, 22, 86, 20);
		panel_5.add(percentEnergyUsedField);
		
		percentCostEnergyField = new JTextField();
		percentCostEnergyField.setText(Math.abs(ecPercent) + "%");
		percentCostEnergyField.setEditable(false);
		percentCostEnergyField.setColumns(10);
		percentCostEnergyField.setBounds(133, 61, 86, 20);
		panel_5.add(percentCostEnergyField);
	}
	
	
	public void tableEnergy() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 284, 746, 170);
		frmExperimentalEnergyConsumption.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setBackground(Color.YELLOW);
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Appliance", "Power (W)", "Daily Usage (h)", "Monthly Usage (h)", "Energy Used (kWh)"
			}
		));
		
	}
}
