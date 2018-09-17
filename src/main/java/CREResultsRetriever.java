import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CREResultsRetriever implements Job {

    private List<CurrenciesExchangeRate> currenciesExchangeRateList = new ArrayList<CurrenciesExchangeRate>();

    private static CurrenciesExchangeRate createCurrenciesExchangeRate(Currencies first, Currencies second, String output) {
        String split[] = output.split(":");
        String exchangeValueString = split[1].replace("}", "");
        double exchangeValue = Double.parseDouble(exchangeValueString);
        return new CurrenciesExchangeRate(first, second, exchangeValue);
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            prepareCREResults();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (CurrenciesExchangeRate c : currenciesExchangeRateList) {
            System.out.println(c);
        }

    }

    private void prepareCREResults() throws IOException {
        Currencies currenciesTable[] = Currencies.values();

        for (Currencies first : currenciesTable) {
            for (Currencies second : currenciesTable) {

                if (!second.equals(first)) {
                    try {
                        URL url = new URL("https://free.currencyconverterapi.com/api/v6/convert?q=" + first + "_" + second + "&compact=ultra");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setRequestProperty("Accept", "application/json");

                        if (connection.getResponseCode() != 200) {
                            throw new RuntimeException("Failed : Http error code : " + connection.getResponseCode());
                        }

                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String output;
                        while ((output = bufferedReader.readLine()) != null) {
                            currenciesExchangeRateList.add(createCurrenciesExchangeRate(first, second, output));
                        }
                        connection.disconnect();
                    } catch (MalformedURLException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }

        LocalDate localDate = LocalDate.now();
        File currencies = new File("dates"+ File.separator+localDate + "-Currencies Rate.txt");
        addResultsToFile(currencies);
    }

    private void addResultsToFile(File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        for (CurrenciesExchangeRate c : currenciesExchangeRateList){
            fileWriter.write(c.toString());
        }
        currenciesExchangeRateList.clear();
        fileWriter.close();
    }

}
