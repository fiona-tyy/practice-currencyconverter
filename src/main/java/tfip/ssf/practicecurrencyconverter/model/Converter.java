package tfip.ssf.practicecurrencyconverter.model;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;


public class Converter {
    private String timeLastUpdate;
    private String timeNextUpdate;
    private String base_code;
    private Map<String, Float> conversion_rates;

    public Converter(){
        this.conversion_rates = new TreeMap<>();
    }

    public Converter(String baseCode){
        this.conversion_rates = new TreeMap<>();
        this.base_code = baseCode;
    }

    public String getTimeLastUpdate() {
        return timeLastUpdate;
    }
    public void setTimeLastUpdate(String timeLastUpdate) {
        this.timeLastUpdate = timeLastUpdate;
    }
    public String getTimeNextUpdate() {
        return timeNextUpdate;
    }
    public void setTimeNextUpdate(String timeNextUpdate) {
        this.timeNextUpdate = timeNextUpdate;
    }
    public String getBase_code() {
        return base_code;
    }
    public void setBase_code(String base_code) {
        this.base_code = base_code;
    }
    public Map<String, Float> getConversion_rates() {
        return conversion_rates;
    }
    public void setConversion_rates(Map<String, Float> conversion_rates) {
        this.conversion_rates = conversion_rates;
    }

    @Override
    public String toString() {
        return "Converter [timeLastUpdate=" + timeLastUpdate + ", timeNextUpdate=" + timeNextUpdate + ", base_code="
                + base_code + ", conversion_rates=" + conversion_rates + "]";
    }

    public JsonObject toJSON(){
        JsonObjectBuilder conversion = Json.createObjectBuilder();
        for(Map.Entry<String, Float> entry : this.getConversion_rates().entrySet()){
            String key = entry.getKey();
            Float value = entry.getValue();
            conversion.add(key, value);
        }
        return Json.createObjectBuilder()
                    .add("time_last_update_utc", this.getTimeLastUpdate())
                    .add("time_next_update_utc", this.getTimeNextUpdate())
                    .add("base_code", this.getBase_code())
                    .add("conversion_rates", conversion)
                    .build();
    }

    public static Converter createFromJsonString(String json){
        Converter c = new Converter();
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject obj = reader.readObject();

        c.setBase_code(obj.getString("base_code"));
        c.setTimeLastUpdate(obj.getString("time_last_update_utc"));
        c.setTimeNextUpdate(obj.getString("time_next_update_utc"));

        Map<String, Float> convRates = new TreeMap<>();

        JsonObject curr = obj.getJsonObject("conversion_rates");
        Set<String> keys = curr.keySet();
        for(String k : keys){
            Float value = (float) curr.getJsonNumber(k).doubleValue();
            convRates.put(k, value);
        }
        c.setConversion_rates(convRates);
        return c;
    }
    
}
