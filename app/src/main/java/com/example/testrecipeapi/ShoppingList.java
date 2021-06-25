package com.example.testrecipeapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShoppingList extends AppCompatActivity {
    public static ArrayList<String> shoppingList = new ArrayList<>();
    Button addToPantryBtn, deleteItemBtn, clearListBtn, addIngredientBtn ;
    ListView shopView;
    TextView ingredientTxt;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist);

        shopView = findViewById(R.id.shopView);
        addToPantryBtn = findViewById(R.id.addToPantryBtn);
        deleteItemBtn = findViewById(R.id.deleteItemBtn);
        clearListBtn =  findViewById(R.id.clearListBtn);
        addIngredientBtn = findViewById(R.id.addIngredientBtn);
        ingredientTxt = findViewById(R.id.ingredientTxt);

        if(shoppingList.isEmpty()){
            shoppingList.add("Shopping list is empty");
        }
        // Create local shoppingList array and pass ingredientList
        if(!DishSearch.ingredientList.isEmpty()) {
            if(shoppingList.contains("Shopping list is empty"))
                shoppingList.remove(0);

           shoppingList.addAll((ArrayList<String>) getIntent().getSerializableExtra("ingredientList"));
        }


        // Set shopView to display shoppingList items in selectable ListView
        // making this shopView(List View) selectable because I want user to be able
        // to select items and delete them once purchased or if they already have
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_selectable_list_item, shoppingList);
        shopView.setAdapter(arrayAdapter);

        //remove item from shopping list
        shopView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                deleteItemBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        shoppingList.remove(i);
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_selectable_list_item, shoppingList);
                        shopView.setAdapter(arrayAdapter);
                    }
                });
            }
        });

        addToPantryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingList.this, Pantry.class);
                intent.putExtra("shoppingList", shoppingList);
                startActivity(intent);
            }
        });

        clearListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shoppingList = new ArrayList<>();
                shoppingList.add("Shopping list is empty.");
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_selectable_list_item, shoppingList);
                shopView.setAdapter(arrayAdapter);
            }
        });

        addIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shoppingList.contains("Shopping list is empty"))
                    shoppingList.remove(0);

                shoppingList.add(ingredientTxt.getText().toString());
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_selectable_list_item, shoppingList);
                shopView.setAdapter(arrayAdapter);
            }
        });
    }
}
