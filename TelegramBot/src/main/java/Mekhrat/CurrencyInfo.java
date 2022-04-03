package Mekhrat;

public class CurrencyInfo {
    private String data;
    private Double value;

    public CurrencyInfo(String data, Double value) {
        this.data = data;
        this.value = value;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "data='" + data + '\'' +
                ", value=" + value +
                '}';
    }
}