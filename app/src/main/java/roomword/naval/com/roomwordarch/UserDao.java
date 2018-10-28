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
public interface UserDao
{
    // another table users
    // Returns a list of all users in the database
    @Query("SELECT * FROM users")
    List<User> getAll();

    // Inserts multiple users
    @Insert
    void insertAll(User... users);

    // Deletes a single user
    @Delete
    void delete(User user);

    @Query("SELECT * FROM users WHERE firstName LIKE :first AND lastName LIKE :last LIMIT 1")
    User findByName(String first, String last);
}
