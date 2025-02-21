package com.example.tccadoteumaarvore.activity.ui.newplant;


import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.fragment.app.Fragment;
import com.example.tccadoteumaarvore.config.ConfigFirebase;
import com.example.tccadoteumaarvore.databinding.FragmentNewplantBinding;
import com.example.tccadoteumaarvore.model.Arvore;
import com.example.tccadoteumaarvore.model.Plantio;
import com.example.tccadoteumaarvore.model.Usuario;
import com.example.tccadoteumaarvore.utils.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewPlantFragment extends Fragment {

    private FragmentNewplantBinding binding;
    private DatabaseReference reference = ConfigFirebase.getFirebaseRef();
    private Spinner listaEspecies;
    private ArrayList<String> especies;
    private Plantio plantio;
    private ArrayAdapter<String> adapter;
    private LocationManager locMngr;
    private EditText apelido, sobre;
    private Usuario usuarioDoacao;
    private double posLatitude, posLongitude;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewplantBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listaEspecies   = binding.spinnerEspecies;
        apelido         = binding.txtNewPlantApelido;
        sobre           = binding.txtNewPlantSobre;

        especies = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, especies);
        listaEspecies.setAdapter(adapter);

        //Recuperar posição atual
        locMngr = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        locMngr.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                2000,
                10,
                locationListenerGPS);

        binding.btnNewPlantAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plantio = new Plantio();
                String especie = (String) listaEspecies.getSelectedItem();
                String strApelido = apelido.getText().toString();
                String strSobre = sobre.getText().toString();
                String strUui = strApelido + "_" + especie;
                plantio.setEspecie(especie);
                plantio.setApelido(strApelido);
                plantio.setSobre(strSobre);
                plantio.setPosLatitude(posLatitude);
                plantio.setPosLongitude(posLongitude);
                plantio.setUui(strUui);
                plantio.setUuiUser(carregaUsuario());
                plantio.salvarPlantio();

                Toast.makeText(getContext(), "Plantio registrado com sucesso!", Toast.LENGTH_SHORT).show();
                limparPlantio();
            }
        });
        carregarEspecies();
    }
    @RequiresPermission(anyOf = {"android. permission. ACCESS_COARSE_LOCATION","android. permission. ACCESS_FINE_LOCATION"})
    public void requestLocationUpdates(String provider, long minTimeMs, float minDistanceM, android. location. LocationListener listener){}

    //Posição Original
    LocationListener locationListenerGPS = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            double latitude     = location.getLatitude();
            double longitude    = location.getLongitude();
            posLatitude         = latitude;
            posLongitude        = longitude;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    public void onResume(){
        super.onResume();
    }

    public void carregarEspecies(){
        DatabaseReference referenceArvores = reference.child("arvores");
        referenceArvores.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                especies.clear();

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Arvore arvore = postSnapshot.getValue(Arvore.class);
                    if(arvore !=null){
                        especies.add(arvore.getPopular());
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Erro ao buscar dados", error.toException());
            }
        });
    }

    public String carregaUsuario(){
        FirebaseAuth userAuth = ConfigFirebase.getFirebaseAuth();
        String uuiUser = Base64Custom.encodeBase64(userAuth.getCurrentUser().getEmail());
        return uuiUser;
    }

    public void limparPlantio(){
        binding.txtNewPlantApelido.setText("");
        binding.txtNewPlantSobre.setText("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}