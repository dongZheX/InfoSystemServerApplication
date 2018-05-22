package _import;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class importStudent {
	public static void importFile(File file) throws Exception {
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
				String sql_insert="insert into student_all values("; 
				for(int j =0;j<cells.length;j++) {//��ȡÿ�е�ÿ��ֵ
					sql_insert+="'"+cells[j].getContents()+"',";
				}
				sql_insert = sql_insert.substring(0,sql_insert.length()-1);
				sql_insert+=");";
				System.out.println(sql_insert);
				dbManager.executeUpdate(sql_insert);
				
			}
		
		
	}
}
