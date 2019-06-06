package util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.zip.GZIPInputStream;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AnalysisTar {
  private static int count = 0;
  private static Map<LocalDate, Integer> date2Count = new HashMap<>();
  private static ObjectMapper mapper = new ObjectMapper();
  private static DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss").appendValue(ChronoField.MILLI_OF_SECOND, 3).toFormatter();
  
  public static void main(String[] args) throws IOException, ParseException {
    parseDate();
    
//    String tarPath = "D:\\share\\upload.tar.gz";
//    tarToLine(tarPath, AnalysisTar::analysis);
//    date2Count.forEach((key, v) -> {
//      System.out.println(key + "\t" + v);
//    });
//    
//    System.out.println(count);
  }
  
  public static void parseDate() {
    String dateStr = "2018-11-20 06:56:20.093";
    
    LocalDateTime parse = LocalDateTime.parse(dateStr, formatter);
    System.out.println(parse);
  }
  
  public static void tarToLine(String tarPath, Consumer<String> consumer) throws IOException {
    try (TarArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(new GZIPInputStream(new FileInputStream(tarPath)));) {
      ArchiveEntry originEntry;
      while ((originEntry = tarArchiveInputStream.getNextEntry()) != null) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(tarArchiveInputStream));
        String readLine;
        while ((readLine = bufferedReader.readLine()) != null) {
          consumer.accept(readLine);          
        }
      }
    }
  }
  
  public static void analysis(String readLine) {
    if (StringUtils.isBlank(readLine)) {
      return;
    }
    HashMap readValue;
    try {
      readValue = mapper.readValue(readLine, HashMap.class);
      String timeStamp = (String) readValue.get("LastTimeStamp");
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
      Date parse = simpleDateFormat.parse(timeStamp);
//    LocalDateTime parsedTimeStamp = LocalDateTime.parse(timeStamp, formatter);
//    LocalDate localDate = parsedTimeStamp.toLocalDate();
      LocalDate localDate = parse.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      Integer dateCount = date2Count.getOrDefault(localDate, 0);
      date2Count.put(localDate, dateCount + 1);
      count++;
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }

  }
}
