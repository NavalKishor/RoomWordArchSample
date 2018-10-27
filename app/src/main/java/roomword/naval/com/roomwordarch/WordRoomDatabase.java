package roomword.naval.com.roomwordarch;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

@Database(entities = {Word.class}, version = 1)
public abstract class WordRoomDatabase extends RoomDatabase
{
    public abstract WordDao wordDao();
    private static volatile WordRoomDatabase INSTANCE;
    static WordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE= Room.databaseBuilder(
                            context.getApplicationContext(),
                            WordRoomDatabase.class,
                            "word_database")
                            /*
                            * If you don’t want to provide migrations and you specifically want your database
                            * to be cleared when you upgrade the version, call fallbackToDestructiveMigration in the database builder:
                            * So now, our app doesn’t crash, but we lose all the data. So be sure that
                            * this is how you specifically want to handle migrations.
                             * */
                            //.fallbackToDestructiveMigration()// empty migration or upgrade to db, delete and recreate just change version no above > previous version
                            //.addMigrations(MIGRATION_1_2)
                            //.addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                            //.addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
                            //.addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_1_4)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    // soon after the opening of database if you we can to perform some action we can use this call back
                    // every time it will be called.
                   // new PopulateDbAsync(INSTANCE).execute();
                    Log.i(this.getClass().getSimpleName(),   "RoomDatabase.Callback-->onOpen: ");
                }
    };
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>
    {

        private final WordDao mDao;

        PopulateDbAsync(WordRoomDatabase db) {
            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
          //  mDao.deleteAll();
            Word word = new Word("Hello");
            mDao.insert(word);
            word = new Word("World");
            mDao.insert(word);
            return null;
        }
    }

    // data base upgrade or update
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
            //Increase the version to 2
            //To keep the user’s data, we need to implement a migration.
            // Since the schema doesn’t change, we just need to provide an empty migration implementation
            // and tell Room to use it.
        }
    };
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            //Let’s add another column: last_update, to our Word table, by modifying the Word class.
            //Increase the version to 3
            database.execSQL("ALTER TABLE word_table ADD COLUMN last_update INTEGER");
        }
    };
    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
         //   For example, changing the id of the word from an int to a String takes several steps
            //Increase the version to 4
            // Create the new table
            database.execSQL("CREATE TABLE word_table_new (userid TEXT, word TEXT, last_update INTEGER, PRIMARY KEY(word))");
// Copy the data
            database.execSQL("INSERT INTO word_table_new ( word, last_update) SELECT  word, last_update FROM word_table");
// Remove the old table
            database.execSQL("DROP TABLE word_table");
// Change the table name to the correct one
            database.execSQL("ALTER TABLE word_table_new RENAME TO word_table");
        }
    };
    static final Migration MIGRATION_1_4 = new Migration(1, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            /*
            What if your users have an old version of your app, running database version 1,
            and want to upgrade to version 4? So far, we have defined the following migrations:
            version 1 to 2, version 2 to 3, version 3 to 4, so Room will trigger all migrations, one after another.

            Room can handle more than one version increment: we can define a migration that goes
            from version 1 to 4 in a single step, making the migration process faster.
            */
            //Increase the version to 4
            // Create the new table
            database.execSQL(
                    "CREATE TABLE users_new (userid TEXT, username TEXT, last_update INTEGER, PRIMARY KEY(userid))");

            // Copy the data
            database.execSQL(
                    "INSERT INTO users_new (userid, username, last_update) SELECT userid, username, last_update FROM users");
// Remove the old table
            database.execSQL("DROP TABLE users");
// Change the table name to the correct one
            database.execSQL("ALTER TABLE users_new RENAME TO users");
        }
    };
}
