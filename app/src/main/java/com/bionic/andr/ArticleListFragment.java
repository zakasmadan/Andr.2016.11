package com.bionic.andr;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**  */
public class ArticleListFragment extends Fragment {
    private static final String TAG = ArticleListFragment.class.getSimpleName();

    private static final char[] TITLES = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    private ListView listView;

    private ArticleAdapter adapter;

    @Nullable
    private OnArticleSelectionListener listener;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            listener = (OnArticleSelectionListener) getActivity();
        } catch (Exception e) {
            Log.e(TAG, "Activity is not OnArticleSelectionListener", e);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(android.R.id.list);
        adapter = new ArticleAdapter(getActivity(), TITLES);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (listener != null) {
                    listener.onArticleSelected(adapter.getItem(position));
                }
            }
        });
    }

    public interface OnArticleSelectionListener {
        void onArticleSelected(String title);
    }

    private static class ArticleAdapter extends BaseAdapter {

        private final LayoutInflater inflater;
        private final List<String> items;

        public ArticleAdapter(Context context, char[] item) {
            inflater = LayoutInflater.from(context);
            items = new ArrayList<>();
            for (char i : item) {
                items.add(Character.toString(i));
            }
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public String getItem(final int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(final int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.li_article, parent, false);
                holder.title = (TextView) convertView.findViewById(R.id.article_title);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final String text = getItem(position);
            holder.title.setText(text);

            return convertView;
        }
    }

    static class ViewHolder {
        TextView title;
    }

}
