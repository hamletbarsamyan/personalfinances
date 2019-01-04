package am.jsl.personalfinances.service;

import am.jsl.personalfinances.dao.BaseDao;
import am.jsl.personalfinances.ex.CannotDeleteException;
import am.jsl.personalfinances.log.AppLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * The service implementation of the {@link BaseService}.
 * @author hamlet
 * @param <T> the parametrisation entity type.
 */
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class BaseServiceImpl<T> implements BaseService<T> {
    protected static final AppLogger log = new AppLogger(BaseServiceImpl.class);

    /**
     * Flag indicating that user objects should be published as html files.
     */
    @Value("${personalfinances.user.publish.html}")
    protected boolean publishHtml;

    /**
     * Folder where will be stored user html files.
     */
    @Value("${personalfinances.user.html.dir}")
    protected String userHtmlDir;

    /**
     * The base dao.
     */
    private BaseDao<T> baseDao;

    @Override
    public List<T> list(long userId) {
        return baseDao.list(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void delete(long id, long userId) throws CannotDeleteException {
        if (!baseDao.canDelete(id, userId)) {
            throw new CannotDeleteException();
        }
        baseDao.delete(id, userId);
    }

    @Override
    public boolean exists(String name, long id, long userId)  {
        return baseDao.exists(name, id, userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void create(T object) throws Exception {
        baseDao.create(object);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void update(T object) throws Exception {
        baseDao.update(object);
    }

    @Override
    public T get(long id, long userId) {
        return baseDao.get(id, userId);
    }

    @Override
    public List<T> lookup(long userId) {
        return baseDao.lookup(userId);
    }

    @Override
    public void publish(long userId, String fileName, String content) {
        StringBuilder filePath = new StringBuilder();
        filePath.append(userHtmlDir).append(userId);
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            File file = new File(filePath.toString());

            if (!file.isDirectory()) {
                file.mkdir();
            }

            filePath.append(File.separator).append(fileName);

            file = new File(filePath.toString());
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(content);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (final IOException ioe) {
                // ignore
            }
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (final IOException ioe) {
                // ignore
            }
        }
    }

    /**
     * Setter for property 'baseDao'.
     *
     * @param baseDao Value to set for property 'baseDao'.
     */
    public void setBaseDao(BaseDao<T> baseDao) {
        this.baseDao = baseDao;
    }
}
