package com.example.lab5.controllers;

import com.example.lab5.entities.Zadanie;
import com.example.lab5.repositories.ZadanieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class PageController {
    @Autowired
    public ZadanieRepository rep;

    @RequestMapping("/")
    @ResponseBody
    public String mainPage() {
        return "Hello Spring Boot from mainPage() method!";
    }
    @RequestMapping("/hello")
    @ResponseBody
    public String pageTwo() {
        return "Hello Spring Boot from pageTwo() method!";
    }
    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<String> deleteZadanie(@PathVariable Long id) {
        rep.deleteById(id);
        return ResponseEntity.ok("Pomyślnie usunięto");
    }
    @RequestMapping("/zadania/wykonane")
    @ResponseBody
    public String getWykonaneZadania(){
        List<Zadanie> zadanieList = rep.findByWykonane(true);
        return zadanieList.toString();
    }

    @RequestMapping("/zadania/kosztMniejszyNiż/{koszt}")
    @ResponseBody
    public String getKosztLessZadania(@PathVariable double koszt){
        List<Zadanie> zadanieList = rep.findByKosztLessThan(koszt);
        return zadanieList.toString();
    }

    @RequestMapping("/zadania/kosztPomiedzy/{minKoszt}/{maxKoszt}")
    @ResponseBody
    public String getKosztBetween(@PathVariable double minKoszt, @PathVariable double maxKoszt){
        List<Zadanie> zadanieList = rep.findByKosztBetween(minKoszt,maxKoszt);
        return zadanieList.toString();
    }

    @RequestMapping("/listaZadan")
    @ResponseBody
    public String listaZadan() {
        StringBuilder odp = new StringBuilder();
        Zadanie zadanie = new Zadanie();
        Zadanie z;
        double k=1000;
        boolean wyk=false;
        for (int i=1;i<=10;i++){
            z = new Zadanie();
            z.setNazwa("zadanie "+i);
            z.setOpis("Opis czynnosci do wykonania w zadaniu "+i);
            z.setKoszt(k);
            z.setWykonane(wyk);
            wyk=!wyk;
            k+=200.50;
            rep.save(z);
        }
        //korzystając z obiektu repozytorium zapisujemy zadanie do bazy
        rep.save(zadanie);
        //korzystając z repozytorium pobieramy wszystkie zadania z bazy
        for(Zadanie i: rep.findAll()) {

            odp.append("<a href='/delete/").append(i.getId()).append("'>Usuń</a>").append("<br>").append(i);

        }
        return odp.toString();
    }

}

