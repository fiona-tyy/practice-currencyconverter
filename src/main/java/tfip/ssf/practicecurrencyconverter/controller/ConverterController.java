package tfip.ssf.practicecurrencyconverter.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import tfip.ssf.practicecurrencyconverter.model.Converter;
import tfip.ssf.practicecurrencyconverter.service.ConverterService;

@Controller
@RequestMapping
public class ConverterController {

    @Autowired
    private ConverterService cSvc;
    
    @GetMapping("/convert")
    public String convertCurrency(Model model, @RequestParam String base_code){
        Optional<Converter> converter = cSvc.getFromApi(base_code);
        model.addAttribute("converter", converter.get());
        return "result";
    }
}
