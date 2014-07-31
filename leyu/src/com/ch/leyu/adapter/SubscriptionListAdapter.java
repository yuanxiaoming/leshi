
package com.ch.leyu.adapter;

import com.ch.leyu.R;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.Info;
import com.ch.leyu.responseparse.StarDetailResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.utils.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/***
 * 我的订阅适配器
 * 
 * @author L
 */
public class SubscriptionListAdapter extends BaseAdapter{
    
    private ArrayList<Info> mArrayList;

    private Context mContext;
    
    private String mLoginUid, mAuth, mPassStr ;
    

    public SubscriptionListAdapter(Context context, ArrayList<Info> objects,String mLoginUid,String mAuth,String mPassStr) {
        this.mArrayList = objects;
        this.mContext = context;
        this.mLoginUid = mLoginUid;
        this.mAuth = mAuth;
        this.mPassStr = mPassStr;
    }

    public void chargeArrayList(ArrayList<Info> arrayList) {
        this.mArrayList = arrayList;
        notifyDataSetChanged();
    }

    public void addArrayList(ArrayList<Info> arrayList) {
        if (mArrayList != null) {
            this.mArrayList.addAll(arrayList);
            notifyDataSetChanged();
        } else {
            chargeArrayList(arrayList);
        }
    }

    @Override
    public int getCount() {
        if (mArrayList != null) {
            return mArrayList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mArrayList != null) {
            return mArrayList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.subscription_list_item,parent, false);
            viewHolder = new ViewHolder();
            viewHolder.cover = (ImageView) convertView.findViewById(R.id.fragment_subscription_imageview);
            viewHolder.name = (TextView) convertView.findViewById(R.id.fragment_subscription_textview);
            viewHolder.cancel = (Button) convertView.findViewById(R.id.fragment_subscription_btn);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Info item = (Info) getItem(position);
        if (item != null) {
            viewHolder.name.setText(mArrayList.get(position).getNickname());
            ImageLoader.getInstance().displayImage(item.getThumb(), viewHolder.cover,ImageLoaderUtil.getImageLoaderOptions());
        }
        
        viewHolder.cancel.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                requestData(mArrayList.get(position).getUid(), position);
            }
        });

        return convertView;
    }

    class ViewHolder {

        ImageView cover;

        TextView name;
        
        Button cancel;

    }
    
    public void requestData(String uid,final int position){
        RequestParams params = new RequestParams();
        params.put("action", "mySubscribe");
        params.put(Constant.SORT, "cancel");
        params.put(Constant.UID, uid);
        params.put(Constant.AUTH,mAuth);
        params.put(Constant.LOGIN_UID,mLoginUid);
        params.put(Constant.PASS_STR,mPassStr);
        Log.d("tag", JHttpClient.getUrlWithQueryString(Constant._URL, params));
        JHttpClient.getFromServer(mContext, Constant._URL, params , StarDetailResponse.class, new DataCallback<StarDetailResponse>() {

            @Override
            public void onStart() {
                
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, StarDetailResponse data) {
                    mArrayList.remove(position);
                    chargeArrayList(mArrayList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                    Exception exception) {
                
            }

            @Override
            public void onFinish() {
                
            }
       
        });
    }
    
}
