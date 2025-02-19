package com.example.tccadoteumaarvore.activity.ui.profile;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.tccadoteumaarvore.config.ConfigFirebase;
import com.example.tccadoteumaarvore.databinding.FragmentProfileBinding;
import com.example.tccadoteumaarvore.model.Usuario;
import com.example.tccadoteumaarvore.utils.Base64Custom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.gridlines.LatLonGridlineOverlay2;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private Usuario user = new Usuario();

    //Maps
    MapView mapa;
    LocationManager locMngr;
    ArrayList<Marker> marcas;
    GeoPoint centroToledo = new GeoPoint(-24.7248, -53.7362);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        returnUserInfo();

        Context ctx = getContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        mapa = (MapView) binding.mapa;
        mapa.setTileSource(TileSourceFactory.MAPNIK);
        mapa.setZoomLevel(16.0);
        mapa.setInitCenter(centroToledo);

        //TODO: Adicionar Array de Objetos
        marcas = new ArrayList<>();
        if (savedInstanceState != null) {
            ArrayList<Marker> marcasAnteriores = (ArrayList<Marker>) savedInstanceState.getSerializable("marcas");
            if (marcasAnteriores != null) {
                for (Marker m : marcasAnteriores) {
                    Marker nova = new Marker(mapa);
                    nova.setPosition(m.getPosition());
                    nova.setTitle(m.getTitle());
                    marcas.add(nova);
                    mapa.getOverlays().add(nova);
                }
            }
        }

        // Localização
        locMngr = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                                PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // Re-centraliza posição 2000ms
        locMngr.requestLocationUpdates(locMngr.GPS_PROVIDER, 2000, 0F, new LocationListener(){
            @Override
            public void onLocationChanged(@NonNull Location location) {
                centroToledo = new GeoPoint(location.getLatitude(), location.getLongitude());
                mapa.setInitCenter(centroToledo);
            }
        });
    }


    public void returnUserInfo(){

        FirebaseAuth userAuth = ConfigFirebase.getFirebaseAuth();
        String uuiUser = Base64Custom.encodeBase64(userAuth.getCurrentUser().getEmail());
        DatabaseReference databaseref = ConfigFirebase.getFirebaseRef();

        databaseref.child("usuarios").child(uuiUser).get().addOnCompleteListener(
            new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()){
                        if (task.getResult().exists()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            user = dataSnapshot.getValue(Usuario.class);
                            String nomeCompleto = user.getNome() + " " + user.getSobrenome();
                            binding.profileName.setText(nomeCompleto);
                            binding.profileLogin.setText(user.getLogin());
                        }
                    }
                }
            }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}