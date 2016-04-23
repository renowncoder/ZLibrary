package com.duzhou.zlibray.listadapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.AbsListView;
import android.widget.ListView;

import com.duzhou.zlibray.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhou on 16-4-23.
 */
public class TestActivity extends AppCompatActivity implements AbsListView.OnScrollListener {


    private ListView listView;
    private List<String> list;
    private ListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listadapter_test);

        listView = (ListView) findViewById(R.id.listView1);
        list = getList();
        adapter = new ListAdapter(this, list);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(this);
        listView.setSelection(list.size());

    }


    /**
     * 获取数据
     *
     * @return
     */
    public List<String> getList() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }


    /**
     * Callback method to be invoked while the list view or grid view is being scrolled. If the
     * view is being scrolled, this method will be called before the next frame of the scroll is
     * rendered. In particular, it will be called before any calls to
     * {@link Adapter#getView(int, View, ViewGroup)}.
     *
     * @param view        The view whose scroll state is being reported
     * @param scrollState The current scroll state. One of
     *                    {@link #SCROLL_STATE_TOUCH_SCROLL} or {@link #SCROLL_STATE_IDLE}.
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    /**
     * Callback method to be invoked when the list or grid has been scrolled. This will be
     * called after the scroll has completed
     *
     * @param view             The view whose scroll state is being reported
     * @param firstVisibleItem the index of the first visible cell (ignore if
     *                         visibleItemCount == 0)
     * @param visibleItemCount the number of visible cells
     * @param totalItemCount   the number of items in the list adaptor
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
/**到顶部添加数据关键<span style="font-size:14px;">代码</span>*/
        if (firstVisibleItem <= 2) {
            listView.setSelection(list.size() + 2);
        } else if (firstVisibleItem + visibleItemCount > adapter.getCount() - 2) {//到底部添加数据
            listView.setSelection(firstVisibleItem - list.size());
        }
    }
}
