package com.example.telebotspring.services;

import com.example.telebotspring.entities.Currency;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public interface ChangesService {
    void setElements(String name);
    Document getPage(String str);
    String getELementsByName(String name);
    Double getPercent (String name);
}
