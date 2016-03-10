package com.segment.analytics.sample;

import android.app.Application;
import android.widget.Toast;
import com.segment.analytics.Analytics;
import com.segment.analytics.android.integrations.flurry.FlurryIntegration;
import com.segment.analytics.android.integrations.google.analytics.GoogleAnalyticsIntegration;
import com.segment.analytics.android.integrations.mixpanel.MixpanelIntegration;

public class SampleApp extends Application {

  private static final String ANALYTICS_WRITE_KEY = "l8v1ga655b";

  @Override public void onCreate() {
    super.onCreate();

    // Initialize a new instance of the Analytics client.
    Analytics.Builder builder = new Analytics.Builder(this, ANALYTICS_WRITE_KEY)
            .use(GoogleAnalyticsIntegration.FACTORY)
            .use(MixpanelIntegration.FACTORY)
            .use(FlurryIntegration.FACTORY);
    if (BuildConfig.DEBUG) {
      builder.logLevel(Analytics.LogLevel.VERBOSE);
    }

    // Set the initialized instance as a globally accessible instance.
    Analytics.setSingletonInstance(builder.build());

    // Now anytime you call Analytics.with, the custom instance will be returned.
    Analytics.with(this).track("App Launched");

    // If you need to listen for
    Analytics.with(this).onIntegrationReady("Segment.io", new Analytics.Callback() {
      @Override public void onReady(Object instance) {
        Toast.makeText(SampleApp.this, "Segment integration!", Toast.LENGTH_LONG).show();
      }
    });
  }
}
