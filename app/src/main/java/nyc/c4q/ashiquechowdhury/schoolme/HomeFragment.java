package nyc.c4q.ashiquechowdhury.schoolme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.ashiquechowdhury.schoolme.swipe.SwipeStack;

public class HomeFragment extends Fragment implements SwipeStack.SwipeStackListener {

    private ArrayList<String> data; // CHANGE TO MODEL
    private SwipeStack swipeStack;
    private SwipeStackAdapter swipeAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.swipe_view_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        setViews();
    }

    public void setViews() {
        swipeStack = (SwipeStack) getView().findViewById(R.id.swipeStack);
        data = new ArrayList<>();
        swipeAdapter = new SwipeStackAdapter(data);
        swipeStack.setAdapter(swipeAdapter);
        swipeStack.setListener(this);
        fillWithTestData();
    }

    private void fillWithTestData() {
        for (int x = 0; x < 5; x++) {
            data.add(getString(R.string.school_text) + " " + (x + 1));
        }
    }

    @Override
    public void onViewSwipedToRight(int position) {
        String swipedElement = swipeAdapter.getItem(position);
        Toast.makeText(getActivity(), getString(R.string.view_swiped_right, swipedElement),
                Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onViewSwipedToLeft(int position) {
        String swipedElement = swipeAdapter.getItem(position);
        Toast.makeText(getContext(), getString(R.string.view_swiped_left, swipedElement),
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onStackEmpty() {
        Toast.makeText(getContext(), R.string.stack_empty, Toast.LENGTH_SHORT).show();
    }

    public class SwipeStackAdapter extends BaseAdapter {

        //ADD TPP TO LIST, CREATE MODEL

        private List<String> data;

        public SwipeStackAdapter(List<String> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public String getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.school_card, parent, false);
            }

            TextView textViewCard = (TextView) convertView.findViewById(R.id.borough);
            textViewCard.setText(data.get(position));
            return convertView;
        }
    }
}
