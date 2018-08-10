import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ClientGet {

    public static void main(String[] args) throws IOException {


        Currencies currenciesTable[] = Currencies.values();
        List<String> currenciesExchangeRateStringList = new ArrayList<>();
        List<CurrenciesExchangeRate> currenciesExchangeRateList = new ArrayList<>();


        for (int j = 0; j < currenciesTable.length; j++) {
            for (int i = 0; i < currenciesTable.length; i++) {
                CurrenciesExchangeRate currenciesExchangeRate = new CurrenciesExchangeRate();

                currenciesExchangeRate.setFirstCurrency(currenciesTable[j].toString());
                currenciesExchangeRate.setSecondCurrency(currenciesTable[i].toString());

                if (currenciesTable[i] != currenciesTable[j]) {
                    try {
                        URL url = new URL("https://free.currencyconverterapi.com/api/v6/convert?q=" + currenciesTable[j] + "_" + currenciesTable[i] + "&compact=ultra");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setRequestProperty("Accept", "application/json");

                        if (connection.getResponseCode() != 200) {
                            throw new RuntimeException("Failed : Http error code : " + connection.getResponseCode());
                        }

                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                        String output;
                        while ((output = bufferedReader.readLine()) != null) {
                            currenciesExchangeRateStringList.add(output);
                            String split[] = output.split(":");
                            String exchangeValueString = split[1].replace("}", "");
                            double exchangeValue = Double.parseDouble(exchangeValueString);
                            currenciesExchangeRate.setExchangeRate(exchangeValue);
                            currenciesExchangeRateList.add(currenciesExchangeRate);
                        }
                        connection.disconnect();
                    } catch (MalformedURLException exception) {
                        exception.printStackTrace();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }
        //Printing the result
        for (CurrenciesExchangeRate c : currenciesExchangeRateList) {
            System.out.println(c.getFirstCurrency() + " " + c.getSecondCurrency() + " " + c.getExchangeRate());
        }
    }
}
