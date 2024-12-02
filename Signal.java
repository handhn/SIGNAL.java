import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Signal {
    private List<Double> closingPrices;

    public Signal() {
        this.closingPrices = new ArrayList<>();
    }
	// cnx réseau
    public void updateFromMarketstack(String accessKey, String symbol) throws Exception {
        String urlString = "http://api.marketstack.com/v1/eod?access_key=" + accessKey + "&symbols=" + symbol;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET"); // requette serveur 

        // Lire la réponse et réception des données
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // Traiter la réponse pour extraire les prix de clôture
        parseClosingPrices(response.toString());
    }

    private void parseClosingPrices(String jsonResponse) {
        // Simplification : extraction manuelle des prix de clôture
        String[] lines = jsonResponse.split("\\},\\{"); // Diviser par objets JSON
        for (String line : lines) {
            if (line.contains("\"close\":")) {
                String[] parts = line.split("\"close\":");
                if (parts.length > 1) {
                    String priceStr = parts[1].split(",")[0]; // Obtenir le prix de clôture
                    double closingPrice = Double.parseDouble(priceStr);
                    closingPrices.add(closingPrice);
                }
            }
        }
    }

    public List<Double> getClosingPrices() {
        return new ArrayList<>(closingPrices);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Closing Prices:\n");
        for (int i = 0; i < closingPrices.size(); i++) {
            sb.append(String.format("Day %d: %.2f\n", i + 1, closingPrices.get(i)));
        }
        return sb.toString();
    }
}
