package _import;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;


public class Frame extends JFrame implements ActionListener,MouseListener{
	private File file;
	private JButton import_Classbtn = new JButton("导入班级信息");
	private JButton import_studentbtn = new JButton("导入学生信息");
	private JButton command = new JButton("执行");
	private JButton viewbtn = new JButton("浏览");
	private JTextField sqlText = new JTextField("在此输入sql语句");
	private JTextField fileText = new JTextField();
	private JTable table;
	protected DefaultTableModel mymodel;
	public Frame() {
		// TODO Auto-generated constructor stub
		//初始化面板
		super("服务器应用");
		this.setResizable(false);
	    this.setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	
		this.setLayout(new BorderLayout());
		JPanel btnPanel = new JPanel();
		JPanel sqlPanel = new JPanel();
		JPanel viewPanel = new JPanel();
		JPanel mainPanel = new JPanel();
		JPanel viewTable = new JPanel();
		//界面大小和位置
		this.setSize(new Dimension(700,570));	
		sqlText.setSize(new Dimension(100, 110));
		//布局方式设置
		btnPanel.setLayout(new FlowLayout());	
		btnPanel.setBorder(new TitledBorder("导入操作"));
		btnPanel.setSize(new Dimension(100, 100));
		sqlPanel.setLayout(new FlowLayout());
		sqlPanel.setBorder(new TitledBorder("sql操作"));
		viewPanel.setLayout(new FlowLayout());
		viewPanel.setBorder(new TitledBorder("路径选择"));		
		//设置组件大小		
		//添加组件	
		mainPanel.add(viewPanel);
		mainPanel.add(btnPanel);
		mainPanel.add(sqlPanel);
		btnPanel.add(import_Classbtn);btnPanel.add(import_studentbtn);
		//sqlText初设置
		sqlText.setColumns(30);
		sqlText.setForeground(Color.GRAY);
		sqlPanel.add(sqlText);sqlPanel.add(command);
		fileText.setColumns(20);
		viewbtn.setSize(new Dimension(10, 10));
		viewPanel.add(fileText);viewPanel.add(viewbtn);	
		mymodel = new DefaultTableModel(0,0);
		table = new JTable(mymodel);
		table.setSize(100, 100);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setVerticalScrollBarPolicy(scrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(600, 300));
		viewTable.add(scrollPane);
		viewTable.setBorder(new TitledBorder("查询结果"));
		mainPanel.add(viewTable);
		this.getContentPane().add(mainPanel);
		
		
		//注册监听器
		import_Classbtn.addActionListener(this);
		import_studentbtn.addActionListener(this);
		sqlText.addActionListener(this);
		sqlText.addMouseListener(this);
		viewbtn.addActionListener(this);
		command.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String action=e.getActionCommand();
		if(action.equals("导入班级信息")&&(new JOptionPane().showConfirmDialog(this, "您确定要导入班级信息？"))==JOptionPane.YES_OPTION) {
			if(file!=null) {
				try {
					importClass.importFile(file);
				}catch (Exception e2) {
					new JOptionPane().showMessageDialog(this, "导入班级错误");
				}
			}
		}
		else if(action.equals("导入学生信息")) {
			
			if(file!=null&&(new JOptionPane().showConfirmDialog(this, "您确定要导入学生信息？"))==JOptionPane.YES_OPTION)
				try {
					importStudent.importFile(file);
				}
				catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					new JOptionPane().showMessageDialog(this, "导入学生错误");
				}
		}
		else if(action.equals("执行")) {
			System.out.println("sdsd");
			if(!sqlText.getText().toString().equals("")) {
				ResultSet resultSet = Command.toCommand(sqlText.getText().toString());
			
				try {
					if(resultSet!=null) {
						mymodel = new DefaultTableModel(0,0);
						table.setModel(mymodel);
						ResultSetMetaData rsmd= resultSet.getMetaData();
						int count = rsmd.getColumnCount();
						for(int i = 1;i<= count;i++){
							mymodel.addColumn(rsmd.getColumnLabel(i));
						}
						Object[] colums = new Object[count];
						while(resultSet.next()){
							for(int j =1;j<=colums.length;j++){
								colums[j-1] = resultSet.getString(j);
							}
							mymodel.addRow(colums);
						}
						
						resultSet.close();
					}
				}catch (Exception e1) {
					// TODO: handle exception
					e1.printStackTrace();
					new JOptionPane().showMessageDialog(this, "输入SQL语句错误");
				}
			}						
		}
		else if(action.equals("浏览")) {
			viewFile();
		}
		
	}
	
	public void viewFile() {
		JFileChooser jfc=new JFileChooser(); 
		jfc.addChoosableFileFilter(new MyFileFilter());
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
        jfc.showOpenDialog(this);
        try {
        	file = jfc.getSelectedFile(); 
        	if(file!=null) {
        		fileText.setText(file.getAbsolutePath().toString());
        	}
        }catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
		}
	}
	public static void main(String args[]) {
		Frame test = new Frame();
		test.setVisible(true);
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(sqlText.getText().toString().equals("在此输入sql语句"))
		{
			sqlText.setText("");
			sqlText.setForeground(Color.BLACK);
		}
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(sqlText.getText().toString().equals("")){
			sqlText.setText("在此输入sql语句");
			sqlText.setForeground(Color.GRAY);
		}
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
