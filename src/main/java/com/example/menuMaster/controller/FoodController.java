package com.example.menuMaster.controller;


import com.example.menuMaster.food.Food;
import com.example.menuMaster.food.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class FoodController {

    @Autowired
    private FoodRepository repository;
    @GetMapping
    public String status(){
        return "Api on";
    }
    @GetMapping("/foods")
    public List<Food> getAll(){
        List<Food> foodsResponseList =  repository.findAll().stream().map(Food::new).toList(); //O construtor recebe Food
        return foodsResponseList;
    }
    @GetMapping("/foods/{id}")
    public ResponseEntity<Food> getById(@PathVariable Long id){
        Optional<Food> optionalFood = repository.findById(id);
        return optionalFood
                .map(food -> ResponseEntity.ok().body(food))
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/post")
    public void saveFood( @RequestBody Food dataFood){
        System.out.println("Id do produto: " + dataFood.getId());
        repository.save(dataFood);
        return;
    }

    @DeleteMapping("/foods/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        Optional<Food> optionalFood = repository.findById(id);

        if(optionalFood.isPresent()){
            repository.deleteById(id);
            return ResponseEntity.noContent().build(); //204 no content
        } else {
            return  ResponseEntity.notFound().build();//404 not found
        }

    }

    @PutMapping("/foods/{id}")
    public ResponseEntity<Food> updateFood(@PathVariable Long id, @RequestBody Food foodUpdate){

        Optional<Food> optionalFood = repository.findById(id);

        if(optionalFood.isPresent()){
            Food existingFood = optionalFood.get();
            existingFood.setTitle(foodUpdate.getTitle());
            existingFood.setImg(foodUpdate.getImg());
            existingFood.setPrice(foodUpdate.getPrice());

            Food savedFood = repository.save(existingFood);
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.notFound().build();
        }


    }


}
