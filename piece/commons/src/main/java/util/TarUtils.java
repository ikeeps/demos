package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.io.IOUtils;

public class TarUtils {
  
  public static void main(String[] args) throws IOException {
    renameAll("C:\\Users\\ikeeps\\Documents\\Tencent Files\\3327963573\\FileRecv\\18110610001.tar.gz", "18110610001.tar.gz", "newmaster");
  }
  
  public static File renameAll(String originalTar, String originFileName, String newFile) throws IOException {
    Path originalTarPath = Paths.get(originalTar);
    String extension = ".tar.gz";
    String baseName = originFileName.replace(extension, "");

    Path newTarPath = originalTarPath.resolveSibling(newFile + extension);

    try (TarArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(new GZIPInputStream(new FileInputStream(originalTar)));
            TarArchiveOutputStream tarArchiveOutStream = new TarArchiveOutputStream(new GZIPOutputStream(new FileOutputStream(newTarPath.toFile())));) {
      ArchiveEntry originEntry;

      while ((originEntry = tarArchiveInputStream.getNextEntry()) != null) {
        String newEntryName = originEntry.getName().replace(baseName, newFile);
        TarArchiveEntry tarArchiveEntry = new TarArchiveEntry(newEntryName);
        tarArchiveEntry.setSize(originEntry.getSize());
        if (originEntry.isDirectory()) {
          tarArchiveOutStream.putArchiveEntry(tarArchiveEntry);
        } else {
          tarArchiveOutStream.putArchiveEntry(tarArchiveEntry);
          IOUtils.copy(tarArchiveInputStream, tarArchiveOutStream);
        }
        tarArchiveOutStream.closeArchiveEntry();
      }
    }
    return newTarPath.toFile();
  }
}
