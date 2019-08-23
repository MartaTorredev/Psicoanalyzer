package com.ondesarrollo.psicoanalyzer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import adapters.RecyclerViewOnItemClickListener;
import adapters.Resultados;
import layout.MyFragmentPagerAdapter;
import layout.QuestionsFragment;
import models.Cuestionario;
import utils.Constant;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> questions=null;
    private ArrayList<String> responses=null;

    private ViewFlipper viewFlipper;
    private int cantResponses;
    private int cantQuestions;
    private TextView question;

    private RequestQueue rqt;
    private String tag_json_array="jarray_req";
    private List<Cuestionario> items;
    private ProgressBar progressBar;
    private ArrayList<String> preguntas_generales_en_curso;
    private int id_pregunta_gneral_actual;
    private int cant_preguntas_generales_actuales;
    private int cuestiona_actual;
    private ArrayList<String> preguntas_especificas_en_curso;
    private int id_pregunta_especifica_actual;
    private int tipo;
    private ArrayList<String> enfermedades;
    private ArrayList<String> descripciones;
    private ArrayList<String> padeceD;
    private ArrayList<String> padece;
    public MediaPlayer audio;
    private SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);


//        ViewPager pager= (ViewPager) findViewById(R.id.pager);
        viewFlipper=(ViewFlipper) findViewById(R.id.viewFlipper);
        audio= MediaPlayer.create(this,R.raw.uno);
        prefs =getSharedPreferences("Psicoanalyzer", Context.MODE_PRIVATE);
        boolean activeSound=prefs.getBoolean("activeSound",true);
        if(activeSound){
            audio.setLooping(true);
            audio.start();
        }else{
            audio.setLooping(false);
            audio.stop();
        }
        Button opYes= (Button) findViewById(R.id.opYes);
        Button opNo= (Button) findViewById(R.id.opNo);
        Button opInde= (Button) findViewById(R.id.opInde);
        RelativeLayout panel= (RelativeLayout) findViewById(R.id.panel);
        question= (TextView) findViewById(R.id.textView_question);
        //Seleccion de idioma
        Button langEs= (Button) findViewById(R.id.langEs);
        langEs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setIndeterminate(true);
                getCuestionario("es");
            }
        });

        Button langIngles= (Button) findViewById(R.id.langIngles);
        langIngles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setIndeterminate(true);
                getCuestionario("en");
            }
        });

        Log.i("eal","Hola, soy el mensaje que has pedido");

        opYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion(1);
            }
        });
        opNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion(2);
            }
        });
        opInde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion(3);
            }
        });

        Button btnNewTest= (Button) findViewById(R.id.btnNewTest);
        btnNewTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.showNext();
                cuestiona_actual=0;
                Cuestionario c=items.get(cuestiona_actual);
                //Toast.makeText(getApplicationContext(),"Cuestionarios "+items.size(),Toast.LENGTH_LONG).show();
                preguntas_generales_en_curso=c.getPreguntas_generales();
                id_pregunta_gneral_actual=0;
                tipo=1;
                cant_preguntas_generales_actuales=preguntas_generales_en_curso.size();
                question.setText(preguntas_generales_en_curso.get(id_pregunta_gneral_actual));
            }
        });

        Button btnBack=(Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.showPrevious();
            }
        });

        Button btnInfo= (Button) findViewById(R.id.btnInfo);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo(v);
            }
        });
    }
    private void nextQuestion(int res){
        int yes=0;
        int rgl=0;
        if(tipo==1){
//        ArrayList<Integer> rpg = items.get(cuestiona_actual).getRespuestas_generales();
            items.get(cuestiona_actual).getRespuestas_generales().add(id_pregunta_gneral_actual,res);
            Cuestionario c=items.get(cuestiona_actual);
            ArrayList<Integer> rg = items.get(cuestiona_actual).getRespuestas_generales();

            yes=0;
            rgl=items.get(cuestiona_actual).getCant_generales();
            for(int i=0;i<rgl;i++){
                if(rg.get(i)==1){//si
                    yes+=1;
                }
            }
            Log.i("eal","Respuestas:"+rgl+"=="+yes);

            id_pregunta_gneral_actual+=1;
        }else if(tipo==2){
            items.get(cuestiona_actual).getRespuestas_especificas().add(id_pregunta_especifica_actual,res);
            Cuestionario c=items.get(cuestiona_actual);
            ArrayList<Integer> rg = items.get(cuestiona_actual).getRespuestas_especificas();

            yes=0;
            rgl=items.get(cuestiona_actual).getCant_generales();

            for(int i=0;i<rgl;i++){
                if((int) rg.get(i) ==(int) 1){//si
                    yes+=1;
//                    if((rgl+1)==i){
//                        id_pregunta_especifica_actual=0;
//                    }
                }
            }
            Log.i("eal","Respuestas:"+rgl+"=="+yes);

        }

        if(id_pregunta_gneral_actual<cant_preguntas_generales_actuales && tipo==1){
            question.setText(preguntas_generales_en_curso.get(id_pregunta_gneral_actual));
        }else if(rgl>0 && yes>0 && rgl==yes && tipo==2){
            id_pregunta_especifica_actual+=1;
            if(id_pregunta_especifica_actual<preguntas_generales_en_curso.size()){
                Log.i("eal","Respuestas:"+rgl+"=="+yes);
                Toast.makeText(this,"Especifica:"+(id_pregunta_especifica_actual+1),Toast.LENGTH_LONG).show();

                question.setText(preguntas_especificas_en_curso.get(id_pregunta_especifica_actual));
            }else{
                //Toast.makeText(this,"Termino con las especificas",Toast.LENGTH_LONG).show();
                tipo=1;
                Toast.makeText(this,"Generales: "+cuestiona_actual,Toast.LENGTH_LONG).show();
                cuestiona_actual+=1;
                if(cuestiona_actual<items.size()){
                    Cuestionario cc=items.get(cuestiona_actual);
                    preguntas_generales_en_curso=cc.getPreguntas_generales();
                    id_pregunta_gneral_actual=0;
                    cant_preguntas_generales_actuales=preguntas_generales_en_curso.size();
                    question.setText(preguntas_generales_en_curso.get(id_pregunta_gneral_actual));
                }else{
                    //Toast.makeText(this,"Fin del test",Toast.LENGTH_LONG).show();
                    //items.get(cuestiona_actual-1).getRespuestas_especificas().add(id_pregunta_especifica_actual-1,res);
                    enfermedades=new ArrayList<>();
                    descripciones=new ArrayList<>();
                    for(int i=0;i<items.size();i++){
                        Cuestionario c=items.get(i);
                        ArrayList<Integer> es = c.getRespuestas_especificas();
                        int caEs=es.size();

                        for(int j=0;j < caEs;j++){

                            if(es.get(j)==1){

                                Toast.makeText(getApplicationContext(),
                                        " reEspecificas: "+es.size()+" Enfermedades"
                                                +c.getEnfermedades().size()
                                        ,
                                        Toast.LENGTH_LONG);


                                enfermedades.add(c.getEnfermedades().get(j));
                                descripciones.add(c.getAllDescripciones().get(j));
                            }
                        }
                    }
                    TextView textViewResultados= (TextView) findViewById(R.id.textViewResultados);
                    padece=new ArrayList<>();
                    padeceD=new ArrayList<>();
                    for(int i=0;i<enfermedades.size();i++){
                        if(i<(enfermedades.size()-1)){
                            padece.add(enfermedades.get(i));
                            padeceD.add(descripciones.get(i));
                        }else{
                            padece.add(enfermedades.get(i));
                            padeceD.add(descripciones.get(i));
                        }
                    }
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                    recyclerView.setAdapter(new Resultados(padece,new RecyclerViewOnItemClickListener() {
                        @Override
                        public void onClick(View v, int position) {
                            viewFlipper.showNext();
                            TextView textViewDescripcion= (TextView) findViewById(R.id.textViewDescripcion);
                            Toast.makeText(getApplicationContext(),"Posicion: "+position,Toast.LENGTH_LONG).show();
                            textViewDescripcion.setText(padeceD.get(position));
                            //Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                        }
                    }));
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    //recyclerView.addItemDecoration(new DividerItemDecoration(this));
//                    textViewResultados.setText(padece);
                    viewFlipper.showNext();
//                    viewFlipper.showNext();
                }
            }
        }else{
//            Toast.makeText(this,"General:"+(id_pregunta_gneral_actual+1),Toast.LENGTH_LONG).show();
            //Si respondio a todas las generales que si voy con las especificas
            if(rgl>0 && yes>0 && rgl==yes){

                //Toast.makeText(this,"Hizo el cambio de tipo",Toast.LENGTH_LONG).show();
                tipo=2;
                id_pregunta_especifica_actual=0;
                Cuestionario c=items.get(cuestiona_actual);
                preguntas_especificas_en_curso=c.getPreguntas_especificas();
                question.setText(preguntas_especificas_en_curso.get(id_pregunta_especifica_actual));
            }else if(tipo==1){
                //Toast.makeText(this,"General",Toast.LENGTH_LONG).show();

                cuestiona_actual+=1;
                if(cuestiona_actual<items.size()){
                    Cuestionario cc=items.get(cuestiona_actual);
                    preguntas_generales_en_curso=cc.getPreguntas_generales();
                    id_pregunta_gneral_actual=0;
                    cant_preguntas_generales_actuales=preguntas_generales_en_curso.size();
                    question.setText(preguntas_generales_en_curso.get(id_pregunta_gneral_actual));
                }else{
                    //Toast.makeText(this,"Fin del test",Toast.LENGTH_LONG).show();
                   //items.get(items.size()-1).getRespuestas_especificas().add(id_pregunta_especifica_actual,res);

                    enfermedades=new ArrayList<>();
                    descripciones=new ArrayList<>();
                    for(int i=0;i<items.size();i++){
                        Cuestionario c=items.get(i);
                        ArrayList<Integer> es = c.getRespuestas_especificas();
                        int caEs=es.size();

                        for(int j=0;j < caEs;j++){

                            if(es.get(j)==1){

                                Toast.makeText(getApplicationContext(),
                                        " reEspecificas: "+es.size()+" Enfermedades"
                                                +c.getEnfermedades().size()
                                        ,
                                        Toast.LENGTH_LONG);


                                enfermedades.add(c.getEnfermedades().get(j));
                                descripciones.add(c.getAllDescripciones().get(j));
                            }
                        }
                    }
                    TextView textViewResultados= (TextView) findViewById(R.id.textViewResultados);
                    padece=new ArrayList<>();
                    padeceD=new ArrayList<>();
                    for(int i=0;i<enfermedades.size();i++){
                        if(i<(enfermedades.size()-1)){
                            padece.add(enfermedades.get(i));
                            padeceD.add(descripciones.get(i));
                        }else{
                            padece.add(enfermedades.get(i));
                            padeceD.add(descripciones.get(i));
                        }
                    }
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                    recyclerView.setAdapter(new Resultados(padece,new RecyclerViewOnItemClickListener() {
                        @Override
                        public void onClick(View v, int position) {
                            viewFlipper.showNext();
                            TextView textViewDescripcion= (TextView) findViewById(R.id.textViewDescripcion);
                            Toast.makeText(getApplicationContext(),"Posicion: "+position,Toast.LENGTH_LONG).show();
                            textViewDescripcion.setText(padeceD.get(position));
                            //Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                        }
                    }));
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    //recyclerView.addItemDecoration(new DividerItemDecoration(this));
//                    textViewResultados.setText(padece);
                    viewFlipper.showNext();

                }
            }else if(tipo==2 && cuestiona_actual==(items.size()-1)){
                Toast.makeText(getApplicationContext(),"asd",Toast.LENGTH_LONG).show();
                viewFlipper.showNext();
            }

        }
    }
    private void getCuestionario(String lang){
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, Constant.URL+""+lang, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        mTxtDisplay.setText("Response: " + response.toString());

                        try{
//                            Log.i("API","" + response.getJSONObject("cuestionarios").length());
                            items=parseJson(response);
                            if(items.size()>0){
                                viewFlipper.showNext();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error al solicitar los datos",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.i("eal","Fallo: " + error.toString());
                    }
                }
                );

        //ConfigureApp.getmInstance().addToRequestQueue(jsObjRequest,tag_json_array);
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }
    protected void onResume(){
        super.onResume();

    }

    public List<Cuestionario> parseJson(JSONObject jsonObject){
        List<Cuestionario> cuestionarios=new ArrayList<>();
        Log.i("API","Antes de parsear ");
        try{

            JSONArray jsonArray=jsonObject.getJSONArray("cuestionarios");
            int r_l=jsonArray.length();
            Log.i("API","Tamaño de cuestionario: " +r_l);
            if(r_l>0){
                for(int i=0;i<r_l;i++){
                    JSONObject objeto= jsonArray.getJSONObject(i);
//                                    JSONArray cuestionarios=jsonObject.get;
                    String categoria=objeto.getString("categoria");
                    ArrayList<String> allPreguntasGenerales=new ArrayList<>();
                    ArrayList<String> allPreguntasEspecificas=new ArrayList<>();
                    ArrayList<String> allPreguntasEnfermedades=new ArrayList<>();
                    ArrayList<String> allDescripciones=new ArrayList<>();

//                    Log.i("API","Categoria: " + objeto.getString("categoria"));
                    JSONArray preguntas_generales=objeto.getJSONArray("generales");
                    int cantidad_pregunta_generaless=preguntas_generales.length();
                    for(int j=0;j<cantidad_pregunta_generaless;j++){
                        JSONObject pregunta=preguntas_generales.getJSONObject(j);
//                        Log.i("API","Generales: " + pregunta.get("pregunta"));
                        allPreguntasGenerales.add(pregunta.getString("pregunta"));
                    }

                    JSONArray preguntas_especificas=objeto.getJSONArray("especificas");
                    int cantidad_preguntas_especificas=preguntas_especificas.length();
                    for(int x=0;x<cantidad_preguntas_especificas;x++){
                        JSONObject pregunta=preguntas_especificas.getJSONObject(x);
//                        Log.i("API","Especifica "+(x+1)+": " + pregunta.get("pregunta"));
                        allPreguntasEspecificas.add(pregunta.getString("pregunta"));
                        allPreguntasEnfermedades.add(pregunta.getString("nombre_enfermedad"));
                        allDescripciones.add(pregunta.getString("descripcion_enfermedad"));
                    }

                    Cuestionario cuestionario=new Cuestionario();
                    cuestionario.setId_categoria(categoria);
                    cuestionario.setPreguntas_generales(allPreguntasGenerales);
                    cuestionario.setPreguntas_especificas(allPreguntasEspecificas);
                    cuestionario.setEnfermedades(allPreguntasEnfermedades);
                    cuestionario.setAllDescripciones(allDescripciones);
                    cuestionarios.add(cuestionario);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return cuestionarios;
    }
    private void setSelectedOp(int op){

    }
    private void showQuestion(){
        question.setText(questions.get(0));
    }
    private class ListenerTouchViewFlipper implements View.OnTouchListener {
        private float init_x;

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: //Cuando el usuario toca la pantalla por primera vez
                    init_x=event.getX();
                    return true;
                case MotionEvent.ACTION_UP: //Cuando el usuario deja de presionar
                    float distance =init_x-event.getX();

                    if(distance>0)
                    {
                        viewFlipper.showPrevious();
                    }

                    if(distance<0)
                    {
                        viewFlipper.showNext();
                    }

                default:
                    break;
            }
            return false;
        }
    }

    public void showInfo(View v){
        Intent i =new Intent(this,Info.class);
        startActivity(i);
    }

}
