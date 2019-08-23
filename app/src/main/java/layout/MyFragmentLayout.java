package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ondesarrollo.psicoanalyzer.R;

public class MyFragmentLayout extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    int mCurrentPage;
    public MyFragmentLayout() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyFragmentLayout.
     */
    // TODO: Rename and change types and number of parameters
    public static MyFragmentLayout newInstance(String param1, String param2) {
        MyFragmentLayout fragment = new MyFragmentLayout();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data=getArguments();
        if (data != null) {
            mCurrentPage = data.getInt("current_page", 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        TextView tv;
        RelativeLayout rl;
        if(mCurrentPage==1){
            v = inflater.inflate(R.layout.inicio,container,false);
            rl=(RelativeLayout) v.findViewById(R.id.inicio);
            return rl;
        }else{

            v = inflater.inflate(R.layout.my_fragment_layout,container,false);
            tv=(TextView) v.findViewById(R.id.tv);
            tv.setText("You are viewing the page #"+mCurrentPage+"\n\n"+"Swipe Horizontally left / right");
            return tv;
        }
    }
}
