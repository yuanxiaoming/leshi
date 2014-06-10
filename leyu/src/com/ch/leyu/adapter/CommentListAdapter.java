
package com.ch.leyu.adapter;

import com.ch.leyu.R;
import com.ch.leyu.responseparse.CommentDetail;
import com.ch.leyu.utils.LeUtils;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CommentListAdapter extends BaseAdapter {

	private ArrayList<CommentDetail> mArrayList;

	private LayoutInflater mInflater;

	private Context mContext;
	
	private LyOnClickListener mLyOnClickListener;
	
	public void setOnReplyClickListener(LyOnClickListener lyOnClickListener){
	    this.mLyOnClickListener = lyOnClickListener;
	}

	public CommentListAdapter(ArrayList<CommentDetail> arrayList, Context context) {
		if(arrayList==null){
			this.mArrayList = new ArrayList<CommentDetail>();
		}else{
			this.mArrayList = arrayList;
		}
		mInflater = LayoutInflater.from(context);
		mContext = context;
	}

	public void chargeArrayList(ArrayList<CommentDetail> arrayList) {
		this.mArrayList = arrayList;
		notifyDataSetChanged();
	}

	public void addArrayList(ArrayList<CommentDetail> arrayList) {
		if (mArrayList != null) {
			this.mArrayList.addAll(arrayList);
			notifyDataSetChanged();
		} else {
			chargeArrayList(arrayList);
		}
	}

	public ArrayList<CommentDetail>  getArrayList() {
		return this.mArrayList;
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
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.comment_list_item, null);
			holder.name = (TextView) convertView.findViewById(R.id.comment_list_tv_name);
			holder.detail = (WebView) convertView.findViewById(R.id.comment_list_web_detail);
			holder.time = (TextView) convertView.findViewById(R.id.comment_list_tv_time);
			holder.reply = (TextView) convertView.findViewById(R.id.comment_list_tv_reply);

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}
		
		
		if(mArrayList.get(position).getReplyNickname().equals("")){
		    holder.name.setText(mArrayList.get(position).getNickname());
		}else {
		  String s1 = "<font color=\"#818181\">回复</font> ";
		  String s = "<font color=\"#3F74A7\">"+mArrayList.get(position).getNickname()+"</font> ";
		  String s2 = "<font color=\"#3F74A7\">"+mArrayList.get(position).getReplyNickname()+"</font> ";
		  holder.name.setText(Html.fromHtml(s+s1+s2));
		}
		

		holder.time.setText(LeUtils.toDate(mArrayList.get(position).getCreateTime()));
		WebSettings settings = holder.detail.getSettings();
		// 设置布局算法
		settings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
		// 设置渲染等级
		settings.setRenderPriority(RenderPriority.HIGH);
		// 设置缓存模式
		settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		// 设置预览模式
		settings.setLoadWithOverviewMode(true);
		// 设置字体缩放级别
		settings.setTextSize(TextSize.NORMAL);
		// 设置支持js
		settings.setJavaScriptEnabled(true);
		// 设置处理客户端
		holder.detail.setWebViewClient(new WebViewClient());
		holder.detail.setBackgroundColor(Color.parseColor("#F0F0F0"));
		holder.detail.loadDataWithBaseURL("", mArrayList.get(position).getComment(), "text/html",
				"UTF-8", "");
		
		holder.reply.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                if(mLyOnClickListener!=null){
                    mLyOnClickListener.onReplyClick(v,mArrayList.get(position).getNickname());
                }
            }
        });

		return convertView;
	}

	class ViewHolder {
		TextView name;

		WebView detail;

		TextView time;

		TextView reply;

	}
	
 public interface LyOnClickListener {
	  
     public void onReplyClick(View v,String uName);
	}

}
