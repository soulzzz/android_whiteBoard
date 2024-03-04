package com.goke.whiteboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.goke.wblib.utils.OperationUtils;
import com.goke.whiteboard.utils.FileUtil;
import com.goke.whiteboard.utils.StoreUtil;
import com.goke.whiteboard.utils.ToastUtils;
import com.goke.whiteboard.view.base.BaseActivity;
import com.goke.whiteboard.view.WhiteBoardActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import butterknife.InjectView;


public class MainActivity extends BaseActivity {

    @InjectView(R.id.lv_wb)
    ListView mLv;
    @InjectView(R.id.iv_wb_add)
    ImageView mIvAdd;


    private WbAdapter mWbAdapter;
    ArrayList<String> filenames = new ArrayList<>();
    ArrayList<String> filepaths = new ArrayList<>();;
    private long mBackPressedTime;
    private final String TAG = "WB_MainActivity";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
//        try {
//            loadData();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        initView();
    }

    private void loadData() throws IOException {
        String path = StoreUtil.getWbPath(this);
        Log.d(TAG, "loadData: "+path);
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        final File[] files = folder.listFiles();
        Arrays.sort(files, new Comparator<File>() {
            public int compare(File f1, File f2) {
                long diff = f1.lastModified() - f2.lastModified();
                if (diff > 0)
                    return -1;
                else if (diff == 0)
                    return 0;
                else
                    return 1;//如果 if 中修改为 返回-1 同时此处修改为返回 1  排序就会是递减
            }

            public boolean equals(Object obj) {
                return true;
            }

        });
        Log.d(TAG, "loadData: length"+files.length);
        filenames.clear();
        filepaths.clear();
        if (files.length > 0) {
            for (File f : files) {
                filenames.add(FileUtil.getFileName(f));
                filepaths.add(f.getAbsolutePath());
            }
        }else{
            File newfile = new File(path+"/Default_WhiteBoard");
            boolean state= newfile.createNewFile();
            Log.d(TAG, "loadData:state "+state +" "+path+"/Default_WhiteBoard");
            filenames.add(FileUtil.getFileName(newfile));
            filepaths.add(newfile.getAbsolutePath());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initView();
    }

    private void initView() {
        mWbAdapter = new WbAdapter();
        mLv.setAdapter(mWbAdapter);
        mIvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OperationUtils.getInstance().initDrawPointList();
                navi2Page(WhiteBoardActivity.class);
            }
        });
    }

    @Override
    public void onBackPressed() {
//        final long mCurrentTime = System.currentTimeMillis();
//        if (mCurrentTime - this.mBackPressedTime > 1000) {
//            ToastUtils.showToast(MainActivity.this, R.string.app_logout);
//            this.mBackPressedTime = mCurrentTime;
//            return;
//        }
        super.onBackPressed();
        System.exit(0);
    }

    private class WbAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return filenames != null ? filenames.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Log.d(TAG, "getView:position "+position);
            WbViewHolder holder = null;
            if (convertView != null) {
                holder = (WbViewHolder) convertView.getTag();
            } else {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.wb_item, null);
                if (convertView != null) {
                    convertView.setTag(
                            holder = new WbViewHolder(convertView)
                    );
                }
                if (holder != null) {
                    holder.nWbName.setText(filenames.get(position));
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            Log.d(TAG, "onClick: "+position);
                            StoreUtil.readWhiteBoardPoints(filepaths.get(position));
                            navi2Page(WhiteBoardActivity.class);
                        }
                    });
                    convertView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {

                            File file = new File(filepaths.get(position));
                            if(file.delete()){
                                Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                filenames.remove(position);
                                filepaths.remove(position);
                                initView();
                            }else{
                                Toast.makeText(MainActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                            }
                            return true;
                        }
                    });
                }
            }
            return convertView;
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(String event){
    }
    private final class WbViewHolder {
        final TextView nWbName;

        public WbViewHolder(final View view) {
            this.nWbName = (TextView) view.findViewById(R.id.tv_wb_name);
        }
    }
}
