package com.pioneer.aaron.cityfinder.finder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.pioneer.aaron.cityfinder.R;

import java.io.*;

/**
 * Data manage class
 *
 * @author Aaron
 *
 */
public class DBManager {
	private final int BUFFER_SIZE = 400000;
	private static final String PACKAGE_NAME = "com.pioneer.aaron.cityfinder";
	public static final String DB_NAME = "china_city_name.db";
	public static final String DB_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME ;
	private Context mContext;
	private SQLiteDatabase database;

	public DBManager(Context context)
	{
		this.mContext = context;
	}

	/**
	 * open data base
	 */
	public void openDateBase() {
		this.database = this.openDateBase(DB_PATH + "/" + DB_NAME);

	}

	/**
	 * 
	 * @param dbFile
	 * @return SQLiteDatabase
	 * @author Aaron
	 */
	private SQLiteDatabase openDateBase(String dbFile)
	{
		File file = new File(dbFile);
		if (!file.exists())
		{
			// // rawеlstream
			InputStream stream = this.mContext.getResources().openRawResource(R.raw.china_city_name);
			try
			{

				// stream дdata
				FileOutputStream outputStream = new FileOutputStream(dbFile);
				byte[] buffer = new byte[BUFFER_SIZE];
				int count = 0;
				while ((count = stream.read(buffer)) > 0)
				{
					outputStream.write(buffer, 0, count);
				}
				outputStream.close();
				stream.close();
				SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
				return db;
			} catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return database;
	}

	public void closeDatabase()
	{
		if (database != null && database.isOpen())
		{
			this.database.close();
		}
	}
}
