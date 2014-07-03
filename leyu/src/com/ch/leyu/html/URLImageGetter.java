
package com.ch.leyu.html;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html.ImageGetter;
import android.widget.TextView;

import com.ch.leyu.utils.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class URLImageGetter implements ImageGetter {
    Context context;

    TextView textView;

    public URLImageGetter(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }

    @Override
    public Drawable getDrawable(String paramString) {
        final URLDrawable urlDrawable = new URLDrawable(context);
        ImageGetterAsyncTask getterTask = new ImageGetterAsyncTask(urlDrawable);
        getterTask.execute(paramString);
        return urlDrawable;
    }

    public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
        URLDrawable urlDrawable;

        public ImageGetterAsyncTask(URLDrawable drawable) {
            this.urlDrawable = drawable;
        }

        @Override
        protected void onPostExecute(Drawable result) {
            if (result != null) {
                urlDrawable.drawable = result;
                URLImageGetter.this.textView.requestLayout();
            }
        }

        @Override
        protected Drawable doInBackground(String... params) {
            String source = params[0];

            Bitmap bitmapOrg = ImageLoader.getInstance().loadImageSync(source,ImageLoaderUtil.getImageOptions());
            Rect bounds = urlDrawable.getDefaultImageBounds(context);
            Bitmap bitmap = Bitmap.createScaledBitmap(bitmapOrg, bounds.right, bounds.bottom,true);
            BitmapDrawable drawable = new BitmapDrawable(bitmap);
            drawable.setBounds(bounds);
            return drawable;
//            return fetchDrawable(source);
        }

//        public Drawable fetchDrawable(String url) {
//            try {
//                InputStream is = fetch(url);
//
//                Rect bounds = urlDrawable.getDefaultImageBounds(context);
//                Bitmap bitmapOrg = BitmapFactory.decodeStream(is);
//                Bitmap bitmap = Bitmap.createScaledBitmap(bitmapOrg, bounds.right, bounds.bottom,true);
//
//                BitmapDrawable drawable = new BitmapDrawable(bitmap);
//                drawable.setBounds(bounds);
//
//                return drawable;
//            } catch (ClientProtocolException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        private InputStream fetch(String url) throws ClientProtocolException, IOException {
//            DefaultHttpClient client = new DefaultHttpClient();
//            HttpGet request = new HttpGet(url);
//
//            HttpResponse response = client.execute(request);
//            return response.getEntity().getContent();
//        }
    }
}
