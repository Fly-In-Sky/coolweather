package activity;

import util.HttpCallbackListener;
import util.HttpUtil;
import util.Utility;
import android.app.Activity;
import android.app.DownloadManager.Query;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coolweather.app.R;

public class WeatherActivity extends Activity {

	private LinearLayout weatherInfoLayout;
	// ������ʾ������
	private TextView cityNameText;
	// ������ʾ������ʱ��
	private TextView publishText;
	// ������ʾ����������Ϣ
	private TextView weatherDespText;
	// ������ʾ����1
	private TextView temp1Text;
	// ������ʾ����2
	private TextView temp2Text;
	// ������ʾ��ǰ����
	private TextView currentDateText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather_layout);

		// ��ʼ�����ֿؼ�
		weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
		cityNameText = (TextView) findViewById(R.id.city_name);
		publishText = (TextView) findViewById(R.id.publish_text);
		weatherDespText = (TextView) findViewById(R.id.weather_desp);
		temp1Text = (TextView) findViewById(R.id.temp1);
		temp2Text = (TextView) findViewById(R.id.temp2);
		currentDateText = (TextView) findViewById(R.id.current_date);

		// ���Դ�intent��ȡ���ؼ�����

		String countyCode = getIntent().getStringExtra("county_code");
		if (!TextUtils.isEmpty(countyCode)) {
			// ���ؼ����ž�ȥ��ѯ����
			publishText.setText("ͬ����...");
			weatherInfoLayout.setVisibility(View.INVISIBLE);
			cityNameText.setVisibility(View.INVISIBLE);
			queryWeatherCode(countyCode);

		} else {
			// û���ؼ�����ʱ��ֱ����ʾ��������
			showWeather();

		}

	}

	// ��ѯ�ؼ����Ŷ�Ӧ����������
	private void queryWeatherCode(String countyCode) {
		// TODO Auto-generated method stub
		String address = "http://www.weather.com.cn/data/list3/city"
				+ countyCode + ".xml";
		queryFromServer(address, "countyCode");

	}

	// ��ѯ�������Ŷ�Ӧ��������
	private void queryweatherInfo(String weatherCode) {
		// TODO Auto-generated method stub
		String address = "http://www.weather.com.cn/data/cityinfo/"
				+ weatherCode + ".html";
		queryFromServer(address, "weatherCode");

	}

	// ���ݴ���ĵ�ַ������ȥ���������ѯ�������Ż���������Ϣ��
	private void queryFromServer(final String address, final String type) {
		// TODO Auto-generated method stub

		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {

			@Override
			public void onFinish(final String response) {
				// TODO Auto-generated method stub
				if ("countyCode".equals(type)) {
					if (!TextUtils.isEmpty(response)) {
						// �ӷ��������ص������н�������������
						String[] array = response.split("\\|");
						if (array != null && array.length == 2) {
							String weatherCode = array[1];
							queryweatherInfo(weatherCode);
						}
					}
				} else if ("weatherCode".equals(type)) {
					// �������������ص�������Ϣ
					Utility.handleWeatherResponse(WeatherActivity.this,
							response);
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							showWeather();
						}
					});
				}

			}

			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						publishText.setText("ͬ��ʧ��");
					}
				});

			}
		});

	}

	// ��SharedPreferences�ļ��ж�ȡ�洢��������Ϣ������ʾ�������ϡ�
	private void showWeather() {
		// TODO Auto-generated method stub
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		cityNameText.setText(prefs.getString("city_name", ""));
		temp1Text.setText(prefs.getString("temp1", ""));
		temp2Text.setText(prefs.getString("temp2", ""));
		weatherDespText.setText(prefs.getString("weather_desp", ""));
		publishText.setText("����" + prefs.getString("publish_time", "����"));
		currentDateText.setText(prefs.getString("current_date", ""));
		weatherInfoLayout.setVisibility(View.VISIBLE);
		cityNameText.setVisibility(View.VISIBLE);

	}

}