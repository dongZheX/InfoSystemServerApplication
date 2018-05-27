package _import;

import java.io.File;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class importTeacher {
	public static void importTeacher(File file) throws Exception {
		//数据库连接
		DBManager dbManager = DBManager.createInstance();
	    dbManager.initDB();
		dbManager.connectDB("Super", "1097300052dz");
		
		Workbook workbook;
		
			workbook = Workbook.getWorkbook(file);
			Sheet sheet1 = workbook.getSheet(0);//获取工作表
			int rows = sheet1.getRows();//获取行数
			for(int i = 1;i<rows;i++) {
				Cell[] cells = sheet1.getRow(i);//获取每行
				if(cells.length==0)
					continue;
				String sql_insert="insert into teacher values("; 
				for(int j =0;j<cells.length;j++) {//获取每行的每个值
					sql_insert+="'"+cells[j].getContents()+"',";
				}
				sql_insert = sql_insert.substring(0,sql_insert.length()-1);
				sql_insert+=");";
				System.out.println(sql_insert);
				String sql2 = "select * from teacher where Teach_course = '"+cells[1].getContents()+"';";
				if(!dbManager.executeQuery(sql2).next())
				dbManager.executeUpdate(sql_insert);
				
			}
		
		
	}
}
