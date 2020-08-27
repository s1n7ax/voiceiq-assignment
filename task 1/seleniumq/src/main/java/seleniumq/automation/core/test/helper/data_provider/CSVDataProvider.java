package seleniumq.automation.core.test.helper.data_provider;

import org.testng.annotations.DataProvider;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CSVDataProvider {
    List<String[]>  data = new ArrayList<>();

    private BufferedReader getBF(String fileName) throws UnsupportedEncodingException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(fileName);
        return new BufferedReader(new InputStreamReader(is, "UTF-8"));
    }

    public String[][] getData(String fileName) throws UnsupportedEncodingException {
        BufferedReader bf = getBF(fileName);

        bf.lines().forEach((str) -> {
            String[] values = str.split(",");
            data.add(values);
        });

        return data.stream().map(u -> u).toArray(String[][]::new);
    }

    @DataProvider(name="yolaData")
    public Object[][] getData() throws UnsupportedEncodingException {
        return getData("data/data.csv");
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        CSVDataProvider c = new CSVDataProvider();
        System.out.println(c.getData("data/data.csv")[0][1]);
    }
}
