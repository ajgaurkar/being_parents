package com.maakservicess.beingparents.app_monitor.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.maakservicess.beingparents.app_monitor.Adapters.GuidanceFAQAdapter;
import com.maakservicess.beingparents.app_monitor.R;
import com.maakservicess.beingparents.app_monitor.controllers.GuidanceFAQListData;

import java.util.ArrayList;

/**
 * Created by Amey on 14-02-2017.
 */

public class Guidance_FAQ extends AppCompatActivity {
    private ListView guidanceListView;
    private ArrayList<GuidanceFAQListData> guidanceFAQListDataArrayList;
    private GuidanceFAQAdapter guidanceFAQAdapter;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance_faq);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        guidanceListView = (ListView) findViewById(R.id.guidance_faq_ListView);
        guidanceFAQListDataArrayList = new ArrayList<>();
        guidanceFAQListDataArrayList.add(new GuidanceFAQListData(1, "Is it ok to apply Kajal, Surma to newborn?", "Kajal should not be applied to eyes of newborn. It may contain lead like poisonous substance.", false));
        guidanceFAQListDataArrayList.add(new GuidanceFAQListData(2, "What kind of cloths should be used for newborn ?", "Loose cotton clothes are best for Indian climate and are skin friendly. Synthetic cloths can cause rashes. In winter, woolen clothes can be worn over cotton cloths. Baby should neither be over covered nor under covered. Former will cause hyperthermia and later will cause hypothermia. Babies could not produce sweat and hence are not efficient to control their body temperature effectively. ", false));
        guidanceFAQListDataArrayList.add(new GuidanceFAQListData(3, "My baby had nappy rash. What can be done for it ? ", "Nappy rash is a rash around waist due to prolong contact of the skin with wet cloth. Use of cotton cloth for preparing nappy and frequently changing nappy as soon as it gets wet will avoid nappy rash.\n" +
                "If baby already had a nappy rash, Keep baby without it for some time throughout the day. Commercially available cream for nappy rash could be used for mild rash. More severe rash needs evaluation and prescription from pediatrician.\n", false));
        guidanceFAQListDataArrayList.add(new GuidanceFAQListData(4, "Applying oil help in early closure of fontanel (small soft portion of scalp of baby) of baby ? ", "Fontanel provides space for rapidly developing brain of baby. It closes of its own at appropriate time. Generally between 12 to 18 months of age, fontanel closes. Applying oil will not alter the course.", false));
        guidanceFAQListDataArrayList.add(new GuidanceFAQListData(5, "My baby is few days old and he looks slightly yellow. Is it worrisome ? ", "All babies develop yellowish discoloration in initial few days of life, called as physiological jaundice. In few cases level of jaundice could be more and may need treatment in the form of phototherapy. Consult your pediatrician. If level of jaundice is low, no need to worry.", false));
        guidanceFAQListDataArrayList.add(new GuidanceFAQListData(6, "My baby cries while passing urine does he has pain in passing urine? ", "Newborn baby may pass urine after 48 hours of life. Passing urine may be irregular for initial few days. Once baby start accepting breast milk well, usually passes more than 6 times urine in a day. Crying before and after urine is a normal phenomenon and does not require any evaluation.", false));
        guidanceFAQListDataArrayList.add(new GuidanceFAQListData(7, "My baby is few days old and passes 5 to 6 times loose stool. Does he need medicine? ", "Newborn baby passes black colored sticky stool for initial few days followed by greenish colored transition stool for next few days.  After few days of birth baby passes golden yellow stool. If baby is on exclusive breast feeding, passing frequent stool is normal. Some baby may even pass stool less frequently (once in few days). If there is no vomiting and distention of abdomen it is also normal. If baby is on formula feed or on cow’s/ buffalo’s milk consult your pediatrician. ", false));
        guidanceFAQListDataArrayList.add(new GuidanceFAQListData(8, "My baby vomits in between feed. Is he sick?  ", "Babies usually swallow air during feeding and this causes vomiting after feeding or in between feed. This is normal phenomenon. If baby is gaining weight normally, there's no need of any investigation. If vomiting is frequent, yellow in color, associated with distension of abdomen or weight loss, consult your pediatrician immediately.  ", false));
        guidanceFAQListDataArrayList.add(new GuidanceFAQListData(9, "My baby had frequent sneezing and coughs few times in a day. Is he sick? ", "Frequent sneezing, hiccups and yawning is normal in newborn periods. Breathing may be noisy in some healthy children. Sometimes baby may breath fast for some time. This is normal. Consult your pediatrician if it is associated with difficulty in feeding. ", false));
        guidanceFAQListDataArrayList.add(new GuidanceFAQListData(10, "I had newborn baby girl of few days old. She had white/ red vaginal discharge from vagina. We are worried. What should we do?  ", "Female newborn may show vaginal discharge( white or red ) under maternal hormonal influence. No need to worry. But if it persist or bleeding increases, consult your paediatrician.", false));
        guidanceFAQListDataArrayList.add(new GuidanceFAQListData(11, "What should mother do if breast get hard and lumpy (engorgement)? What is a cause for this? ", "Incomplete emptying of breast (Poor sucking/ attachment of baby ) or Excessive production of milk will cause heaviness and pain in breast. If unattended engorgement can lead to increasing pain, redness and fever. In severe cases, it may cause breast abscess. ", false));
        guidanceFAQListDataArrayList.add(new GuidanceFAQListData(12, "Is it appropriate to use ghutti, gripe water, honey to newborn baby? ", "No. This can cause severe infection in babies and should be avoided. Mother’s milk is enough and baby do not need any other feed in first 6 months of life. ", false));

        guidanceFAQAdapter = new GuidanceFAQAdapter(getApplicationContext(), guidanceFAQListDataArrayList);
        guidanceListView.setAdapter(guidanceFAQAdapter);

        guidanceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("guidance listview click...........");
                GuidanceFAQListData guidanceFAQListData = guidanceFAQListDataArrayList.get(position);
                guidanceFAQListData.setExpandStatus(!guidanceFAQListData.getExpandStatus());
                guidanceFAQAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
