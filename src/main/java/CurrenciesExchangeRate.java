public class CurrenciesExchangeRate {



    public CurrenciesExchangeRate() {
    }


    public void setFirstCurrency(String firstCurrency) {
        this.firstCurrency = firstCurrency;
    }

    public void setSecondCurrency(String secondCurrency) {
        this.secondCurrency = secondCurrency;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getFirstCurrency() {
        return firstCurrency;
    }

    public String getSecondCurrency() {
        return secondCurrency;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    private String firstCurrency;
    private String secondCurrency;
    private double exchangeRate;
}
