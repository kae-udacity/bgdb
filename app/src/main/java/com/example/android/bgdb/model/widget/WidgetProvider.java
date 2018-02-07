package com.example.android.bgdb.model.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.example.android.bgdb.R;
import com.example.android.bgdb.view.activity.DetailActivity;

/**
 * Implementation of App Widget functionality.
 */
public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
            Intent intent = new Intent(context, ListWidgetService.class);
            views.setRemoteAdapter(R.id.widget_list_view, intent);
            views.setEmptyView(R.id.widget_list_view, R.id.widget_empty_view);

            Intent detailIntent = new Intent(context, DetailActivity.class);
            PendingIntent onClickPendingIntent = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(detailIntent)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.widget_list_view, onClickPendingIntent);

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list_view);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
