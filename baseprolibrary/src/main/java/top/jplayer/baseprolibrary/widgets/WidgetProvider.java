package top.jplayer.baseprolibrary.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import top.jplayer.baseprolibrary.R;
import top.jplayer.baseprolibrary.ui.activity.ActivityCustomCapture;

/**
 * Created by Obl on 2018/8/9.
 * top.jplayer.baseprolibrary.widgets
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class WidgetProvider extends AppWidgetProvider {
    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                 int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
        Intent intent = new Intent(context, ActivityCustomCapture.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.ivSrc, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}
