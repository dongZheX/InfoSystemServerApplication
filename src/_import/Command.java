package _import;

import java.sql.ResultSet;

public class Command {
	public static ResultSet toCommand(String sql) {
		//数据库连接
		DBManager dbManager = DBManager.createInstance();
		dbManager.initDB();
		dbManager.connectDB("Super", "1097300052dz");
		if(sql.indexOf("select")>=0||sql.indexOf("Select")>=0) {
			return dbManager.executeQuery(sql);	
		}
		else {
			 dbManager.executeUpdate(sql);
			 return null;
		}
	}
}
