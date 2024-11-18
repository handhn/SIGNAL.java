import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Signal {
    private List<Double> closingPrices;

    public Signal() {
        this.closingPrices = new ArrayList<>();
    }

    public void updateFromURL(String urlString) throws Exception {
        URL url = new URL(urlString);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length >= 2) {
                addClosingPrice(Double.parseDouble(data[1]));
            }
        }
        reader.close();
    }


    public void addClosingPrice(double closingPrice) {
        closingPrices.add(closingPrice);
    }

    public List<Double> getClosingPrices() {
        return new ArrayList<>(closingPrices);
    }
}


