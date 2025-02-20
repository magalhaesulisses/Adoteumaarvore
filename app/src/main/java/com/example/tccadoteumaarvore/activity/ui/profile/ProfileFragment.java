package com.example.tccadoteumaarvore.activity.ui.profile;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.example.tccadoteumaarvore.config.ConfigFirebase;
import com.example.tccadoteumaarvore.databinding.FragmentProfileBinding;
import com.example.tccadoteumaarvore.model.Plantio;
import com.example.tccadoteumaarvore.model.Usuario;
import com.example.tccadoteumaarvore.utils.Base64Custom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;


import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private DatabaseReference reference = ConfigFirebase.getFirebaseRef();
    private ArrayList<Plantio> plantios = new ArrayList<>();
    private Plantio plantio;
    private Usuario user;
    IMapController mapController;

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
        returnPlantiosUsuario();

        Context ctx = getContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        mapa = (MapView) binding.mapa;
        mapa.setTileSource(TileSourceFactory.MAPNIK);
        //mapa.setZoomLevel(16.0);
        //mapa.setInitCenter(centroToledo);
        IMapController mapController = mapa.getController();
        mapController.setZoom(17.0);
        mapController.setCenter(centroToledo);

        ArrayList<OverlayItem> marcas = new ArrayList<>();
        //Marcando no mapa os Plantios Realizados
        if (plantios != null){
            for (Plantio p : plantios){
                OverlayItem overlayItem = new OverlayItem(p.getApelido(), p.getSobre(), new GeoPoint(p.getPosLatitude(), p.getPosLongitude()));
                Drawable m = overlayItem.getMarker(0);
                marcas.add(overlayItem);
            }
            ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<>(getContext(), marcas, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                @Override
                public boolean onItemSingleTapUp(int index, OverlayItem item) {
                    return true;
                }

                @Override
                public boolean onItemLongPress(int index, OverlayItem item) {
                    return false;
                }
            });
            mOverlay.setFocusItemsOnTap(true);
            mapa.getOverlays().add(mOverlay);
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

    public void returnPlantiosUsuario(){
        DatabaseReference referencePlantios = reference.child("plantios");
        Query usuarioPesquisa = referencePlantios.orderByChild("uuiUser").equalTo(carregaUsuario());
        usuarioPesquisa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                plantios.clear();
                plantio = new Plantio();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    plantio = postSnapshot.getValue(Plantio.class);
                    if(plantio !=null){
                        plantios.add(plantio);
                    }
                }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}