package com.bignerdranch.android.criminalintent;

import java.util.UUID;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CrimeListFragment extends ListFragment {

    private CrimeLab mCrimeLab;
    private boolean mSubtitleVisible = false;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        getActivity().setTitle(R.string.crimes_title);

        mCrimeLab = CrimeLab.get(getActivity());
        final CrimeAdapter crimeAdapter = new CrimeAdapter(mCrimeLab.getCrimeList(), getActivity());
        setListAdapter(crimeAdapter);

        setRetainInstance(true);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.crime_list, container, false);
        configureEmptyListButton(view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (mSubtitleVisible) {
                getActivity().getActionBar().setSubtitle(R.string.subtitle);
            }
        }

        ListView listView = (ListView) view.findViewById(android.R.id.list);
        
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            // Use floating context menus on Froyo and Gingerbread
            registerForContextMenu(listView);
        } else {
            // Use contextual action bar on Honeycomb and higher
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.crime_list_item_context, menu);
                    return true;
                }
                
                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    boolean success = false;

                    switch (item.getItemId()) {
                    case R.id.menu_item_delete_crime:
                        CrimeAdapter adapter = (CrimeAdapter)getListAdapter();
                        CrimeLab crimeLab = CrimeLab.get(getActivity());
                        
                        for (int i = adapter.getCount() - 1; i >= 0; i--) {
                            if (getListView().isItemChecked(i)) {
                                crimeLab.deleteCrime(adapter.getItem(i));
                            }
                        }
                        
                        mode.finish();
                        adapter.notifyDataSetChanged();
                        
                        success = true;
                        break;
                    }
                    
                    return success;
                }
                
                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }
                
                @Override
                public void onDestroyActionMode(ActionMode mode) {
                }
                
                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                }
            });
        }

        return view;
    }
    
    @Override
    public void onPause() {
        super.onPause();
        try {
            mCrimeLab.saveCrimes(getActivity());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    void configureEmptyListButton(View view) {
        Button emptyListButton = (Button) view.findViewById(R.id.empty_list_button);
        emptyListButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addNewCrime();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible && showSubtitle != null) {
            showSubtitle.setTitle(R.string.hide_subtitle);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        CrimeAdapter adapter = (CrimeAdapter)getListAdapter();
        Crime crime = adapter.getItem(position);
        
        boolean complete = false;

        switch (item.getItemId()) {
        case R.id.menu_item_delete_crime:
            CrimeLab.get(getActivity()).deleteCrime(crime);
            adapter.notifyDataSetChanged();
            complete = true;
            break;
        default:
            complete = super.onContextItemSelected(item);
        }

        return complete;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = false;

        switch (item.getItemId()) {
        case R.id.menu_item_new_crime:
            addNewCrime();
            result = true;
            break;
        case R.id.menu_item_show_subtitle:
            toggleSubTitle(item);
            result = true;
            break;
        default:
            result = super.onOptionsItemSelected(item);
            break;
        }

        return result;
    }

    void addNewCrime() {
        Crime crime = new Crime();
        mCrimeLab.addCrime(crime);
        final Intent intent = newCrimePagerIntent(crime.getId());
        startActivityForResult(intent, 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    void toggleSubTitle(MenuItem item) {
        ActionBar actionBar = getActivity().getActionBar();
        CharSequence subTitle = actionBar.getSubtitle();

        if (subTitle == null) {
            actionBar.setSubtitle(R.string.subtitle);
            item.setTitle(R.string.hide_subtitle);
            mSubtitleVisible = false;
        } else {
            actionBar.setSubtitle(null);
            item.setTitle(R.string.show_subtitle);
            mSubtitleVisible = true;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        ((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(final ListView listView, final View v, final int position, final long id) {
        final Crime crime = ((CrimeAdapter) getListAdapter()).getItem(position);
        Log.i(this.getClass().getSimpleName(), "" + position);
        final Intent intent = newCrimePagerIntent(crime.getId());
        startActivity(intent);
    }

    Intent newCrimePagerIntent(UUID uuid) {
        final Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
        intent.putExtra(CrimeFragment.CRIME_KEY, uuid);

        return intent;
    }

}
