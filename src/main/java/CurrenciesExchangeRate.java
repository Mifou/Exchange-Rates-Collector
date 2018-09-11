public class CurrenciesExchangeRate {

    public CurrenciesExchangeRate(Currencies firstCurrency, Currencies secondCurrency, double exchangeRate) {
        this.firstCurrency = firstCurrency;
        this.secondCurrency = secondCurrency;
        this.exchangeRate = exchangeRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrenciesExchangeRate that = (CurrenciesExchangeRate) o;

        if (Double.compare(that.exchangeRate, exchangeRate) != 0) return false;
        if (!firstCurrency.equals(that.firstCurrency)) return false;
        return secondCurrency.equals(that.secondCurrency);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = firstCurrency.hashCode();
        result = 31 * result + secondCurrency.hashCode();
        temp = Double.doubleToLongBits(exchangeRate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return firstCurrency + "_" + secondCurrency + " " + exchangeRate;
    }

    private Currencies firstCurrency;
    private Currencies secondCurrency;
    private double exchangeRate;
}
