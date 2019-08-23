package adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ondesarrollo.psicoanalyzer.R;

import java.util.List;

/**
 * Created by enmanuel on 07/07/16.
 */
public class Resultados extends RecyclerView.Adapter<Resultados.ResultadosHolder> {
    private List<String> data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    public Resultados(@NonNull List<String> data, @NonNull RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        this.data = data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }
    @Override
    public ResultadosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ResultadosHolder(row);
    }

    @Override
    public void onBindViewHolder(ResultadosHolder holder, int position) {
//        Color color = data.get(position);
//        holder.getTitleTextView().setText(color.getName());
//        holder.getSubtitleTextView().setText(color.getHex());

//        GradientDrawable gradientDrawable = (GradientDrawable) holder.getCircleView().getBackground();
//        int colorId = android.graphics.Color.parseColor(color.getHex());
//        gradientDrawable.setColor(colorId);
        TextView resultado=holder.getTextViewResultados();
        resultado.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ResultadosHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {


        private TextView textViewResultados;

        public ResultadosHolder(View itemView) {
            super(itemView);
            textViewResultados=(TextView)itemView.findViewById(R.id.textViewResultados);
            itemView.setOnClickListener(this);

        }

        public TextView getTextViewResultados(){
            return textViewResultados;
        }

        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
        }
    }
}
