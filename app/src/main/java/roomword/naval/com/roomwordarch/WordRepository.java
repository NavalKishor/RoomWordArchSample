package roomword.naval.com.roomwordarch;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;
/**
 * A class which will become a bridge or adapter to have access the data from different resource.
 * like from database or network
 * */
public class WordRepository
{
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;
    WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
    }
    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }
    public void insert (Word word) {
        new insertAsyncTask(mWordDao).execute(word);
    }
    private static class insertAsyncTask extends AsyncTask<Word, Void, Void>
    {

        private WordDao mAsyncTaskDao;

        insertAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
