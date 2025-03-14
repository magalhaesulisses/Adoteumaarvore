package com.example.tccadoteumaarvore.activity.ui.profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.example.tccadoteumaarvore.config.ConfigFirebase;
import com.example.tccadoteumaarvore.databinding.FragmentProfileBinding;
import com.example.tccadoteumaarvore.model.Plantio;
import com.example.tccadoteumaarvore.model.Usuario;
import com.example.tccadoteumaarvore.utils.Base64Custom;
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
import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private DatabaseReference reference = ConfigFirebase.getFirebaseRef();
    private ArrayList<Plantio> plantios = new ArrayList<>();
    private Plantio plantio;
    private Usuario user;
    private Uri mSelectedUri;
    private ImageView image;
    //Maps
    IMapController mapController;
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
        image = binding.profileImage;
        mapa = (MapView) binding.mapa;
        mapa.setTileSource(TileSourceFactory.MAPNIK);
        IMapController mapController = mapa.getController();
        mapController.setZoom(17.0);
        mapController.setCenter(centroToledo);

        binding.profileImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(intent, "Selecione uma foto"), 0);
        });

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == 0){
                mSelectedUri = data.getData();
                image.setImageURI(mSelectedUri);
                salvarImagem(String.valueOf(mSelectedUri));
            }
        }
    }

    public void returnUserInfo(){

        FirebaseAuth userAuth = ConfigFirebase.getFirebaseAuth();
        String uuiUser = Base64Custom.encodeBase64(userAuth.getCurrentUser().getEmail());

        DatabaseReference referenceUser = reference.child("usuarios").child(uuiUser);
        referenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(Usuario.class);
                String nomeCompleto = user.getNome() + " " + user.getSobrenome();
                binding.profileName.setText(nomeCompleto);
                binding.profileLogin.setText(user.getLogin());
                String uri = user.getImageuri();
                if ((!uri.isEmpty()) && (!uri.equals(""))){
                    Uri mSelectedUri;
                    mSelectedUri = Uri.parse(user.getImageuri());
                    binding.profileImage.setImageURI((mSelectedUri));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Erro ao buscar dados", error.toException());
            }
        });
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
                    if(plantio != null){
                        plantios.add(plantio);
                    }
                }

                //Marcando no mapa os Plantios Realizados
                ArrayList<OverlayItem> marcas = new ArrayList<>();
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
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Erro ao buscar dados", error.toException());
            }
        });
    }

    public void salvarImagem(String uri){
        DatabaseReference reference = ConfigFirebase.getFirebaseRef();
        reference.child("usuarios").child(carregaUsuario()).child("imageuri").setValue(uri);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("Firebase", "Gravado com sucesso!");
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