package processor;

import listener.event.SelectEventListener;
import listener.event.UpdateEventListener;

public interface DBProcessor extends SelectEventListener, UpdateEventListener {
}
