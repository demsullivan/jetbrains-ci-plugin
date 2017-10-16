package ui.settings;

import com.intellij.util.messages.Topic;

public interface CiSettingsChangeNotifier {
    Topic<CiSettingsChangeNotifier> CI_SETTINGS_CHANGE_TOPIC = Topic.create("Ci settings change", CiSettingsChangeNotifier.class);

    void beforeAction(CiOptionsProvider options);
    void afterAction(CiOptionsProvider options);
}
