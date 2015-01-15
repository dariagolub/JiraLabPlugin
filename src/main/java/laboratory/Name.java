package laboratory;

import net.java.ao.Entity;
import net.java.ao.Preload;

@Preload
public interface Name extends Entity {
    String getDescription();

    void setDescription(String description);

    boolean isComplete();

    void setComplete(boolean complete);
}
