package com.bashizip.andromed.util;



import com.bashizip.andromed.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CountriesLA extends BaseAdapter {

	LayoutInflater inflater;

	public CountriesLA(Context c) {
		super();
		inflater = LayoutInflater.from(c);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return COUNTRIES.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return COUNTRIES[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = new ViewHolder();

		if (convertView == null) {

			convertView = inflater.inflate(R.layout.item_pays, null);

			holder.tvNom = (TextView) convertView.findViewById(R.id.tv_nom);
			holder.tvZoneCount = (TextView) convertView
					.findViewById(R.id.tv_zone_count);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvNom.setText(COUNTRIES[position]);

		holder.tvZoneCount.setText("Zones:");
		return convertView;
	}

	private class ViewHolder {
		public TextView tvNom;
		TextView tvZoneCount;
	}

	final String[] COUNTRIES = new String[] { "Afghanistan", "Albania",
			"Algeria", "American Samoa", "Andorra", "Angola", "Anguilla",
			"Antarctica", "Antigua and Barbuda", "Argentina", "Armenia",
			"Aruba", "Australia", "Austria", "Azerbaijan", "Bahrain",
			"Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin",
			"Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegovina",
			"Botswana", "Bouvet Island", "Brazil",
			"British Indian Ocean Territory", "British Virgin Islands",
			"Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cote d'Ivoire",
			"Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands",
			"Central African Republic", "Chad", "Chile", "China",
			"Christmas Island", "Cocos (Keeling) Islands", "Colombia",
			"Comoros", "Congo","Congo (Democratic Republic of the) ", "Cook Islands", "Costa Rica", "Croatia",
			"Cuba", "Cyprus", "Czech Republic",
			 "Denmark", "Djibouti",
			"Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt",
			"El Salvador", "Equatorial Guinea", "Eritrea", "Estonia",
			"Ethiopia", "Faeroe Islands", "Falkland Islands", "Fiji",
			"Finland", "Former Yugoslav Republic of Macedonia", "France",
			"French Guiana", "French Polynesia", "French Southern Territories",
			"Gabon", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece",
			"Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala",
			"Guinea", "Guinea-Bissau", "Guyana", "Haiti",
			"Heard Island and McDonald Islands", "Honduras", "Hong Kong",
			"Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq",
			"Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan",
			"Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Laos",
			"Latvia", "Lebanon", "Lesotho", "Liberia", "Libya",
			"Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Madagascar",
			"Malawi", "Malaysia", "Maldives", "Mali", "Malta",
			"Marshall Islands", "Martinique", "Mauritania", "Mauritius",
			"Mayotte", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia",
			"Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia",
			"Nauru", "Nepal", "Netherlands", "Netherlands Antilles",
			"New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria",
			"Niue", "Norfolk Island", "North Korea", "Northern Marianas",
			"Norway", "Oman", "Pakistan", "Palau", "Panama",
			"Papua New Guinea", "Paraguay", "Peru", "Philippines",
			"Pitcairn Islands", "Poland", "Portugal", "Puerto Rico", "Qatar",
			"Reunion", "Romania", "Russia", "Rwanda", "Sqo Tome and Principe",
			"Saint Helena", "Saint Kitts and Nevis", "Saint Lucia",
			"Saint Pierre and Miquelon", "Saint Vincent and the Grenadines",
			"Samoa", "San Marino", "Saudi Arabia", "Senegal", "Seychelles",
			"Sierra Leone", "Singapore", "Slovakia", "Slovenia",
			"Solomon Islands", "Somalia", "South Africa",
			"South Georgia and the South Sandwich Islands", "South Korea",
			"Spain", "Sri Lanka", "Sudan", "Suriname",
			"Svalbard and Jan Mayen", "Swaziland", "Sweden", "Switzerland",
			"Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand",
			"The Bahamas", "The Gambia", "Togo", "Tokelau", "Tonga",
			"Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan",
			"Turks and Caicos Islands", "Tuvalu", "Virgin Islands", "Uganda",
			"Ukraine", "United Arab Emirates", "United Kingdom",
			"United States", "United States Minor Outlying Islands", "Uruguay",
			"Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam",
			"Wallis and Futuna", "Western Sahara", "Yemen", "Yugoslavia",
			"Zambia", "Zimbabwe" };

}
