package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ondesarrollo.psicoanalyzer.R;

import static android.widget.Toast.LENGTH_SHORT;


public class QuestionsFragment extends Fragment {


    public QuestionsFragment() {
        // Required empty public constructor
    }


    public static QuestionsFragment newInstance(String param1, String param2) {
        QuestionsFragment fragment = new QuestionsFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_questions,container,false);
        Button opYes= (Button) view.findViewById(R.id.opYes);
        Button opNo= (Button) view.findViewById(R.id.opNo);
        Button opInde= (Button) view.findViewById(R.id.opInde);
        LinearLayout panel= (LinearLayout) view.findViewById(R.id.panel);
        TextView question= (TextView) view.findViewById(R.id.textView_question);
        question.setText("Marico");
        Log.i("eal","Hola, soy el mensaje que has pedido");
        opYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Sirve");
            }
        });
        opNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        opInde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

}
