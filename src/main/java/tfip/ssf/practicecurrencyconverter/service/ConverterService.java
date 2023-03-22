package tfip.ssf.practicecurrencyconverter.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.client.RestTemplate;

import tfip.ssf.practicecurrencyconverter.model.Converter;

@Service
public class ConverterService {
    
    public static final List<String> CURRENCIES = Arrays.asList("SGD", "USD", "GBP");

    @Value("${practice.converter.api.key}")
    private String apiKey;


    public boolean isValid(String base_code){
        return CURRENCIES.stream().anyMatch(c -> c.equals(base_code));
    }

    public Optional<Converter> getFromApi (String base_code){
        String url = "https://v6.exchangerate-api.com/v6/%s/latest/%s".formatted(apiKey,base_code);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.getForEntity(url, String.class);
        Converter converter = Converter.createFromJsonString(resp.getBody());
        if (null == converter){
            return Optional.empty();
        }
        return Optional.of(converter);
    }
}
