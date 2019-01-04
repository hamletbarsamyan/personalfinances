package am.jsl.personalfinances.service.databasedump;

import am.jsl.personalfinances.log.AppLogger;
import am.jsl.personalfinances.service.ErrorTrackerService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * The service implementation of the {@link DatabaseDumpService}.
 * @author hamlet
 */
@Service("databaseDumpService")
public class DatabaseDumpServiceImpl implements DatabaseDumpService {
    private static final AppLogger log = new AppLogger(DatabaseDumpServiceImpl.class);

    @Value("${personalfinances.db.export.enabled}")
    private boolean exportEnabled = false;

    @Value("${personalfinances.db.export.cmd}")
    private String exportCmd;

    @Value("${personalfinances.db.export.file.prefix}")
    private String exportFilePrefix;

    @Value("${personalfinances.db.export.filedir}")
    private String exportFiledir;

    @Value("${personalfinances.db.export.files_count}")
    private int exportFilesCount;

    @Autowired
    private ErrorTrackerService errorTrackerService;

    @Override
    public void dumpDatabase() {
        if (!exportEnabled) {
            return;
        }

        try {
            log.info("Delete old dumps");

            File exportFolder = new File(exportFiledir);
            File[] files = exportFolder.listFiles();

            if (files != null) {
                int filesCount = files.length;

                if ((filesCount - exportFilesCount) > 0) {
                    Arrays.sort(files, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));

                    Arrays.stream(files, exportFilesCount, filesCount).forEach(File::delete);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            errorTrackerService.sendError(e);
        }

        String fileName = null;
        String filePath = null;
        boolean exported = false;

        try {
            log.info("Dumping database");

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            fileName = exportFilePrefix + "_" + calendar.get(Calendar.YEAR) + "_" +
                    (calendar.get(Calendar.MONTH) + 1) + "_" + calendar.get(Calendar.DATE) +
                    "_" + calendar.get(Calendar.HOUR) + "_" +
                    calendar.get(Calendar.MINUTE) + "_dump.sql";

            filePath = exportFiledir + fileName;

            Process runtimeProcess = Runtime.getRuntime().exec(exportCmd + filePath);
            int processComplete = runtimeProcess.waitFor();

            if (processComplete == 0) {
                log.info("Backup taken successfully");
                exported = true;
            } else {
                log.info("Could not take mysql backup");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            errorTrackerService.sendError(e);
        }

        if (!exported) {
            return;
        }

        // zip file
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        FileInputStream in = null;

        try {
            byte[] buffer = new byte[1024];

            fos = new FileOutputStream(filePath + ".zip");
            zos = new ZipOutputStream(fos);

            ZipEntry ze = new ZipEntry(fileName);
            zos.putNextEntry(ze);
            in = new FileInputStream(filePath);

            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }

            FileUtils.deleteQuietly(new File(filePath));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            errorTrackerService.sendError(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // ignored
                }
            }

            if (zos != null) {
                try {
                    zos.closeEntry();
                    zos.close();
                } catch (IOException e) {
                    // ignored
                }
            }
        }
    }
}
