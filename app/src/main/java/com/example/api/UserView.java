package com.example.api;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.api.services.InfoServices;
import com.example.api.services.dataResponse.InfoResponse;
import com.example.api.services.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserView extends AppCompatActivity {


    TextView noID, autoName, autoUser, autoRol;
    Button btnUpdate, btnDelete, btnBack;
    Intent intent;

    UserView context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);


        noID = findViewById(R.id.noID);
        autoName = findViewById(R.id.autoName);
        autoUser = findViewById(R.id.autoUser);
        autoRol = findViewById(R.id.autoRol);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnBack = findViewById(R.id.btnBack);
        btnDelete = findViewById(R.id.btnDelete);

        context = this;


        String id = getIntent().getStringExtra("paramsId");
        noID.setText(id);
        autoName.setText(getIntent().getStringExtra("paramsName"));
        autoUser.setText(getIntent().getStringExtra("paramsUser"));
        autoRol.setText(getIntent().getStringExtra("paramsRol"));

        //Botón de volver
        btnBack.setOnClickListener(v -> {

            intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        });

        //Botón de borrar
        btnDelete.setOnClickListener(v -> {

            deleteUser(id);

            intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        });

        //Botón de actualizar
        btnUpdate.setOnClickListener(v -> {

            if (autoUser.getText().toString().isEmpty() ||
                autoName.getText().toString().isEmpty() ||
                autoRol.getText().toString().isEmpty()) {
                Toast.makeText(context, "Por favor, ingrese todos los campos", Toast.LENGTH_SHORT).show();
            }else {

                updateUser(id, new User(
                        autoName.getText().toString(),
                        autoUser.getText().toString(),
                        "0", //addPassword.getText().toString(),
                        autoRol.getText().toString())
                );

                intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //Método para borrar un usuario que toma un ID
    private void deleteUser(String i) {
        //Hace la petición DELETE
        Call<InfoResponse> respInfo = (new InfoServices().deleteInfoService(i));
        respInfo.enqueue(new Callback<InfoResponse>() {
            @Override
            public void onResponse(Call<InfoResponse> call, Response<InfoResponse> response) {

                Log.i("Info", "Conexión establecida");
                Log.i("Info", "Usuario eliminado");
            }

            @Override
            public void onFailure(Call<InfoResponse> call, Throwable t) {

                Log.i("Info", "Conexión denegada");
                Log.i("Info", t.getCause().getMessage());
            }
        });
    }


    private void updateUser(String i, User u) {
        //petición PUT
        Call<InfoResponse> respInfo = (new InfoServices().updateInfoService(i, u));
        respInfo.enqueue(new Callback<InfoResponse>() {
            @Override
            public void onResponse(Call<InfoResponse> call, Response<InfoResponse> response) {

                Log.i("Info", "Conexión establecida");
                Log.i("Info", "Usuario actualizado");
            }

            @Override
            public void onFailure(Call<InfoResponse> call, Throwable t) {

                Log.i("Info", "Conexión denegada");
                Log.i("Info", t.getCause().getMessage());
            }
        });
    }
}
