package net.c0f3.jjsc.core;

import java.io.Serializable;

/**
 * User: KPC
 * Date: 08.11.12 - 12:54
 */
public interface ScriptDataContext extends Serializable {

    void setCounter(Integer counter);
    void saveValue(String key, Object value);
    Object removeValue(String key);
    Object getValue(String key);

    void subscribeToEvent(String eventName);
    void unSubscribeFromEvent(String eventName);

    ScriptGateway getGateway();

    Object getDebugger();

    void setActive(boolean isActive);
}
