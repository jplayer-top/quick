package top.jplayer.baseprolibrary.net.progress;

/**
 * Created by PEO on 2017/4/10.
 */

public interface IProgress {
    void tipStart();

    void tipEnd();

    void tipComplete();

    void tipSuccess(String msg);

    void tipFail(String code, String msg);

    void tipError(Throwable t);
}
