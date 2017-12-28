package nodejt400;

import java.sql.Connection;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400JDBCConnectionHandle;
import com.ibm.as400.access.IFSFile;
import com.ibm.as400.access.IFSFileOutputStream;

public class IfsWriteStream {

	private ConnectionProvider connectionProvider;
	private final Connection connection;
	private final IFSFileOutputStream fos;

	public IfsWriteStream(ConnectionProvider connectionProvider, String folderPath, String fileName, boolean append)
			throws Exception {
		this.connectionProvider = connectionProvider;
		connection = connectionProvider.getConnection();
		AS400JDBCConnectionHandle handle = (AS400JDBCConnectionHandle) connection;
		AS400 as400 = handle.getSystem();
		IFSFile folder = new IFSFile(as400,folderPath);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		IFSFile file = new IFSFile(as400, folder, fileName);

		fos = new IFSFileOutputStream(file, IFSFileOutputStream.SHARE_ALL, append, 1208); //Japanese(UTF-8)
	}

	public void write(byte[] data) throws Exception {
		fos.write(data);
		fos.flush();
	}

	public void flush() throws Exception {
		fos.flush();
		fos.close();
		this.connectionProvider.returnConnection(this.connection);
	}
}
