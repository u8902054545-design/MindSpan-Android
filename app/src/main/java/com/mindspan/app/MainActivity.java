package com.mindspan.app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Делаем статус-бар темным под стиль приложения
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#131314"));

        webView = new WebView(this);
        WebSettings settings = webView.getSettings();
        
        // Разрешаем выполнение JavaScript (нужно для календаря и навигации)
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setDatabaseEnabled(true);
        
        // Чтобы ссылки открывались внутри приложения, а не в браузере
        webView.setWebViewClient(new WebViewClient());
        
        // Загружаем начальную страницу
        webView.loadUrl("file:///android_asset/auth.html");
        
        setContentView(webView);
    }

    @Override
    public void onBackPressed() {
        // Если внутри WebView есть история переходов, возвращаемся назад
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
