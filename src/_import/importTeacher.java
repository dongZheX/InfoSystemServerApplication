package _import;

import java.io.File;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class importTeacher {
	public static void importTeacher(File file) throws Exception {
		//���ݿ�����
		DBManager dbManager = DBManager.createInstance();
	    dbManager.initDB();
		dbManager.connectDB("Super", "1097300052dz");
		
		Workbook workbook;
		
			workbook = Workbook.getWorkbook(file);
			Sheet sheet1 = workbook.getSheet(0);//��ȡ������
			int rows = sheet1.getRows();//��ȡ����
			for(int i = 1;i<rows;i++) {
				Cell[] cells = sheet1.getRow(i);//��ȡÿ��
				if(cells.length==0)
					continue;
				String sql_insert="insert into teacher values("; 
				for(int j =0;j<cells.length;j++) {//��ȡÿ�е�ÿ��ֵ
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
