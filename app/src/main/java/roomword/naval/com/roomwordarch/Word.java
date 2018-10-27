package roomword.naval.com.roomwordarch;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
/**
 * this class should be same as the table we have in our database.
 * class name can we same as table name if not then give the mapped referenced with help of @Entity annotation.
 * class variable are column and follow the same property as classname but with annotation @ColumnInfo
 * if you are making the variable private then its complusory  to provide getter and setter as public.
 * */
@Entity(tableName = "word_table")
public class Word
{
//    @PrimaryKey
//    private int uid;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;
    public Word(@NonNull String word) {this.mWord = word;}

    public String getWord(){return this.mWord;}
}
