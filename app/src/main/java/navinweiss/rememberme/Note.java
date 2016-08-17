package navinweiss.rememberme;

import com.google.firebase.database.Exclude;

/**
 * Created by weissna on 8/5/2016.
 */
public class Note {
    public String mNote;
    private String key;

    public Note(){

    }

    public Note(String note){
        this.mNote = note;
    }

    public String getNote(){
        return mNote;
    }

    public void setNote(String note){
        this.mNote = note;
    }

    @Exclude
    public String getKey(){
        return key;
    }

    public void setKey(String key){
        this.key = key;
    }

    public void setValue(Note updateNote) {
        this.mNote = updateNote.mNote;
    }

}
