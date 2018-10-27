package roomword.naval.com.roomwordarch;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * An interface class which will help to have acccess of crud operation with there respective annotation and parameters.
 * */
@Dao
public interface WordDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Word word);
    @Insert
    public void insertBothUsers(Word user1, Word user2);
    @Insert
    void insertAll(Word... users);
    @Insert
    public void insertUsersAndFriends(Word user, List<Word> friends);// here in List different class can be there

    @Update
    public void updateUsers(Word... users);

    @Delete
    void delete(Word user); //single word or row
    @Delete
    public void deleteUsers(Word... users); // multiple row got deleted

    @Query("DELETE FROM word_table")
    void deleteAll(); // all row deleted

    @Query("SELECT * from word_table ORDER BY word ASC")
    LiveData<List<Word>> getAllWords(); // Observable queries

    @Query("SELECT * FROM word_table WHERE word BETWEEN :minAge AND :maxAge")
    public  LiveData<List<Word>> loadAllWordsBetweenRange(String minAge, String maxAge); // Observable queries

    @Query("SELECT word FROM word_table WHERE word LIKE :search  OR word LIKE :search")
    public List<Word> findUserWithName(String search); // non Observable queries

    @Query("SELECT * FROM word_table WHERE word IN (:regions)")
    public List<Word> loadUsersFromRegions(List<String> regions); // non Observable queries
}
