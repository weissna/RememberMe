package navinweiss.rememberme;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weissna on 8/5/2016.
 */
public class Note extends RecyclerView.Adapter<Note.ViewHolder> {
    private RecyclerView mRecyclerView;
    private List <String> mNoteList = new ArrayList<>();

    public Note(RecyclerView recyclerView){
        this.mRecyclerView = recyclerView;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mNoteText.setText(mNoteList.get(position));
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    private void deleteItem(int pos) {
        mNoteList.remove(pos);
        notifyItemRemoved(pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mNoteText;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(new View.OnLongClickListener(){

                @Override
                public boolean onLongClick(View v) {
                    deleteItem(getAdapterPosition());
                    return false;
                }
            });

            mNoteText = (TextView)itemView.findViewById(R.id.note_text);
        }
    }


}
