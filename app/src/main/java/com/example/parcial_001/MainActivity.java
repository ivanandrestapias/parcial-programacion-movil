package com.example.parcial_001;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView menu;
    NavController nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu = findViewById(R.id.menu_abajo);
        nav = Navigation.findNavController(this,R.id.fragment);
        NavigationUI.setupWithNavController(menu, nav);
        databaseHelper db = new databaseHelper(this,"parcial",null,1);
    }
}