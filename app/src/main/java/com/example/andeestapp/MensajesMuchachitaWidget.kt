package com.example.andeestapp

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.example.data.Campos
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

/**
 * Implementation of App Widget functionality.
 */
const val WIDGET_SYNC = "WIDGET_SYNC"

class MensajesMuchachitaWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent?) {

        if (WIDGET_SYNC == intent?.action) {
            val appWidgetId = intent.getIntExtra("appWidgetId", 0)

            updateAppWidget(context, AppWidgetManager.getInstance(context), appWidgetId)
        }
        super.onReceive(context, intent)
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val widgetText =
        arrayOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")
    val db = Firebase.firestore
    val citiesRef = db.collection("Mensaje").document("Muchachita")

    citiesRef.get()
        .addOnSuccessListener { documentReference ->
            if (documentReference != null) {
                val intent = Intent(context, MensajesMuchachitaWidget::class.java)
                intent.action = WIDGET_SYNC
                intent.putExtra("appWidgetId", appWidgetId)
                val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
                // Construct the RemoteViews object
                val views = RemoteViews(context.packageName, R.layout.mensajes_muchachita_widget)
                views.setTextViewText(R.id.appwidget_text,
                    documentReference.get("mensaje").toString()
                )
                views.setOnClickPendingIntent(R.id.iv_sync, pendingIntent)

                // Instruct the widget manager to update the widget
                appWidgetManager.updateAppWidget(appWidgetId, views)

            } else {

            }

        }
        .addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Error adding document", e)
        }

    val randomIndex = Random.nextInt(widgetText.size)
    val randomElement = widgetText[randomIndex]

}