package am.jsl.personalfinances.service.databasedump;

/**
 * Service interface which defines method for dumping database in the specified folder.
 * @author hamlet
 */
public interface DatabaseDumpService {

	/**
	 * Dumps the database in the specified folder and compresses the dump file.
	 */
	void dumpDatabase();
}
