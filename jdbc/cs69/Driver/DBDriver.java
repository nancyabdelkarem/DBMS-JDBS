package eg.edu.alexu.csd.oop.jdbc.cs69.Driver;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.jdbc.cs69.Connection.DBConnectionImp;

public class DBDriver implements Driver{
	private static final Pattern pattern = Pattern.compile("jdbc:(\\w+)db://localhost");
	static {
        try {
            DriverManager.registerDriver(new DBDriver());
        } catch (final SQLException e) {
        }
    }

    public DBDriver() {
    }

	@Override
	public boolean acceptsURL(String url) throws SQLException {
		 final Matcher m = pattern.matcher(url);
		  if (!m.matches()){
			  return false;
		  }
		return true;
	}
	@Override
	public Connection connect(String url, Properties info) throws SQLException {
		if(acceptsURL(url)){
		 final Matcher m = pattern.matcher(url);
		 m.matches();
		 url=m.group(1);
		 final File dir = (File) info.get("path");
		 final String path = dir.getAbsolutePath();
		 return new DBConnectionImp(url, path);
		}else{
			throw new SQLException("Invalid URL");
		}
	}

	/** Parameters: url - the URL of the database to which to connect info - a proposed list of
	 * tag/value pairs that will be sent on connect open Returns: an array of DriverPropertyInfo
	 * objects describing possible properties. This array may be an empty array if no properties are
	 * required. Throws: SQLException - if a database access error occurs
	 *
	 * DriverPropertyInfo(String name, String value) Constructs a DriverPropertyInfo object with a
	 * given name and value.
	 */
	@Override
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		if(!acceptsURL(url)){
			throw new SQLException("Invalid URL");
		}
		final DriverPropertyInfo propertyInfos[] = new DriverPropertyInfo[info.keySet().size()];
		final Iterator<Object> itr = info.keySet().iterator();
		int counter = 0;
		while (itr.hasNext()) {
			final String str = (String) itr.next();
			propertyInfos[counter++] = new DriverPropertyInfo(str, info.getProperty(str));
		}
		return propertyInfos;
	}



	@Override
	public int getMajorVersion(){
		throw new java.lang.UnsupportedOperationException();
	}


	@Override
	public int getMinorVersion() {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public Logger getParentLogger()  {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean jdbcCompliant() {
		throw new java.lang.UnsupportedOperationException();
	}


}
