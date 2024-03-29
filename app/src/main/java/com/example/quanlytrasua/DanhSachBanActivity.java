package com.example.quanlytrasua;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import com.example.quanlytrasua.FragmentApp.HienThiBanFragment;
import com.example.quanlytrasua.FragmentApp.ThongKeFragment;
import com.example.quanlytrasua.ultil.SessionManager;
import com.google.android.material.navigation.NavigationView;


public class DanhSachBanActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle mToggle;

    SessionManager sessionManager;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_danhsachban);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        AddControl();


    }


    private void AddControl() {
        drawerLayout = findViewById(R.id.draw_layout);
        navigationView = findViewById(R.id.navigation);
        toolbar = findViewById(R.id.toolbar);

        name = findViewById(R.id.tenUser);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HienThiBanFragment()).commit();





    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.menudanhsachban){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HienThiBanFragment()).commit();
        }
        if(id == R.id.menuthongke){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ThongKeFragment()).commit();
        }

        if(id == R.id.menudangxuat){
            sessionManager.logout();
//            Intent intent = new Intent(DanhSachBanActivity.this, LoginActivity.class);
//            startActivity(intent);
//            finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



}
