package _import;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.SQLException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class importClass {
	public static void importFile(File file) throws Exception{
		//数据库连接
		DBManager dbManager = DBManager.createInstance();
	    dbManager.initDB();
	    dbManager.connectDB("Super", "1097300052dz");
	  
	    	Workbook workbook = Workbook.getWorkbook(file);
	    	Sheet sheet1 = workbook.getSheet(0);//获取工作表
	    	int rows = sheet1.getRows();//获取行数
	    	String sql = "{call add_class(?,?,?)}";
			for(int i = 1;i<rows;i++) {
				Cell[] cells = sheet1.getRow(i);//获取每行
				if(cells.length==0)
					continue;
				CallableStatement proc = dbManager.conn.prepareCall(sql);
				for(int j =0;j<cells.length;j++) {//获取每行的每个值
					
					try {
						proc.setString(j+1, cells[j].getContents());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				String sql2 = "select * from class where Class_id = '"+cells[2].getContents()+"';";
				
				if((dbManager.executeQuery(sql2).next()))
				proc.executeQuery();
			}
	   
	}
}
