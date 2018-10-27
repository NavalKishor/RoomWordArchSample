package roomword.naval.com.roomwordarch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder>
{
    private  LayoutInflater mInflater;
    private List<Word> mWords; // Cached copy of words
    WordListAdapter(Context context) { mInflater = LayoutInflater.from(context); }


    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
    {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder wordViewHolder, int position)
    {
        if (mWords != null) {
            Word current = mWords.get(position);
            wordViewHolder.wordItemView.setText(current.getWord());
        } else {
            // Covers the case of data not being ready yet.
            wordViewHolder.wordItemView.setText("No Word");
        }

    }

    @Override
    public int getItemCount()
    {
        if (mWords != null)
            return mWords.size();
        else return 0;

    }
    void setWords(List<Word> words){
        mWords = words;
        notifyDataSetChanged();
    }

    public class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        public WordViewHolder(@NonNull View itemView)
        {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }
}
