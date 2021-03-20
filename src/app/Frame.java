package app;

import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import lib.DataManagement;
import lib.FileTypeFilter;

public class Frame extends JFrame {
	private lib.DataManagement manage = new DataManagement();
	private Table table = new Table();
	private String[] names = {"수익금", "지출금"};
	private String[] InOut = {"수익", "지출"};	
	private JTextField IncomeText = new JTextField(20);
	private JTextField OutcomeText = new JTextField(20);
	private JLabel bal;
	
	Frame(){	
		Vector<DataManagement> vec = new Vector<DataManagement>();
		
		setTitle("가계부");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(null);		
	
		lib.Time currentTime = new lib.Time();
		currentTime.start();
		currentTime.time.setSize(500,100);
		currentTime.time.setLocation(100,200);
		
		c.add(currentTime.time);	
		c.add(table.scroll);
		
		int x = 50;
		int y = 360;
		
		IncomeText.setLocation(x+50,y+10);
		IncomeText.setSize(100,30);
		c.add(IncomeText);
		
		OutcomeText.setLocation(x+50,y+60);
		OutcomeText.setSize(100,30);
		c.add(OutcomeText);
		
		bal = new JLabel("현재 잔액은 " + manage.getBalance() + "입니다.");
		bal.setSize(300,100);
		bal.setLocation(100,500);
		c.add(bal);
		
		for(int i =0; i < names.length;i++) {
			JLabel la = new JLabel(names[i]);
			la.setLocation(x,y);
			la.setSize(50,50);
			c.add(la);
			
			JButton btn = new JButton(InOut[i]);
			btn.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e){
						JButton b = (JButton)e.getSource();
						
						try {
							String[] inputStr = new String[4];
							inputStr[0] = currentTime.time.getText();
							DefaultTableModel mod= (DefaultTableModel)table.tab.getModel();
							if(b.getText().equals("수익")) {
								manage.setIncome(Integer.parseInt(IncomeText.getText()));
								inputStr[1] = IncomeText.getText(); 
								IncomeText.setText("");
								inputStr[2] ="0";
								inputStr[3] = "현금";	
								vec.add(new DataManagement(inputStr[0],Integer.parseInt(inputStr[1]),Integer.parseInt(inputStr[2]),inputStr[3]));
								mod.addRow(inputStr);
							}else {
								manage.setOutcome(Integer.parseInt(OutcomeText.getText()));
								inputStr[1]="0";
								inputStr[2]= OutcomeText.getText();	
								inputStr[3]="현금";
								OutcomeText.setText("");
								vec.add(new DataManagement(inputStr[0],Integer.parseInt(inputStr[1]),Integer.parseInt(inputStr[2]),inputStr[3]));
								mod.addRow(inputStr);
							}
						}catch(Exception ex) {//경고창
							JOptionPane.showMessageDialog(null, "숫자를 입력하세요", "Error",JOptionPane.ERROR_MESSAGE);
						}
					
						
						bal.setText("현재 잔액은 " + manage.getBalance() + "원 입니다.");
						
					}
				
			});
			btn.setLocation(x+160,y);
			btn.setSize(70,40);
			c.add(btn);
			y+=50;
			
		}
		
		
		JButton save = new JButton("저장하기");
		save.setSize(100,30);
		save.setLocation(300,500);
		save.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JFileChooser fc = new JFileChooser(new File("c:\\"));
				fc.setDialogTitle("저장하기");
				fc.setFileFilter(new FileTypeFilter(".dat", "Data File"));
				int result = fc.showSaveDialog(null);
				DefaultTableModel mod= (DefaultTableModel)table.tab.getModel();	
				if(result == JFileChooser.APPROVE_OPTION) {
					
					String[] currentTimeTmp = new String[mod.getRowCount()];
					int [] incomeTmp = new int[mod.getRowCount()];
					int [] outcomeTmp = new int[mod.getRowCount()];
					String [] paymentMethodTmp = new String[mod.getRowCount()];
					
					for(int i = 0; i < mod.getRowCount(); i++) {
						currentTimeTmp[i] = vec.get(i).getTime();
						incomeTmp[i] = vec.get(i).getTotalIncome();
						outcomeTmp[i] = vec.get(i).getTotalOutcome();
						paymentMethodTmp[i] = vec.get(i).getPaymentMethod();
						
					}
					
					
					File file =fc.getSelectedFile();
					try {
						PrintWriter pw = new PrintWriter(file.getPath() + ".dat");
						for(int i = 0; i < incomeTmp.length; i++) {
							pw.write(currentTimeTmp[i]);
							pw.print("/");
							pw.print(incomeTmp[i]);
							pw.print("/");
							pw.print(outcomeTmp[i]);
							pw.print("/");
							pw.write(paymentMethodTmp[i]);
							pw.write("\n");
							
						}
						
						pw.flush();
						pw.close();
					}catch(Exception ex) {
						JOptionPane.showMessageDialog(null,ex.getMessage());
					}				
				}
			}
		});
		
		c.add(save);
		
		JButton load = new JButton("불러오기");
		load.setSize(100,30);
		load.setLocation(410,500);
		load.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JFileChooser fs = new JFileChooser(new File("c:\\"));
				fs.setDialogTitle("불러오기");
				fs.setFileFilter(new FileTypeFilter(".dat","Data File"));
				int result = fs.showOpenDialog(null);
				
				String currentTmp;
				int incomeTmp;
				int outcomeTmp;
				String paymentMethodTmp;
				StringTokenizer tk;
				DefaultTableModel mod= (DefaultTableModel)table.tab.getModel();
				mod.setNumRows(0);
				if(result == JFileChooser.APPROVE_OPTION) {
					try {
						File fi = fs.getSelectedFile();
						BufferedReader br = new BufferedReader(new FileReader(fi.getPath()));
						String s;
						String[] outputStr = new String[4];
						
					
						
						while((s=br.readLine()) != null) {
							tk = new StringTokenizer(s,"/");
							
							currentTmp = tk.nextToken();
							outputStr[0] = currentTmp;
							outputStr[1] = tk.nextToken();
							incomeTmp = Integer.parseInt(outputStr[1]);
							manage.setIncome(incomeTmp);
							outputStr[2] = tk.nextToken();
							outcomeTmp = Integer.parseInt(outputStr[2]);
							manage.setOutcome(outcomeTmp);
							paymentMethodTmp = tk.nextToken();
							outputStr[3] = paymentMethodTmp;
							
							vec.add(new DataManagement(currentTmp,incomeTmp,outcomeTmp,paymentMethodTmp));
							mod.addRow(outputStr);
							
						}
						
						bal.setText("현재 잔액은 " + manage.getBalance() + "입니다.");
						bal.setSize(300,100);
						bal.setLocation(100,500);
						c.add(bal);
						
						if(br!=null)
							br.close();
						}catch(Exception e2) {
							JOptionPane.showMessageDialog(null, e2.getMessage());
						}
				}
				
			}
		});
		
		c.add(load);
				
		setSize(1920,1080);
		setVisible(true);
	}
	
	
}

