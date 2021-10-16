package net.casetrue.dikri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

/**
 * this is a class adapter have class holder attribute
 */
public class DikrAdapter extends RecyclerView.Adapter<DikrAdapter.dikrViewHolder>
{
    /**
     * this is a list of dikr charge from onCreate
     */
    ArrayList<Dikr> dikrs;
    private OnDikrClickListener dikrClickListener;

    /**
     * this is an interface
     */
    public interface OnDikrClickListener
    {
        /**
         * this click listener get position
         *
         * @param position
         */
        void OnDikrClick(int position);
    }

    /**
     * this is implement interface from click listener
     *
     * @param dikrClickListener
     */
    public void setDikrClickListener(OnDikrClickListener dikrClickListener)
    {
        this.dikrClickListener = dikrClickListener;
    }

    /**
     * this is a class constructor get list parameter
     *
     * @param dikrs
     */
    public DikrAdapter(ArrayList<Dikr> dikrs)
    {
        this.dikrs = dikrs;
    }

    /**
     * this is a class method to inflate list adapter
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public dikrViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_read, parent, false);
        return new dikrViewHolder(view, dikrClickListener);
    }

    /**
     * this is a class method to set layout values
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull dikrViewHolder holder, int position)
    {
        Dikr dikr;
        dikr = dikrs.get(position);
        holder.txtDikr.setText(dikr.getTxtDikr());
        holder.txtCount.setText(dikr.getNbCount());
    }

    /**
     * this a class method return list size
     *
     * @return
     */
    @Override
    public int getItemCount()
    {
        return dikrs.size();
    }

    /**
     * this is a class extends from RecyclerViewHolder
     */
    static class dikrViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtDikr;
        TextView txtCount;

        /**
         * this is methods to initialise attributes
         *
         * @param itemView
         * @param dikrClickListener
         */
        public dikrViewHolder(@NonNull View itemView, final OnDikrClickListener dikrClickListener)
        {
            super(itemView);
            txtDikr = itemView.findViewById(R.id.txtDikr);
            txtCount = itemView.findViewById(R.id.txtCount);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                /**
                 * this is on click listener
                 * @param v
                 */
                @Override
                public void onClick(View v)
                {
                    if (dikrClickListener != null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            dikrClickListener.OnDikrClick(position);
                        }
                    }
                }
            });
        }
    }
}
