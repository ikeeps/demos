package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

public class ZipUtils {

  public static void main(String[] args) throws IOException {
    Path path = Paths.get("/temp/aaa");
    File file = path.toFile();

    if (!file.exists()) {
      file.mkdirs();
    }

    // printZipEntry("C:/Users/ikeeps/Documents/Tencent
    // Files/3327963573/FileRecv/aaa.zip");
     renameAll("C:/Users/ikeeps/Documents/Tencent Files/3327963573/FileRecv/aaa.zip", "master2");

  }

  public static void unZip(String file, Path root) throws IOException {
    try (ZipFile zf = new ZipFile(file);) {
      Enumeration<? extends ZipEntry> entries = zf.entries();
      while (entries.hasMoreElements()) {
        ZipEntry nextElement = entries.nextElement();
        if (nextElement.isDirectory()) {
          System.out.println(nextElement.getName());
          Path resolve = root.resolve(nextElement.getName());
          resolve.toFile().mkdirs();
        } else {
          System.out.println(nextElement.getName());
          InputStream zis = zf.getInputStream(nextElement);
          Path resolve = root.resolve(nextElement.getName());
          try (FileOutputStream fos = new FileOutputStream(resolve.toFile())) {
            IOUtils.copy(zis, fos);
          }
        }
      }
    }
  }

  public static void printZipEntry(String file) throws IOException {
    Path path = Paths.get(file);

    try (FileSystem zipFs = FileSystems.newFileSystem(path, null)) {
      Path root = zipFs.getPath("/");
      try (Stream<Path> walk = Files.walk(root)) {
        List<Path> collect = walk.collect(Collectors.toList());
        System.out.println(collect);
      }
    }
  }

  public static ZipEntry copyEntry(ZipEntry originEntry, String newName) {
    ZipEntry outEntry = new ZipEntry(newName);
    outEntry.setExtra(originEntry.getExtra());
    outEntry.setComment(originEntry.getComment());
    return outEntry;
  }
  
  public static void renameAll(String originalZip, String newFile) throws IOException {
    Path originalZipPath = Paths.get(originalZip);
    
    String extension = FilenameUtils.getExtension(originalZip);
    String baseName = FilenameUtils.getBaseName(originalZip);
    Path newZipPath = originalZipPath.resolveSibling(newFile + "." + extension);
    
    try (ZipFile zf = new ZipFile(originalZip);
         ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(newZipPath.toFile()))
           ) {
      Enumeration<? extends ZipEntry> entries = zf.entries();
      while (entries.hasMoreElements()) {
        ZipEntry originEntry = entries.nextElement();
        ZipEntry outEntry = copyEntry(originEntry, originEntry.getName().replace(baseName, newFile));
        if (originEntry.isDirectory()) {
          zos.putNextEntry(outEntry);
        } else {
          InputStream inputStream = zf.getInputStream(originEntry);
          zos.putNextEntry(outEntry);
          IOUtils.copy(inputStream, zos);
        }
      }
    }
  }
}
