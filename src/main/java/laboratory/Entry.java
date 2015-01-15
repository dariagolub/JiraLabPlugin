package laboratory;

import net.java.ao.Entity;
import net.java.ao.Preload;

@Preload
public interface Entry extends Entity{

    String getEntry();

    void setEntry(String entry_name);

    boolean isComplete();

    void setComplete(boolean complete);

}
