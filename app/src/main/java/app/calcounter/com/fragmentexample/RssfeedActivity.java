package app.calcounter.com.fragmentexample;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
public class RssfeedActivity extends Activity implements
        MyListFragment.OnItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getResources().getBoolean(R.bool.twoPaneMode)) {
            // all good, we use the fragments defined in the layout
            return;
        }
        // if savedInstanceState is null we do some cleanup
        if (savedInstanceState != null) {
            // cleanup any existing fragments in case we are in detailed mode
            getFragmentManager().executePendingTransactions();
            Fragment fragmentById = getFragmentManager().
                    findFragmentById(R.id.fragment_container);
            if (fragmentById!=null) {
                getFragmentManager().beginTransaction()
                        .remove(fragmentById).commit();
            }
        }
        MyListFragment listFragment = new MyListFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, listFragment).commit();
    }
    @Override
    public void onRssItemSelected(String text) {
        if (getResources().getBoolean(R.bool.twoPaneMode)) {
            DetailFragment fragment = (DetailFragment) getFragmentManager()
                    .findFragmentById(R.id.detailFragment);
            fragment.setText(text);
        } else {
            // replace the fragment
            // Create fragment and give it an argument for the selected article
            DetailFragment newFragment = new DetailFragment();
            Bundle args = new Bundle();
            args.putString(DetailFragment.EXTRA_TEXT, text);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            Reference:	http://www.vogella.com/tutorials/AndroidFragments/article.html#fragments 7
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        }
    }
}
