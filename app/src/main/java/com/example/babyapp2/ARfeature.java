package com.example.babyapp2;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListPopupWindow;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ARfeature extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;
    private ArFragment arFragment;
    private AnchorNode myanchornode;
    TransformableNode mytranode = null;

    private SeekBar model_slider;
    private Spinner model_dropdown;
    private float mySize = 70f;

    int[] glb_source = {R.raw.baby};
    String[] arr_models = {"Baby Model"};
    private ModelRenderable[] renderable_models = new ModelRenderable[glb_source.length];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arfeature);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        model_dropdown = findViewById(R.id.model_dropdown);
        model_slider = findViewById(R.id.model_slider);

        List<AnchorNode> anchorNodes = new ArrayList<>();
        model_slider.setEnabled(false);

        Field popup = null;
        try {
            popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            ListPopupWindow popupWindow = (ListPopupWindow) popup.get(model_dropdown);
            popupWindow.setModal(false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        model_slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mySize = progress;
                myanchornode.setLocalScale(new Vector3(progress/70f, progress/70f, progress/70f));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        for(int i = 0 ; i < glb_source.length ; i++) {
            int finalI = i;
            ModelRenderable.builder()
                    .setSource(this, glb_source[i])
                    .setIsFilamentGltf(true)
                    .setAsyncLoadEnabled(true)
                    .build()
                    .thenAccept(renderable -> renderable_models[finalI] = renderable)
                    .exceptionally(
                            throwable -> {
                                Toast toast =
                                        Toast.makeText(this, "Unable to load any renderable", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                return null;
                            });
        }


        arFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    if (renderable_models[model_dropdown.getSelectedItemPosition()] == null) {
                        return;
                    }
                    // Create the Anchor.
                    Anchor anchor = hitResult.createAnchor();

                    AnchorNode anchorNode = new AnchorNode(anchor);


                    anchorNode.setParent(arFragment.getArSceneView().getScene());
                    anchorNodes.add(anchorNode);
                    model_slider.setEnabled(true);

                    myanchornode = anchorNode;

                    // Create the transformable andy and add it to the anchor.
                    TransformableNode andy;
                    if(mytranode == null)
                        andy = new TransformableNode(arFragment.getTransformationSystem());
                    else andy = mytranode;

                    andy.setParent(anchorNode);
                    andy.setRenderable(renderable_models[model_dropdown.getSelectedItemPosition()]);
                    andy.select();

                    mytranode = andy;
                    mytranode.setLocalRotation(new Quaternion(0f, 0f, 0f, 1f));
                    myanchornode.setLocalScale(new Vector3(mySize/70f, mySize/70f, mySize/70f));
                });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ARfeature.this,
                android.R.layout.simple_spinner_dropdown_item,arr_models);

        model_dropdown.setAdapter(adapter);
        model_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(mytranode != null)
                    mytranode.setRenderable(renderable_models[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }
}



//arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
// The "setOnTapPlaneGlbModel" method is called on the "arFragment" variable with a file path to a .glb model file.
// This method allows the user to tap on a plane in the AR environment and place the 3D model on that plane.
//arFragment.setOnTapPlaneGlbModel(Uri.parse("models/abc.glb").toString());

//arFragment.setOnTapPlaneGlbModel(Uri.parse("https://storage.googleapis.com/ar-answers-in-search-models/static/Tiger/model.glb").toString());
//                        (supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment)
//                .setOnTapPlaneGlbModel("model.glb")