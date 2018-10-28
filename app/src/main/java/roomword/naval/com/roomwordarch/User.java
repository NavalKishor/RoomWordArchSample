package roomword.naval.com.roomwordarch;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

@Entity(tableName = "users")
public class User
{
    @PrimaryKey(autoGenerate = true) // Denotes id as the primary key
    @ColumnInfo(name = "_id")
    public int id;
    public String firstName;
    public String lastName;

    @Ignore // Tells Room to ignore this field
    Bitmap picture;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
