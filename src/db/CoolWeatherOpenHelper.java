package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CoolWeatherOpenHelper extends SQLiteOpenHelper {

	//开始建表语句的书写
	public static final String CREATE_PROVINCE = "creata table Province("
			+ "id integer primary key autoincrement, " 
			+ "province_name text, "
			+ "province_code text)";

	public static final String CREATE_CITY = "create table City("
			+ "id integer primary key autoincrement," 
			+ "city_name text,"
			+ "city_code text," 
			+ "province_id integer )";
	
	public static final String CREATE_COUNTY = "create table County(" +
			"id integer primary key autoincrement," +
			"county_name text," +
			"county_code text," +
			"city_id integer )";
	
	public CoolWeatherOpenHelper(Context context, String name ,CursorFactory
			 factory, int version){
		super(context , name ,factory , version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//建表三张
		db.execSQL(CREATE_PROVINCE);
		db.execSQL(CREATE_CITY);
		db.execSQL(CREATE_COUNTY);
		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
}
