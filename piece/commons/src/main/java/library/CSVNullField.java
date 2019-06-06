package library;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class CSVNullField {
	
	public static void main(String[] args) throws IOException {
		File temp = File.createTempFile("trend", ".csv");
		writeDynamodbFile(temp);
		System.out.println(temp.getAbsolutePath());
	}

	private static File writeDynamodbFile(File temp)
					throws IOException {
		final CSVFormat csvMsyql = CSVFormat.MYSQL;
		
		int count = 0;
		List<String> record1 = Arrays.asList("2018-11-02", "67cb9f9c-9b72-40dc-940f-0fda0c4ce068", "SCTX-7CV7bbHD", "2018-11-02 14:46:25.066", "{\"355\":\"【OUTLET】大塚食品 ヴィシーセレスタン 500ml×24本\",\"338\":\"【アスマル限定セット】セメント　Trace Face　Knit Wear&オーガニックティー(バッグ\",\"723\":\"『FPGIRLS』ロゴのsticker🌼💜 ステッカー\"}", null, "{\"355\":\"4355\",\"338\":\"7144\",\"723\":\"1728\"}");
		List<String> record2 = Arrays.asList("2018-11-02", "9d2535a5-ca96-4a62-ad5e-9c04c7f24b70", "SCTX-7CV7bbHD", "2018-11-02 14:46:44.338", "{\"355\":\"【OUTLET】大塚食品 ヴィシーセレスタン 500ml×24本\",\"338\":\"【アスマル限定セット】セメント　Trace Face　Knit Wear&オーガニックティー(バッグ\",\"723\":\"『FPGIRLS』ロゴのsticker🌼💜 ステッカー\"}", null, "{\"355\":\"4355\",\"338\":\"7144\",\"723\":\"1728\"}");

		try (CSVPrinter print = csvMsyql.print(temp, Charset.forName("UTF-8"));) {
				print.printRecord(record1);
				print.printRecord(record2);
				count++;
		}
		return temp;
	}
}
