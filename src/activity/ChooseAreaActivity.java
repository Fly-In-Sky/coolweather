package activity;

//2017��11��22�� 23:17:02
//��д����ʡ�������ݵĻ

import java.util.ArrayList;
import java.util.List;

import model.City;
import model.County;
import model.Province;
import util.HttpCallbackListener;
import util.HttpUtil;
import util.Utility;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coolweather.app.R;

import db.CoolWeatherDB;



public class ChooseAreaActivity extends Activity {

	public static final int LEVEL_PROVINCE = 0;
	public static final int LEVEL_CITY = 1;
	public static final int LEVEL_COUNTY = 2;

	private ProgressDialog progressDialog;
	private TextView titleText;
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private CoolWeatherDB coolWeatherDB;
	private List<String> dataList = new ArrayList<String>();

	// ʡ�б�
	private List<Province> provinceList;

	// ���б�
	private List<City> cityList;

	// ���б�
	private List<County> countyList;

	// ѡ�е�ʡ��
	private Province selectedProvince;

	// ѡ�еĳ���
	private City selectedCity;

	// ��ǰѡ�еļ���
	private int currentLever1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_area);
		listView = (ListView) findViewById(R.id.list_view);
		titleText = (TextView) findViewById(R.id.title_text);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, dataList);
		listView.setAdapter(adapter);
		coolWeatherDB = CoolWeatherDB.getInstance(this);
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int index,
					long arg3) {
				if (currentLever1 == LEVEL_PROVINCE) {
					selectedProvince = provinceList.get(index);
					queryCities();
				} else if (currentLever1 == LEVEL_CITY) {
					selectedCity = cityList.get(index);
					queryCounties();
				}
			}

		});
		queryProvinces();      //����ʡ������
		

	}
	
	
	
	//��ѯȫ�����е�ʡ�����ȴ����ݿ��ѯ�����û�в�ѯ����ȥ�������ϲ�ѯ
	
	private void queryProvinces() {
		// TODO Auto-generated method stub
		
		provinceList = coolWeatherDB.loadProvinces();
		if (provinceList.size() > 0) {
			dataList.clear();
			for (Province province : provinceList) {
				dataList.add(province.getProvinceName());
			}
			
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleText.setText("�й�");
			currentLever1 = LEVEL_PROVINCE;
		} else {
			queryFromServer(null , "province");
		}
		
	}
	
	//��ѯȫ�����е��У����ȴ����ݿ��ѯ�����û�в�ѯ����ȥ�������ϲ�ѯ
	
	private void queryCities() {
		// TODO Auto-generated method stub
		
		cityList = coolWeatherDB.loadCities(selectedProvince.getId());
		if (cityList.size() > 0) {
			dataList.clear();
			for (City city : cityList) {
				dataList.add(city.getCityName());
			}
			
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleText.setText(selectedProvince.getProvinceName());
			currentLever1 = LEVEL_CITY;
		} else {
			queryFromServer(selectedProvince.getProvinceCode() , "city");
		}
		
	}
	
	//��ѯȫ�����е��أ����ȴ����ݿ��ѯ�����û�в�ѯ����ȥ�������ϲ�ѯ
	
	private void queryCounties() {
		// TODO Auto-generated method stub
		
		countyList = coolWeatherDB.loadCounties(selectedCity.getId());
		if (countyList.size() > 0) {
			dataList.clear();
			for (County county : countyList) {
				dataList.add(county.getCountyName());
			}
			
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleText.setText(selectedCity.getCityName());
			currentLever1 = LEVEL_COUNTY;
		} else {
			queryFromServer(selectedCity.getCityCode() , "county");
		}
		
	}
	
	
	
	//���ݴ���Ĵ������ʹӷ������ϲ�ѯʡ��������
	private void queryFromServer(final String code , final String type) {
		// TODO Auto-generated method stub
		String address;
		if (!TextUtils.isEmpty(code)) {
			address = "http://www.weather.com.cn/data/list3/city"+code+".xml";
		} else {
			address = "http://www.weather.com.cn/data/list3/city.xml";
		}
		showProgressDialog();
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				boolean result = false;
				if ("province".equals(type)) {
					result = Utility.handleProvincesResponse(coolWeatherDB, response);
					
				} else if("city".equals(type)){
					result = Utility.handleCitiesResponse(coolWeatherDB,
							response, selectedProvince.getId());
				}else if("county".equals(type)){
					result = Utility.handleCountiesResponse(coolWeatherDB,
							response, selectedCity.getId());
				}
				if (result) {
					//ͨ��runOnUiThread()�����ص����̴߳����߼�
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							closeProgressDialog();
							if ("province".equals(type)) {
								queryProvinces();
							} else if ("city".equals(type)) {
								queryCities();
							} else if ("county".equals(type)) {
								queryCounties();
							} 
							
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
						closeProgressDialog();
						Toast.makeText(ChooseAreaActivity.this,
								"����ʧ��", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});

	}
	
	
	//��ʾ���ȶԻ���
	
	private void showProgressDialog() {
		// TODO Auto-generated method stub
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("���ڼ���...");
			progressDialog.setCanceledOnTouchOutside(false);
		}
		progressDialog.show();
		
	}
	
	//�رս��ȶԻ���
	private void closeProgressDialog() {
		// TODO Auto-generated method stub
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}
	
	
	//����back���������ݵ�ǰ�ļ������жϣ���ʱӦ�÷������б���ʡ�б�������ֱ���˳���
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		if (currentLever1 == LEVEL_COUNTY) {
			queryCities();
		} else if (currentLever1 == LEVEL_CITY) {
			queryProvinces();
		} else{
			finish();
		}
	}
	
	
	

}