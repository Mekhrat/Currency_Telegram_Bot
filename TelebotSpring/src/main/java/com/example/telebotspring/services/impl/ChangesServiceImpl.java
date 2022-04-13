package com.example.telebotspring.services.impl;

import com.example.telebotspring.entities.Currency;
import com.example.telebotspring.services.ChangesService;
import com.example.telebotspring.services.CurrencyService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChangesServiceImpl implements ChangesService {
    @Autowired
    private CurrencyService currencyService;

    @Override
    public void setElements(String name) {
        ArrayList<Currency> currencies = new ArrayList<>();
        Document document = getPage(name);
        Element element = document.select("table[class=kurs-table]").first();
        Elements tr = element.select("tr");

        for (int i = 1; i < 12; i++) {
            currencies.add(new Currency(null,name,
                    tr.get(i).select("td").get(0).text(),
                    Double.parseDouble(tr.get(i).select("td").get(1).text())));
        }
        currencyService.addCurrencies(currencies);

    }

    @Override
    public String getELementsByName(String name) {
        List<Currency> currencies = currencyService.getTop10(name);
        String res = "";
        if (currencies != null) {
            for (int i = currencies.size() - 1; i > 0; i--) {
                String value = String.format("%.2f", currencies.get(i).getValue());
                String f = String.format("%.2f", Math.abs(currencies.get(i).getValue() - currencies.get(i - 1).getValue()));
                res += currencies.get(i).getDate() + "       " + value + "тг      " + (currencies.get(i).getValue() > currencies.get(i - 1).getValue() ? "⬆" : "⬇") + f + "тг\n";
            }
        }
        return res;
    }

    @Override
    public Double getPercent(String name) {
        List<Currency> currencies = currencyService.getTop10(name);
        Double percent = 0.0;
        if (currencies != null){
            percent = (Math.abs(currencies.get(currencies.size()-1).getValue() - currencies.get(currencies.size()-2).getValue()) / currencies.get(currencies.size()-2).getValue()) * 100;
        }
        return percent;
    }

    @Override
    public Document getPage(String str) {
        String url = "https://kazkredit.com/kurs/" + str;
        Document page = null;
        try {
            page = Jsoup.parse(new URL(url), 10000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }
}
