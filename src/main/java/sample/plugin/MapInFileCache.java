package sample.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.logging.Log;

/** Cache that use last modified date of file and store it in file. */
@Named
@Singleton
public class MapInFileCache implements StopListCache {

  /** Map for cache result. Key is String canonical path to file Value is lastModified of file */
  private HashMap<String, Long> lastModifiedCache = new HashMap<>();

  /** Path to file for cache result. */
  private static final String FILE_CACHE_PATH =
      "target/plugins-cache/stop-list/last-modified-files.cache";

  private Log logger;

  @Override
  public void setLogger(Log logger) {
    this.logger = logger;
  }

  @Override
  public boolean addToCache(File file) {
    try {
      String canonicalPath = file.getCanonicalPath();
      long lastModified = file.lastModified();
      lastModifiedCache.put(canonicalPath, lastModified);

      return true;
    } catch (IOException e) {
      logger.info("Unable to add file to cache: " + file.getPath());
      logger.debug(e);
      return false;
    }
  }

  @Override
  public boolean isFileChanged(File file) {
    try {
      String canonicalPath = file.getCanonicalPath();
      Long modifiedCache = lastModifiedCache.get(canonicalPath);
      if (modifiedCache == null) {
        return true;
      }

      return modifiedCache == file.lastModified();
    } catch (IOException e) {
      logger.info("Unable read file: " + file.getPath());
      logger.debug(e);
      return true;
    }
  }

  @Override
  public void beforeStart() {
    File file = new File(FILE_CACHE_PATH);
    if (!file.exists()) {
      return;
    }

    try (FileInputStream inputStream = new FileInputStream(FILE_CACHE_PATH);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
      lastModifiedCache = (HashMap) objectInputStream.readObject();
    } catch (Exception e) {
      logger.info("Unable deserialize cache from file: " + FILE_CACHE_PATH);
      logger.debug(e);
    }
  }

  @Override
  public void beforeClose() {
    // write cache to file
    File cacheFile = new File(FILE_CACHE_PATH);
    try {
      FileUtils.touch(cacheFile);
    } catch (IOException e) {
      logger.info("Unable create cache file: " + FILE_CACHE_PATH);
      logger.debug(e);
    }

    try (FileOutputStream outputStream = new FileOutputStream(cacheFile);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
      objectOutputStream.flush();
      objectOutputStream.writeObject(lastModifiedCache);
    } catch (IOException e) {
      logger.info("Unable create cache file: " + FILE_CACHE_PATH);
      logger.debug(e);
    }
  }
}
