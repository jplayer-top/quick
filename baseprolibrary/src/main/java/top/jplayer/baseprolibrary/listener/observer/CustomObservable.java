package top.jplayer.baseprolibrary.listener.observer;

import java.util.Observer;
import java.util.Vector;

import top.jplayer.baseprolibrary.BaseInitApplication;

/**
 * Created by Obl on 2018/3/12.
 * top.jplayer.baseprolibrary.listener.observer
 */

public class CustomObservable {
    private boolean changed = false;
    private Vector<CustomObserver> obs;

    public CustomObservable() {
        obs = new Vector<>();
    }

    public synchronized void addObserver(CustomObserver o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.addElement(o);
        }

    }

    public synchronized void addObserver(String key) {
        if (key == null || "".equals(key))
            throw new NullPointerException();
        CustomObserver o = BaseInitApplication.mObserverMap.get(key);
        if (o != null && !obs.contains(o)) {
            obs.addElement(o);
        }
    }

    public void change(Object itg) {
        // setChanged 一下才能通知
        setChanged();
        //通知观察者
        notifyObservers(itg);
    }

    public synchronized void deleteObserver(Observer o) {
        obs.removeElement(o);
    }

    public void notifyObservers() {
        notifyObservers(null);
    }

    public void notifyObservers(Object arg) {

        Object[] arrLocal;

        synchronized (this) {

            if (!hasChanged())
                return;
            arrLocal = obs.toArray();
            clearChanged();
        }

        for (int i = arrLocal.length - 1; i >= 0; i--)
            ((CustomObserver) arrLocal[i]).update(this, arg);
    }

    public synchronized void deleteObservers() {
        obs.removeAllElements();
    }

    protected synchronized void setChanged() {
        changed = true;
    }


    protected synchronized void clearChanged() {
        changed = false;
    }


    public synchronized boolean hasChanged() {
        return changed;
    }


    public synchronized int countObservers() {
        return obs.size();
    }
}
