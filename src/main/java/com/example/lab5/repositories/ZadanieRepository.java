package com.example.lab5.repositories;

import com.example.lab5.entities.Zadanie;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ZadanieRepository extends CrudRepository<Zadanie, Long>{
    List<Zadanie> findByWykonane(boolean wykonane);
    List<Zadanie> findByKosztLessThan(double koszt);
    List<Zadanie> findByKosztBetween(double minKoszt, double maxKoszt);
}